import { request } from '~/src/service/request';

// 获取列表
export const getIngredientsProductNameList = (params: any) => {
  return request.get('/md/mdIngredientsProductName/list', { params });
};
// 通过ID查询单条数据
export const queryIngredientsProductName = () => {
  return request.get('/md/mdIngredientsProductName/selectAll');
};

// 删除产品
export const deleteIngredientsProductName = (params: any) => {
  return request.delete(`/md/mdIngredientsProductName/${params.id}`);
};

// 新增产品
export const addIngredientsProductName = (data: any) => {
  return request.post(`/md/mdIngredientsProductName`, data);
};

// 编辑产品
export const editIngredientsProductName = (data: any) => {
  return request.put(`/md/mdIngredientsProductName`, data);
};
