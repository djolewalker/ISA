package com.ftnisa.isa.dto.ride;

import com.ftnisa.isa.model.ride.RideStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class RideBookingResponseDto {


    private int rideId;
    private RideStatusDto rideStatus;

    private RejectionDto rejection;

    private LocalDateTime startTime;

    private int numberOfPassengers;

    private float totalPrice;

    private Duration estimatedDuration;

    public RideBookingResponseDto() {
    }

    public RideBookingResponseDto(int rideId, RideStatusDto rideStatus, RejectionDto rejection, LocalDateTime startTime, int numberOfPassengers, float totalPrice, Duration estimatedDuration) {
        this.rideId = rideId;
        this.rideStatus = rideStatus;
        this.rejection = rejection;
        this.startTime = startTime;
        this.numberOfPassengers = numberOfPassengers;
        this.totalPrice = totalPrice;
        this.estimatedDuration = estimatedDuration;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public RideStatusDto getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatusDto rideStatus) {
        this.rideStatus = rideStatus;
    }

    public RejectionDto getRejection() {
        return rejection;
    }

    public void setRejection(RejectionDto rejection) {
        this.rejection = rejection;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Duration getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Duration estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }
}
