import { request } from '../../request';

// 查询在线用户列表
export function onlineList(query) {
  return request.get('/system/online/list', query);
}

// 强退用户
export function forceLogout(tokenId) {
  return request.delete(`/system/online/${tokenId}`);
}
