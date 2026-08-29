import { request } from '~/src/service/request';

// 获取清单列表
export const getGroupList = (params: any) => {
  return request.get('/eq/eqItemGroup/list', { params });
};
// 通过ID查询单条数据
export const queryGroupById = (params: any) => {
  return request.get(`/eq/eqItemGroup/selectall/${params.id}`);
};

// 删除产品
export const deleteGroup = (params: any) => {
  return request.delete(`/eq/eqItemGroup/${params.id}`);
};

// 新增产品
export const addGroup = (data: any) => {
  return request.post(`/eq/eqItemGroup`, data);
};

// 编辑产品
export const editGroup = (data: any) => {
  return request.put(`/eq/eqItemGroup`, data);
};
