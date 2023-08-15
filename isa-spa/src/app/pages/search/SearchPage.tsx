import { useNavigate } from 'react-router-dom';

import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';

export const SearchPage = () => {
  const navigate = useNavigate();

  const handleRegisterClicked = () => navigate('/register');
  const handleLoginClicked = () => navigate('/login');

  return (
    <div className="d-flex flex-column">
      <MainHeader>
        <IsaButton className="mx-2" size="large" type="ghost" onClick={handleRegisterClicked}>
          Registracija
        </IsaButton>
        <IsaButton size="large" type="primary" onClick={handleLoginClicked}>
          Prijavi se
        </IsaButton>
      </MainHeader>
      <div className="my-3"></div>
    </div>
  );
};
