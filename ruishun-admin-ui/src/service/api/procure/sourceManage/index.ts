import { request } from '~/src/service/request';

// 获取货源列表
export const getProcureSourceList = (params: any) => {
  return request.get('/procure/procure/procureSource/list', { params });
};
// 通过ID查询单条货源数据
export const queryProcureSourceById = (params: any) => {
  return request.get(`/procure/procure/procureSource/${params.id}`);
};

// 删除货源记录
export const deleteSourceOrder = (params: any) => {
  return request.delete(`/procure/procure/procureSource/${params.id}`);
};

// 新增货源记录
export const addSourceOrder = (data: any) => {
  return request.post(`/procure/procure/procureSource`, data);
};

// 编辑货源记录
export const editSourceOrder = (data: any) => {
  return request.put(`/procure/procure/procureSource`, data);
};
