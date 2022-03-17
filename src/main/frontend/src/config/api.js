import {BASE_URL} from './index';

let prefix = `${BASE_URL.baseUrl}`;
const apis = {
  search: `${prefix}/plc/search`,
  export: `${prefix}/plc/export`,
  confirm: `${prefix}/secure/confirm`,
  configs: `${prefix}/plc/configs`,
  secureClear: `${prefix}/secure/clear`,
  secureConfig: `${prefix}/secure/config`,
  secureConfigs: `${prefix}/secure/configs`,
  secureLogin: `${prefix}/secure/login`,
  secureSignal: `${prefix}/plc/signal`,
  test: `${prefix}/plc/data`,
  count: `${prefix}/plc/count`,
  reinspect: `${prefix}/secure/reinspect`,
  login: `${prefix}/login`,
  logout: `${prefix}/logout`,
  userChange: `${prefix}/user/update`,
  userList: `${prefix}/user/all`,
  roleList: `${prefix}/user/roles`,
  deleteUser: `${prefix}/user/remove`,
  getUsers: `${prefix}/plc/users`,
  addRole: `${prefix}/user/role`,
  deleteRole: `${prefix}/user/role`
}

export default apis;