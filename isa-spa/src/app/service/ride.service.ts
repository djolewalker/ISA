import { BookRide, Ride } from 'app/model/Ride';
import { getRequest, postRequest, putRequest } from './base.service';

const CONTROLLER = 'ride';

export const bookRide = async (bookRide: BookRide) =>
  postRequest(`${CONTROLLER}/booking`, bookRide).then((response) => response.data as Ride);

export const getRide = async (id: number) =>
  getRequest(`${CONTROLLER}/${id}`).then((response) => response.data as Ride);

export const acceptRide = async (id: number, accepted: boolean) =>
  putRequest(`${CONTROLLER}/${id}/accept`, { accepted });

export const rejectRide = async (id: number, reason: string) => putRequest(`${CONTROLLER}/${id}/reject`, { reason });

export const ridePanic = async (id: number, reason: string) => putRequest(`${CONTROLLER}/${id}/panic`, { reason });

export const startRide = async (id: number) => putRequest(`${CONTROLLER}/${id}/start`);

export const finishRide = async (id: number) => putRequest(`${CONTROLLER}/${id}/finish`);

export const rideHistory = async () => getRequest(`${CONTROLLER}/ride-history`).then(({ data }) => data as Ride[]);

export const accountRideHistory = async (id: string) =>
  getRequest(`${CONTROLLER}/ride-history/${id}`).then(({ data }) => data as Ride[]);

export const resolvePanic = async (id: number) => putRequest(`${CONTROLLER}/${id}/panic/resolve`);

export const addToFavourites = async (id: number) => putRequest(`${CONTROLLER}/${id}/favourite/add`);

export const removeFromFavourites = async (id: number) => putRequest(`${CONTROLLER}/${id}/favourite/remove`);

export const cloneRide = async (id: number) =>
  postRequest(`${CONTROLLER}/${id}/clone`, { scheduled: false, scheduledStartTime: null }).then(
    ({ data }) => data as Ride
  );
