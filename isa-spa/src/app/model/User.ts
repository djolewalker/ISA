export type UserRole = 'ROLE_USER' | 'ROLE_DRIVER' | 'ROLE_ADMIN';

export type User = {
  id: number;
  username: string;
  email: string;
  firstname: string;
  lastname: string;
  image: string;
  phone: string;
  address: string;
  roles: UserRole[];
};

export type AccessToken = {
  accessToken: string;
  expiresIn: number;
};
