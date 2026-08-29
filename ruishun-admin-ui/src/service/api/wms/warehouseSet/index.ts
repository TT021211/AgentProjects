import { request } from '@/service/request';

/**
 * 仓库列表
 */
export async function list(reqBody: any) {
  console.log('reqBody', reqBody);
  const data = await request.get<ApiAuth.Token>(`/wms/wms/warehouse/list`, { params: reqBody });
  return data;
}

/**
 * 所有仓库记录
 */
export async function getlist(reqBody: any) {
  const data = await request.get<ApiAuth.Token>(`/wms/wms/warehouse/getlist`, reqBody);
  return data;
}

export async function getWorksstationIds() {
  const data = await request.get<ApiAuth.Token>(`/wms/wms/warehouse/getWorksstationIds`, {});
  return data;
}
/**
 * 仓库新增
 */
export async function add(reqBody: object) {
  const data = await request.post<ApiAuth.Token>(`/wms/wms/warehouse`, reqBody);
  return data;
}

/**
 * 详情
 */
export async function getInfo(warehouseId: number) {
  const data = await request.get<ApiAuth.Token>(`/wms/wms/warehouse/${warehouseId}`, {});
  return data;
}

/**
 * 更新
 */
export async function update(reqBody: object) {
  const data = await request.put<ApiAuth.Token>(`/wms/wms/warehouse`, reqBody);
  return data;
}

/**
 * 删除
 */
export async function deleteInfo(warehouseId: number) {
  const data = await request.delete<ApiAuth.Token>(`/wms/wms/warehouse/${warehouseId}`, {});
  return data;
}
