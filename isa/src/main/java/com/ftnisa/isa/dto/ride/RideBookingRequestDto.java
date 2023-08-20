package com.ftnisa.isa.dto.ride;

import com.ftnisa.isa.dto.location.LocationDto;
import com.ftnisa.isa.dto.vehicle.VehicleTypeResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RideBookingRequestDto {

    private LocationDto startLocation;

    private LocationDto finishLocation;

    private ArrayList<LocationDto> stops;

    private Boolean petTransportFlag;

    private Boolean babyTransportFlag;

    private VehicleTypeResponse vehicleType;

    private int numberOfPassengers;

    private boolean isScheduled;

    private LocalDateTime scheduledStartTime;

    private RouteOptimizationCriteriaDto routeOptimizationCriteria;

    private boolean optimizeStops;




    public RideBookingRequestDto() {
    }

    public RideBookingRequestDto(LocationDto startLocation, LocationDto finishLocation, ArrayList<LocationDto> stops, Boolean petTransportFlag, Boolean babyTransportFlag, VehicleTypeResponse vehicleType, int numberOfPassengers, boolean isScheduled, LocalDateTime scheduledStartTime, RouteOptimizationCriteriaDto routeOptimizationCriteria, boolean optimizeStops) {
        this.startLocation = startLocation;
        this.finishLocation = finishLocation;
        this.stops = stops;
        this.petTransportFlag = petTransportFlag;
        this.babyTransportFlag = babyTransportFlag;
        this.vehicleType = vehicleType;
        this.numberOfPassengers = numberOfPassengers;
        this.isScheduled = isScheduled;
        this.scheduledStartTime = scheduledStartTime;
        this.routeOptimizationCriteria = routeOptimizationCriteria;
        this.optimizeStops = optimizeStops;
    }

    public LocationDto getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LocationDto startLocation) {
        this.startLocation = startLocation;
    }

    public LocationDto getFinishLocation() {
        return finishLocation;
    }

    public void setFinishLocation(LocationDto finishLocation) {
        this.finishLocation = finishLocation;
    }

    public ArrayList<LocationDto> getStops() {
        return stops;
    }

    public void setStops(ArrayList<LocationDto> stops) {
        this.stops = stops;
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

    public VehicleTypeResponse getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleTypeResponse vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }

    public LocalDateTime getScheduledStartTime() {
        return scheduledStartTime;
    }

    public void setScheduledStartTime(LocalDateTime scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }

    public RouteOptimizationCriteriaDto getRouteOptimizationCriteria() {
        return routeOptimizationCriteria;
    }

    public void setRouteOptimizationCriteria(RouteOptimizationCriteriaDto routeOptimizationCriteria) {
        this.routeOptimizationCriteria = routeOptimizationCriteria;
    }

    public boolean isOptimizeStops() {
        return optimizeStops;
    }

    public void setOptimizeStops(boolean optimizeStops) {
        this.optimizeStops = optimizeStops;
    }
}
