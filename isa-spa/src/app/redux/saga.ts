import { fork } from 'redux-saga/effects';

import {
  fetchDriverWatcher,
  fetchUserWatcher,
  fetchUsersWatcher,
  fetchVehicleTypesWatcher
} from 'app/pages/admin/admin.saga';
import { fetchRoutesWatcher } from 'app/pages/routes/routes-page.saga';
import { fetchLocationSuggestionsWatcher } from 'app/pages/search/search-page.saga';
import { fetchActiveDriversLocationsWatcher } from 'app/pages/common.saga';
import { fetchRideWatcher } from 'app/pages/ride/ride-page.saga';

export const SAGA_DEBOUNCE_TIME = 400;

export default function* rootSaga() {
  yield fork(fetchLocationSuggestionsWatcher);
  yield fork(fetchRoutesWatcher);
  yield fork(fetchUsersWatcher);
  yield fork(fetchUserWatcher);
  yield fork(fetchVehicleTypesWatcher);
  yield fork(fetchDriverWatcher);
  yield fork(fetchActiveDriversLocationsWatcher);
  yield fork(fetchRideWatcher);
}
