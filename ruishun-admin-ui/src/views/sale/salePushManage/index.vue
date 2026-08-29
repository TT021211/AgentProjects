<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="出库单号">
          <n-input v-model:value="searchForm.outboundCode" placeholder="请输入出库单号"></n-input>
        </n-form-item>
        <n-form-item label="来源单据号">
          <n-input v-model:value="searchForm.documentNumber" placeholder="请输入来源单据号"></n-input>
        </n-form-item>
        <n-form-item label="出库状态">
          <n-select
            v-model:value="searchForm.outboundStatus"
            :options="outboundStatusOptions"
            class="w-160px"
            placeholder="请选择出库状态"
          ></n-select>
        </n-form-item>
        <n-form-item label="出库类型">
          <n-select
            v-model:value="searchForm.outboundType"
            :options="outboundType"
            class="w-160px"
            placeholder="请选择出库类型"
          ></n-select>
        </n-form-item>
        <n-form-item label="出库日期">
          <n-date-picker
            v-model:formatted-value="searchForm.outboundDate"
            value-format="yyyy-MM-dd"
            class="w-160px"
            type="date"
            clearable
          />
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
    <my-card title="出库记录">
      <template #right>
        <div class="btns">
          <n-button size="tiny" type="warning" quaternary class="mr-5px">
            <icon-uil:export class="mr-4px text-20px" />
            导出
          </n-button>
        </div>
        <CxColumns v-model:columns="columns" size="small" />
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
    <!-- 出库明细 -->
    <my-dialog
      v-model:show="boundDetail"
      sub-text="关闭"
      width="1300px"
      title="出库明细数据"
      :show-cancel="false"
      @cancel="closeDetail"
      @submit="closeDetail"
    >
      <template #content>
        <div class="test">
          <my-card title="搜索条件" search>
            <n-form inline>
              <n-form-item label="品名">
                <n-select
                  v-model:value="searchBoundDetail.productName"
                  :options="productList"
                  class="w-180px"
                  placeholder="请选择品名"
                  @update:value="
                    (val, item: any) => {
                      searchBoundDetail.productName = item.label;
                    }
                  "
                ></n-select>
              </n-form-item>
              <n-form-item label="批号">
                <n-input v-model:value="searchBoundDetail.batchNumber2" placeholder="请输入批号"></n-input>
              </n-form-item>
              <n-form-item label="规格型号">
                <n-input v-model:value="searchBoundDetail.wmsSpec" placeholder="请输入规格型号"></n-input>
              </n-form-item>
              <n-form-item label="箱编号">
                <n-input v-model:value="searchBoundDetail.boxCode" placeholder="请输入箱编码"></n-input>
              </n-form-item>
              <n-form-item>
                <n-button class="mr-5px" type="primary" @click="getBoxReacprd">
                  <icon-tdesign-search class="mr-2px" />
                  搜索
                </n-button>
                <n-button @click="handleBoundReset">
                  <icon-ic-round-refresh class="mr-4px text-20px" />
                  重置
                </n-button>
              </n-form-item>
            </n-form>
          </my-card>
          <my-card title="装托箱记录">
            <template #right>
              <CxColumns v-model:columns="detailColumns" size="small" />
            </template>
            <n-data-table
              :loading="detailLoading"
              :columns="detailColumns"
              :row-key="DetailrowKey"
              :data="detailData"
            ></n-data-table>
            <my-pagination v-model:search-form="searchBoundDetail" @init="getBoxReacprd()"></my-pagination>
          </my-card>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { getlist } from '@/service/api/wms/warehouseSet';
import { useLoading, useWarning } from '~/src/hooks';
import { delBound, editBound, getboundList } from '~/src/service/api/storage/materialstock';
import { getBoxReacprding } from '~/src/service/api/storage/storageBox';
import { getAllClassifyList } from '~/src/service/api/md/classify';

const { loading, startLoading, endLoading } = useLoading();

const warning = useWarning('提示');
const warning1 = useWarning('提示', '是否确认作废？');
const warning2 = useWarning('提示', '是否确认出库？');
const message = useMessage();
const opetions = ref<DataType[]>([]);
const outboundType = ref([
  { label: '销售订单', value: '销售订单' },
  { label: '自制产品订单', value: '自制产品订单' },
  { label: '其他', value: '其他' }
]);
const outboundStatusOptions = ref([
  { label: '待出库', value: 'SHIP_OUT' },
  { label: '已出库', value: 'OUT' },
  { label: '已作废', value: 'INVALID' }
]);
const productList: any = ref([]);

