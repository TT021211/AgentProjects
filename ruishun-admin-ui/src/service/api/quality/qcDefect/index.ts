import { request } from '~/src/service/request';
//
export const getQcDefect = (params: any) => {
  return request.get(`/qc/qcDefect/list`, { params });
};

export const addQcDefect = (data: any) => {
  return request.post(`/qc/qcDefect`, data);
};

export const editQcDefect = (data: any) => {
  return request.put(`/qc/qcDefect`, data);
};

export const deleteQcDefect = (deleteId: number) => {
  return request.delete(`/qc/qcDefect/${deleteId}`);
};
