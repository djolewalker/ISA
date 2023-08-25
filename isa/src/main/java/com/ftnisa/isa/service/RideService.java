package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.ride.*;
import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.ride.Panic;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.model.vehicle.VehicleType;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface RideService {

    Ride bookARide(RideBookingRequestDto rideDto);

    Ride requestQuickRideBooking(Ride ride);

    Ride recreateRide(Integer rideId);

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


    List<Ride> getUsersWholeRideHistory(Integer userId);

    List<Ride> getUsersRidesBetweenDates(Integer userId, LocalDateTime date1, LocalDateTime date2);

    void addRideToFavourites(Integer rideId);
}