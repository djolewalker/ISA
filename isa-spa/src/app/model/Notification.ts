import { Ride } from 'app/model/Ride';

export type UserNotification = {
  id: number;
  description: string;
  activationTime: Date;
};

export type AdminNotification = {
  id: number;
  description: string;
  creationTime: Date;
  ride: Ride;
};
