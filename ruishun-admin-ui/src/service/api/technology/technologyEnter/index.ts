import { request } from '~/src/service/request';

// 获取列表
export const getMesProcessEnterList = (params: any) => {
  return request.get('/mes/mesProcessEnter/list', { params });
};
// 通过ID查询单条数据
export const queryMesProcessEnterById = (params: any) => {
  return request.get(`/mes/mesProcessEnter/${params.id}`);
};

// 删除产品
export const deleteMesProcessEnter = (params: any) => {
  return request.delete(`/mes/mesProcessEnter/${params.id}`);
};

// 新增产品
export const addMesProcessEnter = (data: any) => {
  return request.post(`/mes/mesProcessEnter`, data);
};

// 编辑产品
export const editMesProcessEnter = (data: any) => {
  return request.put(`/mes/mesProcessEnter`, data);
};

// 上传模板
export function uploadProcessEnter(data: any, config: any) {
  return request.post('/mes/mesProcessEnter/import', data, config);
}
