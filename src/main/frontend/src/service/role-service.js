import apis from '@/config/api';
import http from '@/config/request';

export const roleList = () => {
  return http.get(`${apis.roleList}`);
}

export const addRole = (params) => {
  return http.post(`${apis.addRole}`, params);
}

export const deleteRole = (params) => {
  return http.delete(`${apis.deleteRole}`,{"data": params});
}
  