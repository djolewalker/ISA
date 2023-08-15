import { IsaLoader } from 'app/components/isa-loader/IsaLoader';
import { createContext, useCallback, useContext, useMemo, useState } from 'react';

type LoaderContextData = {
  isActive: boolean;
  activateLoader: () => void;
  deactivateLoader: () => void;
};

const LoaderContext = createContext<LoaderContextData>({} as LoaderContextData);

type LoaderProviderProps = {
  children: React.ReactNode;
};

const LoaderProvider = ({ children }: LoaderProviderProps) => {
  const [isActive, setIsActive] = useState(true);

  const activateLoader = useCallback(() => setIsActive(true), [setIsActive]);
  const deactivateLoader = useCallback(() => setIsActive(false), [setIsActive]);

  return (
    <LoaderContext.Provider value={{ isActive, activateLoader, deactivateLoader }}>{children}</LoaderContext.Provider>
  );
};

export const useLoader = () => useContext(LoaderContext);
export default LoaderProvider;
