import { request } from '~/src/service/request';

// 获取退货管理列表
export const getSaleReturnList = (params: any) => {
  return request.get('/sale/salesaleReturnManagement/list', { params });
};

// 新增退货记录
export const addSaleReturnList = (params: any) => {
  return request.post('/sale/salesaleReturnManagement', params);
};

// 修改退货记录
export const updateSaleReturnList = (params: any) => {
  return request.put('/sale/salesaleReturnManagement', params);
};

// 删除退货记录
export const delSaleReturnList = (params: any) => {
  return request.delete(`/sale/salesaleReturnManagement/${params.id}`);
};
