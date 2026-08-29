import { request } from '~/src/service/request';

// 获取清单列表
export const getItemTypeDetailList = (params: any) => {
  return request.get('/eq/eqItemTypeDetail/list', { params });
};
// 通过ID查询单条数据
export const queryItemTypeDetailById = (params: any) => {
  return request.get(`/eq/eqItemTypeDetail/selectall/${params.id}`);
};

// 删除产品
export const deleteItemTypeDetail = (data: any) => {
  return request.delete(`/eq/eqItemTypeDetail/${data}`);
};

// 新增产品
export const addItemTypeDetail = (data: any) => {
  return request.post(`/eq/eqItemTypeDetail`, data);
};

// 编辑产品
export const editItemTypeDetail = (data: any) => {
  return request.put(`/eq/eqItemTypeDetail`, data);
};

// 加入类型设置
export const addTypeDetailList = (ids: string, data: any) => {
  return request.post(`/eq/eqItemTypeDetail/allocated${ids}`, { ...data });
};
