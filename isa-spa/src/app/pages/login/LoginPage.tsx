import { useNavigate } from 'react-router-dom';
import { Form, Input, notification } from 'antd';

import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { signIn } from 'app/service/auth-service';
import { AxiosError } from 'axios';
import { LOCAL_STORAGE_EVENTS, setLocalStorage } from 'app/utils/local-storage';
import { ACCESS_TOKEN_CACHE } from 'app/contexts/auth/auth-context-provider';
import { useEffect, useState } from 'react';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { useLoader } from 'app/contexts/loader/loader-context-provider';

type LoginFormFields = {
  username: string;
  password: string;
};

export const LoginPage = () => {
  const notifications = useNotifications();
  const navigate = useNavigate();

  const handleRegisterClicked = () => navigate('/register');

  const handleOnFinish = async (value: LoginFormFields) => {
    signIn(value.username, value.password)
      .then((accessTokenEntity) => {
        setLocalStorage({
          key: ACCESS_TOKEN_CACHE,
          value: accessTokenEntity.accessToken,
          silent: false,
          eventName: LOCAL_STORAGE_EVENTS.ACCESS_TOKEN_CHANGE_EVENT_NAME
        });
      })
      .catch((error: AxiosError) => {
        if (error?.response?.status === 401) {
          notifications.error({ message: 'Invalid credentials' });
        }
      });
  };

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <IsaButton className="mx-2" size="large" type="ghost" onClick={handleRegisterClicked}>
          Registracija
        </IsaButton>
      </MainHeader>
      <div className="my-3 d-flex flex-column justify-content-center flex-grow-1">
        <h2 className="h2 mb-4 text-center">Prijavi se:</h2>
        <Form labelCol={{ span: 6 }} wrapperCol={{ span: 16 }} onFinish={handleOnFinish} autoComplete="off">
          <Form.Item<LoginFormFields>
            label="Korisničko ime"
            name="username"
            rules={[{ required: true, message: 'Unesite korisničko ime!' }]}
          >
            <Input />
          </Form.Item>

          <Form.Item<LoginFormFields>
            label="Lozinka"
            name="password"
            rules={[{ required: true, message: 'Please input your password!' }]}
          >
            <Input.Password visibilityToggle={false} />
          </Form.Item>

          <Form.Item wrapperCol={{ offset: 10, span: 22 }}>
            <IsaButton type="primary" htmlType="submit">
              Prijavi se
            </IsaButton>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
};
