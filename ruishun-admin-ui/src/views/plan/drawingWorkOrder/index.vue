<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="工单状态：">
          <n-select
            v-model:value="searchForm.status"
            placeholder="请选择工单状态"
            class="w-180px"
            :options="workOrderStatus"
          ></n-select>
        </n-form-item>

        <n-form-item label="机台">
          <n-select
            placeholder="请选择机台"
            :options="machineList"
            :filterable="true"
            class="w-160px"
            @update-value="(val, item: any) => {
                searchForm.equipmentCode = item.label;
             }"
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

        <n-form-item label="拉丝工单号">
          <n-input v-model:value="searchForm.wiredrawingCode" type="text" placeholder="请输入拉丝工单号" />
        </n-form-item>

        <n-form-item label="产品型号">
          <n-input v-model:value="searchForm.model" type="text" placeholder="请输入产品型号" />
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

      <n-form inline>
        <n-form-item label="时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>

        <n-form-item label="规格">
          <n-input v-model:value="searchForm.specification" type="text" placeholder="请输入规格" />
        </n-form-item>

        <n-form-item label="LS机台">
          <n-input v-model:value="searchForm.enamellingEquipmentCode" type="text" placeholder="请输入LS机台" />
        </n-form-item>
      </n-form>
    </my-card>
    <my-card :title="title">
      <template #right>
        <div>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :max-height="400"
        :scroll-x="2400"
        :loading="loading"
        :columns="columns"
        :data="data"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>

    <my-dialog
      v-model:show="showDialog"
      title="修改拉丝机台"
      width="800px"
      @cancel="showDialog = false"
      @submit="submitUpdate"
    >
      <template #content>
        <n-form
          ref="formModel"
          style="width: 700px"
          :model="formModelData"
          label-placement="left"
          label-width="90px"
          :rules="rules"
        >
          <n-form-item label="机台" path="equipmentList">
            <n-select
              v-model:value="formModelData.equipmentList"
              placeholder="请选择机台"
              :options="machineList"
              :filterable="true"
              class="w-260px"
              :multiple="true"
            ></n-select>
          </n-form-item>
        </n-form>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'DrawingWorkOrder'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { useDialog, useMessage } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { getWorkbenchWiredrawingList } from '@/service/api/md/workbench/wiredrawing';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import { addMoldGaugeList } from '~/src/service/api/moid/gaugeChangeForm/index';
import { formatDate } from '~/src/utils/form/rule';
import { getStatusName } from '~/src/utils/common/workOrder';
import { wiredrawingWorkorderComplete } from '~/src/service/api/produre/workOrderArrange';
import { getWiredrawing, editWiredrawingWorkorder } from '~/src/service/api/plan/drawingWorkOrder';
const { loading, startLoading, endLoading } = useLoading();

const title = ref<string>('拉丝工单-全部');
// 工单状态
const workOrderStatus = [
  { label: '全部', value: undefined },
  { label: '已排产', value: 0 },
  { label: '生产中', value: 1 },
  { label: '已完工', value: 2 },
  { label: '已作废', value: 3 }
];
const searchForm = ref<drawingWorkOrder.searchForm>({
  status: undefined,
  pageNum: 1,
  pageSize: 10,
  total: 0,
  equipmentId: null,
  orderType: null,
  model: '',
  wiredrawingCode: '',
  specification: '',
  enamellingEquipmentCode: '',
  equipmentCode: ''
});
const formModelData = ref<{
  equipmentList: number[];
}>({
  equipmentList: []
});
const range = ref<[number, number] | null>(null);
const machineList = ref<{ label: string; value: number }[]>([]);
const rowDetail = ref<any>();
const formModel = ref<FormInst | null>(null);
const showDialog = ref<boolean>(false);
const orderTypeList = ref<{ label: string; value: number }[]>([
  { label: '客户订单', value: 0 },
  { label: '备库订单', value: 1 }
]);
const dialog = useDialog();
const message = useMessage();

