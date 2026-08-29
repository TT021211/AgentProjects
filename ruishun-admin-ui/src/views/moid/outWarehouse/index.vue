<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="模具编号">
          <n-input v-model:value="searchForm.moldCode" type="text" placeholder="请输入模具编号" />
        </n-form-item>
        <n-form-item label="模具类别">
          <n-input v-model:value="searchForm.moldType" type="text" placeholder="请输入模具编号" />
        </n-form-item>
        <n-form-item label="领用人">
          <n-input v-model:value="searchForm.recipient" type="text" placeholder="请输入领用人" />
        </n-form-item>
        <n-form-item label="出库时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="模具出库列表">
      <template #right>
        <div>
          <n-button size="small" type="info" style="margin-right: 10px" @click="addOutWarehouse">
            <icon-mdi-add />
            新增
          </n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog :show="show" width="800px" :title="'新增出库'" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModel"
            style="width: 700px"
            label-placement="left"
            label-align="left"
            :label-width="80"
            :model="addForm"
            :rules="rules"
          >
            <n-grid x-gap="12" :cols="2">
              <n-form-item-grid-item label="机台类型" path="machineType">
                <n-select
                  v-model:value="addForm.machineType"
                  placeholder="请选择机台类型"
                  class="w-180px"
                  :options="machineType"
                  :style="{ width: formItemWidth }"
                  @update:value="handleUpdateValueMachine"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="出库机台" path="machineId">
                <n-select
                  v-model:value="addForm.machineId"
                  placeholder="请选择出库机台"
                  class="w-180px"
                  :options="machineList"
                  :style="{ width: formItemWidth }"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="领用人" path="recipient">
                <n-input
                  v-model:value="addForm.recipient"
                  placeholder="请输入领用人"
                  :style="{ width: formItemWidth }"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="出库时间" path="moldOutboundTime">
                <n-date-picker
                  v-model:formatted-value="addForm.moldOutboundTime"
                  type="datetime"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="模具类别" path="moldType">
                <n-select
                  v-model:value="addForm.moldType"
                  placeholder="请选择模具类别"
                  class="w-180px"
                  :options="moldType"
                  :style="{ width: formItemWidth }"
                  @update:value="handleUpdateValueMold"
                ></n-select>
              </n-form-item-grid-item>

              <n-form-item-grid-item v-show="addForm.moldType === 0" label="模具分类" path="moldType">
                <div style="position: relative; cursor: pointer" @click="ledgerShow = true">
                  <icon-mdi-search class="icon" />
                  <n-input
                    v-model:value="classifyName"
                    :disabled="true"
                    type="text"
                    show-password-on="click"
                    :maxlength="8"
                    :style="{ width: formItemWidth }"
                  >
                    <n-icon :size="16" :component="Search" />
                  </n-input>
                </div>
              </n-form-item-grid-item>
              <n-form-item-grid-item v-show="addForm.moldType === 1" label="套模名称" path="moldType">
                <div style="position: relative; cursor: pointer" @click="modelShow = true">
                  <icon-mdi-search class="icon" />
                  <n-input
                    v-model:value="nestingName"
                    :disabled="true"
                    type="text"
                    show-password-on="click"
                    :maxlength="8"
                    :style="{ width: formItemWidth }"
                  >
                    <n-icon :component="Search" />
                  </n-input>
                </div>
              </n-form-item-grid-item>

              <n-form-item-grid-item v-show="addForm.moldType === 1" label="出口模">
                <div style="position: relative; cursor: pointer" @click="exitMoldShow = true">
                  <icon-mdi-search class="icon" />
                  <n-input
                    v-model:value="exitMoldName"
                    :disabled="true"
                    type="text"
                    show-password-on="click"
                    :maxlength="8"
                    :style="{ width: formItemWidth }"
                  >
                    <n-icon :component="Search" />
                  </n-input>
                </div>
              </n-form-item-grid-item>
            </n-grid>

            <nestingView
              v-show="addForm.moldType === 1"
              ref="nestingViewRef"
              :nesting-mold-id="addForm.nestingMoldId"
            />
            <ledgerList v-show="addForm.moldType === 0" ref="ledgerListRef" :classification-id="classificationId" />
          </n-form>
        </div>
      </template>
    </my-dialog>

    <n-modal v-model:show="modelShow">
      <n-card style="width: 1100px">
        <CoverData
          @cancel="
            () => {
              modelShow = false;
            }
          "
          @submit="confirm"
        />
      </n-card>
    </n-modal>

    <n-modal v-model:show="ledgerShow">
      <n-card style="width: 1100px">
        <ledgerData
          @submit="ledgerSubmit"
          @cancel="
            () => {
              ledgerShow = false;
            }
          "
        />
      </n-card>
    </n-modal>

    <n-modal v-model:show="exitMoldShow">
      <n-card style="width: 1100px">
        <exitMold
          @submit="exitMoldSubmit"
          @cancel="
            () => {
              exitMoldShow = false;
            }
          "
        />
      </n-card>
    </n-modal>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'OutWarehouse'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { Search } from '@vicons/ionicons5';
