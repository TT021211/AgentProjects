<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="托编号">
          <n-input v-model:value="searchForm.installationCode" placeholder="请输入托编号"></n-input>
        </n-form-item>
        <n-form-item label="客户简称">
          <n-select
            v-model:value="searchForm.trader"
            :options="saleUserList"
            class="w-180px"
            :filterable="true"
            :clearable="true"
            placeholder="请选择客户简称"
            @update:value="
              (val, item: any) => {
                searchForm.trader = item.label;
              }
            "
          ></n-select>
        </n-form-item>
        <n-form-item label="批号">
          <n-select
            v-model:value="searchForm.batchNumber2"
            placeholder="请选择批号"
            :filterable="true"
            :clearable="true"
            :options="batchNumberOptions"
            :style="{ width: '180px' }"
          ></n-select>
        </n-form-item>
        <n-form-item label="品名">
          <n-select
            v-model:value="searchForm.productName"
            :options="productList"
            class="w-180px"
            placeholder="请选择品名"
            @update:value="
              (val, item: any) => {
                searchForm.productName = item.label;
              }
            "
          ></n-select>
        </n-form-item>
        <n-form-item label="规格型号">
          <n-select
            v-model:value="searchForm.wmsSpec"
            placeholder="请输入规格型号"
            :filterable="true"
            :clearable="true"
            :options="wmsSpecOptions"
            :style="{ width: '180px' }"
          ></n-select>
        </n-form-item>
        <n-form-item label="装托状态">
          <n-select
            v-model:value="searchForm.installationStatus"
            :options="torrStatusOption"
            class="w-180px"
            placeholder="请选择装托状态"
          ></n-select>
        </n-form-item>
      </n-form>
      <n-form inline>
        <n-form-item label="箱编号">
          <n-input v-model:value="searchForm.boxCode" placeholder="请输入箱编码"></n-input>
        </n-form-item>
        <n-form-item label="装箱状态">
          <n-select
            v-model:value="searchForm.artt2"
            :options="boxStatusOption"
            class="w-180px"
            placeholder="请选择装箱状态"
          ></n-select>
        </n-form-item>
        <n-form-item label="称重时间">
          <n-date-picker v-model:value="searchForm.storageTime" type="datetimerange" clearable />
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
    <my-card title="称重记录">
      <template #right>
        <div class="btns">
          <n-button size="tiny" type="info" quaternary class="mr-5px" @click="toWeightAdmin">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增
          </n-button>
          <n-button size="tiny" type="warning" quaternary class="mr-5px">
            <icon-uil:export class="mr-4px text-20px" />
            导出
          </n-button>
          <n-button
            :disabled="!(checkedRowKeysRef.length > 0)"
            size="tiny"
            type="success"
            quaternary
            class="mr-5px"
            @click="printData('', '2')"
          >
            <n-icon size="20">
              <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 16 16">
                <g fill="none">
                  <path
                    d="M4 3.5A1.5 1.5 0 0 1 5.5 2h5A1.5 1.5 0 0 1 12 3.5V4h1a2 2 0 0 1 2 2v4.5a1.5 1.5 0 0 1-1.5 1.5h-1.4v.5a1.5 1.5 0 0 1-1.5 1.5H5.5A1.5 1.5 0 0 1 4 12.5V12H2.5A1.5 1.5 0 0 1 1 10.5V6a2 2 0 0 1 2-2h1v-.5zm7 .5v-.5a.5.5 0 0 0-.5-.5h-5a.5.5 0 0 0-.5.5V4h6zM4 5H3a1 1 0 0 0-1 1v4.5a.5.5 0 0 0 .5.5H4v-.5A1.5 1.5 0 0 1 5.5 9h5.1a1.5 1.5 0 0 1 1.5 1.5v.5h1.4a.5.5 0 0 0 .5-.5V6a1 1 0 0 0-1-1H4zm1.5 5a.5.5 0 0 0-.5.5v2a.5.5 0 0 0 .5.5h5.1a.5.5 0 0 0 .5-.5v-2a.5.5 0 0 0-.5-.5H5.5z"
                    fill="currentColor"
                  ></path>
                </g>
              </svg>
            </n-icon>
            直接打印(箱)
          </n-button>
          <n-button
            :disabled="!(checkedRowKeysRef.length > 0)"
            size="tiny"
            type="success"
            quaternary
            class="mr-5px"
            @click="printData('', '1')"
          >
            <n-icon size="20">
              <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 16 16">
                <g fill="none">
                  <path
                    d="M4 3.5A1.5 1.5 0 0 1 5.5 2h5A1.5 1.5 0 0 1 12 3.5V4h1a2 2 0 0 1 2 2v4.5a1.5 1.5 0 0 1-1.5 1.5h-1.4v.5a1.5 1.5 0 0 1-1.5 1.5H5.5A1.5 1.5 0 0 1 4 12.5V12H2.5A1.5 1.5 0 0 1 1 10.5V6a2 2 0 0 1 2-2h1v-.5zm7 .5v-.5a.5.5 0 0 0-.5-.5h-5a.5.5 0 0 0-.5.5V4h6zM4 5H3a1 1 0 0 0-1 1v4.5a.5.5 0 0 0 .5.5H4v-.5A1.5 1.5 0 0 1 5.5 9h5.1a1.5 1.5 0 0 1 1.5 1.5v.5h1.4a.5.5 0 0 0 .5-.5V6a1 1 0 0 0-1-1H4zm1.5 5a.5.5 0 0 0-.5.5v2a.5.5 0 0 0 .5.5h5.1a.5.5 0 0 0 .5-.5v-2a.5.5 0 0 0-.5-.5H5.5z"
                    fill="currentColor"
                  ></path>
                </g>
              </svg>
            </n-icon>
            直接打印(轴)
          </n-button>
        </div>
        <CxColumns v-model:columns="columns" size="small" />
      </template>
      <n-data-table
        v-model:checked-row-keys="checkedRowKeysRef"
        :data="data"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
        :summary="summary"
        summary-placement="bottom"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <rightTop />
  </div>
