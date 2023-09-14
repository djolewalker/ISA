import { Form, Input } from 'antd';
import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { User } from 'app/model/User';
import { UpdateUser, updateUserProfile } from 'app/service/user.service';
import { AxiosError } from 'axios';

type UpdateUserForm = UpdateUser;

export const ProfilePage = () => {
  const notifications = useNotifications();
  const { user, setUser } = useAuthContext();
  const { activateTransparentLoader, deactivateLoader } = useLoader();

  const handleOnFinish = (data: UpdateUserForm) => {
    activateTransparentLoader();
    updateUserProfile(data)
      .then((user: User) => {
        notifications.success({ message: 'Podaci korisnika su ispešno izmenjeni!' });
        setUser(user);
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
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column justify-content-center flex-grow-1">
        <>
          <h2 className="h2 mb-4 text-center">Podaci profila:</h2>
          <Form
            className="w-75 mx-auto"
            layout="vertical"
            onFinish={handleOnFinish}
            autoComplete="off"
            validateTrigger="onSubmit"
            initialValues={user || {}}
          >
            <Form.Item<UpdateUserForm>
              label="Email adresa"
              name="email"
              rules={[{ required: true, type: 'email', message: 'Unesite ispravnu email adresu!' }]}
            >
              <Input />
            </Form.Item>

            <Form.Item<UpdateUserForm>
              label="Ime"
              name="firstname"
              rules={[{ required: true, message: 'Unesite ime!' }]}
            >
              <Input />
            </Form.Item>

            <Form.Item<UpdateUserForm>
              label="Prezime"
              name="lastname"
              rules={[{ required: true, message: 'Unesite prezime!' }]}
            >
              <Input />
            </Form.Item>

            <Form.Item<UpdateUserForm> label="Broj telefona" name="phone">
              <Input />
            </Form.Item>

            <Form.Item<UpdateUserForm> label="Adresa" name="address">
              <Input />
            </Form.Item>

            <Form.Item className="d-flex justify-content-center" shouldUpdate={true}>
              {({ isFieldsTouched }) => (
                <IsaButton disabled={!isFieldsTouched()} className="mt-2" type="primary" htmlType="submit" size="large">
                  Sačuvaj
                </IsaButton>
              )}
            </Form.Item>
          </Form>
        </>
      </div>
    </div>
  );
};
