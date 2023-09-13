import { useEffect } from 'react';
import { nanoid } from '@reduxjs/toolkit';
import { useNavigate } from 'react-router-dom';
import { Form } from 'antd';
import { DeleteOutlined } from '@ant-design/icons';
import Select, { DefaultOptionType } from 'antd/es/select';
import { FeatureCollection } from 'geojson';

import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';
import {
  SearchFormFields,
  removeSelectedLocation,
  selectActiveField,
  selectLocationQuery,
  selectSelectedLocations,
  selectSuggestions,
  setActiveField,
  setLocationsQuery,
  setSelectedLocations
} from 'app/pages/search/search-page.slice';
import { useAppDispatch, useAppSelector } from 'app/hooks/common';
import { RouteLocations } from 'app/model/Route';
import {
  selectIsLoadingRoutes,
  selectRouteError,
  setRouteCoordinates,
  setRouteError,
  setRoutes
} from 'app/pages/routes/routes-page.slice';
import { buildCoordinates } from 'app/utils/coordinate.utils';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { OSMLocation } from 'app/service/locations.service';
import { Authorized } from 'app/components/authorized/Authorized';

const mapToOptions = (location: OSMLocation | null) => ({
  label: location?.display_name,
  value: location?.place_id,
  data: location
});

export const SearchPage = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { info } = useNotifications();
  const { activateTransparentLoader, deactivateLoader } = useLoader();

  const locationsQuery = useAppSelector(selectLocationQuery);
  const isLoading = useAppSelector(selectIsLoadingRoutes);
  const routeError = useAppSelector(selectRouteError);
  const selectedLocations = useAppSelector(selectSelectedLocations);
  const suggestions = useAppSelector(selectSuggestions);
  const activeField = useAppSelector(selectActiveField);

  const handleActiveFieldChange = (field: SearchFormFields) => dispatch(setActiveField(field));

  useEffect(() => {
    dispatch(setRoutes({} as FeatureCollection));
  }, [dispatch]);

  useEffect(() => {
    if (isLoading) activateTransparentLoader();
    else {
      deactivateLoader();
    }
  }, [isLoading, activateTransparentLoader, deactivateLoader]);

  useEffect(() => {
    if (routeError) {
      info({ message: 'Ruta nije nađena!', description: routeError });
      dispatch(setRouteError(''));
    }
  }, [dispatch, routeError, info]);

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

  const { start, destination, ...stops } = selectedLocations;
  const numberOfStops = stops ? Object.keys(stops).length : 0;
  const allStopsHaveValues = Object.values(stops).every((stop) => stop != null);
  const submitEnabled = Boolean(start && destination && allStopsHaveValues);

  const getSuggestions = (field: SearchFormFields) => {
    if (suggestions[field]?.length) return suggestions[field];
    if (selectedLocations[field]) return [selectedLocations[field]];

    return [];
  };

  const getiInitialValues = (): { [key: string]: number } => {
    return Object.entries(selectedLocations).reduce((store, [key, data]) => {
      if (data?.place_id) store[key] = data.place_id;
      return store;
    }, {} as { [key: string | number]: number });
  };

  const handleAddStopLocation = () => {
    const nextStop = nanoid();
    dispatch(setSelectedLocations({ [nextStop]: null } as RouteLocations));
  };

  const handleRemoveSelectedLocation = (stepToRemove: string) => {
    dispatch(removeSelectedLocation(stepToRemove));
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
              onClear={() => handleRemoveSelectedLocation('start')}
              allowClear
            />
          </Form.Item>

          {stops &&
            Object.entries(stops)?.map(([key]) => (
              <Form.Item key={key} label="Usputno odredište" name={key}>
                <div className="d-flex align-items-center">
                  <Select
                    style={{ maxWidth: '100%', minWidth: '100%' }}
                    options={getSuggestions(key)?.map(mapToOptions)}
                    showSearch
                    suffixIcon={null}
                    filterOption={false}
                    searchValue={activeField === key ? locationsQuery : ''}
                    onSearch={handleSearchLocations}
                    onFocus={() => handleActiveFieldChange(key)}
                    onBlur={handleFieldLeft}
                    onSelect={handleLocationSelected}
                    onClear={() => handleRemoveSelectedLocation(key)}
                    notFoundContent={null}
                    value={getiInitialValues()[key]}
                  />

                  <IsaButton
                    danger
                    shape="circle"
                    type="link"
                    icon={<DeleteOutlined />}
                    onClick={() => handleRemoveSelectedLocation(key)}
                  />
                </div>
              </Form.Item>
            ))}

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
              onClear={() => handleRemoveSelectedLocation('destination')}
              notFoundContent={null}
              allowClear
            />
          </Form.Item>

          <Form.Item className="d-flex justify-content-center">
            <Authorized roles={['ROLE_USER']}>
              <IsaButton
                disabled={numberOfStops >= 4}
                className="mt-3 mx-2"
                type="link"
                size="large"
                onClick={handleAddStopLocation}
              >
                Dodaj usputno odredište
              </IsaButton>
            </Authorized>

            <IsaButton disabled={!submitEnabled} className="mt-3 mx-2" type="primary" htmlType="submit" size="large">
              Pretraži
            </IsaButton>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
};
