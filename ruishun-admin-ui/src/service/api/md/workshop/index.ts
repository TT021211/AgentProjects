import { request } from '~/src/service/request';

// 获取车间列表
export const getWorkshopList = (params: any) => {
  return request.get('/md/mdWorkshop/list', { params });
};
// 获取全部车间列表
export const getAllWorkshopList = () => {
  return request.get('/md/mdWorkshop/selectall');
};

// 删除车间信息
export const deleteWorkshop = (id: number) => {
  return request.delete(`/md/mdWorkshop/${id}`);
};

// 新增车间信息
export const addWorkshop = (data: any) => {
  return request.post('/md/mdWorkshop', data);
};

// 编辑车间信息
export const editWorkshop = (data: any) => {
  return request.put('/md/mdWorkshop', data);
};
