import { request } from '~/src/service/request';

// 获取出货管理列表
export const getSaleReturnOrderList = (params: any) => {
  return request.get('/sale/sale/saleReturnOrder/list', { params });
};
