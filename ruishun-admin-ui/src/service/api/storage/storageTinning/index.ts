import { request } from '~/src/service/request';

// 获取库存记录
export const getProductStatistics = (params: any) => {
  return request.get(`/wms/wmsProductInout/list2`, { params });
};
