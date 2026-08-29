<template>
  <div class="fixed-center flex-col">
    <system-logo class="text-128px text-primary" />
    <div class="w-56px h-56px my-36px">
      <div class="relative h-full animate-spin">
        <div
          v-for="(item, index) in loadingClasses"
          :key="index"
          class="absolute w-16px h-16px bg-primary rounded-8px animate-pulse"
          :class="item"
        ></div>
      </div>
    </div>
    <h2 class="text-28px font-500 text-#646464">{{ $t('system.title') }}</h2>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useDialog, useMessage } from 'naive-ui';
import { sessionStg, getRgbOfColor } from '@/utils';
import { $t } from '@/locales';
import themeSettings from '@/settings/theme.json';
import { useAuthStore } from '@/store/index';

const router = useRouter();
const message = useMessage();
const dialog = useDialog();
// const router = useRouter();
// onBeforeRouteUpdate((to) => {
//         // console.log('onBeforeRouteUpdate',to.path);
//  });
const route = useRoute();

const loadingClasses = [
  'left-0 top-0',
  'left-0 bottom-0 animate-delay-500',
  'right-0 top-0 animate-delay-1000',
  'right-0 bottom-0 animate-delay-1500'
];

function addThemeColorCssVars() {
  const defaultColor = themeSettings.themeColor;
  const themeColor = sessionStg.get('themeColor') || defaultColor;

  const { r, g, b } = getRgbOfColor(themeColor);

  const cssVars = `--primary-color: ${r},${g},${b}`;
  document.documentElement.style.cssText = cssVars;
}

addThemeColorCssVars();
const errMap = {
  '401': '登录用户不存在',
  '402': '该用户已被删除',
  '403': '该账号已被停用'
};
const { handleActionAfterLogin } = useAuthStore();
onMounted(() => {
  // console.log(router.currentRoute.value.fullPath);
  if (route.query.access_token) {
    handleActionAfterLogin({ access_token: route.query.access_token });
  }
  if (route.query.errorCode) {
    console.log(route.query.errorCode, errMap[route.query.errorCode as string]);
    dialog.error({
      style: { borderRadius: '10px' },
      title: '错误',
      onClose: () => {
        router.push('login/company-login');
      },
      maskClosable: false,
      content: errMap[route.query.errorCode as string],
      positiveText: '确定',
      onPositiveClick: () => {
        router.push('login/company-login');
      }
    });
  }
  if (!route.query.errorCode && !route.query.access_token) {
    setTimeout(() => {
      message.error('登录超时，请重新登录');
      router.push('login/company-login');
    }, 5000);
  }
});
</script>

<style scoped></style>
