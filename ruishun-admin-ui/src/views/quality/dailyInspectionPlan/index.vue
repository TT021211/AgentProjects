<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="送检计划编码">
          <n-space vertical>
            <n-input
              v-model:value="searchForm.dailyplanCode"
              placeholder="请输入送检计划编码"
              :style="{ width: formItemWidth }"
            ></n-input>
          </n-space>
        </n-form-item>
        <n-form-item label="送检计划名称">
          <n-space vertical>
            <n-input
              v-model:value="searchForm.dailyplanName"
              placeholder="请输入送检计划名称"
              :style="{ width: formItemWidth }"
            ></n-input>
          </n-space>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="日常送检计划列表">
      <template #right>
        <div>
          <n-button size="small" type="info" style="margin-right: 10px" @click="addOrEdit(0)">
            <icon-mdi-add />
            新增
          </n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog
      v-model:show="show"
      :title="addForm.dailyplanId ? '编辑日常送检计划' : '新增日常送检计划'"
      width="1000px"
      @cancel="cancel"
      @submit="submit"
    >
      <template #content>
        <div>
          <n-form
            ref="formModel"
            style="width: 900px"
            :rules="rules"
            :model="addForm"
            label-placement="left"
            label-width="120px"
          >
            <n-grid :cols="2" :x-gap="20">
              <n-form-item-grid-item label="送检计划编码" path="dailyplanCode">
                <n-space vertical>
                  <n-input
                    v-model:value="addForm.dailyplanCode"
                    placeholder="请输入送检计划编码"
                    :style="{ width: formItemWidth }"
                  ></n-input>
                </n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="送检计划名称" path="dailyplanName">
                <n-space vertical>
                  <n-input
                    v-model:value="addForm.dailyplanName"
                    placeholder="请输入送检计划名称"
                    :style="{ width: formItemWidth }"
                  ></n-input>
                </n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="QB机台" path="dailyplanDevice">
                <n-space>
                  <n-select
                    v-model:value="dailyplanDeviceId"
                    :options="deviceList"
                    :style="{ width: formItemWidth }"
                    placeholder="请选择机台"
                    filterable
                    @update:value="handleUpdateValue"
                  ></n-select>
                </n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="轴号" path="dailyplanAxle">
                <n-space vertical>
                  <n-select
                    v-model:value="axleNumberId"
                    :style="{ width: formItemWidth }"
                    placeholder="请选择轴号"
                    :options="axleNumberList"
                    filterable
                    @update-value="(val, item: any) => {
              addForm.dailyplanAxle = item.label;
            }"
                  ></n-select>
                </n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="调换前规格" path="dailyplanBeforeSpec">
                <n-space vertical>
                  <n-input-number
                    v-model:value="addForm.dailyplanBeforeSpec"
                    placeholder="请输入调换前规格"
                    :style="{ width: formItemWidth }"
                  />
                </n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="调换后规格" path="dailyplanAfterSpec">
                <n-space vertical>
                  <n-input-number
                    v-model:value="addForm.dailyplanAfterSpec"
                    placeholder="请输入调换后规格"
                    :style="{ width: formItemWidth }"
                  />
                </n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="日期" path="dailyplanDate">
                <n-date-picker
                  v-model:formatted-value="addForm.dailyplanDate"
                  type="datetime"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="送检人" path="dailyplanSubmitter">
                <n-space vertical>
                  <n-input
                    v-model:value="addForm.dailyplanSubmitter"
                    placeholder="请输入送检人"
                    :style="{ width: formItemWidth }"
                  ></n-input>
                </n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="备注">
                <n-input
                  v-model:value="addForm.dailyplanRemark"
                  placeholder="请输入备注"
                  :style="{ width: formItemWidth }"
                ></n-input>
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
  name: 'DailyInspectionPlan'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useDialog, useMessage } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { getWorkbenchEnamellingList } from '@/service/api/md/workbench/enamelling';
import {
  getQcDailyplanList,
  addQcDailyplan,
  editQcDailyplan,
  deleteQcDailyplan
} from '@/service/api/quality/dailyInspectionPlan/index';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';

type SelectMixedOption = {
  label: string;
  value: number;
};

const searchForm = ref<dailyInspectionPlan.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  dailyplanCode: '',
  dailyplanName: ''
});

const addForm = ref<dailyInspectionPlan.addOrEditData>({
  dailyplanDate: null,
  dailyplanDevice: '',
  dailyplanAxle: '',
  dailyplanBeforeSpec: null,
  dailyplanAfterSpec: null,
  dailyplanRemark: '',
  dailyplanSubmitter: '',
  dailyplanId: null,
  dailyplanCode: '',
  dailyplanName: ''
});

const { loading, startLoading, endLoading } = useLoading();

const show = ref<boolean>(false);

const addOrEditType = ref<number>(0); // 0 : 添加， 1 : 编辑

const formItemWidth = ref<string>('240px');
const formModel = ref<FormInst | null>(null);
const message = useMessage();
const dialog = useDialog();
// const addOrEditComplete = ref<boolean>(true);
const dailyplanDeviceId = ref<number | null>(null);
const axleNumberId = ref<number | null>(null);
const rowsWorkbenchEnamellingList = ref<any[]>([]);

const axleNumberList = ref<SelectMixedOption[]>([]);
const deviceList = ref<SelectMixedOption[]>([]);

