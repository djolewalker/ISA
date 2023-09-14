import { DatePicker, Form, InputNumber, Select, Switch } from 'antd';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { useAppDispatch, useAppSelector } from 'app/hooks/common';
import { selectRoutePriorityType, selectSelectedRoute, setRoutes } from 'app/pages/routes/routes-page.slice';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { fetchVehicleTypes, selectVehicleTypes } from 'app/pages/common.slice';
import { BookRide } from 'app/model/Ride';
import { useForm, useWatch } from 'antd/es/form/Form';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { bookRide } from 'app/service/ride.service';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { AxiosError } from 'axios';
import { resetSearch } from 'app/pages/search/search-page.slice';
import { VehicleType } from 'app/model/User';
import { useAuthContext } from 'app/contexts/auth/auth-context-provider';

type BookRideForm = Omit<BookRide, 'routeId' | 'routeOptimizationCriteria'>;

const requiredRules = [{ required: true, message: 'Obavezno polje' }];

export const CreateRidePage = () => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const notifications = useNotifications();
  const { activateTransparentLoader, deactivateLoader } = useLoader();
  const { hasAnyRole } = useAuthContext();

  const [bookRideForm] = useForm<BookRideForm>();
  const selectedRoute = useAppSelector(selectSelectedRoute);
  const vehicleTypes = useAppSelector(selectVehicleTypes);
  const optimizationCriteria = useAppSelector(selectRoutePriorityType);
  const isScheduled = useWatch('scheduled', bookRideForm);

  useEffect(() => {
    dispatch(fetchVehicleTypes());
  }, [dispatch]);

  useEffect(() => {
    if (!selectedRoute) navigate('/');
    else dispatch(setRoutes({ type: 'FeatureCollection', features: [selectedRoute], bbox: selectedRoute.bbox }));
  }, [dispatch, navigate, selectedRoute]);

  const handleBookRide = (formData: BookRideForm) => {
    const data: BookRide = {
      ...formData,
      routeId: selectedRoute?.id,
      routeOptimizationCriteria: optimizationCriteria
    };

    activateTransparentLoader();
    bookRide(data)
      .then((ride) => {
        if (ride.rejection) {
          notifications.info({ message: 'Vožnja nije zakazana!', description: ride.rejection.rejectionReason });
        } else if (isScheduled) {
          notifications.success({
            message: 'Vožnja je zakazana!'
          });
          dispatch(resetSearch());
        } else {
          notifications.success({
            message: 'Vožnja pokrenuta!',
            description: 'Vozilo je krenulo ka dogovorenoj lokaciji!'
          });
          dispatch(resetSearch());
          navigate(`/ride/${ride.id}`);
        }
      })
      .catch((error: AxiosError) => {
        if (error?.response?.status === 409) {
          notifications.error({
            message: 'Ruta privremeno kreirana za ovu vožnju je prestala da postoji!',
            description: 'Odaberi rutu ponovo!'
          });
          navigate('/');
        }
      })
      .finally(deactivateLoader);
  };

  const buildVechileTypeOption = (vechicleType: VehicleType) => {
    const price = selectedRoute?.properties?.summary.distance * vechicleType.pricePerKm;
    const label = price
      ? `${vechicleType.vehicleTypeName} - ${price.toLocaleString()} RSD`
      : vechicleType.vehicleTypeName;
    return { label, value: vechicleType.id };
  };

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column justify-content-center flex-grow-1">
        {hasAnyRole(['ROLE_DRIVER']) ? (
          <h2 className="h2 mb-4 text-center">Vozač nema mogućnost kreiranja vožnje!</h2>
        ) : (
          <>
            <h2 className="h2 mb-4 text-center">Unesite detalje vožnje:</h2>
            <Form form={bookRideForm} onFinish={handleBookRide} className="w-75 mx-auto" layout="vertical">
              <Form.Item<BookRideForm> label="Tip Vozila" name="vehicleTypeId" rules={requiredRules}>
                <Select options={vehicleTypes?.map(buildVechileTypeOption)} />
              </Form.Item>

              <Form.Item<BookRideForm> label="Broj putnika" name="numberOfPassengers" rules={requiredRules}>
                <InputNumber />
              </Form.Item>

              <Form.Item<BookRideForm> valuePropName="checked" label="Prevozi se beba" name="babyTransportFlag">
                <Switch />
              </Form.Item>

              <Form.Item<BookRideForm> valuePropName="checked" label="Prevoze se ljubimci" name="petTransportFlag">
                <Switch />
              </Form.Item>

              <Form.Item<BookRideForm> valuePropName="checked" label="Zakaži u tačno vreme" name="scheduled">
                <Switch />
              </Form.Item>

              {isScheduled && (
                <Form.Item<BookRideForm> label="Zakazano vreme" name="scheduledStartTime">
                  <DatePicker showTime={{ format: 'DD-MM-YYYY HH:mm' }} placeholder="Unesite datum i vreme" />
                </Form.Item>
              )}

              <Form.Item<BookRideForm> className="d-flex justify-content-center" shouldUpdate={true}>
                {({ isFieldsTouched }) => (
                  <IsaButton
                    disabled={!isFieldsTouched()}
                    className="mt-2"
                    type="primary"
                    htmlType="submit"
                    size="large"
                  >
                    Zakaži vožnju
                  </IsaButton>
                )}
              </Form.Item>
            </Form>
          </>
        )}
      </div>
    </div>
  );
};
