// import { addDateRange } from '@/utils';
import { request } from '@/service/request';

// 获取模具供应商列表
export function getmoldSupplierList(params: object) {
  return request.get('/mold/mold/moldSupplier/list', { params });
}

// 添加供应商
export function addMoldSupplier(data: object) {
  return request.post('/mold/mold/moldSupplier', data);
}

// 编辑供应商
export function editMoldSupplier(data: object) {
  return request.put('/mold/mold/moldSupplier', data);
}

// 删除供应商
export function deleteMoldSupplier(id: number) {
  return request.delete(`/mold/mold/moldSupplier/${id}`);
}
