import { request } from '~/src/service/request';

// 查询任务
export function getJobList(params: object) {
  return request.get('/job/job/list', { params });
}

// 添加任务
export function addJob(data: object) {
  return request.post('/job/job', data);
}

// 修改任务
export function editJon(data: object) {
  return request.put('/job/job', data);
}

// 删除任务
export function deleteJob(jobIds: string) {
  return request.delete(`/job/job/${jobIds}`);
}

// 修改状态
export function changeStatus(data: object) {
  return request.put(`/job/job/changeStatus`, data);
}
// 执行一次
export function executeOnce(data: object) {
  return request.put(`/job/job/run`, data);
}

// 导出
export function exportJob() {
  return request.post(`/job/job/export`);
}
