import { request } from '../../request';

/**
 * 库存记录列表
 */
export async function fetchMaterialStockList(reqBody: any) {
  console.log(reqBody);
  const data = await request.get<ApiAuth.Token>(`/wms/mes/wm/wmstock/list`, reqBody);
  return data;
}

/**
 * 库存记录详情
 */
export async function fetchMaterialStockInfo(materialStockId: number) {
  const data = await request.get<ApiAuth.Token>(`/wms/mes/wm/wmstock/${materialStockId}`, {});
  return data;
}

/**
 * 库存记录更新
 */
export async function updateMaterialStock(reqBody: object) {
  const data = await request.put<ApiAuth.Token>(`/wms/mes/wm/wmstock`, reqBody);
  return data;
}

/**
 * 库存记录删除
 */
export async function deleteMaterialStock(materialStockId: number) {
  const data = await request.delete<ApiAuth.Token>(`/wms/mes/wm/wmstock/${materialStockId}`, {});
  return data;
}

/**
 * 库存记录新增
 */
export async function addMaterialStock(reqBody: object) {
  const data = await request.post<ApiAuth.Token>(`/wms/mes/wm/wmstock`, reqBody);
  return data;
}
