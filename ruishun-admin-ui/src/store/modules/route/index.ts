import { defineStore } from 'pinia';
import { ROOT_ROUTE, constantRoutes, router, routes as staticRoutes } from '@/router';
import { fetchUserRoutes } from '@/service';
import {
  localStg,
  filterAuthRoutesByUserPermission,
  getCacheRoutes,
  getConstantRouteNames,
  transformAuthRouteToVueRoutes,
  transformAuthRouteToVueRoute,
  transformAuthRouteToMenu,
  transformAuthRouteToSearchMenus,
  transformRouteNameToRoutePath,
  transformRoutePathToRouteName,
  sortRoutes
} from '@/utils';
import { home, dispatch } from '@/router/routes/modules';
import { userinfo } from '@/router/routes/modules/userinfo';
import { workGroup } from '@/router/routes/modules/workgroup';
import { produce } from '@/router/routes/modules/produce';
import { eqclassify } from '@/router/routes/modules/eqclassify';
import { equipmentGroup } from '@/router/routes/modules/equipmentGroup';
import { print } from '@/router/routes/modules/print';
import { board } from '@/router/routes/modules/board';
import { monitor } from '@/router/routes/modules/monitor';
import { useAppStore } from '../app';
import { useAuthStore } from '../auth';
import { useTabStore } from '../tab';

