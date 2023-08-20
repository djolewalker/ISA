package com.ftnisa.isa.dto.route;


import com.ftnisa.isa.dto.location.LocationDto;


public class RouteDto {

    private LocationDto startLocation;

    private LocationDto finishLocation;

    public RouteDto() {
    }

    public RouteDto(LocationDto startLocation, LocationDto finishLocation) {
        this.startLocation = startLocation;
        this.finishLocation = finishLocation;
    }

    public LocationDto getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LocationDto startLocation) {
        this.startLocation = startLocation;
    }

    public LocationDto getFinishLocation() {
        return finishLocation;
    }

    public void setFinishLocation(LocationDto finishLocation) {
        this.finishLocation = finishLocation;
    }
}
