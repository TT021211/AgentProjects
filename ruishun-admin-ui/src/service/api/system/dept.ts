import { request } from '../../request';

// // 查询部门列表
export function listDept(query) {
  return request.get('/system/dept/list', query);
}

// // 查询部门列表（排除节点）
export function listDeptExcludeChild(deptId) {
  return request.get(`/system/dept/list/exclude/${deptId}`);
}

// // 查询部门详细
export function getDept(deptId) {
  return request.get(`/system/dept/${deptId}`);
}

// // 新增部门
export function addDept(data) {
  return request.post('/system/dept', data);
}

// // 修改部门
export function updateDept(data) {
  return request.put('/system/dept', data);
}

// // 删除部门
export function delDept(deptId) {
  return request.delete(`/system/dept/${deptId}`);
}
