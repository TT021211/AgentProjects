// import { transforUserData } from '@/utils';
import { request } from '../../request';
// import { adapterOfFetchUserList } from './management.adapter';

/** 获取用户列表 */
export const fetchUserList = async query => {
  const { rows, total } = await request.get('/system/user/list', query);
  return { rows, total };
};
