import { request } from '~/src/service/request';

// 获取列表
export const getIngredientsManufacturerList = (params: any) => {
  return request.get('/md/mdIngredientsManufacturer/list', { params });
};
// 查询列表
export const queryIngredientsManufacturer = () => {
  return request.get('/md/mdIngredientsManufacturer/selectAll');
};

// 删除产品
export const deleteIngredientsManufacturer = (params: any) => {
  return request.delete(`/md/mdIngredientsManufacturer/${params.id}`);
};

// 新增产品
export const addIngredientsManufacturer = (data: any) => {
  return request.post(`/md/mdIngredientsManufacturer`, data);
};

// 编辑产品
export const editIngredientsManufacturer = (data: any) => {
  return request.put(`/md/mdIngredientsManufacturer`, data);
};
