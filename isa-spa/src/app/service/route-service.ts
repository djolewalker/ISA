import axios, { AxiosResponse } from 'axios';

import { Coordinates } from 'app/model/Location';
import { FeatureCollection } from 'geojson';

const client = axios.create({
  baseURL: 'https://api.openrouteservice.org/v2/directions',
  headers: {
    Authorization: process.env.REACT_APP_ORS_TOKEN
  }
});

const defaultParams = {
  alternative_routes: { target_count: 3 },
  units: 'km',
  instructions_format: 'html'
};

export const searchRoutes = async (coordinates: Coordinates) => {
  return client
    .post('driving-car/geojson', { ...defaultParams, coordinates })
    .then((response: AxiosResponse<FeatureCollection>) => response.data);
};
