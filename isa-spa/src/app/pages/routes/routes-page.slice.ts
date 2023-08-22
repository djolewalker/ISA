import { PayloadAction, createSelector, createSlice } from '@reduxjs/toolkit';
import { Coordinates } from 'app/model/Location';
import { RoutePriorityType, RoutePriorityTypeMeasuresMap } from 'app/model/Route';
import { RootState } from 'app/redux/store';
import { FeatureCollection, Feature } from 'geojson';

export type RoutesState = {
  routeCoordinates: Coordinates;
  routes: FeatureCollection;
  isLoadingRoutes: boolean;
  routeError: string;
  prioritizedRouteId: string | number;
  selectedRouteId: string | number;
  routePriorityType: RoutePriorityType;
};

const initialState: RoutesState = {
  routeCoordinates: [],
  routes: {} as FeatureCollection,
  isLoadingRoutes: false,
  routeError: '',
  routePriorityType: 'BY_TIME',
  prioritizedRouteId: '',
  selectedRouteId: ''
};

const calculatePrioritizedRouteId = (state: RoutesState) => {
  const measure = RoutePriorityTypeMeasuresMap[state.routePriorityType];
  const values = state.routes.features.map((f) => f.properties?.summary[measure]);
  const minByPrio = Math.min(...values);
  return state.routes.features.find((f) => f.properties?.summary[measure] === minByPrio)?.id;
};

const routesPage = createSlice({
  name: 'routesPage',
  initialState,
  reducers: {
    setRouteCoordinates: (state, { payload }: PayloadAction<Coordinates>) => {
      state.routeCoordinates = payload;
    },
    setRoutes: (state, { payload }: PayloadAction<FeatureCollection>) => {
      state.routes = payload;

      if (!state?.routes?.features?.length) return;

      const prioritizedRouteId = calculatePrioritizedRouteId(state);
      if (prioritizedRouteId) {
        state.prioritizedRouteId = prioritizedRouteId;
        state.selectedRouteId = prioritizedRouteId;
      }
    },
    setIsLoadingRoutes: (state, { payload }: PayloadAction<boolean>) => {
      state.isLoadingRoutes = payload;
    },
    setRouteError: (state, { payload }: PayloadAction<string>) => {
      state.routeError = payload;
    },
    setRoutePriorityType: (state, { payload }: PayloadAction<RoutePriorityType>) => {
      state.routePriorityType = payload;

      if (!state.routes?.features?.length) return;

      const prioritizedRouteId = calculatePrioritizedRouteId(state);
      if (prioritizedRouteId) state.prioritizedRouteId = prioritizedRouteId;
    },
    setPrioritizedRouteId: (state, { payload }: PayloadAction<string>) => {
      state.routeError = payload;
    },
    setSelectedRouteId: (state, { payload }: PayloadAction<string>) => {
      state.selectedRouteId = payload;
    }
  }
});

const routesPageSliceSelector = (state: RootState) => state.routesPage;

export const selectRouteCoordinates = createSelector(
  routesPageSliceSelector,
  ({ routeCoordinates }) => routeCoordinates
);
export const selectRoutes = createSelector(routesPageSliceSelector, ({ routes }) => routes);
export const selectAreRoutesExist = createSelector(routesPageSliceSelector, ({ routes }) =>
  Boolean(routes?.features?.length)
);
export const selectIsLoadingRoutes = createSelector(routesPageSliceSelector, ({ isLoadingRoutes }) => isLoadingRoutes);
export const selectRouteError = createSelector(routesPageSliceSelector, ({ routeError }) => routeError);
export const selectRoutePriorityType = createSelector(
  routesPageSliceSelector,
  ({ routePriorityType }) => routePriorityType
);
export const selectPrioritizedRouteId = createSelector(
  routesPageSliceSelector,
  ({ prioritizedRouteId }) => prioritizedRouteId
);
export const selectSelectedRouteId = createSelector(routesPageSliceSelector, ({ selectedRouteId }) => selectedRouteId);
export const selectSelectedRoute = createSelector(routesPageSliceSelector, ({ routes, selectedRouteId }) =>
  routes.features.find((route) => route.id === selectedRouteId)
);

export const {
  setRouteCoordinates,
  setRoutes,
  setIsLoadingRoutes,
  setRouteError,
  setRoutePriorityType,
  setPrioritizedRouteId,
  setSelectedRouteId
} = routesPage.actions;

export default routesPage.reducer;
