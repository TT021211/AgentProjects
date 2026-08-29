export const home: AuthRoute.Route = {
  name: 'home',
  path: '/home',
  component: 'self',
  meta: {
    title: '首页',
    requiresAuth: true,
    keepAlive: true,
    singleLayout: 'basic',
    icon: '../../assets/svg-icon/home.svg',
    order: 0
  }
};
