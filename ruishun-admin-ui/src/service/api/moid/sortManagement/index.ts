// import { addDateRange } from '@/utils';
import { request } from '@/service/request';

// 获取模具列表
export function getMoidList(params: object) {
  return request.get('/mold/mold/moldClassification/list', { params });
}

// 获取模具列表
export function getMoidList2(params: object) {
  return request.get('/mold/mold/moldClassification/list2', { params });
}

// 添加模具分类
export function addMoidClassify(data: object) {
  return request.post('/mold/mold/moldClassification', data);
}

// 编辑模具分类
export function editMoidClassify(data: object) {
  return request.put('/mold/mold/moldClassification', data);
}

// 删除模具分类
export function deleteMoidClassify(id: number) {
  return request.delete(`/mold/mold/moldClassification/${id}`);
}
