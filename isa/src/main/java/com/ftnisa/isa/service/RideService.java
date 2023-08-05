package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.ride.*;
import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.ride.Panic;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.vehicle.VehicleType;

import java.time.LocalDateTime;
import java.util.List;

public interface RideService {


    Ride requestQuickRideBooking(Ride ride);


    void finalizeRideBooking(boolean isRideAccepted, int rideId);

    void startRideByDriver(Integer rideId);

    void finishRideByDriver(Integer rideId);

    Panic panic(Integer userId, Integer rideId, String panicReason);

    Ride scheduledRideBooking(Ride ride);

    void rejectRideByDriver(Integer rideId, String rejectionReason);

    List<Driver> filterDriversByRideCriteria(List<Driver> drivers, Boolean isPetTransported, Boolean isBabyTransported, VehicleType vehicleType, int numberOfPassengers, float newRideDurationMinutes);

    float calculateRidePrice(long rideLength, VehicleType vehicleType);

//    Location getRidesFinishLocation(Ride ride);
//
//    Location getRidesStartLocation(Ride ride);

    LocalDateTime estimateDriversTimeOfArrival(Ride ride);

    boolean checkIfRidesOverlap(Ride ride1, Ride ride2);

    boolean checkIfRideIsSchedulableForDriver(Ride ride, Driver driver);
}
