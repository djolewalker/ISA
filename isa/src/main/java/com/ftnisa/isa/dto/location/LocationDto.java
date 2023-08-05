package com.ftnisa.isa.dto.location;

public class LocationDto {

    private float longitude;

    private float latitude;

    public LocationDto() {
    }

    public LocationDto(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
