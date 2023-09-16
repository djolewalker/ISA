import { combineReducers, configureStore } from '@reduxjs/toolkit';
import createSagaMiddleware from 'redux-saga';
import rootSaga from 'app/redux/saga';

import common from 'app/pages/common.slice';
import admin from 'app/pages/admin/admin.slice';
import searchPage from 'app/pages/search/search-page.slice';
import routesPage from 'app/pages/routes/routes-page.slice';
import ridePage from 'app/pages/ride/ride-page.slice';

const reducer = combineReducers({
  common,
  admin,
  searchPage,
  routesPage,
  ridePage
});

const sagaMiddleware = createSagaMiddleware();

export const store = configureStore({
  reducer,
  middleware: [sagaMiddleware],
  devTools: true
});

sagaMiddleware.run(rootSaga);

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
