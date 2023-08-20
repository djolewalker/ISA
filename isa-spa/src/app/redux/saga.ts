import { fork } from 'redux-saga/effects';

import {
  fetchDriverWatcher,
  fetchUserWatcher,
  fetchUsersWatcher,
  fetchVehicleTypesWatcher
} from 'app/pages/admin/admin.saga';
import { fetchRoutesWatcher } from 'app/pages/routes/routes-page.saga';
import { fetchLocationSuggestionsWatcher } from 'app/pages/search/search-page.saga';

export const SAGA_DEBOUNCE_TIME = 400;

export default function* rootSaga() {
  yield fork(fetchLocationSuggestionsWatcher);
  yield fork(fetchRoutesWatcher);
  yield fork(fetchUsersWatcher);
  yield fork(fetchUserWatcher);
  yield fork(fetchVehicleTypesWatcher);
  yield fork(fetchDriverWatcher);
}
