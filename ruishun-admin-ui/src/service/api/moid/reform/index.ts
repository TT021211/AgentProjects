import { request } from '@/service/request';

// 获取模具改制列表
export function getMoldRestructuringList(params: object) {
  return request.get('/mold/mold/moldRestructuring/list', { params });
}

// 添加模具改制
export function addMoldRestructuringList(data: object) {
  return request.post('/mold/mold/moldRestructuring', data);
}
