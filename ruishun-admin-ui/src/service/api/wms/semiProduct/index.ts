import { request } from '@/service/request';

// 获取半成品出入库
export function getMesOutputLogWmsList(params: any) {
  return request.get('mes/mesOutputLog/wmsList', { params });
}
