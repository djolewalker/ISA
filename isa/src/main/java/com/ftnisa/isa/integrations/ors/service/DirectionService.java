package com.ftnisa.isa.integrations.ors.service;

import com.ftnisa.isa.integrations.ors.responses.routing.geojson.GeoJSONRouteResponse;
import reactor.core.publisher.Mono;

public interface DirectionService {
    Mono<GeoJSONRouteResponse> findRoutes(Double[][] coordinates) throws Exception;
}
