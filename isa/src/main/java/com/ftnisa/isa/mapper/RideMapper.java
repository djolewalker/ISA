package com.ftnisa.isa.mapper;


import com.ftnisa.isa.dto.ride.RideBookingRequestDto;
import com.ftnisa.isa.dto.ride.RideBookingResponseDto;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.user.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RideMapper {



    Ride rideBookingRequestDtoToRide(RideBookingRequestDto rideBookingRequestDto);

    @Mapping(target = "rideStatus", source = "rideStatus")
    @Mapping(target = "rejection", source = "rejection")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "rideId", source = "id")
    RideBookingResponseDto rideToRideBookingResponseDto(Ride ride);





}
