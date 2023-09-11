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

const exampleCoordinates = [
  [45.245083, 19.823715],
  [45.246143, 19.827682],
  [45.246421, 19.826467],
  [45.246729, 19.826339],
  [45.246883, 19.823897],
  [45.246946, 19.826632],
  [45.246961, 19.825345],
  [45.247099, 19.820211],
  [45.247119, 19.825375],
  [45.247151, 19.826341],
  [45.247201, 19.820983],
  [45.247827, 19.828078],
  [45.248304, 19.828496],
  [45.248438, 19.826234],
  [45.249442, 19.822959],
  [45.249515, 19.830617],
  [45.249861, 19.828781],
  [45.249954, 19.823521],
  [45.250062, 19.830444],
  [45.250128, 19.824453],
  [45.251474, 19.820508],
  [45.251674, 19.824403],
  [45.251817, 19.829534],
  [45.252403, 19.829335],
  [45.252981, 19.822061],
  [45.253105, 19.823987],
  [45.253821, 19.821158],
  [45.254109, 19.822503],
  [45.254305, 19.825271],
  [45.254338, 19.823676]
];

export const getExampleCoordinate = () => {
  const radnom = Math.floor(Math.random() * exampleCoordinates.length);
  return exampleCoordinates[radnom];
};
