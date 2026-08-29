<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="工单类型：">
          <n-select
            v-model:value="searchForm.workOrderType"
            placeholder="请选择工单类型"
            class="w-180px"
            :options="workOrderType"
            @update-value="changeWorkOrderStatus"
          ></n-select>
        </n-form-item>

        <n-form-item label="工单状态：">
          <div>
            <n-button
              v-for="item in workOrderStatusList"
              :key="item.value"
              :class="isActive === item.value ? 'active' : ''"
              @click="changeActive(item.value)"
            >
              {{ item.label }}
            </n-button>
          </div>
        </n-form-item>

        <n-form-item label="工序工单号">
          <n-input
            v-show="searchForm.workOrderType === 0"
            v-model:value="searchForm.wiredrawingCode"
            type="text"
            placeholder="请输入工序工单号"
          />
          <n-input
            v-show="searchForm.workOrderType === 1"
            v-model:value="searchForm.annealingCode"
            type="text"
            placeholder="请输入工序工单号"
          />
          <n-input
            v-show="searchForm.workOrderType === 2"
            v-model:value="searchForm.tinningCode"
            type="text"
            placeholder="请输入工序工单号"
          />
          <n-input
            v-show="searchForm.workOrderType === 3"
            v-model:value="searchForm.doublewireCode"
            type="text"
            placeholder="请输入工序工单号"
          />
          <n-input
            v-show="searchForm.workOrderType === 4"
            v-model:value="searchForm.strandedwireCode"
            type="text"
            placeholder="请输入工序工单号"
          />
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
      <n-form inline>
        <n-form-item label="时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item label="机台名称">
          <n-input v-model:value="searchForm.equipmentCode" type="text" placeholder="请输入机台名称" />
        </n-form-item>
      </n-form>
    </my-card>
    <my-card v-show="workOrderTypeValue === 0" :title="title">
      <template #right>
        <div>
          <CxColumns v-model:columns="lsColumns" size="small" />
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :columns="lsColumns"
        :data="lsData"
        :max-height="400"
        :scroll-x="2800"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>

    <my-card v-show="workOrderTypeValue === 1" :title="title">
      <template #right>
        <div>
          <CxColumns v-model:columns="thColumns" size="small" />
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :columns="thColumns"
        :data="thData"
        :max-height="400"
        :scroll-x="2800"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>

    <my-card v-show="workOrderTypeValue === 2" :title="title">
      <template #right>
        <div>
          <CxColumns v-model:columns="dxColumns" size="small" />
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :columns="dxColumns"
        :data="dxData"
        :max-height="400"
        :scroll-x="2800"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>

    <my-card v-show="workOrderTypeValue === 3" :title="title">
      <template #right>
        <div>
          <CxColumns v-model:columns="bsColumns" size="small" />
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :columns="bsColumns"
        :data="bsData"
        :max-height="400"
        :scroll-x="2800"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>

    <my-card v-show="workOrderTypeValue === 4" :title="title">
      <template #right>
        <div>
          <CxColumns v-model:columns="jxColumns" size="small" />
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :columns="jxColumns"
        :data="jxData"
        :max-height="400"
        :scroll-x="2800"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>
    <my-dialog v-model:show="showInputDialog" title="排产" width="800px" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <lsIndex :form-model="lsFormModel"></lsIndex>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'WorkOrderArrange'
});
import type { Ref } from 'vue';
import { ref, onMounted, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { useMessage, type DataTableColumns } from 'naive-ui';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import { getWiredrawingWorkorder } from '~/src/service/api/plan';
import {
  wiredrawingUpdateSort,
  annealingUpdateSort,
  tinningUpdateSort,
  doublewireUpdateSort,
  strandedwireUpdateSort,
  wiredrawingWorkorderCancel,
  tinningWorkorderCancel,
  annealingWorkorderCancel,
  doublewireWorkorderCancel,
  strandedwireWorkorderCancel
} from '~/src/service/api/produre/workOrderArrange/index';
import { getStatusName } from '~/src/utils/common/workOrder';
import { formatDate } from '~/src/utils/form/rule';
import { getWorkAnnealing } from '~/src/service/api/plan/planAnneal';
import { getWorkTinning } from '~/src/service/api/plan/paintJobOrder';
import { getWireWorkorder } from '~/src/service/api/plan/planFilament';
import { getStrandedwire } from '~/src/service/api/plan/planStrandedWire';
const { loading, startLoading, endLoading } = useLoading();
const title = ref<string>('拉丝工单-所有');
const router = useRouter();
// 工单状态
const workOrderStatusList = ref<{ label: string; value: number | undefined }[]>([
  { label: '所有', value: undefined },
  { label: '已排产', value: 0 },
  { label: '生产中', value: 1 },
  { label: '完工', value: 2 },
  { label: '已作废', value: 3 }
]);
// 工单类型
const workOrderType = [
  { label: '拉丝工单', value: 0 },
  { label: '退火工单', value: 1 },
  { label: '镀锡工单', value: 2 },
  { label: '并丝工单', value: 3 },
  { label: '绞线工单', value: 4 }
];

const searchForm = ref<workOrderArrange.searchForm>({
  status: undefined,
  pageNum: 1,
  pageSize: 10,
  total: 0,
  workOrderType: 0,
  wiredrawingCode: '',
  specification: '',
  equipmentCode: '',
  annealingCode: '',
  tinningCode: '',
  doublewireCode: '',
  strandedwireCode: ''
});

const isActive = ref<number | undefined>(undefined);

const workOrderTypeValue = ref<number>(0);
const range = ref<[number, number] | null>(null);
const showInputDialog = ref<boolean>(false);

const lsData = ref<workOrderArrange.lsTabelList[]>([]);

const lsFormModel = reactive<feedRecords.TabelList>({
  id: null,
  taskType: '',
  workstationName: '',
  orderType: '', // 订单类型
  orderSn: '',
  clientName: '',
  workorderSn: '',
  taskSn: '',
  productModel: '',
  productSpecification: '',
  inputWeight: null,
  measureName: '',
  createTime: '',
  createBy: '',
  partNumber: '',
  batchCode: '',
  itemSpecification: '',
  axleNumber: '',
  alexPositionNumber: ''
});

const message = useMessage();

const lsColumns: Ref<DataTableColumns<workOrderArrange.lsTabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '创建时间',
    align: 'center',
    width: 170,
    key: 'createTime'
  },
  {
    title: '工单类型',
    align: 'center',
    width: 100,
    key: '',
    render: () => {
      return <n-space>{'拉丝工单'}</n-space>;
    }
  },
  {
    title: '订单类型',
    align: 'center',
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
  //   title: '订单编号',
  //   width: 100,
  //   key: 'orderCode'
  // },
  // {
  //   title: '生产工单号',
  //   width: 100,
  //   key: 'workorderCode'
  // },
  {
    title: '工序工单',
    align: 'center',
    width: 100,
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
    title: '特殊要求',
    align: 'center',
    width: 100,
    key: 'remark'
  },
  {
    title: '状态',
    align: 'center',
    width: 300,
    key: 'status',
    render: row => {
      const statusInfo = getStatusName(row.status);
      return <n-tag type={statusInfo.bgColor}>{statusInfo.statusName}</n-tag>;
    }
  },
  {
    title: '操作',
    align: 'center',
    width: 140,
    key: 'action',
    fixed: 'right',
    render: row => {
      if (row.status === '3') return '已作废,不可操作';
      if (row.status === '0') {
        return (
          <n-space>
            <n-popconfirm onPositiveClick={() => priority(row.id, wiredrawingUpdateSort)}>
              {{
                default: () => '确认优先吗>',
                trigger: () => <n-button type="success">优先</n-button>
              }}
            </n-popconfirm>
            <n-popconfirm onPositiveClick={() => handleDeleteItemTypeLs(row.id, wiredrawingWorkorderCancel)}>
              {{
                default: () => '确认作废吗>',
                trigger: () => <n-button type="warning">作废</n-button>
              }}
            </n-popconfirm>
          </n-space>
        );
      }
      return (
        <n-space>
          <n-button
            type="info"
            v-show={row.status === '1' || row.status === '2'}
            onClick={() => {
              router.push({
                name: 'produce_feed',
                query: { id: row.id, taskType: 0, title: '拉丝' }
              });
            }}
          >
            投料记录
          </n-button>
          <n-button
            type="info"
            v-show={row.status === '1' || row.status === '2'}
            onClick={() => {
              router.push({
                name: 'produce_report',
                query: { id: row.id, taskType: 0, title: '拉丝' }
              });
            }}
          >
            报工记录
          </n-button>
        </n-space>
      );
    }
  }
]);

