package com.ftnisa.isa.model.user;

import com.ftnisa.isa.model.vehicle.Vehicle;

import javax.persistence.*;

@Entity
@Table(name = "isa_driver")
public class Driver extends User {
    @Column(name = "driver_license", nullable = false)
    private String driverLicense;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "occupied", nullable = false)
    private boolean occupied;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "driver")
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;



    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
