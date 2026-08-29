<template>
  <n-breadcrumb class="px-12px">
    <template v-for="breadcrumb in breadcrumbs" :key="breadcrumb.key">
      <n-breadcrumb-item>
        <n-dropdown
          v-if="breadcrumb.hasChildren"
          :render-icon="renderMenuIcon"
          :options="breadcrumb.options"
          @select="dropdownSelect"
        >
          <span>
            <img
              v-if="theme.header.crumb.showIcon"
              :src="getImg(`../../${breadcrumb.iconName}`)"
              class="inline-block align-text-bottom mr-4px text-16px"
            />
            <span>{{ breadcrumb.label }}</span>
          </span>
        </n-dropdown>
        <template v-else>
          <img
            v-if="theme.header.crumb.showIcon"
            :src="getImg(`../../${breadcrumb.iconName}`)"
            class="inline-block align-text-bottom mr-4px text-16px"
            :class="{ 'text-#BBBBBB': theme.header.inverted }"
          />
          <span :class="{ 'text-#BBBBBB': theme.header.inverted }">
            {{ breadcrumb.label }}
            <!-- 99999 -->
          </span>
        </template>
      </n-breadcrumb-item>
    </template>
  </n-breadcrumb>
</template>

<script setup lang="ts">
import { computed, h } from 'vue';
import { useRoute } from 'vue-router';
import { routePath } from '@/router';
import { useRouteStore, useThemeStore } from '@/store';
import { useRouterPush } from '@/composables';
import { getBreadcrumbByRouteKey } from '@/utils';
import { $t } from '@/locales';

defineOptions({ name: 'GlobalBreadcrumb' });

const route = useRoute();
const theme = useThemeStore();
const routeStore = useRouteStore();
const { routerPush } = useRouterPush();
const breadcrumbs: any = computed(() =>
  getBreadcrumbByRouteKey(route.name as string, routeStore.menus as App.GlobalMenuOption[], routePath('root')).map(
    item => ({
      ...item,
      label: item.i18nTitle ? $t(item.i18nTitle) : item.label,
      options: item.options?.map(oItem => ({ ...oItem, label: oItem.i18nTitle ? $t(oItem.i18nTitle) : oItem.label }))
    })
  )
);
function getImg(name) {
  return new URL(name, import.meta.url).href;
}
function renderMenuIcon(option) {
  return h('img', { src: getImg(`../../${option.iconName}`) });
}
function dropdownSelect(key: string) {
  routerPush({ name: key });
}
</script>

<style scoped></style>
