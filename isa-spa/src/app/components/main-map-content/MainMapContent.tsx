import { useEffect, useState } from 'react';
import { LatLngTuple, geoJson, GeoJSON, icon, marker, Marker } from 'leaflet';
import { useMap } from 'react-leaflet';

import { useAppSelector } from 'app/hooks/common';
import { selectRoutes, selectSelectedRouteId } from 'app/pages/routes/routes-page.slice';

import iconUrl from 'leaflet/dist/images/marker-icon.png';
import shadowUrl from 'leaflet/dist/images/marker-shadow.png';
import { selectActiveDriversLocations } from 'app/pages/common.slice';

const Icon = icon({
  iconUrl,
  shadowUrl
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
    console.log(locations);
    const markers: Marker[] = locations.map(({ longitude, latitude, occupied }) =>
      marker([latitude, longitude], { icon: Icon })
    );
    markers.forEach((m) => m.addTo(map));

    return () => {
      markers.forEach((m) => m.remove());
    };
  }, [map, locations]);

  return <></>;
};
