// import { addDateRange } from '@/utils';
import { request } from '@/service/request';

// 获取模具入库列表
export function getMoldmoldStorageList(params: object) {
  return request.get('/mold/mold/moldStorage/list', { params });
}

// 添加模具入库
export function addMoldmoldStorageList(data: object) {
  return request.post('/mold/mold/moldStorage', data);
}

// 确认模具入库
export function confirmMoldmoldStorageList(data: object) {
  return request.post('/mold/mold/moldStorage/confirm', data);
}
