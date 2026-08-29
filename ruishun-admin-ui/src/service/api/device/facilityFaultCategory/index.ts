import { request } from '~/src/service/request';

// 获取清单列表
export const getBreakdownTypeList = (params: any) => {
  return request.get('/eq/eqBreakdownType/list', { params });
};
// 通过ID查询单条数据
export const queryBreakdownTypeById = (params: any) => {
  return request.get(`/eq/eqBreakdownType/selectall/${params.id}`);
};

// 删除产品
export const deleteBreakdownType = (params: any) => {
  return request.delete(`/eq/eqBreakdownType/${params.id}`);
};

// 新增产品
export const addBreakdownType = (data: any) => {
  return request.post(`/eq/eqBreakdownType`, data);
};

// 编辑产品
export const editBreakdownType = (data: any) => {
  return request.put(`/eq/eqBreakdownType`, data);
};
