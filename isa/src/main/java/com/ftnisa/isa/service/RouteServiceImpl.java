package com.ftnisa.isa.service;

import com.ftnisa.isa.integrations.ors.responses.routing.geojson.GeoJSONRouteResponse;
import com.ftnisa.isa.integrations.ors.service.DirectionService;
import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.route.IntermediateStop;
import com.ftnisa.isa.model.route.Route;
import com.ftnisa.isa.repository.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    private final DirectionService directionService;

    public RouteServiceImpl(RouteRepository routeRepository, DirectionService directionService) {
        this.routeRepository = routeRepository;
        this.directionService = directionService;
    }

    @Override
    public List<Route> generateAndOrganizeRoutes(Location startLocation, Location finishLocation, List<Location> stops,
                                                 boolean optimizeOrder) {
        if (optimizeOrder) {
            List<List<Location>> stopPermutations = generateStopPermutations(stops);
            List<Location> optimalStopPermutation = stopPermutations.stream()
                    .min(Comparator.comparing(
                            s -> calculateTotalDistanceForRouteList(generateRoutes(startLocation, finishLocation, s))))
                    .orElse(new ArrayList<Location>());

            return generateRoutes(startLocation, finishLocation, optimalStopPermutation);
        }
        return generateRoutes(startLocation, finishLocation, stops);
    }

    @Override
    public ArrayList<Route> generateRoutes(Location startLocation, Location finishLocation, List<Location> stops) {
        ArrayList<Route> routes = new ArrayList<>();
        int numberOfStops = stops.size();
        Route firstRoute = new Route();
        firstRoute.setStartLocation(startLocation);

        if (numberOfStops == 0) {

            firstRoute.setFinishLocation(finishLocation);
            routes.add(firstRoute);
        } else {
            firstRoute.setFinishLocation(stops.get(0));
            routes.add(firstRoute);
            for (int i = 0; i < numberOfStops - 1; i++) {
                Route nextRoute = new Route();
                nextRoute.setStartLocation(stops.get(i));
                nextRoute.setFinishLocation(stops.get(i + 1));
                routes.add(nextRoute);
            }
            Route lastRoute = new Route();
            lastRoute.setStartLocation(stops.get(numberOfStops - 1));
            lastRoute.setFinishLocation(finishLocation);
            routes.add(lastRoute);
        }
        return routes;
    }

    @Override
    public List<List<Location>> generateStopPermutations(List<Location> stops) {
        if (stops.isEmpty()) {
            List<List<Location>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }
        Location firstElement = stops.remove(0);
        List<List<Location>> returnValue = new ArrayList<>();
        List<List<Location>> permutations = generateStopPermutations(stops);
        for (List<Location> smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                List<Location> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }


    @Override
    public long fetchRouteLengthMeters(Route route) {
        return (long) (route.getLength() * 1000);
    }


    @Override
    public float fetchRouteDurationMinutes(Route route) {
        return (float) route.getEstimatedDuration().toMinutes() / 60;
    }


    @Override
    public Double[][] convertLocationsToCoordinateArray(Location location1, Location location2) {
        Double[][] coordinates = new Double[2][2];
        coordinates[0][0] = (double) location1.getLongitude();
        coordinates[0][1] = (double) location1.getLatitude();
        coordinates[1][0] = (double) location2.getLongitude();
        coordinates[1][1] = (double) location2.getLatitude();
        return coordinates;
    }

    @Override
    public float fetchDistanceInMetersBetweenLocations(Location location1, Location location2) throws Exception {
        var coordinates = convertLocationsToCoordinateArray(location1, location2);
        List<Long> distances = new ArrayList<>();

        var routeResponse = directionService.findRoutes(coordinates).block();
        routeResponse.getRoutes().stream().forEach(geoJSONIndividualRouteResponse -> {
            distances.add(geoJSONIndividualRouteResponse.getProperties().getSummary().getDistance().longValue() * 1000);
        });

        return (float) Collections.min(distances);
    }


    @Override
    public long fetchTimeInMinutesBetweenLocations(Location location1, Location location2) throws Exception {
        var coordinates = convertLocationsToCoordinateArray(location1, location2);
        List<Long> durations = new ArrayList<>();

        var routeResponse = directionService.findRoutes(coordinates).block();
        routeResponse.getRoutes().stream().forEach(geoJSONIndividualRouteResponse -> {
            durations.add(geoJSONIndividualRouteResponse.getProperties().getSummary().getDuration().longValue() / 60);
        });

        return Collections.min(durations);
    }

    @Override
    public long calculateTotalDistanceForRouteList(List<Route> routes) {
        var totalDistance = 0L;
        for (Route route : routes) {
            totalDistance = totalDistance + fetchRouteLengthMeters(route);
        }
        return totalDistance;
    }

    @Override
    public Location getRidesFinishLocation(Ride ride) {
        List<Route> routes = ride.getRoutes();
        return routes.get(routes.size() - 1).getFinishLocation();
    }

    @Override
    public Location getRidesStartLocation(Ride ride) {
        return ride.getRoutes().get(0).getStartLocation();
    }

    @Override
    @Transactional
    public int cloneRoutes(Route oldRoute) {
        Route newRoute = new Route();

        //stops + stops.route + locations
        List<IntermediateStop> stops = new ArrayList<>();
        for (IntermediateStop s : oldRoute.getStops()) {
            var intermediateStop = new IntermediateStop();
            intermediateStop.setRoute(newRoute);
            var location = new Location();
            location.setName(s.getLocation().getName());
            location.setLatitude(s.getLocation().getLatitude());
            location.setLongitude(s.getLocation().getLongitude());
            intermediateStop.setLocation(location);
            stops.add(intermediateStop);
        }
        newRoute.setStops(stops);

        newRoute.setStartLocation(oldRoute.getStartLocation());
        newRoute.setFinishLocation(oldRoute.getFinishLocation());
        newRoute.setEstimatedDuration(oldRoute.getEstimatedDuration());
        newRoute.setLength(oldRoute.getLength());
        newRoute.setGeo(oldRoute.getGeo());

        routeRepository.save(newRoute);

        return newRoute.getId();
    }

    @Override
    @Transactional
    public GeoJSONRouteResponse searchRoute(Double[][] coordinates) throws Exception {
        var routeResponse = directionService.findRoutes(coordinates).block();
        routeResponse.getRoutes().stream().forEach(geoJSONIndividualRouteResponse -> {
            var route = new Route();
            route.setStartLocation(new Location(coordinates[0][0].floatValue(), coordinates[0][1].floatValue(),
                    geoJSONIndividualRouteResponse.startLocationName()));
            route.setFinishLocation(new Location(coordinates[coordinates.length - 1][0].floatValue(),
                    coordinates[coordinates.length - 1][1].floatValue(),
                    geoJSONIndividualRouteResponse.destinationLocationName()));

            if (coordinates.length > 2) {
                var stops = new ArrayList<IntermediateStop>(coordinates.length - 2);
                for (var i = 1; i < coordinates.length - 1; i++) {
                    var location = new Location();
                    location.setLongitude(coordinates[i][0].floatValue());
                    location.setLatitude(coordinates[i][1].floatValue());
                    location.setName(geoJSONIndividualRouteResponse.stopLocationName(i));

                    var intermediateStop = new IntermediateStop();
                    intermediateStop.setRoute(route);
                    intermediateStop.setLocation(location);
                    intermediateStop.setOrder(i);
                    stops.add(intermediateStop);
                }
                route.setStops(stops);
            }

            route.setEstimatedDuration(
                    Duration.of(geoJSONIndividualRouteResponse.getProperties().getSummary().getDuration().longValue(),
                            ChronoUnit.MINUTES));
            route.setLength(geoJSONIndividualRouteResponse.getProperties().getSummary().getDistance().floatValue());

            geoJSONIndividualRouteResponse.clearSegments();
            route.setGeo(geoJSONIndividualRouteResponse);

            routeRepository.save(route);

            geoJSONIndividualRouteResponse.setId(route.getId());
        });
        return routeResponse;
    }

    @Override
    public int cleanOrphanRoutes() {
        var timeInPastToSaveUntil = Instant.now().minus(10, ChronoUnit.MINUTES);
        var routes = routeRepository.findAllWithCreationDateTimeBefore(timeInPastToSaveUntil);
        var orphanRoutes = routes.stream().filter(route -> route.getRide() == null).toList();
        routeRepository.deleteAll(orphanRoutes);
        return orphanRoutes.size();
    }
}
