import { UserNotification } from 'app/model/Notification';
import { getRequest } from 'app/service/base.service';

const CONTROLLER = 'notification';

export const getUserNotifications = () =>
  getRequest(CONTROLLER).then((response) => response.data as UserNotification[]);
