import { request } from '../../request';

// 获取打印模板信息
export const getPrintTemplate = (id: string) => request.get(`/md/printTemplate/${id}`);

// 获取客户信息列表
export const getCustomerList = (params: any) => request.get('ytgypt_api/md/client/', { params });

// 保存打印机模板信息
export const savePrintTemplate = (data: any) => request.post('/md/printTemplate', data);

// 获取打印模板信息
export const getPrintTemplateOne = (params: any) => request.get(`/md/printTemplate/list`, { params });
