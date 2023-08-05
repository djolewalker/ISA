package com.ftnisa.isa.dto.vehicle;

import com.ftnisa.isa.dto.location.LocationDto;
import com.ftnisa.isa.dto.user.UserResponse;
import com.ftnisa.isa.model.vehicle.VehicleType;

public class VehicleDto {

    private String vehicleModel;

    private String registrationNumber;

    private int numberOfSeats;

    private boolean babyFriendly;

    private boolean petFriendly;

    private Integer userId;

    private VehicleTypeDto vehicleType;

    private LocationDto currentLocation;



    public VehicleDto() {
    }


    public VehicleDto(String vehicleModel, String registrationNumber, int numberOfSeats, boolean babyFriendly, boolean petFriendly, Integer userId, VehicleTypeDto vehicleType, LocationDto currentLocation) {
        this.vehicleModel = vehicleModel;
        this.registrationNumber = registrationNumber;
        this.numberOfSeats = numberOfSeats;
        this.babyFriendly = babyFriendly;
        this.petFriendly = petFriendly;
        this.userId = userId;
        this.vehicleType = vehicleType;
        this.currentLocation = currentLocation;
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

    public VehicleTypeDto getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleTypeDto vehicleType) {
        this.vehicleType = vehicleType;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocationDto getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(LocationDto currentLocation) {
        this.currentLocation = currentLocation;
    }
}
