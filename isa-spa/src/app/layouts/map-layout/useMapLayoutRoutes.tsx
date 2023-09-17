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
import { CreateRidePage } from 'app/pages/create-ride/CreateRidePage';
import { RidePage } from 'app/pages/ride/RidePage';
import { DriverProfilePage } from 'app/pages/driver-profile/DriverProfilePage';
import { HistoryPage } from 'app/pages/history/HistoryPage';

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
          element: <PermittedRoute roles={['ROLE_ADMIN', 'ROLE_USER']} />,
          children: [
            {
              path: '/profile',
              element: <ProfilePage />
            }
          ]
        },
        {
          path: '/driver',
          element: <PermittedRoute roles={['ROLE_DRIVER']} />,
          children: [
            {
              path: '/driver/profile',
              element: <DriverProfilePage />
            }
          ]
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
              path: '/admin/driver',
              element: <UserPage isCreate isDriverComponent />
            },
            {
              path: '/admin/drivers/:userId',
              element: <UserPage isDriverComponent />
            },
            {
              path: '/admin/users/:userId',
              element: <UserPage />
            },
            {
              path: '/admin',
              element: <Navigate to="/" replace />
            },
            {
              path: '/admin/ride/history/user/:userId',
              element: <HistoryPage />
            }
          ]
        },
        {
          path: '/ride/booking',
          element: <CreateRidePage />
        },
        {
          path: '/ride/:rideId',
          element: <RidePage />
        },
        {
          path: '/ride/history/:rideId',
          element: <RidePage isHistory />
        },
        {
          path: '/ride/history',
          element: <HistoryPage />
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
