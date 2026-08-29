import { request } from '~/src/service/request';

// 获取绞线工单
export const getStrandedwire = (params: any) => {
  return request.get('/mes/mesStrandedwireWorkorder/list', { params });
};
// 编辑绞线工单
export const putStrandedwire = (params: any) => {
  return request.put('/mes/mesStrandedwireWorkorder', params);
};

// 绞线插单
export const insertStrandedwireWorkorder = (data: object) => {
  return request.put(`/mes/mesStrandedwireWorkorder/insertWorkorder`, data);
};

// 绞线插单取消
export const cancelInsertStrandedwireWorkorder = (id: number) => {
  return request.put(`/mes/mesStrandedwireWorkorder/cancelWorkorder/${id}`);
};

// 绞线插单完成
export const insertStrandedwireWorkorderComplete = (id: number) => {
  return request.put(`/mes/mesStrandedwireWorkorder/cancelInsertWorkorder/${id}`);
};

// 根据插单id获取绞线信息
export const getStrandedwireWorkorder = (id: number) => {
  return request.get(`/mes/mesStrandedwireWorkorder/${id}`);
};
