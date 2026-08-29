import { request } from '~/src/service/request';
//
export const getQcInspectionItems = (params: any) => {
  return request.get(`/qc/qcInspectionItems/list`, { params });
};

export const addQcInspectionItems = (data: any) => {
  return request.post(`/qc/qcInspectionItems`, data);
};

export const editQcInspectionItems = (data: any) => {
  return request.put(`/qc/qcInspectionItems`, data);
};

export const deleteQcInspectionItems = (deleteId: number) => {
  return request.delete(`/qc/qcInspectionItems/${deleteId}`);
};
