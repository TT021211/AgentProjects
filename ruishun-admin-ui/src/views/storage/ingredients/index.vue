<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="出入库类型">
          <n-select
            v-model:value="searchForm.status"
            :options="options"
            class="w-180px"
            placeholder="请选择出入库类型"
          ></n-select>
        </n-form-item>
        <n-form-item label="辅料名称">
          <n-input v-model:value="searchForm.accessoryName" placeholder="请输入辅料名称"></n-input>
        </n-form-item>
        <n-form-item>
          <component
            :is="
              useSearchBtn(
                () => {
                  search();
                },
                () => {
                  reset();
                }
              )
            "
          />
        </n-form-item>
      </n-form>
    </my-card>
    <my-card :title="title">
      <template #right>
        <div>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :columns="columns"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
        :data="data"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { dataTableConfig } from '@/config/dataTableConfig';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import { getWmsAccessoryLogList } from '~/src/service/api/wms/ingredients';
const { loading, startLoading, endLoading } = useLoading();

const title = ref<string>('辅料出入库');
const searchForm = ref<ingredients.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  status: null,
  accessoryName: ''
});
const options = [
  {
    label: `入库`,
    value: '0'
  },
  {
    label: `出库`,
    value: '1'
  }
];
const data = ref<ingredients.tabelList[]>([]);

const columns: Ref<DataTableColumns<ingredients.tabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '名称',
    align: 'center',
    width: 100,
    key: 'accessoryName'
  },
  {
    title: '规格',
    align: 'center',
    width: 100,
    key: 'specification'
  },
  {
    title: '数量/重量',
    align: 'center',
    width: 100,
    key: 'number'
  },
  {
    title: '单位',
    align: 'center',
    width: 100,
    key: 'numberMeasureName'
  },
  {
    title: '状态',
    align: 'center',
    width: 100,
    key: 'status',
    render: (row: any) => (
      <n-tag type={row.status === '0' ? 'success' : 'error'}>{row.status === '0' ? '入库' : '出库'}</n-tag>
    )
  },
  {
    title: '出入库时间',
    align: 'center',
    key: 'createTime',
    width: 180
  }
]);

function search() {
  searchForm.value.pageNum = 1;
  init();
}

function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    status: null,
    accessoryName: '',
    total: 0
  };
  init();
}

function init() {
  startLoading();
  getWmsAccessoryLogList({ ...searchForm.value }).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
    endLoading();
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped>
.active {
  background-color: lightgreen !important;
  color: #fff !important;
}
</style>
