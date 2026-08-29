// import { addDateRange } from '@/utils';
import { request } from '@/service/request';

// 获取模具采购列表
export function getPurchaseList(params: object) {
  return request.get('/mold/mold/moldPurchase/list', { params });
}

// 添加模具采购
export function addPurchase(data: object) {
  return request.post('/mold/mold/moldPurchase', data);
}

// 添加模具出库
export function deletePurchase(data: object) {
  return request.post('/mold/mold/moldPurchase', data);
}

// 添加模具出库
export function updatePurchase(data: object) {
  return request.put('/mold/mold/moldPurchase', data);
}

// 添加模具出库
export function updatePurchaseOrderStatus(data: object) {
  return request.put('/mold/mold/moldPurchase', data);
}

// 添加单个采购单号多条信息
export function addPurchaseInsertBatch(data: object) {
  return request.post('/mold/mold/moldPurchase/insertBatch', data);
}
