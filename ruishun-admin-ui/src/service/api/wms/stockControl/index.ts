import { request } from '@/service/request';

// 获取产品信息
export function getStorageManageList(params: any) {
  return request.get('wms/wms/stock/list', { params });
}

export function update(params: any) {
  return request.post('wms/wms/stock/editSave', params);
}
// 库存管理新增
export function addRealStock(params: any) {
  return request.post('wms/wms/stock/addRealtimeStock2', params);
}
