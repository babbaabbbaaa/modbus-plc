import {BASE_URL} from './index';

let prefix = `${BASE_URL.baseUrl}`;
const apis = {
  search: `${prefix}/plc/search`,
  export: `${prefix}/plc/export`,
  confirm: `${prefix}/plc/confirm`,
  configs: `${prefix}/plc/configs`,
  secureClear: `${prefix}/secure/clear`,
  secureConfig: `${prefix}/secure/config`,
  secureConfigs: `${prefix}/secure/configs`,
  secureLogin: `${prefix}/secure/login`,
  secureSignal: `${prefix}/secure/signal`,
  test: `${prefix}/plc/test`
}

export default apis;