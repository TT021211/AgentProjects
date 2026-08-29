<template>
  <div class="relative flex-center wh-full" :style="{ backgroundColor: bgColor }">
    <!-- 切换暗黑主题 -->
    <!-- <dark-mode-switch
      :dark="theme.darkMode"
      class="absolute left-48px top-24px z-3 text-20px"
      @update:dark="theme.setDarkMode"
    /> -->
    <div class="logbg z-4 w-825px h-500px my-center my-shadow">
      <div style="margin: 0 auto; width: 45%; height: 100%; padding: 5px">
        <div class="topTitle">
          <img style="width: 70px; height: 70px" src="@/assets/img/sys.png" alt="" />
          <span style="font-size: 30px; color: #f3f3f3">江西瑞顺超细铜线</span>
        </div>
        <div>
          <main>
            <div>
              <transition name="fade-slide" mode="out-in" appear>
                <component :is="activeModule.component" />
              </transition>
            </div>
          </main>
        </div>
        <img
          style="float: right; width: 20px; position: absolute; right: 0px; bottom: 0px"
          src="@/assets/img/setting.png"
          @click="changeLoginType"
        />
      </div>
    </div>
    <!-- 登录框 -->
    <!-- <n-card :bordered="false" size="large" class="z-4 !w-auto rounded-20px shadow-sm ">
      <div
        class="loginType"
        :style="{ '--soybean-primary': theme.darkMode ? 'rgb(24, 24, 28)' : 'white' }"
        @click="changeLoginType"
      >
        <span v-if="loginTypeFlag"><icon-ic-baseline-qr-code-scanner class="mr-4px text-50px text-#36748f" /></span>
        <span v-else><icon-carbon-cloud-service-management class="mr-4px text-50px text-#36748f" /></span>
      </div>
      <div class="w-300px sm:w-360px">
        <header class="flex-y-center">
          <system-logo class="text-64px text-primary w-120px" />
          <n-gradient-text type="primary" :size="28">{{ $t('system.title') }}</n-gradient-text>
        </header>
        <main class="pt-24px" style="margin: 10px auto">
          <div class="pt-24px">
            <transition name="fade-slide" mode="out-in" appear>
              <component :is="activeModule.component" />
            </transition>
          </div>
        </main>
      </div>
    </n-card> -->

    <login-bg :theme-color="bgThemeColor" />
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import type { Component } from 'vue';
import { loginModuleLabels } from '@/constants';
import { useThemeStore } from '@/store';
import { useRouterPush } from '@/composables';
import { getColorPalette, mixColor } from '@/utils';
// import { $t } from '@/locales';
import { CompanyLogin, BindWechat, CodeLogin, LoginBg, PwdLogin, Register, ResetPwd } from './components';

interface Props {
  /** 登录模块分类 */
  module: UnionKey.LoginModule;
}

const props = withDefaults(defineProps<Props>(), {
  module: 'pwd-login'
});
const { toLoginModule } = useRouterPush();
const theme = useThemeStore();

const loginTypeFlag = ref(false);

interface LoginModule {
  key: UnionKey.LoginModule;
  label: string;
  component: Component;
}

const modules: LoginModule[] = [
  { key: 'pwd-login', label: loginModuleLabels['pwd-login'], component: PwdLogin },
  { key: 'code-login', label: loginModuleLabels['code-login'], component: CodeLogin },
  { key: 'register', label: loginModuleLabels.register, component: Register },
  { key: 'reset-pwd', label: loginModuleLabels['reset-pwd'], component: ResetPwd },
  { key: 'bind-wechat', label: loginModuleLabels['bind-wechat'], component: BindWechat },
  { key: 'company-login', label: loginModuleLabels['company-login'], component: CompanyLogin }
];

const activeModule = computed(() => {
  // const active: LoginModule = { ...modules[0] };
  // const findItem = modules.find(item => item.key === props.module);
  // if (findItem) {
  //   Object.assign(active, findItem);
  // }
  // return active;
  const findItem = modules.find(item => item.key === props.module);
  return findItem || modules[0];
});

// const activeComponent = ref<any>(null);

const bgThemeColor = computed(() => (theme.darkMode ? getColorPalette(theme.themeColor, 7) : theme.themeColor));

const bgColor = computed(() => {
  const COLOR_WHITE = '#ffffff';
  const ratio = theme.darkMode ? 0.5 : 0.2;
  return mixColor(COLOR_WHITE, theme.themeColor, ratio);
});

function changeLoginType() {
  loginTypeFlag.value = !loginTypeFlag.value;
  // console.log(e);
  // loginTypeFlag.value = !loginTypeFlag.value;
  if (activeModule.value.key !== 'company-login') {
    // activeComponent.value = CompanyLogin;
    toLoginModule('company-login');
  } else {
    toLoginModule('pwd-login');
    // activeComponent.value = PwdLogin;
  }
}
onMounted(() => {
  if (activeModule.value.key === 'pwd-login') loginTypeFlag.value = true;
  else loginTypeFlag.value = false;
});
// onMounted(() => {
//   console.log(0);
//   activeComponent.value = PwdLogin;
// });
</script>

<style scoped>
.pt-74px {
  padding-top: 20px;
}
.topTitle {
  width: 100%;
  padding-top: 30px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
.fade-slide-enter-from,
.fade-slide-leave-to {
  transform: scale(0);
  /* animation: bounce-in 0.5s; */
  /* animation: bounce-in 0.5s reverse; */
  opacity: 0;
}
.fade-slide-enter-active,
.fade-slide-leave-active {
  transition: all 0.6s ease-in-out;
}
/* .fade-slide-enter-active {
  animation: bounce-in 0.5s;
}
.fade-slide-leave-active {
  animation: bounce-in 0.5s reverse;
} */
@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  50% {
    transform: scale(1.15);
  }
  100% {
    transform: scale(1);
  }
}
/* 绝对定位剧中 */
.my-center {
  display: flex;
  justify-content: space-between;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
/* .logowhite {
  background: url('@/assets/img/logowhite.png') no-repeat center center;
  background-size: cover;
} */
.flex-y-center {
  width: 100%;
  display: flex;
  justify-content: start;
}
.loginType {
  width: 60px;
  height: 60px;
  position: absolute;
  border-top: solid transparent;
  border-radius: 0 20px 0 0;
  top: 0px;
  right: 0px;
  background-color: #d5d5d5;
}

.center-box {
  width: 400px;
  display: flex;
  justify-content: center;
}
.loginType::before {
  transition: all 0.2s;
  content: '';
  position: absolute;
  top: -61px;
  /* left: -80px; */
  width: 0;
  height: 0;
  border-top: 60px solid transparent;
  border-right: 60px solid transparent;
  border-bottom: 60px solid var(--soybean-primary);
}
.logbg {
  background: url('@/assets/img/rs-bgPhoto.jpg') no-repeat center center;
  background-size: cover;
  border-top-right-radius: 40px;
  border-bottom-left-radius: 40px;
}
.my-shadow {
  box-shadow: 0px 10px 20px 5px rgba(19, 0, 0, 0.1);
}
</style>
