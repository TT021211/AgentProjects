import { request } from '@/service/request';

export const fetchMaterialStockList = async (reqBody: any) => {
  const data = await request.get<ApiAuth.Token>(`/wms/wms/transaction/list`, reqBody);
  return data;
};

export const selectAll = async (reqBody: any) => {
  const data = await request.get<ApiAuth.Token>(`/wms/wms/transaction/selectAll`, { params: reqBody });
  return data;
};

export const selectWaringValue = async (reqBody: any) => {
  const data = await request.get<ApiAuth.Token>(`/wms/wms/transaction/selectWaringValue`, { params: reqBody });
  return data;
};

export function updateWaringValue(params: any) {
  return request.post('wms/wms/transaction/editSave', params);
}
