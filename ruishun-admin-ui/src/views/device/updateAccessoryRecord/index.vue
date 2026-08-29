<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="更换时间">
          <n-date-picker v-model:value="searchForm.replaceTime" type="date"></n-date-picker>
        </n-form-item>
        <n-form-item label="配件编号">
          <n-input v-model:value="searchForm.attachmentCode"></n-input>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="数据列表">
      <n-data-table :data="data" :columns="columns" :loading="loading"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { NDataTable, NForm, NFormItem } from 'naive-ui';
import dayjs from 'dayjs';
import { useLoading } from '@/hooks';
import { updateAccessoryRecord } from '@/service/api/device/updateAccessoryRecord';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
const { loading, startLoading, endLoading } = useLoading();
const searchForm = ref({
  attachmentCode: null,
  replaceTime: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function search() {
  searchForm.value.pageNum = 1;
  init();
}
function reset() {
  searchForm.value = {
    attachmentCode: null,
    replaceTime: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  attachmentName: string;
  attachmentCode: string;
  equipmentCode: string;
  distributionPath: string;
  usedTime: string;
  suppier: string;
  replaceTime: number | string;
  status: string;
  maxServiceTime: string | number;
};
const data = ref<DataType[]>([]);
const columns: Ref<DataTableColumns<DataType>> = ref([
  // {
  //   type: 'selection'
  // },
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1,
    width: 50
  },
  {
    title: '配件名称',
    align: 'center',
    key: 'attachmentName',
    width: 100
  },
  {
    title: '配件状态',
    key: 'status',
    render: row => {
      return <n-tag type={row.status !== '预警' ? 'success' : 'error'}>{row.status}</n-tag>;
    },
    width: 100
  },
  {
    title: '已使用时间',
    key: 'usedTime',
    render: row => {
      return <div>{`${row.usedTime ? row.usedTime : '0'}天`}</div>;
    },
    width: 100
  },
  {
    title: '已生产重量',
    key: 'producedWeight',
    width: 100
  },
  {
    title: '更换时间',
    key: 'replaceTime',
    render: row => {
      return row.replaceTime ? dayjs(row.replaceTime).format('YYYY-MM-DD hh:mm:ss') : '';
    },
    width: 100,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '配件编号',
    align: 'center',
    key: 'attachmentCode',
    width: 100
  },
  {
    title: '适配设备',
    align: 'center',
    key: 'equipmentCode',
    width: 100
  },
  {
    title: '配件分类路径',
    align: 'center',
    key: 'distributionPath',
    width: 100
  },
  // {
  //   title: '使用寿命',
  //   align: 'center',
  //   key: 'usedTime',
  //   width: 100
  // },
  {
    title: '最大生产重量',
    align: 'center',
    key: 'maxServiceWeight',
    width: 100
  },
  {
    title: '供应商',
    align: 'center',
    key: 'suppier',
    width: 100
  },
  {
    title: '最大使用时间',
    key: 'maxServiceTime',
    render: row => {
      return <div>{`${row.maxServiceTime ? row.maxServiceTime : '0'}天`}</div>;
    },
    width: 100
  }
]);

function init() {
  // console.log('init');
  startLoading();
  updateAccessoryRecord({
    ...searchForm.value,
    replaceTime: dayjs(searchForm.value.replaceTime).format('YYYY-MM-DD')
  }).then(res => {
    endLoading();
    if (res.code === 200) {
      searchForm.value.total = res.total;
      data.value = res.rows;
    }
    endLoading();
    if (res.code === 200) {
      searchForm.value.total = res.total;
      data.value = res.rows;
    }
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped lang="scss"></style>
