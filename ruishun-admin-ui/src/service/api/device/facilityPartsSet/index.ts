import { request } from '~/src/service/request';

// 获取清单列表
export const getAttachmentList = (params: any) => {
  return request.get('/eq/eqAttachment/list', { params });
};
// 通过ID查询单条数据
export const queryAttachmentById = (params: any) => {
  return request.get(`/eq/eqAttachment/selectall/${params.id}`);
};

// 删除产品
export const deleteAttachment = (params: any) => {
  return request.delete(`/eq/eqAttachment/${params.id}`);
};

// 新增产品
export const addAttachment = (data: any) => {
  return request.post(`/eq/eqAttachment`, data);
};

// 更新配件接口
export const updateAttachment = (data: any, oldId: number | string) => {
  return request.put(`/eq/eqAttachment/replace/?oldEmsAttachmentId=${oldId}`, data);
};

// 编辑产品
export const editAttachment = (data: any) => {
  return request.put(`/eq/eqAttachment`, data);
};

// 通过设备id获取配件列表
export const getAttachmentListByEquipmentId = (params: any) => {
  return request.get(`/eq/eqAttachment/selectListByEquipmentId/${params.id}`);
};
