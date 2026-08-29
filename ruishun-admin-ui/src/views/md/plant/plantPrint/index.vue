<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="数据列表">
      <n-data-table></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import { ref, onMounted } from 'vue';
import { useSearchBtn } from '~/src/hooks/common/useBtn';

const searchForm = ref({
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function search() {
  console.log('search');
}
function reset() {
  console.log('reset');
}
function init() {
  console.log('init');
}
onMounted(() => {
  init();
});
</script>

<style scoped lang="scss"></style>
