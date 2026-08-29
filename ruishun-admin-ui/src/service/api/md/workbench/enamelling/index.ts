import { request } from '~/src/service/request';

// 获取列表
export const getWorkbenchEnamellingList = (params: any) => {
  return request.get('/md/mdWorkbenchTinning/list', { params });
};
export const selectAllWorkbenchEnamellingList = (params: any) => {
  return request.get('/md/mdWorkbenchTinning/selectAll', { params });
};
// 通过ID查询单条数据
export const queryWorkbenchEnamellingById = (params: any) => {
  return request.get(`/md/mdWorkbenchTinning/${params.id}`);
};

// 删除产品
export const deleteWorkbenchEnamelling = (params: any) => {
  return request.delete(`/md/mdWorkbenchTinning/${params.id}`);
};

// 新增产品
export const addWorkbenchEnamelling = (data: any) => {
  return request.post(`/md/mdWorkbenchTinning`, data);
};

// 编辑
export const editWorkbenchEnamelling = (data: any) => {
  return request.put(`/md/mdWorkbenchTinning`, data);
};
// 启用禁用
export const updateIsEnable = (data: any) => {
  return request.put(`/md/mdWorkbenchTinning/updateEnable`, data);
};
