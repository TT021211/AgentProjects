import { request } from '~/src/service/request';
// 获取原料检记录
export const geMaterialRecordList = (params: any) => {
  return request.get(`/qc/qcMaterialInspection/list`, { params });
};

// 添加线盘检记录
export const addQcWireDiscRecord = (data: object) => {
  return request.post(`/qc/qcWireDiscRecord`, data);
};

// 填写线盘检记录
export const writeQcWireDiscRecord = (data: object) => {
  return request.put(`/qc/qcWireDiscRecord`, data);
};

// 删除线盘检记录
export const deleteQcWireDiscRecord = (deleteId: number) => {
  return request.delete(`/qc/qcMaterialInspection/${deleteId}`);
};
