import { request } from '~/src/service/request';

// 获取收料列表
export const getProcureReceivingList = (params: any) => {
  return request.get('/procure/procure/procureReceiving/list', { params });
};
// 通过ID查询单条收料数据
export const queryProcureReceivingById = (params: any) => {
  return request.get(`/procure/procure/procureReceiving/${params.id}`);
};

// 删除收料记录
export const deleteReceivingOrder = (params: any) => {
  return request.delete(`/procure/procure/procureReceiving/${params.id}`);
};

// 新增收料记录
export const addReceivingOrder = (data: any) => {
  return request.post(`/procure/procure/procureReceiving`, data);
};

// 编辑收料记录
export const editReceivingOrder = (data: any) => {
  return request.put(`/procure/procure/procureReceiving`, data);
};
