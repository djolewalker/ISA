import { Avatar, Dropdown, MenuProps } from 'antd';
import { ItemType } from 'antd/es/menu/hooks/useItems';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useDriverStatusContext } from 'app/contexts/driver-status/driver-status-provider';
import { useNavigate } from 'react-router-dom';

export const HeaderActions = () => {
  const { isAuthorized, user, logOut, hasAnyRole } = useAuthContext();
  const { active, loading, activate, deactivate } = useDriverStatusContext();
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

  return isAuthorized ? (
    <Dropdown menu={menu} trigger={['click']}>
      <div className="d-flex align-items-center cursor-pointer">
        <div className="mx-2">{(user?.firstname || '') + ' ' + (user?.lastname || '')}</div>
        <Avatar style={{ backgroundColor: '#f56a00', verticalAlign: 'middle' }} size="large">
          {(user?.firstname[0] || '') + (user?.lastname[0] || '')}
        </Avatar>
      </div>
    </Dropdown>
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
