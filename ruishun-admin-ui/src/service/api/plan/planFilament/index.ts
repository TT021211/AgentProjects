import { request } from '~/src/service/request';

// 获取并丝工单
export const getWireWorkorder = (params: any) => {
  return request.get('/mes/mesDoublewireWorkorder/list', { params });
};
// 编辑并丝工单
export const putDoublewire = (params: any) => {
  return request.put('/mes/mesDoublewireWorkorder', params);
};

// 并丝插单
export const insertDoublewireWorkorder = (data: object) => {
  return request.put(`/mes/mesDoublewireWorkorder/insertWorkorder`, data);
};

// 并丝插单取消
export const cancelInsertDoublewireWorkorder = (id: number) => {
  return request.put(`/mes/mesDoublewireWorkorder/cancelWorkorder/${id}`);
};

// 并丝插单完成
export const insertDoublewireWorkorderComplete = (id: number) => {
  return request.put(`/mes/mesDoublewireWorkorder/cancelInsertWorkorder/${id}`);
};

// 根据插单id获取并丝信息
export const getDoublewireWorkorder = (id: number) => {
  return request.get(`/mes/mesDoublewireWorkorder/${id}`);
};
