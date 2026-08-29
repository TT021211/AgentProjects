<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="托编号">
          <n-input v-model:value="searchForm.installationCode" placeholder="请输入托编号"></n-input>
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
            class="w-160px"
            placeholder="请选择品名"
            @update:value="
              (val, item: any) => {
                searchForm.productName = item.label;
              }
            "
          ></n-select>
        </n-form-item>
        <n-form-item label="出货状态">
          <n-select
            v-model:value="searchForm.shipStatus"
            :options="shipStatusOption"
            class="w-160px"
            placeholder="请选择出货状态"
          ></n-select>
        </n-form-item>
        <n-form-item>
          <n-button class="mr-5px" type="primary" @click="handleSearch('1')">
            <icon-tdesign-search class="mr-2px" />
            搜索
          </n-button>
          <n-button @click="handleReset('1')">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="装托记录">
      <template #right>
        <div class="btns">
          <n-button
            size="tiny"
            :disabled="!(checkedBoxRowKeysRef.length > 0)"
            type="info"
            quaternary
            class="mr-5px"
            @click="addNotice"
          >
            <n-icon size="20">
              <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 24 24">
                <g fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M14 3v4a1 1 0 0 0 1 1h4"></path>
                  <path d="M17 21H7a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h7l5 5v11a2 2 0 0 1-2 2z"></path>
                  <path d="M9 17v-5"></path>
                  <path d="M12 17v-1"></path>
                  <path d="M15 17v-3"></path>
                </g>
              </svg>
            </n-icon>
            <!-- 未选中时不能点击 -->
            生成发货通知单
          </n-button>
          <n-button size="tiny" type="warning" quaternary class="mr-5px" @click="addExportTorr()">
            <icon-uil:export class="mr-4px text-20px" />
            导出
          </n-button>
          <n-button
            :disabled="!(checkedBoxRowKeysRef.length === 1)"
            size="tiny"
            type="info"
            quaternary
            class="mr-5px"
            @click="toPrint"
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
            打印
          </n-button>
        </div>
        <CxColumns v-model:columns="columns" size="small" />
      </template>
      <n-data-table
        v-model:checked-row-keys="checkedBoxRowKeysRef"
        :data="data"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init('1')"></my-pagination>
    </my-card>
    <!-- 装托明细 -->
    <my-dialog
      v-model:show="torrDetail"
      sub-text="关闭"
      width="1300px"
      title="已装托明细数据"
      :show-cancel="false"
      @cancel="closeDetail"
      @submit="closeDetail"
    >
      <template #content>
        <div class="test">
          <my-card title="搜索条件" search>
            <n-form inline>
              <n-form-item label="托编号">
                <n-input
                  v-model:value="searchFormDetail.installationCode"
                  :disabled="true"
                  placeholder="请输入托编号"
                ></n-input>
              </n-form-item>

              <n-form-item label="批号">
                <n-input v-model:value="searchFormDetail.batchNumber2" placeholder="请输入批号"></n-input>
              </n-form-item>

              <n-form-item label="品名">
                <n-select
                  v-model:value="searchFormDetail.productName"
                  :options="productList"
                  class="w-180px"
                  placeholder="请选择品名"
                  @update:value="
                    (val, item: any) => {
                      searchFormDetail.productName = item.label;
                    }
                  "
                ></n-select>
              </n-form-item>

              <n-form-item label="规格型号">
                <n-input v-model:value="searchFormDetail.wmsSpec" placeholder="请输入规格型号"></n-input>
              </n-form-item>
              <n-form-item label="装托状态">
                <n-select
                  v-model:value="searchFormDetail.installationStatus"
                  :options="torrStatusOption"
                  class="w-180px"
                  placeholder="请选择装托状态"
                ></n-select>
              </n-form-item>
            </n-form>
            <n-form inline>
              <n-form-item label="箱编号">
                <n-input v-model:value="searchFormDetail.boxCode" placeholder="请输入箱编号"></n-input>
              </n-form-item>
              <n-form-item>
                <n-button class="mr-5px" type="primary" @click="handleSearch('0')">
                  <icon-tdesign-search class="mr-2px" />
                  搜索
                </n-button>
                <n-button @click="handleReset('0')">
                  <icon-ic-round-refresh class="mr-4px text-20px" />
                  重置
                </n-button>
              </n-form-item>
            </n-form>
          </my-card>
          <my-card title="装托箱记录">
            <template #right>
              <div class="btns">
                <n-button
                  v-if="checkedRowKeysRef.length > 0"
                  size="large"
                  type="default"
                  quaternary
                  style="font-weight: bold"
                >
                  {{ lenWeight.toFixed(3) + '  KG' }}
                </n-button>
                <n-button
                  v-if="checkedRowKeysRef.length > 0"
                  size="large"
                  type="default"
                  quaternary
                  style="font-weight: bold"
                >
                  {{ len + '  箱' }}
                </n-button>
                <n-button
                  :disabled="!(checkedRowKeysRef.length > 0)"
                  size="tiny"
                  type="info"
                  quaternary
                  class="mr-5px"
                  @click="addInstallation"
                >
                  <icon-mdi-edit class="mr-1px text-18px" />
                  <!-- 未选中不能点击 -->
                  重新装托
                </n-button>
                <n-button
                  v-show="delToTorr"
                  size="tiny"
                  :disabled="!(checkedRowKeysRef.length > 0)"
                  type="error"
                  quaternary
                  class="mr-5px"
                  @click="removeBoxRecord('')"
                >
                  <icon-tdesign-delete class="mr-1px text-18px" />
                  <!-- 未选中不能点击 -->
                  从托中移除
                </n-button>
                <n-button
                  :disabled="!(checkedRowKeysRef.length > 0)"
                  size="tiny"
                  type="success"
                  quaternary
                  class="mr-5px"
                  @click="printData('')"
                >
                  <n-icon size="20">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      xmlns:xlink="http://www.w3.org/1999/xlink"
                      viewBox="0 0 16 16"
                    >
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
              </div>
              <CxColumns v-model:columns="detailColumns" size="small" />
            </template>
            <n-data-table
              v-model:checked-row-keys="checkedRowKeysRef"
              :loading="detailLoading"
              :columns="detailColumns"
              :row-key="rowKeyDetail"
              :data="detailData"
              :max-height="dataTableConfig.maxHeight"
              :scroll-x="dataTableConfig.scrollWidth(detailColumns)"
              @update-checked-row-keys="handleDetailCheck"
            ></n-data-table>
            <my-pagination v-model:search-form="searchFormDetail" @init="init('0')"></my-pagination>
          </my-card>
        </div>
      </template>
    </my-dialog>
    <addNoticeDialog v-if="showAddNotice" :data="NoticeData" @cencel-click="cencelClick"></addNoticeDialog>

    <my-dialog title="重新装托" width="750px" :show="showReinstall" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="reinstallFromRef"
            :rules="rules"
            :model="reinstallFrom"
            label-placement="left"
            label-width="auto"
          >
            <n-grid :cols="24" :x-gap="20">
              <n-form-item-grid-item label="产品(物料)编码" :span="12">
                <n-input v-model:value="reinstallFrom.itemCode" :disabled="true" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="产品(物料)名称" :span="12">
                <n-input v-model:value="reinstallFrom.productName" :disabled="true" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="规格型号" :span="12">
                <n-input v-model:value="reinstallFrom.wmsSpec" :disabled="true" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="批号" :span="12">
                <n-input v-model:value="reinstallFrom.batchNumber2" :disabled="true" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="总净重(kg)" :span="12">
                <n-input-number v-model:value="reinstallFrom.totalNetWeight" :disabled="true" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="总毛重(kg)" :span="12">
                <n-input-number v-model:value="reinstallFrom.totalGrossWeight" :disabled="true" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="发货数量(箱)" :span="12" path="boxPerNumber">
                <n-input-number v-model:value="reinstallFrom.boxNumber" placeholder="请输入发货数量" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="发货净重(kg)" :span="12" path="netWeight">
                <n-input-number v-model:value="reinstallFrom.netWeight" placeholder="请输入发货净重" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="发货毛重(kg)" :span="24" path="grossWeight">
                <n-input-number v-model:value="reinstallFrom.grossWeight" placeholder="请输入发货毛重" />
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { computed, ref, onMounted } from 'vue';
import { NButton, useMessage } from 'naive-ui';
import type { SelectOption, DataTableColumns, FormInst } from 'naive-ui';
import { hiprint } from 'vue-plugin-hiprint';
import axios from 'axios';
import { useAuthStore } from '@/store';
import { dataTableConfig } from '@/config/dataTableConfig';
import { delTorr, getInstallationList, removeForBoxAndWeighRecord } from '@/service/api/storage/storageTorr/index';
import { addInstallationing, getBoxReacprding } from '@/service/api/storage/storageBox/index';
// import { downloadList } from '@/service/request/helpers';
import { useLoading, useWarning } from '~/src/hooks';
import { getAllClassifyList } from '~/src/service/api/md/classify';
import addNoticeDialog from '~/src/views/storage/storageNotice/component/index.vue';
import { getPrintTemplateList } from '~/src/service/api/md/printTemplate';
import { getUserList } from '~/src/service/api/sale/userManage';
import { serviceEnv } from '~/.env-config';
import { getAllProduct } from '~/src/service/api/md/list';
import { createRequiredFormRule } from '~/src/utils';
const authStore = useAuthStore();

