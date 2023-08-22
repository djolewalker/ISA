import { BookRide, Ride } from 'app/model/Ride';
import { postRequest } from './base.service';

const CONTROLLER = 'ride';

export const bookRide = async (bookRide: BookRide) => {
  return postRequest(`${CONTROLLER}/booking`, bookRide).then((response) => response.data as Ride);
};
