import { request } from '~/src/service/request';

// 获取原料出入库
export const getIngredientsLog = (params: any) => {
  return request.get(`/wms/wmsIngredientsLog/list`, { params });
};

// 添加原料出入库
export const addIngredientsLog = (data: any) => {
  return request.post(`/wms/wmsIngredientsLog`, data);
};

// 编辑原料出入库
export const editIngredientsLog = (data: any) => {
  return request.put(`/wms/wmsIngredientsLog`, data);
};

// 错误处理
export const deleteIngredientsLogMistake = (ids: any) => {
  return request.post(`/wms/wmsIngredientsLog/mistake/${ids}`);
};
