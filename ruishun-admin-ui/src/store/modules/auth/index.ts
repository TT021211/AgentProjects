import { unref, nextTick } from 'vue';
import { defineStore } from 'pinia';
import { router } from '@/router';
import { fetchLogin } from '@/service';
import { useRouterPush } from '@/composables';
import { localStg } from '@/utils';
import { $t } from '@/locales';
import { useTabStore } from '../tab';
import { useRouteStore } from '../route';
import { getToken, getUserInfo, clearAuthStorage, getUserInfoRefresh } from './helpers';
interface AuthState {
  /** 用户信息 */
  userInfo: Auth.UserInfo;
  /** 用户token */
  token: string;
  /** 登录的加载状态 */
  loginLoading: boolean;
}
export const useAuthStore = defineStore('auth-store', {
  state: (): AuthState => ({
    userInfo: getUserInfo(),
    token: getToken(),
    loginLoading: false
  }),
  getters: {
    /** 是否登录 */
    isLogin(state) {
      return Boolean(state.token);
    }
  },
  actions: {
    /** 重置auth状态 */
    resetAuthStore() {
      const { toLogin } = useRouterPush(false);
      const { resetTabStore } = useTabStore();
      const { resetRouteStore } = useRouteStore();
      const route = unref(router.currentRoute);

      clearAuthStorage();
      this.$reset();

      if (route.meta.requiresAuth) {
        console.log('toLogin');
        toLogin();
      }

      nextTick(() => {
        resetTabStore();
        resetRouteStore();
      });
    },
    /**
     * 处理登录后成功或失败的逻辑
     * @param backendToken - 返回的token
     */
    async handleActionAfterLogin(
      backendToken: ApiAuth.Token,
      fun = () => {
        console.log(1);
      }
    ) {
      const route = useRouteStore();
      const { toLoginRedirect } = useRouterPush(false);
      const loginSuccess = await this.loginByToken(backendToken);
      if (loginSuccess) {
        await route.initAuthRoute();
        // 跳转登录后的地址
        toLoginRedirect();
        // 登录成功弹出欢迎提示
        if (route.isInitAuthRoute) {
          window.$notification?.success({
            title: $t('page.login.common.loginSuccess'),
            content: $t('page.login.common.welcomeBack', { userName: this.userInfo.userName }),
            duration: 3000
          });
        }
        return;
      }
      // 不成功则重置状态
      this.resetAuthStore();
      fun();
    },
    /**
     * 根据token进行登录
     * @param backendToken - 返回的token
     */
    async loginByToken(backendToken: ApiAuth.Token) {
      let successFlag = false;

      // 先把token存储到缓存中(后面接口的请求头需要token)
      const { access_token } = backendToken;
      localStg.set('token', access_token);

      // localStg.set('refreshToken', refreshToken);
      // console.log(localStg.get('token')+'000')
      // 获取用户信息

      let userdata;
      await getUserInfoRefresh().then(res => {
        userdata = res;
      });

      if (userdata) {
        // 成功后把用户信息存储到缓存中
        localStg.set('userInfo', userdata);
        // 更新状态
        this.userInfo = userdata;
        this.token = access_token;

        successFlag = true;
      }
      // 获取到用户信息之后返回true
      return successFlag;
    },

    /**
     * 登录
     * @param userName - 用户名
     * @param password - 密码
     */
    // eslint-disable-next-line max-params
    async login(
      username: string,
      password: string,
      code: string,
      uuid: string,
      fun = () => {
        console.log(1);
      }
    ) {
      this.loginLoading = true;
      const { data } = await fetchLogin(username, password, code, uuid);
      if (data) {
        await this.handleActionAfterLogin(data, fun);
      }
      fun();
      this.loginLoading = false;
    }
    /**
     * 更换用户权限(切换账号)
     * @param userRole
     */
    // async updateUserRole(userRole: Auth.RoleType) {
    //   const { resetRouteStore, initAuthRoute } = useRouteStore();

    //   const accounts: Record<Auth.RoleType, { userName: string; password: string }> = {
    //     super: {
    //       userName: 'Super',
    //       password: 'super123'
    //     },
    //     admin: {
    //       userName: 'Admin',
    //       password: 'admin123'
    //     },
    //     user: {
    //       userName: 'User01',
    //       password: 'user01123'
    //     }
    //   };
    //   const { userName, password } = accounts[userRole];
    //   const { data } = await fetchLogin(userName, password);
    //   if (data) {
    //     await this.loginByToken(data);
    //     resetRouteStore();
    //     initAuthRoute();
    //   }
    // }
  }
});
