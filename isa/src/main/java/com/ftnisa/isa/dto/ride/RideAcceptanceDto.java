package com.ftnisa.isa.dto.ride;

public class RideAcceptanceDto {

    private boolean isRideAccepted;

    private int rideId;

    public RideAcceptanceDto() {
    }

    public RideAcceptanceDto(boolean isRideAccepted, int rideId) {
        this.isRideAccepted = isRideAccepted;
        this.rideId = rideId;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public boolean isRideAccepted() {
        return isRideAccepted;
    }

    public void setRideAccepted(boolean rideAccepted) {
        isRideAccepted = rideAccepted;
    }
}