const thData = ref<workOrderArrange.thTabelList[]>([]);

const thColumns: Ref<DataTableColumns<workOrderArrange.thTabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '创建时间',
    align: 'center',
    width: 170,
    key: 'createTime'
  },
  {
    title: '工单类型',
    align: 'center',
    width: 100,
    key: '',
    render: () => {
      return <n-space>{'退火工单'}</n-space>;
    }
  },
  {
    title: '订单类型',
    align: 'center',
    width: 100,
    key: 'orderType',
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
  //   title: '订单编号',
  //   width: 100,
  //   key: 'orderCode'
  // },
  // {
  //   title: '生产工单号',
  //   width: 100,
  //   key: 'workorderCode'
  // },
  {
    title: '工序工单',
    align: 'center',
    width: 100,
    key: 'annealingCode'
  },
  {
    title: '退火机台',
    align: 'center',
    width: 100,
    key: 'equipmentCode'
  },
  {
    title: '位置',
    align: 'center',
    width: 100,
    key: 'axleNumber'
  },
  {
    title: '线盘',
    width: 100,
    align: 'center',
    key: 'wireDisc'
  },
  // {
  //   title: '油漆',
  //   width: 100,
  //   key: 'plantName'
  // },
  {
    title: '型号',
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
    title: '特殊要求',
    width: 100,
    align: 'center',
    key: 'remark'
  },
  {
    title: '状态',
    align: 'center',
    width: 300,
    key: 'status',
    fixed: 'right',
    render: row => {
      const statusInfo = getStatusName(row.status);
      return <n-tag type={statusInfo.bgColor}>{statusInfo.statusName}</n-tag>;
    }
  },
  {
    title: '操作',
    align: 'center',
    width: 140,
    key: 'action',
    fixed: 'right',
    render: row => {
      if (row.status === '3') return '已作废,不可操作';
      if (row.status === '0') {
        return (
          <n-space>
            <n-popconfirm onPositiveClick={() => priority(row.id, annealingUpdateSort)}>
              {{
                default: () => '确认优先吗>',
                trigger: () => <n-button type="success">优先</n-button>
              }}
            </n-popconfirm>
            <n-popconfirm onPositiveClick={() => handleDeleteItemTypeLs(row.id, annealingWorkorderCancel)}>
              {{
                default: () => '确认作废吗>',
                trigger: () => <n-button type="warning">作废</n-button>
              }}
            </n-popconfirm>
          </n-space>
        );
      }
      return (
        <n-space>
          <n-button
            type="info"
            v-show={row.status === '1' || row.status === '2'}
            onClick={() => {
              router.push({
                name: 'produce_feed',
                query: { id: row.id, taskType: 1, title: '退火' }
              });
            }}
          >
            投料记录
          </n-button>
          <n-button
            type="info"
            v-show={row.status === '1' || row.status === '2'}
            onClick={() => {
              router.push({
                name: 'produce_report',
                query: { id: row.id, taskType: 1, title: '退火' }
              });
            }}
          >
            报工记录
          </n-button>
        </n-space>
      );
    }
  }
]);

