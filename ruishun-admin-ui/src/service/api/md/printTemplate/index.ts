import { request } from '@/service/request';

// 获取模板列表
export function getPrintTemplateList(params: any) {
  return request.get('/md/printTemplate/list', { params });
}

// 获取模板信息
export function getPrintTemplateInfo(id: number | string) {
  return request.get(`/md/printTemplate/${id}`);
}

// 删除模板
export function deletePrintTemplate(id: number | string) {
  return request.delete(`/md/printTemplate/${id}`);
}

// 修改模板
export function updatePrintTemplate(data: any) {
  return request.put('/md/printTemplate', data);
}

// 复制模板
export function copyPrintTemplate(data: any) {
  return request.post('/md/printTemplate/copy', data);
}

// 添加模板
export function addPrintTemplate(data: object) {
  return request.post('/md/printTemplate', data);
}
