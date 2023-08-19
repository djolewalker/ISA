import { Navigate, Outlet } from 'react-router-dom';
import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { UserRole } from 'app/model/User';

type Props = {
  roles: UserRole[];
};

export const PermittedRoute = ({ roles }: Props) => {
  const { isAuthorized, hasAnyRole } = useAuthContext();
  const permitted = isAuthorized && hasAnyRole(roles);

  if (!permitted) {
    return <Navigate to="/" />;
  }

  return <Outlet />;
};