const { loading, startLoading, endLoading } = useLoading();
const warning = useWarning('警告', '此操作会删除成品出入库数据并删除同一箱记录数据，是否确认删除？');
const message = useMessage();
// 选择托
const torrCode = ref<string>('');
const TorrOptions = ref<SelectOption[]>([]);
const batchNumberOptions = ref<SelectOption[]>([]);
const wmsSpecOptions = ref<SelectOption[]>([]);
// 选中的详情数据
const detailChecked = ref<(string | number)[]>([]);
const checkedRowKeysRef = ref<(string | number)[]>([]);
const torrDetail = ref<boolean>(false);
// 勾选总箱数
const len = computed(() => {
  return checkedRowKeysRef.value.length;
});
// 勾选总净重
const lenWeight = computed(() => {
  return checkedRowKeysRef.value.reduce((prev, cur: any) => {
    return prev + cur.netWeight;
  }, 0);
});
// 新增托记录
function addInstallation() {
  if (checkedRowKeysRef.value.length <= 0) {
    message.warning('未选择装托数据！');
    return;
  }
  const obj: any = JSON.parse(JSON.stringify(checkedRowKeysRef.value[0]));
  let list = [];
  list = JSON.parse(JSON.stringify(checkedRowKeysRef.value));
  obj.installationNumber = checkedRowKeysRef.value.length;
  obj.netWeight = parseFloat(list.reduce((prevValue, row: any) => prevValue + row.netWeight, 0).toFixed(4));
  obj.grossWeight = parseFloat(list.reduce((prevValue, row: any) => prevValue + row.grossWeight, 0).toFixed(4));
  obj.installationCode = torrCode.value;
  let wmsBoxes: any = [];
  wmsBoxes = checkedRowKeysRef.value;
  removeForBoxAndWeighRecord(wmsBoxes).then(res => {
    if (res.code === 200) {
      const torrList = checkedRowKeysRef.value.map((n: any) => {
        n.installationStatus = 'OUT';
        n.installationCode = null;
        return n;
      });
      addInstallationing({
        ...obj,
        wmsBoxes: torrList
      }).then(rs => {
        if (rs.code === 200) {
          message.success('装托成功');
          torrDetail.value = false;
          torrCode.value = '';
          checkedRowKeysRef.value = [];
          init('1');
          getWarehouseList();
        }
      });
    }
  });
}
const shipStatusOption = ref([
  { label: '已出货', value: 'OUT' },
  { label: '未出货', value: 'IN' },
  { label: '待出库', value: 'SHIP_OUT' }
]);
const torrStatusOption = ref([
  { label: '已装托', value: 'IN' },
  { label: '未装托', value: 'OUT' }
]);
const productList: any = ref([]);
const saleUserList: any = ref([]);

