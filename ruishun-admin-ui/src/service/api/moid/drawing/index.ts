// import { addDateRange } from '@/utils';
import { request } from '@/service/request';

// 获取拉丝套模列表
export function getMoldNestingList(params: object) {
  return request.get('/mold/mold/moldNesting/list', { params });
}

// 获取启用拉丝套模列表
export function getMoldNestingList2(params: object) {
  return request.get('/mold/mold/moldNesting/list2', { params });
}

// 添加拉丝套模
export function addMoldNesting(data: object) {
  return request.post('/mold/mold/moldNesting', data);
}

// 编辑拉丝套模
export function editMoldNesting(data: object) {
  return request.put('/mold/mold/moldNesting', data);
}

// 删除拉丝套模
export function deleteMoldNesting(id: number) {
  return request.delete(`/mold/mold/moldNesting/${id}`);
}

export function getMoldLedgerList(params?: object) {
  return request.get('mold/mold/moldLedger/queryByClassificationId', { params });
}

export function getMoldLedgerList2(params?: object) {
  return request.get('mold/mold/moldLedger/queryByClassificationId2', { params });
}

// 查看拉丝套模详情
export function getMoldNestingView(params: object) {
  return request.get('mold/mold/moldNesting/view', { params });
}

// 删除拉丝套模详情
export function deleteByMoldLedger(data: object) {
  return request.post('mold/mold/moldNesting/deleteByMoldLedger', data);
}
