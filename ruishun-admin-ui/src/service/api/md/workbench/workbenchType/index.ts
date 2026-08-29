import { request } from '~/src/service/request';

// 获取分页列表
export const getWorkbenchTypeList = (params: any) => {
  return request.get('/md/mdWorkbenchType/list', { params });
};
// 获取所有列表
export const getWorkbenchTypeListAll = () => {
  return request.get('/md/mdWorkbenchType/selectall');
};
// 通过ID查询单条数据
export const queryWorkbenchTypeById = (params: any) => {
  return request.get(`/md/mdWorkbenchType/${params.id}`);
};

// 删除产品
export const deleteWorkbenchType = (params: any) => {
  return request.delete(`/md/mdWorkbenchType/${params.id}`);
};

// 新增产品
export const addWorkbenchType = (data: any) => {
  return request.post(`/md/mdWorkbenchType`, data);
};

// 编辑产品
export const editWorkbenchType = (data: any) => {
  return request.put(`/md/mdWorkbenchType`, data);
};