import { createRequiredFormRule } from '@/utils';
import { getWorkbenchWiredrawingList } from '@/service/api/md/workbench/wiredrawing';
import { getWorkbenchEnamellingList } from '@/service/api/md/workbench/enamelling';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { formatDate } from '~/src/utils/form/rule';
import {
  getMoldOutboundList,
  addMoldOutboundList,
  confirmMoldOutboundList
} from '~/src/service/api/moid/outWarehouse/index';
import { getMoldNestingList } from '~/src/service/api/moid/drawing/index';
import { getMoidList } from '~/src/service/api/moid/sortManagement/index';
import exitMold from './exitMold/index.vue';
const searchForm = ref<outWarehouse.searchForm>({
  moldCode: '',
  moldType: '',
  recipient: '',
  moldOutboundTime: null,
  startDate: null,
  endDate: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const addForm = ref<outWarehouse.addForm>({
  machineType: undefined,
  moldType: undefined,
  nestingId: null,
  lsId: null,
  recipient: '',
  moldId: null,
  qbId: null,
  machineId: null,
  classificationId: null,
  moldLedgerList: [],
  moldOutboundTime: null,
  nestingMoldId: null,
  moldLedger: null,
  moldNesting: {}
});

const { loading, startLoading, endLoading } = useLoading();
const classifyName = ref<string>('');
const nestingName = ref<string>('');
const show = ref<boolean>(false);
const modelShow = ref<boolean>(false);
const ledgerShow = ref<boolean>(false);
const exitMoldShow = ref<boolean>(false);
const isGetMoidList = ref<boolean>(false);
const isGetdrawingList = ref<boolean>(false);
const isSubmitComplete = ref<boolean>(true);
const exitMoldName = ref<string>('');
const formModel = ref<FormInst | null>(null);
const classificationId = ref<number>(-1);
const message = useMessage();
const range = ref<[number, number] | null>(null);
const formItemWidth = ref<string>('220px');

const machineType = ref<{ label: string; value: number }[]>([
  { label: '拉丝', value: 0 },
  { label: '漆包', value: 1 }
]);
const machineList = ref<{ label: string; value: number }[]>([]);
const wiredrawingList = ref<{ label: string; value: number }[]>([]);
const eamellingList = ref<{ label: string; value: number }[]>([]);
const moldType = ref<{ label: string; value: number }[]>([
  { label: '个', value: 0 },
  { label: '套', value: 1 }
]);
const moldList = ref<{ label: string; value: number }[]>([]);
const drawingList = ref<{ label: string; value: number }[]>([]);
const moidOptions = ref<{ label: string; value: number }[]>([]);
const nestingViewRef = ref();
const ledgerListRef = ref();
const rules = {
  machineType: createRequiredFormRule('请选择机台类型'),
  machineId: createRequiredFormRule('请选择出库机台'),
  moldType: createRequiredFormRule('请选择模具类别'),
  recipient: createRequiredFormRule('请输入领用人'),
  moldOutboundTime: createRequiredFormRule('请选择出库时间')
};

const data = ref<outWarehouse.TableList[]>([
  {
    moldCode: '2023221',
    moldType: '拉丝模',
    moldSpec: '出线模',
    moldMaterial: '高金',
    moldAperture: '0.145',
    moldArea: '一楼仓库',
    moldName: '拉丝模',
    recipient: '****',
    lsName: 'LS37',
    moldOutboundTime: '2023-09-08',
    moldOutboundId: 1,
    nestingId: 1,
    moldId: 1,
    qbId: 1,
    moldLedger: '',
    inOutStatus: ''
  },
  {
    moldCode: '2023221',
    moldType: '镀锡模',
    moldSpec: '立式',
    moldMaterial: '合金',
    moldAperture: '0.145',
    moldArea: '一楼仓库',
    moldName: '拉丝模',
    recipient: '****',
    lsName: 'LS37',
    moldOutboundTime: '2023-09-08',
    moldOutboundId: 1,
    nestingId: 1,
    moldId: 1,
    qbId: 1,
    moldLedger: '',
    inOutStatus: ''
  },
  {
    moldCode: '2023221',
    moldType: '冷接模',
    moldSpec: 'DM-2',
    moldMaterial: '高金',
    moldAperture: '0.145',
    moldArea: '一楼仓库',
    moldName: '拉丝模',
    recipient: '****',
    lsName: 'LS37',
    moldOutboundTime: '2023-09-08',
    moldOutboundId: 1,
    nestingId: 1,
    moldId: 1,
    qbId: 1,
    moldLedger: '',
    inOutStatus: ''
  }
]);

const columns: Ref<DataTableColumns<outWarehouse.TableList>> = ref([
  {
    title: '模具编号',
    key: 'moldCode',
    align: 'center',
    width: 100
  },
  {
    title: '模具类别',
    key: 'moldType',
    align: 'center',
    width: 100
  },
  {
    title: '型号',
    key: 'moldSpec',
    align: 'center',
    width: 100
  },
  {
    title: '材质',
    key: 'moldMaterial',
    align: 'center',
    width: 100
  },
  {
    title: '模具孔径',
    key: 'moldAperture',
    align: 'center',
    width: 100
  },
  {
    title: '存放位置',
    key: 'moldArea',
    align: 'center',
    width: 100
  },
  // {
  //   title: '公差',
  //   key: 'engineeringTolerance',
  //   align: 'center',
  //   width: 100
  // },。5
  {
    title: '套模名称',
    key: 'moldName',
    align: 'center',
    width: 100
  },
  {
    title: '领用人',
    key: 'recipient',
    align: 'center',
    width: 100
  },
  {
    title: '出库机台',
    key: 'lsName',
    align: 'center',
    width: 100
  },
  {
    title: '出库时间',
    key: 'moldOutboundTime',
    align: 'center',
    width: 100
  }
]);

function search() {
  if (range.value !== null) {
    searchForm.value.startDate = formatDate(new Date(range.value[0]), 'yyyy-MM-dd hh:mm:ss');
    searchForm.value.endDate = formatDate(new Date(range.value[1]), 'yyyy-MM-dd hh:mm:ss');
  } else {
    searchForm.value.startDate = null;
    searchForm.value.endDate = null;
  }
  init();
}
function reset() {
  searchForm.value = {
    moldCode: '',
    moldType: '',
    recipient: '',
    moldOutboundTime: null,
    startDate: null,
    endDate: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function getOptionsList(
  func: any,
  list: { label: string; value: number }[],
  params: {
    label: string;
    value: string;
    isEnable?: number;
  }
) {
  func({ pageSize: 999, isEnable: params.isEnable }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        list.push({
          label: item[params.label],
          value: item[params.value]
        });
      });
    }
  });
}

