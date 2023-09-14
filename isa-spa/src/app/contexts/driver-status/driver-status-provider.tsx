import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { activateCurrentDriver, deactivateCurrentDriver, getIsCurrentDriverActive } from 'app/service/user.service';
import { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';

type DriverStatusType = {
  active: boolean;
  loading: boolean;
  activate: () => void;
  deactivate: () => void;
};

const DriverStatusContext = createContext<DriverStatusType>({} as DriverStatusType);

type DriverStatusProviderProps = {
  children: React.ReactNode;
};

const DriverStatusProvider = ({ children }: DriverStatusProviderProps) => {
  const { error } = useNotifications();
  const { hasAnyRole, isAuthorized } = useAuthContext();
  const [active, setActive] = useState(false);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (isAuthorized && hasAnyRole(['ROLE_DRIVER'])) {
      setLoading(true);
      getIsCurrentDriverActive()
        .then(({ active }) => setActive(active))
        .catch(() => setActive(false))
        .finally(() => setLoading(false));
    } else {
      setActive(false);
    }
  }, [isAuthorized, hasAnyRole]);

  const deactivate = useCallback(() => {
    if (active) {
      setLoading(true);
      deactivateCurrentDriver()
        .then(() => setActive(false))
        .catch((err) => error({ message: 'Nije mouće deaktivirati vozača!', description: err.message }))
        .finally(() => setLoading(false));
    }
  }, [active, error]);

  const activate = useCallback(() => {
    if (!active) {
      setLoading(true);
      activateCurrentDriver()
        .then(() => setActive(true))
        .catch((err) => error({ message: 'Nije mouće aktivirati vozača!', description: err.message }))
        .finally(() => setLoading(false));
    }
  }, [active, error]);

  const status: DriverStatusType = useMemo(
    () => ({
      active,
      activate,
      deactivate,
      loading
    }),
    [activate, active, deactivate, loading]
  );

  return <DriverStatusContext.Provider value={status}>{children}</DriverStatusContext.Provider>;
};

export const useDriverStatusContext = () => useContext(DriverStatusContext);
export default DriverStatusProvider;
