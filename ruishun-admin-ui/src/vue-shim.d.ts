import type { useMessage } from 'naive-ui';

declare module '@vue/runtime-core' {
  interface ComponentCustomProperties {
    $msg: ReturnType<typeof useMessage>; // 声明全局属性的类型
  }
}
