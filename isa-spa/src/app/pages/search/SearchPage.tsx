import { Form } from 'antd';
import Select, { DefaultOptionType } from 'antd/es/select';
import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';
import {
  SearchFormFields,
  selectActiveField,
  selectLocationQuery,
  selectSelectedLocations,
  selectSuggestions,
  setActiveField,
  setLocationsQuery,
  setSelectedLocations
} from 'app/pages/search/search-page.slice';
import { useAppDispatch, useAppSelector } from 'app/hooks/common';
import { useEffect, useState } from 'react';
import { RouteLocations } from 'app/model/Route';
import {
  selectAreRoutesExist,
  selectIsLoadingRoutes,
  selectRouteError,
  setRouteCoordinates
} from 'app/pages/routes/routes-page.slice';
import { buildCoordinates } from 'app/utils/coordinate.utils';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { useNavigate } from 'react-router-dom';
import { OSMLocation } from 'app/service/locations.service';

const mapToOptions = (location: OSMLocation) => ({
  label: location.display_name,
  value: location.place_id,
  data: location
});

export const SearchPage = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { info } = useNotifications();
  const { activateTransparentLoader, deactivateLoader } = useLoader();

  const locationsQuery = useAppSelector(selectLocationQuery);
  const isLoading = useAppSelector(selectIsLoadingRoutes);
  const areRoutesExist = useAppSelector(selectAreRoutesExist);
  const routeError = useAppSelector(selectRouteError);
  const selectedLocations = useAppSelector(selectSelectedLocations);
  const suggestions = useAppSelector(selectSuggestions);
  const activeField = useAppSelector(selectActiveField);

  const handleActiveFieldChange = (field: SearchFormFields) => dispatch(setActiveField(field));

  useEffect(() => {
    if (isLoading) activateTransparentLoader();
    else {
      deactivateLoader();
    }
  }, [isLoading, activateTransparentLoader, deactivateLoader]);

  useEffect(() => {}, [navigate, areRoutesExist]);

  useEffect(() => {
    if (routeError) info({ message: 'Ruta nije nađena!', description: routeError });
  }, [routeError, info]);

  const handleFieldLeft = () => {
    handleSearchLocations('');
    dispatch(setActiveField(null));
  };

  const handleSearchLocations = (searchText: string) => {
    dispatch(setLocationsQuery(searchText));
  };

  const handleSearchFinish = () => {
    const routeCoordinates = buildCoordinates(selectedLocations);
    dispatch(setRouteCoordinates(routeCoordinates));
  };

  const handleLocationSelected = (_: number, b: DefaultOptionType) => {
    if (!activeField) return;
    dispatch(setSelectedLocations({ [activeField]: b.data } as RouteLocations));
  };

  const submitEnabled = Boolean(selectedLocations.start && selectedLocations.destination);

  const getSuggestions = (field: SearchFormFields) => {
    if (suggestions[field]?.length) return suggestions[field];
    if (selectedLocations[field]) return [selectedLocations[field]];

    return [];
  };

  const getiInitialValues = (): { [key: string]: number } => {
    return Object.entries(selectedLocations).reduce((store, [key, data]) => {
      store[key] = data.place_id;
      return store;
    }, {} as { [key: string | number]: number });
  };

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column justify-content-center flex-grow-1">
        <h2 className="h2 mb-4 text-center">Zakaži vožnju:</h2>
        <Form
          className="w-75 mx-auto"
          onFinish={handleSearchFinish}
          autoComplete="off"
          layout="vertical"
          initialValues={getiInitialValues()}
        >
          <Form.Item label="Polazna tačka" name="start">
            <Select
              options={getSuggestions('start')?.map(mapToOptions)}
              showSearch
              suffixIcon={null}
              filterOption={false}
              searchValue={activeField === 'start' ? locationsQuery : ''}
              onSearch={handleSearchLocations}
              onFocus={() => handleActiveFieldChange('start')}
              onBlur={handleFieldLeft}
              onSelect={handleLocationSelected}
              notFoundContent={null}
              allowClear
            />
          </Form.Item>

          <Form.Item label="Konačna destinacija" name="destination">
            <Select
              options={getSuggestions('destination')?.map(mapToOptions)}
              showSearch
              suffixIcon={null}
              filterOption={false}
              searchValue={activeField === 'destination' ? locationsQuery : ''}
              onSearch={handleSearchLocations}
              onFocus={() => handleActiveFieldChange('destination')}
              onBlur={handleFieldLeft}
              onSelect={handleLocationSelected}
              notFoundContent={null}
              allowClear
            />
          </Form.Item>

          <Form.Item className="d-flex justify-content-center">
            <IsaButton disabled={!submitEnabled} className="mt-3" type="primary" htmlType="submit" size="large">
              Pretraži
            </IsaButton>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
};
