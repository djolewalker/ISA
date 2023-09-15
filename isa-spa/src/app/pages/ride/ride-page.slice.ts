import { PayloadAction, createSelector, createSlice } from '@reduxjs/toolkit';
import { Ride } from 'app/model/Ride';
import { RootState } from 'app/redux/store';

export type RideState = {
  rideId?: number;
  isLoadingRide?: boolean;
  ride?: Ride;
};

const initialState: RideState = {};

const ridePage = createSlice({
  name: 'ridePage',
  initialState,
  reducers: {
    fetchRide: (state, { payload }: PayloadAction<number>) => {
      state.rideId = payload;
    },
    setRide: (state, { payload }: PayloadAction<Ride>) => {
      state.ride = payload;
    },
    setIsLoadingRide: (state, { payload }: PayloadAction<boolean>) => {
      state.isLoadingRide = payload;
    }
  }
});

const ridePageSliceSelector = (state: RootState) => state.ridePage;

export const selectRideId = createSelector(ridePageSliceSelector, ({ rideId }) => rideId);
export const selectRide = createSelector(ridePageSliceSelector, ({ ride }) => ride);
export const selectIsLoadingRide = createSelector(ridePageSliceSelector, ({ isLoadingRide }) => isLoadingRide);

export const { fetchRide, setRide, setIsLoadingRide } = ridePage.actions;

export default ridePage.reducer;
