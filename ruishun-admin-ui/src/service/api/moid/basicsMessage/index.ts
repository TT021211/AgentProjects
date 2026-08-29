import { request } from '@/service/request';

// 获取台账列表
export function getMoldLedgerList(params: object) {
  return request.get('/mold/mold/moldLedger/list', { params });
}

// 获取出口模台账列表
export function getMoldLedgerList2(params: object) {
  return request.get('/mold/mold/moldLedger/list2', { params });
}

// 添加台账
export function addMoldLedger(data: object) {
  return request.post('/mold/mold/moldLedger', data);
}

// 台账导入
export function addMoldLedgerUpload(data: any, config: any) {
  return request.post('/mold/mold/moldLedger/upload', data, config);
}

// 编辑台账
export function ediMoldLedger(data: object) {
  return request.put('/mold/mold/moldLedger', data);
}

// 删除台账
export function deleteMoldLedger(id: number) {
  return request.delete(`/mold/mold/moldLedger/${id}`);
}
