import { PayloadAction, createSelector, createSlice } from '@reduxjs/toolkit';
import { DriverLocation } from 'app/model/Location';
import { VehicleType } from 'app/model/User';
import { RootState } from 'app/redux/store';

export type CommonState = {
  naviagateTo: string | null;
  vehicleTypes: VehicleType[];
  activeDriversLocations: DriverLocation[];
};

const initialState: CommonState = {
  naviagateTo: null,
  vehicleTypes: [],
  activeDriversLocations: []
};

const common = createSlice({
  name: 'common',
  initialState,
  reducers: {
    setNavigateTo: (state, { payload }: PayloadAction<string>) => {
      state.naviagateTo = payload;
    },
    clearNavigateTo: (state) => {
      state.naviagateTo = null;
    },
    fetchVehicleTypes: () => {},
    setVehicleTypes: (state, { payload }: PayloadAction<VehicleType[]>) => {
      state.vehicleTypes = payload;
    },
    fetchActiveDriversLocations: () => {},
    setActiveDriversLocations: (state, { payload }: PayloadAction<DriverLocation[]>) => {
      state.activeDriversLocations = payload;
    },
    updateDriversLocation: (state, { payload }: PayloadAction<DriverLocation>) => {
      const locationsWithoutUpdated = state.activeDriversLocations.filter(({ id }) => id !== payload.id);
      state.activeDriversLocations = [...locationsWithoutUpdated, payload];
    },
    remvoveActiveDriverLocation: (state, { payload }: PayloadAction<number>) => {
      state.activeDriversLocations = state.activeDriversLocations.filter(({ id }) => id !== payload);
    }
  }
});

const commonSliceSelector = (state: RootState) => state.common;

export const selectNavigateTo = createSelector(commonSliceSelector, ({ naviagateTo }) => naviagateTo);
export const selectVehicleTypes = createSelector(commonSliceSelector, ({ vehicleTypes }) => vehicleTypes);
export const selectActiveDriversLocations = createSelector(
  commonSliceSelector,
  ({ activeDriversLocations }) => activeDriversLocations
);

export const {
  clearNavigateTo,
  setNavigateTo,
  fetchVehicleTypes,
  setVehicleTypes,
  fetchActiveDriversLocations,
  setActiveDriversLocations,
  updateDriversLocation,
  remvoveActiveDriverLocation
} = common.actions;

export default common.reducer;
