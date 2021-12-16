import ConfigPage from '@/pages/config-page';
import FilterPage from '@/pages/filter-page';
import TestPage from '@/pages/test-page';

const routes =[
  {
      path: "/",
      component: FilterPage,
  },
  {
      path: '/config',
      component: ConfigPage
  },
  {
    path: '/test',
    component: TestPage
  }
]
export default routes;