interface RouteState {
  /**
   * 权限路由模式:
   * - static - 前端声明的静态
   * - dynamic - 后端返回的动态
   */
  authRouteMode: ImportMetaEnv['VITE_AUTH_ROUTE_MODE'];
  /** 是否初始化了权限路由 */
  isInitAuthRoute: boolean;
  /** 路由首页name(前端静态路由时生效，后端动态路由该值会被后端返回的值覆盖) */
  routeHomeName: AuthRoute.AllRouteKey;
  /** 菜单 */
  menus: App.GlobalMenuOption[];
  /** 搜索的菜单 */
  searchMenus: AuthRoute.Route[];
  /** 缓存的路由名称 */
  cacheRoutes: string[];
}
/** ruoyi后端返回的数据格式 */
type RuoYiRoute = {
  name: string;
  /** 路由路径 */
  path: string;
  /** 路由重定向 */
  redirect?: string;
  hide?: boolean;
  hidden?: boolean;
  component?: string;
  /** 子路由 */
  children?: RuoYiRoute[] | null;
  alwaysShow?: boolean;
  /** 路由描述 */
  meta: RuoYiRouteMeta;
};
type RuoYiRouteMeta = {
  title: string;
  icon: string;
  noCache?: boolean;
  keepAlive?: boolean;
  link?: string | null;
  singleLayout?: string;
  requiresAuth?: boolean;
  hide?: boolean;
};
export const useRouteStore = defineStore('route-store', {
  state: (): RouteState => ({
    authRouteMode: import.meta.env.VITE_AUTH_ROUTE_MODE,
    isInitAuthRoute: false,
    routeHomeName: transformRoutePathToRouteName(import.meta.env.VITE_ROUTE_HOME_PATH),
    menus: [],
    searchMenus: [],
    cacheRoutes: []
  }),
  actions: {
    /** 重置路由的store */
    resetRouteStore() {
      this.resetRoutes();
      this.$reset();
    },
    /** 重置路由数据，保留固定路由 */
    resetRoutes() {
      const routes = router.getRoutes();
      routes.forEach(route => {
        const name = (route.name || 'root') as AuthRoute.AllRouteKey;
        if (!this.isConstantRoute(name)) {
          router.removeRoute(name);
        }
      });
    },
    /**
     * 是否是固定路由
     * @param name 路由名称
     */
    isConstantRoute(name: AuthRoute.AllRouteKey) {
      const constantRouteNames = getConstantRouteNames(constantRoutes);
      return constantRouteNames.includes(name);
    },
    /**
     * 是否是有效的固定路由
     * @param name 路由名称
     */
    isValidConstantRoute(name: AuthRoute.AllRouteKey) {
      const NOT_FOUND_PAGE_NAME: AuthRoute.NotFoundRouteKey = 'not-found';
      const constantRouteNames = getConstantRouteNames(constantRoutes);
      return constantRouteNames.includes(name) && name !== NOT_FOUND_PAGE_NAME;
    },
    /**
     * 处理权限路由
     * @param routes - 权限路由
     */
    handleAuthRoute(routes: AuthRoute.Route[]) {
      (this.menus as App.GlobalMenuOption[]) = transformAuthRouteToMenu(routes);
      this.searchMenus = transformAuthRouteToSearchMenus(routes);

      const vueRoutes = transformAuthRouteToVueRoutes(routes);

      vueRoutes.forEach(route => {
        router.addRoute(route);
      });
      this.cacheRoutes = getCacheRoutes(vueRoutes);
    },
    /** 动态路由模式下：更新根路由的重定向 */
    handleUpdateRootRedirect(routeKey: AuthRoute.AllRouteKey) {
      if (routeKey === 'root' || routeKey === 'not-found') {
        throw new Error('routeKey的值不能为root或者not-found');
      }
      const rootRoute: AuthRoute.Route = { ...ROOT_ROUTE, redirect: transformRouteNameToRoutePath(routeKey) };
      const rootRouteName: AuthRoute.AllRouteKey = 'root';
      router.removeRoute(rootRouteName);
      const rootVueRoute = transformAuthRouteToVueRoute(rootRoute)[0];
      router.addRoute(rootVueRoute);
    },
    /** 初始化动态路由 */
    async initDynamicRoute() {
      const { resetAuthStore } = useAuthStore();
      const { initHomeTab } = useTabStore();

      const { userId } = localStg.get('userInfo') || {};

      if (!userId) {
        throw new Error('userId 不能为空!');
      }
      // const { resetAuthStore } = useAuthStore();
      // 请求路由数据
      // const { error, data } = await fetchUserRoutes(userId);
      const { error, data } = await fetchUserRoutes();
      if (!data) {
        // router.push('/login');
        location.href = '/#/login';
      }
      // 路由格式转换
      const resultData: any = this.transforRule(data);
      resultData.unshift(home);

      resultData.unshift(print);
      resultData.unshift(board);
      resultData.forEach(item => {
        if (item.name === 'system') {
          if (!item.children) {
            item.children = [];
          }
          item.children.unshift(...dispatch);
        }
        if (item.name === 'md') {
          if (!item.children) {
            item.children = [];
          }
          item.children.unshift(...workGroup);
        }
        if (item.name === 'produce') {
          if (!item.children) {
            item.children = [];
          }
          item.children.unshift(...produce);
        }
        if (item.name === 'device') {
          if (!item.children) {
            item.children = [];
          }
          item.children.forEach(route => {
            if (route.name === 'device_pointInspection') {
              route.children.unshift(...eqclassify);
            }
          });
          item.children.forEach(route => {
            if (route.name === 'device_pointInspection') {
              route.children.unshift(...equipmentGroup);
            }
          });
        }
        if (item.name === 'monitor') {
          if (!item.children) {
            item.children = [];
          }
          item.children.unshift(...monitor);
        }
      });
      resultData.unshift(userinfo);

      if (!error) {
        this.handleAuthRoute(sortRoutes(resultData));
        // home相关处理需要在最后，否则会出现找不到主页404的情况
        // 必须指定默认页面
        const firstPage = 'home';
        this.routeHomeName = firstPage;
        this.handleUpdateRootRedirect(firstPage);
        initHomeTab(firstPage, router);
        this.isInitAuthRoute = true;
      } else {
        resetAuthStore();
      }
    },
    /** 初始化静态路由 */
    async initStaticRoute() {
      const { initHomeTab } = useTabStore();
      const auth = useAuthStore();
      const routes = filterAuthRoutesByUserPermission(staticRoutes, auth.userInfo.userRole);
      this.handleAuthRoute(routes);
      initHomeTab(this.routeHomeName, router);
      this.isInitAuthRoute = true;
    },
    /** 初始化权限路由 */
    async initAuthRoute() {
      if (this.authRouteMode === 'dynamic') {
        await this.initDynamicRoute();
      } else {
        await this.initStaticRoute();
      }
    },
    /** 从缓存路由中去除某个路由 */
    removeCacheRoute(name: AuthRoute.AllRouteKey) {
      const index = this.cacheRoutes.indexOf(name);
      if (index > -1) {
        this.cacheRoutes.splice(index, 1);
      }
    },
    // ruoyi路由格式转soybean格式
    transforRule(data: RuoYiRoute[]) {
      if (!data) {
        // eslint-disable-next-line no-param-reassign
        data = [];
      }
      return data.map(item => {
        const itemName = item.name.toLowerCase();

        // eslint-disable-next-line @typescript-eslint/no-shadow
        function menuInfo(fName, fPath, item) {
          return item?.children?.map(child => {
            return {
              name: `${fName}_${child.path}`,
              path: `${fPath}/${child.path}`,
              // eslint-disable-next-line no-prototype-builtins
              component: child.hasOwnProperty('children') ? 'multi' : 'self',
              meta: {
                title: child.meta.title,
                requiresAuth: true,
                icon: child.meta.icon,
                hide: child.hidden
              }
            };
          });
        }
        const children = item?.children?.map(child => {
          const name = `${itemName}_${child.path}`;
          const path = `${item.path}/${child.path}`;
          return {
            name,
            path,
            // eslint-disable-next-line no-prototype-builtins
            component: child.hasOwnProperty('children') ? 'multi' : 'self',
            meta: {
              title: child.meta.title,
              requiresAuth: true,
              icon: child.meta.icon,
              hide: child.hidden,
              keepAlive: child.meta.noCache
            },
            children: menuInfo(name, path, child)
          };
        });

        const obj: RuoYiRoute = {
          name: itemName,
          path: item.path,

          // eslint-disable-next-line no-prototype-builtins
          component: item.hasOwnProperty('children') ? 'basic' : 'self',
          meta: {
            title: item.meta.title,
            requiresAuth: true,
            icon: item.meta.icon,
            hide: item.hidden
          },
          children
        };

        // 判断是否是单页面组件
        // eslint-disable-next-line no-prototype-builtins
        if (!item.hasOwnProperty('children')) {
          obj.meta.singleLayout = 'basic';
          delete obj.children;
        }
        return obj;
      });
    },
    /** 添加某个缓存路由 */
    addCacheRoute(name: AuthRoute.AllRouteKey) {
      const index = this.cacheRoutes.indexOf(name);
      if (index === -1) {
        this.cacheRoutes.push(name);
      }
    },
    /**
     * 重新缓存路由
     */
    async reCacheRoute(name: AuthRoute.AllRouteKey) {
      const { reloadPage } = useAppStore();

      const isCached = this.cacheRoutes.includes(name);

      if (isCached) {
        this.removeCacheRoute(name);
      }

      await reloadPage();

      if (isCached) {
        this.addCacheRoute(name as AuthRoute.AllRouteKey);
      }
    }
  }
});