const dxData = ref<workOrderArrange.dxTabelList[]>([]);

const dxColumns: Ref<DataTableColumns<workOrderArrange.dxTabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '创建时间',
    align: 'center',
    width: 170,
    key: 'createTime'
  },
  {
    title: '工单类型',
    align: 'center',
    width: 100,
    key: '',
    render: () => {
      return <n-space>{'镀锡工单'}</n-space>;
    }
  },
  {
    title: '订单类型',
    align: 'center',
    width: 100,
    key: 'orderType',
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
  //   title: '订单编号',
  //   width: 100,
  //   key: 'orderCode'
  // },
  // {
  //   title: '生产工单号',
  //   width: 100,
  //   key: 'workorderCode'
  // },
  {
    title: '工序工单',
    align: 'center',
    width: 100,
    key: 'tinningCode'
  },
  {
    title: '镀锡机台',
    align: 'center',
    width: 100,
    key: 'equipmentCode'
  },
  {
    title: '位置',
    align: 'center',
    width: 100,
    key: 'axleNumber'
  },
  {
    title: '线盘',
    width: 100,
    align: 'center',
    key: 'wireDisc'
  },
  // {
  //   title: '油漆',
  //   width: 100,
  //   key: 'plantName'
  // },
  {
    title: '型号',
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
    title: '特殊要求',
    width: 100,
    align: 'center',
    key: 'remark'
  },
  {
    title: '状态',
    align: 'center',
    width: 300,
    key: 'status',
    fixed: 'right',
    render: row => {
      const statusInfo = getStatusName(row.status);
      return <n-tag type={statusInfo.bgColor}>{statusInfo.statusName}</n-tag>;
    }
  },
  {
    title: '操作',
    align: 'center',
    width: 140,
    key: 'action',
    fixed: 'right',
    render: row => {
      if (row.status === '3') return '已作废,不可操作';
      if (row.status === '0') {
        return (
          <n-space>
            <n-popconfirm onPositiveClick={() => priority(row.id, tinningUpdateSort)}>
              {{
                default: () => '确认优先吗>',
                trigger: () => <n-button type="success">优先</n-button>
              }}
            </n-popconfirm>
            <n-popconfirm onPositiveClick={() => handleDeleteItemTypeLs(row.id, tinningWorkorderCancel)}>
              {{
                default: () => '确认作废吗>',
                trigger: () => <n-button type="warning">作废</n-button>
              }}
            </n-popconfirm>
          </n-space>
        );
      }
      return (
        <n-space>
          <n-button
            type="info"
            v-show={row.status === '1' || row.status === '2'}
            onClick={() => {
              router.push({
                name: 'produce_feed',
                query: { id: row.id, taskType: 2, title: '镀锡' }
              });
            }}
          >
            投料记录
          </n-button>
          <n-button
            type="info"
            v-show={row.status === '1' || row.status === '2'}
            onClick={() => {
              router.push({
                name: 'produce_report',
                query: { id: row.id, taskType: 2, title: '镀锡' }
              });
            }}
          >
            报工记录
          </n-button>
        </n-space>
      );
    }
  }
]);

