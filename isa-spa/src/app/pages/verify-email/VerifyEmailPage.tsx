import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { IsaButton } from 'app/components/isa-button/IsaButton';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { verifyEmail } from 'app/service/auth.service';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

export const VerifyEmailPage = () => {
  const navigate = useNavigate();
  const { token } = useParams();

  const [verified, setVerified] = useState(false);
  const [message, setMessage] = useState('Validacija u toku...');

  const handleLoginClicked = () => navigate('/login');

  useEffect(() => {
    if (!token || verified) return;
    verifyEmail(token)
      .then(() => {
        setVerified(true);
        setMessage('Email uspešno verifikovan');
      })
      .catch(() => setMessage('Neispravan ili nevažeći token!'));
  }, [token, verified, setMessage]);

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column justify-content-center align-items-center flex-grow-1">
        <h4 className="h4 text-center">{message}</h4>
        {verified && (
          <IsaButton className="mt-4" type="primary" size="large" onClick={handleLoginClicked}>
            Prijavi se
          </IsaButton>
        )}
      </div>
    </div>
  );
};
