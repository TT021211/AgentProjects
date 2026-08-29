import { request } from '~/src/service/request';

// 获取清单列表
export const getEquipmentTypeList = (params: any) => {
  return request.get('/eq/eqEquipmentType/list', { params });
};
// 通过ID查询单条数据
export const queryEquipmentTypeAll = (data: any) => {
  return request.get('/eq/eqEquipmentType/selectall', data);
};

// 删除
export const deleteEquipmentType = (params: any) => {
  return request.delete(`/eq/eqEquipmentType/${params.id}`);
};

// 新增
export const addEquipmentType = (data: any) => {
  return request.post(`/eq/eqEquipmentType`, data);
};

// 编辑
export const editEquipmentType = (data: any) => {
  return request.put(`/eq/eqEquipmentType`, data);
};
