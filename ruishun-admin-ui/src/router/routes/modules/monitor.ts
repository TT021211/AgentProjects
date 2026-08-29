export const monitor: AuthRoute.Route[] = [
  {
    name: 'monitor_jobLog',
    path: '/monitor/jobLog',
    component: 'self',
    meta: {
      title: '调度日志',
      singleLayout: 'blank',
      icon: '../../assets/svg-icon/home.svg',
      hide: true
    }
  }
];
