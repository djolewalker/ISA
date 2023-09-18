import { FloatButton } from 'antd';
import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useWs } from 'app/contexts/ws/ws-provider';
import { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Message, Subscription } from 'stompjs';
import { CarOutlined, ExclamationCircleOutlined } from '@ant-design/icons';
import { useAppSelector } from 'app/hooks/common';
import { selectRide } from 'app/pages/ride/ride-page.slice';

type ActiveRideType = {
  active: boolean;
  rideId?: number;
  driverWithPanicInCar?: number;
};

const ActiveRideContext = createContext<ActiveRideType>({} as ActiveRideType);

type ActiveRideProviderProps = {
  children: React.ReactNode;
};

const ActiveRideProvider = ({ children }: ActiveRideProviderProps) => {
  const [rideId, setRideId] = useState<number | undefined>();
  const [driverWithPanicInCar, setDriverWithPanicInCar] = useState<number | undefined>();
  const ride = useAppSelector(selectRide);
  const navigate = useNavigate();

  const { hasAnyRole } = useAuthContext();
  const { client, connected } = useWs();

  useEffect(() => {
    if (ride?.panicFlag) {
      setDriverWithPanicInCar(ride?.driver?.id);
    } else {
      setDriverWithPanicInCar(undefined);
    }
  }, [ride]);

  useEffect(() => {
    const subscriptions: Subscription[] = [];
    if (connected) {
      if (hasAnyRole(['ROLE_DRIVER'])) {
        subscriptions.push(
          client.subscribe('/user/queue/assigned-ride', ({ body }: Message) => {
            setRideId(parseInt(body));
            navigate(`/ride/${body}`);
          })
        );
      }

      if (hasAnyRole(['ROLE_USER'])) {
        subscriptions.push(
          client.subscribe('/user/queue/active-ride', ({ body }: Message) => {
            setRideId(parseInt(body));
            navigate(`/ride/${body}`);
          })
        );
      }

      if (hasAnyRole(['ROLE_DRIVER', 'ROLE_USER'])) {
        subscriptions.push(
          client.subscribe('/user/queue/finish-ride', ({ body }: Message) => {
            setRideId(undefined);
            navigate(`/ride/${body}`);
          })
        );

        subscriptions.push(
          client.subscribe('/user/queue/panic-car', ({ body }: Message) => {
            setDriverWithPanicInCar(parseInt(body));
          })
        );

        subscriptions.push(
          client.subscribe('/user/queue/panic-car-resolved', ({ body }: Message) => {
            setDriverWithPanicInCar((current) => (parseInt(body) === current ? undefined : current));
          })
        );
      }
    }

    return () => {
      subscriptions?.forEach((subscription) => subscription.unsubscribe());
    };
  }, [client, connected, hasAnyRole, navigate]);

  const fetchRideStatus = useCallback(() => {
    if (hasAnyRole(['ROLE_DRIVER']) && connected) {
      client.send('/app/assigned-ride', {});
    }

    if (hasAnyRole(['ROLE_USER']) && connected) {
      client.send('/app/active-ride', {});
    }
  }, [client, connected, hasAnyRole]);

  useEffect(() => {
    fetchRideStatus();
  }, [fetchRideStatus]);

  const handleActiveRideClick = () => {
    navigate(`/ride/${rideId}`);
  };

  const api: ActiveRideType = useMemo(
    () => ({
      active: !!rideId,
      rideId,
      driverWithPanicInCar
    }),
    [driverWithPanicInCar, rideId]
  );

  return (
    <ActiveRideContext.Provider value={api}>
      {rideId && (
        <FloatButton
          tooltip={driverWithPanicInCar ? 'Opasnost u aktivnoj vožnji' : 'Aktivna vožnja'}
          icon={driverWithPanicInCar ? <ExclamationCircleOutlined /> : <CarOutlined />}
          type={driverWithPanicInCar ? 'default' : 'primary'}
          shape="circle"
          onClick={handleActiveRideClick}
        />
      )}
      {children}
    </ActiveRideContext.Provider>
  );
};

export const useActiveRideContext = () => useContext(ActiveRideContext);
export default ActiveRideProvider;
