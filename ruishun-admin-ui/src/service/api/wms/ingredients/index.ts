import { request } from '@/service/request';

export const getWmsAccessoryLogList = async (reqBody: any) => {
  const data = await request.get<ApiAuth.Token>(`/wms/wmsAccessoryLog/list`, { params: reqBody });
  return data;
};

export const selectAll = async (reqBody: any) => {
  const data = await request.get<ApiAuth.Token>(`/wms/wmsAccessoryLog/selectAll`, { params: reqBody });
  return data;
};
