import { Spin } from 'antd';
import { useLoader } from 'app/contexts/loader/loader-context-provider';

import './IsaLoader.scss';

export const IsaLoader = () => {
  const { isActive } = useLoader();
  return (
    <>
      {isActive && (
        <div className="isa-loader-container d-flex justify-content-center align-items-center">
          <Spin size="large" />
        </div>
      )}
    </>
  );
};
