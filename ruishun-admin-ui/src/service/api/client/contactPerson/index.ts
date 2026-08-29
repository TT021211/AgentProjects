import { request } from '~/src/service/request';

// 获取客户联系人
export const getCrmContactPerson = (params: object) => {
  return request.get('/crm/crmContactPerson/list', { params });
};

// 添加客户联系人
export const addCrmContactPerson = (data: object) => {
  return request.post('/crm/crmContactPerson', data);
};

// 编辑客户联系人
export const editCrmContactPerson = (data: object) => {
  return request.put('/crm/crmContactPerson', data);
};

// 删除客户联系人
export const deleteCrmContactPerson = (id: number) => {
  return request.delete(`/crm/crmContactPerson/${id}`);
};
