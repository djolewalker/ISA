import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import { useMapLayoutRoutes } from 'app/layouts/map-layout/useMapLayoutRoutes';

export const Routes = () => {
  const mapLayoutRoutes = useMapLayoutRoutes();

  const router = createBrowserRouter([...mapLayoutRoutes]);

  return <RouterProvider router={router} />;
};
