import { request } from '../../request';

// // 查询岗位列表
export function listPost(query) {
  return request.get('/system/post/list', query);
}

// // 查询岗位详细
export function getPost(postId) {
  return request.get(`/system/post/${postId}`);
}

// // 新增岗位
export function addPost(data) {
  return request.post('/system/post', data);
}

// // 修改岗位
export function updatePost(data) {
  return request.put('/system/post', data);
}

// // 删除岗位
export function delPost(postId) {
  return request.delete(`/system/post/${postId}`);
}
