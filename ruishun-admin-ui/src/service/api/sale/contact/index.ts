import { request } from '~/src/service/request';

// 获取合同列表
export const getSaleContractList = (params: any) => {
  return request.get('/sale/sale/saleContractManage/list', { params });
};

// 新增合同列表
export const addSaleContractList = (params: any) => {
  return request.post('/sale/sale/saleContractManage', params);
};

// 修改合同列表
export const updateSaleContractList = (params: any) => {
  return request.put('/sale/sale/saleContractManage', params);
};

// 删除产品
export const delSaleContractList = (params: any) => {
  return request.delete(`/sale/sale/saleContractManage/${params.id}`);
};
