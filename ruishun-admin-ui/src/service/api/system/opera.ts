import { request } from '../../request';

// 查询操作日志列表
export function operaList(query) {
  return request.get('/system/operlog/list', query);
}

// // 删除操作日志
export function delOperlog(operId) {
  return request.delete(`/system/operlog/${operId}`);
}

// // 清空操作日志
export function cleanOperlog() {
  return request.delete('/system/operlog/clean');
}
