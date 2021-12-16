import React, { useState, useEffect, useRef } from 'react';

import {secureSignal} from '@/service/config-service';

const Semaphore = (props) => {
  const timer= useRef();
  
  const [isLight, setIsLight] = useState('config-tip config-tishi');
  
  
  useEffect(() => {
    timer.current = setInterval(()=>{
      getSignal();
    },500)
    return componentWillUnmount;
  });

  const componentWillUnmount = () => {
    clearInterval(timer.current);
  }

  const getSignal = () => {
    secureSignal().then(res=>{
      if(res.code === 0){
        let configName = res.data=== 0? 'config-tip config-tishi': 'config-tip config-tishi-active'
        setIsLight(configName)
      }
    })
  }
  return (
    <div className={isLight}></div>
);
};

export default Semaphore;