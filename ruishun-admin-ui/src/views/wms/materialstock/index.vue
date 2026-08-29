<template>
  <div class="h-full">
    <n-card title="库存记录管理" :bordered="false" class="h-full rounded-8px shadow-sm">
      <n-space vertical>
        <n-space>
          <n-form
            ref="formRef"
            inline
            :model="formValue"
            label-placement="left"
            label-align="center"
            label-width="auto"
            require-mark-placement="right-hanging"
          >
            <n-form-item label="物料编码" path="Item.ItemCode" :label-style="labelStyle">
              <n-input v-model:value="formValue.itemCode" placeholder="请输入物料编码" />
            </n-form-item>
            <n-form-item label="物料名称" path="Item.ItemName" :label-style="labelStyle">
              <n-input v-model:value="formValue.itemName" placeholder="请输入物料名称" />
            </n-form-item>
            <n-form-item label="入库批次号" path="Item.batchCode" :label-style="labelStyle">
              <n-input v-model:value="formValue.batchCode" placeholder="请输入库批次号" />
            </n-form-item>
            <n-form-item label="仓库名称" path="Item.warehouseName" :label-style="labelStyle">
              <n-input v-model:value="formValue.warehouseName" placeholder="请输入仓库名称" />
            </n-form-item>
            <n-form-item label="供应商名称" path="Item.vendorName" :label-style="labelStyle">
              <n-input v-model:value="formValue.vendorName" placeholder="请输入供应商名称" />
            </n-form-item>
            <n-form-item label="入库日期" path="daterange" :label-style="labelStyle">
              <n-date-picker v-model:value="daterange" type="daterange" :format="format" />
            </n-form-item>
            <n-form-item>
              <n-button type="primary" attr-type="button" @click="handleSearchClick">搜索</n-button>
            </n-form-item>
            <n-form-item>
              <n-button type="default" attr-type="button" @click="handleResetClick">重置</n-button>
            </n-form-item>
          </n-form>
        </n-space>
        <div>
          <n-data-table
            :loading="loading"
            :columns="columns"
            :data="tableData"
            :expanded-row-keys="expandedRows"
            :max-height="400"
            :scroll-x="1800"
            striped
          />
        </div>
      </n-space>
    </n-card>
  </div>
</template>

<script lang="tsx" setup>
import { ref, reactive } from 'vue';
import { type DataTableColumns } from 'naive-ui';
import { fetchMaterialStockList } from '@/service';
import { useLoading } from '@/hooks';
import { formatDate, addDateRange } from '@/utils';

const { loading, startLoading, endLoading } = useLoading(false);

// let allRowKeysArr: string[] = [];
const expandedRows = ref(['200']);
const expandedSwitchshow = ref(true);
const format = 'yyyy-MM-dd';
const daterange = ref();

const tableData = ref<ApiMaterialStockManagement.materialStock[]>([]);
function setTableData(data: ApiMaterialStockManagement.materialStock[]) {
  tableData.value = data;
}

const formValue = ref({
  itemName: '',
  itemCode: '',
  batchCode: '',
  warehouseName: '',
  vendorCode: '',
  vendorName: '',
  recptDate: null
});

const labelStyle = reactive({
  backgroundColor: '#F8F8F8',
  border: '1px solid #E0E0E6',
  borderRight: 'none',
  marginRight: '-2px',
  padding: '0 15px',
  width: '140px'
});

const columns: DataTableColumns<ApiMaterialStockManagement.materialStock> = [
  {
    title: '物料编码',
    key: 'itemCode'
  },
  {
    title: '物料名称',
    key: 'itemName',
    align: 'center'
  },
  {
    title: '规格型号',
    key: 'specification',
    align: 'center'
  },
  {
    title: '在库数量',
    key: 'quantityOnhand',
    align: 'center'
  },
  {
    title: '单位',
    key: 'unitOfMeasure',
    align: 'center'
  },
  {
    title: '入库批次',
    key: 'batchCode',
    align: 'center'
  },
  {
    title: '仓库',
    key: 'warehouseName',
    align: 'center'
  },
  {
    title: '库区',
    key: 'locationName',
    align: 'center'
  },
  {
    title: '库位',
    key: 'areaName',
    align: 'center'
  },
  {
    title: '供应商编码',
    key: 'vendorCode',
    align: 'center'
  },
  {
    title: '供应商名称',
    key: 'vendorName',
    align: 'center'
  },
  {
    title: '供应商简称',
    key: 'vendorNick',
    align: 'center'
  },
  {
    title: '生产工单',
    key: 'workorderCode',
    align: 'center'
  },
  {
    title: '入库日期',
    key: 'recptDate',
    align: 'center'
  },
  {
    title: '库存有效期',
    key: '	expireDate',
    align: 'center'
  }
];

async function getTableData() {
  startLoading();
  let dateArr;
  if (daterange.value) {
    dateArr = daterange.value.map(item => {
      return formatDate(new Date(item), format);
    });
  }
  const data = await fetchMaterialStockList({ params: addDateRange(formValue.value, dateArr, undefined) });
  // console.log(data.rows);
  if (data) {
    setTimeout(() => {
      setTableData(data.rows);
      endLoading();
      // allRowKeysArr = data.allParentIDArr;
      // expandedRows.value = data.allParentIDArr;
    }, 100);
  }
}

function handleSearchClick() {
  // if (formValue.value.recptDate !== null) {
  //   formValue.value.recptDate = formatDate(new Date(formValue.value.recptDate), format);
  // }
  getTableData();
  expandedSwitchshow.value = true;
}

function handleResetClick() {
  formValue.value.itemName = '';
  formValue.value.itemCode = '';
  formValue.value.batchCode = '';
  formValue.value.warehouseName = '';
  formValue.value.vendorCode = '';
  formValue.value.vendorName = '';
  formValue.value.recptDate = null;
  daterange.value = null;
  getTableData();
}

getTableData();
</script>

<style>
.width110 {
  width: 110px;
}

.n-form {
  display: flex;
  flex-wrap: wrap;
}
</style>
