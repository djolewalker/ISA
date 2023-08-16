import { MapContainer, Marker, Popup, TileLayer } from 'react-leaflet';
import { LatLngExpression, icon } from 'leaflet';

import iconUrl from 'leaflet/dist/images/marker-icon.png';
import shadowUrl from 'leaflet/dist/images/marker-shadow.png';

import './MainMap.scss';

const Icon = icon({
  iconUrl,
  shadowUrl
});

export const MainMap = () => {
  const position: LatLngExpression = [45.2396, 19.8227];
  return (
    <MapContainer className="main-map" center={position} zoom={13}>
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />
      <Marker icon={Icon} position={position}>
        <Popup>
          A pretty CSS3 popup. <br /> Easily customizable.
        </Popup>
      </Marker>
    </MapContainer>
  );
};
