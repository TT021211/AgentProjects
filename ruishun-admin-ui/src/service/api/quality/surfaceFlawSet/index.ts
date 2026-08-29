import { request } from '~/src/service/request';
//
export const getQcSurface = (params: any) => {
  return request.get(`/qc/qcSurface/list`, { params });
};

export const addQcSurface = (data: any) => {
  return request.post(`/qc/qcSurface`, data);
};

export const editQcSurface = (data: any) => {
  return request.put(`/qc/qcSurface`, data);
};

export const deleteQcSurface = (deleteId: number) => {
  return request.delete(`/qc/qcSurface/${deleteId}`);
};
