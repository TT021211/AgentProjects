import { request } from '~/src/service/request';

// 获取客户联系人
export const getClientAnalyze = () => {
  return request.get('/crm/crmClient/getClientAnalyze');
};
