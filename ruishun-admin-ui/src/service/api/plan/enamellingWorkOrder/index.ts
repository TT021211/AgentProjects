import { request } from '~/src/service/request';

// 退火排产
export const addEnamellingWorkorder = (data: any) => {
  return request.post(`/mes/mesAnnealingWorkorder`, data);
};
// 镀锡排产
export const addTinningWorkorder = (data: any) => {
  return request.post(`/mes/mesTinningWorkorder`, data);
};
// 拉丝排产
export const addWiredrawingWorkorder = (data: any) => {
  return request.post(`/mes/mesWiredrawingWorkorder`, data);
};
// 并丝排产
export const addWireWorkorder = (data: any) => {
  return request.post(`/mes/mesDoublewireWorkorder`, data);
};
// 绞线排产
export const addStrandedwire = (data: any) => {
  return request.post(`/mes/mesStrandedwireWorkorder`, data);
};
// addWiredrawingWorkorder
// addEnamellingWorkorder
// addTinningWorkorder
// addWireWorkorder
// addStrandedwire
