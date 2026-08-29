import { request } from '~/src/service/request';

// 获取退料列表
export const getProcureMaterialList = (params: any) => {
  return request.get('/procure/procure/procureMaterialReturn/list', { params });
};
// 通过ID查询单条退料数据
export const queryProcureMaterialById = (params: any) => {
  return request.get(`/procure/procure/procureMaterialReturn/${params.id}`);
};

// 删除退料记录
export const deleteMaterialOrder = (params: any) => {
  return request.delete(`/procure/procure/procureMaterialReturn/${params.id}`);
};

// 新增退料记录
export const addMaterialOrder = (data: any) => {
  return request.post(`/procure/procure/procureMaterialReturn`, data);
};

// 编辑退料记录
export const editMaterialOrder = (data: any) => {
  return request.put(`/procure/procure/procureMaterialReturn`, data);
};
