// import { addDateRange } from '@/utils';
import { request } from '@/service/request';

// 获取模具生命周期报表列表
export function getMoldLifeCycleList(params: object) {
  return request.get('/mold/mold/moldLifeCycle/list', { params });
}
