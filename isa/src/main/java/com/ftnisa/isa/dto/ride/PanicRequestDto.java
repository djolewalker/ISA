package com.ftnisa.isa.dto.ride;

import javax.persistence.criteria.CriteriaBuilder;

public class PanicRequestDto {

    private String panicReason;

    private Integer rideId;

    private Integer userId;

    public PanicRequestDto() {
    }

    public PanicRequestDto(String panicReason, Integer rideId, Integer userId) {
        this.panicReason = panicReason;
        this.rideId = rideId;
        this.userId = userId;
    }

    public String getPanicReason() {
        return panicReason;
    }

    public void setPanicReason(String panicReason) {
        this.panicReason = panicReason;
    }

    public Integer getRideId() {
        return rideId;
    }

    public void setRideId(Integer rideId) {
        this.rideId = rideId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
