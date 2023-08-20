import { AxiosError } from 'axios';
import { useState } from 'react';
import { Form, Input } from 'antd';

import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { forgotPassword } from 'app/service/auth.service';

type ForgotPasswordForm = {
  email: string;
};

export const ForgotPasswordPage = () => {
  const [submitted, setSubmitted] = useState(false);
  const notifications = useNotifications();
  const { activateTransparentLoader, deactivateLoader } = useLoader();

  const handleOnFinish = ({ email }: ForgotPasswordForm) => {
    activateTransparentLoader();
    forgotPassword(email)
      .then(() => {
        setSubmitted(true);
      })
      .catch((error: AxiosError) => {
        if (error?.response?.status === 500) {
          notifications.error({ message: 'Korisnik sa navedenom email adresom ne postoji!' });
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
          <>
            <h2 className="h2 text-center">Zahtev uspešno poslat!</h2>
            <h4 className="h4 text-center mt-4">
              Na vašu email adresu poslati su podaci sa instrukcijama kako da resetujete lozinku.
            </h4>
          </>
        ) : (
          <>
            <h4 className="h4 text-center mb-4">Zaboravljena lozinka:</h4>
            <Form onFinish={handleOnFinish} autoComplete="off" validateTrigger="onSubmit">
              <Form.Item<ForgotPasswordForm>
                labelCol={{ span: 6 }}
                wrapperCol={{ span: 16 }}
                label="Email adresa"
                name="email"
                rules={[{ required: true, type: 'email', message: 'Unesite ispravnu email adresu!' }]}
              >
                <Input />
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
