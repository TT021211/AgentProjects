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
        <n-form-item label="日期">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="">
      <template #right>
        <div>
          <n-button size="small" type="info" style="margin-right: 10px" @click="addData">
            <icon-mdi-add />
            新增
          </n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog :show="show" width="800px" :title="'新增维修保养'" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModel"
            style="width: 700px"
            label-placement="left"
            label-align="left"
            :label-width="110"
            :model="addForm"
            :rules="rules"
          >
            <n-grid x-gap="12" :cols="2">
              <n-form-item-grid-item label="维修保养" path="status">
                <n-select
                  v-model:value="addForm.status"
                  placeholder="请选择维修保养"
                  :options="maintenanceList"
                  :style="{ width: formItemWidth }"
                ></n-select>
              </n-form-item-grid-item>

              <n-form-item-grid-item label="模具台账信息" path="ledgerMsg">
                <div style="position: relative; cursor: pointer" @click="ledgerShow = true">
                  <icon-mdi-search class="icon" />
                  <n-input
                    v-model:value="addForm.ledgerMsg"
                    type="text"
                    show-password-on="click"
                    :maxlength="8"
                    :style="{ width: formItemWidth }"
                    :disabled="true"
                  >
                    <n-icon :component="Search" />
                  </n-input>
                </div>
              </n-form-item-grid-item>

              <!-- <my-card title="模具台账基础信息" style="max-height: 401px; overflow: auto;">
                <n-data-table :columns="ledgeColumns" :data="ledgerData"></n-data-table>
              </my-card> -->
            </n-grid>
            <div style="max-height: 350px; overflow: auto">
              <my-card title="模具台账信息">
                <n-data-table :loading="loading" :columns="ledgeColumns" :data="ledgerData"></n-data-table>
              </my-card>
            </div>
          </n-form>
        </div>
      </template>
    </my-dialog>

    <n-modal v-model:show="ledgerShow">
      <n-card style="width: 1100px">
        <basics
          @submit="basicsSubmit"
          @cancel="
            () => {
              ledgerShow = false;
            }
          "
        />
      </n-card>
    </n-modal>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage, useDialog } from 'naive-ui';
import { Search } from '@vicons/ionicons5';
import { createRequiredFormRule } from '@/utils';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { formatDate } from '~/src/utils/form/rule';
import {
  getMoldMaintenanceList,
  addMoldmoldMaintenance,
  moldMaintenanceWarehousing
} from '~/src/service/api/moid/maintenance/index';
import basics from './basics/index.vue';

const searchForm = ref<maintenance.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  moldCode: '',
  moldType: '',
  startDate: null,
  endDate: null
});

const addForm = ref<maintenance.addForm>({
  moldLedgerList: [],
  status: null,
  ledgerMsg: ''
});

const { loading, startLoading, endLoading } = useLoading();
const dialog = useDialog();
const show = ref<boolean>(false);
const ledgerShow = ref<boolean>(false);

const formModel = ref<FormInst | null>(null);
const addComplete = ref<boolean>(true);
const message = useMessage();
const formItemWidth = ref<string>('260px');
const range = ref<[number, number] | null>(null);
const maintenanceList = ref<{ label: string; value: string }[]>([
  { label: '维修', value: 'UNDER_REPAIR' },
  { label: '保养', value: 'MAINTENANCE' }
]);

const rules = {
  status: createRequiredFormRule('请选择维修保养'),
  ledgerMsg: createRequiredFormRule('请选择模具台账信息')
};

const data = ref<maintenance.TableList[]>([
  {
    id: 1,
    moldId: 1,
    status: '维修',
    createTime: '2023-11-23',
    moldLedger: {},
    moldLedgerList: {},
    moldCode: '20231123',
    moldType: '拉丝模',
    moldSpec: '出线模',
    moldArea: '一楼仓库',
    moldName: 'D3',
    moldMaterial: '高金',
    moldAperture: ''
  },
  {
    id: 1,
    moldId: 1,
    status: '维修',
    createTime: '2023-10-23',
    moldLedger: {},
    moldLedgerList: {},
    moldCode: '20231023',
    moldType: '拉丝模',
    moldSpec: '出线模',
    moldArea: '一楼仓库',
    moldName: 'L3',
    moldMaterial: '高金',
    moldAperture: ''
  },
  {
    id: 1,
    moldId: 1,
    status: '保养',
    createTime: '2023-11-23',
    moldLedger: {},
    moldLedgerList: {},
    moldCode: '20231123',
    moldType: '镀锡模',
    moldSpec: '进线模',
    moldArea: '一楼仓库',
    moldName: 'T3',
    moldMaterial: '聚金',
    moldAperture: ''
  }
]);

