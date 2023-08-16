import { useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { Form, Select } from 'antd';
import { DefaultOptionType } from 'antd/es/select';

import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { useAppDispatch, useAppSelector } from 'app/hooks/common';
import { RoutePriorityType } from 'app/model/Route';
import {
  selectPrioritizedRouteId,
  selectRoutePriorityType,
  selectRoutes,
  selectSelectedRouteId,
  setRoutePriotiryType,
  setSelectedRouteId
} from 'app/pages/routes/routes-page.slice';
import { IsaButton } from 'app/components/isa-button/IsaButton';

const routePriorityOptions: DefaultOptionType[] = [
  { label: 'Udaljenost', value: 'distance' },
  { label: 'Vreme dolaska', value: 'duration' }
];

export const RoutesPage = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const routes = useAppSelector(selectRoutes);
  const prioritizedRouteId = useAppSelector(selectPrioritizedRouteId);
  const selectedRouteId = useAppSelector(selectSelectedRouteId);
  const routePriorityType = useAppSelector(selectRoutePriorityType);

  const { prioritizedRoute, alternativeRoutes } = useMemo(() => {
    const features = routes.features;
    const prioritizedRoute = features?.find((route) => route.id === prioritizedRouteId);
    const alternativeRoutes = features
      ?.filter((route) => route !== prioritizedRoute)
      .sort((a, b) => a.properties?.summary[routePriorityType] - b.properties?.summary[routePriorityType]);
    return { prioritizedRoute, alternativeRoutes };
  }, [routes.features, prioritizedRouteId, routePriorityType]);

  useEffect(() => {
    if (!routes?.features?.length) navigate('/');
  }, [routes, navigate]);

  const handleRoutePriorityTypeChange = (priorityType: RoutePriorityType) =>
    dispatch(setRoutePriotiryType(priorityType));

  const handleRouteSelected = (routeId: string | number) => dispatch(setSelectedRouteId(routeId));

  const handleModifyLocations = () => navigate('/');

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column justify-content-center flex-grow-1">
        <h2 className="h2 mb-4 text-center">Odaberi rutu:</h2>
        <Form className="w-75 mx-auto" layout="vertical">
          <Form.Item label="Prioritizuj rutu po:">
            <Select value={routePriorityType} options={routePriorityOptions} onChange={handleRoutePriorityTypeChange} />
          </Form.Item>
        </Form>
        <div className="w-75 mx-auto">
          <h4 className="my-3 h4">Preporuka:</h4>
          <IsaButton
            className="w-100 "
            type={selectedRouteId === prioritizedRouteId ? 'primary' : 'link'}
            size="large"
            onClick={() => handleRouteSelected(prioritizedRouteId)}
          >
            Udaljenost: {prioritizedRoute?.properties?.summary.distance} km - Vreme dolaska:{' '}
            {(prioritizedRoute?.properties?.summary.duration / 60).toFixed(2)} min
          </IsaButton>

          {alternativeRoutes && (
            <>
              <h4 className="mt-4 mb-3 h4">Alternative:</h4>
              {alternativeRoutes.map((alternative) => (
                <IsaButton
                  key={alternative.id}
                  className="w-100 mb-3"
                  type={selectedRouteId === alternative.id ? 'primary' : 'link'}
                  size="large"
                  onClick={() => handleRouteSelected(alternative.id as number)}
                >
                  Udaljenost: {alternative?.properties?.summary.distance} km - Vreme dolaska:{' '}
                  {(alternative?.properties?.summary.duration / 60).toFixed(2)} min
                </IsaButton>
              ))}
            </>
          )}
        </div>
        <div className="mt-4 w-75 mx-auto d-flex justify-content-center">
          <IsaButton className="mx-4" size="large" onClick={handleModifyLocations}>
            Izmeni
          </IsaButton>
          <IsaButton className="mx-4" type="primary" size="large">
            Nastavi
          </IsaButton>
        </div>
      </div>
    </div>
  );
};
