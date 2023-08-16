import { useEffect, useState } from 'react';
import { LatLngTuple, geoJson, GeoJSON } from 'leaflet';
import { useMap } from 'react-leaflet';

import { useAppSelector } from 'app/hooks/common';
import { selectRoutes, selectSelectedRouteId } from 'app/pages/routes/routes-page.slice';

const selectedRouteStyle = {
  color: 'blue',
  opacity: 0.8
};

const alternatveRouteStyle = {
  color: 'gray',
  opacity: 0.8
};

type GeoJSONRoutes = { [key: string | number]: GeoJSON };

export const MainMapContent = () => {
  const map = useMap();

  const routes = useAppSelector(selectRoutes);
  const selectedRouteId = useAppSelector(selectSelectedRouteId);

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
      store[current.id] = geoJson(current);
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
      if (key === selectedRouteId) gJSON.setStyle(selectedRouteStyle).bringToFront();
      else gJSON.setStyle(alternatveRouteStyle);
    });
  }, [displayedRoutes, selectedRouteId]);

  return <></>;
};
