import { useEffect } from 'react';
import { Outlet, useNavigate } from 'react-router-dom';
import { MainMap } from 'app/components/main-map/MainMap';
import { IsaLoader } from 'app/components/isa-loader/IsaLoader';
import { useAppDispatch, useAppSelector } from 'app/hooks/common';
import { clearNavigateTo, selectNavigateTo } from 'app/pages/common.slice';

import './MapLayout.scss';

export const MapLayout = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const navigateTo = useAppSelector(selectNavigateTo);
  useEffect(() => {
    if (!navigateTo) return;

    navigate(navigateTo);
    dispatch(clearNavigateTo());
  }, [dispatch, navigate, navigateTo]);

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
