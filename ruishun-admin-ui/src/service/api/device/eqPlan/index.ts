import { request } from '~/src/service/request';

// 获取清单列表
export const getEqPlanList = (params: any) => {
  return request.get('/eq/eqPlan/list', { params });
};
// 通过ID查询单条数据
export const queryEqPlanAll = (data: any) => {
  return request.get('/eq/eqPlan/selectall', data);
};

// 删除产品
export const deleteEqPlan = (params: any) => {
  return request.delete(`/eq/eqPlan/${params.id}`);
};

// 新增产品
export const addEqPlan = (data: any) => {
  return request.post(`/eq/eqPlan`, data);
};

// 编辑产品
export const editEqPlan = (data: any) => {
  return request.put(`/eq/eqPlan`, data);
};
