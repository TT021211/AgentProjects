import { request } from '~/src/service/request';

// 获取箱记录
export const getBoxReacprding = (params: any) => {
  return request.get(`/wms/wmsBox/list`, { params });
};

// 新增箱记录
export const addBoxReacprding = (data: object) => {
  return request.post(`/wms/wmsBox`, data);
};

// 删除箱记录
export const delBoxReacprding = id => {
  return request.delete(`/wms/wmsBox/${id}`);
};

// 装托
export const addInstallationing = (data: object) => {
  return request.post(`/wms/wmsInstallation`, data);
};
// 查询审核记录
export const selAuditRecord = (params: any) => {
  return request.get(`/wms/wmsNoticeInvoiceRecord/list`, { params });
};
