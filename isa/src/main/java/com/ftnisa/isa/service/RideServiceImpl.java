package com.ftnisa.isa.service;

import com.ftnisa.isa.model.ride.Panic;
import com.ftnisa.isa.model.ride.Rejection;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.ride.RideStatus;
import com.ftnisa.isa.model.route.Route;
import com.ftnisa.isa.model.user.Driver;

import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.model.vehicle.VehicleType;

import com.ftnisa.isa.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RideServiceImpl implements RideService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RideRepository rideRepository;

    private final DriverService driverService;

    private final RouteService routeService;

    private final UserRepository userRepository;

    private final PanicRepository panicRepository;

    private final RejectionRepository rejectionRepository;

    private final RouteRepository routeRepository;

    private final NotificationService notificationService;


    public RideServiceImpl(RideRepository rideRepository, DriverService driverService, RouteService routeService, UserRepository userRepository, PanicRepository panicRepository, RejectionRepository rejectionRepository, RouteRepository routeRepository, NotificationService notificationService) {
        this.rideRepository = rideRepository;
        this.driverService = driverService;
        this.routeService = routeService;
        this.userRepository = userRepository;
        this.panicRepository = panicRepository;
        this.rejectionRepository = rejectionRepository;
        this.routeRepository = routeRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Ride requestQuickRideBooking(Ride ride) {

        // set the ride passenger
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User passenger = userRepository.findByUsername(user.getUsername());
        ride.setPassenger(passenger);

        //check if passenger has an active ride, if yes, reject
        if (!rideRepository.findByPassengerAndRideStatus(passenger, RideStatus.ACTIVE).isEmpty()){
            Rejection rejection = new Rejection("Sorry, new ride cant be booked while having an active ride", LocalDateTime.now());
            ride.setRejection(rejection);
            ride.setRideStatus(RideStatus.REJECTED);
            rideRepository.save(ride);
            return ride;
        }

        // check if there are active drivers (else abort)
        List<Driver> activeDrivers = driverService.getActiveDrivers();

        if (activeDrivers.isEmpty()){
            Rejection rejection = new Rejection("Sorry, there are no active drivers currently", LocalDateTime.now());
            ride.setRejection(rejection);
            ride.setRideStatus(RideStatus.REJECTED);
            rideRepository.save(ride);
            return ride;
        }

        // if all drivers are taken and already have a ride booked after the current one, then abort
        List<Driver> freeActiveDrivers = driverService.getFreeActiveDrivers(activeDrivers);
        List<Driver> driversWithoutNextBooking = driverService.getDriversWithoutNextBooking(activeDrivers);

        if (freeActiveDrivers.isEmpty() && driversWithoutNextBooking.isEmpty()){
            Rejection rejection = new Rejection( "Sorry, there are no available drivers for this ride. Try again a bit later.", LocalDateTime.now());
            ride.setRejection(rejection);
            ride.setRideStatus(RideStatus.REJECTED);
            rideRepository.save(ride);
            return ride;
        }

        // get the distances for the routes from openstreetmaps
        long rideLengthMeters = 0;
        float rideDurationMinutes = 0;
        for (Route route : ride.getRoutes()){
            rideLengthMeters = rideLengthMeters + routeService.fetchRouteLengthMeters(route);
            rideDurationMinutes = rideDurationMinutes + routeService.fetchRouteDurationMinutes(route);
        }

        // choose the driver and find his estimated arrival time
        if (!freeActiveDrivers.isEmpty()){
            List<Driver> appropriateDrivers = filterDriversByRideCriteria(
                    freeActiveDrivers,
                    ride.getPetTransportFlag(),
                    ride.getBabyTransportFlag(),
                    ride.getVehicleType(),
                    ride.getNumberOfPassengers(),
                    rideDurationMinutes
            );
            Driver chosenDriver = driverService.selectCurrentlyClosestDriver(appropriateDrivers, routeService.getRidesStartLocation(ride));
            ride.setDriver(chosenDriver);
        } else {
            List<Driver> appropriateDrivers = filterDriversByRideCriteria(
                    driversWithoutNextBooking,
                    ride.getPetTransportFlag(),
                    ride.getBabyTransportFlag(),
                    ride.getVehicleType(),
                    ride.getNumberOfPassengers(),
                    rideDurationMinutes
            );
            Driver chosenDriver = driverService.selectClosestDriverAfterCurrentRide(appropriateDrivers, routeService.getRidesStartLocation(ride));
            ride.setDriver(chosenDriver);
        }
        ride.setStartTime(estimateDriversTimeOfArrival(ride));

        // calculate the price and the  estimated finish time
        ride.setTotalPrice(calculateRidePrice(rideLengthMeters, ride.getVehicleType()));
        ride.setFinishTime(ride.getStartTime().plusMinutes((long)rideDurationMinutes));
        ride.setEstimatedDuration(Duration.of((long)rideDurationMinutes, ChronoUnit.MINUTES));
        ride.setRideStatus(RideStatus.PENDING);

        // save and return
        rideRepository.save(ride);
        routeService.saveRoutesForRide(ride);
        return ride;

    }

    @Transactional
    @Override
    public Ride scheduledRideBooking(Ride ride) {

        // set the ride passenger
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User passenger = userRepository.findByUsername(user.getUsername());
        ride.setPassenger(passenger);

        //check if passenger has an active ride, if yes, reject
        if (!rideRepository.findByPassengerAndRideStatus(passenger, RideStatus.ACTIVE).isEmpty()){
            Rejection rejection = new Rejection("Sorry, new ride cant be booked while having an active ride", LocalDateTime.now());
            ride.setRejection(rejection);
            ride.setRideStatus(RideStatus.REJECTED);
            rideRepository.save(ride);
            return ride;
        }

        // if ride start time is more than 5 hours later reject
        if (ride.getStartTime().isAfter(LocalDateTime.now().plusHours(5))){
            Rejection rejection = new Rejection("Sorry, the ride cant be scheduled more than 5 hours in advance", LocalDateTime.now());
            ride.setRejection(rejection);
            ride.setRideStatus(RideStatus.REJECTED);
            rideRepository.save(ride);
            return ride;
        }

        // check if there are active drivers (else abort)
        List<Driver> activeDrivers = driverService.getActiveDrivers();

        if (activeDrivers.isEmpty()){
            Rejection rejection = new Rejection("Sorry, there are no active drivers currently", LocalDateTime.now());
            ride.setRejection(rejection);
            ride.setRideStatus(RideStatus.REJECTED);
            rideRepository.save(ride);
            return ride;
        }

        // get the distances for the routes from openstreetmaps
        long rideLengthMeters = 0;
        float rideDurationMinutes = 0;
        for (Route route : ride.getRoutes()){
            rideLengthMeters = rideLengthMeters + routeService.fetchRouteLengthMeters(route);
            rideDurationMinutes = rideDurationMinutes + routeService.fetchRouteDurationMinutes(route);
        }

        // choose the driver
        List<Driver> appropriateDrivers = filterDriversByRideCriteria(
                activeDrivers,
                ride.getPetTransportFlag(),
                ride.getBabyTransportFlag(),
                ride.getVehicleType(),
                ride.getNumberOfPassengers(),
                rideDurationMinutes
        );

        List<Driver> schedulableAppropriateDrivers = filterDriversBySchedule(appropriateDrivers, ride);


        Driver chosenDriver = driverService.selectCurrentlyClosestDriver(schedulableAppropriateDrivers, routeService.getRidesStartLocation(ride));

        ride.setDriver(chosenDriver);


        ride.setTotalPrice(calculateRidePrice(rideLengthMeters, ride.getVehicleType()));
        ride.setFinishTime(ride.getStartTime().plusMinutes((long)rideDurationMinutes));
        ride.setRideStatus(RideStatus.PENDING);

        // save and return
        rideRepository.save(ride);
        routeService.saveRoutesForRide(ride);
        notificationService.createScheduledDriveReminders(passenger, ride.getStartTime());
        return ride;

    }

    @Override
    @Transactional
    public Ride recreateRide(Integer rideId){
        Ride oldRide = rideRepository.findById(rideId).orElse(null);
        if (oldRide == null){
            return  null;
        }
        Ride newRide = new Ride();
        newRide.setNumberOfPassengers(oldRide.getNumberOfPassengers());
        newRide.setRouteOptimizationCriteria(oldRide.getRouteOptimizationCriteria());
        newRide.setPetTransportFlag(oldRide.getPetTransportFlag());
        newRide.setBabyTransportFlag(oldRide.getBabyTransportFlag() );
        newRide.setVehicleType( new VehicleType(oldRide.getVehicleType().getVehicleTypeName(), oldRide.getVehicleType().getPricePerKm()) );
        newRide.setRoutes(routeService.cloneRoutes(oldRide.getRoutes()));

        return newRide;

    }



    // if user confirmed the price and the reservation finalize the ride booking  (else abort)
    @Override
    public void finalizeRideBooking(boolean isRideAccepted, int rideId){
        Ride ride = rideRepository.findOneById(rideId);
        if (isRideAccepted){
            ride.setRideStatus(RideStatus.ACCEPTED);
            notificationService.createInstantNotification(ride.getPassenger(), "Vaša vožnja je upravo zakazana. Status vožnje možete pratiti na Vašem dešbordu.");
            notificationService.createInstantNotification(ride.getDriver(), "Zakazana Vam je nova vožnja");
        } else {
            ride.setRideStatus(RideStatus.REJECTED);
            ride.setRejection(new Rejection( "Passenger did not accept the ride", ride.getPassenger() , LocalDateTime.now()));
        }
        //save
        rideRepository.save(ride);
    }


    @Override
    @Transactional
    public void startRideByDriver(Integer rideId){
        Ride ride = rideRepository.findOneById(rideId);
        ride.setRideStatus(RideStatus.ACTIVE);
        ride.getDriver().setOccupied(true);
        rideRepository.save(ride);
        notificationService.createInstantNotification(ride.getPassenger(), "Vaša vožnja je započeta.");
    }


    @Override
    @Transactional
    public void finishRideByDriver(Integer rideId){
        Ride ride = rideRepository.findOneById(rideId);
        ride.setRideStatus(RideStatus.FINISHED);
        ride.getDriver().setOccupied(false);
        rideRepository.save(ride);
        notificationService.createInstantNotification(ride.getPassenger(), "Vaša vožnja je završena.");
    }


    @Override
    public Panic panic(Integer userId, Integer rideId, String panicReason){
        User user = userRepository.findById(userId).orElse(null);
        Ride ride = rideRepository.findById(rideId).orElse(null);
        if (user == null || ride == null){
            return null;
        }
        Panic panic = new Panic(LocalDateTime.now(), user, panicReason, ride);
        panicRepository.save(panic);
        ride.setPanicFlag(true);
        rideRepository.save(ride);
        return panic;
    }


    @Override
    @Transactional
    public void rejectRideByDriver(Integer rideId, String rejectionReason){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Ride ride = rideRepository.findOneById(rideId);
        ride.setRideStatus(RideStatus.REJECTED);
        Rejection rejection = new Rejection( rejectionReason, user, LocalDateTime.now());
        ride.setRejection(rejection);
        rideRepository.save(ride);

        // notification
        User passenger = ride.getPassenger();
        notificationService.createInstantNotification(passenger, "Vašu vožnju je vozač nažalost morao da otkaže.");
    }


    @Override
    public List<Driver> filterDriversByRideCriteria(List<Driver> drivers, Boolean isPetTransported, Boolean isBabyTransported, VehicleType vehicleType, int numberOfPassengers, float newRideDurationMinutes){

        if (isPetTransported){
            drivers = drivers.stream().filter(d -> d.getVehicle().isPetFriendly()).toList();
        }
        if (isBabyTransported){
            drivers = drivers.stream().filter(d -> d.getVehicle().isBabyFriendly()).toList();
        }

        drivers = drivers.stream().filter(d -> d.getVehicle().getVehicleType().getVehicleTypeName() == vehicleType.getVehicleTypeName())
                .filter(d -> d.getVehicle().getNumberOfSeats() >= numberOfPassengers)
                .filter(d -> driverService.checkDailyWorkHourLimit(d, newRideDurationMinutes)).toList();

        return drivers;
    }

    @Override
    public float calculateRidePrice(long rideLength, VehicleType vehicleType){
        float price = 120 + (vehicleType.getPricePerKm()*rideLength/1000);
        return price;
    }


    @Override
    public LocalDateTime estimateDriversTimeOfArrival(Ride ride){
        Driver driver = ride.getDriver();

        if (!driver.isOccupied()){
             return LocalDateTime.now().plusMinutes(routeService.fetchTimeInMinutesBetweenLocations(driver.getVehicle().getCurrentLocation(), routeService.getRidesStartLocation(ride)));
        } else {
            Ride driversCurrentRide = driverService.getDriversCurrentRide(driver);
            return  driversCurrentRide.getFinishTime().plusMinutes(routeService.fetchTimeInMinutesBetweenLocations(routeService.getRidesFinishLocation(driversCurrentRide),routeService.getRidesStartLocation(ride)));
        }
    }


    @Override
    public boolean checkIfRidesOverlap(Ride ride1, Ride ride2){
        if (ride1.getFinishTime()
                .plusMinutes(routeService.fetchTimeInMinutesBetweenLocations(routeService.getRidesFinishLocation(ride1), routeService.getRidesStartLocation(ride2)))
                .isBefore(ride2.getStartTime())
            ||
           (ride2.getFinishTime()
                .plusMinutes(routeService.fetchTimeInMinutesBetweenLocations(routeService.getRidesFinishLocation(ride2), routeService.getRidesStartLocation(ride1)))
                .isBefore(ride1.getStartTime())))
        {
            return false;
        } else {
            return true;
        }
    }

    public List<Driver> filterDriversBySchedule(List<Driver> drivers, Ride ride){
        List<Driver> schedulableDrivers = new ArrayList<>();
        for (Driver d : drivers){
            if (checkIfRideIsSchedulableForDriver(ride, d)){
                schedulableDrivers.add(d);
            }
        }
        return schedulableDrivers;
    }


    @Override
    public boolean checkIfRideIsSchedulableForDriver(Ride ride, Driver driver){
        List<Ride> rides = new ArrayList<>();
        rides.addAll(rideRepository.findByDriverAndRideStatus(driver, RideStatus.ACTIVE));
        rides.addAll(rideRepository.findByDriverAndRideStatus(driver, RideStatus.ACCEPTED));
        rides.addAll(rideRepository.findByDriverAndRideStatus(driver, RideStatus.PENDING));

        for (Ride r : rides){
            if (checkIfRidesOverlap(r, ride)){
                return false;
            }
        }
        return true;
    }


    @Override
    public List<Ride> getUsersWholeRideHistory(Integer userId){
        return rideRepository.findByPassenger(userRepository.findById(userId).orElse(null));
    }

    @Override
    public List<Ride> getUsersRidesBetweenDates(Integer userId, LocalDateTime date1, LocalDateTime date2){
        User user = userRepository.findById(userId).orElse(null);
        if (user == null){
            return null;
        }
        return rideRepository.findByPassengerAndStartTimeBetween(user, date1, date2);
    }

    @Override
    public void addRideToFavourites(Integer rideId){
        Ride ride = rideRepository.findById(rideId).orElse(null);
        if (ride != null){
            ride.setFavourite(true);
        }
    }










}
