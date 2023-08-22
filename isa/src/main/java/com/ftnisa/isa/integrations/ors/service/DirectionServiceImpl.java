package com.ftnisa.isa.integrations.ors.service;

import com.ftnisa.isa.exception.ORSException;
import com.ftnisa.isa.integrations.ors.APIEnums;
import com.ftnisa.isa.integrations.ors.configuration.OSMConfigration;
import com.ftnisa.isa.integrations.ors.requests.routing.RouteRequest;
import com.ftnisa.isa.integrations.ors.requests.routing.RouteRequestAlternativeRoutes;
import com.ftnisa.isa.integrations.ors.responses.routing.geojson.GeoJSONRouteResponse;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DirectionServiceImpl implements DirectionService {
    private final OSMConfigration osmOmsOSMConfiguration;

    public DirectionServiceImpl(OSMConfigration osmOmsOSMConfiguration) {
        this.osmOmsOSMConfiguration = osmOmsOSMConfiguration;
    }

    @Override
    public Mono<GeoJSONRouteResponse> findRoutes(Double[][] coordinates) throws Exception {
        WebClient client = WebClient.builder()
                .baseUrl(osmOmsOSMConfiguration.getOsmUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, osmOmsOSMConfiguration.getOsmToken())
                .build();

        var alternativeRouteSettings = new RouteRequestAlternativeRoutes();
        alternativeRouteSettings.setTargetCount(3);
        alternativeRouteSettings.setShareFactor(0.5);

        var routeRequest = new RouteRequest(coordinates);
        routeRequest.setInstructionsFormat(APIEnums.InstructionsFormat.HTML);
        routeRequest.setUnits(APIEnums.Units.KILOMETRES);
        routeRequest.setAlternativeRoutes(alternativeRouteSettings);
        routeRequest.setMaximumSearchRadii(new Double[]{(double) -1});

        var spec = client
                .post()
                .uri("/driving-car/geojson")
                .body(Mono.just(routeRequest), RouteRequest.class);

        return spec.exchangeToMono(clientResponse -> {
            if (clientResponse.statusCode().is2xxSuccessful()) {
                return clientResponse.bodyToMono(GeoJSONRouteResponse.class);
            } else {
                var errorMessage = clientResponse.bodyToMono(JSONObject.class);
                return errorMessage.flatMap(error -> Mono.error(new ORSException(error)));
            }
        });
    }
}
