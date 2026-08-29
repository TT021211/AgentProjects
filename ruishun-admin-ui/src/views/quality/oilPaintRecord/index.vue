<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="型号">
          <n-input v-model:value="searchForm.paintSpec" type="text" placeholder="请输入型号" />
        </n-form-item>
        <n-form-item label="采购单号">
          <n-input v-model:value="searchForm.purchaseOrder" type="text" placeholder="请输入采购单号" />
        </n-form-item>
        <n-form-item label="检测结果">
          <n-input v-model:value="searchForm.detectionResult" type="text" placeholder="请输入检测结果" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="油漆检列表">
      <template #right>
        <div>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { geQcPaintRecordList } from '~/src/service/api/quality/oilPaintRecord/index';
const searchForm = ref<oilPaintRecord.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  paintSpec: '',
  purchaseOrder: '',
  detectionResult: ''
});

const { loading, startLoading, endLoading } = useLoading();
const data = ref<oilPaintRecord.TableList[]>([]);

const columns: Ref<DataTableColumns<oilPaintRecord.TableList>> = ref([
  {
    title: '检测项名称',
    key: '',
    align: 'center',
    width: 100
  },
  {
    title: '检测分类',
    key: '',
    align: 'center',
    width: 100
  },
  {
    title: '型号',
    key: 'paintSpec',
    align: 'center',
    width: 100
  },
  {
    title: '采购订单',
    key: 'purchaseOrder',
    align: 'center',
    width: 100
  },
  {
    title: '供应商',
    key: 'supplier',
    align: 'center',
    width: 100
  },
  {
    title: '批号',
    key: 'batchNumber',
    align: 'center',
    width: 100
  },
  {
    title: '重量',
    key: 'weight',
    align: 'center',
    width: 100
  },
  {
    title: '单位',
    key: 'measure',
    align: 'center',
    width: 100
  },
  {
    title: '质检人',
    key: 'qualityInspector',
    align: 'center',
    width: 100
  },
  {
    title: '检测结果',
    key: 'detectionResult',
    align: 'center',
    width: 100
  },
  {
    title: '缺陷项',
    key: '',
    align: 'center',
    width: 100
  },
  {
    title: '检测数据',
    key: '',
    align: 'center',
    width: 100,
    render: () => {
      return (
        <n-space justify={'center'}>
          <n-button attr-type="button" size={'small'}>
            <icon-mdi-search />
            查看详情
          </n-button>
        </n-space>
      );
    }
  },
  {
    title: '操作',
    key: '',
    align: 'center',
    width: 100,
    render: () => {
      return (
        <n-space justify={'center'}>
          <n-button attr-type="button" size={'small'}>
            <icon-mdi-edit />
            填写
          </n-button>

          <n-button type="warning" size={'small'}>
            <icon-mdi-delete />
            删除
          </n-button>
        </n-space>
      );
    }
  }
]);

function search() {
  init();
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    paintSpec: '',
    purchaseOrder: '',
    detectionResult: ''
  };
  init();
}

function init() {
  startLoading();
  geQcPaintRecordList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped>
:deep(.n-space) {
  justify-content: center !important;
}

:deep(.n-data-table .n-data-table-th .n-data-table-th__title-wrapper) {
  text-align: center;
}
</style>
