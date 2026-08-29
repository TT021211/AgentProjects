import { request } from '~/src/service/request';

// 获取内控标准
export function getInternalControlStandard() {
  return request.get('/mes/mesProcessStandard/selectAll/0');
}

// 获取国家标准
export function getNationalStandard() {
  return request.get('/mes/mesProcessStandard/selectAll/1');
}

// 获取客户标准
export function getClientStandard() {
  return request.get('/mes/mesProcessStandard/selectAll/2');
}
// 上传内控标准
export function uploadInternalControlStandard(data: any, config: any) {
  return request.post('/mes/mesProcessStandard/import/0', data, config);
}
// 上传国家标准
export function uploadNationalStandard(data: any, config: any) {
  return request.post('/mes/mesProcessStandard/import/1', data, config);
}
// 上传客户标准
export function uploadClientStandard(data: any, config: any) {
  return request.post('/mes/mesProcessStandard/import/2', data, config);
}
