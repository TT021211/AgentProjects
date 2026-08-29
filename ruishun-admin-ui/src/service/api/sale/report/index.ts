import { request } from '~/src/service/request';

// 获取价格管理列表
export const getSaleReportList = (params: any) => {
  return request.get('sale/sale/saleReportManagement/list', { params });
};

// 新增价格记录
export const addSaleReportList = (params: any) => {
  return request.post('/sale/salesaleReportManagement', params);
};

// 修改价格记录
export const updateSaleReportList = (params: any) => {
  return request.put('/sale/salesaleReportManagement', params);
};

// 删除价格记录
export const delSaleReportList = (params: any) => {
  return request.delete(`/sale/salesaleReportManagement/${params.id}`);
};
