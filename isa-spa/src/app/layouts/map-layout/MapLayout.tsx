import { Outlet } from 'react-router-dom';
import { MainMap } from 'app/components/main-map/MainMap';

import './MapLayout.scss';
import LoaderProvider from 'app/contexts/loader/loader-context-provider';
import { IsaLoader } from 'app/components/isa-loader/IsaLoader';

export const MapLayout = () => {
  return (
    <div className="map-layout-main-content">
      <div className="map">
        <MainMap />
      </div>
      <div className="content p-4 d-flex flex-column position-relative">
        <Outlet />
        <IsaLoader />
      </div>
    </div>
  );
};
