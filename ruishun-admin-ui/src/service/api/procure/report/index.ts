import { request } from '~/src/service/request';

// 获取报表列表
export const getProcureReportList = (params: any) => {
  return request.get('/procure/procureReportManagement/list', { params });
};
// 通过ID查询单条报表数据
export const queryProcureReportById = (params: any) => {
  return request.get(`/procure/procureReportManagement/${params.id}`);
};

// 删除报表记录
export const deleteReportOrder = (params: any) => {
  return request.delete(`/procure/procureReportManagement/${params.id}`);
};

// 新增报表记录
export const addReportOrder = (data: any) => {
  return request.post(`/procure/procureReportManagement`, data);
};

// 编辑报表记录
export const editReportOrder = (data: any) => {
  return request.put(`/procure/procureReportManagement`, data);
};
