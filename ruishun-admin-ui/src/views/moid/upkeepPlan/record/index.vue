<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="套模编号">
          <n-input v-model:value="searchForm.nestingName" type="text" placeholder="请输入套模编号" />
        </n-form-item>
        <n-form-item label="计划名称">
          <n-input v-model:value="searchForm.maintainPlanName" type="text" placeholder="请输入计划名称" />
        </n-form-item>
        <n-form-item label="保养类型">
          <n-input v-model:value="searchForm.maintainPlanType" type="text" placeholder="请输入保养类型" />
        </n-form-item>
        <n-form-item label="保养时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="">
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

<script setup lang="tsx">
defineOptions({
  name: 'UpkeepPlanRecord'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { getMoldMaintainRecord } from '~/src/service/api/moid/upkeepPlan/index';
import { formatDate } from '~/src/utils/form/rule';
const searchForm = ref<upkeepPlan.recodeSearchForm>({
  nestingName: '',
  maintainPlanName: '',
  maintainPlanType: '',
  startDate: null,
  endDate: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const { loading, startLoading, endLoading } = useLoading();
const range = ref<[number, number] | null>(null);
const data = ref<upkeepPlan.recordTabelList[]>([
  {
    nestingName: 'LS005',
    nestingScope: '5#拉丝机',
    nestingArea: '二楼车间',
    maintainPlanName: '拉丝机保养计划',
    maintainPlanType: '拉丝每月保养',
    createBy: '*****',
    createTime: '2024-1-1',
    maintainItems: [
      {
        maintainItemName: '拉丝机'
      }
    ],
    clientName: '*****'
  },
  {
    nestingName: 'DX005',
    nestingScope: '5#镀锡机',
    nestingArea: '一楼车间',
    maintainPlanName: '镀锡机保养计划',
    maintainPlanType: '镀锡每月保养',
    createBy: '*****',
    createTime: '2024-1-1',
    maintainItems: [
      {
        maintainItemName: '镀锡机'
      }
    ],
    clientName: '*****'
  },
  {
    nestingName: 'THDX005',
    nestingScope: '5#退火镀锡机',
    nestingArea: '三楼车间',
    maintainPlanName: '退火镀锡机保养计划',
    maintainPlanType: '退火镀锡每月保养',
    createBy: '*****',
    createTime: '2024-1-1',
    maintainItems: [
      {
        maintainItemName: '镀锡机'
      }
    ],
    clientName: '*****'
  }
]);

const columns: Ref<DataTableColumns<upkeepPlan.recordTabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '套模编号',
    key: 'nestingName',
    align: 'center',
    width: 100
  },
  {
    title: '套模范围',
    key: 'nestingScope',
    align: 'center',
    width: 100
  },
  {
    title: '存放位置',
    key: 'nestingArea',
    align: 'center',
    width: 100
  },
  {
    title: '模具供应商',
    key: 'clientName',
    align: 'center',
    width: 100
  },
  {
    title: '计划名称',
    key: 'maintainPlanName',
    align: 'center',
    width: 100
  },
  {
    title: '保养类型',
    key: 'maintainPlanType',
    align: 'center',
    width: 100
  },
  {
    title: '保养人',
    key: 'createBy',
    align: 'center',
    width: 100
  },
  {
    title: '保养项',
    key: 'maintenanceItem',
    align: 'center',
    width: 100,
    render: row => {
      let maintenanceItem = '';
      if (row.maintainItems) {
        row.maintainItems.forEach(item => {
          maintenanceItem += `${item.maintainItemName},`;
        });
        maintenanceItem = maintenanceItem.slice(0, maintenanceItem.length - 1);
      }
      return maintenanceItem;
    }
  },
  {
    title: '保养时间',
    key: 'createTime',
    align: 'center',
    width: 100
  }
]);

function search() {
  if (range.value !== null) {
    searchForm.value.startDate = formatDate(new Date(range.value[0]), 'yyyy-MM-dd hh:mm:ss');
    searchForm.value.endDate = formatDate(new Date(range.value[1]), 'yyyy-MM-dd hh:mm:ss');
  } else {
    searchForm.value.startDate = null;
    searchForm.value.endDate = null;
  }
  init();
}
function reset() {
  searchForm.value = {
    nestingName: '',
    maintainPlanName: '',
    maintainPlanType: '',
    startDate: null,
    endDate: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function init() {
  startLoading();
  getMoldMaintainRecord(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      res.rows.forEach(item => {
        if (item.createTime) {
          item.createTime = formatDate(new Date(item.createTime), 'yyyy-MM-dd hh:mm:ss');
        }
      });
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped></style>
