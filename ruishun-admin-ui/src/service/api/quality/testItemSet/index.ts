import { request } from '~/src/service/request';
//
export const getQcDetectionItems = (params: any) => {
  return request.get(`/qc/qcDetectionItems/list`, { params });
};

export const addQcDetectionItems = (data: any) => {
  return request.post(`/qc/qcDetectionItems`, data);
};

export const editQcDetectionItems = (data: any) => {
  return request.put(`/qc/qcDetectionItems`, data);
};

export const deleteQcDetectionItems = (deleteId: number) => {
  return request.delete(`/qc/qcDetectionItems/${deleteId}`);
};
