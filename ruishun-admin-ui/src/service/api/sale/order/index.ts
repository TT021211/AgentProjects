import { request } from '~/src/service/request';

// 获取列表
export const getSaleOrderList = (params: any) => {
  return request.get('/sale/sale/SaleOrder/list', { params });
};
// 通过ID查询单条数据
export const querySaleOrderById = (params: any) => {
  return request.get(`/sale/sale/SaleOrder/${params.id}`);
};

// 删除产品
export const deleteSaleOrder = (params: any) => {
  return request.delete(`/sale/sale/SaleOrder/${params.id}`);
};

// 新增产品
export const addSaleOrder = (data: any) => {
  return request.post(`/sale/sale/SaleOrder`, data);
};

// 编辑产品
export const editSaleOrder = (data: any) => {
  return request.put(`/sale/sale/SaleOrder`, data);
};
