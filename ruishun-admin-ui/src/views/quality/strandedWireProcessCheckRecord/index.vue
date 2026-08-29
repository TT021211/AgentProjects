<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="生产工单">
          <n-input
            v-model:value="searchForm.generateWorkOrderNumber"
            :style="{ width: formItemWidth }"
            type="text"
            placeholder="请输入生产工单"
          />
        </n-form-item>
        <n-form-item label="订单编号">
          <n-input
            v-model:value="searchForm.orderNumber"
            type="text"
            :style="{ width: formItemWidth }"
            placeholder="请输入订单编号"
          />
        </n-form-item>
        <n-form-item label="是否合格">
          <n-select
            v-model:value="searchForm.qualifiedOrNot"
            placeholder="请选择是否合格"
            :options="qualifiedOrNotList"
            :style="{ width: formItemWidth }"
          ></n-select>
        </n-form-item>
        <n-form-item label="机台">
          <n-select
            v-model:value="machineId"
            placeholder="请选择机台"
            :options="machineList"
            :style="{ width: formItemWidth }"
            @update-value="(val,item : any) => {
              searchForm.deviceName = item.label
            }"
          ></n-select>
        </n-form-item>
        <n-form-item label="检验类型">
          <n-select
            v-model:value="searchForm.inspectionType"
            placeholder="请选择检验类型"
            :options="inspectionList"
            :style="{ width: formItemWidth }"
          ></n-select>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="绞线过程检列表">
      <template #right>
        <div>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :columns="columns"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
        :data="data"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { dataTableConfig } from '@/config/dataTableConfig';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { getSummartRecordList } from '~/src/service/api/quality/drawingProcessCheckRecord/index';
import { getWorkbenchEnamellingList } from '~/src/service/api/md/workbench/workStrandedWire';
const searchForm = ref<drawingProcessCheckRecord.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  taskType: '4',
  inspectionType: null,
  generateWorkOrderNumber: '',
  orderNumber: '',
  qualifiedOrNot: null,
  deviceName: null
});
const { loading, startLoading, endLoading } = useLoading();
const formItemWidth = ref<string>('180px');
const machineList = ref<{ label: string; value: number }[]>([]);
const inspectionList = ref([
  {
    value: '0',
    label: '首检'
  },
  {
    value: '1',
    label: '尾检'
  }
]);
const qualifiedOrNotList = ref<{ label: string; value: string }[]>([
  { label: '合格', value: '0' },
  { label: '不合格', value: '1' }
]);
const machineId = ref<number | null>(null);
const data = ref<drawingProcessCheckRecord.TableList[]>([]);

const columns: Ref<DataTableColumns<drawingProcessCheckRecord.TableList>> = ref([
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
    render: () => {
      return '绞线';
      //   switch (row.taskType) {
      //     case '0':
      //       return '拉丝';
      //     case '1':
      //       return '退火';
      //     case '2':
      //       return '镀锡';
      //     case '3':
      //       return '并丝';
      //     default:
      //       return '绞线';
      //   }
    }
  },
  {
    title: '线径数据',
    key: 'wireDiameterData',
    align: 'center',
    width: 100
  },
  {
    title: '机台',
    key: 'machineTable',
    align: 'center',
    width: 100
  },
  {
    title: '轴号',
    key: 'machinePosition',
    align: 'center',
    width: 80
  },
  {
    title: '位置',
    key: 'axisNumber',
    align: 'center',
    width: 80
  },
  {
    title: '订单类型',
    key: 'orderType',
    width: 100,
    render: row => {
      return row.orderType === '0' ? '客户订单' : '备库订单';
    }
  },
  {
    title: '订单编号',
    key: 'saleOrderCode',
    align: 'center',
    width: 200
  },
  // {
  //   title: '客户',
  //   key: 'clientName',
  //   align: 'center',
  //   width: 100
  // },
  {
    title: '生产工单号',
    key: 'workorderCode',
    align: 'center',
    width: 180
  },
  {
    title: '产品规格',
    key: 'productSpecification',
    align: 'center',
    width: 100
  },
  {
    title: '重量',
    key: 'weight',
    align: 'center',
    width: 100
  },
  {
    title: '单位',
    key: 'measureName',
    align: 'center',
    width: 100
  },
  {
    title: '批号',
    key: 'batchNumber',
    align: 'center',
    width: 120,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '日期',
    key: 'createTime',
    align: 'center',
    width: 180
  },
  {
    title: '质检人',
    key: 'createBy',
    align: 'center',
    width: 100
  },
  {
    title: '是否合格',
    key: 'status',
    align: 'center',
    width: 100,
    render: row => (
      <n-tag type={row.status === '0' ? 'success' : 'error'}>{row.status === '0' ? '合格' : '不合格'}</n-tag>
    )
  },
  {
    title: '缺陷项',
    key: 'defectName',
    align: 'center',
    width: 180
  }
]);

function search() {
  init();
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    taskType: '4',
    inspectionType: null,
    generateWorkOrderNumber: '',
    orderNumber: '',
    qualifiedOrNot: null,
    deviceName: null
  };
  init();
}

function getList() {
  getWorkbenchEnamellingList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach((item: any) => {
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
  getSummartRecordList(searchForm.value).then(res => {
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

<style scoped>
:deep(.n-space) {
  justify-content: center !important;
}

:deep(.n-data-table .n-data-table-th .n-data-table-th__title-wrapper) {
  text-align: center;
}

:deep(.n-data-table-td) {
  text-align: center;
}
</style>
