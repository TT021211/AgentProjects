<template>
  <div>
    <my-dialog
      v-model:show="showNotice"
      width="1300px"
      :title="props.data?.productTitle"
      :show-cancel="props.data?.showCancel"
      @cancel="closeDetail"
      @submit="addNotice"
    >
      <template #content>
        <div class="test">
          <my-card title="基础信息" search>
            <n-form inline>
              <n-form-item label="单据状态">
                <n-select
                  v-model:value="searchProductInfo.documentStatus"
                  :disabled="true"
                  :options="documentStatusOpetions"
                  class="w-180px"
                  placeholder="请选择单据状态"
                ></n-select>
              </n-form-item>
              <n-form-item label="单据类型">
                <n-select
                  v-model:value="searchProductInfo.documentType"
                  :options="documentTypeOpetions"
                  class="w-180px"
                  placeholder="请选择单据类型"
                ></n-select>
              </n-form-item>
              <hr />
              <n-form-item label="来源单据号">
                <n-input v-model:value="searchProductInfo.artt1" placeholder="请输入来源单据号"></n-input>
              </n-form-item>
              <n-form-item label="客户简称">
                <n-input v-model:value="searchProductInfo.trader" placeholder="请输入客户"></n-input>
                <!-- <n-select
                  v-model:value="searchProductInfo.trader"
                  :options="saleUserList"
                  :filterable="true"
                  class="w-180px"
                  placeholder="请选择客户简称"
                  @update:value="
                    (val, item: any) => {
                      searchProductInfo.trader = item.label;
                    }
                  "
                ></n-select> -->
              </n-form-item>
              <n-form-item label="发货人">
                <n-input v-model:value="searchProductInfo.createBy" placeholder="请输入发货人"></n-input>
              </n-form-item>
              <!-- <n-form-item label="承运商名称">
                <n-input v-model:value="searchProductInfo.shipperName" placeholder="请输入承运商名称"></n-input>
              </n-form-item>
              <n-form-item label="运单号">
                <n-input v-model:value="searchProductInfo.trackingCode" placeholder="请输入运单号"></n-input>
              </n-form-item> -->
              <n-form-item label="送货方式">
                <n-select
                  v-model:value="searchProductInfo.deliveryMethod"
                  :options="deliveryMethodOpetions"
                  class="w-180px"
                  placeholder="请选择送货方式"
                ></n-select>
              </n-form-item>
              <n-form-item label="付款方式">
                <n-select
                  v-model:value="searchProductInfo.paymentMethod"
                  :options="paymentMethodOpetions"
                  class="w-180px"
                  placeholder="请选择付款方式"
                ></n-select>
              </n-form-item>
              <n-form-item label="仓库">
                <n-select
                  v-model:value="searchProductInfo.outboundWarehouse"
                  :options="warehouseOptions"
                  class="w-180px"
                  placeholder="请选择仓库"
                ></n-select>
              </n-form-item>
              <n-form-item label="发货日期">
                <n-date-picker v-model:value="searchProductInfo.shipmentDate" type="date" clearable />
              </n-form-item>
              <n-form-item label="备注">
                <n-input
                  v-model:value="searchProductInfo.artt2"
                  style="width: 55vw"
                  type="textarea"
                  placeholder="请输入备注"
                ></n-input>
              </n-form-item>
            </n-form>
          </my-card>
          <my-card title="产品信息">
            <template v-if="props.data?.showBtns" #right>
              <div class="btns">
                <n-button size="tiny" type="info" quaternary class="mr-5px" @click="selectStorage(0)">
                  <icon-mdi-edit class="mr-1px text-18px" />
                  选择库存
                </n-button>
                <n-button size="tiny" type="error" quaternary class="mr-5px" @click="selectStorage(1)">
                  <icon-tdesign-edit class="mr-1px text-18px" />
                  选择托
                </n-button>
                <n-button size="tiny" type="error" quaternary class="mr-5px" @click="clearData">
                  <icon-tdesign-delete class="mr-1px text-18px" />
                  清除
                </n-button>
              </div>
              <CxColumns v-model:columns="detailColumns" size="small" />
            </template>
            <n-data-table
              :loading="false"
              :columns="detailColumns"
              :data="detailData"
              :max-height="dataTableConfig.maxHeight"
              :scroll-x="dataTableConfig.scrollWidth(detailColumns)"
              :summary="summary"
            ></n-data-table>
          </my-card>
        </div>
      </template>
    </my-dialog>

    <!-- 选择库存 -->
    <my-dialog
      v-model:show="storageDetail"
      width="1300px"
      :title="storageTitle"
      :show-cancel="true"
      @cancel="closeStorageDetail()"
      @submit="addTorrNotice"
    >
      <template v-if="storageShow" #content>
        <div class="test">
          <my-card title="搜索条件" search>
            <n-form inline>
              <n-form-item label="托编号">
                <n-input v-model:value="storageFrom.installationCode" placeholder="请输入托编号"></n-input>
              </n-form-item>
              <n-form-item label="品名">
                <n-select
                  v-model:value="storageFrom.productName"
                  :options="productList"
                  class="w-180px"
                  placeholder="请选择品名"
                  @update:value="
                    (val, item: any) => {
                      storageFrom.productName = item.label;
                    }
                  "
                ></n-select>
              </n-form-item>
              <n-form-item label="规格型号">
                <n-input v-model:value="storageFrom.wmsSpec" placeholder="请输入规格型号"></n-input>
              </n-form-item>
              <n-form-item label="批号">
                <n-input v-model:value="storageFrom.batchNumber2" placeholder="请输入批号"></n-input>
              </n-form-item>
              <n-form-item label="出货状态">
                <n-select
                  v-model:value="storageFrom.shipStatus"
                  :options="shipStatusOption"
                  class="w-180px"
                  placeholder="请选择出货状态"
                ></n-select>
              </n-form-item>
              <n-form-item>
                <n-button class="mr-5px" type="primary" @click="handleStorageSearch">
                  <icon-tdesign-search class="mr-2px" />
                  搜索
                </n-button>
                <n-button @click="handleStorageReset">
                  <icon-ic-round-refresh class="mr-4px text-20px" />
                  重置
                </n-button>
              </n-form-item>
            </n-form>
          </my-card>
          <my-card title="托记录">
            <n-data-table
              v-model:checked-row-keys="checkedTorrRowKeysRef"
              :data="storageData"
              :row-key="storageRowKey"
              :columns="storageColumns"
              :max-height="dataTableConfig.maxHeight"
              :scroll-x="dataTableConfig.scrollWidth(storageColumns)"
              @update-checked-row-keys="handleStorageCheck"
            ></n-data-table>
            <my-pagination v-model:search-form="storageFrom" @init="storageInit"></my-pagination>
          </my-card>
        </div>
      </template>
      <template v-else #content>
        <div class="test">
          <my-card title="搜索条件" search>
            <n-form inline>
              <n-form-item label="托编号">
                <n-input v-model:value="boxSearchForm.installationCode" placeholder="请输入托编号"></n-input>
              </n-form-item>
              <n-form-item label="客户简称">
                <n-select
                  v-model:value="boxSearchForm.trader"
                  placeholder="请选择客户简称"
                  :filterable="true"
                  :options="saleUserList"
                  :style="{ width: witeFormItemWidth }"
                  @update:value="
                    (val, item: any) => {
                      boxSearchForm.trader = item.label;
                    }
                  "
                ></n-select>
              </n-form-item>
              <n-form-item label="批号">
                <n-input v-model:value="boxSearchForm.batchNumber2" placeholder="请输入批号"></n-input>
              </n-form-item>
              <n-form-item label="品名">
                <n-select
                  v-model:value="boxSearchForm.productName"
                  :options="productList"
                  class="w-180px"
                  placeholder="请选择品名"
                  @update:value="
                    (val, item: any) => {
                      boxSearchForm.productName = item.label;
                    }
                  "
                ></n-select>
              </n-form-item>
              <n-form-item label="规格型号">
                <n-input v-model:value="boxSearchForm.wmsSpec" placeholder="请输入规格型号"></n-input>
              </n-form-item>
            </n-form>
            <n-form inline>
              <n-form-item label="装托状态">
                <n-select
                  v-model:value="boxSearchForm.installationStatus"
                  :disabled="true"
                  :options="torrStatusOption"
                  class="w-180px"
                  placeholder="请选择装托状态"
                ></n-select>
              </n-form-item>
              <n-form-item label="箱编号">
                <n-input v-model:value="boxSearchForm.boxCode" placeholder="请输入箱编码"></n-input>
              </n-form-item>
              <n-form-item label="称重时间">
                <n-date-picker v-model:value="boxSearchForm.createTime" type="datetimerange" clearable />
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
          <my-card title="库存记录">
            <n-data-table
              v-model:checked-row-keys="checkedBoxRowKeysRef"
              :data="Boxdata"
              :row-key="boxRowKey"
              :columns="boxColumns"
              :max-height="dataTableConfig.maxHeight"
              :scroll-x="dataTableConfig.scrollWidth(boxColumns)"
              @update-checked-row-keys="handleBoxCheck"
            ></n-data-table>
            <my-pagination v-model:search-form="boxSearchForm" @init="BoxInit"></my-pagination>
          </my-card>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'AddNoticeDialog'
});
import type { Ref } from 'vue';
import { h, ref, defineProps, defineEmits, onMounted } from 'vue';
import type { DataTableColumns, DataTableCreateSummary } from 'naive-ui';
import { NInput, useMessage } from 'naive-ui';
import { dataTableConfig } from '@/config/dataTableConfig';
import { useLoading } from '~/src/hooks';
import { getAllClassifyList } from '~/src/service/api/md/classify';
import { getBoxReacprding } from '~/src/service/api/storage/storageBox';
import { addNoticeLnvoice, selectCodeToBox, updateNoticeLnvoice } from '~/src/service/api/storage/storageNotice';
import { getInstallationList } from '~/src/service/api/storage/storageTorr';
import { list } from '~/src/service/api/wms/warehouseSet';
import { getUserList } from '~/src/service/api/sale/userManage';
const emit = defineEmits(['cencel-click']);
const props = defineProps({
  // eslint-disable-next-line vue/require-default-prop
  data: Object
});
const { startLoading, endLoading } = useLoading();