</template>

<script setup lang="tsx">
import { ref, onMounted, h } from 'vue';
import type { Ref } from 'vue';
import { useRouter } from 'vue-router';
import type { DataTableColumns, DataTableCreateSummary, SelectOption } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { hiprint } from 'vue-plugin-hiprint';
import { useAuthStore } from '@/store';
import { getUserList } from '@/service/api/sale/userManage';
import { delRecordingByID, getRecoordList } from '@/service/api/storage/storageRecording/index';
import { dataTableConfig } from '@/config/dataTableConfig';
import { useLoading, useWarning } from '~/src/hooks';
import { getAllClassifyList } from '~/src/service/api/md/classify';
import { getPrintTemplateInfo } from '~/src/service/api/md/printTemplate';
import { formatDate } from '~/src/utils/form/rule';
import { getAllProduct } from '~/src/service/api/md/list';
import rightTop from './compents/rightTop.vue';
const authStore = useAuthStore();

const { loading, startLoading, endLoading } = useLoading();

const warning = useWarning('警告', '此操作会删除成品出入库数据并删除同一箱数据记录，是否确认删除？');
const message = useMessage();
const router = useRouter();
const batchNumberOptions = ref();
const wmsSpecOptions = ref<SelectOption[]>([]);
const torrStatusOption = ref([
  { label: '已装托', value: 'IN' },
  { label: '未装托', value: 'OUT' }
]);
const boxStatusOption = ref([
  { label: '已装箱', value: 1 },
  { label: '未装箱', value: 0 }
]);
const saleUserList: any = ref([]);
const productList: any = ref([]);
// 选中的数据
const checked = ref<(string | number)[]>([]);
const checkedRowKeysRef = ref<(string | number)[]>([]);
// 直接打印
function printData(clientId: any, type) {
  if (checkedRowKeysRef.value.length > 0) {
    if (type === '1') {
      checkedRowKeysRef.value.forEach((n: any) => {
        n.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=${n.snidCode}`;
        printList(n, n.batchNumber);
      });
    } else {
      checkedRowKeysRef.value.forEach((n: any) => {
        n.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=${n.snidCode}`;
        printList(n, n.materialManufacturer);
      });
    }
  } else {
    clientId.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=${clientId.snidCode}`;
    if (type === '1') {
      printList(clientId, clientId.batchNumber);
    } else {
      printList(clientId, clientId.materialManufacturer);
    }
  }
}
function printList(clientId, templateId) {
  getPrintTemplateInfo(templateId).then(res => {
    new Promise(resolve => {
      const axishiprintTemplate = new hiprint.PrintTemplate({
        template: JSON.parse(res.data.content)
      });
      resolve(axishiprintTemplate);
    }).then((resl: any) => {
      resl.print2(clientId, {
        printer: JSON.parse(res.data.content).panels[0].livePrint,
        title: 'hiprint测试打印'
      });
    });
  });
}
// 新增称重
function toWeightAdmin() {
  router.push('/storage/weightAdmin');
}
const summary: DataTableCreateSummary = pageData => {
  return {
    boxCode: {
      value: h('span', '合计')
    },
    netWeight: {
      value: h(
        'span',
        parseFloat(
          (pageData as unknown as DataType[]).reduce((prevValue, row) => prevValue + row.netWeight, 0).toFixed(4)
        )
      )
    },
    grossWeight: {
      value: h(
        'span',
        parseFloat(
          (pageData as unknown as DataType[]).reduce((prevValue, row) => prevValue + row.grossWeight, 0).toFixed(4)
        )
      )
    },
    axleLoad: {
      value: h(
        'span',
        parseFloat(
          (pageData as unknown as DataType[]).reduce((prevValue, row) => prevValue + row.axleLoad, 0).toFixed(4)
        )
      )
    }
  };
};
type searchType = {
  productName: string | null;
  installationCode: string | null;
  trader: string | null;
  artt2: number | null;
  boxCode: string;
  wmsSpec: string | null;
  startTime: string | null;
  endTime: string | null;
  batchNumber2: string | null;
  storageTime: [number, number] | null;
  installationStatus: string | null;
  createBy: string | null;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchForm = ref<searchType>({
  boxCode: '',
  trader: null,
  batchNumber2: null,
  productName: null,
  wmsSpec: null,
  artt2: null,
  installationStatus: null,
  installationCode: null,
  storageTime: null,
  startTime: null,
  endTime: null,
  createBy:
    authStore.userInfo.userName !== 'elck' && authStore.userInfo.userName !== 'slck' ? '' : authStore.userInfo.userName,
  pageNum: 1,
  pageSize: 10,
  total: 0
});

function handleSearch() {
  searchForm.value.pageNum = 1;
  if (searchForm.value.storageTime) {
    searchForm.value.startTime = formatDate(new Date(searchForm.value.storageTime[0]), 'yyyy-MM-dd hh-mm-ss');
    searchForm.value.endTime = formatDate(new Date(searchForm.value.storageTime[1]), 'yyyy-MM-dd hh-mm-ss');
  } else {
    searchForm.value.startTime = null;
    searchForm.value.endTime = null;
  }
  init();
}
function handleReset() {
  searchForm.value = {
    productName: null,
    installationCode: null,
    trader: null,
    artt2: null,
    boxCode: '',
    wmsSpec: null,
    batchNumber2: null,
    storageTime: null,
    installationStatus: null,
    createBy:
      authStore.userInfo.userName !== 'slck' && authStore.userInfo.userName !== 'elck'
        ? ''
        : authStore.userInfo.userName,
    startTime: null,
    endTime: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

const data = ref<DataType[]>([]);
type DataType = {
  id: number;
  boxCode: string;
  boxSerialNumber: string;
  quantity: number;
  savedQuantity: number;
  itemCode: string;
  productName: string;
  wmsSpec: string;
  batchNumber: string;
  netWeight: number;
  grossWeight: number;
  axleLoad: number;
  totalNetWeight: number;
  totalGrossWeight: number;
  createTime: string;
  wmsUnit: string;
  inboundStatus: string;
  installationStatus: string;
  installationCode: string;
  axisSerialNumber: string;
  createBy: string;
  remark: string;
  materialCode: string;
  artt1: string;
  artt2: string;
  trader: string;
};

const rowKey = (row: any) => row;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '箱编号',
    titleAlign: 'center',
    align: 'center',
    key: 'boxCode',
    width: 180
  },
  {
    title: '装箱状态',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'artt2',
    render: row => {
      return row.artt2 === '0' ? <n-tag type="error">{'未装箱'}</n-tag> : <n-tag type="success">{'已装箱'}</n-tag>;
    }
  },
  {
    title: '箱流水号',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'boxSerialNumber'
  },
  {
    title: '每箱盘数',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'boxPerNumber'
  },
  {
    title: '盘数',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'artt3'
  },
  {
    title: '物品编码',
    titleAlign: 'center',
    align: 'center',
    width: 180,
    key: 'itemCode'
  },
  {
    title: '品名',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'productName'
  },
  {
    title: '规格型号',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'wmsSpec',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '批号',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'batchNumber2',
    ellipsis: {
      tooltip: true
    }
  },
  // {
  //   title: '批次号',
  //   width: 80,
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
  //   key: 'materialManufacturer'
  // },
  {
    title: '净重',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'netWeight',
    render(row) {
      return <span>{`${row.netWeight} ${row.wmsUnit}`}</span>;
    }
  },
  {
    title: '毛重',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'grossWeight',
    render(row) {
      return <span>{`${row.grossWeight} ${row.wmsUnit}`}</span>;
    }
  },
  {
    title: '轴重',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'axleLoad',
    render(row) {
      return <span>{`${row.axleLoad} ${row.wmsUnit}`}</span>;
    }
  },
  {
    title: '称重时间',
    titleAlign: 'center',
    align: 'center',
    width: 180,
    key: 'createTime'
  },
  {
    title: '单位',
    titleAlign: 'center',
    align: 'center',
    width: 60,
    key: 'wmsUnit'
  },
  {
    title: '入库状态',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'inboundStatus',
    render: row => {
      return row.inboundStatus === 'IN' ? <n-tag type="success">{'入库'}</n-tag> : <n-tag type="error">{'出库'}</n-tag>;
    }
  },
  {
    title: '装托状态',
    titleAlign: 'center',
    align: 'center',
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
    title: '托编号',
    titleAlign: 'center',
    align: 'center',
    width: 150,
    key: 'installationCode'
  },
  {
    title: '轴流水号',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'axisSerialNumber'
  },
  {
    title: '仓库',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'artt1'
  },
  {
    title: '称重人',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'createBy'
  },
  {
    title: '备注',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'remark'
  },
  {
    title: '物料代码',
    titleAlign: 'center',
    align: 'center',
    width: 140,
    key: 'materialCode',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '操作',
    titleAlign: 'center',
    align: 'center',
    fixed: 'right',
    key: 'cz',
    width: 270,
    render(row) {
      return (
        <div>
          <n-button
            onClick={() => {
              printData(row, '2');
            }}
            size="tiny"
            type="info"
            quaternary
            class="mr-5px"
          >
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
          <n-button
            onClick={() => {
              printData(row, '1');
            }}
            size="tiny"
            type="info"
            quaternary
            class="mr-5px"
          >
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
            打印(轴)
          </n-button>
          <n-button
            v-show={row.installationStatus === 'OUT' && row.artt2 === '0'}
            onClick={() => {
              warning.warn(() => {
                delRecordingByID(row.id).then(res => {
                  if (res.code === 200) {
                    message.success('删除成功');
                    init();
                  } else {
                    message.error('删除失败');
                  }
                });
              });
            }}
            size="tiny"
            type="error"
            quaternary
            class="mr-5px"
          >
            <icon-tdesign-delete class="mr-1px text-15px " />
            删除
          </n-button>
        </div>
      );
    }
  }
]);

const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};

// 去重
function uniqueByMap(arr) {
  const map = new Map();
  arr.forEach(item => {
    if (!map.has(item.label)) {
      map.set(item.label, item);
    }
  });
  return [...map.values()];
}
function getWarehouseList() {
  getAllProduct().then(res => {
    batchNumberOptions.value = res.data.map(item => {
      return {
        label: item.batchNumber,
        value: item.batchNumber
      };
    });
    wmsSpecOptions.value = res.data.map(item => {
      return {
        label: item.specification,
        value: item.specification
      };
    });
    wmsSpecOptions.value = uniqueByMap(wmsSpecOptions.value);
  });
  getUserList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      saleUserList.value = [];
      res.rows.forEach(item => {
        saleUserList.value.push({ label: item.clientNick, value: item.id });
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

function init() {
  startLoading();
  data.value = [];
  getRecoordList(searchForm.value).then(res => {
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
.n-checkbox .n-checkbox-box .n-checkbox-box__border {
  border: 1px solid black;
}
</style>