function getList(): void {
  getOptionsList(getWorkbenchWiredrawingList, wiredrawingList.value, {
    label: 'equipmentCode',
    value: 'id',
    isEnable: 1
  });
  getOptionsList(getWorkbenchEnamellingList, eamellingList.value, { label: 'equipmentCode', value: 'id', isEnable: 1 });
  getMoidList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      // moidList.value = res.rows;
      res.rows.forEach(item => {
        moidOptions.value.push({
          label: `${item.classificationName}+${item.classificationType}`,
          value: item.classificationId
        });
      });
    }
  });
}

function handleUpdateValueMachine(value: number): void {
  machineList.value = [];
  addForm.value.machineId = null;
  if (value === 0) {
    machineList.value = wiredrawingList.value;
  } else {
    machineList.value = eamellingList.value;
  }
}

function handleUpdateValueMold(value: number): void {
  if (value === 0) {
    if (isGetMoidList.value) return;
    getMoidList({ pageSize: 999 }).then(res => {
      if (res.code === 200) {
        isGetMoidList.value = true;
        res.rows.forEach(item => {
          moldList.value.push({
            label: `${item.classificationName}+${item.classificationType}+${item.classificationCode}`,
            value: item.classificationId
          });
        });
      } else {
        isGetMoidList.value = false;
      }
    });
  } else {
    if (isGetdrawingList.value) return;
    getMoldNestingList({ pageSize: 999 }).then(res => {
      if (res.code === 200) {
        isGetdrawingList.value = true;
        res.rows.forEach(item => {
          drawingList.value.push({
            label: item.nestingName,
            value: item.nestingMoldId
          });
        });
      } else {
        isGetdrawingList.value = false;
      }
    });
  }
}