const message = useMessage();
const productList: any = ref([]);
const shipStatusOption = ref([
  { label: '已出货', value: 'OUT' },
  { label: '未出货', value: 'IN' },
  { label: '待出库', value: 'SHIP_OUT' }
]);
const torrStatusOption = ref([
  { label: '已装托', value: 'IN' },
  { label: '未装托', value: 'OUT' }
]);
const saleUserList: any = ref([]);
const documentStatusOpetions = ref([
  { label: '草稿', value: 'DRAFT' },
  { label: '已确认(待审核)', value: 'CONFIRM' },
  { label: '已审核(待出库)', value: 'CHECK' },
  { label: '已出库', value: 'OUT' }
]);
const documentTypeOpetions = ref([
  { label: '销售订单', value: '销售订单' },
  { label: '自制产品订单', value: '自制产品订单' },
  { label: '其他', value: '其他' }
]);
const deliveryMethodOpetions = ref([
  { label: '送货上门', value: '送货上门' },
  { label: '客户自提', value: '客户自提' }
]);
const paymentMethodOpetions = ref([
  { label: '寄付', value: '寄付' },
  { label: '到付', value: '到付' },
  { label: '月结', value: '月结' }
]);
const warehouseOptions: any = ref([]);
const showNotice = ref<boolean>(true);
const detailData = ref<DetailsDataType[]>([]);
const storageShow = ref<boolean>(false);
const storageDetail = ref<boolean>(false); // 搜索条件
type productInfo = {
  id: number | null;
  itemCode: string;
  documentStatus: string | null;
  documentType: string | null;
  outboundWarehouse: string | null;
  artt1: string;
  trader: string | null;
  createBy: string;
  shipmentDate: [number, number] | null;
  shipperName: string;
  trackingCode: string;
  deliveryMethod: string | null;
  paymentMethod: string | null;
  artt2: string;
};
const searchProductInfo = ref<productInfo>({
  id: null,
  itemCode: '',
  documentStatus: props.data?.documentStatus,
  documentType: props.data?.documentType,
  outboundWarehouse: null,
  artt1: '', // 来源单据号
  trader: null,
  createBy: props.data?.createBy,
  shipmentDate: props.data?.shipmentDate,
  shipperName: '',
  trackingCode: '',
  deliveryMethod: null,
  paymentMethod: null,
  artt2: props.data?.artt2
});

