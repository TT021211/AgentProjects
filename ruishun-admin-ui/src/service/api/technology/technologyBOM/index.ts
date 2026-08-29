import { request } from '~/src/service/request';

// 获取BOM的信息
export function fetchBomInfo(data) {
  return request.get(`/mes/mesProductBom/selectall/`, { params: { ...data } });
}

// 新增BOM信息
export function addBomInfo(data) {
  return request.post(`/mes/mesProductBom`, data);
}

// 修改BOM信息
export function editBomInfo(data) {
  return request.put(`/mes/mesProductBom`, data);
}

// 根据模具id获取所需要的用料列表
export function fetchBomList(moidId) {
  return request.get(`/md/mdItem/mouldItems/${moidId}`);
}

// 根据模具id来获取生成物的列表
export function fetchProductList(data) {
  return request.post(`/md/mdItem/createItemByMouldId/${data.mouldId}`, data);
}
