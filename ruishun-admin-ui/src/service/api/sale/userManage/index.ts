import { request } from '~/src/service/request';

// 获取用户列表信息
export const getUserList = (params: any) => {
  return request.get('/md/mdClient/list', { params });
};

// 新增用户信息
export const addUser = (data: any) => {
  return request.post('/md/mdClient', data);
};

// 删除用户信息
export const deleteUser = (id: number) => {
  return request.delete(`/md/mdClient/${id}`);
};

// 编辑客户信息
export const editUserFach = (data: any) => {
  return request.put('/md/mdClient', data);
};

// 获取全部的客户
export const getAllUser = () => {
  return request.get('/md/mdClient/selectall');
};
