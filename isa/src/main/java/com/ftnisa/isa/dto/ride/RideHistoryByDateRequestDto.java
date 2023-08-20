package com.ftnisa.isa.dto.ride;

import java.time.LocalDateTime;
import java.util.Date;

public class RideHistoryByDateRequestDto {

    private Integer userId;

    private LocalDateTime date1;

    private LocalDateTime date2;

    public RideHistoryByDateRequestDto() {
    }

    public RideHistoryByDateRequestDto(Integer userId, LocalDateTime date1, LocalDateTime date2) {
        this.userId = userId;
        this.date1 = date1;
        this.date2 = date2;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate1() {
        return date1;
    }

    public void setDate1(LocalDateTime date1) {
        this.date1 = date1;
    }

    public LocalDateTime getDate2() {
        return date2;
    }

    public void setDate2(LocalDateTime date2) {
        this.date2 = date2;
    }
}
