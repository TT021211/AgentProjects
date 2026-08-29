export const produce: AuthRoute.Route[] = [
  {
    name: 'produce_feed',
    path: '/produce/feed',
    component: 'self',
    meta: {
      title: '投料记录',
      singleLayout: 'blank',
      icon: '../../assets/svg-icon/home.svg',
      hide: true
    }
  },
  {
    name: 'produce_report',
    path: '/produce/report',
    component: 'self',
    meta: {
      title: '报工记录',
      singleLayout: 'blank',
      icon: '../../assets/svg-icon/home.svg',
      hide: true
    }
  }
];
