import { request } from '~/src/service/request';

// 获取列表
export const getWeighMachineList = (params: any) => {
  return request.get('/md/mdWorkbenchWeigh/list', { params });
};
// 获取列表
export const getWeighMachineSelectAll = (params: any) => {
  return request.get('/md/mdWorkbenchWeigh/list', { params });
};

// 通过ID查询单条数据
export const queryWeighMachineById = (params: any) => {
  return request.get(`/md/mdWorkbenchWeigh/${params.id}`);
};

// 删除产品
export const deleteWeighMachine = (params: any) => {
  return request.delete(`/md/mdWorkbenchWeigh/${params.id}`);
};

// 新增产品
export const addWeighMachine = (data: any) => {
  return request.post(`/md/mdWorkbenchWeigh`, data);
};

// 编辑
export const editWeighMachine = (data: any) => {
  return request.put(`/md/mdWorkbenchWeigh`, data);
};
// 启用禁用
export const updateIsEnable = (data: any) => {
  return request.put(`/md/mdWorkbenchWeigh/updateEnable`, data);
};
