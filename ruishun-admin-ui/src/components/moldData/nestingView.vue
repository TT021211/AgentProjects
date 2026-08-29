<template>
  <div>
    <my-card title="套模列表">
      <n-data-table
        v-model:checked-row-keys="checkedRowKeys"
        :loading="loading"
        :columns="columns"
        :data="data"
        :row-key="rowKey"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import { ref, watch, h } from 'vue';
import type { Ref } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { NInputNumber } from 'naive-ui';
import { useLoading } from '~/src/hooks';
import { getMoldNestingView } from '~/src/service/api/moid/drawing/index';
defineOptions({ name: 'NestingView' });

const props = withDefaults(
  defineProps<{
    nestingMoldId: number | null;
  }>(),
  {
    nestingMoldId: null
  }
);

const searchForm = ref<{
  pageNum: number;
  pageSize: number;
  total: number;
  nestingMoldId: number | null;
}>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  nestingMoldId: null
});

const { loading, startLoading, endLoading } = useLoading();
const checkedRowKeys = ref<Array<string | number>>([]);
const rowKey = (row: any) => row.moldId;
const isAddColumns = ref<boolean>(false);
// const filterStatus = ref<>
const isAddTolerance = ref<boolean>(false);

const data = ref<drawing.viewData[]>([]);
const columns: Ref<DataTableColumns<drawing.viewData>> = ref([
  {
    type: 'selection'
  },
  {
    title: '套模编号',
    key: 'moldName',
    align: 'center',
    width: 100
  },
  {
    title: '模具编号',
    key: 'moldCode',
    align: 'center',
    width: 100
  },
  {
    title: '模具孔径',
    key: 'moldAperture',
    align: 'center',
    width: 100
  },
  {
    title: '公差',
    key: 'engineeringTolerance',
    align: 'center',
    width: 100
  },
  {
    title: '材质',
    key: 'moldMaterial',
    align: 'center',
    width: 100
  },
  {
    title: '型号',
    key: 'moldSpec',
    align: 'center',
    width: 100
  }
]);

const getDataIndex = key => {
  return data.value.findIndex(item => item.moldId === key);
};

function changeColumns() {
  if (isAddColumns.value) return;
  columns.value.splice(4, 0, {
    title: '改制后孔径',
    key: '',
    align: 'center',
    width: 140,
    render(row) {
      const index = getDataIndex(row.moldId);
      return h(NInputNumber, {
        value: row.nowAperture,
        onUpdateValue(v) {
          if (v !== null) {
            data.value[index].nowAperture = v;
          }
        }
      });
    }
  });
  isAddColumns.value = true;
}

function changeColumnsTolerance() {
  if (isAddTolerance.value) return;
  columns.value.splice(6, 0, {
    title: '改制后公差',
    key: '',
    align: 'center',
    width: 140,
    render(row) {
      const index = getDataIndex(row.moldId);
      return h(NInputNumber, {
        value: row.updateTolerance,
        onUpdateValue(v) {
          if (v !== null) {
            data.value[index].updateTolerance = v;
          }
        }
      });
    }
  });

  isAddTolerance.value = true;
}

function getData() {
  const list: any = [];
  checkedRowKeys.value.forEach(item => {
    const items = data.value.find(ele => ele.moldId === item);
    if (items) {
      list.push(items);
    }
  });
  return list;
}

defineExpose({
  checkedRowKeys,
  changeColumns,
  getData,
  changeColumnsTolerance
});

defineEmits(['cancel', 'submit', 'update:show']);

function init() {
  searchForm.value.nestingMoldId = props.nestingMoldId;
  startLoading();
  getMoldNestingView(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}

watch(
  () => props.nestingMoldId,
  () => {
    init();
  }
);
</script>

<style scoped>
button {
  padding: 0 20px;
}

.btn-box {
  display: flex;
  flex-direction: row-reverse;
  margin-top: 20px;
}
</style>
