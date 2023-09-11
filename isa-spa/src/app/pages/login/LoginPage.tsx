import { Link, useNavigate } from 'react-router-dom';
import { Form, Input } from 'antd';

import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { signIn } from 'app/service/auth.service';
import { AxiosError } from 'axios';
import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { useLoader } from 'app/contexts/loader/loader-context-provider';

type LoginFormFields = {
  username: string;
  password: string;
};

export const LoginPage = () => {
  const notifications = useNotifications();
  const navigate = useNavigate();
  const { activateTransparentLoader, deactivateLoader } = useLoader();
  const { fetchUser } = useAuthContext();

  const handleRegisterClicked = () => navigate('/register');

  const handleOnFinish = async (value: LoginFormFields) => {
    activateTransparentLoader();
    signIn(value.username, value.password)
      .then(fetchUser)
      .catch((error: AxiosError) => {
        if (error?.response?.status === 401) {
          notifications.error({ message: 'Invalid credentials' });
        }
      })
      .finally(deactivateLoader);
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
            rules={[{ required: true, message: 'Unesite lozinku!' }]}
          >
            <Input.Password visibilityToggle={false} />
          </Form.Item>

          <Form.Item className="d-flex justify-content-center">
            <IsaButton className="mt-3" type="primary" htmlType="submit" size="large">
              Prijavi se
            </IsaButton>
          </Form.Item>
        </Form>
        <Link className="mx-auto" to="/forgot-password">
          Zaboravljena lozinka?
        </Link>
      </div>
    </div>
  );
};
