import { request } from '~/src/service/request';

// 生成发货通知单
export const addNoticeLnvoice = (params: any) => {
  return request.post(`/wms/wmsNoticeLnvoice`, params);
};
// 修改发货通知单
export const updateNoticeLnvoice = (params: any) => {
  return request.put(`/wms/wmsNoticeLnvoice`, params);
};
// 根据托编号查询产品信息
export const selectCodeToBox = (params: any) => {
  return request.post(`/wms/wmsBox/selectInstallationCodeToBox`, params);
};
// 查询发货通知单
export const getNoticeLnvoice = (params: any) => {
  return request.get(`/wms/wmsNoticeLnvoice/list`, { params });
};
// 移除发货通知单
export const DelNoticeLnvoice = (id: any) => {
  return request.delete(`/wms/wmsNoticeLnvoice/delete?ids=${id}`);
};
// 审核通知单
export const ConfimopinionAudit = (params: any) => {
  return request.post(`/wms/wmsNoticeLnvoice/audit`, params);
};
// 取回审核通知单
export const retrieveAudit = (params: any) => {
  return request.post(`/wms/wmsNoticeLnvoice/recall`, params);
};