const rules = {
  equipmentList: createRequiredFormRule('请选择修改机台')
};

const data = ref<drawingWorkOrder.tabelList[]>([]);

const columns: Ref<DataTableColumns<drawingWorkOrder.tabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '创建时间',
    width: 180,
    align: 'center',
    key: 'createTime'
  },
  {
    title: '订单类型',
    key: 'orderType',
    width: 100,
    render: row => {
      return <n-space>{row.orderType === '0' ? '客户订单' : '备库订单'}</n-space>;
    }
  },
  {
    title: '客户',
    align: 'center',
    width: 100,
    key: 'customerName'
  },
  // {
  //   title: '销售订单编号',
  //   width: 100,
  //   key: 'orderCode'
  // },
  // {
  //   title: '生产工单号',
  //   width: 100,
  //   key: 'workorderCode'
  // },
  {
    title: '拉丝工单号',
    align: 'center',
    width: 180,
    key: 'wiredrawingCode'
  },
  {
    title: '拉丝机台',
    align: 'center',
    width: 100,
    ellipsis: {
      tooltip: true
    },
    key: 'equipmentCode'
  },
  {
    title: '完成度',
    align: 'center',
    width: 100,
    key: 'degreeOfCompletion',
    render: (row: any) => {
      return (
        <n-progress
          type="line"
          percentage={row.degreeOfCompletion * 100}
          indicator-placement="inside"
          processing
        ></n-progress>
      );
    }
  },
  {
    title: '线盘',
    align: 'center',
    width: 80,
    key: 'wireDisc'
  },
  // {
  //   title: 'DX机台',
  //   width: 100,
  //   key: 'enamellingEquipmentCode'
  // },
  {
    title: '产品型号',
    align: 'center',
    width: 100,
    key: 'model'
  },

  {
    title: '产品规格',
    align: 'center',
    width: 100,
    key: 'specification'
  },
  {
    title: '重量(kg)',
    align: 'center',
    width: 100,
    key: 'weight'
  },
  {
    title: '已完成重量',
    align: 'center',
    width: 100,
    key: 'wightCompletion'
  },
  {
    title: '盘数',
    align: 'center',
    width: 100,
    key: 'discNumber'
  },
  {
    title: '开始时间',
    align: 'center',
    width: 180,
    key: 'startTime'
  },
  {
    title: '结束时间',
    align: 'center',
    width: 180,
    key: 'endTime'
  },
  {
    title: '技术要求',
    align: 'center',
    width: 100,
    key: 'artt6',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '备注',
    align: 'center',
    width: 100,
    key: 'remark'
  },
  {
    title: '状态',
    key: 'status',
    width: 80,
    fixed: 'right',
    render: row => {
      const statusInfo = getStatusName(row.status);
      return <n-tag type={statusInfo.bgColor}>{statusInfo.statusName}</n-tag>;
    }
  },
  {
    title: '操作',
    key: 'action',
    fixed: 'right',
    width: 140,
    render: row => {
      return (
        <n-space>
          {/* <n-button type="info" onClick={() => updateEquipmentCode(row)}>
            修改LS机台
          </n-button> */}
          <n-button type="info" v-show={row.status !== '3'} onClick={() => lssueChangeOrder(row)}>
            下发换规单
          </n-button>
          <n-button
            type="warning"
            v-show={row.status !== '2' && row.status !== '3'}
            onClick={() => updateEquipmentCode(row)}
          >
            修改LS机台
          </n-button>
          <n-button type="success" v-show={row.status === '1'} onClick={() => completeWorkOrder(row.id)}>
            工单完工
          </n-button>
        </n-space>
      );
    }
  }
]);

