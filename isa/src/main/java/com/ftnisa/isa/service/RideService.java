package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.ride.*;
import com.ftnisa.isa.model.ride.Panic;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.model.vehicle.VehicleType;

import java.time.LocalDateTime;
import java.util.List;

public interface RideService {

    Ride bookARide(RideBookingRequestDto rideDto) throws Exception;

    Ride requestQuickRideBooking(Ride ride) throws Exception;

    Ride recreateRide(int id, RecreateRideDto recreateRideDto) throws Exception;

    Ride finalizeRideBooking(boolean isRideAccepted, int rideId);

    void startRideByDriver(Integer rideId);

    Ride finishRideByDriver(Integer rideId);

    Panic panic(User user, Integer rideId, String panicReason);

    Ride scheduledRideBooking(Ride ride) throws Exception;

    void rejectRideByDriver(Integer rideId, String rejectionReason);

    List<Driver> filterDriversByRideCriteria(List<Driver> drivers, Boolean isPetTransported, Boolean isBabyTransported,
            VehicleType vehicleType, int numberOfPassengers, float newRideDurationMinutes);

    float calculateRidePrice(long rideLength, VehicleType vehicleType);

    // Location getRidesFinishLocation(Ride ride);
    //
    // Location getRidesStartLocation(Ride ride);

    LocalDateTime estimateDriversTimeOfArrival(Ride ride,Driver driver) throws Exception;

    boolean checkIfRidesOverlap(Ride oldRide, Ride newRide, Driver driver) throws Exception;

    List<Driver> filterDriversBySchedule(List<Driver> drivers, Ride ride) throws Exception;

    boolean checkIfRideIsSchedulableForDriver(Ride ride, Driver driver) throws Exception;

    List<Ride> getUsersWholeRideHistory(User user);

    List<Ride> getDriversWholeRideHistory(Driver driver);

    List<Ride> getUsersRidesBetweenDates(User user, LocalDateTime date1, LocalDateTime date2);

    List<Ride> getDriversRidesBetweenDates(Driver driver, LocalDateTime date1, LocalDateTime date2);

    void addRideToFavourites(Integer rideId);

    Ride findRideById(int id);

    Ride findDriversActiveRide(Driver driver);

    Ride findUsersActiveRide(User user);
}
