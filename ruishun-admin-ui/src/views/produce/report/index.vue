<template>
  <div>
    <my-card :title="title">
      <template #right>
        <div style="display: flex; align-items: center">
          <CxColumns v-model:columns="columns" size="small" />
          <n-button class="ml-5px" type="primary" size="small" attr-type="button" style="width: 80px" @click="back">
            返回
          </n-button>
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type { Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import type { DataTableColumns } from 'naive-ui';
import { useLoading } from '~/src/hooks';
import { getOutputLog } from '~/src/service/api/produre/workRecords/index';
const { loading, startLoading, endLoading } = useLoading();

const searchForm = ref<{
  taskId: number;
  taskType: number;
  pageNum: number;
  pageSize: number;
  total: number;
}>({
  taskId: 0,
  taskType: 0,
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const route = useRoute();

const router = useRouter();

const title = ref<string>('报工记录');

const data = ref<workRecords.TabelList[]>([]);

const columns: Ref<DataTableColumns<workRecords.TabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '工单类型',
    key: 'taskType',
    width: 100,
    render: row => {
      switch (row.taskType) {
        case '0':
          return '拉丝';
        case '1':
          return '退火';
        case '2':
          return '镀锡';
        case '3':
          return '并丝';
        case '4':
          return '绞线';
        default:
          return '';
      }
    }
  },
  {
    title: '机台',
    key: 'workstationCode',
    width: 100
  },
  {
    title: '轴位置数',
    key: 'alexPositionNumber',
    width: 100
  },
  {
    title: '批次',
    key: 'alexLot',
    width: 100
  },
  {
    title: '订单类型',
    key: 'orderType',
    width: 100,
    render: row => {
      return row.orderType === '0' ? '客户订单' : '备库订单';
    }
  },
  {
    title: '订单编号',
    key: 'orderSn',
    width: 100
  },
  {
    title: '客户',
    key: 'clientName',
    width: 100
  },
  {
    title: '生产工单号',
    key: 'taskSn',
    width: 100
  },
  {
    title: '二维码编号',
    key: 'outputSn',
    width: 100
  },
  {
    title: '产品型号',
    key: 'productModel',
    width: 100
  },
  {
    title: '产品规格',
    key: 'productSpecification',
    width: 100
  },
  {
    title: '报工重量',
    key: 'outputWeight',
    width: 100
  },
  {
    title: '单位',
    key: 'measureName',
    width: 100
  },
  {
    title: '报工时间',
    key: 'createTime',
    width: 100
  },
  {
    title: '报工人',
    key: 'createBy',
    width: 100
  },
  {
    title: '报工工号',
    key: 'jobNumber',
    width: 100
  }
]);

function init() {
  startLoading();
  getOutputLog({ ...searchForm.value }).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
    endLoading();
  });
}

function back() {
  router.go(-1);
}

onMounted(() => {
  title.value = `${route.query.title}-${title.value}`;
  searchForm.value.taskId = Number(route.query.id);
  searchForm.value.taskType = Number(route.query.taskType);
  init();
});
</script>

<style scoped></style>
