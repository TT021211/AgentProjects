import { request } from '~/src/service/request';

// 获取油漆清单列表
export const getPlantSupplierList = (params: any) => {
  return request.get('/md/mdPlantSupplier/list', { params });
};
// 通过ID查询单条数据
export const queryPlantSupplierById = (params: any) => {
  return request.get(`/md/mdPlantSupplier/selectall/${params.id}`);
};

// 删除产品
export const deletePlantSupplier = (params: any) => {
  return request.delete(`/md/mdPlantSupplier/${params.id}`);
};

// 新增产品
export const addPlantSupplier = (data: any) => {
  return request.post(`/md/mdPlantSupplier`, data);
};

// 编辑产品
export const editPlantSupplier = (data: any) => {
  return request.put(`/md/mdPlantSupplier`, data);
};
