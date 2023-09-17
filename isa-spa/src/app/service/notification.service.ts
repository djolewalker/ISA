import { notification } from 'antd';
import { AdminNotification, UserNotification } from 'app/model/Notification';
import { getRequest } from 'app/service/base.service';

const CONTROLLER = 'notification';

export const getUserNotifications = () =>
  getRequest(CONTROLLER).then(({ data }) =>
    (data as any[]).map(
      (notification) =>
        ({
          ...notification,
          activationTime: new Date(notification.activationTime)
        } as UserNotification)
    )
  );

export const getAdminNotifications = () =>
  getRequest(`${CONTROLLER}/admin`).then(({ data }) =>
    (data as any[]).map(
      (notification) =>
        ({
          ...notification,
          creationTime: new Date(notification.creationTime)
        } as AdminNotification)
    )
  );
