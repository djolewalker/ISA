import { call, put, takeLatest } from 'redux-saga/effects';
import { safe } from 'app/redux/safe-interceptor';

import { DriverLocation } from 'app/model/Location';
import { fetchActiveDriversLocations, setActiveDriversLocations } from 'app/pages/common.slice';
import { getActiveDriversLocations } from 'app/service/user.service';

export function* fetchActiveDriversLocationsWatcher() {
  yield takeLatest(fetchActiveDriversLocations.type, safe(fetchActiveDriversLocationsFlow));
}

function* fetchActiveDriversLocationsFlow() {
  const locations: DriverLocation[] = yield call(getActiveDriversLocations);
  yield put(setActiveDriversLocations(locations));
}