const data = ref<DataType[]>([]);
// 是否可以移除
const delToTorr = ref<boolean>(false);
// 选中的数据
const checked = ref<(string | number)[]>([]);
const checkedBoxRowKeysRef = ref<(string | number)[]>([]);
// 导出
// const showExport = ref(false);
async function addExportTorr() {
  try {
    const response = await axios.post(`${serviceEnv.prod.url}/wms/wmsInstallation/export`, checkedBoxRowKeysRef.value, {
      responseType: 'blob',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      }
    });
    if (response.status !== 200 || !response.data) {
      throw new Error('服务器响应异常');
    }
    let fileName = '装托表数据.xlsx';
    const disposition = response.headers['content-disposition'];
    if (disposition && disposition.includes('filename=')) {
      fileName = decodeURIComponent(disposition.split('filename=')[1].replace(/"/g, ''));
    }
    const blob = new Blob([response.data], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', fileName);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
    window.$message?.success('文件下载成功');
  } catch (error) {
    window.$message?.error('导出失败，请检查网络或联系管理员');
  }
  // showExport.value = true;
  // downloadList('wms/wmsInstallation/export', checkedBoxRowKeysRef.value)
  //   .then(() => {
  //     showExport.value = false;
  //   })
  //   .catch(() => {
  //     showExport.value = false;
  //   });
}
// 是否可选
const isSelected = ref<boolean>(true);
// 直接打印
function printData(clientId: any) {
  const userId: any = [];
  if (!clientId) {
    saleUserList.value.forEach(item => {
      checkedRowKeysRef.value.forEach((n: any) => {
        if (item.label === n.trader) {
          n.ids = item.value;
          userId.push(n);
        }
      });
    });
    getTemplate(userId);
  } else {
    saleUserList.value.forEach(item => {
      if (item.label === clientId.trader) {
        clientId.ids = item.value;
      }
    });
    getTemplate(clientId);
  }
}
// 获取模板
function getTemplate(clientId: any) {
  if (typeof clientId.ids === 'number') {
    clientId.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=${clientId.snidCode}`;
    getPrintTemplateList({ clientId: clientId.ids, createBy: '1' }).then(res => {
      new Promise(resolve => {
        const boxhiprintTemplate = new hiprint.PrintTemplate({
          template: JSON.parse(res.rows[0].content)
        });
        resolve(boxhiprintTemplate);
      }).then((resl: any) => {
        resl.print2(clientId, {
          printer: JSON.parse(res.rows[0].content).panels[0].livePrint,
          title: 'hiprint测试打印'
        });
      });
    });
  } else {
    clientId.forEach(item => {
      item.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=${item.snidCode}`;
      getPrintTemplateList({ clientId: item.ids, createBy: '1' }).then(res => {
        new Promise(resolve => {
          const boxhiprintTemplate = new hiprint.PrintTemplate({
            template: JSON.parse(res.rows[0].content)
          });
          resolve(boxhiprintTemplate);
        }).then((resl: any) => {
          resl.print2(item, { printer: JSON.parse(res.rows[0].content).panels[0].livePrint, title: 'hiprint测试打印' });
        });
      });
    });
  }
}

