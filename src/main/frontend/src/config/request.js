import axios from "axios";
import storage from 'good-storage';
import Loading from '@/components/loading';
import { message } from "antd";
import Login from '@/components/login';
let loadingCount = 0;

function addLoading() {
	Loading.show();
	loadingCount += 1;
}

function inputPassword() {
  Login.show();
}

function isCloseLoading() {
	loadingCount -= 1;
	if (loadingCount <= 0) Loading.hidden();
}

var service = axios.create({
  timeout:60000,
  withCredentials: true
})

let requestList = [];
let cancelToken = axios.CancelToken;
service.interceptors.request.use(
  config => {
    if(config.withCredentials){
      addLoading();
    }
    const {uploadOrDownload} = config;//download or upload dont need timeout
    if(uploadOrDownload) {
      config.timeout = 0;
    }
    let requestFlag = JSON.stringify(config.url) + JSON.stringify(config.data) + '&' + config.method;
    if (!!config.cancelToken || !requestList.includes(requestFlag)) {
      requestList.push(requestFlag);
    } else {
      (config.url).indexOf('vendor/all')<0 ? 
        config.cancelToken = new cancelToken((cancel) => cancel()) : 
        config.cancelToken='';
    }
    if (config.method === 'get') {
      const date = new Date().getTime();
      if (config.url.indexOf('?') === -1) {
        config.url =`${config.url}?t=${date}`;
      } else {
        config.url = `${config.url}&t=${date}`;
      }
    } 
  
    return config;
  },
  error => {
    return Promise.reject(error);
  }
)

service.interceptors.response.use(
  response => {
    isCloseLoading();
    let requestFlag = JSON.stringify(response.config.url) + JSON.stringify(response.config.data) + '&' + response.config.method;
    requestList.splice(requestList.findIndex(item => item === requestFlag), 1);
    const {config,data} = response;
    const {code,msg}=data;
    if (Object.prototype.toString.call(data) === "[object Blob]") {
      return response;
    }
    if(code&&code!==200){
      message.error(msg);
      return Promise.reject(response.data);
    }else{
      if(config.uploadOrDownload){
        return response;
      }
      return response.data;
    }
  },
  error => {
    loadingCount = 0;
    Loading.hidden();
    requestList = [];
    // eslint-disable-next-line no-mixed-operators
    const status = error.response&&error.response.status|| error.status;
    const data =  error.response&&error.response.data||{};
    const { debugMessage } = data;
    if(status === 401){
      localStorage.clear();
      inputPassword();
    }else if (status === 403) {  
      message.error('无权限操作！');  
    }else{
      error.response&& message.error(debugMessage||'error')
    }
    return Promise.reject(error);
  }
)


export default service;