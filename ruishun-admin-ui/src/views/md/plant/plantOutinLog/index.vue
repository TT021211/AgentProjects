<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="油漆名称">
          <n-input v-model:value="searchForm.plantName" placeholder="请输入油漆名称"></n-input>
        </n-form-item>
        <n-form-item label="油漆型号">
          <n-input v-model:value="searchForm.plantType" placeholder="请输入油漆型号" />
        </n-form-item>
        <n-form-item label="状态">
          <n-select
            v-model:value="searchForm.status"
            class="w-180px"
            :options="[
              { label: '全部', value: '' },
              { label: '出库', value: '0' },
              { label: '入库', value: '1' }
            ]"
          />
        </n-form-item>
        <n-form-item label="出入库时间">
          <n-date-picker v-model:formatted-value="searchForm.createTime" type="daterange" clearable />
        </n-form-item>
        <n-form-item>
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
          ></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="油漆型号">
      <template #right>
        <n-button :loading="exportLoading" size="small" type="warning" class="mr-5px ml-5px" @click="exportList">
          <icon-uil:export class="mr-4px text-20px" />
          导出
        </n-button>
        <CxColumns v-model:columns="columns" size="small" />
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
import { download } from '@/service/request/helpers';
import { getPlantOutinLogList } from '@/service/api/md/plant/plantOutinLog';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();

type searchType = {
  plantType: string;
  plantName: string;
  createTime: string | null;
  status: string;
  pageNum: number;
  pageSize: number;
  total: number;
  params: {
    beginTime: string;
    endTime: string;
  };
};

const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  plantType: '',
  plantName: '',
  createTime: '',
  status: '',
  pageNum: 1,
  pageSize: 10,
  total: 0,
  params: {
    beginTime: '',
    endTime: ''
  }
});
function handleSearch() {
  if (searchForm.value.createTime) {
    searchForm.value.params = {
      beginTime: searchForm.value.createTime[0],
      endTime: searchForm.value.createTime[1]
    };
    searchForm.value.createTime = null;
  }
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    plantType: '',
    plantName: '',
    createTime: '',
    status: '',
    pageNum: 1,
    pageSize: 10,
    total: 0,
    params: {
      beginTime: '',
      endTime: ''
    }
  };
  init();
}
const exportLoading = ref<boolean>(false);
function exportList() {
  exportLoading.value = true;
  download('/md/mdPlantOutinLog/export', searchForm.value, `油漆出入库记录.xlsx`, undefined)
    .then(() => {
      exportLoading.value = false;
    })
    .catch(() => {
      exportLoading.value = false;
    });
}
type DataType = {
  id?: number;
  plantName: string;
  plantCode: string;
  plantType: string;
  weight: string;
  measureName: string;
  customerBatch: string;
  workbenchCode: string;
  status: string;
  createTime: string;
  createBy: string;
  jobNumber: string;
};

const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '二维码编号',
    align: 'center',
    key: 'identificationCode'
  },
  {
    title: '油漆编码',
    align: 'center',
    key: 'plantCode'
  },
  {
    title: '油漆名称',
    align: 'center',
    key: 'plantName'
  },
  {
    title: '油漆型号',
    align: 'center',
    key: 'plantType'
  },
  {
    title: '油漆重量',
    align: 'center',
    key: 'weight'
  },
  {
    title: '单位',
    align: 'center',
    key: 'measureName'
  },
  {
    title: '客户批号',
    align: 'center',
    key: 'customerBatch'
  },
  {
    title: '供应商',
    align: 'center',
    key: 'supplierName'
  },
  {
    title: '批号',
    align: 'center',
    key: 'lot_number'
  },
  {
    title: '机台号',
    align: 'center',
    key: 'workbenchCode'
  },
  {
    title: '使用人',
    align: 'center',
    key: 'createBy'
  },
  {
    title: '工号',
    align: 'center',
    key: 'jobNumber'
  },
  {
    title: '状态',
    align: 'center',
    key: 'status',
    render(row: any) {
      return <n-tag type={row.status === '0' ? 'success' : 'error'}>{row.status === '0' ? '入库' : '出库'}</n-tag>;
    }
  },
  {
    title: '出入库时间',
    align: 'center',
    key: 'createTime'
  }
]);

const checked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};

function init() {
  startLoading();
  data.value = [];
  getPlantOutinLogList(searchForm.value).then(res => {
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
