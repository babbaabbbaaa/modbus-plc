import React from 'react'
import {HashRouter as Router,Routes,Route} from 'react-router-dom';
import routes from './routes/index';
import Header from '@/components/header';
import { dispatchEventStroage } from '@/utils/index';

function App() {
  dispatchEventStroage();
  let userInfo = localStorage.getItem('userInfo')||'{}';
  userInfo = JSON.parse(userInfo);
  let roles = userInfo.roles||[];
  let routeList = routes;
  // if(roles.indexOf('ADMIN')<0){
  //   routeList = routes.filter(item => {
  //     if(item.path !== '/user' && item.path !== '/role'){
  //       return item;
  //     }
  //   })
  // }
  return (
    <Router>
      <Header/>
      <Routes>
        {routeList.map((route,index) => {
          return <Route key={index} path={route.path} element={<route.component/>}/>
        })}
      </Routes>
    </Router>
  );
}

export default App;
