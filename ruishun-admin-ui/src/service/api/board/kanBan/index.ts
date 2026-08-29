import { request } from '~/src/service/request';

// 获取库存汇总
export const getWmsKanban = () => {
  return request.get('/wms/wmsIngredientsLog/getWmsKanban');
};

// 获取不良率统计
export const getQcKanbanPage = () => {
  return request.get('/qc/qcInspectionSummart/qcCountData');
};

// 获取能耗
export const getEcmElectricityConsumption = () => {
  return request.get('/ecm/ecmElectricityConsumption/getKanban');
};

// 获取生产
export const getMesProductWorkorder = () => {
  return request.get('/mes/mesProductWorkorder/getkanbanPage');
};
