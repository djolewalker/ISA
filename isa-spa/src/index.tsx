import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';

import reportWebVitals from 'reportWebVitals';
import AppRoot from 'app/App';

import { store } from 'app/redux/store';
import LoaderProvider from 'app/contexts/loader/loader-context-provider';
import AuthContextProvider from 'app/contexts/auth/auth-context-provider';
import NotificationsProvider from 'app/contexts/notifications/notifications-provider';

import 'styles/index.scss';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

root.render(
  <LoaderProvider>
    <Provider store={store}>
      <AuthContextProvider>
        <NotificationsProvider>
          <AppRoot />
        </NotificationsProvider>
      </AuthContextProvider>
    </Provider>
  </LoaderProvider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
