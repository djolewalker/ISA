package com.ftnisa.isa.model.route;


import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.ride.Ride;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "isa_route")
public class Route {

    @Id
    @SequenceGenerator(name = "routeSeqGen", sequenceName = "routeSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routeSeqGen")
    private Integer id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "finish_time")
    private LocalDateTime finishTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "start_location")
    @NotNull
    private Location startLocation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "finish_location")
    @NotNull
    private Location finishLocation;

    @Column(name = "length")
    private float length;

    @Column(name = "estimated_duration")
    private Duration estimatedDuration;

    @Column(name = "route_price")
    private float routePrice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ride_id")
    private Ride ride;

    public Route() {
    }

    public Route(LocalDateTime startTime, LocalDateTime finishTime, Location startLocation, Location finishLocation, float length, Duration estimatedDuration, float routePrice, Ride ride) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.startLocation = startLocation;
        this.finishLocation = finishLocation;
        this.length = length;
        this.estimatedDuration = estimatedDuration;
        this.routePrice = routePrice;
        this.ride = ride;
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

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getFinishLocation() {
        return finishLocation;
    }

    public void setFinishLocation(Location finishLocation) {
        this.finishLocation = finishLocation;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public Duration getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Duration estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public float getRoutePrice() {
        return routePrice;
    }

    public void setRoutePrice(float routePrice) {
        this.routePrice = routePrice;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}
