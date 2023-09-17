import { Route, RoutePriorityType } from 'app/model/Route';
import { Driver, VehicleType } from './User';

export type BookRide = {
  petTransportFlag: boolean;
  babyTransportFlag: boolean;
  vehicleTypeId: number;
  numberOfPassengers: number;
  scheduled: boolean;
  scheduledStartTime: '2023-08-25T08:45:43.316Z';

  routeId?: string | number;
  routeOptimizationCriteria: RoutePriorityType;
};

export type Rejection = {
  rejectionReason: string;
  rejectionTime: Date;
};

export type RideStatus = 'PENDING' | 'ACCEPTED' | 'REJECTED' | 'ACTIVE' | 'FINISHED';

export type Ride = {
  id: number;
  startTime?: Date;
  finishTime?: Date;
  numberOfPassengers: number;
  totalPrice: number;
  estimatedDuration: number;
  rideStatus: RideStatus;
  routeOptimizationCriteria: RoutePriorityType;
  panicFlag?: boolean;
  petTransportFlag: boolean;
  babyTransportFlag: boolean;
  driver?: Driver;
  routes: Route[];
  rejection?: Rejection;
  vehicleType: VehicleType;
};
