import React from 'react'
import {HashRouter as Router,Routes,Route} from 'react-router-dom';
import routes from './routes/index';
import Header from '@/components/header';

function App() {
  return (
      <Router>
        <Header/>
        <Routes>
          {routes.map((route,index) => {
            return <Route key={index} path={route.path} element={<route.component/>}/>
          })}
        </Routes>
      </Router>
  );
}

export default App;
