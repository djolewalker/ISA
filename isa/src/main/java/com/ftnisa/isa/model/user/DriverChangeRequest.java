package com.ftnisa.isa.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.notification.UserNotification;
import com.ftnisa.isa.model.vehicle.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "isa_driver_change")
public class DriverChangeRequest {

    @Id
    @SequenceGenerator(name = "driverChangeSeqGen", sequenceName = "driverChangeSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driverChangeSeqGen")
    private Integer id;

    @Column(name = "driverId")
    private Integer driverId;

    @Column(name = "approved")
    private boolean isApproved;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Column(name = "approval_time")
    private LocalDateTime approvalTime;



    @Column(name = "username")
    private String username;

//    @JsonIgnore
//    @Column(name = "password")
//    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "image")
    private String image;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "driver_license", nullable = false)
    private String driverLicense;


    //vehicle
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

    @Column(name = "vehicle_type")
    private Integer vehicleTypeId;



    public DriverChangeRequest() {
    }

    public DriverChangeRequest(Integer driverId, boolean isApproved, User approvedBy, LocalDateTime approvalTime, String username, String email, String firstname, String lastname, String image, String phone, String address, String driverLicense, String vehicleModel, String registrationNumber, int numberOfSeats, boolean babyFriendly, boolean petFriendly, Integer vehicleTypeId) {
        this.driverId = driverId;
        this.isApproved = isApproved;
        this.approvedBy = approvedBy;
        this.approvalTime = approvalTime;
        this.username = username;
        //this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.image = image;
        this.phone = phone;
        this.address = address;
        this.driverLicense = driverLicense;
        this.vehicleModel = vehicleModel;
        this.registrationNumber = registrationNumber;
        this.numberOfSeats = numberOfSeats;
        this.babyFriendly = babyFriendly;
        this.petFriendly = petFriendly;
        this.vehicleTypeId = vehicleTypeId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDateTime getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(LocalDateTime approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
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

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
}