const columns: Ref<DataTableColumns<maintenance.TableList>> = ref([
  {
    title: '套模名称',
    key: 'moldName',
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
    title: '模具编号',
    key: 'moldCode',
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
    title: '存放位置',
    key: 'moldArea',
    align: 'center',
    width: 100
  },
  {
    title: '维修保养',
    key: 'status',
    align: 'center',
    width: 100,
    render: row => {
      // const obj = getMoidStatus(row.status);
      return <n-tag>{row.status}</n-tag>;
    }
  },
  {
    title: '日期',
    key: 'createTime',
    align: 'center',
    width: 100
  },
  {
    title: '操作',
    key: 'actions',
    align: 'center',
    render: row => {
      return (
        <n-space justify={'center'}>
          <n-button
            type="info"
            size={'small'}
            onClick={() => {
              handleConfirm(row);
            }}
          >
            入库
          </n-button>
        </n-space>
      );
    }
  }
]);

const ledgerData = ref<basicsMessage.TableList[]>([]);

const ledgeColumns: Ref<DataTableColumns<basicsMessage.TableList>> = ref([
  {
    title: '套模名称',
    key: 'moldName',
    align: 'center',
    width: 100
  },
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
  {
    title: '状态',
    key: 'status',
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
    pageNum: 1,
    pageSize: 10,
    total: 0,
    moldCode: '',
    moldType: '',
    startDate: null,
    endDate: null
  };
  init();
}

const sleep = row =>
  new Promise(resolve => {
    moldMaintenanceWarehousing(row).then(res => {
      resolve(res);
    });
  });

function addData() {
  show.value = true;
  addForm.value = {
    moldLedgerList: [],
    status: null,
    ledgerMsg: ''
  };
  ledgerData.value = [];
}

function basicsSubmit(e) {
  addForm.value.ledgerMsg = '已选择台账信息';
  ledgerData.value = e;
  ledgerShow.value = false;
}

function handleConfirm(row: maintenance.TableList) {
  const d = dialog.warning({
    title: '提示',
    content: '你确定要入库吗？',
    positiveText: '确定',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise<void>(resolve => {
        sleep({ moldLedger: row, id: row.id }).then((res: any) => {
          if (res.code === 200) {
            message.success('入库成功');
            init();
          }
          return resolve();
        });
      });
    }
  });
}

function cancel(): void {
  show.value = false;
}

function submit(): void {
  if (!addComplete.value) return;
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      addForm.value.moldLedgerList = ledgerData.value;
      addComplete.value = false;
      addMoldmoldMaintenance(addForm.value)
        .then(res => {
          addComplete.value = true;
          if (res.code === 200) {
            message.success('新增成功');
            show.value = false;
            init();
          }
        })
        .catch(() => {
          addComplete.value = true;
        });
    }
  });
}

function init() {
  startLoading();
  getMoldMaintenanceList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      res.rows.forEach(item => {
        if (item.createTime) {
          item.createTime = formatDate(new Date(item.createTime), 'yyyy-MM-dd hh:mm:ss');
        }
      });
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped>
.icon {
  position: absolute;
  z-index: 4;
  font-size: 36px;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  border-left: 1px solid #ddd;
  padding-left: 10px;
  cursor: pointer;
}

:deep(.n-input__input-el) {
  color: #000 !important;
  padding-right: 40px;
}

:deep(.n-space) {
  justify-content: center !important;
}

:deep(.n-data-table .n-data-table-th .n-data-table-th__title-wrapper) {
  text-align: center;
}
</style>
