import { request } from '~/src/service/request';

// 获取清单列表
export const getEqListLogList = (params: any) => {
  return request.get('/eq/eqListLog/list', { params });
};
// 通过ID查询单条数据
export const queryEqListLogAll = (data: any) => {
  return request.get('/eq/eqListLog/selectall', data);
};

// 删除产品
export const deleteEqListLog = (params: any) => {
  return request.delete(`/eq/eqListLog/${params.id}`);
};

// 新增产品
export const addEqListLog = (data: any) => {
  return request.post(`/eq/eqListLog`, data);
};

// 编辑产品
export const editEqListLog = (data: any) => {
  return request.put(`/eq/eqListLog`, data);
};
