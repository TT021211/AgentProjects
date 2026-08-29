<template>
  <div class="h-full">
    <n-card title="绞线在制品查询" :bordered="false" class="h-full rounded-8px shadow-sm">
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
            <!-- <n-form-item label="入库日期" path="Item.recptDate" :label-style="labelStyle">
              <n-date-picker v-model:value="formValue.recptDate" type="date" :format="format" />
            </n-form-item> -->
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
        <my-pagination v-model:search-form="formValue" class="mr-10" @init="getTableData"></my-pagination>
      </n-space>
    </n-card>

    <my-dialog
      v-model:show="detail"
      sub-text="关闭"
      width="1200px"
      title="订单明细"
      :show-cancel="false"
      @cancel="closeDetail"
      @submit="closeDetail"
    >
      <template #content>
        <div>
          <n-data-table
            class="w-1000px"
            :loading="detailLoading"
            :columns="detailColumns"
            :data="detailData"
          ></n-data-table>
        </div>
      </template>
    </my-dialog>
    <my-dialog v-model:show="flag" title="设置预警值" width="600px" @cancel="cancel" @submit="submit">
      <template #content>
        <n-form label-placement="left" label-width="90px" label-align="left">
          <n-form-item label="物料名称：">
            <span>{{ formData.itemName }}</span>
          </n-form-item>
          <n-form-item label="预警值">
            <n-input-number v-model:value="formData.waringValue" type="number" :min="0" placeholder="请输入预警值" />
          </n-form-item>
        </n-form>
      </template>
    </my-dialog>
  </div>
</template>

