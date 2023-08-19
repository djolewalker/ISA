import { User } from 'app/model/User';
import { getRequest, putRequest } from './base-service';

const CONTROLLER = 'user';

export const getUsers = async () =>
  getRequest(CONTROLLER)
    .then((response) => response.data as User[])
    .catch(() => null);

export const getUser = async (id: number) =>
  getRequest(`${CONTROLLER}/${id}`)
    .then((response) => response.data as User)
    .catch(() => null);

export const getUserInfo = async () =>
  getRequest(`${CONTROLLER}/info`)
    .then((response) => response.data as User)
    .catch(() => null);

export type UpdateUserRequest = {
  email: string;
  firstname: string;
  lastname: string;
  image: string;
  phone: string;
  address: string;
};

export const updateUser = async (id: number, data: UpdateUserRequest) =>
  putRequest(`${CONTROLLER}/${id}`, data).then((response) => response.data as User);
