<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form label-placement="top" class="flex gap-4">
        <n-form-item label="工序">
          <n-select
            v-model:value="searchForm.taskType"
            placeholder="请输入工序"
            class="w-160px"
            :options="processOptions"
            @update:value="
              () => {
                init();
              }
            "
          ></n-select>
        </n-form-item>
        <n-form-item label="机台">
          <n-input v-model:value="searchForm.workstationCode" type="text" placeholder="请输入机台" />
        </n-form-item>
        <n-form-item label="生产工单号">
          <n-input v-model:value="searchForm.taskSn" type="text" placeholder="请输入生产工单号" />
        </n-form-item>
        <!-- <n-form-item label="订单编号">
          <n-input v-model:value="searchForm.orderSn" type="text" placeholder="请输入订单编号" />
        </n-form-item> -->
        <n-form-item label="产品规格">
          <n-input v-model:value="searchForm.productSpecification" type="text" placeholder="请输入产品规格" />
        </n-form-item>
        <n-form-item label="投料规格">
          <n-input v-model:value="searchForm.itemSpecification" type="text" placeholder="请输入投料规格" />
        </n-form-item>

        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>

      <n-form label-placement="top" class="flex gap-4">
        <n-form-item label="时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item label="料号">
          <n-input v-model:value="searchForm.partNumber" type="text" placeholder="请输入料号" />
        </n-form-item>
        <n-form-item label="批号">
          <n-input v-model:value="searchForm.batchCode" type="text" placeholder="请输入批号" />
        </n-form-item>
      </n-form>
    </my-card>

    <my-card title="投料记录">
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
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type { Ref } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import { getItputLog } from '~/src/service/api/produre/feedRecords/index';
import { formatDate } from '~/src/utils/form/rule';
const { loading, startLoading, endLoading } = useLoading();
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
    label: '融锡'
  },
  {
    value: 7,
    label: '锡水'
  }
];

const searchForm = ref<feedRecords.searchForm>({
  workstationCode: '',
  taskType: null, // 工序
  taskSn: '', //
  pageNum: 1,
  pageSize: 10,
  total: 0,
  orderSn: '',
  productSpecification: '',
  itemSpecification: '',
  partNumber: '',
  batchCode: ''
});
const range = ref<[number, number] | null>(null);
const data = ref<feedRecords.TabelList[]>([]);

const columns: Ref<DataTableColumns<feedRecords.TabelList>> = ref([
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
          return '融锡';
        case '7':
          return '锡水';
        default:
          return '';
      }
    }
  },
  {
    title: '机台',
    key: 'workstationCode',
    align: 'center',
    width: 100
  },
  {
    title: '轴号',
    align: 'center',
    key: 'axleNumber',
    width: 100
  },
  {
    title: '轴位置数',
    align: 'center',
    key: 'alexPositionNumber',
    width: 100
  },
  {
    title: '订单类型',
    align: 'center',
    key: 'orderType',
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
    width: 100
  },
  {
    title: '生产工单号',
    align: 'center',
    key: 'taskSn',
    width: 100,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '二维码编号',
    align: 'center',
    key: 'inputSn',
    width: 180,
    ellipsis: {
      tooltip: true
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
    title: '厂家料号',
    align: 'center',
    key: 'partNumber',
    width: 120,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '投料重量',
    align: 'center',
    key: 'inputWeight',
    width: 120
  },
  {
    title: '入库批号',
    align: 'center',
    key: 'batchCode',
    width: 100,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '单位',
    align: 'center',
    key: 'measureName',
    width: 100
  },
  {
    title: '投料规格',
    align: 'center',
    key: 'itemSpecification',
    width: 100
  },
  {
    title: '投料时间',
    align: 'center',
    key: 'createTime',
    width: 180,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '操作人',
    align: 'center',
    key: 'createBy',
    width: 100
  }
]);

function search() {
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
    workstationCode: '',
    taskType: null, // 工序
    taskSn: '', //
    pageNum: 1,
    pageSize: 10,
    total: 0,
    orderSn: '',
    productSpecification: '',
    itemSpecification: '',
    partNumber: '',
    batchCode: ''
  };
  range.value = null;
  search();
}
async function init() {
  startLoading();
  getItputLog({ ...searchForm.value }).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
    endLoading();
  });
}

onMounted(() => {
  init();
});
</script>

<style scoped></style>
