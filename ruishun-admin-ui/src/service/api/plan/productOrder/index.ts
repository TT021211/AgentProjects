import { request } from '~/src/service/request';

// 获取计划的生产工单
export const getWorkOrderArrange = (params: any) => {
  return request.get('/mes/mesProductWorkorder/list', { params });
};

// 下发工单
export const addProductWorkOrder = (data: any) => {
  return request.post(`/mes/mesProductWorkorder`, data);
};

// 作废
export const cancellation = (id: number) => {
  return request.put(`/mes/mesProductWorkorder/cancel/${id}`);
};

// 通过拉丝机台id查询该机台工单列表
// export const getListByStrandedwireId = (params: any) => {
//   return request.get(`/mes/mesStrandedwireWorkorder/getListByEquipmentId/${params.equipmentId}/${params.axleNumber}`);
// };

// 通过退火机台id查询该机台工单列表
export const getListByAnnealingId = (params: any) => {
  return request.get(`/mes/mesAnnealingWorkorder/getListByEquipmentId/${params.equipmentId}/${params.axleNumber}`);
};

// 通过镀锡机台id查询该机台工单列表
export const getListByTinningId = (params: any) => {
  return request.get(`/mes/mesTinningWorkorder/getListByEquipmentId/${params.equipmentId}/${params.axleNumber}`);
};

// 通过并丝机台id查询该机台工单列表
export const getListByDoublewireId = (params: any) => {
  return request.get(`/mes/mesDoublewireWorkorder/getListByEquipmentId/${params.equipmentId}/${params.axleNumber}`);
};

// 通过绞线机台id查询该机台工单列表
export const getListByStrandedwireId = (params: any) => {
  return request.get(`/mes/mesStrandedwireWorkorder/getListByEquipmentId/${params.equipmentId}/${params.axleNumber}`);
};

// 消息推送
export const sendToRegistrationIds = (params: any) => {
  return request.post(`/mes/jpush/sendToRegistrationIds`, params);
};
