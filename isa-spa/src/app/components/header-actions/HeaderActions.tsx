import { Avatar, Dropdown, MenuProps } from 'antd';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { useAuthContext } from 'app/contexts/auth/auth-context-provider';
import { useNavigate } from 'react-router-dom';

export const HeaderActions = () => {
  const { isAuthorized, user, logOut } = useAuthContext();
  const navigate = useNavigate();

  const handleRegisterClicked = () => navigate('/register');
  const handleLoginClicked = () => navigate('/login');

  const menu: MenuProps = {
    items: [
      {
        label: 'Odjavi se',
        key: '0',
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
