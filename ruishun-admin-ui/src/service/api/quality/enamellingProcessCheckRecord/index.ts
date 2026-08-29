import { request } from '~/src/service/request';
// 获取漆包过程检记录列表
export const getQcEnamelProcessRecordList = (params: any) => {
  return request.get(`/qc/qcEnamelProcessRecord/list`, { params });
};
