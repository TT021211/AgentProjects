import { request } from '~/src/service/request';

// 获取清单列表
export const getEqListList = (params: any) => {
  return request.get('/eq/eqList/list', { params });
};
// 通过ID查询单条数据
export const queryEqListAll = (data: any) => {
  return request.get('/eq/eqList/selectall', data);
};

// 删除产品
export const deleteEqList = (params: any) => {
  return request.delete(`/eq/eqList/${params}`);
};

// 新增产品
export const addEqList = (data: any) => {
  return request.post(`/eq/eqList`, data);
};

// 编辑产品
export const editEqList = (data: any) => {
  return request.put(`/eq/eqList`, data);
};
