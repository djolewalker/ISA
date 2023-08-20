import { Form, Input, InputNumber, Select, Switch } from 'antd';
import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useLoader } from 'app/contexts/loader/loader-context-provider';
import { useNotifications } from 'app/contexts/notifications/notifications-provider';
import { useAppDispatch, useAppSelector } from 'app/hooks/common';
import {
  fetchVehicleTypes,
  selectUser,
  selectVehicleTypes,
  setFetchDriverId,
  setFetchUserId,
  setUser
} from 'app/pages/admin/admin.slice';
import { UpdateUser, createDriver, updateUser } from 'app/service/user.service';
import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

type Props = {
  isCreate?: boolean;
  isDriverComponent?: boolean;
};

type FormData = {
  [key: string]: any;
};

const requiredRules = [{ required: true, message: 'Obavezno polje' }];

export const UserPage = ({ isCreate = false, isDriverComponent = false }: Props) => {
  const { userId } = useParams();
  const { user: authUser, setUser: setUserAuth } = useAuthContext();

  const notifications = useNotifications();
  const { activateTransparentLoader, deactivateLoader } = useLoader();
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const user = useAppSelector(selectUser);
  const vehicleTypes = useAppSelector(selectVehicleTypes);

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
    dispatch(fetchVehicleTypes());
  }, [dispatch]);

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
        const id = parseInt(userId);
        if (isDriverComponent) dispatch(setFetchDriverId(id));
        else dispatch(setFetchUserId(id));
      } catch {
        navigate('/');
      }
    }
  }, [activateTransparentLoader, dispatch, isCreate, isDriverComponent, navigate, userId]);

  const handleSaveUserData = (data: FormData) => {
    if (!userId) return;

    activateTransparentLoader();
    updateUser(parseInt(userId), data as UpdateUser)
      .then((user) => {
        notifications.success({ message: 'Podaci korisnika su ispešno izmenjeni!' });
        setUser(user);
        if (user.id === authUser?.id) setUserAuth(user);
      })
      .catch((error: AxiosError<{ message: string }>) => {
        notifications.open({ message: error?.response?.data?.message });
      })
      .finally(deactivateLoader);
  };

  const handleSaveDriverData = ({
    email,
    firstname,
    lastname,
    image,
    phone,
    address,
    driverLicense,
    vehicleModel,
    registrationNumber,
    numberOfSeats,
    babyFriendly,
    petFriendly,
    vehicleTypeId,
    username,
    password
  }: FormData) => {
    activateTransparentLoader();
    if (isCreate) {
      createDriver({
        email,
        firstname,
        lastname,
        image,
        phone,
        address,
        driverLicense,
        vehicle: {
          vehicleModel,
          registrationNumber,
          numberOfSeats,
          babyFriendly: Boolean(babyFriendly),
          petFriendly: Boolean(petFriendly),
          vehicleTypeId
        },
        username,
        password
      })
        .then((driver) => {
          notifications.success({ message: `Vozač ${driver.username} je uspešno kreiran!` });
          navigate('/admin/users');
        })
        .catch((error: AxiosError<{ message: string }>) => {
          if (error?.response?.status === 409) {
            notifications.open({ message: error?.response.data?.message });
          }
        })
        .finally(deactivateLoader);
    }
  };

  const handleSaveData = (data: FormData) => {
    if (isDriver) handleSaveDriverData(data);
    else handleSaveUserData(data);
  };

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
            <h3 className="h3 mb-4">Izmeni podatke korisnika: {user?.username}</h3>
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
              <Form.Item label="Email adresa" name="email" rules={requiredRules}>
                <Input />
              </Form.Item>

              {isDriver && isCreate && (
                <>
                  <Form.Item label="Korisničko ime" name="username" rules={requiredRules}>
                    <Input />
                  </Form.Item>

                  <Form.Item label="Lozinka" name="password" rules={requiredRules}>
                    <Input.Password visibilityToggle={false} />
                  </Form.Item>
                </>
              )}

              <Form.Item label="Ime" name="firstname" rules={requiredRules}>
                <Input />
              </Form.Item>

              <Form.Item label="Prezime" name="lastname" rules={requiredRules}>
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
                  <Form.Item label="Broj vozačke dozvole" name="driverLicense" rules={requiredRules}>
                    <Input />
                  </Form.Item>

                  <Form.Item label="Tip Vozila" name="vehicleTypeId" rules={requiredRules}>
                    <Select
                      options={vehicleTypes?.map((vechicleType) => ({
                        label: vechicleType.vehicleTypeName,
                        value: vechicleType.id
                      }))}
                    />
                  </Form.Item>

                  <Form.Item label="Model vozila" name="vehicleModel" rules={requiredRules}>
                    <Input />
                  </Form.Item>

                  <Form.Item label="Registarska oznaka vozila" name="registrationNumber" rules={requiredRules}>
                    <Input />
                  </Form.Item>

                  <Form.Item label="Broj sedišta" name="numberOfSeats" rules={requiredRules}>
                    <InputNumber />
                  </Form.Item>

                  <Form.Item valuePropName="checked" label="Mogućnost prevoza beba" name="babyFriendly">
                    <Switch />
                  </Form.Item>

                  <Form.Item valuePropName="checked" label="Mogućnost prevoza ljubimaca" name="petFriendly">
                    <Switch />
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
