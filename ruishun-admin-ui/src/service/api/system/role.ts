import { request } from '../../request';

// // 查询角色列表
export async function fetchRoleList(query) {
  const { rows, total } = await request.get('/system/role/list', query);
  return { rows, total };
}

// // 查询角色详细
export function getRole(roleId) {
  return request.get(`/system/role/${roleId}`);
}

// // 新增角色
export function addRole(data) {
  return request.post('/system/role', data);
}

// // 修改角色
export function updateRole(data) {
  return request.put('/system/role', data);
}

// // 角色数据权限
export function dataScope(data) {
  return request.put('/system/role/dataScope', data);
}

// // 角色状态修改
export function changeRoleStatus(roleId, status) {
  return request.put('/system/role/changeStatus', { roleId, status });
}

// // 删除角色
export function delRole(roleId) {
  return request.delete(`/system/role/${roleId}`);
}
export function deptRoleTreeSelect(roleId) {
  return request.get(`/system/role/deptTree/${roleId}`);
}

// // 查询角色已授权用户列表
export function allocatedUserList(query) {
  return request.get('/system/role/authUser/allocatedList', query);
}

// // 查询角色未授权用户列表
export function unallocatedUserList(query) {
  return request.get('/system/role/authUser/unallocatedList', query);
}

// // 取消用户授权角色
export function authUserCancel(data) {
  return request.put('/system/role/authUser/cancel', data);
}

// // 批量取消用户授权角色
export function authUserCancelAll(data) {
  return request.put(putHandler('/system/role/authUser/cancelAll', data));
}

// // 授权用户选择
export function authUserSelectAll(data) {
  return request.put(putHandler('/system/role/authUser/selectAll', data));
}
// 导入用户权限put请求转换
function putHandler(url, data) {
  const userIds = data.userIds.join(',');
  return `${url}?roleId=${data.roleId}&userIds=${userIds}`;
}

// // 根据角色ID查询部门树结构
