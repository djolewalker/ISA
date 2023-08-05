package com.ftnisa.isa.service;

import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.ride.RideStatus;
import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.repository.DriverRepository;
import com.ftnisa.isa.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService{


    private final DriverRepository driverRepository;

    private final RideRepository rideRepository;

    private final RouteService routeService;




    public DriverServiceImpl(DriverRepository driverRepository, RideRepository rideRepository, RouteService routeService) {
        this.driverRepository = driverRepository;
        this.rideRepository = rideRepository;
        this.routeService = routeService;

    }

    @Override
    public List<Driver> getActiveDrivers(){
        return driverRepository.findByActiveTrue();
    }

    @Override
    public List<Driver> getFreeActiveDrivers(List<Driver> activeDrivers){

        List<Driver> freeActiveDrivers = new ArrayList<>();
        for (Driver driver : activeDrivers){
            if (driver.isOccupied() == false){
                freeActiveDrivers.add(driver);
            }
        }
        return freeActiveDrivers;
    }

    @Override
    public List<Driver> getDriversWithoutNextBooking(List<Driver> activeDrivers){
        List<Driver> driversWithoutNextBooking = new ArrayList<Driver>();
        for (Driver d : activeDrivers){
            if (d.isOccupied() && !checkIfDriverHasNextBooking(d)){
                driversWithoutNextBooking.add(d);
            }
        }
        return driversWithoutNextBooking;
    }

    // PENDING time limit has to be added
    @Override
    public boolean checkIfDriverHasNextBooking(Driver driver){
        List<Ride> rides = rideRepository.findByDriver(driver);
        LocalDateTime now = LocalDateTime.now();
        for (Ride r : rides){
            if (r.getStartTime().isAfter(now) &&
                    (r.getRideStatus().equals(RideStatus.ACCEPTED) || r.getRideStatus().equals(RideStatus.PENDING)) ){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkDailyWorkHourLimit(Driver driver, float newRideDurationMinutes){
        List<Ride> todaysRides = rideRepository.findByDriverAndStartTimeBetween(driver, LocalDateTime.now().minusHours(24), LocalDateTime.now().plusHours(5));
        float workMinutesToday = 0;
        for (Ride r : todaysRides){
            if (r.getRideStatus() != RideStatus.REJECTED) {
                workMinutesToday = workMinutesToday + (float) r.getStartTime().until(r.getFinishTime(), ChronoUnit.SECONDS) / 60;
            }
        }
        if (workMinutesToday + newRideDurationMinutes > 480){
            return false;
        }
        return true;

    }


    @Override
    public Driver selectCurrentlyClosestDriver(List<Driver> drivers, Location location) {
        Driver driver = drivers.stream().min(Comparator.comparing(d -> routeService.fetchDistanceInMetersBetweenLocations(d.getVehicle().getCurrentLocation(), location))).get();
        return driver;
    }

    @Override
    public Driver selectClosestDriverAfterCurrentRide(List<Driver> drivers, Location location) {

        Driver driver = drivers
                .stream()
                .min(
                        Comparator.comparing(d -> routeService.fetchDistanceInMetersBetweenLocations(routeService.getRidesFinishLocation(getDriversCurrentRide(d)), location))
                ).get();

        return driver;
    }

    @Override
    public Ride getDriversCurrentRide(Driver driver){
        return rideRepository.findOneByDriverAndRideStatus(driver, RideStatus.ACTIVE);
    }





}
