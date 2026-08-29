import { request } from '~/src/service/request';

// 获取出库单记录
export const getboundList = (params: any) => {
  return request.get(`/wms/wmsOutboundOrder/list`, { params });
};
// 出库或者作废
export const editBound = (params: any) => {
  return request.post(`/wms/wmsOutboundOrder/outboundOrCancel`, params);
};
// 删除出库单
export const delBound = (id: any) => {
  return request.delete(`/wms/wmsOutboundOrder/${id} `);
};
