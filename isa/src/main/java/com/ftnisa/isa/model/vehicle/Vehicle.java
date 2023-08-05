package com.ftnisa.isa.model.vehicle;


import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.user.User;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "isa_vehicle")
public class Vehicle {

    @Id
    @SequenceGenerator(name = "vehicleSeqGen", sequenceName = "vehicleSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicleSeqGen")
    private Integer id;

    @Column(name = "vehicle_model", nullable = false)
    private String vehicleModel;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(name = "number_of_seats", nullable = false)
    private int numberOfSeats;

    @Column(name = "baby_friendly")
    private boolean babyFriendly;

    @Column(name = "pet_friendly")
    private boolean petFriendly;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private User driver;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_type")
    private VehicleType vehicleType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location")
    private Location currentLocation;

    public Vehicle() {
    }

    public Vehicle(String vehicleModel, String registrationNumber, int numberOfSeats, boolean babyFriendly, boolean petFriendly, User driver, VehicleType vehicleType, Location currentLocation) {
        this.vehicleModel = vehicleModel;
        this.registrationNumber = registrationNumber;
        this.numberOfSeats = numberOfSeats;
        this.babyFriendly = babyFriendly;
        this.petFriendly = petFriendly;
        this.driver = driver;
        this.vehicleType = vehicleType;
        this.currentLocation = currentLocation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public boolean isBabyFriendly() {
        return babyFriendly;
    }

    public void setBabyFriendly(boolean babyFriendly) {
        this.babyFriendly = babyFriendly;
    }

    public boolean isPetFriendly() {
        return petFriendly;
    }

    public void setPetFriendly(boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}
