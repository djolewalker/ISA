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

    Ride bookARide(RideBookingRequestDto rideDto) throws Exception;

    Ride requestQuickRideBooking(Ride ride) throws Exception;

    Ride recreateRide(RecreateRideDto recreateRideDto) throws Exception;

    void finalizeRideBooking(boolean isRideAccepted, int rideId);

    void startRideByDriver(Integer rideId);

    void finishRideByDriver(Integer rideId);

    Panic panic(Integer userId, Integer rideId, String panicReason);

    Ride scheduledRideBooking(Ride ride) throws Exception;

    void rejectRideByDriver(Integer rideId, String rejectionReason);

    List<Driver> filterDriversByRideCriteria(List<Driver> drivers, Boolean isPetTransported, Boolean isBabyTransported, VehicleType vehicleType, int numberOfPassengers, float newRideDurationMinutes);

    float calculateRidePrice(long rideLength, VehicleType vehicleType);

//    Location getRidesFinishLocation(Ride ride);
//
//    Location getRidesStartLocation(Ride ride);

    LocalDateTime estimateDriversTimeOfArrival(Ride ride) throws Exception;

    boolean checkIfRidesOverlap(Ride ride1, Ride ride2) throws Exception;

    List<Driver> filterDriversBySchedule(List<Driver> drivers, Ride ride) throws Exception;

    boolean checkIfRideIsSchedulableForDriver(Ride ride, Driver driver) throws Exception;


    List<Ride> getUsersWholeRideHistory(Integer userId);

    List<Ride> getUsersRidesBetweenDates(Integer userId, LocalDateTime date1, LocalDateTime date2);

    void addRideToFavourites(Integer rideId);
}
