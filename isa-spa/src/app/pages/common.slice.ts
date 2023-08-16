import { PayloadAction, createSelector, createSlice } from '@reduxjs/toolkit';
import { RootState } from 'app/redux/store';

export type CommonState = {
  naviagateTo: string | null;
};

const initialState: CommonState = {
  naviagateTo: null
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
    }
  }
});

const commonSliceSelector = (state: RootState) => state.common;

export const selectNavigateTo = createSelector(commonSliceSelector, ({ naviagateTo }) => naviagateTo);

export const { clearNavigateTo, setNavigateTo } = common.actions;

export default common.reducer;
