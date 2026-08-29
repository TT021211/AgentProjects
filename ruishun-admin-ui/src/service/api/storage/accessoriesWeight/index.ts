import { request } from '~/src/service/request';

// 获取当天流水记录
export const getAccessoriesType = () => {
  return request.get(`/md/mdItemType/selectall`);
};
