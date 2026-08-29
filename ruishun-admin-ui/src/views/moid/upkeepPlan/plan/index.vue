<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="计划编码">
          <n-input v-model:value="searchForm.maintainPlanCode" type="text" placeholder="请输入计划编码" />
        </n-form-item>
        <n-form-item label="计划名称">
          <n-input v-model:value="searchForm.maintainPlanName" type="text" placeholder="请输入计划名称" />
        </n-form-item>
        <n-form-item label="保养类型">
          <n-input v-model:value="searchForm.maintainPlanType" type="text" placeholder="请输入保养类型" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="">
      <template #right>
        <div>
          <n-button size="small" type="info" style="margin-right: 10px" @click="addOrEdit(0)">
            <icon-mdi-add />
            新增计划
          </n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog :show="show" width="800px" :title="addOrEditTitle" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModel"
            style="width: 700px"
            label-placement="left"
            label-align="left"
            :label-width="80"
            :model="addForm"
            :rules="rules"
          >
            <n-grid :cols="2" :x-gap="20">
              <n-form-item-grid-item label="计划编码" path="maintainPlanCode">
                <n-input
                  v-model:value="addForm.maintainPlanCode"
                  placeholder="请输入计划编码"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="计划名称" path="maintainPlanName">
                <n-input
                  v-model:value="addForm.maintainPlanName"
                  placeholder="请输入计划名称"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="保养名称" path="maintainPlanType">
                <n-input
                  v-model:value="addForm.maintainPlanType"
                  placeholder="请输入保养名称"
                  :style="{ width: formItemWidth }"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="开始时间" path="maintainPlanStarttime">
                <n-date-picker
                  v-model:formatted-value="addForm.maintainPlanStarttime"
                  type="datetime"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="结束时间" path="maintainPlanEndtime">
                <n-date-picker
                  v-model:formatted-value="addForm.maintainPlanEndtime"
                  type="datetime"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>

              <n-form-item-grid-item label="频率" path="maintainPlanFrequency">
                <n-select
                  v-model:value="addForm.attr4"
                  :options="frequencyList"
                  :style="{ width: formItemWidth }"
                  placeholder="请选择频率"
                  @update:value="handleUpdateValue"
                />
              </n-form-item-grid-item>
              <!--
              <n-form-item-grid-item label="状态" path="maintainPlanStatus">
                <n-input v-model:value="addForm.maintainPlanStatus" placeholder="请输入状态"
                  :style="{ width: formItemWidth }"></n-input>
              </n-form-item-grid-item> -->

              <n-form-item-grid-item label="拉丝套模" path="moldNesting">
                <n-select
                  v-model:value="addForm.moldNesting"
                  multiple
                  :options="options"
                  :style="{ width: formItemWidth }"
                  placeholder="请选择拉丝套模"
                  clearable
                  filterable
                />
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'UpkeepPlanPlan'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { formatDate } from '~/src/utils/form/rule';
import { getMoidStatus } from '~/src/utils/common/moidFunc';
import {
  getMmoldMaintainPlan,
  addMmoldMaintainPlan,
  editMmoldMaintainPlan,
  deleteMmoldMaintainPlan,
  scheduleJobTask,
  scheduleJobTaskEdit
} from '~/src/service/api/moid/upkeepPlan/index';
import { getMoldNestingList2 } from '~/src/service/api/moid/drawing/index';
const searchForm = ref<upkeepPlan.planSearchFrom>({
  maintainPlanCode: '',
  maintainPlanName: '',
  maintainPlanType: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const addForm = ref<upkeepPlan.planAddForm>({
  maintainPlanId: -1,
  maintainPlanCode: null,
  maintainPlanName: null,
  maintainPlanType: null,
  maintainPlanStarttime: null,
  maintainPlanEndtime: null,
  maintainPlanFrequency: null,
  maintainPlanStatus: null,
  moldNesting: [],
  frequency: null,
  attr4: null,
  triggerName: null,
  jobName: null
});

const formItemWidth = ref<string>('260px');
const addOrEditTitle = ref<string>('');

const { loading, startLoading, endLoading } = useLoading();

const show = ref<boolean>(false);

const type = ref<number>(0); // 0 : 添加， 1 : 编辑

const formModel = ref<FormInst | null>(null);

const message = useMessage();

const addOrEditComplete = ref<boolean>(true);

const optionsList = ref<any[]>([]);

const options = ref<{ label: string; value: number }[]>([]);
const frequencyList = ref<{ label: string; value: number }[]>([
  { label: '1月1次', value: 1 },
  { label: '1季1次', value: 2 },
  { label: '1年1次', value: 3 }
]);

const rules = {
  maintainPlanCode: createRequiredFormRule('请输入计划编码'),
  maintainPlanName: createRequiredFormRule('请输入计划名称'),
  maintainPlanType: createRequiredFormRule('请输入保养名称'),
  maintainPlanStarttime: createRequiredFormRule('请选择开始时间'),
  maintainPlanEndtime: createRequiredFormRule('请选择结束时间'),
  maintainPlanFrequency: createRequiredFormRule('请输入频率'),
  // maintainPlanStatus: createRequiredFormRule('请输入状态'),
  moldNesting: createRequiredFormRule('请选择拉丝套模')
};

const data = ref<upkeepPlan.planTabelList[]>([
  {
    maintainPlanId: 1,
    maintainPlanStatus: '启动',
    maintainPlanCode: 'BYJH231219-01',
    maintainPlanName: '拉丝套模保养计划',
    maintainPlanType: '拉丝每月保养',
    maintainPlanStarttime: '2024-1-1',
    maintainPlanEndtime: '2024-2-1',
    maintainPlanFrequency: '1月1次',
    moldNesting: [],
    triggerName: '',
    jobName: ''
  },
  {
    maintainPlanId: 1,
    maintainPlanStatus: '启动',
    maintainPlanCode: 'BYJH231219-01',
    maintainPlanName: '镀锡套模保养计划',
    maintainPlanType: '镀锡每月保养',
    maintainPlanStarttime: '2024-1-1',
    maintainPlanEndtime: '2024-2-1',
    maintainPlanFrequency: '1月1次',
    moldNesting: [],
    triggerName: '',
    jobName: ''
  },
  {
    maintainPlanId: 1,
    maintainPlanStatus: '启动',
    maintainPlanCode: 'BYJH231219-01',
    maintainPlanName: '拉丝套模保养计划',
    maintainPlanType: '拉丝每月保养',
    maintainPlanStarttime: '2024-1-1',
    maintainPlanEndtime: '2024-2-1',
    maintainPlanFrequency: '1月1次',
    moldNesting: [],
    triggerName: '',
    jobName: ''
  }
]);

const columns: Ref<DataTableColumns<upkeepPlan.planTabelList>> = ref([
  {
    title: '计划编码',
    key: 'maintainPlanCode',
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
    title: '开始时间',
    key: 'maintainPlanStarttime',
    align: 'center',
    width: 100
  },
  {
    title: '结束时间',
    key: 'maintainPlanEndtime',
    align: 'center',
    width: 100
  },
  {
    title: '频率',
    key: 'maintainPlanFrequency',
    align: 'center',
    width: 100
  },
  {
    title: '状态',
    key: 'maintainPlanStatus',
    align: 'center',
    width: 100,
    render: row => {
      const obj = getMoidStatus(row.maintainPlanStatus);
      return <n-tag type={obj.statusType}>{row.maintainPlanStatus}</n-tag>;
    }
  },
  {
    title: '操作',
    key: 'actions',
    align: 'center',
    render: row => {
      if (row.maintainPlanStatus === 'FINISH') {
        return (
          <n-popconfirm onPositiveClick={() => handleDeleteItemType(row.maintainPlanId)}>
            {{
              default: () => '确认删除',
              trigger: () => (
                <n-button type="warning" size={'small'}>
                  <icon-mdi-delete />
                  删除
                </n-button>
              )
            }}
          </n-popconfirm>
        );
      }
      return (
        <n-space justify={'center'}>
          <n-button
            attr-type="button"
            size={'small'}
            v-if={row.maintainPlanStatus === 'FINISH'}
            onClick={() => {
              addOrEdit(1, row);
            }}
          >
            <icon-mdi-edit />
            编辑
          </n-button>
          <n-popconfirm onPositiveClick={() => handleDeleteItemType(row.maintainPlanId)}>
            {{
              default: () => '确认删除',
              trigger: () => (
                <n-button type="warning" size={'small'}>
                  <icon-mdi-delete />
                  删除
                </n-button>
              )
            }}
          </n-popconfirm>
        </n-space>
      );
    }
  }
]);

function search() {
  init();
}
function reset() {
  searchForm.value = {
    maintainPlanCode: '',
    maintainPlanName: '',
    maintainPlanType: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function addOrEdit(count: number, row?: upkeepPlan.planTabelList): void {
  type.value = count;
  show.value = true;
  if (count === 0) {
    addOrEditTitle.value = '新增保养计划';
    for (const key in addForm.value) {
      if (key !== 'moldNesting') {
        addForm.value[key] = null;
      }
    }
    addForm.value.moldNesting = [];
  } else {
    addOrEditTitle.value = '编辑保养计划';
    for (const key in addForm.value) {
      if (row) {
        addForm.value[key] = row[key];
      }
    }
    if (row && row.moldNesting) {
      addForm.value.moldNesting = [];
      row.moldNesting.forEach(item => {
        addForm.value.moldNesting.push(item.nestingMoldId);
      });
    }
  }
}

function cancel(): void {
  show.value = false;
}

function handleUpdateValue(value: number, option: { label: string; value: number }) {
  addForm.value.maintainPlanFrequency = option.label;
  console.log(value);
}

function submitSame(msg: string, res: any, requestType?: string): void {
  show.value = false;
  addOrEditComplete.value = true;

  if (res.code === 200) {
    addForm.value.maintainPlanId = res.data.maintainPlanId;
    addForm.value.maintainPlanStatus = null;
    if (requestType === 'add') {
      scheduleJobTask(addForm.value);
      getMoldNestingOptions();
    }
    if (requestType === 'edit') {
      scheduleJobTaskEdit(addForm.value);
    }
    message.success(msg);
    init();
  }
}

function submit(): void {
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      if (!addOrEditComplete.value) return;
      addOrEditComplete.value = false;
      const moldNestingList = [...addForm.value.moldNesting];
      addForm.value.moldNesting = [];
      optionsList.value.forEach(item => {
        const index = moldNestingList.findIndex(val => val === item.nestingMoldId);
        if (index > -1) {
          addForm.value.moldNesting.push({
            nestingMoldId: item.nestingMoldId,
            nestingName: item.nestingName,
            nestingArea: item.nestingArea,
            nestingScope: item.nestingScope,
            enable: item.enable,
            planId: item.planId
          });
        }
      });
      addForm.value.maintainPlanStatus = 'MAINTENANCE';
      addForm.value.attr4 = 0;
      if (type.value === 0) {
        addMmoldMaintainPlan(addForm.value).then(res => {
          submitSame('添加成功', res, 'add');
        });
      } else {
        editMmoldMaintainPlan(addForm.value).then(res => {
          submitSame('编辑成功', res, 'edit');
        });
      }
    }
  });
}

function handleDeleteItemType(id: number): void {
  if (id !== null) {
    deleteMmoldMaintainPlan(id).then(res => {
      submitSame('删除成功', res, '删除');
    });
  }
}

function getMoldNestingOptions() {
  getMoldNestingList2({
    pageNum: 1,
    pageSize: 999,
    total: 0
  }).then(res => {
    if (res.code === 200) {
      optionsList.value = res.rows;
      options.value = [];
      res.rows.forEach(item => {
        options.value.push({
          label: item.nestingName,
          value: item.nestingMoldId
        });
      });
    }
  });
}

function init() {
  startLoading();
  getMmoldMaintainPlan(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      res.rows.forEach(item => {
        if (item.maintainPlanStarttime) {
          item.maintainPlanStarttime = formatDate(new Date(item.maintainPlanStarttime), 'yyyy-MM-dd hh:mm:ss');
        }
        if (item.maintainPlanEndtime) {
          item.maintainPlanEndtime = formatDate(new Date(item.maintainPlanEndtime), 'yyyy-MM-dd hh:mm:ss');
        }
      });
      data.value = res.rows;

      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
  getMoldNestingOptions();
});
</script>

<style scoped></style>
