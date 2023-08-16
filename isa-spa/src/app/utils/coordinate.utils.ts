import { RouteLocations } from 'app/model/Route';

export const buildCoordinates = (routeLocations: RouteLocations) => {
  const numberOfCoordinates = Object.keys(routeLocations).length;
  const { start, destination, ...stops } = routeLocations;

  const routeCoordinates = new Array(numberOfCoordinates);
  routeCoordinates[0] = [parseFloat(start.lon), parseFloat(start.lat)];
  routeCoordinates[numberOfCoordinates - 1] = [parseFloat(destination.lon), parseFloat(destination.lat)];

  if (stops) {
    Object.entries(stops).forEach(([order, { lon, lat }]) => {
      routeCoordinates[parseFloat(order)] = [parseFloat(lon), parseFloat(lat)];
    });
  }

  return routeCoordinates;
};
