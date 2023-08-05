package com.ftnisa.isa.dto.ride;

public class RideRejectionRequestDto {

    private Integer rideId;

    private String rejectionReason;

    public RideRejectionRequestDto() {
    }

    public RideRejectionRequestDto(Integer rideId, String rejectionReason) {
        this.rideId = rideId;
        this.rejectionReason = rejectionReason;
    }

    public Integer getRideId() {
        return rideId;
    }

    public void setRideId(Integer rideId) {
        this.rideId = rideId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
