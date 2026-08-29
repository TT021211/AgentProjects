import { request } from '~/src/service/request';

// 获取产品列表
export const getProductList = (params: any) => {
  return request.get('/md/mdProduct/list', { params });
};
// 获取全部的列表
export const getAllProductList = () => {
  return request.get('/md/mdProduct/selectall');
};

// 删除产品
export const deleteProduct = (params: any) => {
  return request.delete(`/md/mdProduct/${params.id}`);
};

// 新增产品
export const addProduct = (data: any) => {
  return request.post(`/md/mdProduct`, data);
};

// 编辑产品
export const editProduct = (data: any) => {
  return request.put(`/md/mdProduct`, data);
};

// 获取全部的产品列表
export const getAllProduct = () => {
  return request.get('/md/mdProduct/selectall');
};

// 导入产品列表
export function addProductUpload(data: any, config: any) {
  return request.post('/md/mdProduct/upload', data, config);
}

// 根据产品型号查询
export const getProductByProductSize = (data: any) => {
  return request.get(`/md/mdProduct/getProductListByProductSize${data}`);
};
