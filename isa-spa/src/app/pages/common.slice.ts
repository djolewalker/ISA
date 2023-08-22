import { PayloadAction, createSelector, createSlice } from '@reduxjs/toolkit';
import { VehicleType } from 'app/model/User';
import { RootState } from 'app/redux/store';

export type CommonState = {
  naviagateTo: string | null;
  vehicleTypes: VehicleType[];
};

const initialState: CommonState = {
  naviagateTo: null,
  vehicleTypes: []
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
    }
  }
});

const commonSliceSelector = (state: RootState) => state.common;

export const selectNavigateTo = createSelector(commonSliceSelector, ({ naviagateTo }) => naviagateTo);
export const selectVehicleTypes = createSelector(commonSliceSelector, ({ vehicleTypes }) => vehicleTypes);

export const { clearNavigateTo, setNavigateTo, fetchVehicleTypes, setVehicleTypes } = common.actions;

export default common.reducer;
