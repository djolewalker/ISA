import { Spin } from 'antd';
import { useLoader } from 'app/contexts/loader/loader-context-provider';

import './IsaLoader.scss';

export const IsaLoader = () => {
  const { isActive, isTransparent } = useLoader();
  return (
    <>
      {isActive && (
        <div
          className={
            'isa-loader-container d-flex justify-content-center align-items-center' +
            (isTransparent ? ' opacity-50' : '')
          }
        >
          <Spin size="large" />
        </div>
      )}
    </>
  );
};
