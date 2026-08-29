import { request } from '~/src/service/request';

// 获取铜条列表
export function getCopperBarList(params: any) {
  return request.get('/md/mdCopperbarUnitweight/list', { params });
}

// 删除铜杆
export function deleteCopperBar(params: any) {
  return request.delete(`/md/mdCopperbarUnitweight/${params.id}`);
}

// 新增铜杆
export function addCopperBar(data: any) {
  return request.post(`/md/mdCopperbarUnitweight`, data);
}

// 修改铜杆
export function updateCopperBar(data: any) {
  return request.put(`/md/mdCopperbarUnitweight`, data);
}
