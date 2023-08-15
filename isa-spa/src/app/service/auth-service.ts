import { AccessToken } from 'app/model/User';
import { postRequest } from './base-service';

const CONTROLLER = 'auth';

export const signIn = async (username: string, password: string) =>
  postRequest(`${CONTROLLER}/signin`, { username, password }).then((response) => response.data as AccessToken);
