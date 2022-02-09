import ConfigPage from '@/pages/config-page';
import FilterPage from '@/pages/filter-page';
import TestPage from '@/pages/test-page';
import FilterAscPage from '@/pages/filter-asc-page';

const routes = {
  ASC: [
    {
      path: "/",
      component: FilterAscPage,
    },
    {
      path: '/config',
      component: ConfigPage
    },
    {
      path: '/test',
      component: TestPage
    }
  ],
  EXAM: [
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
}  

export default routes[process.env.REACT_APP_NAME];