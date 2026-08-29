import { request } from '~/src/service/request';

// 获取拉丝工单
export const getWiredrawing = (params: any) => {
  return request.get('/mes/mesWiredrawingWorkorder/list', { params });
};
// 修改计划的拉丝工单
export const editWiredrawingWorkorder = (data: any) => {
  return request.put('/mes/mesWiredrawingWorkorder', data);
};
