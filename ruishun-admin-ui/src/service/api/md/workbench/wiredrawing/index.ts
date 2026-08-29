import { request } from '~/src/service/request';

// 获取列表
export const getWorkbenchWiredrawingList = (params: any) => {
  return request.get('/md/mdWorkbenchWiredrawing/list', { params });
};
// 通过ID查询单条数据
export const queryWorkbenchWiredrawingById = (params: any) => {
  return request.get(`/md/mdWorkbenchWiredrawing/${params.id}`);
};

// 删除产品
export const deleteWorkbenchWiredrawing = (params: any) => {
  return request.delete(`/md/mdWorkbenchWiredrawing/${params.id}`);
};

// 新增产品
export const addWorkbenchWiredrawing = (data: any) => {
  return request.post(`/md/mdWorkbenchWiredrawing`, data);
};

// 编辑
export const editWorkbenchWiredrawing = (data: any) => {
  return request.put(`/md/mdWorkbenchWiredrawing`, data);
};
// 启用禁用
export const updateIsEnable = (data: any) => {
  return request.put(`/md/mdWorkbenchWiredrawing/updateEnable`, data);
};
