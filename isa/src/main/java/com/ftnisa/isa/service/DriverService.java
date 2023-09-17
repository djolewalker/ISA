package com.ftnisa.isa.service;

import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.user.Driver;

import java.util.List;

public interface DriverService {

    List<Driver> getActiveDrivers();

    List<Driver> getFreeActiveDrivers(List<Driver> drivers);

    List<Driver> getDriversWithoutNextBooking(List<Driver> activeDrivers);

    // PENDING time limit has to be added
    boolean checkIfDriverHasNextBooking(Driver driver);

    boolean checkDailyWorkHourLimit(Driver driver, float newRideDuration);

    Driver selectCurrentlyClosestDriver(List<Driver> drivers, Location location);

    Driver selectClosestDriverAfterCurrentRide(List<Driver> drivers, Location location);

    Ride getDriversCurrentRide(Driver driver);

    Driver findDriverById(int id);

    Driver findDriverByUsername(String username);

    Driver activateDriver(String username);

    Driver deactivateDriver(String username);

    Driver updateDriverLocation(int id, float lon, float lat);
}
