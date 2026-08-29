<template>
  <n-popover placement="bottom" trigger="click">
    <template #trigger>
      <n-button :size="size" type="primary">
        <icon-ant-design-setting-outlined class="mr-4px text-16px" />
        表格列设置
      </n-button>
    </template>
    <div class="w-230px">
      <vue-draggable v-model="list" item-key="key">
        <template #item="{ element }">
          <div v-if="element.key" class="flex-y-center h-36px px-12px hover:bg-primary_active">
            <icon-mdi-drag class="mr-8px text-20px cursor-move" />
            <n-checkbox v-model:checked="element.checked">
              {{ element.title }}
            </n-checkbox>
          </div>
        </template>
      </vue-draggable>
    </div>
  </n-popover>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import type { DataTableColumn } from 'naive-ui';
import VueDraggable from 'vuedraggable';
import type { Size } from 'naive-ui/es/select/src/interface';
type Column = DataTableColumn;

interface Props {
  columns: DataTableColumn[];
  size: Size;
}

// 设置默认值
const props = withDefaults(defineProps<Props>(), {
  size: 'small'
});

interface Emits {
  (e: 'update:columns', columns: Column[]): void;
}

const emit = defineEmits<Emits>();

type List = Column & { checked?: boolean };

const list = ref(initList());

function initList(): List[] {
  return props.columns.map(item => ({ ...item, checked: true }));
}

watch(
  list,
  newValue => {
    const newColumns = newValue.filter(item => item.checked);

    const columns: Column[] = newColumns.map(item => {
      const column = { ...item };
      delete column.checked;
      return column;
    }) as Column[];

    emit('update:columns', columns);
  },
  { deep: true }
);
</script>

<style scoped></style>
