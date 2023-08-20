package com.ftnisa.isa.model.ride;


import com.ftnisa.isa.model.route.Route;
import com.ftnisa.isa.model.user.Driver;

import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.model.vehicle.VehicleType;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "isa_ride")
public class Ride {

    @Id
    @SequenceGenerator(name = "rideSeqGen", sequenceName = "rideSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rideSeqGen")
    private Integer id;

    @Column(name = "start_time", nullable = true)
    private LocalDateTime startTime;

    @Column(name = "finish_time", nullable = true)
    private LocalDateTime finishTime;

    @Column(name = "number_of_passengers")
    private int numberOfPassengers;

    @Column(name = "total_price")
    private float totalPrice;

    @Column(name = "estimated_duration")
    private Duration estimatedDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "ride_status", nullable = false)
    private RideStatus rideStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "route_optimization_criteria")
    private RouteOptimizationCriteria routeOptimizationCriteria;

    @Column(name = "panic_flag")
    private Boolean panicFlag;

    @Column(name = "pet_transport_flag")
    private Boolean petTransportFlag;

    @Column(name = "baby_transport_flag")
    private Boolean babyTransportFlag;


    @ManyToOne
    @JoinColumn(name = "driver")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "passenger")
    private User passenger;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Route> routes;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rejection")
    private Rejection rejection;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "favourite_flag")
    private Boolean favourite;





    public Ride() {
    }

    public Ride(LocalDateTime startTime, LocalDateTime finishTime, int numberOfPassengers, float totalPrice, Duration estimatedDuration, RideStatus rideStatus, RouteOptimizationCriteria routeOptimizationCriteria, Boolean panicFlag, Boolean petTransportFlag, Boolean babyTransportFlag, Driver driver, User passenger, List<Route> routes, Rejection rejection, VehicleType vehicleType, Boolean favourite) {
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
        this.passenger = passenger;
        this.routes = routes;
        this.rejection = rejection;
        this.vehicleType = vehicleType;
        this.favourite = favourite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }

    public RouteOptimizationCriteria getRouteOptimizationCriteria() {
        return routeOptimizationCriteria;
    }

    public void setRouteOptimizationCriteria(RouteOptimizationCriteria routeOptimizationCriteria) {
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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }


    public Rejection getRejection() {
        return rejection;
    }

    public void setRejection(Rejection rejection) {
        this.rejection = rejection;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }
}
