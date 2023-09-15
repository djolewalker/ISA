import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { UserNotification } from 'app/model/Notification';
import { getUserNotifications } from 'app/service/notification.service';
import { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';

type UserNotificationsType = {
  userNotifications: UserNotification[];
  addNewNotification: (notification: UserNotification) => void;
};

const UserNotificationsContext = createContext<UserNotificationsType>({} as UserNotificationsType);

type UserNotificationsProviderProps = {
  children: React.ReactNode;
};

const UserNotificationsProvider = ({ children }: UserNotificationsProviderProps) => {
  const { info, error } = useNotifications();
  const { hasAnyRole, isAuthorized } = useAuthContext();

  const [userNotifications, setUserNotifications] = useState<UserNotification[]>([]);

  useEffect(() => {
    if (isAuthorized && !hasAnyRole(['ROLE_ADMIN'])) {
      getUserNotifications().then(setUserNotifications).catch(console.error);
    }
  }, [hasAnyRole, isAuthorized]);

  const addNewNotification = useCallback(
    (notification: UserNotification) => {
      setUserNotifications((state) => [notification, ...state]);
    },
    [setUserNotifications]
  );

  const api = useMemo(
    () => ({
      userNotifications,
      addNewNotification
    }),
    [addNewNotification, userNotifications]
  );

  return <UserNotificationsContext.Provider value={api}>{children}</UserNotificationsContext.Provider>;
};

export const useUserNotifications = () => useContext(UserNotificationsContext);
export default UserNotificationsProvider;
