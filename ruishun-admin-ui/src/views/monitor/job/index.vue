<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="任务名称">
          <n-input v-model:value="searchForm.jobName" type="text" placeholder="请输入任务名称" />
        </n-form-item>
        <n-form-item label="任务组名" path="moldType">
          <n-select
            v-model:value="searchForm.jobGroup"
            placeholder="请选择任务组名"
            class="w-180px"
            :options="taskNameList"
          ></n-select>
        </n-form-item>
        <n-form-item label="任务状态" path="moldType">
          <n-select
            v-model:value="searchForm.status"
            placeholder="请选择任务状态"
            class="w-180px"
            :options="taskStatusList"
          ></n-select>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>

    <div>
      <div class="btn-box" style="margin-bottom: 20px">
        <n-button type="info" style="margin-right: 10px" :size="'small'" @click="addOrEditTask(0)">
          <icon-mdi-add />
          新增
        </n-button>
        <n-button
          size="small"
          type="success"
          style="margin-right: 10px"
          :disabled="checkedRowKeys.length !== 1"
          @click="addOrEditTask(1)"
        >
          <icon-mdi-edit />
          修改
        </n-button>
        <n-button
          size="small"
          type="error"
          style="margin-right: 10px"
          :disabled="checkedRowKeys.length === 0"
          @click="deleteJobList"
        >
          <icon-mdi-delete />
          删除
        </n-button>
        <n-button size="small" type="warning" style="margin-right: 10px" @click="handleExport">
          <icon-mdi-export />
          导出
        </n-button>
        <n-button size="small" style="margin-right: 10px" @click="lookLog">
          <icon-mdi-record />
          日志
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

    <my-dialog :show="show" width="800px" :title="dialogTitle" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModel"
            width="700px"
            label-placement="left"
            label-align="left"
            :label-width="100"
            :model="addForm"
            :rules="rules"
          >
            <n-grid x-gap="12" :cols="2">
              <n-form-item-grid-item label="任务名称" path="jobName">
                <n-input
                  v-model:value="addForm.jobName"
                  type="text"
                  placeholder="请输入任务名称"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="任务组名">
                <n-select
                  v-model:value="addForm.jobGroup"
                  placeholder="请选择任务组名"
                  class="w-180px"
                  :style="{ width: formItemWidth }"
                  :options="taskNameList"
                ></n-select>
              </n-form-item-grid-item>
            </n-grid>

            <n-form-item label="调用方法" path="invokeTarget">
              <n-input v-model:value="addForm.invokeTarget" type="text" placeholder="请输入调用方法" />
            </n-form-item>
            <n-form-item label="cron表达式" path="invokeTarget">
              <n-input v-model:value="addForm.cronExpression" type="text" placeholder="请输入cron表达式" />
            </n-form-item>
            <n-form-item label="执行策略" path="invokeTarget">
              <div>
                <n-button
                  v-for="item in misfirePolicyList"
                  :key="item.value"
                  :class="addForm.misfirePolicy === item.value ? 'active' : ''"
                  @click="changeMisfirePolicy(item.value)"
                >
                  {{ item.label }}
                </n-button>
              </div>
            </n-form-item>
            <n-form-item label="是否并发">
              <div>
                <n-button
                  v-for="item in concurrentList"
                  :key="item.value"
                  :class="addForm.concurrent === item.value ? 'active' : ''"
                  @click="changeConcurrent(item.value)"
                >
                  {{ item.label }}
                </n-button>
              </div>
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>

    <my-dialog
      v-model:show="showDialog"
      title="任务详细"
      width="800px"
      @cancel="showDialog = false"
      @submit="showDialog = false"
    >
      <template #content>
        <div>
          <n-form style="width: 700px" :model="formModelData" label-placement="left" label-width="auto">
            <n-grid :cols="2" :x-gap="20">
              <n-form-item-grid-item label="任务编号: ">
                <n-space>{{ formModelData.index }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="任务分组: ">
                <n-space>{{ formModelData.jobGroup }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="任务名称: ">
                <n-space>{{ formModelData.jobName }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="创建时间: ">
                <n-space>{{ formModelData.createTime }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="cron表达式: ">
                <n-space>{{ formModelData.cronExpression }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="下次执行时间: ">
                <n-space>{{ formModelData.nextValidTime }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="调用目标方法: ">
                <n-space>{{ formModelData.invokeTarget }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="任务状态: ">
                <n-space>{{ formModelData.status }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="是否并发: ">
                <n-space>{{ formModelData.concurrent }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="执行策略: ">
                <n-space>{{ formModelData.misfirePolicy }}</n-space>
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage, useDialog, NButton, NPopselect } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { download } from '@/service/request/helpers';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { getJobList, addJob, editJon, deleteJob, changeStatus, executeOnce } from '~/src/service/api/monitor/job';
const searchForm = ref<job.searchForm>({
  jobName: '',
  jobGroup: null,
  status: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const addForm = ref<job.addForm>({
  concurrent: '0', // 0 允许 ；1禁止
  cronExpression: '',
  misfirePolicy: '1', // 执行策略
  jobName: '',
  invokeTarget: '',
  jobGroup: null,
  jobId: null,
  status: '1'
});

const formModelData = ref<job.FormModel>({
  index: 0,
  jobGroup: '',
  jobName: '',
  createTime: '',
  cronExpression: '',
  nextValidTime: '',
  invokeTarget: '',
  status: '',
  concurrent: '',
  misfirePolicy: ''
});

const dialogTitle = ref<string>('');
const router = useRouter();
const { loading, startLoading, endLoading } = useLoading();
const taskNameList = ref<{ label: string; value: string }[]>([
  { label: '默认', value: 'DEFAULT' },
  { label: '系统', value: 'SYSTEM' }
]);
const taskStatusList = ref<{ label: string; value: string }[]>([
  { label: '正常', value: '0' },
  { label: '暂停', value: '1' }
]);
const misfirePolicyList = ref<{ label: string; value: string }[]>([
  { label: '立即执行', value: '1' },
  { label: '执行一次', value: '2' },
  { label: '放弃执行', value: '3' }
]);
const concurrentList = ref<{ label: string; value: string }[]>([
  { label: '允许', value: '0' },
  { label: '禁止', value: '1' }
]);
const moreOptions = [
  {
    label: '执行一次',
    value: '0'
  },
  {
    label: '任务详细',
    value: '1'
  },
  {
    label: '调度日志',
    value: '2'
  }
];
const show = ref<boolean>(false);
const showDialog = ref<boolean>(false);
const formModel = ref<FormInst | null>(null);
const formItemWidth = ref<string>('260px');
const message = useMessage();
const dialog = useDialog();
const isSubmitComplete = ref<boolean>(true);
const addOrEditType = ref<number>(0); // 0 : 添加， 1 :编辑
const deleteJobIds = ref<string>('');
const checkedRowKeys = ref<Array<string | number>>([]);
const rowKey = (row: any) => row.jobId;

const rules = {
  jobName: createRequiredFormRule('请输入归还人'),
  invokeTarget: createRequiredFormRule('请输入调用方法'),
  cronExpression: createRequiredFormRule('请输入cron表达式')
};

const data = ref<job.TableList[]>([]);

const columns: Ref<DataTableColumns<job.TableList>> = ref([
  {
    type: 'selection'
  },
  {
    title: '任务编号',
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
    title: 'cron执行表达式',
    key: 'cronExpression',
    align: 'center',
    width: 100
  },
  {
    title: '状态',
    key: 'moldOutArea',
    align: 'center',
    width: 100,
    render: row => {
      return (
        <my-switch
          v-model:value={row.status}
          checked={'0'}
          unchecked={'1'}
          onChecked={val => {
            changeRowStatus(row, val);
          }}
          onUnchecked={val => {
            changeRowStatus(row, val);
          }}
        ></my-switch>
      );
    }
  },
  {
    title: '操作',
    key: 'actions',
    align: 'center',
    render: (row, index) => {
      return (
        <n-space justify={'center'}>
          <n-button
            size="small"
            type="success"
            onClick={() => {
              updateRow(row.jobId);
            }}
          >
            <icon-mdi-edit />
            修改
          </n-button>
          <n-button
            size="small"
            type="error"
            onClick={() => {
              deleteJobOne(row);
            }}
          >
            <icon-mdi-delete />
            删除
          </n-button>
          <NPopselect trigger="click" options={moreOptions} onUpdateValue={value => roleMore(value, row, index)}>
            <n-button size="small" type="warning" style="margin-right: 10px">
              <icon-mdi-export />
              更多
            </n-button>
          </NPopselect>
        </n-space>
      );
    }
  }
]);

function addOrEditTask(typeValue: number) {
  if (typeValue === 0) {
    dialogTitle.value = '新增';
    addForm.value = {
      concurrent: '0', // 0 允许 ；1禁止
      cronExpression: '',
      misfirePolicy: '1', // 执行策略
      jobName: '',
      invokeTarget: '',
      jobGroup: null,
      jobId: null,
      status: '1'
    };
  } else {
    if (checkedRowKeys.value.length !== 1) {
      message.warning('请选择一个列表进行修改');
      return;
    }
    dialogTitle.value = '编辑';
    const row = data.value.find(item => item.jobId === checkedRowKeys.value[0]);
    if (row) {
      for (const key in addForm.value) {
        if (row[key]) {
          addForm.value[key] = row[key];
        }
      }
    }
  }
  show.value = true;
  addOrEditType.value = typeValue;
}

function updateRow(jobId: number) {
  checkedRowKeys.value = [jobId];
  addOrEditTask(1);
}

function changeConcurrent(concurrent: string) {
  addForm.value.concurrent = concurrent;
}

function changeMisfirePolicy(misfirePolicy: string) {
  addForm.value.misfirePolicy = misfirePolicy;
}

function changeRowStatus(row: job.TableList, val: string) {
  row.status = val;
  changeStatus({ jobId: row.jobId, status: row.status }).then(res => {
    if (res.code === 200) {
      message.success('状态修改成功');
    }
  });
}

function handleExport() {
  download('job/job/export', searchForm.value, `job_${new Date().getTime()}.xlsx`, undefined);
}

function cancel() {
  show.value = false;
}

function requestSucess(msg: string) {
  message.success(msg);
  show.value = false;
  init();
}

function submit() {
  if (!isSubmitComplete.value) return;
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      isSubmitComplete.value = false;
      if (addOrEditType.value === 0) {
        addJob(addForm.value).then(res => {
          isSubmitComplete.value = true;
          if (res.code === 200) {
            requestSucess('添加成功');
          }
        });
      } else {
        editJon(addForm.value).then(res => {
          isSubmitComplete.value = true;
          if (res.code === 200) {
            requestSucess('编辑成功');
          }
        });
      }
    }
  });
}

const sleep = (func: any, value: any) =>
  new Promise(resolve => {
    func(value).then(res => {
      resolve(res);
    });
  });

function diaLogFunc(title: string, func: any, value: any) {
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

function roleMore(value: string, row: job.TableList, index: number) {
  const statusIndex = taskStatusList.value.findIndex(item => item.value === row.status);
  const concurrentIndex = concurrentList.value.findIndex(item => item.value === row.concurrent);
  const misfirePolicyIndex = misfirePolicyList.value.findIndex(item => item.value === row.misfirePolicy);
  switch (value) {
    case '0':
      diaLogFunc('执行一次', executeOnce, { jobGroup: 'DEFAULT', jobId: row.jobId });
      break;
    case '1':
      showDialog.value = true;
      for (const key in formModelData.value) {
        if (row[key]) {
          formModelData.value[key] = row[key];
        }
      }
      if (statusIndex > -1) {
        formModelData.value.status = taskStatusList.value[statusIndex].label;
      }
      if (concurrentIndex > -1) {
        formModelData.value.concurrent = concurrentList.value[concurrentIndex].label;
      }
      if (misfirePolicyIndex > -1) {
        formModelData.value.misfirePolicy = misfirePolicyList.value[misfirePolicyIndex].label;
      }
      formModelData.value.index = index;

      break;
    case '2':
      lookLog();
      break;
    default:
      break;
  }
}

function deleteJobList() {
  if (checkedRowKeys.value.length === 0) {
    message.warning('请选择列表进行删除');
    return;
  }
  deleteJobIds.value = '';
  checkedRowKeys.value.forEach(item => {
    deleteJobIds.value += `${item},`;
  });
  deleteJobIds.value = deleteJobIds.value.slice(0, deleteJobIds.value.length - 1);
  diaLogFunc('删除', deleteJob, deleteJobIds.value);
}

function deleteJobOne(row: job.TableList) {
  deleteJobIds.value = row.jobId.toString();
  diaLogFunc('删除', deleteJob, deleteJobIds.value);
}

function lookLog() {
  router.push({
    name: 'monitor_jobLog'
  });
}

function search() {
  init();
}
function reset() {
  searchForm.value = {
    jobName: '',
    jobGroup: null,
    status: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function init() {
  startLoading();
  getJobList(searchForm.value).then(res => {
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

.active {
  background-color: blue !important;
  color: #fff !important;
}
</style>
