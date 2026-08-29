<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="机台">
          <n-select
            v-model:value="searchForm.equipmentId"
            placeholder="请选择机台"
            :options="machineList"
            :filterable="true"
            class="w-160px"
          ></n-select>
        </n-form-item>
        <n-form-item label="订单类型">
          <n-select
            v-model:value="searchForm.orderType"
            placeholder="请选择订单类型"
            :options="orderTypeList"
            class="w-160px"
          ></n-select>
        </n-form-item>

        <n-form-item label="生产工单号">
          <n-input v-model:value="searchForm.workorderCode" type="text" placeholder="请输入生产工单号" />
        </n-form-item>

        <n-form-item label="产品型号">
          <n-input v-model:value="searchForm.model" type="text" placeholder="请输入产品型号" />
        </n-form-item>

        <n-form-item label="规格">
          <n-input v-model:value="searchForm.specification" type="text" placeholder="请输入规格" />
        </n-form-item>

        <n-form-item>
          <component
            :is="
              useSearchBtn(
                () => {
                  search();
                },
                () => {
                  reset();
                }
              )
            "
          />
        </n-form-item>
      </n-form>
    </my-card>
    <my-card :title="title">
      <template #right>
        <div>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>

    <my-dialog :show="show" width="1000px" @cancel="show = false" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModel"
            style="width: 900px"
            label-placement="left"
            label-align="left"
            :label-width="100"
            :rules="rules"
            :model="addForm"
          >
            <n-grid x-gap="12" :cols="2">
              <n-form-item-grid-item label="新工单编号" path="insertId">
                <n-select
                  v-model:value="addForm.insertId"
                  placeholder="请选择新工单编号"
                  class="w-280px"
                  :options="workOrderList"
                  filterable
                  @update:value="handleUpdateValue"
                ></n-select>
              </n-form-item-grid-item>
            </n-grid>
            <n-data-table :columns="inserColumns" :data="inserData"></n-data-table>
          </n-form>
        </div>
      </template>
    </my-dialog>

    <my-dialog
      :show="insertShow"
      width="1000px"
      :title="'插单详情'"
      @cancel="insertShow = false"
      @submit="insertShow = false"
    >
      <template #content>
        <div>
          <n-form ref="formModel" style="width: 900px" label-placement="left" label-align="left" :label-width="100">
            <div class="insertBtn">
              <n-button v-show="status === '0'" type="info" @click="handleDeleteItemType">插单取消</n-button>
            </div>
            <div class="insertBtn">
              <n-button v-show="status === '1'" type="info" @click="handleDeleteItemTypeComplete">插单完成</n-button>
            </div>
            <n-data-table :columns="inserColumns" :data="inserData"></n-data-table>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useMessage, useDialog } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { getWorkbenchEnamellingList } from '@/service/api/md/workbench/enamelling';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import {
  getWorkAnnealing,
  insertAnnealingWorkorder,
  cancelInsertAnnealingWorkorder,
  insertAnnealingWorkorderComplete,
  getAnnealingWorkorder
} from '~/src/service/api/plan/planAnneal';
const { loading, startLoading, endLoading } = useLoading();

const title = ref<string>('退火计划管理列表');
const searchForm = ref<enamelEoverPlanAdmin.searchForm>({
  status: 1,
  pageNum: 1,
  pageSize: 10,
  total: 0,
  equipmentId: null,
  orderType: null,
  model: '',
  workorderCode: '',
  specification: ''
});

const addForm = ref<{ insertId: number | null; id: number | null }>({
  insertId: null,
  id: null
});

const message = useMessage();
const dialog = useDialog();
const show = ref<boolean>(false);
const insertId = ref<number>(-1);
const insertShow = ref<boolean>(false);
const insertComplete = ref<boolean>(true);
const status = ref<string>('');
const rules = {
  insertId: createRequiredFormRule('请选择新工单编号')
};
const workOrderList = ref<{ label: string; value: number }[]>([]);
const workOrderData = ref<planWorkOrder.planWorkOrderList[]>([]);
const machineList = ref<{ label: string; value: number }[]>([]);
const orderTypeList = ref<{ label: string; value: number }[]>([
  { label: '客户订单', value: 0 },
  { label: '备库订单', value: 1 }
]);
const data = ref<planWorkOrder.planWorkOrderList[]>([]);

const columns: Ref<DataTableColumns<planWorkOrder.planWorkOrderList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '退火机台',
    key: 'equipmentCode',
    width: 100
  },
  {
    title: '轴号',
    key: 'axleNumber',
    width: 100
  },
  {
    title: '订单类型',
    width: 100,
    key: 'orderType',
    render: row => {
      return <n-space>{row.orderType === '0' ? '客户订单' : '备库订单'}</n-space>;
    }
  },
  {
    title: '客户',
    key: 'customerName',
    width: 100
  },
  // {
  //   title: '订单编号',
  //   key: 'orderCode',
  //   width: 100,
  //   maxWidth: 180
  // },
  {
    title: '生产工单号',
    key: 'workorderCode',
    width: 180
  },
  {
    title: '产品型号',
    key: 'model',
    width: 100
  },

  {
    title: '产品规格',
    key: 'specification',
    width: 100
  },

  {
    title: '重量(kg)',
    key: 'weight',
    width: 100
  },

  {
    title: '盘数',
    key: 'discNumber',
    width: 100
  },
  {
    title: '开始时间',
    key: 'startTime',
    width: 200,
    maxWidth: 180
  },
  {
    title: '结束时间',
    key: 'endTime',
    width: 200,
    maxWidth: 180
  },
  {
    title: '特殊要求',
    key: 'remark',
    width: 100
  },
  {
    title: '插单工单',
    key: 'insertWorkorder',
    width: 180
  },
  {
    title: '插单型号',
    key: 'insertModel',
    width: 100
  },
  {
    title: '插单状态',
    key: 'insertStatus',
    width: 100,
    render: row => {
      return <n-space>{getInsertStatusName(row.insertStatus)}</n-space>;
    }
  },
  {
    title: '操作',
    key: 'status',
    align: 'center',
    width: 220,
    fixed: 'right',
    render: row => {
      if (row.insertStatus === '0') {
        return (
          <n-button
            type="info"
            onClick={() => {
              handleConfirm(row);
            }}
          >
            插单
          </n-button>
        );
      } else if (row.insertStatus === '2') {
        return (
          <n-button
            type="info"
            onClick={() => {
              insertDetail(row);
            }}
          >
            插单详情
          </n-button>
        );
      }
      return <n-space></n-space>;
    }
  }
]);

