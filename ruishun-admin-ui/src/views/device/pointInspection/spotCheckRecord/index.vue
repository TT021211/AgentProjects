<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form
        inline
        label-placement="left"
        label-align="center"
        label-width="auto"
        require-mark-placement="right-hanging"
      >
        <n-grid :cols="4" x-gap="15">
          <n-form-item-grid-item :span="1" label="项目类型">
            <n-input v-model:value="searchForm.itemTypeName" placeholder="请输入项目类型"></n-input>
          </n-form-item-grid-item>
          <n-form-item-grid-item :span="1" label="设备编码">
            <n-input v-model:value="searchForm.equipmentCode" placeholder="请输入设备编码"></n-input>
          </n-form-item-grid-item>
          <n-form-item-grid-item :span="1" label="点检人">
            <n-input v-model:value="searchForm.headName" placeholder="请输入负责人名称"></n-input>
          </n-form-item-grid-item>
          <n-form-item-grid-item :span="1" label="状态">
            <n-select
              v-model:value="searchForm.status"
              placeholder="请选择状态"
              style="width: 180px"
              :options="[
                { label: '已完成', value: 0 },
                { label: '未点检', value: 1 }
              ]"
            ></n-select>
          </n-form-item-grid-item>
          <n-form-item-grid-item :span="2" label="点检时间">
            <n-date-picker v-model:value="searchForm.logTime" :format="format" type="daterange" clearable />
            <component
              :is="
                useSearchBtn(
                  () => {
                    handleSearch();
                  },
                  () => {
                    handleReset();
                  }
                )
              "
              class="ml-8"
            ></component>
          </n-form-item-grid-item>
          <n-form-item-grid-item :span="1"></n-form-item-grid-item>
        </n-grid>
      </n-form>
    </my-card>
    <my-card title="点检记录">
      <template #right>
        <div>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { getEqListLogList } from '@/service/api/device/eqRecord';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();

type searchType = {
  planType: string;
  equipmentCode: string;
  headName: string;
  itemTypeName: string;
  status: string | null;
  pageNum: number;
  pageSize: number;
  total: number;
  logTime: any;
  params: {
    beginTime: string;
    endTime: string;
  };
};
const format = 'yyyy-MM-dd';
function formatTime(time: string) {
  // 将时间戳转换为年月日的形式'ri
  const date = new Date(time);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  return `${year}-${month}-${day}`;
}
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  planType: '0',
  itemTypeName: '',
  status: null,
  equipmentCode: '',
  headName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0,
  logTime: null,
  params: {
    beginTime: '',
    endTime: ''
  }
});
function handleSearch() {
  if (searchForm.value.logTime && searchForm.value.logTime.length > 0) {
    searchForm.value.params = {
      beginTime: formatTime(searchForm.value.logTime[0]),
      endTime: formatTime(searchForm.value.logTime[1])
    };
  }
  searchForm.value.pageNum = 1;
  searchForm.value.logTime = null;
  init();
}

function handleReset() {
  searchForm.value = {
    planType: '0',
    equipmentCode: '',
    itemTypeName: '',
    status: null,
    headName: '',
    pageNum: 1,
    pageSize: 10,
    total: 0,
    logTime: null,
    params: {
      beginTime: '',
      endTime: ''
    }
  };
  init();
}
type DataType = {
  id: number;
  equipmentId: string | null;
  equipmentCode: string;
  equipmentName: string;
  equipmentModel: string;
  planId: string | null;
  planCode: string;
  planType: string;
  planName: string;
  itemTypeId: string | null;
  itemTypeName: string;
  workgroupId: string | null;
  workgroupName: string;
  headId: string | null;
  headName: string;
  status: string;
};
const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    width: 80,
    key: 'index',
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '计划编码',
    align: 'center',
    width: 120,
    key: 'planCode'
  },
  {
    title: '计划名称',
    align: 'center',
    width: 120,
    key: 'planName'
  },
  {
    title: '设备编码',
    align: 'center',
    width: 120,
    key: 'equipmentCode'
  },
  {
    title: '设备名称',
    align: 'center',
    width: 120,
    key: 'equipmentName'
  },
  {
    title: '设备类型',
    align: 'center',
    width: 120,
    key: 'equipmentModel'
  },
  {
    title: '点检类型',
    align: 'center',
    width: 120,
    key: 'itemTypeName'
  },
  {
    title: '点检组别',
    align: 'center',
    width: 120,
    key: 'workgroupName'
  },
  {
    title: '负责人',
    align: 'center',
    width: 120,
    key: 'headName'
  },
  {
    title: '点检时间',
    align: 'center',
    width: 120,
    key: 'createTime'
  },
  {
    title: '记录生成时间',
    align: 'center',
    width: 120,
    key: 'logTime'
  },
  {
    title: '备注',
    align: 'center',
    width: 200,
    key: 'remark'
  },
  {
    title: '状态',
    align: 'center',
    fixed: 'right',
    width: 80,
    key: 'status',
    render: (row: any) => (
      <n-tag type={row.status === '0' ? 'success' : 'error'}>{row.status === '0' ? '已完成' : '未点检'}</n-tag>
    )
  }
]);
const checked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};
function init() {
  startLoading();
  data.value = [];
  getEqListLogList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  init();
});
</script>

<style scoped></style>
