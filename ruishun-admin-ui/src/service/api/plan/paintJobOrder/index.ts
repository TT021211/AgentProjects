import { request } from '~/src/service/request';

// 获取镀锡工单
export const getWorkTinning = (params: any) => {
  return request.get('/mes/mesTinningWorkorder/list', { params });
};

// 编辑镀锡工单
export const putTinning = (params: any) => {
  return request.put('/mes/mesTinningWorkorder', params);
};

// 镀锡插单
export const insertTinningWorkorder = (data: object) => {
  return request.put(`/mes/mesTinningWorkorder/insertWorkorder`, data);
};

// 镀锡插单取消
export const cancelInsertTinningWorkorder = (id: number) => {
  return request.put(`/mes/mesTinningWorkorder/cancelWorkorder/${id}`);
};

// 镀锡插单完成
export const insertTinningWorkorderComplete = (id: number) => {
  return request.put(`/mes/mesTinningWorkorder/cancelInsertWorkorder/${id}`);
};

// 根据插单id获取镀锡信息
export const getTinningWorkorder = (id: number) => {
  return request.get(`/mes/mesTinningWorkorder/${id}`);
};
