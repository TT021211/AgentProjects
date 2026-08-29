import { request } from '~/src/service/request';

// 获取成品出入库记录
export const getFinishedList = (params: any) => {
  return request.get(`/wms/wmsProductInout/list`, { params });
};
// 删除成品出入库记录
export const delProductInout = (boxCode: any) => {
  return request.delete(`/wms/wmsProductInout//deleteByBoxCode/${boxCode}`);
};
