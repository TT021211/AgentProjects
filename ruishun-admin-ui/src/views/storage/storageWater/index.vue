<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="类型">
          <n-select
            v-model:value="searchForm.transactionType"
            :options="opetions"
            class="w-180px"
            placeholder="请选择类型"
          ></n-select>
        </n-form-item>
        <n-form-item label="产品(物料)编码">
          <n-input v-model:value="searchForm.itemCode" placeholder="请输入产品(物料)编码"></n-input>
        </n-form-item>
        <n-form-item label="产品(物料)名称">
          <n-input v-model:value="searchForm.itemName" placeholder="请输入产品(物料)名称"></n-input>
        </n-form-item>
        <n-form-item label="批次号">
          <n-input v-model:value="searchForm.batchCode" placeholder="请输入批次号"></n-input>
        </n-form-item>
        <n-form-item label="箱编码">
          <n-input v-model:value="searchForm.containerCode" placeholder="请输入箱编码"></n-input>
        </n-form-item>
      </n-form>
      <n-form inline>
        <n-form-item label="来源单据号">
          <n-input v-model:value="searchForm.sourceDocCode" placeholder="请输入来源单据号"></n-input>
        </n-form-item>
        <n-form-item label="记录日期">
          <n-date-picker v-model:value="searchForm.transactionDate" type="date" clearable />
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
    <my-card title="库存流水记录">
      <template #right>
        <CxColumns v-model:columns="columns" size="small" />
        <div></div>
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
        :summary="summary"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted, h } from 'vue';
import type { DataTableColumns, DataTableCreateSummary } from 'naive-ui';
import { getlist } from '@/service/api/wms/warehouseSet';
import { useLoading } from '~/src/hooks';

const { loading, startLoading, endLoading } = useLoading();

const opetions = ref<DataType[]>([]);

type searchType = {
  transactionType: string | null;
  itemCode: string;
  itemName: string;
  batchCode: string;
  containerCode: string;
  sourceDocCode: string;
  transactionDate: number | null;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchForm = ref<searchType>({
  transactionType: null,
  itemCode: '',
  itemName: '',
  batchCode: '',
  containerCode: '',
  sourceDocCode: '',
  transactionDate: null,
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
    transactionType: null,
    itemCode: '',
    itemName: '',
    batchCode: '',
    containerCode: '',
    sourceDocCode: '',
    transactionDate: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

const data = ref<DataType[]>([
  {
    itemCode: 'IF2023030735',
    itemName: '裸铜线',
    specification: '0.06',
    unitOfMeasure: 'kg',
    batchCode: '06',
    containerCode: 'WPG20230612000002',
    sourceDocCode: 'R20240616006',
    transactionType: '入库',
    transactionQuantity: '+1999',
    weight: 198,
    totalWeight: 38,
    transactionDate: '2024-06-14',
    createTime: '2024-06-14 14:13:00'
  },
  {
    itemCode: 'IF2023030735',
    itemName: '裸铜线',
    specification: '0.06',
    unitOfMeasure: 'kg',
    batchCode: '06',
    containerCode: 'WPG20230612000002',
    sourceDocCode: 'R20240616006',
    transactionType: '出库',
    transactionQuantity: '-1999',
    weight: 198,
    totalWeight: 38,
    transactionDate: '2024-06-14',
    createTime: '2024-06-14 14:13:00'
  }
]);
type DataType = {
  itemCode: string;
  itemName: string;
  specification: string;
  unitOfMeasure: string;
  batchCode: string;
  containerCode: string;
  sourceDocCode: string;
  transactionType: string;
  transactionQuantity: string;
  weight: number;
  totalWeight: number;
  transactionDate: string;
  createTime: string;
};
const summary: DataTableCreateSummary = pageData => {
  return {
    index: {
      value: h('span', '合计')
    },
    weight: {
      value: h(
        'span',
        (pageData as unknown as DataType[]).reduce((prevValue, row) => prevValue + row.weight, 0)
      )
    },
    transactionQuantity: {
      value: h(
        'span',
        (pageData as unknown as DataType[]).reduce(
          (prevValue, row) => prevValue + parseInt(row.transactionQuantity, 10),
          0
        )
      )
    }
  };
};
const rowKey = (row: any) => row.materialStockId;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '产品(物料)编码',
    key: 'itemCode',
    width: 180
  },
  {
    title: '产品(物料)名称',
    width: 120,
    key: 'itemName'
  },
  {
    title: '规格型号',
    width: 80,
    key: 'specification'
  },
  {
    title: '单位',
    width: 60,
    key: 'unitOfMeasure'
  },
  {
    title: '批次号',
    width: 80,
    key: 'batchCode'
  },
  {
    title: '箱编码',
    titleAlign: 'center',
    width: 180,
    key: 'containerCode'
  },
  {
    title: '来源单据号',
    width: 130,
    key: 'sourceDocCode'
  },
  {
    title: '类型',
    width: 60,
    key: 'transactionType',
    render: row => {
      return row.transactionType === '入库' ? (
        <n-tag type="success">{row.transactionType}</n-tag>
      ) : (
        <n-tag type="error">{row.transactionType}</n-tag>
      );
    }
  },
  {
    title: '数量',
    width: 80,
    key: 'transactionQuantity',
    render: row => {
      return row.transactionType === '入库' ? (
        <span style="color:green;font-weight:800;font-size:16px;">{row.transactionQuantity}</span>
      ) : (
        <span style="color:red;font-weight:800;font-size:16px;">{row.transactionQuantity}</span>
      );
    }
  },
  {
    title: '净重',
    width: 80,
    key: 'weight'
  },
  {
    title: '毛重',
    width: 80,
    key: 'totalWeight'
  },
  {
    title: '来源日期',
    width: 120,
    key: 'transactionDate'
  },
  {
    title: '记录时间',
    titleAlign: 'center',
    width: 180,
    key: 'createTime'
  }
]);

const checked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};

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
  // data.value = [];
  // getStorageManageList(searchForm.value).then(res => {
  // data.value = res.rows;
  // searchForm.value.total = res.total;
  // });
  endLoading();
}
onMounted(() => {
  init();
  getWarehouseList();
});
</script>
