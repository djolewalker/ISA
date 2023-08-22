import { FeatureCollection } from 'geojson';

import { Coordinates } from 'app/model/Location';
import { postRequest } from 'app/service/base.service';

const CONTROLLER = 'route';

export const searchRoutes = async (coordinates: Coordinates) => {
  return postRequest(CONTROLLER, { stops: coordinates }).then((response) => response.data as FeatureCollection);
};
