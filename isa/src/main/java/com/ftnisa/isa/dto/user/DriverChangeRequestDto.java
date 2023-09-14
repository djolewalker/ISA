package com.ftnisa.isa.dto.user;

import javax.persistence.criteria.CriteriaBuilder;

public class DriverChangeRequestDto {
    private String email;
    private String firstname;
    private String lastname;
    private String image;
    private String phone;
    private String address;

    private Integer driverId;
    private String driverLicense;

    private String vehicleModel;
    private String registrationNumber;
    private int numberOfSeats;
    private boolean babyFriendly;
    private boolean petFriendly;
    private Integer vehicleTypeId;
}
