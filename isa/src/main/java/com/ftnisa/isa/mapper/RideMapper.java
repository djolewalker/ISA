package com.ftnisa.isa.mapper;

import com.ftnisa.isa.dto.ride.RideDto;
import com.ftnisa.isa.dto.route.RouteDto;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.route.Route;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RideMapper {



    @Mapping(target = "routes", source = "routes")
    RideDto rideToRideDto(Ride ride);

    List<RouteDto> routesToRoutesDto(List<Route> routes);
}
