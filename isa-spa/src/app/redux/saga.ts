import { fetchUserWatcher, fetchUsersWatcher } from 'app/pages/admin/admin-sage';
import { fetchRoutesWatcher } from 'app/pages/routes/routes-page.saga';
import { fetchLocationSuggestionsWatcher } from 'app/pages/search/search-page.saga';
import { fork } from 'redux-saga/effects';

export const SAGA_DEBOUNCE_TIME = 400;

export default function* rootSaga() {
  yield fork(fetchLocationSuggestionsWatcher);
  yield fork(fetchRoutesWatcher);
  yield fork(fetchUsersWatcher);
  yield fork(fetchUserWatcher);
}
