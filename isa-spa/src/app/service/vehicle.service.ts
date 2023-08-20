import { VehicleType } from 'app/model/User';
import { getRequest } from 'app/service/base.service';

const CONTROLLER = 'vehicle';

export const getVehicleTypes = async () =>
  getRequest(`${CONTROLLER}/types`)
    .then((response) => response.data as VehicleType[])
    .catch(() => []);
