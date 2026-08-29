import { request } from '~/src/service/request';

// 获取分类列表
export const getClassifyList = (params: any) => {
  return request.get('/md/mdProductType/list', { params });
};
// 获取全部类型列表
export const getAllClassifyList = (params: any) => {
  return request.get('/md/mdProductType/selectall', { params });
};

// 删除分类
export const deleteClassify = data => {
  return request.delete(`/md/mdProductType/${data}`);
};

// 获取类型树
export const getClassifyTree = () => {
  return request.get('/md/mdProductType/tree');
};

// 新增分类
export const addClassifyWay = data => {
  return request.post(`/md/mdProductType`, data);
};

// 编辑分类
export const editClassifyWay = data => {
  return request.put(`/md/mdProductType`, data);
};
