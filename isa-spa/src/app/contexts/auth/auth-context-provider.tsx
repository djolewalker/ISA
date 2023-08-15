import { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';
import axios from 'axios';

import { User, UserRole } from 'app/model/User';
import { LOCAL_STORAGE_EVENTS, useListenLocalStorage } from 'app/utils/local-storage';
import { getUser } from 'app/service/user-service';
import { useNotifications } from '../notifications/notifications-provider';
import { useNavigate } from 'react-router-dom';
import { setAccessToken } from 'app/service/base-service';

export const ACCESS_TOKEN_CACHE = 'access_token';

type AuthContextType = {
  isAuthorized: boolean;
  user: User | null;
  hasRole: (role: UserRole) => boolean;
};

const AuthContext = createContext<AuthContextType>({
  isAuthorized: false,
  user: null,
  hasRole: (_: UserRole) => false
});

type AuthContextProviderProps = {
  children: React.ReactNode;
};
const AuthContextProvider = ({ children }: AuthContextProviderProps) => {
  const [accessToken, setToken] = useState(localStorage.getItem(ACCESS_TOKEN_CACHE));
  const [user, setUser] = useState<User | null>(null);

  const updateToken = useCallback(() => {
    setToken(localStorage.getItem(ACCESS_TOKEN_CACHE));
  }, [setToken]);

  useListenLocalStorage(LOCAL_STORAGE_EVENTS.ACCESS_TOKEN_CHANGE_EVENT_NAME, updateToken);

  useEffect(() => {
    setAccessToken(accessToken);
  }, [accessToken]);

  useEffect(() => {
    if (accessToken) {
      getUser()
        .then(setUser)
        .catch(() => {
          setToken(null);
          setUser(null);
        });
    }
  }, [accessToken, setUser]);

  const contextValue = useMemo<AuthContextType>(
    () => ({
      user,
      isAuthorized: Boolean(user),
      hasRole: (role: UserRole) => Boolean(user?.roles?.includes(role))
    }),
    [user]
  );

  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
};

export const useAuthContext = () => useContext(AuthContext);
export default AuthContextProvider;
