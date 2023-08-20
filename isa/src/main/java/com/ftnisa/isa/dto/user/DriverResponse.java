package com.ftnisa.isa.dto.user;

import com.ftnisa.isa.dto.vehicle.VehicleResponse;

public class DriverResponse extends UserRequest {
    private boolean active;
    private boolean occupied;

    private String username;
    private String driverLicense;
    private VehicleResponse vehicle;

    public DriverResponse() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public VehicleResponse getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleResponse vehicle) {
        this.vehicle = vehicle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
