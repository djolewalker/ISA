import { OSMLocation } from 'app/service/locations.service';
import { ISALocation } from './Location';

export type RouteLocations = {
  start: OSMLocation;
  destination: OSMLocation;
  [key: string]: OSMLocation | null;
};

export type RoutePriorityType = 'BY_LENGTH' | 'BY_TIME' | 'BY_PRICE';

export const RoutePriorityTypeMeasuresMap = {
  BY_LENGTH: 'distance',
  BY_PRICE: 'distance',
  BY_TIME: 'duration'
};

export type Route = {
  id: number;
  startLocation: ISALocation;
  finishLocation: ISALocation;
};
