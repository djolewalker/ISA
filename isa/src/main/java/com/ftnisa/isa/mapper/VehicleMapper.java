package com.ftnisa.isa.mapper;

import com.ftnisa.isa.dto.vehicle.VehicleTypeResponse;
import com.ftnisa.isa.model.vehicle.VehicleType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleTypeResponse vehicleTypeToVehicleTypeResponse(VehicleType vehicleType);
}
