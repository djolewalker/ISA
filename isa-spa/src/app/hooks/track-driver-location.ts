import { useCallback, useEffect } from 'react';
import { Message } from 'stompjs';

import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useWs } from 'app/contexts/ws/ws-provider';
import { useAppDispatch } from 'app/hooks/common';
import { getExampleCoordinate } from 'app/utils/coordinate.utils';
import { DriverLocation } from 'app/model/Location';
import { remvoveActiveDriverLocation, updateDriversLocation } from 'app/pages/common.slice';

export const useDriverLocations = () => {
  const dispatch = useAppDispatch();
  const { hasAnyRole } = useAuthContext();
  const { client, connected } = useWs();

  const publishDriverLocation = useCallback(() => {
    if (hasAnyRole(['ROLE_DRIVER']) && connected) {
      const [latitude, longitude] = getExampleCoordinate();
      client.send('/app/driver/location', {}, JSON.stringify({ latitude, longitude }));
    }
  }, [client, connected, hasAnyRole]);

  useEffect(() => {
    publishDriverLocation();
    const interval = setInterval(publishDriverLocation, 10000);
    return () => {
      clearInterval(interval);
    };
  }, [publishDriverLocation]);

  const listenForDriverLocation = useCallback(() => {
    if (connected) {
      client.subscribe('/topic/driver/location', (message: Message) => {
        const driverLocation = JSON.parse(message.body) as DriverLocation;
        dispatch(updateDriversLocation(driverLocation));
      });

      client.subscribe('/topic/driver/deactivated', (message: Message) => {
        dispatch(remvoveActiveDriverLocation(parseInt(message.body)));
      });
    }
  }, [connected, client, dispatch]);

  useEffect(() => {
    listenForDriverLocation();
  }, [listenForDriverLocation]);
};
