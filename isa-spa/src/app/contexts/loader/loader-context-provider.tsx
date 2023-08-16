import { createContext, useCallback, useContext, useMemo, useState } from 'react';

type LoaderContextData = {
  isActive: boolean;
  isTransparent: boolean;
  activateLoader: () => void;
  activateTransparentLoader: () => void;
  deactivateLoader: () => void;
};

const LoaderContext = createContext<LoaderContextData>({} as LoaderContextData);

type LoaderProviderProps = {
  children: React.ReactNode;
};

const LoaderProvider = ({ children }: LoaderProviderProps) => {
  const [isActive, setIsActive] = useState(true);
  const [isTransparent, setIsTransparent] = useState(false);

  const activateLoader = useCallback(() => {
    setIsTransparent(false);
    setIsActive(true);
  }, [setIsActive, setIsTransparent]);

  const activateTransparentLoader = useCallback(() => {
    setIsTransparent(true);
    setIsActive(true);
  }, [setIsActive, setIsTransparent]);

  const deactivateLoader = useCallback(() => setIsActive(false), [setIsActive]);

  const contextValue = useMemo(
    () => ({ isActive, isTransparent, activateLoader, activateTransparentLoader, deactivateLoader }),
    [isActive, isTransparent, activateLoader, activateTransparentLoader, deactivateLoader]
  );

  return <LoaderContext.Provider value={contextValue}>{children}</LoaderContext.Provider>;
};

export const useLoader = () => useContext(LoaderContext);
export default LoaderProvider;
