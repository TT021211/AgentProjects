import { request } from '~/src/service/request';

// 获取所有的工艺路线
export const getAllTechnologyList = (data: any) => {
  return request.get('/mes/mesProcessRouting/list', { params: data });
};
// 获取所有的工艺路线不分页
export const getTechnologyListLine = () => {
  return request.get('/mes/mesProcessRouting/selectall');
};

// 新增工艺路线
export const fetchAddTechnology = (data: any) => {
  return request.post('/mes/mesProcessRouting', data);
};

// 编辑工艺路线
export const fetchEditTechnology = (data: any) => {
  return request.put('/mes/mesProcessRouting', data);
};

// 删除工艺路线
export const fetchDeleteTechnology = (id: number) => {
  return request.delete(`/mes/mesProcessRouting/${id}`);
};

// 新增工序
export function fetchAddProcess(data: Tline.addProcessType) {
  return request.post('/mes/mesRoutingWorkstage', data);
}
// 获取所有工序
export function getAllProcessList() {
  return request.get('/mes/mesWorkstage/selectall');
}
// 根据工艺路线id获取工序
export function getProcessList(id: number) {
  return request.get(`/mes/mesRoutingWorkstage/selectall`, { params: { routingId: id } });
}

// 根据工艺路线id和产品id获取工序
export function getProcessListByProduct(id: number, productId: number) {
  return request.get(`/mes/mesProductBom/selectall`, { params: { routingId: id, productId } });
}

// 删除工序
export function fetchDeleteProcess(id: number) {
  return request.delete(`/mes/mesRoutingWorkstage/${id}`);
}
