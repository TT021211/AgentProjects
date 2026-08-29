import { request } from '~/src/service/request';

// 排产拉丝
export const addWiredrawingWorkOrder = (data: any) => {
  return request.post(`/mes/mesWiredrawingWorkorder`, data);
};
