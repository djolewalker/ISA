package com.ftnisa.isa.dto.ride;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RideRejectionRequestDto {
    private Integer rideId;
    private String rejectionReason;
}
