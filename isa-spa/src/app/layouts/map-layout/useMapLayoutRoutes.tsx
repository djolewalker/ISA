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

export const useMapLayoutRoutes = () => {
  const { isAuthorized } = useAuthContext();

  const publicRoutes: RouteObject[] = [
    {
      path: '/',
      element: <SearchPage />
    }
  ];

  const authorizedRoutesOnly: RouteObject[] = [
    {
      element: <AuthorizedRoute />,
      children: []
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
