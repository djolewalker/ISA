package com.ftnisa.isa.dto.ride;

import com.ftnisa.isa.dto.route.RouteDto;
import com.ftnisa.isa.dto.user.DriverResponse;
import com.ftnisa.isa.dto.vehicle.VehicleTypeResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class RideDto {
    private LocalDateTime startTime;

    private LocalDateTime finishTime;

    private int numberOfPassengers;

    private float totalPrice;

    private Duration estimatedDuration;

    private RideStatusDto rideStatus;

    private RouteOptimizationCriteriaDto routeOptimizationCriteria;

    private Boolean panicFlag;

    private Boolean petTransportFlag;

    private Boolean babyTransportFlag;

    private DriverResponse driver;

    private List<RouteDto> routes;

    private RejectionDto rejection;

    private VehicleTypeResponse vehicleType;


    public RideDto() {
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
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

    public RideStatusDto getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatusDto rideStatus) {
        this.rideStatus = rideStatus;
    }

    public RouteOptimizationCriteriaDto getRouteOptimizationCriteria() {
        return routeOptimizationCriteria;
    }

    public void setRouteOptimizationCriteria(RouteOptimizationCriteriaDto routeOptimizationCriteria) {
        this.routeOptimizationCriteria = routeOptimizationCriteria;
    }

    public Boolean getPanicFlag() {
        return panicFlag;
    }

    public void setPanicFlag(Boolean panicFlag) {
        this.panicFlag = panicFlag;
    }

    public Boolean getPetTransportFlag() {
        return petTransportFlag;
    }

    public void setPetTransportFlag(Boolean petTransportFlag) {
        this.petTransportFlag = petTransportFlag;
    }

    public Boolean getBabyTransportFlag() {
        return babyTransportFlag;
    }

    public void setBabyTransportFlag(Boolean babyTransportFlag) {
        this.babyTransportFlag = babyTransportFlag;
    }

    public List<RouteDto> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteDto> routes) {
        this.routes = routes;
    }

    public RejectionDto getRejection() {
        return rejection;
    }

    public void setRejection(RejectionDto rejection) {
        this.rejection = rejection;
    }
}
