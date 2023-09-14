import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { MainHeader } from 'app/components/main-header/MainHeader';

export const DriverProfilePage = () => {
  return (
    <div className="d-flex flex-column flex-grow-1">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3 d-flex flex-column justify-content-center flex-grow-1">
        <>
          <h2 className="h2 mb-4 text-center">Podaci profila:</h2>
        </>
      </div>
    </div>
  );
};
