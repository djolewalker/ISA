package com.ftnisa.isa.dto.ride;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
public class RideHistoryByDateRequestDto {
    private Integer userId;
    private OffsetDateTime date1;
    private OffsetDateTime date2;

    public LocalDateTime getDate1() {
        return date1.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public LocalDateTime getDate2() {
        return date2.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
}
