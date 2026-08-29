<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="模具编号">
          <n-input v-model:value="searchForm.moldCode" type="text" placeholder="请输入模具编号" />
        </n-form-item>
        <n-form-item label="模具类别">
          <n-input v-model:value="searchForm.moldType" type="text" placeholder="请输入模具编号" />
        </n-form-item>
        <n-form-item label="日期">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="生命周期列表">
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
  name: 'LifeCycleReport'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { getMoldLifeCycleList } from '~/src/service/api/moid/lifeCycleReport/index';
import { formatDate } from '~/src/utils/form/rule';
import { getMoidStatus } from '~/src/utils/common/moidFunc';
const searchForm = ref<lifeCycleReport.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  moldCode: '',
  moldType: '',
  startDate: null,
  endDate: null
});

const { loading, startLoading, endLoading } = useLoading();
const range = ref<[number, number] | null>(null);
const data = ref<lifeCycleReport.TableList[]>([
  {
    id: 1,
    nestingId: 1,
    moldId: 1,
    status: '首次出库',
    moldLedger: '',
    createTime: '2023-03-12',
    moldCode: '20230312',
    moldType: '拉丝模',
    moldSpec: '出线模',
    moldArea: '一楼仓库',
    moldName: 'L3',
    moldMaterial: '高金',
    moldAperture: ''
  },
  {
    id: 1,
    nestingId: 1,
    moldId: 1,
    status: '首次入库',
    moldLedger: '',
    createTime: '2023-03-12',
    moldCode: '20230312',
    moldType: '镀锡模',
    moldSpec: '出线模',
    moldArea: '一楼仓库',
    moldName: 'D3',
    moldMaterial: '高金',
    moldAperture: ''
  },
  {
    id: 1,
    nestingId: 1,
    moldId: 1,
    status: '出库',
    moldLedger: '',
    createTime: '2023-03-12',
    moldCode: '20230312',
    moldType: '拉丝模',
    moldSpec: '出线模',
    moldArea: '一楼仓库',
    moldName: 'L3',
    moldMaterial: '聚金',
    moldAperture: ''
  }
]);

const columns: Ref<DataTableColumns<lifeCycleReport.TableList>> = ref([
  {
    title: '套模名称',
    key: 'moldName',
    align: 'center',
    width: 100
  },
  {
    title: '模具类别',
    key: 'moldType',
    align: 'center',
    width: 100
  },
  {
    title: '模具编码',
    key: 'moldCode',
    align: 'center',
    width: 100
  },
  {
    title: '型号',
    key: 'moldSpec',
    align: 'center',
    width: 100
  },
  {
    title: '材质',
    key: 'moldMaterial',
    align: 'center',
    width: 100
  },
  {
    title: '存放位置',
    key: 'moldArea',
    align: 'center',
    width: 100
  },
  {
    title: '状态',
    key: 'status',
    align: 'center',
    width: 100,
    render: row => {
      const obj = getMoidStatus(row.status);
      return <n-tag type={obj.statusType}>{row.status}</n-tag>;
    }
  },
  {
    title: '日期',
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
    pageNum: 1,
    pageSize: 10,
    total: 0,
    moldCode: '',
    moldType: '',
    startDate: null,
    endDate: null
  };
  init();
}

function init() {
  startLoading();
  getMoldLifeCycleList(searchForm.value).then(res => {
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

<style scoped>
:deep(.n-space) {
  justify-content: center !important;
}

:deep(.n-data-table .n-data-table-th .n-data-table-th__title-wrapper) {
  text-align: center;
}
</style>
