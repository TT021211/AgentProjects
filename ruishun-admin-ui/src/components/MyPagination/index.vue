<template>
  <div>
    <n-pagination
      :page="searchForm.pageNum"
      :item-count="searchForm.total"
      :page-size="searchForm.pageSize"
      :page-sizes="[...new Set([searchForm.pageSize, 10, 20, 50, 100, 200, 500, 1000])]"
      show-size-picker
      style="margin-top: 20px; float: right"
      @update:page="changePage"
      @update:page-size="changePageSize"
    >
      <template #prefix="{ itemCount }">共 {{ itemCount }} 条</template>
    </n-pagination>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'CXPagination'
});
const props = defineProps({
  searchForm: {
    type: Object,
    default: () => {
      return {
        pageNum: 1,
        pageSize: 10,
        total: 0
      };
    }
  }
});
const emit = defineEmits(['update:searchForm', 'init']);
const changePage = (page: number) => {
  emit('update:searchForm', {
    ...props.searchForm,
    pageNum: page
  });
  emit('init');
};
const changePageSize = (pageSize: number) => {
  emit('update:searchForm', {
    ...props.searchForm,
    pageSize
  });
  emit('init');
};
</script>

<style scoped></style>
