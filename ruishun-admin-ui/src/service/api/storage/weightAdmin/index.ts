import { request } from '~/src/service/request';

// 获取当天流水记录
export const selectMaxValues = () => {
  return request.get(`/wms/wmsWeighRecord/selectMaxValues`);
};
// 获取对应产品的规格数据
export const getSpecList = specName => {
  return request.get(`/md/mdProduct/getSpecList?specName=${specName}`);
};
// 获取原料最大批号料号
export const getLocalSpecList = trade => {
  return request.get(`wms/wmsIngredientsLog/selectMaxBatch/${trade}`);
};
