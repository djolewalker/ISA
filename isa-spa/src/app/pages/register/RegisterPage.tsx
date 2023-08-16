import { useNavigate } from 'react-router-dom';
import { Form, Input } from 'antd';

import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { RegisterUser, register } from 'app/service/auth-service';
import { useState } from 'react';
import { AxiosError } from 'axios';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';

type RegisterFormPage = RegisterUser;

export const RegisterPage = () => {
  const notifications = useNotifications();
  const navigate = useNavigate();
  const handleLoginClicked = () => navigate('/login');
  const { activateTransparentLoader, deactivateLoader } = useLoader();

  const [registrationRequested, setRegistrationRequested] = useState(false);

  const handleOnFinish = (data: RegisterFormPage) => {
    activateTransparentLoader();
    register(data)
      .then(() => {
        setRegistrationRequested(true);
      })
      .catch((error: AxiosError<{ message: string }>) => {
        if (error?.response?.status === 409) {
          notifications.open({ message: error?.response.data?.message });
        }
      })
      .finally(deactivateLoader);
  };

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <IsaButton className="mx-2" size="large" type="primary" onClick={handleLoginClicked}>
          Prijavi se
        </IsaButton>
      </MainHeader>
      <div className="my-3 d-flex flex-column justify-content-center flex-grow-1">
        {registrationRequested ? (
          <>
            <h2 className="h2 mb-4 text-center">Registracija uspešna!</h2>
            <h4 className="h4 text-center">Verifikacioni email je poslat na vašu email adresu.</h4>
          </>
        ) : (
          <>
            <h2 className="h2 mb-4 text-center">Registracija:</h2>
            <Form
              labelCol={{ span: 6 }}
              wrapperCol={{ span: 16 }}
              onFinish={handleOnFinish}
              autoComplete="off"
              validateTrigger="onSubmit"
            >
              <Form.Item<RegisterFormPage>
                label="Korisničko ime"
                name="username"
                rules={[{ required: true, message: 'Unesite korisničko ime!' }]}
              >
                <Input />
              </Form.Item>

              <Form.Item<RegisterFormPage>
                label="Email adresa"
                name="email"
                rules={[{ required: true, type: 'email', message: 'Unesite ispravnu email adresu!' }]}
              >
                <Input />
              </Form.Item>

              <Form.Item<RegisterFormPage>
                label="Ime"
                name="firstname"
                rules={[{ required: true, message: 'Unesite ime!' }]}
              >
                <Input />
              </Form.Item>

              <Form.Item<RegisterFormPage>
                label="Prezime"
                name="lastname"
                rules={[{ required: true, message: 'Unesite prezime!' }]}
              >
                <Input />
              </Form.Item>

              <Form.Item<RegisterFormPage>
                label="Lozinka"
                name="password"
                rules={[{ required: true, message: 'Unesite lozinku!' }]}
              >
                <Input.Password visibilityToggle={false} />
              </Form.Item>

              <Form.Item<RegisterFormPage>
                label="Ponovi lozinku"
                name="repeatPassword"
                rules={[
                  { required: true, message: 'Ponovite lozinku!' },
                  ({ getFieldValue }) => ({
                    validator: (_, value) => {
                      if (!value) return Promise.resolve();
                      const password = getFieldValue('password');
                      if (!password) return Promise.resolve();
                      return value === password
                        ? Promise.resolve()
                        : Promise.reject(new Error('Passwords are not matching!'));
                    }
                  })
                ]}
              >
                <Input.Password visibilityToggle={false} />
              </Form.Item>

              <Form.Item className="d-flex justify-content-center">
                <IsaButton className="mt-2" type="primary" htmlType="submit" size="large">
                  Registruj se
                </IsaButton>
              </Form.Item>
            </Form>
          </>
        )}
      </div>
    </div>
  );
};
