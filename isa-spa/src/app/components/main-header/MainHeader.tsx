import React from 'react';
import { useNavigate } from 'react-router-dom';

import './MainHeader.scss';

interface Props {
  children?: string | React.JSX.Element | React.JSX.Element[];
}

export const MainHeader = ({ children }: Props) => {
  const navigate = useNavigate();

  const handleHomeClicked = () => navigate('/');

  return (
    <div className="d-flex justify-content-between">
      <h1 className="h1 main-header-logo" onClick={handleHomeClicked}>
        ISA Uber
      </h1>
      <div className="d-flex align-items-center">{children}</div>
    </div>
  );
};
