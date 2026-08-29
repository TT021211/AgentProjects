// import { addDateRange } from '@/utils';
import { request } from '@/service/request';

// 获取模具出库列表
export function getMoldOutboundList(params: object) {
  return request.get('/mold/mold/moldOutbound/list', { params });
}

export function getQueryByClassificationId(params: object) {
  return request.get('mold/mold/moldLedger/queryByClassificationId', { params });
}

// 添加模具出库
export function addMoldOutboundList(data: object) {
  return request.post('/mold/mold/moldOutbound', data);
}

// 确认模具出库
export function confirmMoldOutboundList(data: object) {
  return request.post('/mold/mold/moldOutbound/confirm', data);
}
