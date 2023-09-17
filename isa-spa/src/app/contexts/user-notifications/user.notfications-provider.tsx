import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { useWs } from 'app/contexts/ws/ws-provider';
import { AdminNotification, UserNotification } from 'app/model/Notification';
import { getAdminNotifications, getUserNotifications } from 'app/service/notification.service';
import { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';
import { Message, Subscription } from 'stompjs';

type UserNotificationsType = {
  userNotifications: UserNotification[];
  adminNotifications: AdminNotification[];
  addNewNotification: (notification: UserNotification) => void;
};

const UserNotificationsContext = createContext<UserNotificationsType>({} as UserNotificationsType);

type UserNotificationsProviderProps = {
  children: React.ReactNode;
};

const UserNotificationsProvider = ({ children }: UserNotificationsProviderProps) => {
  const { info } = useNotifications();
  const { hasAnyRole, isAuthorized } = useAuthContext();
  const { client, connected } = useWs();

  const [userNotifications, setUserNotifications] = useState<UserNotification[]>([]);
  const [adminNotifications, setAdminNotifications] = useState<AdminNotification[]>([]);

  const isUserAdmin = hasAnyRole(['ROLE_ADMIN']);

  useEffect(() => {
    if (isAuthorized && !isUserAdmin) {
      getUserNotifications().then(setUserNotifications).catch(console.error);
    } else if (isUserAdmin) {
      getAdminNotifications().then(setAdminNotifications).catch(console.error);
    }
  }, [isAuthorized, isUserAdmin]);

  const handleNewNotification = useCallback(
    (notification: UserNotification) => {
      info({ message: notification.description });
      setUserNotifications((state) => [notification, ...state]);
    },
    [info]
  );

  const handleNewAdminNotification = useCallback(
    (notification: AdminNotification) => {
      info({ message: notification.description });
      setAdminNotifications((state) => [notification, ...state]);
    },
    [info]
  );

  useEffect(() => {
    const subscriptions: Subscription[] = [];
    if (isAuthorized && !isUserAdmin && connected) {
      subscriptions.push(
        client.subscribe('/user/queue/notification', (message: Message) =>
          handleNewNotification(JSON.parse(message.body) as UserNotification)
        )
      );
    }

    if (isUserAdmin && connected) {
      subscriptions.push(
        client.subscribe('/topic/notification/admin', (message: Message) =>
          handleNewAdminNotification(JSON.parse(message.body) as AdminNotification)
        )
      );
    }

    return () => {
      subscriptions?.forEach((subscription) => subscription.unsubscribe());
    };
  }, [client, connected, handleNewAdminNotification, handleNewNotification, isAuthorized, isUserAdmin]);

  const addNewNotification = useCallback(
    (notification: UserNotification) => {
      setUserNotifications((state) => [notification, ...state]);
    },
    [setUserNotifications]
  );

  const api = useMemo(
    () => ({
      userNotifications,
      adminNotifications,
      addNewNotification
    }),
    [addNewNotification, adminNotifications, userNotifications]
  );

  return <UserNotificationsContext.Provider value={api}>{children}</UserNotificationsContext.Provider>;
};

export const useUserNotifications = () => useContext(UserNotificationsContext);
export default UserNotificationsProvider;
