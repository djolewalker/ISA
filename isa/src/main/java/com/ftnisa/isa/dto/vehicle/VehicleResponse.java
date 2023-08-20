package com.ftnisa.isa.dto.vehicle;

public class VehicleResponse extends VehicleDto {
    private int id;
    private VehicleTypeResponse vehicleType;

    public VehicleResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VehicleTypeResponse getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleTypeResponse vehicleType) {
        this.vehicleType = vehicleType;
    }
}
