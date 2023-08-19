import { Form, Input } from 'antd';
import { useForm } from 'antd/es/form/Form';
import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { useAppDispatch, useAppSelector } from 'app/hooks/common';
import { selectUser, setFetchUserId, setUser } from 'app/pages/admin/admin-slice';
import { UpdateUserRequest } from 'app/service/user-service';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

type Props = {
  isCreate?: boolean;
  isDriverComponent?: boolean;
};

type UserForm = UpdateUserRequest & { driverLicense?: string };

export const UserPage = ({ isCreate = false, isDriverComponent = false }: Props) => {
  const { userId } = useParams();

  const { activateTransparentLoader, deactivateLoader } = useLoader();
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const user = useAppSelector(selectUser);

  const [isDriver, setIsDriver] = useState(isDriverComponent);

  const showForm = isCreate || user;

  useEffect(() => {
    return () => {
      dispatch(setUser(null));
    };
  }, [dispatch, userId]);

  useEffect(() => {
    if (!isCreate && user) deactivateLoader();
  }, [deactivateLoader, isCreate, user]);

  useEffect(() => {
    const hasDriverRole = user?.roles?.includes('ROLE_DRIVER');
    setIsDriver(hasDriverRole || isDriverComponent);
  }, [user, isDriverComponent, setIsDriver]);

  useEffect(() => {
    if (isCreate && !isDriver) navigate('/');
  }, [navigate, isCreate, isDriver]);

  useEffect(() => {
    if (!isCreate) {
      try {
        if (!userId) return navigate('/');
        activateTransparentLoader();
        dispatch(setFetchUserId(parseInt(userId)));
      } catch {
        navigate('/');
      }
    }
  }, [activateTransparentLoader, dispatch, isCreate, navigate, userId]);

  const handleSaveData = (data: UserForm) => {};

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column flex-grow-1">
        <>
          {isCreate ? (
            <h3 className="h3 mb-4">Kreiraj vozača:</h3>
          ) : (
            <h3 className="h3 mb-4">
              Izmeni {isDriver ? 'vozača' : 'korisnika'}: {user?.username}
            </h3>
          )}
          {showForm && (
            <Form
              className="w-75 mx-auto"
              layout="vertical"
              onFinish={handleSaveData}
              autoComplete="off"
              validateTrigger="onSubmit"
              initialValues={user || {}}
            >
              <Form.Item
                label="Email adresa"
                name="email"
                rules={[{ required: true, type: 'email', message: 'Unesite ispravnu email adresu!' }]}
              >
                <Input />
              </Form.Item>

              <Form.Item label="Ime" name="firstname" rules={[{ required: true, message: 'Unesite ime!' }]}>
                <Input />
              </Form.Item>

              <Form.Item label="Prezime" name="lastname" rules={[{ required: true, message: 'Unesite prezime!' }]}>
                <Input />
              </Form.Item>

              <Form.Item label="Broj telefona" name="phone">
                <Input />
              </Form.Item>

              <Form.Item label="Adresa" name="address">
                <Input />
              </Form.Item>

              {isDriver && (
                <>
                  <Form.Item label="Broj vozačke dozvole" name="driverLicense">
                    <Input />
                  </Form.Item>
                </>
              )}

              <Form.Item className="d-flex justify-content-center" shouldUpdate={true}>
                {({ isFieldsTouched }) => (
                  <IsaButton
                    disabled={!isFieldsTouched()}
                    className="mt-2"
                    type="primary"
                    htmlType="submit"
                    size="large"
                  >
                    {isCreate ? 'Kreiraj' : 'Sačuvaj'}
                  </IsaButton>
                )}
              </Form.Item>
            </Form>
          )}
        </>
      </div>
    </div>
  );
};
