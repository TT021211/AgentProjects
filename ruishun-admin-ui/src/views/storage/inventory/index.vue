<template>
  <div class="h-full">
    <n-card title="库存查询" :bordered="false" class="h-full rounded-8px shadow-sm">
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
            <n-form-item label="规格型号" path="Item.specification" :label-style="labelStyle">
              <n-input v-model:value="formValue.specification" placeholder="请输入规格型号" />
            </n-form-item>
            <n-form-item label="类型" :label-style="labelStyle">
              <n-select
                v-model:value="formValue.attr1"
                placeholder="请选择类型"
                class="w-180px"
                :options="statusList"
              ></n-select>
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
            :max-height="500"
            :scroll-x="1300"
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
import { ref, reactive, h } from 'vue';
import { type DataTableColumns } from 'naive-ui';
import { useMessage, NTag } from 'naive-ui';
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
  itemName: '',
  itemCode: '',
  batchCode: '',
  warehouseName: '',
  vendorCode: '',
  vendorName: '',
  recptDate: null,
  specification: '',
  attr1: null
});
function changeType(str: any) {
  if (str === '0' || str === '4') {
    return 'default';
  } else if (str === '5' || str === '6' || str === '7') {
    return 'warning';
  } else if (str === '2') {
    return 'info';
  } else if (str === '3') {
    return 'success';
  }
  return 'error';
}
function getStatus(status: string) {
  if (status === '0') return '铜丝在制品';
  // if (status === '1') return '漆包在制品';
  if (status === '2') return '辅料';
  if (status === '3') return '成品';
  if (status === '4') return '锡锭';
  if (status === '5') return '锡水';
  if (status === '6') return '并丝在制品';
  if (status === '7') return '绞线在制品';
  return '';
}
const labelStyle = reactive({
  backgroundColor: '#F8F8F8',
  border: '1px solid #E0E0E6',
  borderRight: 'none',
  marginRight: '-2px',
  padding: '0 15px',
  width: '140px'
});
const statusList = ref<Array<{ label: string; value: string }>>([
  { label: '铜丝在制品', value: '0' },
  // { label: '漆包在制品', value: '1' },
  { label: '退火在制品', value: '4' },
  { label: '镀锡在制品', value: '5' },
  { label: '并丝在制品', value: '6' },
  { label: '绞线在制品', value: '7' },
  { label: '辅料', value: '2' },
  { label: '成品', value: '3' }
]);
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
    align: 'center',
    key: 'itemCode'
  },
  {
    title: '物料名称',
    align: 'center',
    key: 'itemName'
  },
  {
    title: '规格型号',
    align: 'center',
    key: 'specification'
  },

  {
    title: '库存重量',
    align: 'center',
    key: 'afterWeight',
    render(row) {
      if (row.afterWeight > 0) {
        return <span style="color: green;font-weight: bold;font-size: 16px;">{row.afterWeight}</span>;
      }
      return <span style="color: red;font-weight: bold;font-size: 16px;">{row.afterWeight}</span>;
    }
  },
  {
    title: '库存数量',
    align: 'center',
    key: 'afterQuantity',
    render(row) {
      if (row.afterQuantity > 0) {
        return <span style="color: green;font-weight: bold;font-size: 16px;">{row.afterQuantity}</span>;
      }
      return <span style="color: red;font-weight: bold;font-size: 16px;">{row.afterQuantity}</span>;
    }
  },
  {
    title: '单位',
    align: 'center',
    key: 'unitOfMeasure'
  },
  {
    title: '类型',
    align: 'center',
    key: 'attr1',
    render: (row: ApiMaterialStockManagement.materialStock) => {
      return h(
        NTag,
        {
          type: changeType(row.attr1)
        },
        () => getStatus(row.attr1 as string)
      );
    }
  },
  {
    title: '所在仓库',
    align: 'center',
    key: 'warehouseName'
  },
  {
    title: '预警值',
    align: 'center',
    key: 'waringValue'
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
    align: 'center',
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
              pageSize: 999,
              attr1: row.attr1
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
    align: 'center',
    key: 'index',
    render(_row, index) {
      return index + 1;
    },
    width: 80
  },
  {
    title: '物料名称',
    align: 'center',
    key: 'itemName',
    width: 140
  },
  {
    title: '批号',
    align: 'center',
    key: 'batchCode',
    width: 120,
    ellipsis: {
      tooltip: true
    }
  },

  {
    title: '规格型号',
    align: 'center',
    key: 'specification',
    width: 80,
    ellipsis: {
      tooltip: true
    }
  },

  {
    title: '类型',
    align: 'center',
    key: 'transactionType',
    width: 80,
    render(row) {
      return <n-tag type={changeStatus(row, 'key')}>{changeStatus(row, 'label')}</n-tag>;
    }
  },
  {
    title: '数量',
    align: 'center',
    key: 'transactionQuantity',
    width: 80,
    render(row) {
      if (row.transactionQuantity > 0) {
        return <span style="color: green;font-weight: bold;font-size: 16px;">{row.transactionQuantity}</span>;
      }
      return <span style="color: red;font-weight: bold;font-size: 16px;">{row.transactionQuantity}</span>;
    }
  },
  {
    title: '变化前数量',
    align: 'center',
    key: 'beforeQuantity',
    width: 120
  },
  {
    title: '变化后数量',
    align: 'center',
    key: 'afterQuantity',
    width: 120
  },
  {
    title: '变化前重量',
    align: 'center',
    key: 'beforeWeight',
    width: 120
  },
  {
    title: '变化后重量',
    align: 'center',
    key: 'afterWeight',
    width: 120
  },
  {
    title: '变化日期',
    align: 'center',
    key: 'createTime',
    width: 180
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
  formValue.value.specification = '';
  formValue.value.attr1 = null;
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
