import { request } from '../../request';

// // 查询菜单列表
export function listMenu(query) {
  return request.get('/system/menu/list', query);
}

// // 查询菜单详细
export function getMenu(menuId) {
  return request.get(`/system/menu/${menuId}`);
}

// // 查询菜单下拉树结构
export function treeselect() {
  return request.get('/system/menu/treeselect');
}

// // 根据角色ID查询菜单下拉树结构
export function roleMenuTreeselect(roleId) {
  return request.get(`/system/menu/roleMenuTreeselect/${roleId}`);
}

// 获取角色权限
export function rolePermission(roleId) {
  return request.get(`/system/menu/userMenuTreeselect/${roleId}`);
}

// // 新增菜单
export function addMenu(data) {
  return request.post('/system/menu', data);
}

// // 修改菜单
export function updateMenu(data) {
  return request.put('/system/menu', data);
}

// // 删除菜单
export function delMenu(menuId) {
  return request.delete(`/system/menu/${menuId}`);
}
