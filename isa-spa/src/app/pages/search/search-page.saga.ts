import { call, debounce, put, select } from '@redux-saga/core/effects';
import { safe } from 'app/redux/safe-interceptor';
import { OSMLocation, searchLocations } from 'app/service/locations-service';
import { locationReceived, selectLocationQuery, setLocationsQuery } from './search-page.slice';
import { SAGA_DEBOUNCE_TIME } from 'app/redux/saga';

export function* fetchLocationSuggestionsWatcher() {
  yield debounce(SAGA_DEBOUNCE_TIME, setLocationsQuery.type, safe(fetchLocationSuggestionsFlow));
}

function* fetchLocationSuggestionsFlow() {
  const locationQuery: string = yield select(selectLocationQuery);
  if (locationQuery.length < 3) {
    yield put(locationReceived([]));
    return;
  }

  const locations: OSMLocation[] = yield call(searchLocations, locationQuery);
  yield put(locationReceived(locations));
}
