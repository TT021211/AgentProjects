const md: AuthRoute.Route = {
  name: 'md',
  path: '/md',
  component: 'basic',
  meta: { title: 'md', icon: 'mdi:menu' },
  children: [
    { name: 'md_itemtype', path: '/md/itemtype', component: 'self', meta: { title: 'md_itemtype', icon: 'mdi:menu' } },
    { name: 'md_list', path: '/md/list', component: 'self', meta: { title: 'md_list', icon: 'mdi:menu' } },
    { name: 'md_unit', path: '/md/unit', component: 'self', meta: { title: 'md_unit', icon: 'mdi:menu' } },
    {
      name: 'md_workgroup',
      path: '/md/workgroup',
      component: 'self',
      meta: { title: 'md_workgroup', icon: 'mdi:menu' }
    },
    { name: 'md_workshop', path: '/md/workshop', component: 'self', meta: { title: 'md_workshop', icon: 'mdi:menu' } }
  ]
};

export default md;
