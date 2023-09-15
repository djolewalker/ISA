package com.ftnisa.isa.dto.ride;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RideHistoryByDateRequestDto {
    private Integer userId;
    private LocalDateTime date1;
    private LocalDateTime date2;
}
