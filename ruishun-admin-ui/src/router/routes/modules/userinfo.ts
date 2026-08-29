export const userinfo: AuthRoute.Route = {
  name: 'userinfo',
  path: '/userinfo',
  component: 'self',
  meta: {
    requiresAuth: true,
    title: '个人信息',
    singleLayout: 'basic',
    icon: '../../assets/svg-icon/home.svg',
    hide: true
  }
};
