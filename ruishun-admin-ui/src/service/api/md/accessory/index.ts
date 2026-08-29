import { request } from '~/src/service/request';

// 获取列表
export const getAccessoryList = (params: any) => {
  return request.get('/md/mdAccessory/list', { params });
};
// 通过ID查询单条数据
export const queryAccessoryById = (params: any) => {
  return request.get(`/md/mdAccessory/${params.id}`);
};

// 删除产品
export const deleteAccessory = (params: any) => {
  return request.delete(`/md/mdAccessory/${params.id}`);
};

// 新增产品
export const addAccessory = (data: any) => {
  return request.post(`/md/mdAccessory`, data);
};

// 编辑产品
export const editAccessory = (data: any) => {
  return request.put(`/md/mdAccessory`, data);
};
