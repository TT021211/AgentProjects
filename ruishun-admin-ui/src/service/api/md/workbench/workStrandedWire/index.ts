import { request } from '~/src/service/request';

// 获取列表
export const getWorkbenchEnamellingList = (params: any) => {
  return request.get('/md/mdWorkbenchStrandedwire/list', { params });
};
export const selectAllWorkbenchEnamellingList = (params: any) => {
  return request.get('/md/mdWorkbenchStrandedwire/selectAll', { params });
};
// 通过ID查询单条数据
export const queryWorkbenchEnamellingById = (params: any) => {
  return request.get(`/md/mdWorkbenchStrandedwire/${params.id}`);
};

// 删除产品
export const deleteWorkbenchEnamelling = (params: any) => {
  return request.delete(`/md/mdWorkbenchStrandedwire/${params.id}`);
};

// 新增产品
export const addWorkbenchEnamelling = (data: any) => {
  return request.post(`/md/mdWorkbenchStrandedwire`, data);
};

// 编辑
export const editWorkbenchEnamelling = (data: any) => {
  return request.put(`/md/mdWorkbenchStrandedwire`, data);
};
// 启用禁用
export const updateIsEnable = (data: any) => {
  return request.put(`/md/mdWorkbenchStrandedwire/updateEnable`, data);
};
