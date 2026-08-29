import { request } from '~/src/service/request';

// 获取清单列表
export const getRepairReportList = (params: any) => {
  return request.get('/eq/eqRepairReport/list', { params });
};
// 通过ID查询单条数据
export const queryRepairReportById = (params: any) => {
  return request.get(`/eq/eqRepairReport/selectall/${params.id}`);
};

// 删除产品
export const deleteRepairReport = (params: any) => {
  return request.delete(`/eq/eqRepairReport/${params.id}`);
};

// 新增产品
export const addRepairReport = (data: any) => {
  return request.post(`/eq/eqRepairReport`, data);
};

// 编辑产品
export const editRepairReport = (data: any) => {
  return request.put(`/eq/eqRepairReport`, data);
};

// 写入设备知识库
export const addKnowledgeRepairReport = (params: any) => {
  return request.put(`/eq/eqRepairReport/addKnowledge/${params.id}`);
};
// 获取设备知识库列表
export const getKnowledgeRepairReportList = (params: any) => {
  return request.get('/eq/eqRepairReport/getKnowledgeList', { params });
};
// 删除设备知识库数据源
export const deleteKnowledgeByIds = (params: any) => {
  return request.delete(`/eq/eqRepairReport/deleteKnowledgeByIds/${params.id}`);
};
