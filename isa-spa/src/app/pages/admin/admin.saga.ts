import { call, put, select, takeLatest } from 'redux-saga/effects';

import {
  setFetchUserId,
  fetchUsers,
  selectFetchUserId,
  setUsers,
  setUser,
  fetchVehicleTypes,
  setVehicleTypes,
  setFetchDriverId
} from 'app/pages/admin/admin.slice';
import { Driver, User, VehicleType } from 'app/model/User';
import { safe } from 'app/redux/safe-interceptor';
import { getDriver, getUser, getUsers } from 'app/service/user.service';
import { getVehicleTypes } from 'app/service/vehicle.service';

export function* fetchUsersWatcher() {
  yield takeLatest(fetchUsers.type, safe(fetchUsersFlow));
}

function* fetchUsersFlow() {
  const users: User[] = yield call(getUsers);
  yield put(setUsers(users));
}

export function* fetchUserWatcher() {
  yield takeLatest(setFetchUserId.type, safe(fetchUserFlow));
}

function* fetchUserFlow() {
  const userId: number | null = yield select(selectFetchUserId);
  if (!userId) return;

  const user: User = yield call(getUser, userId);
  yield put(setUser(user));
}

export function* fetchDriverWatcher() {
  yield takeLatest(setFetchDriverId.type, safe(fetchDriverFlow));
}

function* fetchDriverFlow() {
  const driverId: number | null = yield select(selectFetchUserId);
  if (!driverId) return;

  const driver: Driver = yield call(getDriver, driverId);
  const user = {
    ...driver,
    ...driver.vehicle,
    vehicleTypeId: driver.vehicle.vehicleType.id
  } as User;
  yield put(setUser(user));
}

export function* fetchVehicleTypesWatcher() {
  yield takeLatest(fetchVehicleTypes.type, safe(fetchVehicleTypesFlow));
}

function* fetchVehicleTypesFlow() {
  const vehicleTypes: VehicleType[] = yield call(getVehicleTypes);
  yield put(setVehicleTypes(vehicleTypes));
}
