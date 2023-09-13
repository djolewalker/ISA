package com.ftnisa.isa.dto.ride;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RejectionDto {
    private String rejectionReason;
    private String rejectionTime;
}
