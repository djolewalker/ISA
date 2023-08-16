import { HeaderActions } from 'app/components/header-actions/HeaderActions';
import { MainHeader } from 'app/components/main-header/MainHeader';

export const SearchPage = () => {
  return (
    <div className="d-flex flex-column">
      <MainHeader>
        <HeaderActions />
      </MainHeader>
      <div className="my-3"></div>
    </div>
  );
};