const inserData = ref<planWorkOrder.planWorkOrderList[]>([]);

const inserColumns: Ref<DataTableColumns<planWorkOrder.planWorkOrderList>> = ref([
  {
    title: '型号',
    key: 'model',
    width: 100
  },
  {
    title: '规格',
    key: 'specification',
    width: 100
  },
  {
    title: '退火机台',
    key: 'equipmentCode',
    width: 100
  },
  {
    title: '位置',
    key: 'axleNumber',
    width: 100
  },
  {
    title: '重量',
    key: 'weight',
    width: 100
  },
  {
    title: '盘数',
    key: 'discNumber',
    width: 100
  },
  // {
  //   title: '油漆',
  //   key: 'plantName',
  //   width: 100,
  //   maxWidth: 180
  // },
  {
    title: '线盘',
    key: 'wireDisc',
    width: 120,
    maxWidth: 180
  },
  {
    title: '开始时间',
    key: 'startTime',
    width: 200,
    maxWidth: 180
  },
  {
    title: '结束时间',
    key: 'endTime',
    width: 200,
    maxWidth: 180
  },
  {
    title: '特殊要求',
    key: 'remark',
    width: 100
  }
]);

const sleep = (id, func) =>
  new Promise(resolve => {
    func(id).then(res => {
      resolve(res);
    });
  });

function insertDialog(titleText, func) {
  const d = dialog.info({
    title: `插单${titleText}`,
    content: `确认要插单${titleText}吗?`,
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        sleep(insertId.value, func).then((res: any) => {
          if (res.code === 200) {
            message.success(`插单${titleText}成功`);
            init();
            insertShow.value = false;
          }
          resolve(res);
        });
      });
    }
  });
}

function handleDeleteItemType() {
  insertDialog('取消', cancelInsertAnnealingWorkorder);
}

function handleDeleteItemTypeComplete() {
  insertDialog('完成', insertAnnealingWorkorderComplete);
}

function getList() {
  getWorkbenchEnamellingList({ pageSize: 999 }).then(res => {
    res.rows.forEach((item: { equipmentCode: any; id: any }) => {
      machineList.value.push({
        label: item.equipmentCode,
        value: item.id
      });
    });
  });
}

function search() {
  init();
}
function reset() {
  searchForm.value = {
    status: 1,
    pageNum: 1,
    pageSize: 10,
    total: 0,
    equipmentId: null,
    orderType: null,
    model: '',
    workorderCode: '',
    specification: ''
  };
  init();
}

function handleUpdateValue(value: number) {
  inserData.value = workOrderData.value.filter(item => item.id === value);
}

function submit() {
  if (!insertComplete.value) return;
  if (addForm.value.insertId === null) {
    message.warning('请选择新工单编号');
    return;
  }
  insertComplete.value = false;
  insertAnnealingWorkorder(addForm.value).then(res => {
    insertComplete.value = true;
    if (res.code === 200) {
      message.success('插单成功');
      init();
      show.value = false;
    }
  });
}

function getInsertStatusName(num: string | null): string {
  let statusName: string = '';
  switch (num) {
    case '0':
      statusName = '正常';
      break;
    case '1':
      statusName = '插单中';
      break;
    case '2':
      statusName = '被插单';
      break;
    default:
      statusName = '';
      break;
  }
  return statusName;
}

function handleConfirm(row: planWorkOrder.planWorkOrderList) {
  show.value = true;
  const { equipmentCode, axleNumber, specification } = row;
  workOrderList.value = [];
  inserData.value = [];
  addForm.value.insertId = null;
  addForm.value.id = row.id;
  getWorkAnnealing({ equipmentCode, axleNumber, specification, status: 0, pageSize: 999 }).then(res => {
    if (res.code === 200) {
      workOrderData.value = res.rows;
      res.rows.forEach(item => {
        workOrderList.value.push({
          label: item.annealingCode,
          value: item.id
        });
      });
    }
  });
}

function insertDetail(row: planWorkOrder.planWorkOrderList) {
  insertId.value = row.id;
  inserData.value = [];
  insertShow.value = true;
  status.value = '';
  getAnnealingWorkorder(row.insertId).then(res => {
    if (res.code === 200) {
      inserData.value.push(res.data);
      status.value = res.data.status;
    }
  });
}

function init() {
  startLoading();
  getWorkAnnealing({ ...searchForm.value }).then(res => {
    if (res.code === 200) {
      searchForm.value.total = res.total;
      data.value = res.rows;
    }
    endLoading();
  });
}
onMounted(() => {
  init();
  getList();
});
</script>

<style scoped>
.active {
  background-color: lightgreen !important;
  color: #fff !important;
}

.insertBtn {
  text-align: right;
  margin-bottom: 20px;
}
</style>
