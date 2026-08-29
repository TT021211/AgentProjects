import { request } from '~/src/service/request';

// 获取配件列表
export function updateAccessoryRecord(params: any) {
  return request.get('eq/eqAttachment/listAll', { params });
}
