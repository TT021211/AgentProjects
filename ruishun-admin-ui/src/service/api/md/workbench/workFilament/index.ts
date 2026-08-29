import { request } from '~/src/service/request';

// 获取列表
export const getWorkbenchEnamellingList = (params: any) => {
  return request.get('/md/mdWorkbenchDoublewire/list', { params });
};
export const selectAllWorkbenchEnamellingList = (params: any) => {
  return request.get('/md/mdWorkbenchDoublewire/selectAll', { params });
};
// 通过ID查询单条数据
export const queryWorkbenchEnamellingById = (params: any) => {
  return request.get(`/md/mdWorkbenchDoublewire/${params.id}`);
};

// 删除产品
export const deleteWorkbenchEnamelling = (params: any) => {
  return request.delete(`/md/mdWorkbenchDoublewire/${params.id}`);
};

// 新增产品
export const addWorkbenchEnamelling = (data: any) => {
  return request.post(`/md/mdWorkbenchDoublewire`, data);
};

// 编辑
export const editWorkbenchEnamelling = (data: any) => {
  return request.put(`/md/mdWorkbenchDoublewire`, data);
};
// 启用禁用
export const updateIsEnable = (data: any) => {
  return request.put(`/md/mdWorkbenchDoublewire/updateEnable`, data);
};
