import { request } from '~/src/service/request';

// 获取油漆型号列表
export const getPlantType = (params: any) => {
  return request.get('/md/mdPlantType/list', { params });
};
// 通过ID查询单条数据
export const getPlantTypeAll = (params: any) => {
  return request.get('/md/mdPlantType/selectall', { params });
};

// 删除油漆型号
export const deletePlantType = (params: any) => {
  return request.delete(`/md/mdPlantType/${params.id}`);
};

// 新增油漆型号
export const addPlantType = (data: any) => {
  return request.post(`/md/mdPlantType`, data);
};

// 编辑油漆型号
export const editPlantType = (data: any) => {
  return request.put(`/md/mdPlantType`, data);
};
