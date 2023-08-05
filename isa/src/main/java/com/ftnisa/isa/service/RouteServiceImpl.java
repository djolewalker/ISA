package com.ftnisa.isa.service;

import com.ftnisa.isa.model.location.Location;
import com.ftnisa.isa.model.ride.Ride;
import com.ftnisa.isa.model.route.Route;
import com.ftnisa.isa.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }


    @Override
    public List<Route> generateAndOrganizeRoutes(Location startLocation, Location finishLocation, List<Location> stops, boolean optimizeOrder){
        if (optimizeOrder){
            List<List<Location>> stopPermutations = generateStopPermutations(stops);
            List<Location> optimalStopPermutation = stopPermutations.stream().min(Comparator.comparing(s -> calculateTotalDistanceForRouteList(generateRoutes(startLocation, finishLocation,s)))).orElse(null);

            return generateRoutes(startLocation, finishLocation, optimalStopPermutation);
        }
        return generateRoutes(startLocation, finishLocation, stops);
    }


    @Override
    public ArrayList<Route> generateRoutes(Location startLocation, Location finishLocation, List<Location> stops){
        ArrayList<Route> routes = new ArrayList<>();
        int numberOfStops = stops.size();
        Route firstRoute = new Route();
        firstRoute.setStartLocation(startLocation);

        if (numberOfStops == 0){

            firstRoute.setFinishLocation(finishLocation);
            routes.add(firstRoute);
        } else {
            firstRoute.setFinishLocation(stops.get(0));
            routes.add(firstRoute);
            for (int i = 0; i < numberOfStops-1; i++){
                Route nextRoute = new Route();
                nextRoute.setStartLocation(stops.get(i));
                nextRoute.setFinishLocation(stops.get(i+1));
                routes.add(nextRoute);
            }
            Route lastRoute = new Route();
            lastRoute.setStartLocation(stops.get(numberOfStops-1));
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




    // DUMMMY returns 2500 meters
    @Override
    public long fetchRouteLengthMeters(Route route){
        return 2500;
    }

    // DUMMMY
    @Override
    public float fetchRouteDurationMinutes(Route route){
        return 15;
    }



    // DUMMMY calculates the estimated route duration based on the length, and an average speed of 50km/h
    @Override
    public Duration estimateRouteDuration(long routeLength){
        return Duration.of((long)(routeLength / 13.889), ChronoUnit.SECONDS);
    }

    // DUMMMY
    @Override
    public float fetchDistanceInMetersBetweenLocations(Location location1, Location location2){
        return 1000;
    }

    // DUMMMY
    @Override
    public long fetchTimeInMinutesBetweenLocations(Location location1, Location location2){
        return 8;
    }


    @Override
    public long calculateTotalDistanceForRouteList(List<Route> routes){
        Long totalDistance = 0l;
        for (Route route : routes){
            totalDistance = totalDistance + fetchRouteLengthMeters(route);
        }
        return totalDistance;
    }


    @Override
    public void saveRoutesForRide(Ride ride){
        List<Route> routes = ride.getRoutes();
        routeRepository.saveAll(routes);
    }


    @Override
    public Location getRidesFinishLocation(Ride ride){
        List<Route> routes = ride.getRoutes();
        return routes.get(routes.size()-1).getFinishLocation();
    }

    @Override
    public Location getRidesStartLocation(Ride ride){
        return ride.getRoutes().get(0).getStartLocation();
    }




}
