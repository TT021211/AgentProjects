<template>
  <div>
    <my-card :title="'称重记录'">
      <template #right>
        <div style="display: flex">
          <n-button type="info" size="small" style="margin-right: 10px" @click="init()">刷新数据</n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data" :row-key="rowKey"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'RawMaterial'
});
defineExpose({
  init
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { hiprint } from 'vue-plugin-hiprint';
import { useLoading, useWarning } from '~/src/hooks';
import { delRecordingByID, getRecoordList } from '~/src/service/api/storage/storageRecording';
const emit = defineEmits(['reloadRecord']);
import { useAuthStore } from '~/src/store';
import { getPrintTemplateInfo } from '~/src/service/api/md/printTemplate';

const authStore = useAuthStore();
const { loading, startLoading, endLoading } = useLoading();
const warning = useWarning('警告', '此操作会删除成品出入库数据并删除同一箱数据记录，是否确认删除？');
const message = useMessage();
// 直接打印
function printData(clientId: any, type) {
  clientId.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=${clientId.snidCode}`;
  if (type === '1') {
    printList(clientId, clientId.batchNumber);
  } else {
    printList(clientId, clientId.materialManufacturer);
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
const searchForm = ref({
  artt2: '0',
  createBy: authStore.userInfo.userName,
  pageNum: 1,
  pageSize: 50,
  total: 0
});

const rowKey = (row: any) => row.id;
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
  boxPerNumber: string;
};
const columns: Ref<DataTableColumns<DataType>> = ref([
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
    align: 'center',
    width: 80,
    key: 'boxSerialNumber'
  },
  {
    title: '每箱盘数',
    align: 'center',
    width: 80,
    key: 'boxPerNumber',
    render: row => {
      return `${data.value.length}/${row.boxPerNumber}`;
    }
  },
  {
    title: '品名',
    align: 'center',
    width: 80,
    key: 'productName'
  },
  {
    title: '规格型号',
    align: 'center',
    width: 80,
    key: 'wmsSpec'
  },
  {
    title: '批号',
    align: 'center',
    width: 80,
    key: 'batchNumber2'
  },
  {
    title: '净重',
    align: 'center',
    width: 80,
    key: 'netWeight'
  },
  {
    title: '毛重',
    align: 'center',
    width: 80,
    key: 'grossWeight'
  },
  {
    title: '轴重',
    align: 'center',
    width: 80,
    key: 'axleLoad'
  },
  {
    title: '单位',
    align: 'center',
    width: 60,
    key: 'wmsUnit'
  },
  {
    title: '操作',
    align: 'center',
    titleAlign: 'center',
    key: 'cz',
    width: 270,
    render(row) {
      return (
        <div style="display:flex">
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
            onClick={() => {
              warning.warn(() => {
                delRecordingByID(row.id).then(res => {
                  if (res.code === 200) {
                    if (data.value.length < 1) {
                      emit('reloadRecord');
                    }
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
});
</script>

<style scoped>
.n-data-table .n-data-table-th.n-data-table-th--fixed-right,
.n-data-table .n-data-table-td.n-data-table-td--fixed-right {
  box-shadow: -5px 2px 10px 0px rgb(198, 196, 196);
}
.active {
  background-color: lightgreen !important;
  color: #fff !important;
}

:deep(.n-input__input-el) {
  color: black !important;
}
</style>
