<template>
  <div class="h-full">
    <my-card title="搜索条件" search>
      <n-form ref="formRef" inline label-width="auto" :model="searchForm" size="medium">
        <n-form-item label="连接服务名称">
          <n-input v-model:value="searchForm.dictLabel" placeholder="请输入连接服务名称" />
        </n-form-item>
        <n-form-item label="ip">
          <n-input v-model:value="searchForm.dictValue" placeholder="请输入ip" />
        </n-form-item>

        <n-form-item>
          <n-button type="primary" class="mr-10px" @click="search">
            <icon-ic-round-search class="mr-4px text-20px" />
            搜索
          </n-button>
          <n-button @click="reset">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="连接服务列表">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
        </div>
      </template>
      <div>
        <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
        <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
      </div>
    </my-card>
  </div>
</template>

<script lang="tsx" setup>
import { ref } from 'vue';
import type { Ref } from 'vue';
import { NButton, useMessage } from 'naive-ui';
import type { DataTableColumns } from 'naive-ui';
import { useLoading } from '@/hooks';
import { getDictDataList } from '@/service/api/system/dictData';
import { useSocketStore } from '@/store/modules/socket/index';
const message = useMessage();

const searchForm = ref<connectSocket.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  dictLabel: '',
  dictValue: '',
  dictType: 'sys_connect_socket'
});

const { loading, startLoading, endLoading } = useLoading(false);

const socketStore = useSocketStore();

const data = ref<connectSocket.TableList[]>([]);

const columns: Ref<DataTableColumns<connectSocket.TableList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_, index) => {
      return `${index + 1}`;
    }
  },
  {
    title: '连接服务名称',
    key: 'dictLabel',
    align: 'center'
  },
  {
    title: 'ip',
    key: 'dictValue',
    align: 'center'
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    render: row => {
      return (
        <n-space justify={'center'}>
          <n-button
            size="small"
            type="info"
            onClick={() => {
              connect(row.dictValue);
            }}
          >
            连接
          </n-button>
          <n-button
            size="small"
            type="info"
            v-show={socketStore.socket === row.dictValue}
            onClick={() => {
              breakConnect();
            }}
          >
            断开
          </n-button>
        </n-space>
      );
    }
  }
]);

function connect(ip: string) {
  if (socketStore.connected) {
    message.warning('已连接，请先断开连接');
    return;
  }
  socketStore.setOpenSocket(ip);
}
// wss://https://192.168.11.45/ws?userId=ls1
function breakConnect() {
  socketStore.setCloseScoket();
}

// 搜索
function search() {
  init();
}

// 重置
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    dictLabel: '',
    dictValue: '',
    dictType: 'sys_connect_socket'
  };
  init();
}

function init() {
  startLoading();
  getDictDataList(searchForm.value).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
    endLoading();
  });
}
init();
</script>
