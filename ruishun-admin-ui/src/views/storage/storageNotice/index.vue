<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="单据编号">
          <n-input v-model:value="searchForm.documentNumber" placeholder="请输入单据编号"></n-input>
        </n-form-item>
        <n-form-item label="来源单据号">
          <n-input v-model:value="searchForm.artt1" placeholder="请输入来源单据号"></n-input>
        </n-form-item>
        <n-form-item label="客户简称">
          <n-input v-model:value="searchForm.trader" placeholder="请输入客户"></n-input>
          <!-- <n-select
            v-model:value="searchForm.trader"
            placeholder="请选择客户简称"
            :filterable="true"
            :clearable="true"
            :options="saleUserList"
            class="w-180px"
            @update:value="
              (val, item: any) => {
                searchForm.trader = item.label;
              }
            "
          ></n-select> -->
        </n-form-item>
        <n-form-item label="状态">
          <n-select
            v-model:value="searchForm.documentStatus"
            :options="documentStatusOpetions"
            class="w-180px"
            placeholder="请选择状态"
          ></n-select>
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
    <my-card title="发货通知记录">
      <template #right>
        <div class="btns">
          <n-button size="tiny" type="info" quaternary class="mr-5px" @click="handleTable('', '2')">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增
          </n-button>
          <n-button
            :disabled="!(checkedRowKeysRef.length > 0)"
            size="tiny"
            type="error"
            quaternary
            class="mr-5px"
            @click="DelNotice"
          >
            <icon-tdesign-delete class="mr-1px text-18px" />
            删除
          </n-button>
          <n-button
            :disabled="!(checkedRowKeysRef.length > 0)"
            size="tiny"
            type="success"
            quaternary
            class="mr-5px"
            @click="showAudit = true"
          >
            <n-icon size="20">
              <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 20 20">
                <g fill="none">
                  <path
                    d="M3.26 11.602C3.942 8.327 6.793 6 10 6c3.206 0 6.057 2.327 6.74 5.602a.5.5 0 0 0 .98-.204C16.943 7.673 13.693 5 10 5c-3.693 0-6.943 2.673-7.72 6.398a.5.5 0 0 0 .98.204zM10 8a3.5 3.5 0 1 0 0 7a3.5 3.5 0 0 0 0-7zm-2.5 3.5a2.5 2.5 0 1 1 5 0a2.5 2.5 0 0 1-5 0z"
                    fill="currentColor"
                  ></path>
                </g>
              </svg>
            </n-icon>
            审核
          </n-button>
          <n-button
            :disabled="!(checkedRowKeysRef.length > 0)"
            size="tiny"
            type="error"
            quaternary
            class="mr-5px"
            @click="(showAudit = true), (auditStatus = '3'), (opinionAudit = '不同意')"
          >
            <n-icon size="20">
              <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 20 20">
                <g fill="none">
                  <path
                    d="M17.5 4a.5.5 0 0 0-.5.5v11a.5.5 0 0 0 1 0v-11a.5.5 0 0 0-.5-.5zM15 10a.5.5 0 0 0-.5-.5H3.707l3.147-3.146a.5.5 0 0 0-.708-.708l-4 4a.498.498 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.707L3.707 10.5H14.5a.5.5 0 0 0 .5-.5z"
                    fill="currentColor"
                  ></path>
                </g>
              </svg>
            </n-icon>
            反审核
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
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="showAudit"
      width="500px"
      title="数据审核"
      :show-cancel="true"
      @cancel="closeAudit"
      @submit="confirmAudit"
    >
      <template #content>
        <div class="test">
          <n-form-item label-placement="left" label="流转动作">
            <n-radio-group v-model:value="auditStatus" name="radiogroup" @change="editOpinion">
              <n-space>
                <n-radio value="1">通过</n-radio>
                <n-radio value="2">退回</n-radio>
                <n-radio value="3">取回(反审核)</n-radio>
              </n-space>
            </n-radio-group>
          </n-form-item>
          <n-form-item label-placement="left" label="流转意见">
            <n-input v-model:value="opinionAudit" type="textarea" placeholder="请输入流转意见"></n-input>
          </n-form-item>
        </div>
      </template>
    </my-dialog>
    <addNoticeDialog v-if="showAddNotice" :data="NoticeData" @cencel-click="cencelClick"></addNoticeDialog>
    <my-dialog
      v-model:show="auditDetail"
      sub-text="关闭"
      width="1100px"
      title="审核明细"
      :show-cancel="false"
      @submit="closeDetail"
    >
      <template #content>
        <div>
          <n-data-table
            class="w-1000px"
            :columns="detailAduitColumns"
            :data="detailAuditData"
            :max-height="dataTableConfig.maxHeight"
            :scroll-x="dataTableConfig.scrollWidth(detailAduitColumns)"
          ></n-data-table>
          <my-pagination
            v-model:search-form="searchAuditRecordForm"
            @init="lookauditRecord(searchAuditRecordForm.noticeInvoiceCode)"
          ></my-pagination>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { watchEffect, ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useMessage, NInput } from 'naive-ui';
