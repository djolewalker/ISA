import { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';
import axios from 'axios';

import { User, UserRole } from 'app/model/User';
import {
  LOCAL_STORAGE_EVENTS,
  removeLocalStorage,
  setLocalStorage,
  useListenLocalStorage
} from 'app/utils/local-storage.utils';
import { getUserInfo } from 'app/service/user-service';
import { setAccessToken } from 'app/service/base-service';
import { useLoader } from 'app/contexts/loader/loader-context-provider';

export const ACCESS_TOKEN_CACHE = 'access_token';

type AuthContextType = {
  isAuthorized: boolean;
  user: User | null;
  hasAnyRole: (roles: UserRole[]) => boolean;
  logOut: () => void;
  setUser: (user: User) => void;
};

const AuthContext = createContext<AuthContextType>({} as AuthContextType);

type AuthContextProviderProps = {
  children: React.ReactNode;
};
const AuthContextProvider = ({ children }: AuthContextProviderProps) => {
  const { activateLoader, deactivateLoader } = useLoader();

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
      activateLoader();
      getUserInfo()
        .then(setUser)
        .catch(() => {
          setToken(null);
          setUser(null);
        })
        .finally(deactivateLoader);
    } else {
      deactivateLoader();
    }
  }, [accessToken, setUser, activateLoader, deactivateLoader]);

  const logOut = () => {
    setToken(null);
    setUser(null);
    removeLocalStorage({ key: ACCESS_TOKEN_CACHE });
  };

  const contextValue = useMemo<AuthContextType>(
    () => ({
      user,
      isAuthorized: Boolean(user),
      hasAnyRole: (roles: UserRole[]) => roles.some((role) => user?.roles.includes(role)),
      logOut,
      setUser
    }),
    [user, setUser]
  );

  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
};

export const useAuthContext = () => useContext(AuthContext);
export default AuthContextProvider;
