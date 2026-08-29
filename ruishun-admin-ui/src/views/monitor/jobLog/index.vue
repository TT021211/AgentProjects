<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="任务名称">
          <n-input v-model:value="searchForm.jobName" type="text" placeholder="请输入任务名称" />
        </n-form-item>
        <n-form-item label="任务组名">
          <n-select
            v-model:value="searchForm.jobGroup"
            placeholder="请选择任务组名"
            class="w-180px"
            :options="taskNameList"
          ></n-select>
        </n-form-item>
        <n-form-item label="执行状态">
          <n-select
            v-model:value="searchForm.status"
            placeholder="请选择执行状态"
            class="w-180px"
            :options="taskStatusList"
          ></n-select>
        </n-form-item>
        <n-form-item label="执行时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>

    <div>
      <div class="btn-box" style="margin-bottom: 20px">
        <n-button
          size="small"
          type="error"
          style="margin-right: 10px"
          :disabled="checkedRowKeys.length === 0"
          @click="deleteJobLogList"
        >
          <icon-mdi-delete />
          删除
        </n-button>
        <n-button size="small" type="error" style="margin-right: 10px" @click="clearTableList">
          <icon-mdi-delete />
          清 空
        </n-button>
        <n-button size="small" type="warning" style="margin-right: 10px" @click="handleExport">
          <icon-mdi-export />
          导出
        </n-button>
        <n-button size="small" type="warning" style="margin-right: 10px" @click="closeCurrentPage">
          <icon-mdi-close />
          关闭
        </n-button>
      </div>
      <n-data-table
        v-model:checked-row-keys="checkedRowKeys"
        :loading="loading"
        :columns="columns"
        :data="data"
        :row-key="rowKey"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </div>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import type { DataTableColumns } from 'naive-ui';
import { useMessage, useDialog } from 'naive-ui';
import { useTabStore } from '@/store';
import { download } from '@/service/request/helpers';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { getJobLogList, deleteJobLog, cleanJobLog } from '~/src/service/api/monitor/jobLog';
import { formatDate } from '~/src/utils/form/rule';
const searchForm = ref<jobLog.searchForm>({
  jobName: '',
  jobGroup: null,
  status: null,
  'params[beginTime]': null,
  'params[endTime]': null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const { loading, startLoading, endLoading } = useLoading();
const tab = useTabStore();
const router = useRouter();
const taskNameList = ref<{ label: string; value: string }[]>([
  { label: '默认', value: 'DEFAULT' },
  { label: '系统', value: 'SYSTEM' }
]);
const taskStatusList = ref<{ label: string; value: string }[]>([
  { label: '成功', value: '0' },
  { label: '失败', value: '1' }
]);
const range = ref<[number, number] | null>(null);
const message = useMessage();
const dialog = useDialog();
const deleteJobLogIds = ref<string>('');
const checkedRowKeys = ref<Array<string | number>>([]);
const rowKey = (row: any) => row.jobLogId;

const data = ref<jobLog.TableList[]>([]);

const columns: Ref<DataTableColumns<jobLog.TableList>> = ref([
  {
    type: 'selection'
  },
  {
    title: '日志编号',
    key: 'jobName',
    align: 'center',
    width: 100,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '任务名称',
    key: 'jobName',
    align: 'center',
    width: 100
  },
  {
    title: '任务组名',
    key: 'jobGroup',
    align: 'center',
    width: 100,
    render: row => (row.jobGroup === 'DEFAULT' ? '默认' : '系统')
  },
  {
    title: '调用目标字符串',
    key: 'invokeTarget',
    align: 'center',
    width: 100
  },
  {
    title: '日志信息',
    key: 'jobMessage',
    align: 'center',
    width: 100
  },
  {
    title: '状态',
    key: 'status',
    align: 'center',
    width: 100,
    render: row => (row.status === '0' ? '成功' : '失败')
  },
  {
    title: '执行时间',
    key: 'createTime',
    align: 'center',
    width: 100
  },
  {
    title: '操作',
    key: 'actions',
    align: 'center',
    render: row => {
      return (
        <n-space justify={'center'}>
          <n-button
            size="small"
            type="error"
            style="margin-right: 10px"
            onClick={() => {
              deleteJobLogOne(row);
            }}
          >
            <icon-mdi-delete />
            删除
          </n-button>
        </n-space>
      );
    }
  }
]);

const sleep = (func: any, value: any) =>
  new Promise(resolve => {
    func(value).then(res => {
      resolve(res);
    });
  });

function deleteJobLogs(func: any, title: string, value?: any) {
  const d = dialog.warning({
    title: `${title}`,
    content: `确认要${title}吗?`,
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        sleep(func, value).then((res: any) => {
          if (res.code === 200) {
            message.success(`${title}成功`);
            init();
            resolve(res);
          }
        });
      });
    }
  });
}

function deleteJobLogList() {
  if (checkedRowKeys.value.length === 0) {
    message.warning('请选择列表进行删除');
    return;
  }
  deleteJobLogIds.value = '';
  checkedRowKeys.value.forEach(item => {
    deleteJobLogIds.value += `${item},`;
  });
  deleteJobLogIds.value = deleteJobLogIds.value.slice(0, deleteJobLogIds.value.length - 1);
  deleteJobLogs(deleteJobLog, '删除', deleteJobLogIds.value);
}

function deleteJobLogOne(row: jobLog.TableList) {
  deleteJobLogIds.value = row.jobLogId.toString();
  deleteJobLogs(deleteJobLog, '删除', deleteJobLogIds.value);
}

function clearTableList() {
  deleteJobLogs(cleanJobLog, '清除全部');
}

function handleExport() {
  download('job/job/log/export', searchForm.value, `jobLog_${new Date().getTime()}.xlsx`, undefined);
}

function closeCurrentPage() {
  const tabItem = tab.tabs.find(item => item.fullPath === '/monitor/jobLog');
  if (tabItem?.fullPath) {
    tab.removeTab(tabItem.fullPath);
  }
  router.back();
}

function search() {
  if (range.value !== null) {
    searchForm.value['params[beginTime]'] = formatDate(new Date(range.value[0]), 'yyyy-MM-dd');
    searchForm.value['params[endTime]'] = formatDate(new Date(range.value[1]), 'yyyy-MM-dd');
  }
  init();
}
function reset() {
  searchForm.value = {
    jobName: '',
    jobGroup: null,
    status: null,
    'params[beginTime]': null,
    'params[endTime]': null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function init() {
  startLoading();
  getJobLogList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
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

.n-tag {
  cursor: pointer;
}

.active {
  background-color: blue !important;
  color: #fff !important;
}
</style>
