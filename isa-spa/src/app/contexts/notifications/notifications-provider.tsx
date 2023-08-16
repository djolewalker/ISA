import { notification } from 'antd';
import { NotificationInstance } from 'antd/es/notification/interface';
import { createContext, useContext } from 'react';

const NotificationsContext = createContext<NotificationInstance>({} as NotificationInstance);

type NotificationsProviderProps = {
  children: React.ReactNode;
};

const NotificationsProvider = ({ children }: NotificationsProviderProps) => {
  const [api, contextHolder] = notification.useNotification({ placement: 'topLeft' });

  return (
    <NotificationsContext.Provider value={api}>
      {contextHolder}
      {children}
    </NotificationsContext.Provider>
  );
};

export const useNotifications = () => useContext(NotificationsContext);
export default NotificationsProvider;
