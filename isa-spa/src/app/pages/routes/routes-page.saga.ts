import { call, put, select, takeLatest } from '@redux-saga/core/effects';
import {
  selectRouteCoordinates,
  setIsLoadingRoutes,
  setRouteCoordinates,
  setRouteError,
  setRoutes
} from './routes-page.slice';
import { Coordinates } from 'app/model/Location';
import { searchRoutes } from 'app/service/route.service';
import { FeatureCollection } from 'geojson';
import { nanoid } from '@reduxjs/toolkit';
import { setNavigateTo } from 'app/pages/common.slice';

export function* fetchRoutesWatcher() {
  yield takeLatest(setRouteCoordinates.type, fetchRoutesFlow);
}

function* fetchRoutesFlow() {
  yield put(setIsLoadingRoutes(true));
  try {
    const coordinates: Coordinates = yield select(selectRouteCoordinates);
    const routes: FeatureCollection = yield call(searchRoutes, coordinates);
    yield put(setRoutes(routes));
    yield put(setNavigateTo('/route'));
  } catch (error: any) {
    if (error?.response?.data?.orsError?.error?.code === 2004) {
      yield put(setRouteError('Distanca izmedju trazenih lokacija je ća od 150km.'));
    }
  } finally {
    yield put(setIsLoadingRoutes(false));
  }
}
