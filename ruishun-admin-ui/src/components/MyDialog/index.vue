<script setup lang="ts">
import { computed } from 'vue';
const props = withDefaults(
  defineProps<{
    show?: boolean;
    title?: string;
    width?: number | string;
    height?: number | string;
    subDisable?: boolean;
    showCancel?: boolean;
  }>(),
  { show: false, title: 'Modal Title', width: '450px', height: '300px', subDisable: false, showCancel: true }
);
const emit = defineEmits(['cancel', 'submit', 'update:show']);
const showModal = computed({
  get: () => props.show,
  set: value => {
    emit('update:show', value);
    if (!value) {
      cancel();
    }
  }
});
function cancel() {
  emit('cancel');
}
function handleSubmit() {
  emit('submit');
}
function catchWay(e: KeyboardEvent) {
  console.log('11');
  if (e.key === 'Esc' || e.key === 'Escape') {
    emit('cancel');
  }
}
</script>

<template>
  <n-modal v-model:show="showModal" :mask-closable="false" :closable="true">
    <template #default>
      <div>
        <n-card
          :title="props.title"
          aria-modal="true"
          :style="{ width: props.width, 'min-height': props.height }"
          class="rounded-10px"
        >
          <template #header-extra>
            <slot name="right">
              <svg
                style="cursor: pointer"
                class="w-30px font-800"
                xmlns="http://www.w3.org/2000/svg"
                xmlns:xlink="http://www.w3.org/1999/xlink"
                viewBox="0 0 32 32"
                @click="cancel"
              >
                <path
                  d="M24 9.4L22.6 8L16 14.6L9.4 8L8 9.4l6.6 6.6L8 22.6L9.4 24l6.6-6.6l6.6 6.6l1.4-1.4l-6.6-6.6L24 9.4z"
                  fill="currentColor"
                ></path>
              </svg>
            </slot>
          </template>
          <div class="content-style" @keypress="catchWay">
            <slot name="content"></slot>
          </div>
          <template #footer>
            <div class="footer-style">
              <n-button v-if="showCancel" class="w-120px" @click="cancel">取消</n-button>
              <n-button class="w-120px" type="primary" :disabled="props.subDisable" @click="handleSubmit">
                确认
              </n-button>
            </div>
          </template>
        </n-card>
      </div>
    </template>
  </n-modal>
</template>

<style lang="scss" scoped>
.footer-style {
  width: 100%;
  display: flex;
  justify-content: space-around;
}
:deep(.n-card-header) {
  border-bottom: 1px solid #e8e8e8;
  padding: 10px 20px;
}
.content-style {
  width: 100%;
  padding: 20px;
  padding-bottom: 0px;
  height: 100%;
  display: flex;
  justify-content: center;
}
</style>
