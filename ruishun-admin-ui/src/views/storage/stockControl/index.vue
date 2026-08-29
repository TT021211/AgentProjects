<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="所在仓库">
          <n-select
            v-model:value="searchForm.warehouseId"
            :options="opetions"
            class="w-180px"
            placeholder="请选择仓库"
          ></n-select>
        </n-form-item>
        <n-form-item label="物料编码">
          <n-input v-model:value="searchForm.itemCode" placeholder="请输入物料编码"></n-input>
        </n-form-item>
        <n-form-item label="二维码编码">
          <n-input v-model:value="searchForm.itemIdentifier" placeholder="请输入二维码编码"></n-input>
        </n-form-item>
        <n-form-item label="批号">
          <n-input v-model:value="searchForm.batchCode" placeholder="请输入批号"></n-input>
        </n-form-item>
        <n-form-item label="规格">
          <n-input v-model:value="searchForm.specification" placeholder="请输入规格"></n-input>
        </n-form-item>
        <n-form-item>
          <n-button class="mr-5px" type="primary" @click="handleSearch">
            <icon-tdesign-search class="mr-2px" />
            搜索
          </n-button>
          <n-button @click="handleReset">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
      <n-form inline>
        <n-form-item label="报表类型">
          <n-select
            v-model:value="time"
            clearable
            :options="[
              { label: '日报表', value: 0 },
              { label: '月报表', value: 1 },
              { label: '年报表', value: 2 }
            ]"
            class="w-160px"
            placeholder="请选择报表类型"
          ></n-select>
        </n-form-item>
        <n-form-item label="时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="库存列表">
      <template #right>
        <div style="display: flex">
          <n-button type="info" size="small" style="margin-right: 10px" @click="lookReport">查看报表</n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
        <!-- <n-button type="primary" :disabled="checkedRowKeys.length <= 0" @click="dialogFlag = true">
            <icon-tdesign-edit />
            报废申请
          </n-button> -->
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <!-- <my-dialog title="报废申请" :show="dialogFlag" @cancel="handleCancel" @submit="handleSubmit">
      <template #content>
        <div>
          <n-form label-placement="left" label-width="70px" label-align="left">
            <n-grid :cols="2" :x-gap="18">
              <n-form-item-grid-item :span="2" label="模具编号">
                <n-input placeholder="请输入模具编号" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="尺寸">
                <n-input disabled />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="公差">
                <n-input disabled />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="2" label="报废原因">
                <n-input type="textarea" placeholder="请输入模具编号" />
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog> -->
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { dataTableConfig } from '@/config/dataTableConfig';
import { getStorageManageList } from '@/service/api/wms/stockControl';
import { getlist } from '@/service/api/wms/warehouseSet';
import { useLoading } from '~/src/hooks';
import { formatDate } from '~/src/utils';
import { serviceEnv } from '~/.env-config';

const { loading, startLoading, endLoading } = useLoading();
// 锡水报表
const time = ref(0);
const range = ref<[number, number] | null>(null);
// 查看今日报表
function lookReport() {
  if (range.value !== null) {
    let formateValue = '';
    switch (time.value) {
      case 0:
        formateValue = 'yyyy-MM-dd';
        break;
      case 1:
        formateValue = 'yyyy-MM';
        break;
      case 2:
        formateValue = 'yyyy';
        break;
      default:
        formateValue = 'yyyy-MM-dd';
        break;
    }
    const todayTime = formatDate(new Date(), formateValue);
    const url = `${serviceEnv.prod.reportServerUrl}/jmreport/view/1084647370402955264?createTime=${todayTime}`;
    window.open(url, '_blank');
  } else {
    window.$message?.error('请选择时间范围！');
  }
}
type searchType = {
  warehouseId: string | null;
  itemIdentifier: string;
  itemCode: string;
  batchCode: string;
  pageNum: number;
  pageSize: number;
  total: number;
  specification: string;
};

const data = ref<DataType[]>([]);
const opetions = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  warehouseId: null,
  itemIdentifier: '',
  itemCode: '',
  batchCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0,
  specification: ''
});

function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    warehouseId: null,
    itemIdentifier: '',
    itemCode: '',
    batchCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0,
    specification: ''
  };
  init();
}
type DataType = {
  materialStockId: string;
  itemCode: string;
  itemName: string;
  specification: string;
  warehouseName: string;
  vendorNick: string;
  unitOfMeasure: string;
  waringValue: string;
  itemIdentifier: string;
};

const rowKey = (row: any) => row.materialStockId;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '物料识别码',
    align: 'center',
    width: 180,
    key: 'itemIdentifier',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '物料编码',
    align: 'center',
    width: 150,
    key: 'itemCode'
  },
  {
    title: '物料名称',
    align: 'center',
    width: 100,
    key: 'itemName'
  },
  {
    title: '类型',
    align: 'center',
    width: 80,
    key: 'itemTypeName'
  },
  {
    title: '规格',
    align: 'center',
    width: 120,
    key: 'specification',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '重量',
    width: 80,
    align: 'center',
    key: 'totalWeight'
  },
  {
    title: '单位',
    width: 80,
    align: 'center',
    key: 'weightUnit'
  },
  {
    title: '批号',
    width: 140,
    align: 'center',
    key: 'batchCode',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '所在仓库',
    width: 80,
    align: 'center',
    key: 'warehouseName'
  }
]);

const checked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};
// function handleEdit(materialStockId) {
//   console.log(materialStockId.value);
// }
// function handleDelete(materialStockId) {
//   console.log(materialStockId.value);
// }
// // 弹窗
// const dialogFlag = ref<boolean>(false);
// const handleCancel = () => {
//   dialogFlag.value = false;
// };
// const handleSubmit = () => {
//   dialogFlag.value = false;
// };

function getWarehouseList() {
  getlist({}).then(res => {
    opetions.value = res.data.map(item => {
      return {
        label: item.warehouseName,
        value: item.warehouseId
      };
    });
  });
}

function init() {
  startLoading();
  data.value = [];
  getStorageManageList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  init();
  getWarehouseList();
});
</script>

<style scoped></style>
