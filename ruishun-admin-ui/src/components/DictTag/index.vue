<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(item.dictValue)">
        <span
          v-if="!item.listClass || item.listClass == 'default' || item.listClass == ''"
          :key="item.dictValue"
          :index="index"
          :class="item.listClass"
        >
          {{ item.dictLabel }}
        </span>

        <n-tag
          v-else
          :key="item.dictValue + ''"
          :index="index"
          :type="item.listClass === 'primary' ? '' : item.listClass"
          :class="item.listClass"
        >
          {{ item.dictLabel }}
        </n-tag>
      </template>
    </template>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
defineOptions({
  name: 'DictTag'
});
const props = defineProps<{
  options: any[];
  value: any;
}>();

const values = computed(() => {
  if (props.value !== null && typeof props.value !== 'undefined') {
    return Array.isArray(props.value) ? props.value : [String(props.value)];
  }
  return [];
});
</script>

<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>
