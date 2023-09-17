package com.ftnisa.isa.dto.ride;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
public class RecreateRideDto {
    private Boolean scheduled;
    private OffsetDateTime scheduledStartTime;

    public LocalDateTime getScheduledStartTime() {
        return scheduledStartTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
}
