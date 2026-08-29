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
    </my-card>
    <my-card title="库存列表">
      <template #right>
        <CxColumns v-model:columns="columns" size="small" />
        <div>
          <!-- <n-button type="primary" :disabled="checkedRowKeys.length <= 0" @click="dialogFlag = true">
						<icon-tdesign-edit />
						报废申请
					</n-button> -->
        </div>
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
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

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted, h } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useMessage, NTag } from 'naive-ui';
import { selectWaringValue, updateWaringValue } from '@/service/api/wms/materialstock';
import { getlist } from '@/service/api/wms/warehouseSet';
import { useLoading } from '~/src/hooks';

const { loading, startLoading, endLoading } = useLoading();
type searchType = {
  warehouseId: string | null;
  itemCode: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
const flag = ref<boolean>(false);
const itemName = ref<string>('');
const data = ref<DataType[]>([]);
const opetions = ref<DataType[]>([]);
const message = useMessage();
const searchForm = ref<searchType>({
  warehouseId: null,
  itemCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    warehouseId: '',
    itemCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
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
  waringValue: number;
  itemIdentifier: string;
  quantityOnhand: number;
  attr1: string;
};

const formData = ref<any>({
  itemName: '',
  itemCode: '',
  waringValue: null
});

const statusMap = {
  1: {
    key: 'error',
    label: '预警'
  },
  2: {
    key: 'success',
    label: '正常'
  }
};

const changeStatus = (row: any, type: string): string => {
  if (row.waringValue === null || row.quantityOnhand === null) {
    return 'error';
  }
  if (type === 'key') {
    return statusMap[row.waringValue <= row.quantityOnhand ? 2 : 1].key;
  }
  return statusMap[row.waringValue <= row.quantityOnhand ? 2 : 1].label;
};
function changeType(str: any) {
  if (str === '0') {
    return 'default';
  } else if (str === '1') {
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
  if (status === '2') return '辅料';
  if (status === '3') return '成品';
  return '';
}
const rowKey = (row: any) => row.materialStockId;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '仓库',
    key: 'warehouseName',
    align: 'center',
    width: 100,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '物料名称',
    key: 'itemName',
    width: 120,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '物料类型',
    key: 'itemCode',
    width: 120,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '型号',
    key: 'specification',
    align: 'center',
    width: 100,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '重量',
    key: 'afterWeight',
    align: 'center',
    width: 60,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '数量',
    align: 'center',
    key: 'afterQuantity',
    width: 60,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '单位',
    align: 'center',
    key: 'unitOfMeasure',
    width: 60,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '状态',
    align: 'center',
    key: '',
    width: 60,
    render(row) {
      return <n-tag type={changeStatus(row, 'key')}>{changeStatus(row, 'label')}</n-tag>;
    }
  },

  {
    title: '类型',
    key: 'attr1',
    align: 'center',
    width: 120,
    ellipsis: {
      tooltip: true
    },
    render: (row: DataType) => {
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
    title: '安全库存',
    align: 'center',
    key: 'waringValue',
    width: 100,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '操作',
    align: 'center',
    key: 'actions',
    render(row) {
      return (
        <n-button size="tiny" type="primary" class="text-15px" onClick={() => handleSeeting(row)}>
          <icon-tdesign-setting class="mr-2" />
          设置预警值
        </n-button>
      );
    }
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

function cancel() {
  formData.value.waringValue = 0;
  flag.value = false;
  itemName.value = '';
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
      init();
    });
  }
}

function handleSeeting(row: any) {
  formData.value = JSON.parse(JSON.stringify(row));
  itemName.value = row.itemName;
  flag.value = true;
}

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
  selectWaringValue(searchForm.value).then(res => {
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
