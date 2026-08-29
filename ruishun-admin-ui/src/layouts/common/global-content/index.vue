<template>
  <div>
    <router-view v-slot="{ Component, route }" class="h-full">
      <transition
        :name="theme.pageAnimateMode"
        mode="out-in"
        :appear="true"
        @before-leave="app.setDisableMainXScroll(true)"
        @after-enter="app.setDisableMainXScroll(false)"
      >
        <keep-alive :include="routeStore.cacheRoutes">
          <component
            :is="Component"
            v-if="app.reloadFlag"
            :key="route.path"
            :class="{ 'p-16px': showPadding }"
            class="flex-grow bg-#f6f9f8 dark:bg-#101014 transition duration-300 ease-in-out"
            style="min-height: calc(100vh - 0px)"
          />
        </keep-alive>
      </transition>
    </router-view>
    <n-watermark
      :content="waterMarkName"
      cross
      fullscreen
      :font-size="16"
      :line-height="16"
      :width="384"
      :height="384"
      :x-offset="12"
      :y-offset="60"
      :rotate="-15"
    />
  </div>
</template>

<script setup lang="ts">
import { watch, ref } from 'vue';
import { useAppStore, useRouteStore, useThemeStore, useAuthStore } from '@/store';
const authStore = useAuthStore();
const waterMarkName = ref<string>('432');
// 监听用户名字的改变
watch(
  () => authStore.userInfo.userName,
  newVal => {
    waterMarkName.value = `${newVal}`;
    // eslint-disable-next-line no-console
    console.log(authStore.userInfo, '水印');
  },
  { immediate: true, deep: true }
);
defineOptions({ name: 'GlobalContent' });

interface Props {
  /** 显示padding */
  showPadding?: boolean;
}

withDefaults(defineProps<Props>(), {
  showPadding: true
});

const app = useAppStore();
const theme = useThemeStore();
const routeStore = useRouteStore();
</script>

<style scoped>
.heitht-style {
  /* height: auto; */
  height: 100%;
  min-height: 100%;
  padding: 0px;
}
</style>
