package com.ftnisa.isa.dto.user;

import com.ftnisa.isa.dto.vehicle.VehicleDto;
import com.ftnisa.isa.model.vehicle.Vehicle;

public class DriverDto extends UserResponse {

    private String driverLicense;

    private String vehicleRegistration;

    private boolean active;

    private boolean occupied;

    private VehicleDto vehicle;

    public DriverDto() {
    }

    public DriverDto(String driverLicense, String vehicleRegistration, boolean active, boolean occupied, VehicleDto vehicle) {
        this.driverLicense = driverLicense;
        this.vehicleRegistration = vehicleRegistration;
        this.active = active;
        this.occupied = occupied;
        this.vehicle = vehicle;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getVehicleRegistration() {
        return vehicleRegistration;
    }

    public void setVehicleRegistration(String vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
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

    public VehicleDto getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDto vehicle) {
        this.vehicle = vehicle;
    }
}
