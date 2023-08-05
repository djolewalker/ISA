package com.ftnisa.isa.service;

import com.ftnisa.isa.model.vehicle.Vehicle;
import com.ftnisa.isa.repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService{

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void saveNewVehicle(Vehicle vehicle){
        vehicleRepository.save(vehicle);
    }
}
