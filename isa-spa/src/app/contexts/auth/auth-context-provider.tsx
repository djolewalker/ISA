import { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';

import { User, UserRole } from 'app/model/User';
import { getUserInfo } from 'app/service/user.service';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { signOut } from 'app/service/auth.service';
import { useWs } from 'app/contexts/ws/ws-provider';

export const ACCESS_TOKEN_CACHE = 'access_token';

type AuthContextType = {
  isAuthorized: boolean;
  user: User | null;
  hasAnyRole: (roles: UserRole[]) => boolean;
  logOut: () => void;
  setUser: (user: User) => void;
  fetchUser: () => void;
};

const AuthContext = createContext<AuthContextType>({} as AuthContextType);

type AuthContextProviderProps = {
  children: React.ReactNode;
};
const AuthContextProvider = ({ children }: AuthContextProviderProps) => {
  const { reconnect } = useWs();
  const { activateLoader, deactivateLoader } = useLoader();
  const [user, setUser] = useState<User | null>(null);

  const fetchUser = useCallback(() => {
    activateLoader();
    getUserInfo()
      .then(setUser)
      .catch(() => {
        setUser(null);
      })
      .finally(deactivateLoader);
  }, [activateLoader, deactivateLoader]);

  useEffect(() => {
    reconnect();
    // eslint-disable-next-line
  }, [user]);

  useEffect(() => {
    if (!user) {
      fetchUser();
    } else {
      deactivateLoader();
    }
  }, [deactivateLoader, fetchUser, user]);

  const contextValue = useMemo<AuthContextType>(
    () => ({
      user,
      isAuthorized: Boolean(user),
      hasAnyRole: (roles: UserRole[]) => roles.some((role) => user?.roles.includes(role)),
      logOut: () => {
        signOut().then(() => {
          deactivateLoader();
          setUser(null);
        });
      },
      setUser,
      fetchUser
    }),
    [user, fetchUser, deactivateLoader]
  );

  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
};

export const useAuthContext = () => useContext(AuthContext);
export default AuthContextProvider;
