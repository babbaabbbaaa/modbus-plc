import apis from '@/config/api';
import http from '@/config/request';

export const secureConfig = (params) => {
  return http.post(`${apis.secureConfig}`,params);
}
export const secureConfigs = (params) => {
  return http.get(`${apis.secureConfigs}`,{params});
}
export const secureClear = (params) => {
  return http.post(`${apis.secureClear}`,params);
}
export const secureLogin = (params) => {
  return http.get(`${apis.secureLogin}?x-api-access-key=${params}`);
}
export const secureSignal = () => {
  return http.get(`${apis.secureSignal}`,{withCredentials:false});
}