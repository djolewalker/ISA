package com.ftnisa.isa.dto.ride;

import com.ftnisa.isa.dto.route.RouteDto;
import com.ftnisa.isa.dto.user.DriverInfoDto;
import com.ftnisa.isa.dto.vehicle.VehicleTypeResponse;
import com.ftnisa.isa.model.ride.RideStatus;
import com.ftnisa.isa.model.ride.RouteOptimizationCriteria;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Data
@NoArgsConstructor
public class RideDto {
    private int id;
    private String startTime;
    private String finishTime;
    private int numberOfPassengers;
    private float totalPrice;
    private Duration estimatedDuration;
    private RideStatus rideStatus;
    private RouteOptimizationCriteria routeOptimizationCriteria;
    private Boolean panicFlag;
    private Boolean petTransportFlag;
    private Boolean babyTransportFlag;
    private DriverInfoDto driver;
    private List<RouteDto> routes;
    private RejectionDto rejection;
    private VehicleTypeResponse vehicleType;
    private Boolean scheduled;
    private Boolean favourite;
}
