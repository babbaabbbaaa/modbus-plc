import apis from '@/config/api';
import http from '@/config/request';

export const testSearch = () => {
  return http.get(`${apis.test}`);
}