import { RouterProvider, createHashRouter } from 'react-router-dom';

import { useMapLayoutRoutes } from 'app/layouts/map-layout/useMapLayoutRoutes';

export const Routes = () => {
  const mapLayoutRoutes = useMapLayoutRoutes();

  const router = createHashRouter([...mapLayoutRoutes]);

  return <RouterProvider router={router} />;
};
