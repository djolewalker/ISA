import { useCallback, useEffect } from 'react';
import { Message, Subscription } from 'stompjs';

import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useWs } from 'app/contexts/ws/ws-provider';
import { useAppDispatch } from 'app/hooks/common';
import { getExampleCoordinate } from 'app/utils/coordinate.utils';
import { DriverLocation } from 'app/model/Location';
import { remvoveActiveDriverLocation, updateDriversLocation } from 'app/pages/common.slice';
import { useDriverStatusContext } from 'app/contexts/driver-status/driver-status-provider';
import { useNavigate } from 'react-router-dom';

export const useDriverLocations = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { active } = useDriverStatusContext();
  const { hasAnyRole } = useAuthContext();
  const { client, connected } = useWs();

  const publishDriverLocation = useCallback(() => {
    if (hasAnyRole(['ROLE_DRIVER']) && active && connected) {
      const [latitude, longitude] = getExampleCoordinate();
      client.send('/app/driver/location', {}, JSON.stringify({ latitude, longitude }));
    }
  }, [active, client, connected, hasAnyRole]);

  useEffect(() => {
    publishDriverLocation();
    const interval = setInterval(publishDriverLocation, 10000);
    return () => {
      clearInterval(interval);
    };
  }, [publishDriverLocation]);

  useEffect(() => {
    const subscriptions: Subscription[] = [];
    if (connected) {
      subscriptions.push(
        client.subscribe('/topic/driver/location', (message: Message) => {
          const driverLocation = JSON.parse(message.body) as DriverLocation;
          dispatch(updateDriversLocation(driverLocation));
        }),
        client.subscribe('/topic/driver/deactivated', (message: Message) => {
          dispatch(remvoveActiveDriverLocation(parseInt(message.body)));
        })
      );

      if (hasAnyRole(['ROLE_DRIVER'])) {
        subscriptions.push(
          client.subscribe('/user/queue/assigned-ride', (message: Message) => {
            navigate(`/ride/${message.body}`);
          })
        );
      }
    }

    return () => {
      subscriptions?.forEach((subscription) => subscription.unsubscribe());
    };
  }, [client, connected, dispatch, hasAnyRole, navigate]);
};
