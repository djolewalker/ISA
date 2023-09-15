package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.vehicle.VehicleRequest;
import com.ftnisa.isa.mapper.LocationMapper;
import com.ftnisa.isa.model.vehicle.Vehicle;
import com.ftnisa.isa.model.vehicle.VehicleType;
import com.ftnisa.isa.repository.VehicleRepository;
import com.ftnisa.isa.repository.VehicleTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleTypeRepository vehicleTypeRepository,
            LocationMapper locationMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    @Override
    public void createVehicle(VehicleRequest vehicleDto) {
        var vehicle = new Vehicle(
                vehicleDto.getVehicleModel(),
                vehicleDto.getRegistrationNumber(),
                vehicleDto.getNumberOfSeats(),
                vehicleDto.isBabyFriendly(),
                vehicleDto.isPetFriendly(),
                vehicleTypeRepository.findById(vehicleDto.getVehicleTypeId()).orElseThrow(),
                null);
        vehicleRepository.save(vehicle);
    }

    @Override
    public List<VehicleType> getVehicleTypes() {
        return vehicleTypeRepository.findAll();
    }

}
