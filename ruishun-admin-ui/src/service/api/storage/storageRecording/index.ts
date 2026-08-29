import { request } from '~/src/service/request';

// 获取称重记录
export const getRecoordList = (params: any) => {
  return request.get(`/wms/wmsWeighRecord/list`, { params });
};

// 新增称重记录
export const addRecording = (data: object) => {
  return request.post(`/wms/wmsWeighRecord`, data);
};
// 根据箱编码删除称重记录
export const delRecording = boxCode => {
  return request.delete(`/wms/wmsWeighRecord/deleteByBoxCode/${boxCode}`);
};
// 根据id删除记录
export const delRecordingByID = id => {
  return request.delete(`/wms/wmsWeighRecord/${id}`);
};
// 获取昨天未装箱的数据
export const getYesterday = () => {
  return request.get(`/wms/wmsWeighRecord/queryYesterday`);
};
// 获取当前批次的流水号
export const getBoxSerial = (params: any) => {
  return request.post(`/wms/wmsWeighRecord/selectWmsWeighRecordToParams`, params);
};
// 获取当前批次的流水号
export const getBoxSerialToUser = (params: any) => {
  return request.post(`/wms/wmsWeighRecord/selectWmsWeighRecordToParams2`, params);
};
