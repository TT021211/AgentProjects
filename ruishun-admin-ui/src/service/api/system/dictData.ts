import { request } from '../../request';

// 根据字典type来获取所该字典的资讯
export function fetchDictData(type: string) {
  return request.get(`/system/dict/data/type/${type}`);
}

// 根据字典code进行删除
export function deleteDictData(code: any) {
  return request.delete(`/system/dict/data/${code}`);
}

// 新增字典数据
export function addDictData(data: any) {
  return request.post('/system/dict/data', data);
}

// 修改字典数据
export function editDictData(data: any) {
  return request.put('/system/dict/data', data);
}

// 获取字典数据列表
export function getDictDataList(params: any) {
  return request.get('/system/dict/data/list', { params });
}
// 获取所有字典类型
export function getDictTypeList() {
  return request.get('/system/dict/type/optionselect');
}
