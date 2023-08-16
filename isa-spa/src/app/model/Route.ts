import { OSMLocation } from 'app/service/locations-service';

export type RouteLocations = {
  start: OSMLocation;
  destination: OSMLocation;
  [key: number]: OSMLocation;
};

export type RoutePriorityType = 'distance' | 'duration';
