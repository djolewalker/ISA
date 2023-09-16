import { Avatar, Badge, Dropdown, MenuProps } from 'antd';
import { ItemType } from 'antd/es/menu/hooks/useItems';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useDriverStatusContext } from 'app/contexts/driver-status/driver-status-provider';
import { useUserNotifications } from 'app/contexts/user-notifications/user.notfications-provider';
import { useNavigate } from 'react-router-dom';
import { BellTwoTone } from '@ant-design/icons';

export const HeaderActions = () => {
  const { isAuthorized, user, logOut, hasAnyRole } = useAuthContext();
  const { active, loading, activate, deactivate } = useDriverStatusContext();
  const { userNotifications } = useUserNotifications();
  const navigate = useNavigate();

  const handleRegisterClicked = () => navigate('/register');
  const handleLoginClicked = () => navigate('/login');

  const adminMenu: ItemType[] = [
    {
      type: 'divider'
    },
    {
      type: 'group',
      label: 'Administracija',
      children: [{ label: 'Korisnici', key: 'users', onClick: () => navigate('/admin/users') }]
    }
  ];

  const driverMenu: ItemType[] = [
    active
      ? {
          key: 'status',
          label: 'Postani neaktivan',
          disabled: loading,
          danger: true,
          onClick: deactivate
        }
      : {
          key: 'status',
          label: 'Postani aktivan',
          disabled: loading,
          onClick: activate
        }
  ];

  const menu: MenuProps = {
    items: [
      {
        label: 'Profil',
        key: 'profile',

        onClick: () => (hasAnyRole(['ROLE_DRIVER']) ? navigate('/driver/profile') : navigate('/profile'))
      },
      ...(hasAnyRole(['ROLE_ADMIN']) ? adminMenu : []),
      ...(hasAnyRole(['ROLE_DRIVER']) ? driverMenu : []),
      {
        type: 'divider'
      },
      {
        label: 'Odjavi se',
        key: 'logout',
        danger: true,
        onClick: logOut
      }
    ]
  };

  const notificationsMenu: MenuProps = {
    items: userNotifications.length
      ? userNotifications.map(({ id, description }) => ({
          key: id,
          label: (
            <div style={{ maxWidth: '350px' }} className="d-flex">
              {description}
            </div>
          )
        }))
      : [
          {
            key: 'empty',
            disabled: true,
            label: 'Nemate notifikacija!'
          }
        ]
  };

  return isAuthorized ? (
    <div className="d-flex align-items-center">
      <Dropdown
        className="mx-2 cursor-pointer"
        menu={notificationsMenu}
        trigger={['click']}
        placement="bottomCenter"
        arrow={{ pointAtCenter: true }}
      >
        <BellTwoTone style={{ fontSize: '32px' }} />
      </Dropdown>
      <Dropdown menu={menu} trigger={['click']}>
        <div className="d-flex align-items-center cursor-pointer">
          <div className="mx-2">{(user?.firstname || '') + ' ' + (user?.lastname || '')}</div>
          <Avatar style={{ backgroundColor: '#f56a00', verticalAlign: 'middle' }} size="large">
            {(user?.firstname[0] || '') + (user?.lastname[0] || '')}
          </Avatar>
        </div>
      </Dropdown>
    </div>
  ) : (
    <>
      <IsaButton className="mx-2" size="large" type="ghost" onClick={handleRegisterClicked}>
        Registracija
      </IsaButton>
      <IsaButton size="large" type="primary" onClick={handleLoginClicked}>
        Prijavi se
      </IsaButton>
    </>
  );
};
