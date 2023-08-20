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

export type Driver = User & {
  driverLicense: string;
  vehicle: Vehicle;
  active?: boolean;
  occupied?: boolean;
};

export type Vehicle = {
  id: number;
  vehicleModel: string;
  registrationNumber: string;
  numberOfSeats: number;
  babyFriendly: boolean;
  petFriendly: boolean;
  vehicleType: VehicleType;
};

export type AccessToken = {
  accessToken: string;
  expiresIn: number;
};

export type VehicleType = {
  id: number;
  vehicleTypeName: string;
  pricePerKm: number;
};
