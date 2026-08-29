import { request } from '~/src/service/request';

// 获取清单列表
export const getItemTypeList = (params: any) => {
  return request.get('/eq/eqItemType/list', { params });
};
// 通过ID查询单条数据
export const queryItemTypeAll = (params: any) => {
  return request.get('/eq/eqItemType/selectall', { params });
};

// 删除产品
export const deleteItemType = (params: any) => {
  return request.delete(`/eq/eqItemType/${params.id}`);
};

// 新增产品
export const addItemType = (data: any) => {
  return request.post(`/eq/eqItemType`, data);
};

// 编辑产品
export const editItemType = (data: any) => {
  return request.put(`/eq/eqItemType`, data);
};
