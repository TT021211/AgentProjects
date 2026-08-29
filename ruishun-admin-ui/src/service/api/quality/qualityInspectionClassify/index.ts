import { request } from '~/src/service/request';
//
export const getQClassification = (params: any) => {
  return request.get(`/qc/qcClassification/list`, { params });
};

export const addQClassification = (data: any) => {
  return request.post(`/qc/qcClassification`, data);
};

export const editQClassification = (data: any) => {
  return request.put(`/qc/qcClassification`, data);
};

export const deleteQClassification = (deleteId: number) => {
  return request.delete(`/qc/qcClassification/${deleteId}`);
};
