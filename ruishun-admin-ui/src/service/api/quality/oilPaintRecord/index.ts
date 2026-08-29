import { request } from '~/src/service/request';
// 获取日常送检计划列表
export const geQcPaintRecordList = (params: any) => {
  return request.get(`/qc/QcPaintRecord/list`, { params });
};
