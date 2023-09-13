package com.ftnisa.isa.mapper;

import com.ftnisa.isa.dto.ride.RideDto;
import com.ftnisa.isa.dto.route.RouteDto;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.route.Route;
import org.mapstruct.Mapper;

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

    List<RouteDto> routesToRoutesDto(List<Route> routes);
}
