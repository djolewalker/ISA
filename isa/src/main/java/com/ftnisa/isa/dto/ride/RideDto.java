package com.ftnisa.isa.dto.ride;

import com.ftnisa.isa.dto.location.LocationDto;
import com.ftnisa.isa.dto.route.RouteDto;
import com.ftnisa.isa.dto.user.DriverDto;
import com.ftnisa.isa.dto.vehicle.VehicleTypeDto;
import com.ftnisa.isa.model.ride.Rejection;
import com.ftnisa.isa.model.ride.RideStatus;
import com.ftnisa.isa.model.ride.RouteOptimizationCriteria;
import com.ftnisa.isa.model.route.Route;
import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.vehicle.VehicleType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private DriverDto driver;

    private List<RouteDto> routes;

    private RejectionDto rejection;

    private VehicleTypeDto vehicleType;


    public RideDto() {
    }

    public RideDto(LocalDateTime startTime, LocalDateTime finishTime, int numberOfPassengers, float totalPrice, Duration estimatedDuration, RideStatusDto rideStatus, RouteOptimizationCriteriaDto routeOptimizationCriteria, Boolean panicFlag, Boolean petTransportFlag, Boolean babyTransportFlag, DriverDto driver, List<RouteDto> routes, RejectionDto rejection, VehicleTypeDto vehicleType) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.numberOfPassengers = numberOfPassengers;
        this.totalPrice = totalPrice;
        this.estimatedDuration = estimatedDuration;
        this.rideStatus = rideStatus;
        this.routeOptimizationCriteria = routeOptimizationCriteria;
        this.panicFlag = panicFlag;
        this.petTransportFlag = petTransportFlag;
        this.babyTransportFlag = babyTransportFlag;
        this.driver = driver;
        this.routes = routes;
        this.rejection = rejection;
        this.vehicleType = vehicleType;
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

    public DriverDto getDriver() {
        return driver;
    }

    public void setDriver(DriverDto driver) {
        this.driver = driver;
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

    public VehicleTypeDto getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleTypeDto vehicleType) {
        this.vehicleType = vehicleType;
    }
}
