package com.ftnisa.isa.dto.vehicle;

import com.ftnisa.isa.model.vehicle.VehicleTypeName;

public class VehicleTypeDto {

    private VehicleTypeNameDto vehicleTypeName;

    private Float pricePerKm;

    public VehicleTypeDto() {
    }

    public VehicleTypeDto(VehicleTypeNameDto vehicleTypeName, Float pricePerKm) {
        this.vehicleTypeName = vehicleTypeName;
        this.pricePerKm = pricePerKm;
    }

    public VehicleTypeNameDto getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(VehicleTypeNameDto vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

    public Float getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(Float pricePerKm) {
        this.pricePerKm = pricePerKm;
    }
}
