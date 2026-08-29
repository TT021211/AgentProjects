import { request } from '@/service/request';
// 获取全部的客户
export const getAllUser = () => {
  return request.get('crm/crmClient/selectall');
};

// 添加走访记录
export const addMdClientInterview = (data: object) => {
  return request.post('crm/crmClientInterview', data);
};

// 编辑走访记录
export const editMdClientInterview = (data: object) => {
  return request.put('crm/crmClientInterview', data);
};

// 获取走访记录
export const getMdClientInterview = (params: object) => {
  return request.get('crm/crmClientInterview/list', { params });
};

// 删除走访记录
export const deleteMdClientInterview = (deleteId: number) => {
  return request.delete(`crm/crmClientInterview/${deleteId}`);
};

// 同步erp客户
export const getSyncClientList = () => {
  return request.get(`/erp/purchase/syncClientList`);
};
