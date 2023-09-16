package com.ftnisa.isa.mapper;

import com.ftnisa.isa.dto.location.LocationDto;
import com.ftnisa.isa.model.location.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDto locationToLocationDto(Location location);

    Location locationDtoToLocation(LocationDto locationDto);
}
