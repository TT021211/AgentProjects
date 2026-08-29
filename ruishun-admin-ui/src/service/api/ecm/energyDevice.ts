import { request } from '../../request';

const BASE_URL = '/ecm/ecmElectricDevice';
// 获取电表设备列表
export function getEnergyDeviceList(params: any) {
  return request.get(`${BASE_URL}/list`, { params });
}

// 新增电表设备
export function addEnergyDevice(data: any) {
  return request.post(`${BASE_URL}`, data);
}

// 删除电表设备
export function delEnergyDevice(id: any) {
  return request.delete(`${BASE_URL}/${id}`);
}

// 修改电表信息
export function updateEnergyDevice(data: any) {
  return request.put(`${BASE_URL}`, data);
}