function submitUpdate() {
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      let equipmentCode = '';
      let equipmentId = '';
      formModelData.value.equipmentList.forEach(item => {
        const index = machineList.value.findIndex(ele => ele.value === item);
        equipmentCode += `${machineList.value[index].label},`;
        equipmentId += `${item},`;
      });
      rowDetail.value.equipmentCode = equipmentCode.slice(0, -1);
      rowDetail.value.equipmentId = equipmentId.slice(0, -1);
      editWiredrawingWorkorder(rowDetail.value).then(res => {
        if (res.code === 200) {
          message.success('修改成功');
          showDialog.value = false;
          init();
          rowDetail.value = null;
          formModelData.value.equipmentList = [];
        }
      });
    }
  });
}

function updateEquipmentCode(row: drawingWorkOrder.tabelList) {
  showDialog.value = true;
  rowDetail.value = JSON.parse(JSON.stringify(row));
  const equipmentList = rowDetail.value.equipmentId.split(',');
  formModelData.value.equipmentList = [];
  equipmentList.forEach(item => {
    formModelData.value.equipmentList.push(Number(item));
  });
}

const sleep = row =>
  new Promise(resolve => {
    const formValue = {
      model: row.model,
      spec: row.specification,
      quantity: row.discNumber,
      lsDeviceId: row.equipmentId,
      qbDeviceId: row.enamellingEquipmentId,
      startTime: row.startTime,
      endTime: row.endTime,
      remark: row.remark,
      orderDate: formatDate(new Date(), 'yyyy-MM-dd hh:mm:ss'),
      moldId: null,
      moldBeforSpec: '',
      moldAfterSpec: row.specification
    };
    addMoldGaugeList(formValue).then(res => {
      resolve(res);
    });
  });

function sleep2(id: number): any {
  return new Promise(resolve => {
    wiredrawingWorkorderComplete(id, '2').then(res => {
      resolve(res);
    });
  });
}

function lssueChangeOrder(row: drawingWorkOrder.tabelList): void {
  const d = dialog.warning({
    title: '提示',
    content: '确认要下发换规单吗?',
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        sleep(row).then((res: any) => {
          if (res.code === 200) {
            message.success('下发成功');
            init();
            resolve(res);
          }
        });
      });
    }
  });
}

function completeWorkOrder(id: number) {
  const d = dialog.warning({
    title: '提示',
    content: '确认要工单完工吗?',
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        sleep2(id).then((res: any) => {
          if (res.code === 200) {
            message.success('工单完工成功');
            init();
            resolve(res);
          }
        });
      });
    }
  });
}

function search() {
  if (searchForm.value.status !== undefined) {
    title.value = `拉丝工单-${workOrderStatus[searchForm.value.status].label}`;
  } else {
    title.value = `拉丝工单-全部`;
  }
  if (range.value !== null) {
    searchForm.value['params[beginTime]'] = formatDate(new Date(range.value[0]), 'yyyy-MM-dd');
    searchForm.value['params[endTime]'] = formatDate(new Date(range.value[1]), 'yyyy-MM-dd');
  } else {
    searchForm.value['params[beginTime]'] = null;
    searchForm.value['params[endTime]'] = null;
  }
  init();
}

function reset() {
  searchForm.value = {
    status: undefined,
    pageNum: 1,
    pageSize: 10,
    total: 0,
    equipmentId: null,
    orderType: null,
    model: '',
    wiredrawingCode: '',
    specification: '',
    enamellingEquipmentCode: '',
    equipmentCode: ''
  };
  if (searchForm.value.status !== undefined) {
    title.value = `拉丝工单-${workOrderStatus[searchForm.value.status].label}`;
  } else {
    title.value = `拉丝工单-全部`;
  }
  range.value = null;
  init();
}

function getList() {
  getWorkbenchWiredrawingList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach((item: { equipmentCode: any; id: any }) => {
        machineList.value.push({
          label: item.equipmentCode,
          value: item.id
        });
      });
    }
  });
}

function init() {
  startLoading();
  getWiredrawing({ ...searchForm.value }).then(res => {
    if (res.code === 200) {
      searchForm.value.total = res.total;
      data.value = res.rows;
    }
  });
  endLoading();
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
</style>
