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
  const { userNotifications, adminNotifications } = useUserNotifications();
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

  const nonAdminMenu = [
    {
      label: 'Istorija vožnji',
      key: 'history',
      onClick: () => navigate('/ride/history')
    },
    {
      label: 'Analitika',
      key: 'analytics',
      onClick: () => navigate('/analytics')
    }
  ];

  const menu: MenuProps = {
    items: [
      {
        label: 'Profil',
        key: 'profile',
        onClick: () => (hasAnyRole(['ROLE_DRIVER']) ? navigate('/driver/profile') : navigate('/profile'))
      },
      ...(hasAnyRole(['ROLE_ADMIN']) ? adminMenu : nonAdminMenu),
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

  const notifications = hasAnyRole(['ROLE_ADMIN']) ? adminNotifications : userNotifications;
  const notificationsMenu: MenuProps = {
    style: { maxHeight: '500px', overflowY: 'auto' },
    items: notifications.length
      ? notifications.map((notification) => ({
          key: notification.id,
          label: (
            <div style={{ maxWidth: '350px' }} className="d-flex flex-column">
              {notification.description}
              <div className="text-end text-black-50">
                {'activationTime' in notification && notification?.activationTime?.toLocaleString()}
              </div>
              <div className="text-end">
                {'creationTime' in notification && notification?.creationTime?.toLocaleString()}
              </div>
            </div>
          ),
          onClick: ({ key }) => {
            if (hasAnyRole(['ROLE_ADMIN'])) {
              const not = adminNotifications?.find((n) => n.id === parseInt(key));
              if (not) navigate(`/ride/${not.ride.id}`);
            }
          }
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
        placement="bottom"
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
