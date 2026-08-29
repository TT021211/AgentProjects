import { request } from '~/src/service/request';

// 获取清单列表
export const getItemList = (params: any) => {
  return request.get('/eq/eqItem/list', { params });
};
// 通过ID查询单条数据
export const queryItemById = (params: any) => {
  return request.get(`/eq/eqItem/selectall/${params.id}`);
};

// 删除产品
export const deleteItem = (params: any) => {
  return request.delete(`/eq/eqItem/${params.id}`);
};

// 新增产品
export const addItem = (data: any) => {
  return request.post(`/eq/eqItem`, data);
};

// 编辑产品
export const editItem = (data: any) => {
  return request.put(`/eq/eqItem`, data);
};
