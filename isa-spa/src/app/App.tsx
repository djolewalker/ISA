import React, { useEffect } from 'react';
import axios, { AxiosError } from 'axios';

import { Routes } from './routes/Routes';
import AuthContextProvider from './contexts/auth/auth-context-provider';
import NotificationsProvider, { useNotifications } from './contexts/notifications/notifications-provider';

import './App.scss';

const App = () => {
  const notifications = useNotifications();

  useEffect(() => {
    axios.interceptors.response.use(
      (r) => r,
      (error: AxiosError) => {
        if (error?.response?.status !== 401) {
          notifications.error({
            message: 'Something went wrong!',
            description: error.message
          });
        }

        return Promise.reject(error);
      }
    );
  }, [notifications]);

  useEffect(() => {
    console.log('created');
    return () => console.log('deleted');
  }, []);

  return (
    <AuthContextProvider>
      <NotificationsProvider>
        <Routes />
      </NotificationsProvider>
    </AuthContextProvider>
  );
};

export default App;
