import apis from '@/config/api';
import http from '@/config/request';

export const userChange = (params) => {
  return http.post(`${apis.userChange}`,params);
}
export const userList = (params) => {
  return http.get(`${apis.userList}`);
}
export const roleList = (params) => {
    return http.get(`${apis.roleList}`);
  }
  