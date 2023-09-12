package com.ftnisa.isa.mapper;

import com.ftnisa.isa.dto.location.LocationDto;
import com.ftnisa.isa.dto.ride.RideBookingRequestDto;
import com.ftnisa.isa.dto.ride.RideBookingResponseDto;
import com.ftnisa.isa.dto.ride.RideDto;
import com.ftnisa.isa.dto.route.RouteDto;
import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.route.Route;
import com.ftnisa.isa.model.user.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RideMapper {

    Ride rideBookingRequestDtoToRide(RideBookingRequestDto rideBookingRequestDto);

    @Mapping(target = "rejection", source = "rejection")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "rideId", source = "id")
    RideBookingResponseDto rideToRideBookingResponseDto(Ride ride);

    @Mapping(target = "routes", source = "routes")
    RideDto rideToRideDto(Ride ride);

    default List<String> rolesToString(List<Role> roles) {
        return roles.stream().map(Role::getName).toList();
    }
}
