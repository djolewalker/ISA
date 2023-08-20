import { AccessToken, User } from 'app/model/User';

import { getRequest, postRequest } from './base.service';

const CONTROLLER = 'auth';

export const signIn = async (username: string, password: string) =>
  postRequest(`${CONTROLLER}/signin`, { username, password }).then((response) => response.data as AccessToken);

export type RegisterUser = Omit<User, 'id' | 'roles'> & { password: string };
export const register = async (user: RegisterUser) =>
  postRequest(`${CONTROLLER}/signup/user`, user).then((response) => response.data as User);

export const verifyEmail = async (token: string) =>
  getRequest(`${CONTROLLER}/verify-email`, { verificationToken: token });

export const forgotPassword = async (email: string) => postRequest(`${CONTROLLER}/forgot-password`, { email });

export const resetPassword = async (resetPasswordToken: string, password: string) =>
  postRequest(`${CONTROLLER}/reset-password`, { password }, { resetPasswordToken });
