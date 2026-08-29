import { request } from '../../request';

// 查询登录日志列表
export function loginList(query) {
  return request.get('/system/logininfor/list', query);
}

// 删除登录日志
export function delLogininfor(infoId) {
  return request.delete(`/system/logininfor/${infoId}`);
}

// 解锁用户登录状态
export function unlockLogininfor(userName) {
  return request.get(`/system/logininfor/unlock/${userName}`);
}
// 清空登录日志
export function cleanLogininfor() {
  return request.delete('/system/logininfor/clean');
}
