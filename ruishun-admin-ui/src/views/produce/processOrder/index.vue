<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form class="flex gap-5 flex-wrap" label-placement="top">
        <n-form-item label="工序">
          <!-- <n-radio-group>
            <n-radio-button
              v-for="item in processOptions"
              :key="item.label"
              :value="item.value"
              :label="item.label"
            ></n-radio-button>
          </n-radio-group> -->
          <n-select placeholder="请输入工序" class="w-160px" :options="processOptions"></n-select>
        </n-form-item>
        <n-form-item label="生产工单">
          <n-select placeholder="请输入生产工单" filterable class="w-160px"></n-select>
        </n-form-item>
        <n-form-item label="机台">
          <n-select placeholder="请输入机台" filterable class="w-160px"></n-select>
        </n-form-item>
        <n-form-item label="销售订单">
          <n-select placeholder="请输入销售订单" filterable class="w-160px"></n-select>
        </n-form-item>
        <n-form-item label="工单状态">
          <n-select placeholder="请选择工单状态" :options="statusOptions" class="w-160px"></n-select>
        </n-form-item>
        <n-form-item label="时间">
          <n-date-picker type="daterange" placeholder="请选择日期" class="w-280px"></n-date-picker>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="工序工单">
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

// 工单状态选项框
const statusOptions = [
  {
    value: 1,
    label: '生产中'
  },
  {
    value: 2,
    label: '排产中'
  },
  {
    value: 3,
    label: '已完成'
  }
];
// 工序选项框
const processOptions = [
  {
    value: undefined,
    label: '所有'
  },
  {
    value: 1,
    label: '挤压'
  },
  {
    value: 2,
    label: '拉拔'
  },
  {
    value: 3,
    label: '精加工'
  }
];
const searchForm = ref({
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function search() {
  init();
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function init() {
  // eslint-disable-next-line no-console
  console.log('init');
}
onMounted(() => {
  init();
});
</script>

<style lang="scss" scoped>
:deep(.n-form-item-feedback-wrapper) {
  min-height: 0px;
}
</style>
