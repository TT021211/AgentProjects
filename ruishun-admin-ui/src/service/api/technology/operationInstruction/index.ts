import { request } from '@/service/request';

// 获取作业指导书
export function getOperationMethodList(params: object) {
  return request.get('/mes/mesOperationMethod/list', { params });
}

// 获取作业指导书
export function addOperationMethod(data: object) {
  return request.post('/mes/mesOperationMethod', data);
}

// 获取作业指导书
export function editOperationMethod(data: object) {
  return request.put('/mes/mesOperationMethod', data);
}

// 获取作业指导书
export function deleteOperationMethod(ids: any) {
  return request.delete(`/mes/mesOperationMethod/${ids}`);
}
