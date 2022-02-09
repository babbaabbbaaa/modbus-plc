const href = `${window.location.protocol}//${window.location.host}`;
const BASE_URL_LIST={
    ASC:{
      baseUrl: href
    },
    EXAM:{
      baseUrl: href
    },
    QA:{
      baseUrl: href
    },
    DEV:{
      baseUrl: href
    },
    LOCAL:{
      baseUrl: 'http://192.168.17.37:8080/'
    }
}
export const BASE_URL=BASE_URL_LIST[process.env.REACT_APP_BASE_URL]
