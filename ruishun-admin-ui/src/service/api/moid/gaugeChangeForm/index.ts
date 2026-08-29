import { request } from '@/service/request';

// 获取换规单列表
export function getMoldGaugeList(params: object) {
  return request.get('/mold/mold/moldGauge/list', { params });
}

// 添加换规单列表
export function addMoldGaugeList(data: object) {
  return request.post('/mold/mold/moldGauge', data);
}
