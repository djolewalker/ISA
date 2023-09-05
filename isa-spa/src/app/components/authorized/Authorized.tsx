import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { UserRole } from 'app/model/User';

type Props = {
  roles: UserRole[];
  children: JSX.Element;
};

export const Authorized = ({ roles, children }: Props) => {
  const { hasAnyRole } = useAuthContext();
  return <>{hasAnyRole(roles) && children}</>;
};
