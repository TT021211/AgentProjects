import { request } from '~/src/service/request';
// 漆包修改状态
export const enamellingUpdateSort = (id: number) => {
  return request.put(`/mes/mesEnamellingWorkorder/updateSort/${id}`);
};

// 拉丝优先
export const wiredrawingUpdateSort = (id: number) => {
  return request.put(`/mes/mesWiredrawingWorkorder/updateSort/${id}`);
};

// 退火优先
export const annealingUpdateSort = (id: number) => {
  return request.put(`/mes/mesAnnealingWorkorder/updateSort/${id}`);
};
// 镀锡优先
export const tinningUpdateSort = (id: number) => {
  return request.put(`/mes/mesTinningWorkorder/updateSort/${id}`);
};
// 并丝优先
export const doublewireUpdateSort = (id: number) => {
  return request.put(`/mes/mesDoublewireWorkorder/updateSort/${id}`);
};
// 绞线优先
export const strandedwireUpdateSort = (id: number) => {
  return request.put(`/mes/mesStrandedwireWorkorder/updateSort/${id}`);
};
// 拉丝工单作废
export const wiredrawingWorkorderCancel = (id: number) => {
  return request.put(`/mes/mesWiredrawingWorkorder/cancel/${id}`);
};
// 镀锡工单作废
export const tinningWorkorderCancel = (id: number) => {
  return request.put(`/mes/mesTinningWorkorder/cancel/${id}`);
};
// 退火工单作废
export const annealingWorkorderCancel = (id: number) => {
  return request.put(`/mes/mesAnnealingWorkorder/cancel/${id}`);
};
// 并丝工单作废
export const doublewireWorkorderCancel = (id: number) => {
  return request.put(`/mes/mesDoublewireWorkorder/cancel/${id}`);
};
// 绞线工单作废
export const strandedwireWorkorderCancel = (id: number) => {
  return request.put(`/mes/mesStrandedwireWorkorder/cancel/${id}`);
};

// export const enamellingWorkorderCancel = (id: number) => {
//   return request.put(`/mes/mesEnamellingWorkorder/cancel/${id}`);
// };

// 拉丝工单完工
export const wiredrawingWorkorderComplete = (id: number, status: string) => {
  return request.put(`/mes/mesWiredrawingWorkorder/updateStatus/${id}/${status}`);
};
// 退火工单完工
export const annealingWorkorderComplete = (id: number, status: string) => {
  return request.put(`/mes/mesAnnealingWorkorder/updateStatus/${id}/${status}`);
};
// 镀锡工单完工
export const tinningWorkorderComplete = (id: number, status: string) => {
  return request.put(`/mes/mesTinningWorkorder/updateStatus/${id}/${status}`);
};
// 并丝工单完工
export const doublewireWorkorderComplete = (id: number, status: string) => {
  return request.put(`/mes/mesDoublewireWorkorder/updateStatus/${id}/${status}`);
};
// 绞线工单完工
export const strandedwireWorkorderComplete = (id: number, status: string) => {
  return request.put(`/mes/mesStrandedwireWorkorder/updateStatus/${id}/${status}`);
};
