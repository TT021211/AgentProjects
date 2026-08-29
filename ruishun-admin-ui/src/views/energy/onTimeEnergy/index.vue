<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="即时能耗列表">
      <n-data-table :loading="loading"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import { ref, onMounted } from 'vue';
import { useSearchBtn } from '@/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';

const { loading, startLoading, endLoading } = useLoading();
const searchForm = ref({
  pageSize: 10,
  pageNum: 1,
  total: 0
});
function search() {
  searchForm.value.pageNum = 1;
}
function reset() {
  searchForm.value = {
    pageSize: 10,
    pageNum: 1,
    total: 0
  };
}
function init() {
  startLoading();
  endLoading();
}
onMounted(() => {
  init();
});
</script>

<style scoped></style>
