import { request } from '~/src/service/request';
// 获取投料记录
export const getItputLog = (params: any) => {
  return request.get(`/mes/mesInputLog/list`, { params });
};
