import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { MapLayout } from './MapLayout';
import { SearchPage } from 'app/pages/search/SearchPage';
import { Navigate, RouteObject } from 'react-router-dom';
import { AuthorizedRoute } from 'app/components/authorized-route/AuthorizedRoute';
import { NonAuthorizedRoute } from 'app/components/non-authorized-route/NonAuthorizedRoute';
import { LoginPage } from 'app/pages/login/LoginPage';
import { RegisterPage } from 'app/pages/register/RegisterPage';
import { VerifyEmailPage } from 'app/pages/verify-email/VerifyEmailPage';
import { ForgotPasswordPage } from 'app/pages/forgot-password/ForgotPasswordPage';
import { ResetPasswordPage } from 'app/pages/reset-password/RestPasswordPage';
import { RoutesPage } from 'app/pages/routes/RoutesPage';
import { ProfilePage } from 'app/pages/profile/ProfilePage';
import { PermittedRoute } from 'app/components/permitted-route/PermittedRoute';
import { UsersPage } from 'app/pages/admin/users/UsersPage';
import { UserPage } from 'app/pages/admin/user/UserPage';

export const useMapLayoutRoutes = () => {
  const { isAuthorized } = useAuthContext();

  const publicRoutes: RouteObject[] = [
    {
      path: '/',
      element: <SearchPage />
    },
    {
      path: '/route',
      element: <RoutesPage />
    }
  ];

  const authorizedRoutesOnly: RouteObject[] = [
    {
      element: <AuthorizedRoute />,
      children: [
        {
          path: '/profile',
          element: <ProfilePage />
        },
        {
          path: '/admin',
          element: <PermittedRoute roles={['ROLE_ADMIN']} />,
          children: [
            {
              path: '/admin/users',
              element: <UsersPage />
            },
            {
              path: '/admin/users/create-driver',
              element: <UserPage isCreate isDriverComponent />
            },
            {
              path: '/admin/users/:userId',
              element: <UserPage />
            },
            {
              path: '/admin',
              element: <Navigate to="/" replace />
            }
          ]
        }
      ]
    }
  ];

  const nonAuthorizedRoutesOnly: RouteObject[] = [
    {
      element: <NonAuthorizedRoute />,
      children: [
        { path: '/login', element: <LoginPage /> },
        { path: '/register', element: <RegisterPage /> },
        { path: '/verify-email/:token', element: <VerifyEmailPage /> },
        { path: '/forgot-password', element: <ForgotPasswordPage /> },
        { path: '/reset-password/:token', element: <ResetPasswordPage /> }
      ]
    }
  ];

  return [
    {
      path: '/',
      element: <MapLayout />,
      children: [
        ...publicRoutes,
        ...(isAuthorized ? authorizedRoutesOnly : nonAuthorizedRoutesOnly),
        {
          path: '*',
          element: <Navigate to="/" replace />
        }
      ]
    }
  ];
};