// 打印
function toPrint() {
  const installationCode: any = checkedBoxRowKeysRef.value[0];
  const url = `${serviceEnv.prod.reportServerUrl}/jmreport/view/1014404782903066624?installationCode=${installationCode.installationCode}`;
  const userId: any = [];
  saleUserList.value.forEach(item => {
    checkedBoxRowKeysRef.value.forEach((n: any) => {
      if (item.label === n.trader) {
        n.ids = item.value;
        userId.push(n);
      }
    });
  });
  if (userId.length > 0) {
    userId.forEach(item => {
      item.snidCode = `https://dms.idfactory.cn/whois/ui/searchResult?identity=${item.snidCode}`;
      getPrintTemplateList({ clientId: authStore.userInfo.userName === 'slck' ? 3 : 2, createBy: '2' }).then(res => {
        new Promise(resolve => {
          const boxhiprintTemplate = new hiprint.PrintTemplate({
            template: JSON.parse(res.rows[0].content)
          });
          resolve(boxhiprintTemplate);
        }).then((resl: any) => {
          resl.print2(item, { printer: JSON.parse(res.rows[0].content).panels[0].livePrint, title: 'hiprint测试打印' });
        });
      });
    });
  }
  window.open(url, '_blank');
}

// 生成发货通知单
const showNotice = ref<boolean>(true);
const showAddNotice = ref<boolean>(false);
const NoticeData = ref({
  productTitle: '',
  showCancel: true,
  showBtns: true,
  documentType: '其他',
  documentStatus: '草稿',
  createBy: '',
  shipmentDate: new Date(),
  artt2: '',
  documentNumber: null
});

