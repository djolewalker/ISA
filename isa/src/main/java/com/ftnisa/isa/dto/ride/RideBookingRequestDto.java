package com.ftnisa.isa.dto.ride;

import com.ftnisa.isa.model.ride.RouteOptimizationCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideBookingRequestDto {
    private Boolean petTransportFlag = false;
    private Boolean babyTransportFlag = false;
    private int vehicleTypeId;
    private int numberOfPassengers;
    private boolean isScheduled = false;
    private LocalDateTime scheduledStartTime;
    private RouteOptimizationCriteria routeOptimizationCriteria;
    private int routeId;
}
