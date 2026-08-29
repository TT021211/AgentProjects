import { request } from '@/service/request';

// 获取单位列表
export function getUnitList(params: any) {
  return request.get('/md/mdUnitMeasure/list', { params });
}
// 获取全部的单位
export function getAllUnitList() {
  return request.get('/md/mdUnitMeasure/selectall');
}

// 删除单位
export function deleteUnit(params: any) {
  return request.delete(`/md/mdUnitMeasure/${params}`);
}
// 新增单位
export function addUnit(data: any) {
  return request.post('/md/mdUnitMeasure', data);
}

// 获取全部数据
export function getAllUnit() {
  return request.get('/md/mdUnitMeasure/selectall');
}

// 编辑单位
export function editUnit(data: any) {
  return request.put('/md/mdUnitMeasure', data);
}
