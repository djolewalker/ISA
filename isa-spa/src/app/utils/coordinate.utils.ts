import { RouteLocations } from 'app/model/Route';

export const buildCoordinates = (routeLocations: RouteLocations) => {
  const numberOfCoordinates = Object.keys(routeLocations).length;
  const { start, destination, ...stops } = routeLocations;

  const routeCoordinates = new Array(numberOfCoordinates);
  routeCoordinates[0] = [parseFloat(start.lon), parseFloat(start.lat)];
  routeCoordinates[numberOfCoordinates - 1] = [parseFloat(destination.lon), parseFloat(destination.lat)];

  if (stops) {
    Object.entries(stops).forEach(([_, location], index) => {
      if (location) {
        routeCoordinates[index + 1] = [parseFloat(location.lon), parseFloat(location.lat)];
      }
    });
  }

  return routeCoordinates;
};
