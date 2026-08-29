<template>
  <n-modal v-model:show="show" :mask-closable="false">
    <div class="w-200px h-150px bg-gray border-rd-30px flex-col items-center justify-center">
      <dv-loading>
        <div color-white>{{ title }}</div>
      </dv-loading>
      <!-- <n-spin size="large" />
      <n-gradient-text type="info" class="font-size-20px mt-10px">{{ title }}</n-gradient-text> -->
    </div>
  </n-modal>
</template>

<script setup lang="ts">
const show = defineModel('show', { type: Boolean, default: false });
withDefaults(
  defineProps<{
    title?: string;
  }>(),
  {
    title: '加载中...'
  }
);
</script>

<style scoped></style>