const bsData = ref<workOrderArrange.bsTabelList[]>([]);

const bsColumns: Ref<DataTableColumns<workOrderArrange.bsTabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '创建时间',
    align: 'center',
    width: 170,
    key: 'createTime'
  },
  {
    title: '工单类型',
    align: 'center',
    width: 100,
    key: '',
    render: () => {
      return <n-space>{'并丝工单'}</n-space>;
    }
  },
  {
    title: '订单类型',
    align: 'center',
    width: 100,
    key: 'orderType',
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
  //   title: '订单编号',
  //   width: 100,
  //   key: 'orderCode'
  // },
  // {
  //   title: '生产工单号',
  //   width: 100,
  //   key: 'workorderCode'
  // },
  {
    title: '工序工单',
    align: 'center',
    width: 100,
    key: 'doublewireCode'
  },
  {
    title: '并丝机台',
    width: 100,
    align: 'center',
    key: 'equipmentCode'
  },
  {
    title: '位置',
    align: 'center',
    width: 100,
    key: 'axleNumber'
  },
  {
    title: '线盘',
    align: 'center',
    width: 100,
    key: 'wireDisc'
  },
  // {
  //   title: '油漆',
  //   width: 100,
  //   key: 'plantName'
  // },
  {
    title: '型号',
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
    title: '盘数',
    align: 'center',
    width: 100,
    key: 'discNumber'
  },
  {
    title: '开始时间',
    width: 180,
    align: 'center',
    key: 'startTime'
  },
  {
    title: '结束时间',
    align: 'center',
    width: 180,
    key: 'endTime'
  },
  {
    title: '特殊要求',
    align: 'center',
    width: 100,
    key: 'remark'
  },
  {
    title: '状态',
    align: 'center',
    width: 300,
    key: 'status',
    fixed: 'right',
    render: row => {
      const statusInfo = getStatusName(row.status);
      return <n-tag type={statusInfo.bgColor}>{statusInfo.statusName}</n-tag>;
    }
  },
  {
    title: '操作',
    align: 'center',
    width: 140,
    key: 'action',
    fixed: 'right',
    render: row => {
      if (row.status === '3') return '已作废,不可操作';
      if (row.status === '0') {
        return (
          <n-space>
            <n-popconfirm onPositiveClick={() => priority(row.id, doublewireUpdateSort)}>
              {{
                default: () => '确认优先吗>',
                trigger: () => <n-button type="success">优先</n-button>
              }}
            </n-popconfirm>
            <n-popconfirm onPositiveClick={() => handleDeleteItemTypeLs(row.id, doublewireWorkorderCancel)}>
              {{
                default: () => '确认作废吗>',
                trigger: () => <n-button type="warning">作废</n-button>
              }}
            </n-popconfirm>
          </n-space>
        );
      }
      return (
        <n-space>
          <n-button
            type="info"
            v-show={row.status === '1' || row.status === '2'}
            onClick={() => {
              router.push({
                name: 'produce_feed',
                query: { id: row.id, taskType: 3, title: '并丝' }
              });
            }}
          >
            投料记录
          </n-button>
          <n-button
            type="info"
            v-show={row.status === '1' || row.status === '2'}
            onClick={() => {
              router.push({
                name: 'produce_report',
                query: { id: row.id, taskType: 3, title: '并丝' }
              });
            }}
          >
            报工记录
          </n-button>
        </n-space>
      );
    }
  }
]);

