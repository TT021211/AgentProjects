import { request } from '~/src/service/request';

// 获取半成品信息
export const getByIdentifier = (codeSn: string) => {
  return request.get(`/wms/wms/stock/getByIdentifier/${codeSn}`);
};

export const editWriteBackWight = (data: object) => {
  return request.put(`/wms/wms/stock/WriteBackWight`, data);
};
