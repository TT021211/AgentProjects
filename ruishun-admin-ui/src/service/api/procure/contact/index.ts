import { request } from '~/src/service/request';

// 获取合同列表
export const getProcureContractList = (params: any) => {
  return request.get('/procure/procure/procureContract/list', { params });
};

// 新增合同列表
export const addProcureContractList = (params: any) => {
  return request.post('/procure/procure/procureContract', params);
};

// 修改合同列表
export const updateProcureContractList = (params: any) => {
  return request.put('/procure/procure/procureContract', params);
};

// 删除产品
export const delProcureContractList = (params: any) => {
  return request.delete(`/procure/procure/procureContract/${params.id}`);
};
