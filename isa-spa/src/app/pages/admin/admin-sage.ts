import { call, put, select, takeLatest } from 'redux-saga/effects';

import { setFetchUserId, fetchUsers, selectFetchUserId, setUsers, setUser } from 'app/pages/admin/admin-slice';
import { User } from 'app/model/User';
import { safe } from 'app/redux/safe-interceptor';
import { getUser, getUsers } from 'app/service/user-service';

export function* fetchUsersWatcher() {
  yield takeLatest(fetchUsers.type, safe(fetchUsersFlow));
}

function* fetchUsersFlow() {
  const users: User[] = yield call(getUsers);
  yield put(setUsers(users));
}

export function* fetchUserWatcher() {
  yield takeLatest(setFetchUserId.type, safe(fetchUserFlow));
}

function* fetchUserFlow() {
  const userId: number | null = yield select(selectFetchUserId);
  if (!userId) return;

  const user: User = yield call(getUser, userId);
  yield put(setUser(user));
}
