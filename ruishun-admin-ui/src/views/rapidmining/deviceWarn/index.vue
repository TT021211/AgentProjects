<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="设备名称">
          <n-select
            v-model:value="searchForm.deivceName"
            :clearable="true"
            :options="codeOptions"
            class="w-180px"
            placeholder="请选择设备名称"
          ></n-select>
        </n-form-item>
        <n-form-item>
          <n-button class="mr-5px" type="primary" @click="handleSearch">
            <icon-tdesign-search class="mr-2px" />
            搜索
          </n-button>
          <n-button @click="handleReset">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="数据异常警告">
      <template #right>
        <CxColumns v-model:columns="columns" size="medium" />
      </template>
      <n-data-table :data="data" :loading="loading" :row-key="rowKey" :columns="columns"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { type DataTableColumns } from 'naive-ui';
import { useLoading } from '~/src/hooks';

const { loading, startLoading, endLoading } = useLoading();

const codeOptions = [
  {
    label: '配电房',
    value: 'PDF'
  },
  {
    label: 'UV光解',
    value: 'UV'
  },
  {
    label: 'RTO',
    value: 'RTO'
  },
  {
    label: '空压机',
    value: 'KYJ'
  },
  {
    label: '称重机',
    value: 'CZ'
  },
  {
    label: '漆包机',
    value: 'QBJ'
  },
  {
    label: '拉丝机',
    value: 'LSJ'
  }
];
type searchType = {
  deivceName: string | null;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchForm = ref<searchType>({
  deivceName: null,
  pageNum: 1,
  pageSize: 10,
  total: 3
});

function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    deivceName: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

const data = ref<DataType[]>([
  {
    deivceName: '拉丝机01',
    deivceCode: 'LS001',
    deivceIp: '192.168.1.104',
    time: '2024-11-11 09:08:07',
    status: '链接超时'
  },
  {
    deivceName: '拉丝机02',
    deivceCode: 'LS002',
    deivceIp: '192.168.1.109',
    time: '2024-11-11 09:08:07',
    status: '链接超时'
  },
  {
    deivceName: '拉丝机03',
    deivceCode: 'LS003',
    deivceIp: '192.168.1.102',
    time: '2024-11-11 09:08:07',
    status: '链接超时'
  }
]);
type DataType = {
  deivceName: string;
  deivceCode: string;
  deivceIp: string;
  time: string;
  status: string;
};
const rowKey = (row: any) => row.itemCode;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '设备名称',
    align: 'center',
    key: 'deivceName'
  },
  {
    title: '设备编码',
    align: 'center',
    key: 'deivceCode'
  },
  {
    title: '设备IP',
    align: 'center',
    key: 'deivceIp'
  },
  {
    title: '异常时间',
    align: 'center',
    key: 'time'
  },
  {
    title: '接口状态',
    align: 'center',
    key: 'status'
  },
  {
    title: '操作',
    key: 'action',
    titleAlign: 'center',
    width: 80,
    render() {
      return (
        <div>
          <n-button type="info" size="small">
            <icon-tdesign-delete class="mr-2" />
            重连
          </n-button>
        </div>
      );
    }
  }
]);

function init() {
  startLoading();
  // data.value = [];
  // getStorageManageList(searchForm.value).then(res => {
  // data.value = res.rows;
  // searchForm.value.total = res.total;
  // });
  endLoading();
}
onMounted(() => {
  init();
});
</script>
<style scope>
.test {
  width: 1200px;
  display: flex;
  flex-direction: column;
}
.n-icon {
  margin-right: 5px;
}
.n-button .n-button__content {
  font-size: 14px;
}
</style>
