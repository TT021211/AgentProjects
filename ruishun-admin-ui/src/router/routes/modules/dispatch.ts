export const dispatch: AuthRoute.Route[] = [
  {
    name: 'system_dispacthRole',
    path: '/system/dispacthRole',
    component: 'self',
    meta: {
      title: '分配用户',
      singleLayout: 'blank',
      icon: '../../assets/svg-icon/home.svg',
      hide: true
    }
  },
  {
    name: 'system_dispacthUser',
    path: '/system/dispacthUser',
    component: 'self',
    meta: {
      title: '分配角色',
      singleLayout: 'blank',
      icon: '../../assets/svg-icon/home.svg',
      hide: true
    }
  }
];
