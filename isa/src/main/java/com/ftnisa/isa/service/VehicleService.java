package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.vehicle.VehicleRequest;
import com.ftnisa.isa.model.vehicle.VehicleType;

import java.util.List;

public interface VehicleService {
    void createVehicle(VehicleRequest vehicle);

    List<VehicleType> getVehicleTypes();
}
