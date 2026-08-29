import { request } from '~/src/service/request';
// 获取报工记录
export const getOutputLog = (params: any) => {
  return request.get(`/mes/mesOutputLog/list`, { params });
};
