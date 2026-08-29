import { request } from '~/src/service/request';
// 获取搬运记录
export const getMesCarryLog = (params: any) => {
  return request.get(`/mes/mesCarryLog/list`, { params });
};
