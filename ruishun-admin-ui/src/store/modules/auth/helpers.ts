import { fetchUserInfo } from '@/service';
import { localStg } from '@/utils';
/** 获取token */
export function getToken() {
  return localStg.get('token') || '';
}

/** 获取用户信息 */
export function getUserInfo() {
  const emptyInfo: Auth.UserInfo = {
    userId: '',
    userName: '',
    userRole: 'common',
    avatar: '',
    roles: [],
    permissions: [],
    nickName: '',
    sex: '',
    status: ''
  };
  const userInfo: Auth.UserInfo = localStg.get('userInfo') || emptyInfo;

  return userInfo;
}

/** 去除用户相关缓存 */
export function clearAuthStorage() {
  localStg.remove('token');
  localStg.remove('refreshToken');
  localStg.remove('userInfo');
}
export async function getUserInfoRefresh() {
  const result = await fetchUserInfo();

  const { roles, user, permissions } = result;
  const userInfo: Auth.UserInfo = {
    userId: user.userId,
    userName: user.userName,
    nickName: user.nickName,
    userRole: roles[0],
    avatar: user.avatar,
    roles,
    permissions,
    sex: '',
    status: ''
  };
  // 从后端获取信息

  return userInfo;
}