function cancel(): void {
  show.value = false;
}

function submit() {
  if (!formModel.value) return;
  formModel.value.validate(async errors => {
    if (!errors) {
      if (!isSubmitComplete.value) return;
      addForm.value.moldLedgerList = [];
      // 判断机台类型
      if (addForm.value.machineType === 0) {
        addForm.value.lsId = addForm.value.machineId;
        addForm.value.qbId = null;
      } else {
        addForm.value.qbId = addForm.value.machineId;
        addForm.value.lsId = null;
      }
      // 判断模具类别
      if (addForm.value.moldType === 0) {
        if (classificationId.value === -1) {
          message.warning('请选择模具分类');
          return;
        }
        if (ledgerListRef.value.checkedRowKeys.length === 0) {
          message.warning('请选择台账列表');
          return;
        }

        addForm.value.nestingId = null;
        addForm.value.moldLedgerList = ledgerListRef.value.getData();
      } else {
        if (addForm.value.moldLedger === null) {
          message.warning('请选择出口模');
          return;
        }
        if (addForm.value.nestingMoldId === null) {
          message.warning('请选择套模名称');
          return;
        }
        if (nestingViewRef.value.checkedRowKeys.length === 0) {
          message.warning('请选择套模列表');
          return;
        }
        addForm.value.moldLedgerList = nestingViewRef.value.getData();
      }
      isSubmitComplete.value = false;
      addMoldOutboundList(addForm.value)
        .then(res => {
          isSubmitComplete.value = true;
          if (res.code === 200) {
            message.success('添加成功');
            confirmMoldOutboundList(addForm.value).then(result => {
              if (result.code) {
                message.success('出库成功');
                show.value = false;
                init();
              }
            });
          }
        })
        .catch(() => {
          isSubmitComplete.value = true;
        });
    }
  });
}

function confirm(e) {
  addForm.value.nestingMoldId = e.nestingMoldId;
  modelShow.value = false;
  nestingName.value = e.nestingName;
  addForm.value.moldNesting = e;
}

function ledgerSubmit(e) {
  ledgerShow.value = false;
  classifyName.value = `${e.classificationName}+${e.classificationType}+${e.classificationCode}`;
  classificationId.value = e.classificationId;
}

function exitMoldSubmit(e) {
  exitMoldName.value = `${e.moldType}+${e.moldSpec}+${e.moldCode}`;
  addForm.value.moldLedger = e;
  exitMoldShow.value = false;
}

function addOutWarehouse() {
  show.value = true;
  addForm.value = {
    machineType: undefined,
    moldType: undefined,
    nestingId: null,
    lsId: null,
    recipient: '',
    moldId: null,
    qbId: null,
    machineId: null,
    classificationId: null,
    moldLedgerList: [],
    moldOutboundTime: null,
    nestingMoldId: null,
    moldLedger: null,
    moldNesting: {}
  };
}

function init() {
  startLoading();
  getMoldOutboundList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      res.rows.forEach(item => {
        if (item.moldOutboundTime) {
          item.moldOutboundTime = formatDate(new Date(item.moldOutboundTime), 'yyyy-MM-dd hh:mm:ss');
        }
      });
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
  getList();
});
</script>

<style scoped>
:deep(.n-space) {
  justify-content: center !important;
}

:deep(.n-data-table .n-data-table-th .n-data-table-th__title-wrapper) {
  text-align: center;
}

.icon {
  position: absolute;
  z-index: 4;
  font-size: 36px;
  right: 10px;
  top: 49%;
  transform: translateY(-50%);
  border-left: 1px solid #ddd;
  padding-left: 10px;
  cursor: pointer;
}

:deep(.n-input__input-el) {
  color: #000 !important;
  padding-right: 40px;
}
</style>
