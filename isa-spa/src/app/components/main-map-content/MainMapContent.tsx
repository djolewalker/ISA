import { useEffect, useState } from 'react';
import { LatLngTuple, geoJson, GeoJSON, icon, marker, Marker } from 'leaflet';
import { useMap } from 'react-leaflet';
import { LineString } from 'geojson';

import { useAppSelector } from 'app/hooks/common';
import { selectRoutes, selectSelectedRouteId } from 'app/pages/routes/routes-page.slice';

import { selectActiveDriversLocations } from 'app/pages/common.slice';
import { useDriverStatusContext } from 'app/contexts/driver-status/driver-status-provider';
import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useActiveRideContext } from 'app/contexts/active-ride/active-ride-provider';

import redIconResource from 'assets/car-red.png';
import greenIconResource from 'assets/car-green.png';
import exclamationIconResource from 'assets/exclamation-mark.png';
import startIconResource from 'assets/start.png';
import finishIconResource from 'assets/finish.png';

import iconUrl from 'leaflet/dist/images/marker-icon.png';
import shadowUrl from 'leaflet/dist/images/marker-shadow.png';

const redCarIcon = icon({
  iconUrl: redIconResource,
  iconSize: [25, 25]
});

const greenCarIcon = icon({
  iconUrl: greenIconResource,
  iconSize: [25, 25]
});

const locationIcon = icon({
  iconUrl,
  shadowUrl
});

const exclamationMarkIcon = icon({
  iconUrl: exclamationIconResource,
  iconSize: [25, 25]
});

const startMarkIcon = icon({
  iconUrl: startIconResource,
  iconSize: [25, 25]
});

const finishMarkIcon = icon({
  iconUrl: finishIconResource,
  iconSize: [25, 25]
});

const selectedRouteStyle = {
  color: 'blue',
  opacity: 0.8
};

const alternativeRouteStyle = {
  color: 'gray',
  opacity: 0.8
};

type GeoJSONRoutes = { [key: string | number]: GeoJSON };

export const MainMapContent = () => {
  const map = useMap();
  const { driverWithPanicInCar } = useActiveRideContext();

  const { active: isDriverActive } = useDriverStatusContext();
  const { user } = useAuthContext();
  const routes = useAppSelector(selectRoutes);
  const selectedRouteId = useAppSelector(selectSelectedRouteId);
  const locations = useAppSelector(selectActiveDriversLocations);

  const [displayedRoutes, setDisplayedRoutes] = useState<GeoJSONRoutes>({});

  useEffect(() => {
    if (routes.bbox?.length) {
      const bounds: LatLngTuple[] = [
        [routes.bbox[1], routes.bbox[0]],
        [routes.bbox[3], routes.bbox[2]]
      ];
      map.fitBounds(bounds);
    }
  }, [map, routes]);

  useEffect(() => {
    const gJSONsMap = routes.features?.reduce((store, current) => {
      if (!current.id) return store;
      store[current.id] = geoJson(current.geometry);
      return store;
    }, {} as GeoJSONRoutes);
    if (!gJSONsMap) return;

    setDisplayedRoutes(gJSONsMap);

    Object.values(gJSONsMap).forEach((gJSON) => map.addLayer(gJSON));

    return () => {
      Object.values(gJSONsMap).forEach((gJSON) => map.removeLayer(gJSON));
      setDisplayedRoutes({});
    };
  }, [map, routes.features]);

  useEffect(() => {
    Object.entries(displayedRoutes).forEach(([key, gJSON]) => {
      if (parseInt(key) === selectedRouteId) gJSON.setStyle(selectedRouteStyle).bringToFront();
      else gJSON.setStyle(alternativeRouteStyle);
    });
  }, [displayedRoutes, selectedRouteId]);

  useEffect(() => {
    const markers: Marker[] = locations.map(({ longitude, latitude, occupied, id }) => {
      const carIcon = occupied ? redCarIcon : greenCarIcon;
      const locationCarIcon = isDriverActive && id === user?.id ? locationIcon : carIcon;

      const icon = id === driverWithPanicInCar ? exclamationMarkIcon : locationCarIcon;
      const newMarker = marker([latitude, longitude], { icon });
      return newMarker;
    });
    markers.forEach((m) => m.addTo(map));

    return () => {
      markers.forEach((m) => m.remove());
    };
  }, [map, locations, isDriverActive, user?.id, driverWithPanicInCar]);

  useEffect(() => {
    const markers: Marker[] = [];
    if (routes?.features?.length === 1) {
      const route = routes.features[0];
      const geo = route.geometry as LineString;
      const start = geo.coordinates[0];
      if (start?.length) {
        markers.push(marker([start[1], start[0]], { icon: startMarkIcon }));
      }
      const finish = geo.coordinates[geo.coordinates.length - 1];
      if (finish?.length) {
        markers.push(marker([finish[1], finish[0]], { icon: finishMarkIcon }));
      }
      markers.forEach((m) => m.addTo(map));
    }
    return () => {
      markers.forEach((m) => m.remove());
    };
  }, [routes, map]);

  return <></>;
};