// 选择库存
const witeFormItemWidth = ref('180px');
type boxSearchType = {
  productName: string | null;
  installationCode: string | null;
  trader: string | null;
  boxCode: string;
  wmsSpec: string | null;
  batchNumber2: string | null;
  createTime: [number, number] | null;
  installationStatus: string | null;
  pageNum: number;
  pageSize: number;
  total: number;
};
const boxSearchForm = ref<boxSearchType>({
  boxCode: '',
  trader: null,
  batchNumber2: null,
  productName: null,
  wmsSpec: null,
  installationStatus: null,
  installationCode: null,
  createTime: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});

function handleSearch() {
  boxSearchForm.value.pageNum = 1;
  BoxInit();
}
function handleReset() {
  boxSearchForm.value = {
    productName: null,
    installationCode: null,
    trader: null,
    boxCode: '',
    wmsSpec: null,
    batchNumber2: null,
    createTime: null,
    installationStatus: 'OUT',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  BoxInit();
}
const Boxdata = ref<DataType[]>([]);
function BoxInit() {
  startLoading();
  Boxdata.value = [];
  getBoxReacprding(boxSearchForm.value).then(res => {
    const boxList: any = [];
    res.rows.forEach((item: any) => {
      if (!item.noticeNumberCode) {
        boxList.push(item);
      }
    });
    Boxdata.value = boxList;
    boxSearchForm.value.total = res.total;
  });
  endLoading();
}
type DataType = {
  boxCode: string;
  boxSerialNumber: string;
  boxPerNumber: number;
  savedQuantity: number;
  itemCode: string;
  productName: string;
  wmsSpec: string;
  batchNumber: string;
  netWeight: number;
  grossWeight: number;
  axleLoad: number;
  createTime: string;
  wmsUnit: string;
  installationStatus: string | null;
  installationCode: string;
  inboundStatus: string;
  createBy: string;
  // itemCode2:string;
  artt3: number;
  artt1: string;
  // recptCode:string;
};
const boxRowKey = (row: any) => row;
const boxColumns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '托编号',
    titleAlign: 'center',
    align: 'center',
    width: 150,
    key: 'installationCode'
  },
  {
    title: '装托状态',
    titleAlign: 'center',
    width: 80,
    key: 'installationStatus',
    render: row => {
      return row.installationStatus === 'IN' ? (
        <n-tag type="success">{'已装托'}</n-tag>
      ) : (
        <n-tag type="error">{'未装托'}</n-tag>
      );
    }
  },
  {
    title: '称重时间',
    titleAlign: 'center',
    width: 180,
    key: 'createTime'
  },
  {
    title: '箱编号',
    titleAlign: 'center',
    align: 'center',
    key: 'boxCode',
    width: 180
  },
  {
    title: '箱流水号',
    width: 120,
    key: 'boxSerialNumber'
  },
  {
    title: '批号',
    width: 80,
    key: 'batchNumber2'
  },
  // {
  //   title: '批次号',
  //   width:80,
  //   key: 'batchNumber'
  // },
  {
    title: '客户简称',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'trader'
  },
  // {
  //   title: '材料厂家',
  //   align: 'center',
  //   width: 80,
  //   key: 'materialNumber'
  // },
  {
    title: '每箱盘数',
    align: 'center',
    width: 80,
    key: 'boxPerNumber'
  },
  {
    title: '箱数',
    width: 80,
    key: 'artt3'
  },
  {
    title: '品名',
    width: 80,
    key: 'productName'
  },
  {
    title: '规格型号',
    width: 80,
    key: 'wmsSpec'
  },
  {
    title: '净重',
    width: 80,
    key: 'netWeight'
  },
  {
    title: '毛重',
    width: 80,
    key: 'grossWeight'
  },
  {
    title: '轴重',
    width: 70,
    key: 'axleLoad'
  },
  {
    title: '物品编码',
    width: 100,
    key: 'itemCode'
  },
  {
    title: '单位',
    width: 60,
    key: 'wmsUnit'
  },
  {
    title: '入库状态',
    titleAlign: 'center',
    width: 80,
    key: 'inboundStatus',
    render: row => {
      return row.inboundStatus === 'IN' ? <n-tag type="success">{'入库'}</n-tag> : <n-tag type="error">{'出库'}</n-tag>;
    }
  },
  {
    title: '仓库',
    width: 80,
    key: 'artt1'
  },
  {
    title: '称重人',
    width: 120,
    key: 'createBy'
  },
  // {
  //   title: '入库单号',
  //   width:120,
  //   key: 'recptCode'
  // },
  // {
  //   title: '物料代码',
  //   titleAlign:"center",
  //   width:80,
  //   key: 'itemCode2'
  // },
  {
    title: '操作',
    titleAlign: 'center',
    fixed: 'right',
    key: 'cz',
    width: 180,
    render() {
      return (
        <div>
          <n-button size="tiny" type="info" quaternary class="mr-5px">
            <n-icon>
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16">
                <g fill="none">
                  <path
                    d="M4 3.5A1.5 1.5 0 0 1 5.5 2h5A1.5 1.5 0 0 1 12 3.5V4h1a2 2 0 0 1 2 2v4.5a1.5 1.5 0 0 1-1.5 1.5h-1.4v.5a1.5 1.5 0 0 1-1.5 1.5H5.5A1.5 1.5 0 0 1 4 12.5V12H2.5A1.5 1.5 0 0 1 1 10.5V6a2 2 0 0 1 2-2h1v-.5zm7 .5v-.5a.5.5 0 0 0-.5-.5h-5a.5.5 0 0 0-.5.5V4h6zM4 5H3a1 1 0 0 0-1 1v4.5a.5.5 0 0 0 .5.5H4v-.5A1.5 1.5 0 0 1 5.5 9h5.1a1.5 1.5 0 0 1 1.5 1.5v.5h1.4a.5.5 0 0 0 .5-.5V6a1 1 0 0 0-1-1H4zm1.5 5a.5.5 0 0 0-.5.5v2a.5.5 0 0 0 .5.5h5.1a.5.5 0 0 0 .5-.5v-2a.5.5 0 0 0-.5-.5H5.5z"
                    fill="currentColor"
                  ></path>
                </g>
              </svg>
            </n-icon>
            打印(箱)
          </n-button>
          <n-button size="tiny" type="error" quaternary class="mr-5px">
            <icon-tdesign-delete class="mr-1px text-15px " />
            删除
          </n-button>
        </div>
      );
    }
  }
]);
const checkedBoxRowKeysRef = ref<(string | number)[]>([]);
const boxChecked = ref<(string | number)[]>([]);
const handleBoxCheck = (rowKeys: (string | number)[]) => {
  boxChecked.value = rowKeys;
};
// 选择托
type storageType = {
  productName: string | null;
  installationCode: string;
  wmsSpec: string | null;
  batchNumber2: string | null;
  shipStatus: string | null;
  pageNum: number;
  pageSize: number;
  total: number;
};
const storageFrom = ref<storageType>({
  installationCode: '',
  batchNumber2: null,
  productName: null,
  wmsSpec: null,
  shipStatus: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function handleStorageSearch() {
  storageFrom.value.pageNum = 1;
  storageInit();
}
function handleStorageReset() {
  storageFrom.value = {
    productName: null,
    installationCode: '',
    wmsSpec: null,
    batchNumber2: null,
    shipStatus: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  storageInit();
}

const storageData = ref<storageDataType[]>([]);
type storageDataType = {
  itemCode: string;
  productName: string;
  wmsSpec: string;
  batchNumber2: string;
  netWeight: number;
  grossWeight: number;
  installationNumber: number;
  createTime: string;
  inboundStatus: string;
  shipStatus: string;
  installationCode: string;
  createBy: string;
  weightUnit: string;
  artt1: string;
};
function storageInit() {
  startLoading();
  storageData.value = [];
  getInstallationList(storageFrom.value).then(res => {
    storageData.value = res.rows;
    storageFrom.value.total = res.total;
  });
  endLoading();
}
const storageRowKey = (row: any) => row;
const storageChecked = ref<(string | number)[]>([]);
const checkedTorrRowKeysRef = ref<Array<string | number>>([]);
const handleStorageCheck = (rowKeys: (string | number)[]) => {
  storageChecked.value = rowKeys;
};
const storageColumns: Ref<DataTableColumns<storageDataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '托编号',
    titleAlign: 'center',
    align: 'center',
    width: 150,
    key: 'installationCode'
  },
  {
    title: '品名',
    width: 80,
    key: 'productName'
  },
  {
    title: '物品编码',
    width: 120,
    key: 'itemCode'
  },
  {
    title: '规格型号',
    width: 80,
    key: 'wmsSpec'
  },
  {
    title: '批号',
    width: 120,
    key: 'batchNumber2'
  },
  {
    title: '已装托总净重',
    width: 120,
    key: 'netWeight',
    render: row => {
      return <span>{`${row.netWeight} kg`}</span>;
    }
  },
  {
    title: '已装托总毛重',
    width: 120,
    key: 'grossWeight',
    render: row => {
      return <span>{`${row.grossWeight} kg`}</span>;
    }
  },
  {
    title: '已装托数量',
    width: 100,
    key: 'installationNumber',
    render: row => {
      return <span>{`${row.installationNumber}箱`}</span>;
    }
  },
  {
    title: '装托时间',
    titleAlign: 'center',
    width: 180,
    key: 'createTime'
  },
  {
    title: '出货状态',
    width: 80,
    key: 'shipStatus',
    render: row => {
      switch (row.shipStatus) {
        case 'IN':
          return <n-tag type="error">{'未出货'}</n-tag>;
        case 'OUT':
          return <n-tag type="success">{'已出货'}</n-tag>;
        case 'SHIP_OUT':
          return <n-tag type="success">{'待出库'}</n-tag>;
        default:
          return <span>{''}</span>;
      }
    }
  },
  {
    title: '仓库',
    width: 80,
    key: 'artt1'
  },
  {
    title: '装托人',
    width: 80,
    key: 'createBy'
  }
]);

