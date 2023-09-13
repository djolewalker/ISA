package com.ftnisa.isa.mapper;

import com.ftnisa.isa.dto.user.UserResponse;
import com.ftnisa.isa.dto.vehicle.VehicleRequest;
import com.ftnisa.isa.dto.vehicle.VehicleResponse;
import com.ftnisa.isa.dto.vehicle.VehicleTypeResponse;
import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.model.vehicle.Vehicle;
import com.ftnisa.isa.model.vehicle.VehicleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle vehicleResponseToVehicle(VehicleRequest vehicleRequest);

    VehicleResponse vehicleToVehicleResponse(Vehicle vehicle);

    VehicleTypeResponse vehicleTypeToVehicleTypeResponse(VehicleType vehicleType);

}