// 出库明细
type searchTypeBound = {
  productName: string | null;
  batchNumber2: string;
  wmsSpec: string;
  boxCode: string;
  noticeNumberCode?: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchBoundDetail = ref<searchTypeBound>({
  productName: null,
  batchNumber2: '',
  wmsSpec: '',
  boxCode: '',
  noticeNumberCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const boundDetail = ref<boolean>(false);
const detailLoading = ref<boolean>(false);
const DetailrowKey = (row: any) => row.id;
const detailData = ref<DetailsDataType[]>([]);
const detailColumns: Ref<DataTableColumns<DetailsDataType>> = ref([
  {
    title: '产品(物料)编码',
    align: 'center',
    width: 150,
    key: 'itemCode'
  },
  {
    title: '产品(物料)名称',
    align: 'center',
    width: 120,
    key: 'productName'
  },
  {
    title: '规格型号',
    align: 'center',
    width: 120,
    key: 'wmsSpec'
  },
  {
    title: '单位',
    align: 'center',
    key: 'wmsUnit',
    width: 60
  },
  {
    title: '批号',
    align: 'center',
    width: 80,
    key: 'batchNumber2'
  },
  {
    title: '箱编码',
    align: 'center',
    width: 180,
    key: 'boxCode'
  },
  {
    title: '箱内盘数',
    align: 'center',
    width: 80,
    key: 'boxPerNumber'
  },
  {
    title: '发货数量(箱)',
    align: 'center',
    width: 120,
    key: 'artt4'
  },
  {
    title: '发货净重(kg)',
    align: 'center',
    width: 120,
    key: 'netWeight'
  },
  {
    title: '发货毛重(kg)',
    align: 'center',
    width: 120,
    key: 'grossWeight'
  },
  // {
  //   title: '单价(元)',
  //   align:'center',
  //   width:120,
  //   key: 'sellPrice'
  // },
  // {
  //   title: '总价(元)',
  //   align:'center',
  //   width:80,
  //   key: 'totalPrice',
  // },
  {
    title: '备注',
    align: 'center',
    width: 180,
    key: 'artt2'
  }
]);
type DetailsDataType = {
  documentNumber: string;
  itemName: string;
  specification: string;
  unitOfMeasure: string;
  batchCode: string;
  boxCode: string;
  quantityOnhand: number;
  quantity: number;
  weight: number;
  totalWeight: number;
  sellPrice: number;
  totalPrice: number;
  remark: string;
  noticeNumberCode: Array<any>;
};
function handleBoundReset() {
  searchBoundDetail.value = {
    batchNumber2: '',
    productName: null,
    wmsSpec: '',
    boxCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  getBoxReacprd();
}
function closeDetail() {
  detailLoading.value = true;
  boundDetail.value = false;
  searchBoundDetail.value = {
    batchNumber2: '',
    productName: null,
    wmsSpec: '',
    boxCode: '',
    noticeNumberCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

// 出库记录
type searchType = {
  outboundCode: string;
  documentNumber: string;
  outboundStatus: string | null;
  outboundType: string | null;
  outboundDate: string | null;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchForm = ref<searchType>({
  outboundCode: '',
  documentNumber: '',
  outboundStatus: null,
  outboundType: null,
  outboundDate: null,
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
    outboundCode: '',
    documentNumber: '',
    outboundStatus: null,
    outboundType: null,
    outboundDate: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

const data = ref<DataType[]>([]);
type DataType = {
  id: string;
  outboundCode: string;
  outboundName: string;
  outboundDate: string;
  outboundStatus: string;
  outboundType: string;
  documentNumber: string;
  detailedQuantity: string;
  detailedInformation: string;
  detailedWeight: string;
  detailCategory: string;
  createBy: string;
};
const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '出库单号',
    titleAlign: 'center',
    align: 'center',
    width: 180,
    key: 'outboundCode'
  },
  // {
  //   title: '出库名称',
  //   width:120,
  //   key: 'outboundName'
  // },
  {
    title: '出库日期',
    titleAlign: 'center',
    align: 'center',
    width: 180,
    key: 'outboundDate'
  },
  {
    title: '出库状态',
    width: 80,
    titleAlign: 'center',
    align: 'center',
    key: 'outboundStatus',
    render: row => {
      switch (row.outboundStatus) {
        case 'INVALID':
          return <n-tag type="error">{'已作废'}</n-tag>;
        case 'OUT':
          return <n-tag type="success">{'已出库'}</n-tag>;
        case 'SHIP_OUT':
          return <n-tag type="success">{'待出库'}</n-tag>;
        default:
          return <span>{row.outboundStatus}</span>;
      }
    }
  },
  {
    title: '出库类型',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'outboundType',
    render: row => {
      return <n-tag type="info">{`${row.outboundType}出库单`}</n-tag>;
    }
  },
  {
    title: '来源单据号',
    titleAlign: 'center',
    align: 'center',
    key: 'documentNumber',
    width: 180
  },
  {
    title: '明细数量',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'detailedQuantity'
  },
  {
    title: '明细信息',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'detailedInformation'
  },
  {
    title: '明细重量',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'detailedWeight'
  },
  {
    title: '明细类别',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'detailCategory'
  },
  {
    title: '创建人',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'createBy'
  },
  {
    titleAlign: 'center',
    align: 'center',
    title: '创建时间',
    width: 180,
    key: 'createTime'
  },
  {
    title: '操作',
    titleAlign: 'center',
    align: 'center',
    fixed: 'right',
    width: 140,
    key: 'cz',
    render: row => {
      switch (row.outboundStatus) {
        case 'INVALID':
          return (
            <div>
              <n-button
                onClick={() => {
                  editORDelBound(row, 3);
                }}
                size="tiny"
                type="error"
                quaternary
                class="mr-5px"
              >
                删除
              </n-button>
              <n-button
                onClick={() => {
                  lookBound(row);
                }}
                size="tiny"
                type="info"
                quaternary
                class="mr-5px"
              >
                明细
              </n-button>
            </div>
          );

        case 'OUT':
          return (
            <div>
              <n-button
                onClick={() => {
                  editORDelBound(row, 2);
                }}
                size="tiny"
                type="info"
                quaternary
                class="mr-5px"
              >
                作废
              </n-button>
              <n-button
                onClick={() => {
                  lookBound(row);
                }}
                size="tiny"
                type="info"
                quaternary
                class="mr-5px"
              >
                明细
              </n-button>
            </div>
          );
        case 'SHIP_OUT':
          return (
            <div>
              <n-button
                onClick={() => {
                  editORDelBound(row, 1);
                }}
                size="tiny"
                type="info"
                quaternary
                class="mr-5px"
              >
                出库
              </n-button>
              <n-button
                onClick={() => {
                  lookBound(row);
                }}
                size="tiny"
                type="info"
                quaternary
                class="mr-5px"
              >
                明细
              </n-button>
            </div>
          );
        default:
          return <span>{''}</span>;
      }
    }
  }
]);

function getBoxReacprd() {
  startLoading();
  detailData.value = [];
  getBoxReacprding(searchBoundDetail.value).then(res => {
    detailData.value = res.rows;
    searchBoundDetail.value.total = res.total;
    boundDetail.value = true;
    detailLoading.value = false;
  });
  endLoading();
}
function lookBound(row) {
  searchBoundDetail.value.noticeNumberCode = row.documentNumber;
  getBoxReacprd();
}
function editORDelBound(row, artt3) {
  if (artt3 === 3) {
    warning.warn(() => {
      delBound(row.id).then(res => {
        if (res.code === 200) {
          message.success('删除成功');
          init();
        } else {
          message.error('删除失败');
        }
      });
    });
  } else if (artt3 === 1) {
    outOrInvalid({ ...row, artt3 });
  } else {
    outOrInvalid({ ...row, artt3 });
  }
}
function outOrInvalid(row) {
  if (row.artt3 === 1) {
    warning2.warn(() => {
      editBound(row).then(res => {
        if (res.code === 200) {
          message.success('出库成功');
          init();
        } else {
          message.error('出库失败');
        }
      });
    });
  } else {
    warning1.warn(() => {
      editBound(row).then(res => {
        if (res.code === 200) {
          message.success('作废成功');
          init();
        } else {
          message.error('作废失败');
        }
      });
    });
  }
}
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
  getAllClassifyList({ pageNum: 1 }).then(res => {
    res.data.forEach(item => {
      if (item.parentTypeId !== 0) {
        productList.value.push({
          id: item.id,
          value: item.id,
          key: item.id,
          label: item.typeName
        });
      }
    });
  });
}

function init() {
  startLoading();
  data.value = [];
  getboundList(searchForm.value).then(res => {
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
<style scope>
.test {
  width: 1200px;
  display: flex;
  flex-direction: column;
}
.n-icon {
  margin-right: 5px;
}
.n-button .n-button__content {
  font-size: 14px;
}
.n-data-table .n-data-table-th.n-data-table-th--fixed-right,
.n-data-table .n-data-table-td.n-data-table-td--fixed-right {
  box-shadow: -5px 2px 10px 0px rgb(198, 196, 196);
}
</style>