function closeStorageDetail() {
  storageDetail.value = false;
  checkedTorrRowKeysRef.value = [];
  storageFrom.value = {
    productName: null,
    installationCode: '',
    wmsSpec: null,
    batchNumber2: null,
    shipStatus: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
}
// 选择托后确认新增
function addTorrNotice() {
  if (storageShow.value) {
    const shipStatus: any = [];
    checkedTorrRowKeysRef.value.forEach((item: any) => {
      shipStatus.push(item.shipStatus);
    });
    if (shipStatus.includes('OUT') || shipStatus.includes('SHIP_OUT')) {
      message.warning('包含的数据中存在已出货或待出库数据！');
      return;
    }
    const checkedData: any = [];
    checkedTorrRowKeysRef.value.forEach((item: any) => {
      checkedData.push(item.installationCode);
    });
    const newDetailData: any = [];
    if (checkedTorrRowKeysRef.value.length <= 0) {
      message.warning('未选择数据！');
      return;
    }
    selectCodeToBox(checkedData).then(res => {
      if (res.code === 200) {
        newDetailData.push(...res.data);
        detailData.value = uniqueByMap(newDetailData.concat(detailData.value));
        const installationCodeList: any = [];
        detailData.value.forEach((item: any) => {
          installationCodeList.push(item.installationCode);
        });
        searchProductInfo.value.artt2 = Array.from(new Set(installationCodeList)).join(',');
        closeStorageDetail();
      }
    });
  } else {
    if (checkedBoxRowKeysRef.value.length <= 0) {
      message.warning('未选择数据！');
      return;
    }
    detailData.value = uniqueByMap([...detailData.value, ...checkedBoxRowKeysRef.value]);
    closeStorageDetail();
  }
}
// 去重
function uniqueByMap(arr) {
  const map = new Map();
  arr.forEach(item => {
    if (!map.has(item.id)) {
      map.set(item.id, item);
    }
  });
  return [...map.values()];
}

// 清除
function clearData() {
  detailData.value = [];
  checkedBoxRowKeysRef.value = [];
  checkedTorrRowKeysRef.value = [];
  searchProductInfo.value.artt2 = '';
}
function closeDetail() {
  emit('cencel-click', false);
}
function addNotice() {
  if (props.data?.showBtns) {
    if (detailData.value.length <= 0) {
      message.warning('未选择产品信息！');
    } else if (props.data?.productTitle === '添加发货通知单') {
      const pName: any = [];
      detailData.value.forEach(item => {
        pName.push(item.productName);
      });
      const detailedInformation = Array.from(new Set(pName)).join(',');
      const detailedQuantity = detailData.value.length > 1 ? detailData.value.length : detailData.value[0].boxNumber;
      const detailedWeight = parseFloat(
        detailData.value.reduce((prevValue, row) => prevValue + row.netWeight, 0).toFixed(4)
      );
      addNoticeLnvoice({
        detailedInformation,
        detailedWeight,
        detailedQuantity,
        ...searchProductInfo.value,
        wmsBoxList: detailData.value
      }).then(res => {
        if (res.code === 200) {
          message.success('新增成功');
          closeDetail();
        }
      });
    } else {
      searchProductInfo.value.documentStatus = 'DRAFT';
      updateNoticeLnvoice({
        ...searchProductInfo.value,
        wmsBoxList: detailData.value
      }).then(res => {
        if (res.code === 200) {
          message.success('修改成功');
          closeDetail();
        }
      });
    }
  } else {
    closeDetail();
  }
}

const storageTitle = ref<string>('');
// 选择库存选择托
function selectStorage(type) {
  storageDetail.value = true;
  if (type) {
    storageTitle.value = '选择托';
    storageShow.value = true;
    storageFrom.value.shipStatus = 'IN';
    storageInit();
  } else {
    storageTitle.value = '选择库存物资';
    storageShow.value = false;
    boxSearchForm.value.installationStatus = 'OUT';
    BoxInit();
  }
}

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
    key: 'wmsSpec',
    ellipsis: {
      tooltip: true
    }
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
    key: 'batchNumber2',
    ellipsis: {
      tooltip: true
    }
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
    // render(row, index) {
    //   if (props.data?.productTitle !== '查看产品信息') {
    //     return h(NInputNumber, {
    //       value: row.boxNumber ? row.boxNumber : 1,
    //       buttonPlacement: 'both',
    //       onUpdateValue(v: number) {
    //         detailData.value[index].boxNumber = v;
    //       }
    //     });
    //   }
    //   return <span>{row.artt4}</span>;
    // }
  },
  {
    title: '发货净重(kg)',
    align: 'center',
    width: 120,
    key: 'netWeight'
    // render(row, index) {
    //   if (props.data?.productTitle !== '查看产品信息') {
    //     return h(NInputNumber, {
    //       value: row.netWeight,
    //       buttonPlacement: 'both',
    //       onUpdateValue(v: number) {
    //         detailData.value[index].netWeight = v;
    //       }
    //     });
    //   }
    //   return <span>{row.netWeight}</span>;
    // }
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
  //   key: 'sellPrice',
  //   render(row, index) {
  //     if(productTitle.value!=='查看产品信息'){
  //       return h(NInputNumber, {
  //         value: row.artt2,
  //         buttonPlacement:"both",
  //         onUpdateValue(v:number) {
  //           if(detailData.value[index].sellPrice) detailData.value[index].sellPrice = v
  //         }
  //       })
  //     }else{
  //       return <span>{row.sellPrice}</span>
  //     }
  //   }
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
    // render(row, index) {
    //   if (props.data?.productTitle !== '查看产品信息') {
    //     return h(NInput, {
    //       value: row.artt2,
    //       placeholder: '请输入备注',
    //       onUpdateValue(v) {
    //         detailData.value[index].artt2 = v;
    //       }
    //     });
    //   }
    //   return <span>{row.artt2}</span>;
    // }
  }
]);
type DetailsDataType = {
  itemCode: string;
  productName: string;
  wmsSpec: string;
  wmsUnit: string;
  batchNumber2: string;
  boxCode: string;
  boxPerNumber: number;
  artt4: number;
  netWeight: number;
  grossWeight: number;
  sellPrice: number;
  totalPrice: number;
  artt2: string;
  boxNumber: number;
};
const summary: DataTableCreateSummary = pageData => {
  return {
    itemCode: {
      value: h('span', '合计')
    },
    boxPerNumber: {
      value: h(
        'span',
        (pageData as unknown as DetailsDataType[]).reduce((prevValue, row) => prevValue + row.boxPerNumber, 0)
      )
    },
    artt4: {
      value: h(
        'span',
        (pageData as unknown as DetailsDataType[]).reduce((prevValue, row) => prevValue + row.artt4, 0)
      )
    },
    netWeight: {
      value: h(
        'span',
        parseFloat(
          (pageData as unknown as DetailsDataType[]).reduce((prevValue, row) => prevValue + row.netWeight, 0).toFixed(4)
        )
      )
    },
    grossWeight: {
      value: h(
        'span',
        parseFloat(
          (pageData as unknown as DetailsDataType[])
            .reduce((prevValue, row) => prevValue + row.grossWeight, 0)
            .toFixed(4)
        )
      )
    },
    sellPrice: {
      value: h(
        'span',
        (pageData as unknown as DetailsDataType[]).reduce((prevValue, row) => prevValue + row.sellPrice, 0)
      )
    },
    totalPrice: {
      value: h(
        'span',
        (pageData as unknown as DetailsDataType[]).reduce((prevValue, row) => prevValue + row.totalPrice, 0)
      )
    }
  };
};

