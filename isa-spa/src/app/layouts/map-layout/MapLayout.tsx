import { Outlet } from 'react-router-dom';
import { MainMap } from 'app/components/main-map/MainMap';
import { IsaLoader } from 'app/components/isa-loader/IsaLoader';

import './MapLayout.scss';

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