const jxData = ref<workOrderArrange.jxTabelList[]>([]);

const jxColumns: Ref<DataTableColumns<workOrderArrange.jxTabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '创建时间',
    align: 'center',
    width: 170,
    key: 'createTime'
  },
  {
    title: '工单类型',
    width: 100,
    align: 'center',
    key: '',
    render: () => {
      return <n-space>{'绞线工单'}</n-space>;
    }
  },
  {
    title: '订单类型',
    width: 100,
    align: 'center',
    key: 'orderType',
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
  //   title: '订单编号',
  //   width: 100,
  //   key: 'orderCode'
  // },
  // {
  //   title: '生产工单号',
  //   width: 100,
  //   key: 'workorderCode'
  // },
  {
    title: '工序工单',
    align: 'center',
    width: 100,
    key: 'strandedwireCode'
  },
  {
    title: '绞线机台',
    align: 'center',
    width: 100,
    key: 'equipmentCode'
  },
  {
    title: '位置',
    align: 'center',
    width: 100,
    key: 'axleNumber'
  },
  {
    title: '线盘',
    align: 'center',
    width: 100,
    key: 'wireDisc'
  },
  // {
  //   title: '油漆',
  //   width: 100,
  //   key: 'plantName'
  // },
  {
    title: '型号',
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
    title: '特殊要求',
    align: 'center',
    width: 100,
    key: 'remark'
  },
  {
    title: '状态',
    align: 'center',
    width: 300,
    key: 'status',
    fixed: 'right',
    render: row => {
      const statusInfo = getStatusName(row.status);
      return <n-tag type={statusInfo.bgColor}>{statusInfo.statusName}</n-tag>;
    }
  },
  {
    title: '操作',
    align: 'center',
    width: 140,
    key: 'action',
    fixed: 'right',
    render: row => {
      if (row.status === '3') return '已作废,不可操作';
      if (row.status === '0') {
        return (
          <n-space>
            <n-popconfirm onPositiveClick={() => priority(row.id, strandedwireUpdateSort)}>
              {{
                default: () => '确认优先吗>',
                trigger: () => <n-button type="success">优先</n-button>
              }}
            </n-popconfirm>
            <n-popconfirm onPositiveClick={() => handleDeleteItemTypeLs(row.id, strandedwireWorkorderCancel)}>
              {{
                default: () => '确认作废吗>',
                trigger: () => <n-button type="warning">作废</n-button>
              }}
            </n-popconfirm>
          </n-space>
        );
      }
      return (
        <n-space>
          <n-button
            type="info"
            v-show={row.status === '1' || row.status === '2'}
            onClick={() => {
              router.push({
                name: 'produce_feed',
                query: { id: row.id, taskType: 4, title: '绞线' }
              });
            }}
          >
            投料记录
          </n-button>
          <n-button
            type="info"
            v-show={row.status === '1' || row.status === '2'}
            onClick={() => {
              router.push({
                name: 'produce_report',
                query: { id: row.id, taskType: 4, title: '绞线' }
              });
            }}
          >
            报工记录
          </n-button>
        </n-space>
      );
    }
  }
]);

