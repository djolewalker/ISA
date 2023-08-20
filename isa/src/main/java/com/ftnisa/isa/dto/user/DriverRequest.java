package com.ftnisa.isa.dto.user;

import com.ftnisa.isa.dto.vehicle.VehicleRequest;

public class DriverRequest extends UserRequest {
    private String driverLicense;
    private VehicleRequest vehicle;

    public DriverRequest() {
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public VehicleRequest getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleRequest vehicle) {
        this.vehicle = vehicle;
    }
}
