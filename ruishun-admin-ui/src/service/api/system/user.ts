import { parseStrEmpty } from '@/utils';
import { request } from '../../request';
export function deptTreeSelect() {
  return request.get('/system/user/deptTree');
}
// import request from '@/utils/request'
// import { parseStrEmpty } from "@/utils/ruoyi";

// 查询用户列表
export function listUser() {
  return request.get('/system/user/list');
}

// 查询用户详细
export function getUser(userId) {
  return request.get(`/system/user/${parseStrEmpty(userId)}`);
}

// 新增用户
export function addUser(data) {
  return request.post('/system/user/', data);
}

// 修改用户
export function updateUser(data) {
  return request.put('/system/user/', data);
}

// 删除用户
export function delUser(userId) {
  return request.delete(`/system/user/${userId}`);
}

// 用户密码重置
export function resetUserPwd(userId, password) {
  return request.put('/system/user/resetPwd', { userId, password });
}

// 用户状态修改
export function changeUserStatus(userId, status) {
  return request.put('/system/user/changeStatus', { userId, status });
}

// 个人信息
export function getUserInfo() {
  return request.get('/system/user/getUserInfo');
}

// 获取所有用户
export function getAllUser() {
  return request.get('/system/user/list');
}
// const data = {
//   userId,
//   status
// }
// return request({
//   url: '/system/user/changeStatus',
//   method: 'put',
//   data: data
// })

// 查询用户个人信息
// export function getUserProfile() {
//   return request({
//     url: '/system/user/profile',
//     method: 'get'
//   })
// }

// 修改用户个人信息
// export function updateUserProfile(data) {
//   return request({
//     url: '/system/user/profile',
//     method: 'put',
//     data: data
//   })
// }

// 用户密码重置
// export function updateUserPwd(oldPassword, newPassword) {
//   const data = {
//     oldPassword,
//     newPassword
//   }
//   return request({
//     url: '/system/user/profile/updatePwd',
//     method: 'put',
//     params: data
//   })
// }

// 用户头像上传
// export function uploadAvatar(data) {
//   return request({
//     url: '/system/user/profile/avatar',
//     method: 'post',
//     data: data
//   })
// }

// 查询授权角色
export function getAuthRole(userId) {
  return request.get(`/system/user/authRole/${userId}`);
}

// 保存授权角色
export function updateAuthRole(data) {
  return request.put(putUserHandler('/system/user/authRole', data));
}

// 导入用户权限put请求转换
function putUserHandler(url, data) {
  const roleIds = data.roleIds.join(',');
  return `${url}?userId=${data.userId}&roleIds=${roleIds}`;
}
