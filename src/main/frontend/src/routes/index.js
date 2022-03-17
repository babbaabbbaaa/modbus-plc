import ConfigPage from '@/pages/config-page';
import FilterPage from '@/pages/filter-page';
import TestPage from '@/pages/test-page';
import UserPage from '@/pages/user-page';
import RolePage from '@/pages/role-page';
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
    },
    {
      path: "/role",
      component: RolePage
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
    },
    {
      path: "/role",
      component: RolePage
    }
  ]
}  

export default routes[process.env.REACT_APP_NAME];