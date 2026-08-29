import { request } from '~/src/service/request';

// 获取工序列表
export function getTechnologyList(params: any) {
  return request.get('/mes/mesWorkstage/list', { params });
}

// 获取所有工序
export function getAllTechnologyList() {
  return request.get('/mes/mesWorkstage/selectall');
}

// 添加工序
export function fetchAddTechnology(data: any) {
  return request.post('/mes/mesWorkstage', data);
}
// 编辑工序
export function fetchEditTechnology(data: any) {
  return request.put('/mes/mesWorkstage', data);
}

// 删除工序
export function fetchDeleteTechnology(id: number) {
  return request.delete(`/mes/mesWorkstage/${id}`);
}
