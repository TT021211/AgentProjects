<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form label-placement="top" class="flex gap-4">
        <n-form-item label="搬运规格">
          <n-input v-model:value="searchForm.specification" type="text" placeholder="请输入搬运规格" />
        </n-form-item>
        <n-form-item label="完工机台">
          <n-input v-model:value="searchForm.equipmentCode" type="text" placeholder="请输入完工机台" />
        </n-form-item>
        <n-form-item label="搬运机台">
          <n-input v-model:value="searchForm.enamellingEquipmentCode" type="text" placeholder="请输入搬运机台" />
        </n-form-item>
        <n-form-item label="搬运工号">
          <n-input v-model:value="searchForm.jobNumber" type="text" placeholder="请输入搬运工号" />
        </n-form-item>
        <n-form-item label="搬运半成品二维码">
          <n-input v-model:value="searchForm.itemIdentifier" type="text" placeholder="请输入搬运半成品二维码" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>

    <my-card title="搬运记录">
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

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type { Ref } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import { getMesCarryLog } from '~/src/service/api/produre/carryRecords/index';
// import { formatDate } from '~/src/utils/form/rule';
const { loading, startLoading, endLoading } = useLoading();

const searchForm = ref<carryRecords.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  specification: '',
  equipmentCode: '',
  jobNumber: '',
  enamellingEquipmentCode: '',
  itemIdentifier: ''
});
// const range = ref<[number, number] | null>(null);
const data = ref<carryRecords.TabelList[]>([]);

const columns: Ref<DataTableColumns<carryRecords.TabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '搬运规格',
    key: 'specification',
    align: 'center',
    width: 100
  },
  {
    title: '完工机台',
    key: 'equipmentCode',
    align: 'center',
    width: 120
  },
  {
    title: '搬运机台',
    key: 'enamellingEquipmentCode',
    align: 'center',
    width: 120
  },
  {
    title: '搬运半成品二维码',
    key: 'itemIdentifier',
    align: 'center',
    width: 200
  },
  {
    title: '搬运人工号',
    key: 'jobNumber',
    align: 'center',
    width: 100
  },
  {
    title: '搬运时间',
    key: 'createTime',
    align: 'center',
    width: 180
  }
]);

function search() {
  // if (range.value !== null) {
  //   searchForm.value['params[beginTime]'] = formatDate(new Date(range.value[0]), 'yyyy-MM-dd');
  //   searchForm.value['params[endTime]'] = formatDate(new Date(range.value[1]), 'yyyy-MM-dd');
  // } else {
  //   searchForm.value['params[beginTime]'] = null;
  //   searchForm.value['params[endTime]'] = null;
  // }
  init();
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    specification: '',
    equipmentCode: '',
    jobNumber: '',
    enamellingEquipmentCode: '',
    itemIdentifier: ''
  };
  // range.value = null;
  search();
}
async function init() {
  startLoading();
  getMesCarryLog({ ...searchForm.value }).then(res => {
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

<style scoped></style>