const rules = {
  dailyplanDate: createRequiredFormRule('请选择日期'),
  dailyplanDevice: createRequiredFormRule('请选择机台'),
  dailyplanAxle: createRequiredFormRule('请选择轴号'),
  dailyplanBeforeSpec: createRequiredFormRule('请输入调换前规格'),
  dailyplanAfterSpec: createRequiredFormRule('请输入调换后规格'),
  dailyplanSubmitter: createRequiredFormRule('请输入送检人'),
  dailyplanCode: createRequiredFormRule('请输入送检计划编码'),
  dailyplanName: createRequiredFormRule('请输入送检计划名称')
};

const data = ref<dailyInspectionPlan.TableList[]>([]);

const columns: Ref<DataTableColumns<dailyInspectionPlan.TableList>> = ref([
  {
    title: '送检计划编码',
    key: 'dailyplanCode',
    align: 'center',
    width: 120
  },
  {
    title: '送检计划名称',
    key: 'dailyplanName',
    align: 'center',
    width: 120
  },
  {
    title: 'QB机台',
    key: 'dailyplanDevice',
    align: 'center',
    width: 100
  },
  {
    title: '轴号',
    key: 'dailyplanAxle',
    align: 'center',
    width: 100
  },
  {
    title: '换规前规格',
    key: 'dailyplanBeforeSpec',
    align: 'center',
    width: 100
  },
  {
    title: '换规后规格',
    key: 'dailyplanAfterSpec',
    align: 'center',
    width: 100
  },
  {
    title: '日期',
    key: 'dailyplanDate',
    align: 'center',
    width: 100
  },
  {
    title: '备注',
    key: 'dailyplanRemark',
    align: 'center',
    width: 100
  },
  {
    title: '送检人',
    key: 'dailyplanSubmitter',
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
            attr-type="button"
            size={'small'}
            onClick={() => {
              addOrEdit(1, row);
            }}
          >
            <icon-mdi-edit />
            编辑
          </n-button>

          <n-button
            type="warning"
            size={'small'}
            onClick={() => {
              handleDeleteItemType(row.dailyplanId);
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

function handleUpdateValue(val, option) {
  addForm.value.dailyplanDevice = option.label;
  const index: number = rowsWorkbenchEnamellingList.value.findIndex(item => item.id === val);
  if (index === -1) return;
  const axleList = rowsWorkbenchEnamellingList.value[index].axleNumber.split(/[，,]/g);
  axleNumberList.value = [];
  axleNumberId.value = null;
  addForm.value.dailyplanAxle = '';
  for (let i = 0; i < axleList.length; i += 1) {
    axleNumberList.value.push({
      value: i,
      label: axleList[i]
    });
  }
}

function search() {
  init();
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    dailyplanCode: '',
    dailyplanName: ''
  };
  init();
}

function addOrEdit(count: number, row?: dailyInspectionPlan.TableList): void {
  addOrEditType.value = count;
  show.value = true;
  if (count === 0) {
    addForm.value = {
      dailyplanDate: null,
      dailyplanDevice: '',
      dailyplanAxle: '',
      dailyplanBeforeSpec: null,
      dailyplanAfterSpec: null,
      dailyplanRemark: '',
      dailyplanSubmitter: '',
      dailyplanCode: '',
      dailyplanName: '',
      dailyplanId: null
    };
    dailyplanDeviceId.value = null;
    axleNumberId.value = null;
  } else {
    if (!row) return;
    const index = deviceList.value.findIndex(item => item.label === row.dailyplanDevice);
    if (index > -1) {
      dailyplanDeviceId.value = deviceList.value[index].value;
    }
    handleUpdateValue(deviceList.value[index].value, deviceList.value[index]);
    for (const key in addForm.value) {
      if (row) {
        addForm.value[key] = row[key];
      }
    }
    const axleIndex = axleNumberList.value.findIndex(item => item.label === row.dailyplanAxle);
    if (axleIndex > -1) {
      axleNumberId.value = axleNumberList.value[axleIndex].value;
    }
  }
}

function cancel(): void {
  show.value = false;
}

function submitSame(func: any, msg: string): void {
  func(addForm.value).then(res => {
    if (res.code === 200) {
      show.value = false;
      message.success(msg);
      init();
    }
  });
}

function submit(): void {
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      if (addOrEditType.value === 0) {
        submitSame(addQcDailyplan, '添加成功');
      } else {
        submitSame(editQcDailyplan, '编辑成功');
      }
    }
  });
}

const sleep = id =>
  new Promise(resolve => {
    deleteQcDailyplan(id).then(res => {
      resolve(res);
    });
  });

function handleDeleteItemType(id: number | null): void {
  if (id === null) return;
  const d = dialog.warning({
    title: '删除',
    content: '确认要删除吗?',
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        sleep(id).then((res: any) => {
          if (res.code === 200) {
            message.success('删除成功');
            init();
            resolve(res);
          }
        });
      });
    }
  });
}

function getList() {
  getWorkbenchEnamellingList({ pageSize: 999, isEnable: 1 }).then(res => {
    rowsWorkbenchEnamellingList.value = res.rows;
    res.rows.forEach((item: { equipmentCode: any; id: any }) => {
      deviceList.value.push({
        label: item.equipmentCode,
        value: item.id
      });
    });
  });
}

function init() {
  startLoading();
  getQcDailyplanList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
  getList();
});
</script>

<style scoped></style>
