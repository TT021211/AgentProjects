import { request } from '~/src/service/request';

// 获取班组列表
export const getWorkgroupList = (data: any) => {
  return request.get('/md/mdWorkgroup/list', { params: data });
};

// 获取不分页班组信息
export const getWorkgroupAll = () => {
  return request.get('/md/mdWorkgroup/selectall');
};

// 编辑班组
export const editWorkgroup = (data: any) => {
  return request.put('/md/mdWorkgroup', data);
};

// 新增班组
export const addWorkgroup = (data: any) => {
  return request.post('/md/mdWorkgroup', data);
};

// 删除班组
export const deleteWorkgroup = (id: number) => {
  return request.delete(`/md/mdWorkgroup/${id}`);
};

// 获取班组成员列表
export const getWorkgroupUserList = (data: any) => {
  return request.get('/md/mdWorkgroupMember/selectall', { params: data });
};

// 分页获取全部成员列表
export const getAllUserList = (data: any) => {
  return request.get('/md/mdWorkgroupMember/list', { params: data });
};

// 获取全部成员列表
export const getAllUserListAll = (data: any) => {
  return request.get('/md/mdWorkgroupMember/selectall', { params: data });
};

// 删除班组成员
export const deleteWorkgroupUser = (data: any) => {
  return request.delete(`/md/mdWorkgroupMember/${data.id}`);
};

export const getNotJoinUserList = (data: any) => {
  return request.delete('/md/mdWorkgroupMember', { data });
};

// 获取未加入班组的成员列表
export const getNotJoinWorkgroupUserList = (data: string, searchForm: any) => {
  return request.get(`/system/user/unallocated${data}`, { params: searchForm });
};

// 加入班组
export const memberAddWorkgroup = (ids: string, data: any) => {
  return request.post(`/md/mdWorkgroup/allocated${ids}`, { ...data });
};
