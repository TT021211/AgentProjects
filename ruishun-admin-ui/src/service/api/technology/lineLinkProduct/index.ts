import { request } from '@/service/request';

// 通过工艺路线id获取产品列表
export function getProductList(data: LineProduct.searchForm) {
  return request.get('/mes/mesProductRouting/list', { params: { ...data } });
}
// 删除工艺路线对应的产品
export function fetchDeleteProduct(id: number) {
  return request.delete(`/mes/mesProductRouting/${id}`);
}
// 产品关联工艺路线
export const fetchAddTechnologyLine = (data: ProductLine.link) => {
  return request.post('/mes/mesProductRouting', data);
};
