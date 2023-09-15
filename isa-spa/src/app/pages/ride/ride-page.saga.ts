import { Ride } from 'app/model/Ride';
import { fetchRide, selectRideId, setIsLoadingRide, setRide } from 'app/pages/ride/ride-page.slice';
import { getRide } from 'app/service/ride.service';
import { call, put, select, takeLatest } from 'redux-saga/effects';

export function* fetchRideWatcher() {
  yield takeLatest(fetchRide.type, fetchRideFlow);
}

function* fetchRideFlow() {
  yield put(setIsLoadingRide(true));
  try {
    const rideId: number = yield select(selectRideId);
    const ride: Ride = yield call(getRide, rideId);
    yield put(setRide(ride));
  } catch (error: any) {
    console.log(error);
  } finally {
    yield put(setIsLoadingRide(false));
  }
}
