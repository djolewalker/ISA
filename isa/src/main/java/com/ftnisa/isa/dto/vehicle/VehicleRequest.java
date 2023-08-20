package com.ftnisa.isa.dto.vehicle;

public class VehicleRequest extends VehicleDto {
    private int vehicleTypeId;

    public VehicleRequest() {
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
}
