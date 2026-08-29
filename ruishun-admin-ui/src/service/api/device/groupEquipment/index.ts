import { request } from '~/src/service/request';

// 获取清单列表
export const getItemGroupDetailList = (params: any) => {
  return request.get('/eq/eqItemGroupDetail/list', { params });
};

// 删除产品
export const deleteItemGroupDetail = (data: any) => {
  return request.delete(`/eq/eqItemGroupDetail/${data}`);
};

// 新增产品
export const addItemGroupDetail = (data: any) => {
  return request.post(`/eq/eqItemGroupDetail`, data);
};

// 编辑产品
export const editItemGroupDetail = (data: any) => {
  return request.put(`/eq/eqItemGroupDetail`, data);
};

// 批量分配设备
export const addItemGroupDetailList = (ids: string, data: any) => {
  return request.post(`/eq/eqItemGroupDetail/allocated${ids}`, { ...data });
};
