import { request } from '~/src/service/request';
// 获取拉丝过程检记录列表
export const getSummartRecordList = (params: any) => {
  return request.get(`/qc/qcInspectionSummart/list`, { params });
};
