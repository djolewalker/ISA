import { buildUrl } from 'app/utils/query-string.utils';
import axios from 'axios';

const client = axios.create({
  baseURL: 'https://nominatim.openstreetmap.org'
});

const defaultParams = {
  format: 'json',
  countrycodes: 'rs',
  limit: 10
};

export type OSMLocation = {
  place_id: number;
  display_name: string;
  lat: string;
  lon: string;
  importance: number;
};

export const searchLocations = async (query: string) => {
  const url = buildUrl('search', { q: query, ...defaultParams });
  return client.get(url).then((response) => response.data as OSMLocation[]);
};
