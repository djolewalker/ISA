package com.ftnisa.isa.mapper;

import com.ftnisa.isa.dto.ride.RideDto;
import com.ftnisa.isa.dto.route.IntermediateStopDto;
import com.ftnisa.isa.dto.route.RouteDto;
import com.ftnisa.isa.dto.user.PanicResponse;
import com.ftnisa.isa.model.ride.Panic;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.route.IntermediateStop;
import com.ftnisa.isa.model.route.Route;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RideMapper {
    RideDto rideToRideDto(Ride ride);

    List<RideDto> ridesToRidesDto(List<Ride> rides);

    RouteDto routeToRouteDto(Route route);

    List<RouteDto> routesToRoutesDto(List<Route> routes);

    PanicResponse panicToPanicResponse(Panic panic);

    IntermediateStopDto intermediateStopToIntermediateStopDto(IntermediateStop intermediateStop);

    List<IntermediateStopDto> intermediateStopsToIntermediateStopsDto(List<IntermediateStop> intermediateStops);

}
