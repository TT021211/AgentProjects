import { request } from '~/src/service/request';

// 获取退火工单
export const getWorkAnnealing = (params: any) => {
  return request.get('/mes/mesAnnealingWorkorder/list', { params });
};
// 编辑退火工单
export const putAnneal = (params: any) => {
  return request.put('/mes/mesAnnealingWorkorder', params);
};

// 退火插单
export const insertAnnealingWorkorder = (data: object) => {
  return request.put(`/mes/mesAnnealingWorkorder/insertWorkorder`, data);
};

// 退火插单取消
export const cancelInsertAnnealingWorkorder = (id: number) => {
  return request.put(`/mes/mesAnnealingWorkorder/cancelWorkorder/${id}`);
};

// 退火插单完成
export const insertAnnealingWorkorderComplete = (id: number) => {
  return request.put(`/mes/mesAnnealingWorkorder/cancelInsertWorkorder/${id}`);
};

// 根据插单id获取退火信息
export const getAnnealingWorkorder = (id: number) => {
  return request.get(`/mes/mesAnnealingWorkorder/${id}`);
};
