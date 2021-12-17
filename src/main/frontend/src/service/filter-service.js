import apis from '@/config/api';
import http from '@/config/request';

export const searchList = (params) => {
  return http.post(`${apis.search}`,params,{withCredentials:false});
}
export const confirmItem = (params) => {
  return http.post(`${apis.confirm}`,params);
}
export const exportList = (params) => {
  return http.post(`${apis.export}`,params,{responseType: 'blob'});
}
export const configOption = () => {
  return http.post(`${apis.configs}`);
}