import { request } from '~/src/service/request';

// 获取列表
export const getProcureOrderList = (params: any) => {
  return request.get('/procure/procure/procureOrder/list', { params });
};
// 通过ID查询单条数据
export const queryProcureOrderById = (params: any) => {
  return request.get(`/procure/procure/procureOrder/${params.id}`);
};

// 删除产品
export const deleteProcureOrder = (params: any) => {
  return request.delete(`/procure/procure/procureOrder/${params.id}`);
};

// 新增产品
export const addProcureOrder = (data: any) => {
  return request.post(`/procure/procure/procureOrder`, data);
};

// 编辑产品
export const editProcureOrder = (data: any) => {
  return request.put(`/procure/procure/procureOrder`, data);
};
