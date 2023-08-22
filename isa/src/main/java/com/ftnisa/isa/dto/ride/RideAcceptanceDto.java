package com.ftnisa.isa.dto.ride;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RideAcceptanceDto {
    private boolean isRideAccepted;
    private int rideId;
}
