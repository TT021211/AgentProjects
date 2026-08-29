import { request } from '~/src/service/request';

// 获取辅料供应商列表
export const getaccessorySupplierList = (params: any) => {
  return request.get('/md/mdAccessorySupplier/list', { params });
};

// 删除辅料供应商
export const deleteaccessorySupplier = (params: any) => {
  return request.delete(`/md/mdAccessorySupplier/${params.id}`);
};

// 新增辅料供应商
export const addaccessorySupplier = (data: any) => {
  return request.post(`/md/mdAccessorySupplier`, data);
};

// 编辑辅料供应商
export const editaccessorySupplier = (data: any) => {
  return request.put(`/md/mdAccessorySupplier`, data);
};
// 删除辅料出入库
export const deleteWmsAccessoryLog = (ids: Array<number>) => {
  return request.delete(`/wms/wmsAccessoryLog/deleteLog/${ids}`);
};
