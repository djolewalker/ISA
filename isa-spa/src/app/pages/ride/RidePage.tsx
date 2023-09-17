import { useEffect, useState } from 'react';
import { redirect, useNavigate, useParams } from 'react-router-dom';
import { Button, Form, Input, Popconfirm, Tooltip } from 'antd';

import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { useAppDispatch, useAppSelector } from 'app/hooks/common';
import { fetchRide, selectIsLoadingRide, selectRide } from 'app/pages/ride/ride-page.slice';
import { humanizeMiliseconds } from 'app/utils/humanize.utlis';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { setRoutes } from 'app/pages/routes/routes-page.slice';
import { acceptRide, finishRide, rejectRide, ridePanic, startRide, resolvePanic } from 'app/service/ride.service';
import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useActiveRideContext } from 'app/contexts/active-ride/active-ride-provider';

type RidePageProps = {
  isHistory?: boolean;
};

export const RidePage = ({ isHistory = false }: RidePageProps) => {
  const { hasAnyRole } = useAuthContext();
  const { activateLoader, deactivateLoader } = useLoader();
  const { rideId } = useParams();
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { driverWithPanicInCar } = useActiveRideContext();

  const isDriver = hasAnyRole(['ROLE_DRIVER']);
  const isLoading = useAppSelector(selectIsLoadingRide);
  const ride = useAppSelector(selectRide);
  const durationInMiliSecods = (ride?.estimatedDuration || 0) * 60000;
  const route = ride?.routes[0];
  const stops = route?.stops.slice().sort((a, b) => a.order - b.order);

  const [rejectionReason, setRejectionReason] = useState('');
  const [panicReason, setPanicReason] = useState('');

  useEffect(() => {
    if (!rideId) navigate('/');
    else dispatch(fetchRide(parseInt(rideId)));
  }, [dispatch, navigate, rideId]);

  useEffect(() => {
    if (isLoading) activateLoader();
    else deactivateLoader();
  }, [activateLoader, deactivateLoader, isLoading]);

  useEffect(() => {
    if (route?.geo) dispatch(setRoutes({ features: [route.geo], type: 'FeatureCollection', bbox: route.geo.bbox }));
  }, [dispatch, route]);

  const handleAcceptRejectRide = (accept: boolean) => {
    if (!rideId) return;

    activateLoader();
    acceptRide(parseInt(rideId), accept)
      .then(() => {
        dispatch(fetchRide(parseInt(rideId)));
      })
      .catch(deactivateLoader);
  };

  const handlePanicButtonClick = () => {
    if (!rideId) return;

    activateLoader();
    ridePanic(parseInt(rideId), panicReason)
      .then(() => {
        dispatch(fetchRide(parseInt(rideId)));
      })
      .catch(deactivateLoader);
  };

  const handleDriverCanceledRide = () => {
    if (!rideId) return;

    activateLoader();
    rejectRide(parseInt(rideId), rejectionReason)
      .then(() => {
        dispatch(fetchRide(parseInt(rideId)));
      })
      .catch(deactivateLoader);
  };

  const handleFinishRide = () => {
    if (!rideId) return;

    activateLoader();
    finishRide(parseInt(rideId))
      .then(() => {
        dispatch(fetchRide(parseInt(rideId)));
      })
      .catch(deactivateLoader);
  };

  const hanldeStartRide = () => {
    if (!rideId) return;

    activateLoader();
    startRide(parseInt(rideId))
      .then(() => {
        dispatch(fetchRide(parseInt(rideId)));
      })
      .catch(deactivateLoader);
  };

  const handleResolvePanic = () => {
    if (!rideId) return;

    activateLoader();
    resolvePanic(parseInt(rideId))
      .then(() => {
        dispatch(fetchRide(parseInt(rideId)));
      })
      .catch(deactivateLoader);
  };

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column justify-content-center flex-grow-1">
        {!ride ? (
          <h2 className="h2 mb-4 text-center">Vožnja nije nađena!</h2>
        ) : (
          <>
            {isHistory ? (
              <h2 className="h2 mb-4 text-center">Vožnja broj: {ride.id}</h2>
            ) : (
              <Tooltip title={ride.panicFlag ? 'Panik taster pritisnut!' : ''}>
                <h2 className={'h2 mb-4 text-center' + (ride.panicFlag ? ' text-danger' : '')}>
                  {ride?.rideStatus === 'PENDING' && !isDriver && 'Potvrdite vožnju:'}
                  {ride?.rideStatus === 'PENDING' && isDriver && 'Vožnja ponuđena korisniku:'}
                  {ride?.rideStatus === 'REJECTED' && 'Odbijena vožnja:'}
                  {ride?.rideStatus === 'ACCEPTED' && !isDriver && 'Započeta vožnja:'}
                  {ride?.rideStatus === 'ACCEPTED' && isDriver && 'Pokrenuta vožnja:'}
                  {ride?.rideStatus === 'ACTIVE' && 'Vožnja u toku:'}
                  {ride?.rideStatus === 'FINISHED' && 'Završena vožnja:'}
                </h2>
              </Tooltip>
            )}

            <Form className="w-75 mx-auto" layout="vertical">
              {ride?.rideStatus === 'REJECTED' && (
                <Form.Item label="Razlog odbijanja:">
                  <Input value={ride.rejection?.rejectionReason ?? 'Razlog nije naveden'} readOnly />
                </Form.Item>
              )}

              {(hasAnyRole(['ROLE_ADMIN']) || (isHistory && hasAnyRole(['ROLE_ADMIN']))) && (
                <>
                  <Form.Item label="Vozač:">
                    <Input value={`${ride.driver?.firstname} ${ride.driver?.lastname}`} readOnly />
                  </Form.Item>

                  <Form.Item label="Broj putnika:">
                    <Input value={ride?.numberOfPassengers || 0} readOnly />
                  </Form.Item>

                  <Form.Item label="Vozilo:">
                    <Input
                      value={`${ride?.driver?.vehicle.vehicleModel} - ${ride?.driver?.vehicle.vehicleModel}`}
                      readOnly
                    />
                  </Form.Item>
                </>
              )}

              <Form.Item label="Procenjeno trajanje vožnje:">
                <Input value={humanizeMiliseconds(durationInMiliSecods)} readOnly />
              </Form.Item>

              <Form.Item label="Početna destinacija:">
                <Input value={route?.startLocation.name} readOnly />
              </Form.Item>

              {stops?.map(({ location, id }) => (
                <Form.Item key={id} label="Usputna destinacija:">
                  <Input value={location.name} readOnly />
                </Form.Item>
              ))}

              <Form.Item label="Krajnja destinacija:">
                <Input value={route?.finishLocation.name} readOnly />
              </Form.Item>

              <Form.Item label="Cena vožnje:">
                <Input value={ride?.totalPrice.toLocaleString() || 0} suffix="RSD" readOnly />
              </Form.Item>

              {isHistory && (
                <>
                  {ride?.startTime && (
                    <Form.Item label="Vreme starta:">
                      <Input value={new Date(ride?.startTime).toLocaleString()} readOnly />
                    </Form.Item>
                  )}

                  {ride?.finishTime && (
                    <Form.Item label="Vreme završetka:">
                      <Input value={new Date(ride?.finishTime).toLocaleString()} readOnly />
                    </Form.Item>
                  )}
                </>
              )}

              {!isHistory && (
                <>
                  {ride?.rideStatus === 'PENDING' && !isDriver && (
                    <div className="d-flex justify-content-center">
                      <IsaButton
                        className="my-3 mx-2"
                        type="link"
                        size="large"
                        onClick={() => handleAcceptRejectRide(false)}
                      >
                        Odustani
                      </IsaButton>
                      <IsaButton
                        className="mt-3 mx-2"
                        type="primary"
                        size="large"
                        onClick={() => handleAcceptRejectRide(true)}
                      >
                        Prihvati vožnju
                      </IsaButton>
                    </div>
                  )}

                  {ride?.rideStatus === 'ACCEPTED' && isDriver && (
                    <div className="d-flex justify-content-center">
                      <IsaButton className="w-100 mt-2 mb-3" type="primary" size="large" onClick={hanldeStartRide}>
                        Započni vožnju
                      </IsaButton>
                    </div>
                  )}

                  {ride?.rideStatus === 'ACTIVE' && isDriver && (
                    <div className="d-flex justify-content-center">
                      <IsaButton className="w-100 mt-2 mb-3" type="primary" size="large" onClick={handleFinishRide}>
                        Završi vožnju
                      </IsaButton>
                    </div>
                  )}

                  {['ACTIVE', 'ACCEPTED'].includes(ride?.rideStatus ?? '') && (
                    <div className="d-flex justify-content-between">
                      {ride?.rideStatus === 'ACCEPTED' && isDriver && (
                        <Popconfirm
                          icon=""
                          placement="topLeft"
                          title={
                            <Form layout="vertical">
                              <Form.Item label="Unesite razlog prekida vožnje!">
                                <Input.TextArea
                                  value={rejectionReason}
                                  onChange={(value) => setRejectionReason(value.target.value)}
                                />
                              </Form.Item>
                            </Form>
                          }
                          okText="Potvrdi"
                          cancelText="Odustani"
                          okButtonProps={{ disabled: !rejectionReason }}
                          onConfirm={handleDriverCanceledRide}
                        >
                          <IsaButton className="my-3" type="link" size="large">
                            Prekini vožnju
                          </IsaButton>
                        </Popconfirm>
                      )}
                      <div />

                      {hasAnyRole(['ROLE_DRIVER', 'ROLE_USER']) ? (
                        <Popconfirm
                          placement="topLeft"
                          icon=""
                          title={
                            <Form layout="vertical">
                              <Form.Item label="Navedite šta je izazvalo paniku!">
                                <Input.TextArea
                                  value={panicReason}
                                  onChange={(value) => setPanicReason(value.target.value)}
                                />
                              </Form.Item>
                            </Form>
                          }
                          okText="Potvrdi"
                          cancelText="Odustani"
                          okButtonProps={{ disabled: !panicReason, danger: true }}
                          onConfirm={handlePanicButtonClick}
                        >
                          <IsaButton
                            className="my-3"
                            type="primary"
                            size="large"
                            danger
                            disabled={!!driverWithPanicInCar}
                          >
                            {driverWithPanicInCar ? 'OPASNOST PRIJAVLJENA' : 'PRIJAVI OPASNOST'}
                          </IsaButton>
                        </Popconfirm>
                      ) : (
                        ride.panicFlag && (
                          <IsaButton className="my-3" size="large" type="primary" onClick={handleResolvePanic}>
                            Ukloni oznaku OPASNOSTI
                          </IsaButton>
                        )
                      )}
                    </div>
                  )}
                </>
              )}
            </Form>
          </>
        )}
      </div>
    </div>
  );
};
