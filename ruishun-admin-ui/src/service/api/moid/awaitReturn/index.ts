// import { addDateRange } from '@/utils';
import { request } from '@/service/request';

// 获取模具待归还列表
export function getMoldReturnedList(params: object) {
  return request.get('/mold/mold/moldReturned/list', { params });
}

// 模具确认归还
export function confirmAddMoldReturned(data: object) {
  return request.post('/mold/mold/moldReturned/confirm', data);
}
