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
