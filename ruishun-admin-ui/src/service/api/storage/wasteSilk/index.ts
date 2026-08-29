import { request } from '~/src/service/request';

// 获取废丝包明细
export const getWasteSilkPackageDetail = (params: any) => {
  return request.get(`/wms/wmsWasteSilkPackageDetails/list`, { params });
};
// 删除废丝包记录
export const delWasteSilkPackageDetail = (id: any) => {
  return request.delete(`/wms/wmsWasteSilkPackageDetails/${id}`);
};
// 获取废丝包记录
export const getWasteSilkPackage = (params: any) => {
  return request.get(`/wms/wmsWasteSilkPackage/list`, { params });
};
