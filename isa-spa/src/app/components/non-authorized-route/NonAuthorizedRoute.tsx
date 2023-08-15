import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { Navigate, Outlet } from 'react-router-dom';

export const NonAuthorizedRoute = () => {
  const { isAuthorized } = useAuthContext();

  if (isAuthorized) {
    return <Navigate to="/" />;
  }

  return <Outlet />;
};
