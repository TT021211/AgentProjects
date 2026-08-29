<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form label-placement="top" class="flex gap-2">
        <n-form-item label="工单类型">
          <n-select
            v-model:value="searchForm.taskType"
            placeholder="请选择工单类型"
            class="w-160px"
            :options="processOptions"
            :style="{ width: formItemWidth }"
            @update:value="selectTaskType"
          ></n-select>
        </n-form-item>
        <n-form-item label="生产工单号">
          <n-input
            v-model:value="searchForm.taskSn"
            type="text"
            placeholder="生产工单号"
            :style="{ width: formItemWidth }"
          />
        </n-form-item>
        <n-form-item label="机台">
          <n-select
            v-model:value="searchForm.workstationId"
            placeholder="请选择机台"
            :options="machineList"
            class="w-160px"
            :style="{ width: formItemWidth }"
          ></n-select>
        </n-form-item>
        <!-- <n-form-item label="订单编号">
          <n-input
            v-model:value="searchForm.orderSn"
            type="text"
            placeholder="请输入订单编号"
            :style="{ width: formItemWidth }"
          />
        </n-form-item> -->
        <n-form-item label="时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>

      <n-form inline>
        <n-form-item label="批号">
          <n-input
            v-model:value="searchForm.batchCode"
            placeholder="请输入批号"
            :style="{ width: formItemWidth }"
          ></n-input>
        </n-form-item>
        <n-form-item label="贸易商">
          <n-select
            v-model:value="ingredientsId"
            placeholder="请选择贸易商"
            :filterable="true"
            :options="merchantList"
            :style="{ width: formItemWidth }"
            @update-value="
              (val, item: any) => {
                searchForm.ingredientsName = item.label;
              }
            "
          ></n-select>
        </n-form-item>
        <n-form-item label="规格">
          <n-select
            v-model:value="itemId"
            placeholder="选择规格"
            :filterable="true"
            :options="specificationList"
            :style="{ width: formItemWidth }"
            @update-value="
              (val, item: any) => {
                searchForm.itemSpecification = item.label;
              }
            "
          ></n-select>
        </n-form-item>
        <n-form-item label="材料厂家">
          <n-select
            v-model:value="manufacturerId"
            placeholder="选择材料厂家"
            :filterable="true"
            :options="manufacturerList"
            @update-value="
              (val, item: any) => {
                searchForm.manufacturerName = item.label;
              }
            "
          ></n-select>
        </n-form-item>
      </n-form>
    </my-card>

    <my-card title="报工记录">
      <template #right>
        <div style="display: flex">
          <n-button type="info" size="small" style="margin-right: 10px" @click="lookReport">查看报表</n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :max-height="400"
        :scroll-x="2200"
        :loading="loading"
        :columns="columns"
        :data="data"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import { ref, onMounted } from 'vue';
import type { Ref } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { getMaterialList } from '@/service/api/md/itemlist/index';
import { getIngredientsTradersList } from '@/service/api/md/ingredients/traders';
import { getIngredientsManufacturerList } from '@/service/api/md/ingredients/manufacturer';
import { serviceEnv } from '~/.env-config';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import { getOutputLog } from '~/src/service/api/produre/workRecords/index';
import { formatDate } from '~/src/utils/form/rule'; // 拉丝
import { getWorkbenchWiredrawingList } from '~/src/service/api/md/workbench/wiredrawing';
// 镀锡
import { getWorkbenchEnamellingList } from '~/src/service/api/md/workbench/enamelling';
// 退火
import { getWorkbenchEnamellingList as getWorkAnneal } from '~/src/service/api/md/workbench/workAnneal';
// 并丝
import { getWorkbenchEnamellingList as getWorkFilament } from '~/src/service/api/md/workbench/workFilament';
// 绞线
import { getWorkbenchEnamellingList as getWorkStrandedWire } from '~/src/service/api/md/workbench/workStrandedWire';

const { loading, startLoading, endLoading } = useLoading();

const searchForm = ref<workRecords.searchForm>({
  taskSn: '', // 生产工单号
  workstationId: null, // 机台
  taskType: null, // 工单类型
  orderSn: '', // 订单编号
  pageNum: 1,
  pageSize: 10,
  total: 0,
  batchCode: '',
  ingredientsName: '',
  manufacturerName: '',
  itemSpecification: ''
});

