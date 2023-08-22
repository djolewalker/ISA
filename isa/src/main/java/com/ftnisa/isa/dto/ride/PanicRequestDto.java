package com.ftnisa.isa.dto.ride;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PanicRequestDto {
    private String panicReason;
    private Integer rideId;
    private Integer userId;
}
