import { request } from '~/src/service/request';

// 获取油漆清单列表
export const getPlantOutinLogList = (params: any) => {
  return request.get('/md/mdPlantOutinLog/list', { params });
};
// 通过ID查询单条数据
export const queryPlantOutinLogById = (params: any) => {
  return request.get(`/md/mdPlantOutinLog/selectall/${params.id}`);
};

// 删除产品
export const deletePlantOutinLog = (params: any) => {
  return request.delete(`/md/mdPlantOutinLog/${params.id}`);
};

// 新增产品
export const addPlantOutinLog = (data: any) => {
  return request.post(`/md/mdPlantOutinLog`, data);
};

// 编辑产品
export const editPlantOutinLog = (data: any) => {
  return request.put(`/md/mdPlantSupplier`, data);
};
