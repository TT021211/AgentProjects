import { request } from '~/src/service/request';

// 获取列表
export const getIngredientsTradersList = (params: any) => {
  return request.get('/md/mdIngredientsTraders/list', { params });
};
// 通过ID查询单条数据
export const queryIngredientsTradersById = (params: any) => {
  return request.get(`/md/mdIngredientsTraders/selectall/${params.id}`);
};

// 删除产品
export const deleteIngredientsTraders = (params: any) => {
  return request.delete(`/md/mdIngredientsTraders/${params.id}`);
};

// 新增产品
export const addIngredientsTraders = (data: any) => {
  return request.post(`/md/mdIngredientsTraders`, data);
};

// 编辑产品
export const editIngredientsTraders = (data: any) => {
  return request.put(`/md/mdIngredientsTraders`, data);
};
