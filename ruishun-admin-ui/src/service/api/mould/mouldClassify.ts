import { request } from '../../request';

export function getMouldClassify() {
  return request.get('/mould/classify/list');
}
