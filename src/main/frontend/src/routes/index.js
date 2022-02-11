import ConfigPage from '@/pages/config-page';
import FilterPage from '@/pages/filter-page';
import TestPage from '@/pages/test-page';
import UserPage from '@/pages/user-page';
import FilterAscPage from '@/pages/filter-asc-page';
import TestAscPage from '@/pages/test-asc-page';

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
      component: TestAscPage
    },
    {
      path: '/user',
      component: UserPage
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
    },
    {
      path: '/user',
      component: UserPage
    }
  ]
}  

export default routes[process.env.REACT_APP_NAME];