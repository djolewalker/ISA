package com.ftnisa.isa.dto.route;

import com.ftnisa.isa.dto.location.LocationDto;
import com.ftnisa.isa.integrations.ors.responses.routing.geojson.GeoJSONIndividualRouteResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RouteDto {
    private int id;
    private LocationDto startLocation;
    private LocationDto finishLocation;
    private List<IntermediateStopDto> stops;
    private GeoJSONIndividualRouteResponse geo;
}
