// import { addDateRange } from '@/utils';
import { request } from '@/service/request';

// 获取模具维修保养列表
export function getMoldMaintenanceList(params: object) {
  return request.get('/mold/mold/moldMaintenance/list', { params });
}

// 添加维修保养
export function addMoldmoldMaintenance(data: object) {
  return request.post('/mold/mold/moldMaintenance', data);
}

// 维修保养入库
export function moldMaintenanceWarehousing(data: object) {
  return request.post('/mold/mold/moldMaintenance/warehousing', data);
}
