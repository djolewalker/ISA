import { User } from 'app/model/User';
import { getRequest } from './base-service';

const CONTROLLER = 'user';

export const getUser = async () =>
  getRequest(`${CONTROLLER}/info`)
    .then((response) => response.data as User)
    .catch(() => null);