const ingredientsId = ref<number | null>(null);
const manufacturerId = ref<number | null>(null);
const itemId = ref<number | null>(null);

const processOptions = [
  {
    value: undefined,
    label: '所有'
  },
  {
    value: 0,
    label: '拉丝'
  },
  {
    value: 1,
    label: '退火'
  },
  {
    value: 2,
    label: '镀锡'
  },
  {
    value: 3,
    label: '并丝'
  },
  {
    value: 4,
    label: '绞线'
  },
  {
    value: 6,
    label: '锡水'
  }
];

const machineList = ref<{ label: string; value: number }[]>([]);

const wiredrawingList = ref<{ label: string; value: number }[]>([]);
const range = ref<[number, number] | null>(null);
const specificationList = ref<{ label: string; value: number }[]>([]);
const merchantList = ref<{ label: string; value: number }[]>([]);
const manufacturerList = ref<{ label: string; value: number }[]>([]);
const isGetWiredrawingList = ref<boolean>(false);

const formItemWidth = ref<string>('200px');
const data = ref<workRecords.TabelList[]>([]);

const columns: Ref<DataTableColumns<workRecords.TabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '工单类型',
    key: 'taskType',
    align: 'center',
    width: 100,
    render: row => {
      switch (row.taskType) {
        case '0':
          return '拉丝';
        case '1':
          return '退火';
        case '2':
          return '镀锡';
        case '3':
          return '并丝';
        case '4':
          return '绞线';
        case '6':
          return '锡水';
        default:
          return '';
      }
    }
  },
  {
    title: '机台',
    align: 'center',
    key: 'workstationCode',
    width: 100
  },
  {
    title: '轴位置数',
    align: 'center',
    key: 'alexPositionNumber',
    width: 100
  },
  {
    title: '位置',
    key: 'alexNumber',
    width: 100
  },
  {
    title: '批次',
    align: 'center',
    key: 'batchCode',
    width: 140
  },
  {
    title: '订单类型',
    key: 'orderType',
    align: 'center',
    width: 100,
    render: row => {
      return row.orderType === '0' ? '客户订单' : '备库订单';
    }
  },
  // {
  //   title: '订单编号',
  //   key: 'orderSn',
  //   width: 100
  // },
  {
    title: '客户',
    align: 'center',
    key: 'clientName',
    width: 180
  },
  {
    title: '生产工单号',
    key: 'workorderSn',
    align: 'center',
    width: 180,
    render: row => {
      return (
        <n-space>
          <n-ellipsis line-clamp="1">{row.workorderSn}</n-ellipsis>
        </n-space>
      );
    }
  },
  {
    title: '二维码编号',
    key: 'outputSn',
    align: 'center',
    width: 180,
    render: row => {
      return (
        <n-space>
          <n-ellipsis line-clamp="1">{row.outputSn}</n-ellipsis>
        </n-space>
      );
    }
  },
  {
    title: '产品型号',
    align: 'center',
    key: 'productModel',
    width: 100
  },
  {
    title: '产品规格',
    align: 'center',
    key: 'productSpecification',
    width: 100
  },
  {
    title: '报工重量',
    align: 'center',
    key: 'outputWeight',
    width: 100
  },
  {
    title: '报工规格',
    align: 'center',
    key: 'itemSpecification',
    width: 100
  },
  {
    title: '单位',
    align: 'center',
    key: 'measureName',
    width: 100
  },
  {
    title: '报工时间',
    align: 'center',
    key: 'createTime',
    width: 180,
    render: row => {
      return (
        <n-space>
          <n-ellipsis line-clamp="1">{row.createTime}</n-ellipsis>
        </n-space>
      );
    }
  },
  {
    title: '报工人',
    align: 'center',
    key: 'createBy',
    width: 100
  },
  {
    title: '报工工号',
    align: 'center',
    key: 'jobNumber',
    width: 100
  }
]);

