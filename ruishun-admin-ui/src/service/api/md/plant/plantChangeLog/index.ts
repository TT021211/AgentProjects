import { request } from '~/src/service/request';

// 获取油漆更换记录
export const getPlantChangeLogList = (params: any) => {
  return request.get('/md/mdPlantChangeLog/list', { params });
};
// 通过ID查询单条数据
export const queryPlantChangeLogById = (params: any) => {
  return request.get(`/md/mdPlantChangeLog/selectall/${params.id}`);
};

// 删除更换记录
export const deletePlantChangeLog = (params: any) => {
  return request.delete(`/md/mdPlantChangeLog/${params.id}`);
};

// 新增更换记录
export const addPlantChangeLog = (data: any) => {
  return request.post(`/md/mdPlantChangeLog`, data);
};

// 编辑更换记录
export const editPlantChangeLog = (data: any) => {
  return request.put(`/md/mdPlantChangeLog`, data);
};