function search(): void {
  workOrderTypeValue.value = searchForm.value.workOrderType;
  if (searchForm.value.status !== undefined) {
    title.value = `${workOrderType[searchForm.value.workOrderType].label}-${
      workOrderStatusList.value[searchForm.value.status].label
    }`;
  } else {
    title.value = `${workOrderType[searchForm.value.workOrderType].label}-全部`;
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
function reset(): void {
  searchForm.value = {
    status: undefined,
    pageNum: 1,
    pageSize: 10,
    total: 0,
    workOrderType: 0,
    wiredrawingCode: '',
    specification: '',
    equipmentCode: '',
    annealingCode: '',
    tinningCode: '',
    doublewireCode: '',
    strandedwireCode: ''
  };
  isActive.value = undefined;
  range.value = null;
  search();
}

function cancel() {
  showInputDialog.value = false;
}

function submit() {
  showInputDialog.value = false;
}

function changeWorkOrderStatus(val): void {
  if (searchForm.value.workOrderType === val) return;
  searchForm.value.workOrderType = val;
  search();
}

async function priority(id: number, func: any) {
  const res = await func(id);
  if (res.code === 200) {
    message.success('优先成功');
    init();
  }
}

function handleDeleteItemTypeLs(id: number, func: any) {
  func(id).then(res => {
    if (res.code === 200) {
      message.success('作废成功');
      init();
    }
  });
}

function changeActive(num: number | undefined): void {
  isActive.value = num;
  searchForm.value.status = num;
  search();
}

function init(): void {
  startLoading();
  switch (searchForm.value.workOrderType) {
    case 0:
      getWiredrawingWorkorder({ ...searchForm.value }).then(res => {
        if (res.code === 200) {
          searchForm.value.total = res.total;
          lsData.value = res.rows;
        }
        endLoading();
      });
      break;
    case 1:
      getWorkAnnealing({ ...searchForm.value }).then(res => {
        if (res.code === 200) {
          searchForm.value.total = res.total;
          thData.value = res.rows;
        }
        endLoading();
      });
      break;
    case 2:
      getWorkTinning({ ...searchForm.value }).then(res => {
        if (res.code === 200) {
          searchForm.value.total = res.total;
          dxData.value = res.rows;
        }
        endLoading();
      });
      break;
    case 3:
      getWireWorkorder({ ...searchForm.value }).then(res => {
        if (res.code === 200) {
          searchForm.value.total = res.total;
          bsData.value = res.rows;
        }
        endLoading();
      });
      break;
    case 4:
      getStrandedwire({ ...searchForm.value }).then(res => {
        if (res.code === 200) {
          searchForm.value.total = res.total;
          jxData.value = res.rows;
        }
        endLoading();
      });
      break;
    default:
      break;
  }
}

onMounted(() => {
  init();
});
</script>

<style scoped>
.active {
  background-color: blue !important;
  color: #fff !important;
}
</style>