function search(): void {
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
    taskSn: '', // 生产工单号
    workstationId: null, // 机台
    taskType: null, // 工单类型
    orderSn: '', // 订单编号
    pageNum: 1,
    pageSize: 10,
    total: 0,
    batchCode: '',
    ingredientsName: '',
    manufacturerName: '',
    itemSpecification: ''
  };

  ingredientsId.value = null;
  manufacturerId.value = null;
  itemId.value = null;
  range.value = null;
  search();
}
function selCraftMachine(type) {
  machineList.value = [];
  switch (type) {
    case 0:
      getWorkbenchWiredrawingList({ pageSize: 999 }).then(res => {
        res.rows.forEach((item: { equipmentCode: any; id: any }) => {
          machineList.value.push({
            label: item.equipmentCode,
            value: item.id
          });
        });
      });
      break;
    case 1:
      getWorkAnneal({ pageSize: 999 }).then(res => {
        res.rows.forEach((item: { equipmentCode: any; id: any }) => {
          machineList.value.push({
            label: item.equipmentCode,
            value: item.id
          });
        });
      });
      break;
    case 2:
      getWorkbenchEnamellingList({ pageSize: 999 }).then(res => {
        res.rows.forEach((item: { equipmentCode: any; id: any }) => {
          machineList.value.push({
            label: item.equipmentCode,
            value: item.id
          });
        });
      });
      break;
    case 3:
      getWorkFilament({ pageSize: 999 }).then(res => {
        res.rows.forEach((item: { equipmentCode: any; id: any }) => {
          machineList.value.push({
            label: item.equipmentCode,
            value: item.id
          });
        });
      });
      break;
    case 4:
      getWorkStrandedWire({ pageSize: 999 }).then(res => {
        res.rows.forEach((item: { equipmentCode: any; id: any }) => {
          machineList.value.push({
            label: item.equipmentCode,
            value: item.id
          });
        });
      });
      break;
    default:
      break;
  }
}
function selectTaskType(num: number) {
  search();
  searchForm.value.workstationId = null;
  selCraftMachine(num);
}
function lookReport() {
  let url = `${serviceEnv.prod.reportServerUrl}/jmreport/view/989752370909216768`;
  let searchValue = '';
  let isAddQuestionMask = false;
  const filterData = ['pageNum', 'pageSize', 'total', 'params[beginTime]', 'params[endTime]'];
  for (const key in searchForm.value) {
    if (searchForm.value[key] && !filterData.includes(key)) {
      if (!isAddQuestionMask) {
        searchValue += `?${key}=${searchForm.value[key]}`;
        isAddQuestionMask = true;
      } else {
        searchValue += `&${key}=${searchForm.value[key]}`;
      }
    }
  }
  if (range.value !== null) {
    const beginTime = formatDate(new Date(range.value[0]), 'yyyy-MM-dd');
    const endTime = formatDate(new Date(range.value[1]), 'yyyy-MM-dd');
    if (isAddQuestionMask) {
      searchValue += `&beginTime=${beginTime}&endTime=${endTime}`;
    } else {
      searchValue = `?beginTime=${beginTime}&endTime=${endTime}`;
    }
  }
  url += searchValue;
  window.open(url, '_blank');
}

function getList() {
  getMaterialList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        if (item.workstageName === ' ') {
          specificationList.value.push({ label: item.specification, value: item.id });
        }
      });
    }
  });

  getIngredientsTradersList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        merchantList.value.push({ label: item.tradersName, value: item.id });
      });
    }
  });

  getIngredientsManufacturerList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        manufacturerList.value.push({ label: item.manufacturerName, value: item.id, ...item });
      });
    }
  });
}

function init() {
  startLoading();
  getOutputLog({ ...searchForm.value }).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
    endLoading();
  });
  if (isGetWiredrawingList.value) return;
  getWorkbenchWiredrawingList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      isGetWiredrawingList.value = true;
      res.rows.forEach((item: { equipmentCode: any; id: any }) => {
        wiredrawingList.value.push({
          label: item.equipmentCode,
          value: item.id
        });
      });
      machineList.value = wiredrawingList.value;
    }
  });
}
onMounted(() => {
  init();
  getList();
});
</script>

<style scoped></style>
