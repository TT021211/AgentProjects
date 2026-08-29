import { request } from '@/service/request';

// 获取模具报废列表
export function getMoldScrappingList(params: object) {
  return request.get('/mold/mold/moldScrapping/list', { params });
}

// 添加模具报废
export function addMoldScrappingList(data: object) {
  return request.post('/mold/mold/moldScrapping', data);
}
