import { request } from '@/service/request';

// 获取模具保养计划记录列表
export function getMoldMaintainRecord(params: object) {
  return request.get('/mold/mold/moldMaintainRecord/list', { params });
}

// 获取模具保养计划列表
export function getMmoldMaintainPlan(params: object) {
  return request.get('/mold/mold/moldMaintainPlan/list', { params });
}
// 添加模具保养计划
export function addMmoldMaintainPlan(data: object) {
  return request.post('/mold/mold/moldMaintainPlan', data);
}
// 修改模具保养计划
export function editMmoldMaintainPlan(data: object) {
  return request.put('/mold/mold/moldMaintainPlan', data);
}
// 删除模具保养计划
export function deleteMmoldMaintainPlan(id: number) {
  return request.delete(`mold/mold/moldMaintainPlan/${id}`);
}

// 获取模具保养设置列表
export function getMoldMaintainItem(params: object) {
  return request.get('/mold/mold/moldMaintainItem/list', { params });
}

// 获取启用模具保养设置列表
export function getMoldMaintainItem2(params: object) {
  return request.get('/mold/mold/moldMaintainItem/list2', { params });
}

// 添加模具保养设置
export function addMoldMaintainItem(data: object) {
  return request.post('/mold/mold/moldMaintainItem', data);
}
// 修改模具保养设置
export function editMoldMaintainItem(data: object) {
  return request.put('/mold/mold/moldMaintainItem', data);
}
// 删除模具保养设置
export function deleteMoldMaintainItem(id: number) {
  return request.delete(`mold/mold/moldMaintainItem/${id}`);
}

// 获取模具待保养列表
export function getMoldMaintainList(params: object) {
  return request.get('/mold/mold/moldMaintainList/list', { params });
}

// 添加模具待保养
export function addMoldMaintain(params: object) {
  return request.get('/mold/mold/moldMaintainList/maintain', { params });
}

// 新增定时
export function scheduleJobTask(data: object) {
  return request.post('/mold/mold/scheduleJob/task', data);
}

// 修改定时
export function scheduleJobTaskEdit(data: object) {
  return request.post('/mold/mold/scheduleJob/editTask', data);
}
