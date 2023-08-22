import { useNavigate, useParams } from 'react-router-dom';
import { useEffect } from 'react';
import { MainHeader } from 'app/components/main-header/MainHeader';
import { HeaderActions } from 'app/components/header-actions/HeaderActions';

export const RidePage = () => {
  const { rideId } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (!rideId) navigate('/');
  }, [navigate, rideId]);

  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column justify-content-center flex-grow-1">
        <h2 className="h2 mb-4 text-center">Vožnja:</h2>
      </div>
    </div>
  );
};
