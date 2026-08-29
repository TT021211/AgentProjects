import { request } from '../../request';

const BASE_URL = '/ecm/ecmStepTariff';
// 获取阶梯电价列表
export function getElectrovalenceList(params: any) {
  return request.get(`${BASE_URL}/list`, { params });
}
// 新增阶梯电价
export function addElectrovalence(data: any) {
  return request.post(`${BASE_URL}`, data);
}
// 编辑阶梯电价
export function editElectrovalence(data: any) {
  return request.put(`${BASE_URL}`, data);
}

// 删除阶梯电价
export function delElectrovalence(id: any) {
  return request.delete(`${BASE_URL}/${id}`);
}
