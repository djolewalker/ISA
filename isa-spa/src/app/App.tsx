import React, { useEffect } from 'react';
import { AxiosError } from 'axios';

import { Routes } from 'app/routes/Routes';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { registerInterceptor, removeInterceptor } from 'app/service/base.service';

import './App.scss';

const CONTROLLERS_EXCLUDED_FROM_ERROR_HANDLING = ['auth', 'user/info', 'user/driver'];
const isExcludedFromErrorHandling = (url: string) => {
  for (const controller of CONTROLLERS_EXCLUDED_FROM_ERROR_HANDLING) {
    if (url.startsWith(controller)) return true;
  }
  return false;
};

const App = () => {
  const notifications = useNotifications();
  const { isAuthorized, logOut } = useAuthContext();

  useEffect(() => {
    const interceptorId = registerInterceptor(
      (r) => r,
      (error: AxiosError<{ message?: string }>) => {
        const url = error?.response?.config.url;
        if (url && isExcludedFromErrorHandling(url)) return Promise.reject(error);

        if (error?.response?.status === 401) {
          if (isAuthorized) logOut();
        } else {
          notifications.error({
            message: 'Something went wrong!',
            description: error.message
          });
        }

        return Promise.reject(error);
      }
    );

    return () => {
      removeInterceptor(interceptorId);
    };
  }, [notifications, isAuthorized, logOut]);

  return <Routes />;
};

export default App;
