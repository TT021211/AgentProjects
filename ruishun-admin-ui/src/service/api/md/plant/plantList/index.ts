import { request } from '~/src/service/request';

// 获取油漆清单列表
export const getPlantList = (params: any) => {
  return request.get('/md/mdPlantList/list', { params });
};
// 通过ID查询单条数据
export const queryPlantById = (params: any) => {
  return request.get(`/md/mdPlantList/selectall/${params.id}`);
};

// 删除产品
export const deletePlantList = (params: any) => {
  return request.delete(`/md/mdPlantList/${params.id}`);
};

// 新增产品
export const addPlantList = (data: any) => {
  return request.post(`/md/mdPlantList`, data);
};

// 编辑产品
export const editPlantList = (data: any) => {
  return request.put(`/md/mdPlantList`, data);
};
