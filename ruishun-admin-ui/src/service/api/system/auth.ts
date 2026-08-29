import { mockRequest, request } from '../../request';
/**
 * 获取验证码
 * @param phone - 手机号
 * @returns - 返回boolean值表示是否发送成功
 */
export function fetchSmsCode(phone: string) {
  console.log(mockRequest.post<boolean>('/getSmsCode', { phone }));
  return mockRequest.post<boolean>('/getSmsCode', { phone });
}

/**
 * 登录
 * @param userName - 用户名
 * @param password - 密码
 */
// eslint-disable-next-line max-params
export function fetchLogin(username: string, password: string, code: string, uuid: string) {
  // return request.post<ApiAuth.Token>(`/login?username=${userName}&password=${password}`);
  return request.post<ApiAuth.Token>('/auth/login', { username, password, code, uuid });
}

// 获取验证码
export function getImageCode() {
  // return request.post<ApiAuth.Token>(`/login?username=${userName}&password=${password}`);
  return request.get('/code');
}

/** 获取用户信息 */
export function fetchUserInfo() {
  return request.get('/system/user/getInfo');
}

/**
 * 获取用户路由数据
 * @param userId - 用户id
 * @description 后端根据用户id查询到对应的角色类型，并将路由筛选出对应角色的路由数据返回前端
 */
export function fetchUserRoutes() {
  return request.get('/system/menu/getRouters');
}

/**
 * 刷新token
 * @param refreshToken
 */
export function fetchUpdateToken(refreshToken: string) {
  return mockRequest.post<ApiAuth.Token>('/updateToken', { refreshToken });
}
