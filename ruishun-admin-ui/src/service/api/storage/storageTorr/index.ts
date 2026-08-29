import { request } from '~/src/service/request';

// 获取装托记录
export const getInstallationList = (params: any) => {
  return request.get(`/wms/wmsInstallation/list`, { params });
};
// 从托中移除
export const removeForBoxAndWeighRecord = (params: any) => {
  return request.post(`/wms/wmsInstallation/removeForBoxAndWeighRecord`, params);
};
// 拆托
export const removeForBoxAndWeighRecord2 = (params: any) => {
  return request.post(`/wms/wmsInstallation/removeForBoxAndWeighRecord2`, params);
};
// 删除托记录
export const delTorr = (id: any) => {
  return request.delete(`/wms/wmsInstallation/${id}`);
};
// 导出
export const exportTorr = (data: any) => {
  return request.post(`wms/wmsInstallation/export`, data);
};
