import { request } from '~/src/service/request';

// 获取价格管理列表
export const getSalePriceList = (params: any) => {
  return request.get('/sale/sale/salePriceManagement/list', { params });
};

// 新增价格记录
export const addSalePriceList = (params: any) => {
  return request.post('/sale/sale/salePriceManagement', params);
};

// 修改价格记录
export const updateSalePriceList = (params: any) => {
  return request.put('/sale/sale/salePriceManagement', params);
};

// 删除价格记录
export const delSalePriceList = (params: any) => {
  return request.delete(`/sale/sale/salePriceManagement/${params.id}`);
};
