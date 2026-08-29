import { request } from '~/src/service/request';

// 获取计划的拉丝工单
export const getWiredrawingWorkorder = (params: any) => {
  return request.get('/mes/mesWiredrawingWorkorder/list', { params });
};

// 获取计划的漆包工单
export const getEnamellingWorkorderList = (params: any) => {
  return request.get('/mes/mesEnamellingWorkorder/list', { params });
};
