package com.ftnisa.isa.mapper;

import com.ftnisa.isa.dto.user.*;
import com.ftnisa.isa.model.user.Driver;
import com.ftnisa.isa.model.user.DriverChangeRequest;
import com.ftnisa.isa.model.user.Role;
import com.ftnisa.isa.model.user.User;
import com.ftnisa.isa.model.vehicle.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToString")
    UserResponse toUserResponse(User user);

    @Named("rolesToString")
    default List<String> rolesToString(List<Role> roles) {
        return roles.stream().map(Role::getName).toList();
    }

    DriverResponse driverToDriverResponse(Driver driver);

    DriverInfoDto driverToDriverInfoDto(Driver driver);



    DriverChangeRequest driverChangeRequestDtoToDriverChangeRequest(DriverChangeRequestDto driverChangeRequestDto);


    @Mapping(target = "longitude", source = "vehicle", qualifiedByName = "longitude")
    @Mapping(target = "latitude", source = "vehicle", qualifiedByName = "latitude")
    DriverLocationDto driverToDriverLocationDto(Driver driver);

    List<DriverLocationDto> driversToDriversLocationDto(List<Driver> driver);


    @Named("longitude")
    default float driverToLongitude(Vehicle vehicle) {
        return vehicle.getCurrentLocation().getLongitude();
    }

    @Named("latitude")
    default float driverToLatitude(Vehicle vehicle) {
        return vehicle.getCurrentLocation().getLatitude();
    }

    DriverStatusDTO driverToDriverStatusDto(Driver driver);
}
