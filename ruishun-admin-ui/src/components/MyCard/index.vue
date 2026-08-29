<template>
  <div>
    <transition :name="search ? 'top-fade' : 'bottom-fade'" :appear="appear">
      <n-card class="mb-10px shadow-sm rounded-10px">
        <template #header>
          <div class="header-style">
            <n-icon :size="20" color="#646CFF" class="mr-3px">
              <SearchOutline v-if="props.search" />
              <ListSharp v-else />
            </n-icon>
            <span style="color: #646cff; opacity: 0.7">{{ props.title }}</span>
          </div>
        </template>
        <template #header-extra>
          <slot name="right"></slot>
        </template>
        <slot></slot>
      </n-card>
    </transition>
  </div>
</template>

<script setup lang="ts">
import { ListSharp, SearchOutline } from '@vicons/ionicons5';

const props = withDefaults(
  defineProps<{
    title?: string;
    search?: boolean;
    appear?: boolean;
  }>(),
  {
    title: '默认标题',
    search: false,
    appear: true
  }
);
</script>

<style scoped>
.top-fade-enter-active,
.top-fade-leave-active,
.bottom-fade-enter-active,
.bottom-fade-leave-active {
  transition: all 0.5s ease;
}
.top-fade-enter-from,
.top-fade-leave-to {
  opacity: 0;
  transform: translateY(-100px) scale(0);
}
.bottom-fade-enter-from,
.bottom-fade-leave-to {
  opacity: 0;
  transform: translateY(400px) scale(0);
}
.header-style {
  display: flex;
  align-items: center;
  color: #333;
}
:deep(.n-card-header) {
  padding: 10px;
  border-bottom: 1px solid #e8e8e8;
  margin-bottom: 10px;
}
:deep(.n-card__content) {
  padding: 10px;
}
</style>
