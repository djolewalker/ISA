import { MapContainer, TileLayer } from 'react-leaflet';
import { LatLngTuple } from 'leaflet';

import { MainMapContent } from 'app/components/main-map-content/MainMapContent';

import './MainMap.scss';

const DEFAULT_MAP_POSITION: LatLngTuple = [45.2396, 19.8227];
const DEFAULT_MAP_ZOOM = 13;

export const MainMap = () => {
  return (
    <MapContainer className="main-map" center={DEFAULT_MAP_POSITION} zoom={DEFAULT_MAP_ZOOM}>
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />
      <MainMapContent />
    </MapContainer>
  );
};
