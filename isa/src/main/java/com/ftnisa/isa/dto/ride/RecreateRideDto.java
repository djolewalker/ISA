package com.ftnisa.isa.dto.ride;

import java.time.LocalDateTime;

public class RecreateRideDto {

    private Integer rideId;

    private Boolean scheduled;

    private LocalDateTime scheduledStartTime;

    public RecreateRideDto() {
    }

    public RecreateRideDto(Integer rideId, Boolean scheduled, LocalDateTime scheduledStartTime) {
        this.rideId = rideId;
        this.scheduled = scheduled;
        this.scheduledStartTime = scheduledStartTime;
    }

    public Integer getRideId() {
        return rideId;
    }

    public void setRideId(Integer rideId) {
        this.rideId = rideId;
    }

    public Boolean getScheduled() {
        return scheduled;
    }

    public void setScheduled(Boolean scheduled) {
        this.scheduled = scheduled;
    }

    public LocalDateTime getScheduledStartTime() {
        return scheduledStartTime;
    }

    public void setScheduledStartTime(LocalDateTime scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }
}
