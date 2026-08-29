<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline label-placement="top" :model="searchForm">
        <n-form-item label="供应商名称" path="clientName">
          <n-input v-model:value="searchForm.supplierName" placeholder="请输入供应商名称"></n-input>
        </n-form-item>
        <n-form-item label="物资名称" path="creditCode">
          <n-input v-model:value="searchForm.materialsName" placeholder="请输入物资名称"></n-input>
        </n-form-item>
        <n-form-item label="物资型号" path="creditCode">
          <n-input v-model:value="searchForm.materialsCode" placeholder="请输入物资型号"></n-input>
        </n-form-item>
        <n-form-item>
          <n-button type="primary" class="mr-10px" @click="handleSearch">
            <icon-ic-round-search class="mr-4px text-20px" />
            搜索
          </n-button>
          <n-button @click="handleReset">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="报表分析">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
        </div>
      </template>
      <n-data-table
        :max-height="400"
        :scroll-x="1600"
        :loading="loading"
        :data="data"
        :columns="columns"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useLoading } from '~/src/hooks';
import { getProcureReportList } from '~/src/service/api/procure/report';

const { loading, startLoading, endLoading } = useLoading();
const searchForm = ref<Procure.searchForm>({
  supplierName: '',
  materialsName: '',
  materialsCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const data = ref<Procure.report.columns[]>([]);
const columns: Ref<DataTableColumns<Procure.report.columns>> = ref([
  {
    title: '序号',
    key: 'index',
    titleAlign: 'center',
    align: 'center',
    render(_row, index) {
      return index + 1;
    },
    width: 60
  },
  {
    title: '采购日期',
    key: 'saleOrderDate',
    width: 120,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '供应商名称',
    align: 'center',
    key: 'clientName',
    width: 120
  },
  {
    title: '物资名称',
    align: 'center',
    key: 'productName',
    width: 80
  },
  {
    title: '物资型号',
    align: 'center',
    key: 'productModel',
    width: 120,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '单位',
    align: 'center',
    width: 80,
    key: 'unit'
  },
  {
    title: '合同数量',
    align: 'center',
    width: 80,
    key: 'saleContractNumber'
  },
  {
    title: '单价',
    align: 'center',
    width: 80,
    key: 'price'
  },
  {
    title: '到货时间',
    align: 'center',
    width: 120,
    key: 'arrivalTime'
  },
  {
    title: '实收数量',
    align: 'center',
    width: 80,
    key: 'actualCount'
  },
  {
    title: '总价',
    align: 'center',
    width: 100,
    key: 'totalPrice'
  },
  {
    title: '发票',
    align: 'center',
    width: 120,
    key: 'invoice'
  },
  {
    title: '付款时间',
    align: 'center',
    width: 150,
    key: 'paymentTime'
  }
]);
function handleSearch() {
  init();
}
function handleReset() {
  searchForm.value = {
    supplierName: '',
    materialsName: '',
    materialsCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function init() {
  startLoading();
  getProcureReportList(searchForm.value).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
      endLoading();
    }
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped lang="scss"></style>
