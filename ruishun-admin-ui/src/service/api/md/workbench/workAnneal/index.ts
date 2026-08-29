import { request } from '~/src/service/request';

// 获取列表
export const getWorkbenchEnamellingList = (params: any) => {
  return request.get('/md/mdWorkbenchAnnealing/list', { params });
};
export const selectAllWorkbenchEnamellingList = (params: any) => {
  return request.get('/md/mdWorkbenchAnnealing/selectAll', { params });
};
// 通过ID查询单条数据
export const queryWorkbenchEnamellingById = (params: any) => {
  return request.get(`/md/mdWorkbenchAnnealing/${params.id}`);
};

// 删除产品
export const deleteWorkbenchEnamelling = (params: any) => {
  return request.delete(`/md/mdWorkbenchAnnealing/${params.id}`);
};

// 新增产品
export const addWorkbenchEnamelling = (data: any) => {
  return request.post(`/md/mdWorkbenchAnnealing`, data);
};

// 编辑
export const editWorkbenchEnamelling = (data: any) => {
  return request.put(`/md/mdWorkbenchAnnealing`, data);
};
// 启用禁用
export const updateIsEnable = (data: any) => {
  return request.put(`/md/mdWorkbenchAnnealing/updateEnable`, data);
};
