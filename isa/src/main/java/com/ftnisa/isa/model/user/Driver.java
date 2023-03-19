package com.ftnisa.isa.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "isa_driver")
public class Driver extends User {
    @Column(name = "driver_license", nullable = false)
    private String driverLicense;

    @Column(name = "vehicle_registration", nullable = false)
    private String vehicleRegistration;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "accepted", nullable = false)
    private boolean accepted;

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

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
