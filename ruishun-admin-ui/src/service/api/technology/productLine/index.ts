import { request } from '~/src/service/request';
// 获取产品关联的工艺路线
export const getTechnologyList = (params: any) => {
  return request.get('/mes/mesProductRouting/list', { params });
};
// 产品关联工艺路线
export const fetchAddTechnologyLine = (data: ProductLine.link) => {
  return request.post('/mes/mesProductRouting', data);
};
// 删除产品关联工艺路线
export const fetchDeleteTechnologyLine = (id: number) => {
  return request.delete(`/mes/mesProductRouting/${id}`);
};

// 编辑产品工艺路线
export const fetchEditTechnologyLine = (data: any) => {
  return request.put('/mes/mesProductRouting', data);
};
// 不分页获取产品关联的工艺路线
export const fetchGetTechnologyLine = (data: any) => {
  return request.get('/mes/mesProductRouting/selectall', { params: data });
};