function addNotice() {
  const shipStatus: any = [];
  data.value.forEach(item => {
    checked.value.forEach(code => {
      if (item.installationCode === code) {
        shipStatus.push(item.shipStatus);
      }
    });
  });
  if (shipStatus.includes('OUT') || shipStatus.includes('SHIP_OUT')) {
    message.warning('包含的数据中存在已出货或待出库数据！');
    return;
  }
  NoticeData.value.productTitle = '添加发货通知单';
  NoticeData.value.showCancel = true;
  NoticeData.value.showBtns = true;
  showAddNotice.value = true;
}
function cencelClick(value) {
  showAddNotice.value = value;
  checkedBoxRowKeysRef.value = [];
  init('1');
}
// 装托明细
type searchTypeDateil = {
  productName: string | null;
  boxCode: string;
  installationCode: string;
  wmsSpec: string | null;
  batchNumber2: string | null;
  installationStatus: string | null;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchFormDetail = ref<searchTypeDateil>({
  boxCode: '',
  batchNumber2: null,
  productName: null,
  wmsSpec: null,
  installationCode: '',
  installationStatus: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const rowKeyDetail = (row: any) => row;
const handleDetailCheck = (rowKeys: (string | number)[]) => {
  if (rowKeys.length <= 0) {
    isSelected.value = true;
  } else {
    isSelected.value = false;
  }
  detailChecked.value = rowKeys;
};
const detailData = ref<DetailType[]>([]);
const detailColumns: Ref<DataTableColumns<DetailType>> = ref([
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
    title: '称重时间',
    titleAlign: 'center',
    align: 'center',
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
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'boxSerialNumber'
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
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'boxPerNumber'
  },
  {
    title: '箱数',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'artt3'
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
    title: '物品编码',
    titleAlign: 'center',
    align: 'center',
    width: 180,
    key: 'itemCode'
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
    title: '仓库',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'artt1'
  },
  {
    title: '称重人',
    titleAlign: 'center',
    align: 'center',
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
    align: 'center',
    fixed: 'right',
    key: 'cz',
    width: 220,
    render(row) {
      return (
        <div>
          <n-button
            v-show={delToTorr.value}
            size="tiny"
            onClick={() => removeBoxRecord(row)}
            type="error"
            quaternary
            class="mr-5px"
          >
            <icon-tdesign-delete class="mr-1px text-15px " />
            从托中移除
          </n-button>
          {/* <n-button size="tiny" type="error" quaternary class="mr-5px">
            <icon-tdesign-delete class="mr-1px text-15px " />
            删除
          </n-button> */}
          <n-button
            onClick={() => {
              printData(row);
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
        </div>
      );
    }
  }
]);
type DetailType = {
  boxCode: string;
  boxSerialNumber: string;
  installationNumber: number;
  savedQuantity: number;
  itemCode: string;
  productName: string;
  wmsSpec: string;
  batchNumber2: string;
  netWeight: number;
  grossWeight: number;
  axleLoad: number;
  createTime: string;
  wmsUnit: string;
  installationStatus: string;
  inboundStatus: string;
  installationCode: string;
  createBy: string;
  itemCode2: string;
  artt3: number;
  // recptCode: string;
  artt1: string;
};
// 从托中移除
function removeBoxRecord(row) {
  let wmsBoxes: any = [];
  if (row) {
    wmsBoxes.push(row);
  } else {
    wmsBoxes = checkedRowKeysRef.value;
  }
  removeForBoxAndWeighRecord(wmsBoxes).then(res => {
    if (res.code === 200) {
      message.success('移除成功');
      checkedRowKeysRef.value = [];
      init('0');
    }
  });
}

// 查看明细
const detailLoading = ref<boolean>(false);
function closeDetail() {
  detailLoading.value = true;
  torrDetail.value = false;
  checkedBoxRowKeysRef.value = [];
  checkedRowKeysRef.value = [];
  init('1');
}
function handleEditTable(row) {
  torrDetail.value = true;
  delToTorr.value = row.shipStatus === 'IN';
  detailData.value = [];
  getBoxReacprding({ installationCode: row.installationCode, pageNum: 1, pageSize: 10 }).then(res => {
    detailData.value = res.rows;
    searchFormDetail.value.total = res.total;
    searchFormDetail.value.installationCode = row.installationCode;
    searchFormDetail.value.installationStatus = 'IN';
    detailLoading.value = false;
  });
}

// 装托记录
type searchType = {
  productName: string | null;
  installationCode: string;
  wmsSpec: string | null;
  batchNumber2: string | null;
  shipStatus: string | null;
  createBy: string | null;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchForm = ref<searchType>({
  installationCode: '',
  batchNumber2: null,
  productName: null,
  wmsSpec: null,
  shipStatus: null,
  createBy:
    authStore.userInfo.userName !== 'slck' && authStore.userInfo.userName !== 'elck' ? '' : authStore.userInfo.userName,
  pageNum: 1,
  pageSize: 10,
  total: 0
});

function handleSearch(type) {
  if (type === '1') {
    searchForm.value.pageNum = 1;
  } else {
    searchFormDetail.value.pageNum = 1;
  }
  init(type);
}
function handleReset(type) {
  if (type === '1') {
    searchForm.value = {
      productName: null,
      installationCode: '',
      wmsSpec: null,
      batchNumber2: null,
      shipStatus: null,
      createBy:
        authStore.userInfo.userName !== 'slck' && authStore.userInfo.userName !== 'elck'
          ? ''
          : authStore.userInfo.userName,
      pageNum: 1,
      pageSize: 10,
      total: 0
    };
  } else {
    searchFormDetail.value = {
      installationCode: searchFormDetail.value.installationCode,
      productName: null,
      boxCode: '',
      wmsSpec: null,
      batchNumber2: null,
      installationStatus: 'IN',
      pageNum: 1,
      pageSize: 10,
      total: 0
    };
  }
  init(type);
}
type DataType = {
  id: string;
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
const rowKey = (row: any) => row;
const columns: Ref<DataTableColumns<DataType>> = ref([
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
    align: 'center',
    width: 80,
    key: 'productName'
  },
  {
    title: '物品编码',
    align: 'center',
    width: 150,
    key: 'itemCode'
  },
  {
    title: '规格型号',
    align: 'center',
    width: 80,
    key: 'wmsSpec',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '批号',
    align: 'center',
    width: 120,
    key: 'batchNumber2',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '已装托总净重',
    align: 'center',
    width: 120,
    key: 'netWeight',
    render: row => {
      return <span>{`${row.netWeight} kg`}</span>;
    }
  },
  {
    title: '已装托总毛重',
    align: 'center',
    width: 120,
    key: 'grossWeight',
    render: row => {
      return <span>{`${row.grossWeight} kg`}</span>;
    }
  },
  {
    title: '已装托数量',
    align: 'center',
    width: 100,
    key: 'installationNumber',
    render: row => {
      return <span>{`${row.installationNumber}箱`}</span>;
    }
  },
  {
    title: '装托时间',
    align: 'center',
    titleAlign: 'center',
    width: 180,
    key: 'createTime'
  },
  {
    title: '出货状态',
    width: 80,
    align: 'center',
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
    align: 'center',
    width: 80,
    key: 'artt1'
  },
  {
    title: '装托人',
    align: 'center',
    width: 80,
    key: 'createBy'
  },
  {
    title: '操作',
    titleAlign: 'center',
    align: 'center',
    fixed: 'right',
    width: 270,
    key: 'cz',
    render: row => {
      return (
        <div>
          <n-button
            v-show={row.createBy === 'admin'}
            onClick={() => reinstall(row)}
            size="tiny"
            type="info"
            quaternary
            class="mr-5px"
          >
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
            重新装托
          </n-button>
          <n-button onClick={() => handleEditTable(row)} size="tiny" type="info" quaternary class="mr-5px">
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
            明细
          </n-button>
          <n-button
            onClick={() => {
              warning.warn(() => {
                delTorr(row.id).then(res => {
                  if (res.code === 200) {
                    message.success('删除成功');
                    init('1');
                  } else {
                    message.error('删除失败');
                  }
                });
              });
            }}
            v-show={row.installationNumber <= 0}
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
// 重新装托
const showReinstall = ref(false);
const reinstallFrom = ref({
  installationId: 0,
  artt6: 1, // 是否减数量
  boxNumber: 1, // 箱数
  batchNumber2: '', // 批号
  grossWeight: 0, // 毛重
  installationStatus: '', // 出库状态
  installationCode: null, // 托编码
  itemCode: '', // 产品编码
  netWeight: 0, // 净重
  productName: '', // 产品名称
  shipStatus: '', // 装箱状态
  snidCode: '', // 二级节点
  trader: '', // 客户
  wmsSpec: '', // 规格
  totalGrossWeight: 0, // 总净重
  totalNetWeight: 0, // 总毛重
  wmsBoxes: null
});
const reinstallFromRef = ref<FormInst | null>(null);
const rules = {
  productTypeId: createRequiredFormRule('请选择产品类型'),
  productSize: createRequiredFormRule('请输入规格型号'),
  specification: createRequiredFormRule('请输入规格型号'),
  productName: createRequiredFormRule('请输入产品名称'),
  weightMeasureId: createRequiredFormRule('请输入重量单位'),
  batchNumber: createRequiredFormRule('请输入产品批号'),
  axialType: createRequiredFormRule('请输入产品轴型'),
  coefficient: createRequiredFormRule('请输入产品系数')
};
function cancel() {
  showReinstall.value = false;
  reinstallFrom.value = {
    installationId: 0,
    artt6: 1, // 是否减数量
    boxNumber: 1, // 箱数
    batchNumber2: '', // 批号
    grossWeight: 0, // 毛重
    installationStatus: '', // 出库状态
    installationCode: null, // 托编码
    itemCode: '', // 产品编码
    netWeight: 0, // 净重
    productName: '', // 产品名称
    shipStatus: '', // 装箱状态
    snidCode: '', // 二级节点
    trader: '', // 客户
    wmsSpec: '', // 规格
    totalGrossWeight: 0, // 总净重
    totalNetWeight: 0, // 总毛重
    wmsBoxes: null
  };
}
async function submit() {
  if (!reinstallFromRef.value) return;
  reinstallFromRef.value.validate(async errors => {
    if (!errors) {
      reinstallFrom.value.installationStatus = 'OUT';
      removeForBoxAndWeighRecord([reinstallFrom.value]).then(res => {
        if (res.code === 200) {
          window.$message?.success('装托成功');
          init('1');
          showReinstall.value = false;
        }
      });
    }
  });
}
function reinstall(row) {
  NoticeData.value.productTitle = '添加发货通知单';
  showReinstall.value = true;
  reinstallFrom.value = { ...row };
  reinstallFrom.value.installationId = row.id;
  reinstallFrom.value.artt6 = 1;
  reinstallFrom.value.boxNumber = 1;
  reinstallFrom.value.totalGrossWeight = row.grossWeight;
  reinstallFrom.value.totalNetWeight = row.netWeight;
  reinstallFrom.value.installationCode = row.installationCode;
}
const handleCheck = (rowKeys: (string | number)[]) => {
  NoticeData.value.artt2 = rowKeys.map((n: any) => n.installationCode).join(',');
  if (rowKeys.length <= 0) {
    showNotice.value = true;
  } else {
    showNotice.value = false;
  }
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
  getInstallationList({ pageSize: 999, shipStatus: 'IN' }).then(res => {
    TorrOptions.value = res.rows.map(item => {
      return {
        label: item.installationCode,
        value: item.installationCode,
        pName: item.productName,
        spec: item.wmsSpec,
        num: item.installationNumber
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
function init(type) {
  startLoading();
  if (type === '1') {
    data.value = [];
    getInstallationList(searchForm.value).then(res => {
      data.value = res.rows;
      searchForm.value.total = res.total;
    });
  } else {
    checkedRowKeysRef.value = [];
    detailData.value = [];
    getBoxReacprding(searchFormDetail.value).then(res => {
      detailData.value = res.rows;
      searchFormDetail.value.total = res.total;
    });
  }
  endLoading();
}
onMounted(() => {
  init('1');
  getWarehouseList();
});
</script>
<style scope>
.btns {
  display: flex;
  align-items: center;
}
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
.n-checkbox .n-checkbox-box .n-checkbox-box__border {
  border: 1px solid black;
}
</style>
