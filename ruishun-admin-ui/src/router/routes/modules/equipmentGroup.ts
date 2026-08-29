export const equipmentGroup: AuthRoute.Route[] = [
  {
    name: 'device_pointInspection_groupEquipment',
    path: '/device/pointInspection/groupEquipment',
    component: 'self',
    meta: {
      title: '分配设备',
      singleLayout: 'blank',
      icon: '../../assets/svg-icon/home.svg',
      hide: true
    }
  }
];
