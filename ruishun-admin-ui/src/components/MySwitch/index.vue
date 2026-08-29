<template>
  <div>
    <n-switch
      v-model:value="value"
      :unchecked-value="unchecked"
      :checked-value="checked"
      @update:value="onChange"
    ></n-switch>
  </div>
</template>

<script setup lang="ts">
defineOptions({
  name: 'MySwitch'
});
import type { Ref } from 'vue';
import { useDialog } from 'naive-ui';
const dialog = useDialog();
const value: Ref<valueType | undefined> = defineModel('value');

type valueType = boolean | string | number;
const props = withDefaults(
  defineProps<{
    checked: valueType;
    unchecked: valueType;
  }>(),
  {
    checked: true,
    unchecked: false
  }
);
const emit = defineEmits(['checked', 'unchecked']);

function onChange(val: valueType) {
  if (val === props.checked) {
    dialog.warning({
      title: '提示',
      content: '确定启用吗？',
      style: {
        borderRadius: '10px'
      },
      positiveText: '确定',
      negativeText: '取消',
      maskClosable: false,
      onClose: () => {
        value.value = props.unchecked;
      },
      onPositiveClick: () => {
        emit('checked', val);
      },
      onNegativeClick: () => {
        value.value = props.unchecked;
      }
    });
  } else {
    dialog.warning({
      title: '提示',
      content: '确定禁用吗？',
      style: {
        borderRadius: '10px'
      },
      positiveText: '确定',
      negativeText: '取消',
      maskClosable: false,
      onClose: () => {
        value.value = props.checked;
      },
      onPositiveClick: () => {
        emit('unchecked', val);
      },
      onNegativeClick: () => {
        value.value = props.checked;
      }
    });
  }
}
</script>

<style lang="scss" scoped></style>
