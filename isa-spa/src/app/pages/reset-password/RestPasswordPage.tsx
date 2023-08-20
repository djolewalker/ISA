import { Form, Input } from 'antd';
import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { resetPassword } from 'app/service/auth.service';
import { AxiosError } from 'axios';
import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

type ResetPasswordForm = {
  password: string;
  repeatPassword: string;
};

export const ResetPasswordPage = () => {
  const navigate = useNavigate();
  const { token } = useParams();
  const notifications = useNotifications();
  const { activateTransparentLoader, deactivateLoader } = useLoader();

  const [submitted, setSubmitted] = useState(false);

  const handleLoginClicked = () => navigate('/login');

  const handleOnFinish = ({ password }: ResetPasswordForm) => {
    if (!token) return;

    activateTransparentLoader();
    resetPassword(token, password)
      .then(() => {
        setSubmitted(true);
      })
      .catch((error: AxiosError) => {
        if (error?.response?.status === 400) {
          notifications.error({
            message: 'Nije moguće resetovai lozinku!',
            description: 'Nevažeći ili nepostojeći token!'
          });
        }
      })
      .finally(deactivateLoader);
  };

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column justify-content-center flex-grow-1">
        {submitted ? (
          <div className="d-flex flex-column align-items-center">
            <h2 className="h2 text-center">Lozinka je uspešno izmenjena!</h2>
            <IsaButton className="mt-4" type="primary" size="large" onClick={handleLoginClicked}>
              Prijavi se
            </IsaButton>
          </div>
        ) : (
          <>
            <h4 className="h4 text-center mb-4">Unesite novu lozinku:</h4>
            <Form
              labelCol={{ span: 6 }}
              wrapperCol={{ span: 16 }}
              onFinish={handleOnFinish}
              autoComplete="off"
              validateTrigger="onSubmit"
            >
              <Form.Item<ResetPasswordForm>
                label="Nova lozinka"
                name="password"
                rules={[{ required: true, message: 'Unesite novu lozinku!' }]}
              >
                <Input.Password visibilityToggle={false} />
              </Form.Item>

              <Form.Item<ResetPasswordForm>
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
                  Resetuj lozinku
                </IsaButton>
              </Form.Item>
            </Form>
          </>
        )}
      </div>
    </div>
  );
};
