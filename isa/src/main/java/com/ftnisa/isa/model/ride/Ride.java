package com.ftnisa.isa.model.ride;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftnisa.isa.model.route.Route;
import com.ftnisa.isa.model.user.Driver;

import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.model.vehicle.VehicleType;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "isa_ride")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Route> routes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rejection", referencedColumnName = "id")
    private Rejection rejection;

    @ManyToOne
    @JoinColumn(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "favourite_flag")
    private Boolean favourite;


}
