import { request } from '~/src/service/request';

// 查询日志
export function getJobLogList(params: object) {
  return request.get('/job/job/log/list', { params });
}

// 删除日志
export function deleteJobLog(jobLogIds: string) {
  return request.delete(`/job/job/log/${jobLogIds}`);
}

// 清除任务
export function cleanJobLog() {
  return request.delete(`/job/job/log/clean`);
}
