import { PayloadAction, createSelector, createSlice } from '@reduxjs/toolkit';
import { User, VehicleType } from 'app/model/User';
import { RootState } from 'app/redux/store';

export type AdminState = {
  users: User[];
  fetchUserId: number | null;
  user: User | null;
};

const initialState: AdminState = {
  users: [],
  fetchUserId: null,
  user: null
};

const admin = createSlice({
  name: 'admin',
  initialState,
  reducers: {
    fetchUsers: () => {},
    setUsers: (state, { payload }: PayloadAction<User[]>) => {
      state.users = payload;
    },
    setFetchUserId: (state, { payload }: PayloadAction<number>) => {
      state.fetchUserId = payload;
    },
    setFetchDriverId: (state, { payload }: PayloadAction<number>) => {
      state.fetchUserId = payload;
    },
    setUser: (state, { payload }: PayloadAction<User | null>) => {
      state.user = payload;
    }
  }
});

const adminSliceSelector = (state: RootState) => state.admin;

export const selectUsers = createSelector(adminSliceSelector, ({ users }) => users);
export const selectUser = createSelector(adminSliceSelector, ({ user }) => user);
export const selectFetchUserId = createSelector(adminSliceSelector, ({ fetchUserId }) => fetchUserId);

export const { fetchUsers, setUsers, setFetchUserId, setUser, setFetchDriverId } = admin.actions;

export default admin.reducer;
