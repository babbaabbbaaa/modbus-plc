import apis from '@/config/api';
import http from '@/config/request';

export const login = (params) => {
  return http.post(`${apis.login}`,params);
}
export const logout = (params) => {
  return http.get(`${apis.logout}`);
}
