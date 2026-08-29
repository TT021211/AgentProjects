import { request } from '~/src/service/request';

// 获取分页列表
export const getEquipmentList = (params: any) => {
  return request.get('/eq/eqEquipment/list', { params });
};
// 获取所有列表
export const getEquipmentAll = () => {
  return request.get('/eq/eqEquipment/selectall');
};
// 获取所有未关联设备列表
export const getEquipmentAllNoRelevance = () => {
  return request.get('/eq/eqEquipment/selectAllNoRelevance');
};
// 通过ID查询单条数据
export const queryEquipmentById = (params: any) => {
  return request.get(`/eq/eqEquipment/${params}`);
};

// 删除产品
export const deleteEquipment = (params: any) => {
  return request.delete(`/eq/eqEquipment/${params.id}`);
};

// 新增产品
export const addEquipment = (data: any) => {
  return request.post(`/eq/eqEquipment`, data);
};

// 编辑产品
export const editEquipment = (data: any) => {
  return request.put(`/eq/eqEquipment`, data);
};
