package com.ftnisa.isa.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PanicResponse {

    private Integer id;
    private OffsetDateTime panicTime;
    private boolean isResolved;
    private String panicReason;

    public LocalDateTime getPanicTime() {
        return panicTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void setPanicTime(LocalDateTime panicTime) {
        this.panicTime = panicTime.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }
}