import { getlist } from '@/service/api/wms/warehouseSet';
import { dataTableConfig } from '@/config/dataTableConfig';
import { useLoading, useWarning } from '~/src/hooks';
import {
  DelNoticeLnvoice,
  getNoticeLnvoice,
  ConfimopinionAudit,
  retrieveAudit
} from '~/src/service/api/storage/storageNotice';
import { getBoxReacprding, selAuditRecord } from '~/src/service/api/storage/storageBox';
import addNoticeDialog from '~/src/views/storage/storageNotice/component/index.vue';
import { getUserList } from '~/src/service/api/sale/userManage';

const { loading, startLoading, endLoading } = useLoading();
const warning = useWarning();
const message = useMessage();
const opetions = ref<DataType[]>([]);
const saleUserList: any = ref([]);
const documentStatusOpetions = ref([
  { label: '草稿', value: 'DRAFT' },
  { label: '已确认(待审核)', value: 'CONFIRM' },
  { label: '已审核(待出库)', value: 'CHECK' },
  { label: '已出库', value: 'OUT' }
]);

type searchAuditRecordType = {
  noticeInvoiceCode: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchAuditRecordForm = ref<searchAuditRecordType>({
  noticeInvoiceCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

// 选中的数据
const checkedRowKeysRef = ref<Array<string | number>>([]);
// 审核
const showAudit = ref<boolean>(false);
const auditStatus = ref('1');
const opinionAudit = ref('同意');
const status: any = ref([]);
function closeAudit() {
  showAudit.value = false;
  auditStatus.value = '1';
  opinionAudit.value = '同意';
  checkedRowKeysRef.value = [];
  init();
}
function editOpinion() {
  if (auditStatus.value === '1') {
    opinionAudit.value = '同意';
  } else {
    opinionAudit.value = '不同意';
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
// 确认审核
function confirmAudit() {
  status.value = [];
  const backStatus: any = [];
  const retrieveStatus: any = [];
  const dataList = uniqueByMap(JSON.parse(JSON.stringify(checkedRowKeysRef.value)));
  // 是否可以通过或者退回审核
  let isAudit = true;
  dataList.forEach((item: any) => {
    if (auditStatus.value === '1') {
      if (item.documentStatus === 'CHECK') status.value.push(item.documentStatus);
      if (status.value.length > 0) isAudit = false;
    } else if (auditStatus.value === '2') {
      if (item.documentStatus === 'DRAFT' || item.documentStatus === 'CHECK') backStatus.push(item.documentStatus);
      if (backStatus.length > 0) isAudit = false;
    } else {
      if (item.documentStatus !== 'CHECK') retrieveStatus.push(item.documentStatus);
      if (retrieveStatus.length > 0) isAudit = false;
    }
  });
  if (!isAudit) {
    message.warning('存在不可更改状态的数据！');
    return;
  }
  dataList.forEach((item: any) => {
    item.artt5 = opinionAudit.value;
    if (auditStatus.value === '1') {
      switch (item.documentStatus) {
        case 'DRAFT':
          item.documentStatus = 'CONFIRM';
          break;
        case 'CONFIRM':
          item.documentStatus = 'CHECK';
          break;
        default:
          break;
      }
    } else if (auditStatus.value === '2') {
      item.documentStatus = 'CANCEL';
    }
  });
  if (auditStatus.value !== '3') {
    ConfimopinionAudit(dataList).then(res => {
      if (res.code === 200) {
        message.success('更改成功');
        closeAudit();
        init();
      }
    });
  } else {
    retrieveAudit(dataList).then(res => {
      if (res.code === 200) {
        message.success('更改成功');
        closeAudit();
        init();
      }
    });
  }
}
// 搜索条件
type searchType = {
  documentNumber: string;
  documentStatus: string | null;
  trader: string | null;
  artt1: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchForm = ref<searchType>({
  documentNumber: '',
  documentStatus: null,
  trader: null,
  artt1: '',
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
    documentNumber: '',
    documentStatus: null,
    trader: null,
    artt1: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

// 产品信息
const data = ref<DataType[]>([]);
type DataType = {
  id: number;
  documentNumber: string;
  documentType: string;
  trader: string;
  documentStatus: string;
  shipmentDate: string;
  detailedInformation: string;
  detailedQuantity: number;
  detailedWeight: number;
  fromBillCode: string;
  deliveryMethod: string;
  paymentMethod: string;
  wmsBoxList: Array<any>;
};

const detailData = ref<DetailsDataType[]>([]);
const detailColumns: Ref<DataTableColumns<DetailsDataType>> = ref([
  {
    title: '产品(物料)编码',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'itemCode'
  },
  {
    title: '产品(物料)名称',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'productName'
  },
  {
    title: '规格型号',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'wmsSpec'
  },
  {
    title: '单位',
    titleAlign: 'center',
    align: 'center',
    key: 'wmsUnit',
    width: 60
  },
  {
    title: '批号',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'batchNumber2'
  },
  {
    title: '箱编码',
    titleAlign: 'center',
    align: 'center',
    width: 180,
    key: 'boxCode'
  },
  {
    title: '箱内盘数',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'boxPerNumber'
  },
  {
    title: '发货数量',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'artt4'
  },
  {
    title: '发货净重',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'netWeight',
    render(row) {
      return <span>{`${row.netWeight} ${row.wmsUnit}`}</span>;
    }
  },
  {
    title: '发货毛重',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'grossWeight',
    render(row) {
      return <span>{`${row.grossWeight} ${row.wmsUnit}`}</span>;
    }
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
    titleAlign: 'center',
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
  wmsUnit: string;
  netWeight: string;
  grossWeight: string;
  noticeNumberCode: Array<any>;
};

const rowKey = (row: any) => row;
const DetailrowKey = (row: any) => row.id;
const documentNumber = ref('');
watchEffect(() => {
  getBoxReacprd(documentNumber.value);
});
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    type: 'expand',
    renderExpand: row => {
      if (!row.wmsBoxList) {
        documentNumber.value = '';
        documentNumber.value = row.documentNumber;
      }
      return (
        <n-data-table
          row-key={DetailrowKey}
          data={row.wmsBoxList ? row.wmsBoxList : detailData.value}
          columns={detailColumns.value}
        ></n-data-table>
      );
    }
  },
  {
    title: '单据编号',
    titleAlign: 'center',
    align: 'center',
    width: 180,
    key: 'documentNumber'
  },
  {
    title: '单据类型',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'documentType',
    render: row => {
      switch (row.documentType) {
        case '其他':
          return <n-tag type="info">{'其他出库单'}</n-tag>;
        case '销售订单':
          return <n-tag type="info">{'销售出库单'}</n-tag>;
        case '自制产品订单':
          return <n-tag type="info">{'产品出库单'}</n-tag>;
        default:
          return <span>{''}</span>;
      }
    }
  },
  {
    title: '客户简称',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'trader'
  },
  {
    title: '单据状态',
    titleAlign: 'center',
    align: 'center',
    key: 'documentStatus',
    width: 80,
    render: row => {
      switch (row.documentStatus) {
        case 'DRAFT':
          return (
            <n-badge value="1" dot>
              <n-tag
                onClick={() => {
                  lookauditRecord(row.documentNumber);
                }}
                style="background-color:#f3f3f3"
              >
                {'草稿'}
              </n-tag>
            </n-badge>
          );
        case 'CONFIRM':
          return (
            <n-tag
              onClick={() => {
                lookauditRecord(row.documentNumber);
              }}
              type="warning"
            >
              {'已确认(待审核)'}
            </n-tag>
          );
        case 'CHECK':
          return (
            <n-tag
              onClick={() => {
                lookauditRecord(row.documentNumber);
              }}
              type="warning"
            >
              {'已审核(待出库)'}
            </n-tag>
          );
        case 'OUT':
          return (
            <n-tag
              onClick={() => {
                lookauditRecord(row.documentNumber);
              }}
              type="success"
            >
              {'已出库'}
            </n-tag>
          );
        default:
          return <span>{''}</span>;
      }
    }
  },
  {
    title: '发货日期',
    width: 180,
    titleAlign: 'center',
    align: 'center',
    key: 'shipmentDate'
  },
  {
    title: '明细信息',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'detailedInformation',
    // 省略
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '明细数量',
    width: 80,
    titleAlign: 'center',
    align: 'center',
    key: 'detailedQuantity',
    render(row) {
      return <span>{`${row.detailedQuantity} 箱`}</span>;
    }
  },
  {
    title: '明细重量(总净重)',
    titleAlign: 'center',
    align: 'center',
    width: 160,
    key: 'detailedWeight',
    render(row) {
      return <span>{`${row.detailedWeight} kg`}</span>;
    }
  },
  {
    title: '来源单据号',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'artt1'
  },
  {
    title: '仓库',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'outboundWarehouse'
  },
  {
    title: '送货方式',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'deliveryMethod',
    render: row => {
      switch (row.deliveryMethod) {
        case '送货上门':
          return <n-tag type="success">{row.deliveryMethod}</n-tag>;
        case '客户自提':
          return <n-tag type="warning">{row.deliveryMethod}</n-tag>;
        default:
          return <span>{row.deliveryMethod}</span>;
      }
    }
  },
  {
    title: '付款方式',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'paymentMethod'
  },
  {
    title: '操作',
    titleAlign: 'center',
    align: 'center',
    fixed: 'right',
    key: 'cz',
    width: 240,
    render(row) {
      return (
        <div>
          <n-button onClick={() => handleTable(row, '0')} size="tiny" type="info" quaternary class="mr-5px">
            <n-icon size="20">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                <g fill="none">
                  <path
                    d="M3.26 11.602C3.942 8.327 6.793 6 10 6c3.206 0 6.057 2.327 6.74 5.602a.5.5 0 0 0 .98-.204C16.943 7.673 13.693 5 10 5c-3.693 0-6.943 2.673-7.72 6.398a.5.5 0 0 0 .98.204zM10 8a3.5 3.5 0 1 0 0 7a3.5 3.5 0 0 0 0-7zm-2.5 3.5a2.5 2.5 0 1 1 5 0a2.5 2.5 0 0 1-5 0z"
                    fill="currentColor"
                  ></path>
                </g>
              </svg>
            </n-icon>
            查看
          </n-button>
          <span v-show={row.documentStatus === 'DRAFT'}>
            <n-button onClick={() => handleTable(row, '1')} size="tiny" type="info" quaternary class="mr-5px">
              <icon-tdesign-edit class="mr-1px text-18px " />
              修改
            </n-button>
            <n-button
              size="tiny"
              type="info"
              quaternary
              class="mr-5px"
              onClick={() => {
                warning.warn(() => {
                  DelNoticeLnvoice(row.id).then(res => {
                    if (res.code === 200) {
                      message.success('删除成功');
                      init();
                    } else {
                      message.error('删除失败');
                    }
                  });
                });
              }}
            >
              <icon-tdesign-delete class="mr-1px text-15px " />
              删除
            </n-button>
          </span>
        </div>
      );
    }
  }
]);
const auditDetail = ref<boolean>(false);
const detailAduitColumns: Ref<DataTableColumns<AuditDataType>> = ref([
  {
    title: '序号',
    key: 'index',
    titleAlign: 'center',
    align: 'center',
    render(_row, index) {
      return index + 1;
    },
    width: 50
  },
  {
    title: '执行前状态',
    titleAlign: 'center',
    align: 'center',
    key: 'beforeStatus',
    width: 80,
    render: row => {
      switch (row.beforeStatus) {
        case 'DRAFT':
          return <span>{'草稿'}</span>;
        case 'CONFIRM':
          return <span>{'已确认(待审核)'}</span>;
        case 'CHECK':
          return <span>{'已审核(待出库)'}</span>;
        case 'OUT':
          return <span>{'已出库'}</span>;
        default:
          return <span>{''}</span>;
      }
    }
  },
  {
    title: '执行后状态',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'afterStatus',
    render: row => {
      switch (row.afterStatus) {
        case 'DRAFT':
          return <span>{'草稿'}</span>;
        case 'CONFIRM':
          return <span>{'已确认(待审核)'}</span>;
        case 'CHECK':
          return <span>{'已审核(待出库)'}</span>;
        case 'OUT':
          return <span>{'已出库'}</span>;
        default:
          return <span>{''}</span>;
      }
    }
  },

  {
    title: '执行动作',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'action',
    render: row => {
      switch (row.action) {
        case 'DRAFT':
          return <n-tag style="background-color:#f3f3f3">{'草稿'}</n-tag>;
        case 'CONFIRM':
          return <n-tag type="warning">{'已确认(待审核)'}</n-tag>;
        case 'CHECK':
          return <n-tag type="warning">{'已审核(待出库)'}</n-tag>;
        case 'OUT':
          return <n-tag type="success">{'已出库'}</n-tag>;
        case 'CANCEL':
          return <n-tag type="success">{'退回'}</n-tag>;
        case 'RECALL':
          return <n-tag type="success">{'取回'}</n-tag>;
        default:
          return <span>{''}</span>;
      }
    }
  },

  {
    title: '操作人',
    titleAlign: 'center',
    align: 'center',
    key: 'createBy',
    width: 80
  },
  {
    title: '操作时间',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'createTime'
  },
  {
    title: '意见',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'remake'
  }
]);
const detailAuditData = ref<AuditDataType[]>([]);
function closeDetail() {
  auditDetail.value = false;
}
type AuditDataType = {
  beforeStatus: string;
  afterStatus: string;
  action: string;
  createBy: string;
  createTime: string;
  remake: string;
};
function lookauditRecord(documentNumbers) {
  searchAuditRecordForm.value.noticeInvoiceCode = documentNumbers;
  selAuditRecord(searchAuditRecordForm.value).then(res => {
    // let result = Math.max(...res.rows.map(x => x.id)) 获取最大值
    detailAuditData.value = [];
    if (res.code === 200) {
      auditDetail.value = true;
      detailAuditData.value = res.rows;
      searchAuditRecordForm.value.total = res.total;
    }
  });
}
function getBoxReacprd(noticeNumberCode) {
  getBoxReacprding({
    noticeNumberCode,
    pageSize: 999
  }).then(res => {
    data.value.forEach(item => {
      if (item.documentNumber === noticeNumberCode) {
        item.wmsBoxList = res.rows;
      }
    });
  });
}
// 生成发货通知单
const showAddNotice = ref<boolean>(false);
const NoticeData = ref({
  productTitle: '',
  showCancel: true,
  showBtns: true,
  documentType: '其他',
  documentStatus: '草稿',
  createBy: '',
  shipmentDate: new Date(),
  documentNumber: '',
  artt2: '',
  searchCondition: {}
});
// 删除发货通知单
const checked = ref<(string | number)[]>([]);
function DelNotice() {
  warning.warn(() => {
    const ids: any = [];
    checked.value.forEach((item: any) => {
      ids.push(item.id);
    });
    DelNoticeLnvoice(ids.join(',')).then(res => {
      if (res.code === 200) {
        message.success('删除成功');
        init();
      } else {
        message.error('删除失败');
      }
    });
  });
}

function handleTable(row, type) {
  if (type === '0') {
    showAddNotice.value = true;
    NoticeData.value.documentNumber = row.documentNumber;
    NoticeData.value.showBtns = false;
    NoticeData.value.showCancel = false;
    NoticeData.value.searchCondition = row;
    NoticeData.value.productTitle = '查看产品信息';
  } else {
    showAddNotice.value = true;
    NoticeData.value.showBtns = true;
    NoticeData.value.showCancel = true;
    if (type === '1') {
      NoticeData.value.searchCondition = row;
      NoticeData.value.documentNumber = row.documentNumber;
      NoticeData.value.productTitle = '修改产品信息';
    } else {
      NoticeData.value.documentNumber = '';
      NoticeData.value.productTitle = '添加发货通知单';
    }
  }
}

function cencelClick(value) {
  showAddNotice.value = value;
  init();
}

const handleCheck = (rowKeys: (string | number)[]) => {
  rowKeys.forEach((item: any) => {
    getBoxReacprding({
      noticeNumberCode: item.documentNumber,
      pageSize: 999
    }).then(res => {
      item.wmsBoxList = res.rows;
    });
  });
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
  getUserList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      saleUserList.value = [];
      res.rows.forEach(item => {
        saleUserList.value.push({ label: item.clientNick, value: item.id });
      });
    }
  });
}

function init() {
  startLoading();
  data.value = [];
  getNoticeLnvoice(searchForm.value).then(res => {
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
  width: 500px;
  display: flex;
  flex-direction: column;
}
.n-form.n-form--inline {
  width: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
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
.n-checkbox .n-checkbox-box .n-checkbox-box__border {
  border: 1px solid black;
}
</style>
