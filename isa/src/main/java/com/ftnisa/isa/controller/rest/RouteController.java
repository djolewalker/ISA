package com.ftnisa.isa.controller.rest;

import com.ftnisa.isa.dto.route.FindRouteDto;
import com.ftnisa.isa.integrations.ors.responses.routing.geojson.GeoJSONRouteResponse;
import com.ftnisa.isa.integrations.ors.service.DirectionService;
import com.ftnisa.isa.service.RouteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/route", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "isasec")
public class RouteController {
    private RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping()
    public ResponseEntity<GeoJSONRouteResponse> findRoutes(@RequestBody @Valid FindRouteDto findRoute) throws Exception {
        var routes = routeService.searchRoute(findRoute.getStops());
        return ResponseEntity.ok(routes);
    }
}
