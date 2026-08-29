import { request } from '~/src/service/request';
//
export const getQcDefectClassification = (params: any) => {
  return request.get(`/qc/qcDefectClassification/list`, { params });
};

export const addQcDefectClassification = (data: any) => {
  return request.post(`/qc/qcDefectClassification`, data);
};

export const editQcDefectClassification = (data: any) => {
  return request.put(`/qc/qcDefectClassification`, data);
};

export const deleteQcDefectClassification = (deleteId: number) => {
  return request.delete(`/qc/qcDefectClassification/${deleteId}`);
};
