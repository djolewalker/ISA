package com.ftnisa.isa.dto.ride;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RecreateRideDto {
    private Integer rideId;
    private Boolean scheduled;
    private LocalDateTime scheduledStartTime;
}
