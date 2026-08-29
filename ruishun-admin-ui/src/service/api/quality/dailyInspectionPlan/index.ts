import { request } from '~/src/service/request';
// 获取日常送检计划列表
export const getQcDailyplanList = (params: any) => {
  return request.get(`/qc/qcDailyplan/list`, { params });
};

export const addQcDailyplan = (data: any) => {
  return request.post(`/qc/qcDailyplan`, data);
};

export const editQcDailyplan = (data: any) => {
  return request.put(`/qc/qcDailyplan`, data);
};

export const deleteQcDailyplan = (deleteId: number) => {
  return request.delete(`/qc/qcDailyplan/${deleteId}`);
};
