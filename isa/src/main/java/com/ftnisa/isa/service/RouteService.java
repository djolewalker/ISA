package com.ftnisa.isa.service;

import com.ftnisa.isa.dto.route.FindRouteDto;
import com.ftnisa.isa.integrations.ors.responses.routing.RouteResponse;
import com.ftnisa.isa.integrations.ors.responses.routing.geojson.GeoJSONRouteResponse;
import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.route.Route;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public interface RouteService {
    ArrayList<Route> generateRoutes(Location startLocation, Location finishLocation, List<Location> stops);

    List<Route> generateAndOrganizeRoutes(Location startLocation, Location finishLocation, List<Location> stops, boolean optimizeOrder);

    List<List<Location>> generateStopPermutations(List<Location> stops);

    // DUMMY returns 2500 meters
    long fetchRouteLengthMeters(Route route);

    // calculates the estimated route duration based on the length, and an average speed of 50km/h
    Duration estimateRouteDuration(long routeLength);

    float fetchRouteDurationMinutes(Route route);

    // DUMMY
    float fetchDistanceInMetersBetweenLocations(Location location1, Location location2);

    // DUMMY
    long fetchTimeInMinutesBetweenLocations(Location location1, Location location2);

    long calculateTotalDistanceForRouteList(List<Route> routes);

    Location getRidesFinishLocation(Ride ride);

    Location getRidesStartLocation(Ride ride);

    List<Route> cloneRoutes(List<Route> oldRoutes);

    GeoJSONRouteResponse searchRoute(Double[][] coordinates) throws Exception;

    int cleanOrphanRoutes();
}
