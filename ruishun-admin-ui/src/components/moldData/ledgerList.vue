<template>
  <div>
    <my-card title="台账列表">
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
import { NInputNumber } from 'naive-ui';
import type { DataTableColumns } from 'naive-ui';
import { useLoading } from '~/src/hooks';
import { getQueryByClassificationId } from '~/src/service/api/moid/outWarehouse/index';
import { getMoidStatus } from '~/src/utils/common/moidFunc';
const props = withDefaults(
  defineProps<{
    classificationId: number;
  }>(),
  {
    classificationId: -1
  }
);

const searchForm = ref<{
  pageNum: number;
  pageSize: number;
  total: number;
  classificationId: number | null;
}>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  classificationId: null
});
const isAddColumns = ref<boolean>(false);
const isAddTolerance = ref<boolean>(false);

const { loading, startLoading, endLoading } = useLoading();
const checkedRowKeys = ref<Array<string | number>>([]);
const rowKey = (row: any) => row.moldId;

const data = ref<basicsMessage.TableList[]>([]);

const columns: Ref<DataTableColumns<basicsMessage.TableList>> = ref([
  {
    type: 'selection'
  },
  {
    title: '模具编号',
    key: 'moldCode',
    align: 'center',
    width: 100
  },
  {
    title: '模具类别',
    key: 'moldType',
    align: 'center',
    width: 100
  },
  {
    title: '型号',
    key: 'moldSpec',
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
    title: '存放位置',
    key: 'moldArea',
    align: 'center',
    width: 100
  },
  {
    title: '套模名称',
    key: 'moldName',
    align: 'center',
    width: 100
  },
  {
    title: '状态',
    key: 'status',
    align: 'center',
    width: 100,
    render: row => {
      const obj = getMoidStatus(row.status);
      return <n-tag type={obj.statusType}>{obj.text}</n-tag>;
    }
  }
]);

const getDataIndex = key => {
  return data.value.findIndex(item => item.moldId === key);
};

function changeColumns() {
  if (isAddColumns.value) return;
  columns.value.splice(5, 0, {
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
  columns.value.splice(7, 0, {
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
  if (checkedRowKeys.value.length > 0) {
    checkedRowKeys.value.forEach(item => {
      const items = data.value.find(ele => ele.moldId === item);
      if (items) {
        list.push(items);
      }
    });
  }
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
  searchForm.value.classificationId = props.classificationId;
  startLoading();
  getQueryByClassificationId(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}

watch(
  () => props.classificationId,
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