function init(param) {
  startLoading();
  detailData.value = [];
  selectCodeToBox(param.split(',')).then(res => {
    detailData.value = res.data;
  });
  endLoading();
}

function getDataList() {
  getUserList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      saleUserList.value = [];
      res.rows.forEach(item => {
        saleUserList.value.push({ label: item.clientNick, value: item.id });
      });
    }
  });
  list({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        warehouseOptions.value.push({ label: item.warehouseName, value: item.warehouseName });
      });
    }
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
onMounted(() => {
  if (props.data?.documentNumber) {
    searchProductInfo.value.id = props.data?.productTitle === '添加发货通知单' ? '' : props.data?.searchCondition.id;
    searchProductInfo.value.artt2 = props.data?.searchCondition.artt2;
    searchProductInfo.value.trader = props.data?.searchCondition.trader;
    searchProductInfo.value.createBy = props.data?.searchCondition.createBy;
    searchProductInfo.value.deliveryMethod = props.data?.searchCondition.deliveryMethod;
    searchProductInfo.value.paymentMethod = props.data?.searchCondition.paymentMethod;
    searchProductInfo.value.outboundWarehouse = props.data?.searchCondition.outboundWarehouse;
    detailData.value = [];
    getBoxReacprding({
      noticeNumberCode: props.data?.documentNumber,
      pageSize: 999
    }).then(res => {
      detailData.value = res.rows;
    });
  } else if (props.data?.documentNumber === null) {
    init(props.data?.artt2);
  }
  getDataList();
});
</script>

<style scoped>
.n-form.n-form--inline {
  width: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}
.test {
  width: 1200px;
  display: flex;
  flex-direction: column;
}
</style>
