import { request } from '~/src/service/request';

// 获取物料列表
export const getMaterialList = (params: any) => {
  return request.get('/md/mdItem/list', { params });
};

// 新增物料
export const addMaterial = (data: any) => {
  return request.post('/md/mdItem', data);
};

// 编辑物料
export const editMaterial = (data: any) => {
  return request.put('/md/mdItem', data);
};

// 删除物料
export const deleteMaterial = (params: any) => {
  return request.delete(`/md/mdItem/${params}`);
};

// 获取物料选择列表
export const getMaterialOption = () => {
  return request.get('/md/mdItem/selectOptionList');
};

// 不分页获取所有物料信息
export const getMaterialAll = () => {
  return request.get('/md/mdItem/selectall');
};