<script lang="tsx" setup>
import { ref, reactive } from 'vue';
import { type DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { useLoading } from '@/hooks';
import { formatDate, addDateRange } from '@/utils';
import { fetchMaterialStockList, selectAll, updateWaringValue } from '@/service/api/wms/materialstock';
import { useInfoBtn } from '@/hooks/common/useBtn';
const { loading, startLoading, endLoading } = useLoading(false);

// let allRowKeysArr: string[] = [];
const expandedRows = ref(['200']);
const expandedSwitchshow = ref(true);
const format = 'yyyy-MM-dd';
const daterange = ref();
const flag = ref<boolean>(false);
const tableData = ref<ApiMaterialStockManagement.materialStock[]>([]);
const itemName = ref<string>('');
const message = useMessage();
function setTableData(data: ApiMaterialStockManagement.materialStock[]) {
  tableData.value = data;
}
const formData = ref<any>({
  itemName: '',
  itemCode: '',
  waringValue: null
});
const formValue = ref({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  attr1: '0',
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

const detailLoading = ref<boolean>(false);
const detail = ref<boolean>(false);

function closeDetail() {
  detail.value = false;
}
const detailData = ref<Procure.rawProcureList.listType[]>([]);

const statusMap = {
  1: {
    key: 'error',
    label: '出库'
  },
  2: {
    key: 'success',
    label: '入库'
  }
};

const changeStatus = (row: any, type: string): string => {
  if (row.transactionType === null || row.transactionType === null) {
    return '';
  }
  if (type === 'key') {
    return statusMap[row.transactionType === 'ITEM_RECPT' ? 2 : 1].key;
  }
  return statusMap[row.transactionType === 'ITEM_RECPT' ? 2 : 1].label;
};

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
    title: '库存盘数',
    key: 'afterQuantity',
    align: 'center',
    render(row) {
      if (row.afterQuantity > 0) {
        return <span style="color: green;font-weight: bold;font-size: 16px;">{row.afterQuantity}</span>;
      }
      return <span style="color: red;font-weight: bold;font-size: 16px;">{row.afterQuantity}</span>;
    }
  },
  {
    title: '库存重量',
    key: 'afterWeight',
    align: 'center',
    render(row) {
      if (row.afterWeight > 0) {
        return <span style="color: green;font-weight: bold;font-size: 16px;">{row.afterWeight}</span>;
      }
      return <span style="color: red;font-weight: bold;font-size: 16px;">{row.afterWeight}</span>;
    }
  },
  {
    title: '单位',
    key: 'unitOfMeasure',
    align: 'center'
  },
  {
    title: '所在仓库',
    key: 'warehouseName',
    align: 'center'
  },
  {
    title: '预警值',
    key: 'waringValue',
    align: 'center'
  },
  // {
  //   title: '所在库区',
  //   key: 'locationName',
  //   align: 'center'
  // },
  // {
  //   title: '所在库位',
  //   key: 'areaName',
  //   align: 'center'
  // },
  {
    title: '操作',
    key: 'action',
    fixed: 'right',
    width: 240,
    render: row => {
      return [
        useInfoBtn(
          () => {
            detail.value = true;
            detailLoading.value = true;
            selectAll({
              itemCode: row.itemCode,
              itemName: row.itemName,
              specification: row.specification,
              attr1: '0'
            }).then(res => {
              detailLoading.value = false;
              detailData.value = res.rows;
            });
          },
          'tiny',
          '明细'
        ),
        <n-button size="tiny" type="primary" class="ml-12px" onClick={() => handleSeeting(row)}>
          <icon-tdesign-setting class="mr-2" />
          设置预警值
        </n-button>
      ];
    }
  }
];

const detailColumns: DataTableColumns<ApiMaterialStockManagement.materialStock> = [
  {
    title: '序号',
    key: 'index',
    render(_row, index) {
      return index + 1;
    },
    width: 80
  },
  {
    title: '物料名称',
    key: 'itemName',
    width: 180
  },
  {
    title: '批号',
    key: 'batchCode'
  },

  {
    title: '规格型号',
    key: 'specification'
  },

  {
    title: '类型',
    key: 'transactionType',
    align: 'center',
    render(row) {
      return <n-tag type={changeStatus(row, 'key')}>{changeStatus(row, 'label')}</n-tag>;
    }
  },
  {
    title: '数量',
    key: 'transactionQuantity',
    align: 'center',
    render(row) {
      if (row.transactionQuantity > 0) {
        return <span style="color: green;font-weight: bold;font-size: 16px;">{row.transactionQuantity}</span>;
      }
      return <span style="color: red;font-weight: bold;font-size: 16px;">{row.transactionQuantity}</span>;
    }
  },
  {
    title: '变化前数量',
    key: 'beforeQuantity'
  },
  {
    title: '变化后数量',
    key: 'afterQuantity'
  },
  {
    title: '变化前重量',
    key: 'beforeWeight'
  },
  {
    title: '变化后重量',
    key: 'afterWeight'
  },
  {
    title: '变化日期',
    key: 'createTime'
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
      formValue.value.total = data.total;
      setTableData(data.rows);
      endLoading();
      // allRowKeysArr = data.allParentIDArr;
      // expandedRows.value = data.allParentIDArr;
    }, 100);
  }
}
function handleSeeting(row: any) {
  formData.value = JSON.parse(JSON.stringify(row));
  itemName.value = row.itemName;
  flag.value = true;
}
function submit() {
  if (!formData.value.waringValue) {
    message.error('请输入预警值');
  } else {
    updateWaringValue(formData.value).then(res => {
      flag.value = false;
      formData.value.waringValue = 0;
      itemName.value = '';
      if (res.code === 200) {
        message.success('设置成功');
      } else {
        message.error('设置预警值失败');
      }
      getTableData();
    });
  }
}
function cancel() {
  formData.value.waringValue = 0;
  flag.value = false;
  itemName.value = '';
}
function handleSearchClick() {
  formValue.value.pageNum = 1;
  getTableData();
  expandedSwitchshow.value = true;
}

function handleResetClick() {
  formValue.value.attr1 = '0';
  formValue.value.pageNum = 1;
  formValue.value.pageSize = 10;
  formValue.value.total = 0;
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
