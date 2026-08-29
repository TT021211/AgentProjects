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
            <n-form-item label="产品名称" :label-style="labelStyle">
              <n-input v-model:value="formValue.productName" placeholder="请输入产品名称" />
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
            :max-height="dataTableConfig.maxHeight"
            :scroll-x="dataTableConfig.scrollWidth(columns)"
            :expanded-row-keys="expandedRows"
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
      @submit="closeDetail"
    >
      <template #content>
        <div>
          <n-data-table
            class="w-1000px"
            :loading="detailLoading"
            :columns="detailColumns"
            :data="detailData"
            :max-height="dataTableConfig.maxHeight"
            :scroll-x="dataTableConfig.scrollWidth(detailColumns)"
          ></n-data-table>
          <my-pagination v-model:search-form="searchDetailForm" @init="lookDetailList"></my-pagination>
        </div>
      </template>
    </my-dialog>
    <!-- <my-dialog v-model:show="flag" title="设置预警值" width="600px" @cancel="cancel" @submit="submit">
      <template #content>
        <n-form label-placement="left" label-width="90px" label-align="left">
          <n-form-item label="物料名称：">
            <span>{{ formData.productName }}</span>
          </n-form-item>
          <n-form-item label="预警值">
            <n-input-number v-model:value="formData.waringValue" type="number" :min="0" placeholder="请输入预警值" />
          </n-form-item>
        </n-form>
      </template>
    </my-dialog> -->
  </div>
</template>

<script lang="tsx" setup>
import { ref, reactive } from 'vue';
import { type DataTableColumns } from 'naive-ui';
// import { useMessage } from 'naive-ui';
import { useLoading } from '@/hooks';
import { useInfoBtn } from '@/hooks/common/useBtn';
import { dataTableConfig } from '@/config/dataTableConfig';
import { getProductStatistics } from '~/src/service/api/storage/storageTinning';
import { getFinishedList } from '~/src/service/api/storage/storageFinishedProduct';
const { loading, startLoading, endLoading } = useLoading(false);

type searchDetailType = {
  productSpec: string;
  productName: string;
  productCode: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchDetailForm = ref<searchDetailType>({
  productSpec: '',
  productName: '',
  productCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

// let allRowKeysArr: string[] = [];
const expandedRows = ref(['200']);
const expandedSwitchshow = ref(true);
// const format = 'yyyy-MM-dd';
const daterange = ref();
// const flag = ref<boolean>(false);
const tableData = ref<ApiMaterialStockManagement.materialStock[]>([]);
// const productName = ref<string>('');
// const message = useMessage();
function setTableData(data: ApiMaterialStockManagement.materialStock[]) {
  tableData.value = data;
}
// const formData = ref<any>({
//   productName: '',
//   productCode: '',
//   waringValue: null
// });
type formType = {
  pageNum: number;
  pageSize: number;
  total: number;
  productName: string;
};
const formValue = ref<formType>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  productName: ''
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

// const statusMap = {
//   1: {
//     key: 'error',
//     label: '出库'
//   },
//   2: {
//     key: 'success',
//     label: '入库'
//   }
// };

type productType = {
  productCode: string;
  productName: string;
  productNumber: string;
  productSpec: string;
  grossWeight: string;
};
const columns: DataTableColumns<productType> = [
  {
    title: '产品编码',
    align: 'center',
    width: 180,
    key: 'productCode'
  },
  {
    title: '产品名称',
    align: 'center',
    width: 100,
    key: 'productName'
  },
  {
    title: '产品数量',
    width: 100,
    key: 'productNumber',
    align: 'center'
  },
  {
    title: '产品规格',
    width: 100,
    key: 'productSpec',
    align: 'center'
  },
  {
    title: '总重量',
    width: 100,
    key: 'grossWeight',
    align: 'center'
  },
  {
    title: '操作',
    align: 'center',
    width: 100,
    key: 'action',
    fixed: 'right',
    render: row => {
      return [
        useInfoBtn(
          () => {
            detail.value = true;
            detailLoading.value = true;
            searchDetailForm.value.productSpec = row.productSpec;
            searchDetailForm.value.productName = row.productName;
            searchDetailForm.value.productCode = row.productCode;
            lookDetailList();
          },
          'tiny',
          '明细'
        )
        // <n-button size="tiny" type="primary" class="ml-12px" onClick={() => handleSeeting(row)}>
        //   <icon-tdesign-setting class="mr-2" />
        //   设置预警值
        // </n-button>
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
    key: 'productName',
    width: 100
  },
  {
    title: '批号',
    width: 80,
    key: 'productBatchNumber'
  },

  {
    title: '规格型号',
    width: 80,
    key: 'productSpec'
  },

  {
    title: '类型',
    key: 'status',
    width: 80,
    align: 'center',
    render(row) {
      return row.status === 'IN' ? <n-tag type="success">{'入库'}</n-tag> : <n-tag type="error">{'出库'}</n-tag>;
    }
  },
  {
    title: '数量',
    width: 80,
    key: 'productNumber',
    align: 'center'
  },
  {
    title: '变化前数量',
    align: 'center',
    width: 120,
    key: 'artt7'
  },
  {
    title: '变化后数量',
    align: 'center',
    width: 120,
    key: 'artt8'
  },
  {
    title: '变化前重量',
    align: 'center',
    width: 120,
    key: 'artt9',
    render(row) {
      return <span>{parseFloat(row.artt9).toFixed(4)}</span>;
    }
  },
  {
    title: '变化后重量',
    align: 'center',
    width: 120,
    key: 'artt10',
    render(row) {
      return <span>{parseFloat(row.artt10).toFixed(4)}</span>;
    }
  },
  {
    title: '变化日期',
    titleAlign: 'center',
    width: 180,
    key: 'createTime'
  }
];
function lookDetailList() {
  detailData.value = [];
  getFinishedList(searchDetailForm.value).then(res => {
    detailLoading.value = false;
    detailData.value = res.rows;
    searchDetailForm.value.total = res.total;
  });
}
async function getTableData() {
  startLoading();
  // if (daterange.value) {
  //   daterange.value.map(item => {
  //     return formatDate(new Date(item), format);
  //   });
  // }
  const data = await getProductStatistics(formValue.value);
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
function handleSearchClick() {
  formValue.value.pageNum = 1;
  getTableData();
  expandedSwitchshow.value = true;
}

function handleResetClick() {
  formValue.value.pageNum = 1;
  formValue.value.pageSize = 10;
  formValue.value.total = 0;
  formValue.value.productName = '';
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
