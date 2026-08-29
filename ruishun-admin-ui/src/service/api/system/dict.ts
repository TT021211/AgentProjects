import { request } from '../../request';
// 获取字典列表
export function getDictList(params: any) {
  return request.get('/system/dict/type/list', { params });
}

// 删除字典数据
export function delDict(dictId: any) {
  return request.delete(`/system/dict/type/${dictId}`);
}

// 新增字典数据
export function addDict(data: any) {
  return request.post('/system/dict/type', data);
}
// 编辑字典数据
export function editDict(data: any) {
  return request.put('/system/dict/type', data);
}
