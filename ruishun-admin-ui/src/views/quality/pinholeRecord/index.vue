<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="订单类型">
          <n-select placeholder="请选择订单类型" class="w-160px" :options="orderOptions"></n-select>
        </n-form-item>
        <n-form-item label="订单编号">
          <n-select placeholder="请输入订单编号" class="w-160px"></n-select>
        </n-form-item>
        <n-form-item label="时间">
          <n-date-picker type="daterange" placeholder="请选择日期" class="w-320px"></n-date-picker>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="">
      <template #right>
        <div>
          <component :is="useAddBtn()"></component>
        </div>
      </template>
      <n-data-table></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useSearchBtn, useAddBtn } from '~/src/hooks/common/useBtn';

const orderOptions = [
  {
    value: undefined,
    label: '所有'
  },
  {
    value: 1,
    label: '备库订单'
  },
  {
    value: 2,
    label: '客户订单'
  }
];
const searchForm = ref({
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function search() {
  searchForm.value.pageNum = 1;
  init();
  console.log('search');
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
  console.log('reset');
}
function init() {
  console.log('init');
}
onMounted(() => {
  console.log('mounted');
  init();
});
</script>

<style scoped></style>
