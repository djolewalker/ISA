package com.ftnisa.isa.dto.vehicle;

public class VehicleTypeResponse {
    private int id;
    private String vehicleTypeName;
    private Float pricePerKm;

    public VehicleTypeResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

    public Float getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(Float pricePerKm) {
        this.pricePerKm = pricePerKm;
    }
}
