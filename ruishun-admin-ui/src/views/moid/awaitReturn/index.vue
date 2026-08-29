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
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="生命周期列表">
      <template #right>
        <div>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog :show="show" width="800px" :title="dialogTitle" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModel"
            width="600px"
            label-placement="left"
            label-align="left"
            :label-width="80"
            :model="addForm"
            :rules="rules"
          >
            <n-grid x-gap="12" :cols="2">
              <n-form-item-grid-item label="归还人" path="inBy">
                <n-input v-model:value="addForm.inBy" type="text" placeholder="请输入归还人" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="备注" path="remark">
                <n-input v-model:value="addForm.remark" type="text" placeholder="请输入备注" />
              </n-form-item-grid-item>
            </n-grid>

            <div style="max-height: 350px; overflow: auto">
              <n-data-table
                v-model:checked-row-keys="checkedRowKeys"
                :loading="loading"
                :columns="basicsMessageColumns"
                :data="basicsMessageData"
                :row-key="rowKey"
                @update-checked-row-keys="handleCheck"
              ></n-data-table>
            </div>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'AwaitReturn'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { getMoldReturnedList, confirmAddMoldReturned } from '~/src/service/api/moid/awaitReturn/index';
const searchForm = ref<awaitReturn.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  moldCode: '',
  moldType: ''
});

const addForm = ref<awaitReturn.addForm>({
  moldId: null,
  qbId: null,
  lsId: null,
  remark: '',
  inBy: '',
  nestingMoldId: null,
  moldLedgerList: [],
  moldLedger: {},
  moldReturnedId: null
});

const rules = {
  inBy: createRequiredFormRule('请输入归还人')
};

const dialogTitle = ref<string>('');

const { loading, startLoading, endLoading } = useLoading();

const show = ref<boolean>(false);
const formModel = ref<FormInst | null>(null);
const addComplete = ref<boolean>(true);
const message = useMessage();
const isSubmitComplete = ref<boolean>(true);
const checkedRowKeys = ref<Array<string | number>>([]);
const checked = ref<(string | number)[]>([]);
const rowKey = (row: any) => row.moldId;
const confirmType = ref<number>(0); // 0 : 个， 1 : 套
const data = ref<awaitReturn.TableList[]>([
  {
    moldReturnedId: 1,
    nestingMoldId: 1,
    moldLedger: '',
    createTime: '',
    lsName: 'LS005',
    qbName: '',
    moldOutAperture: '0.001',
    moldOutCode: '20230312',
    moldOutArea: '一楼仓库',
    inBy: '',
    moldId: 1,
    moldLedgerList: '',
    lsId: 1,
    qbId: 1,
    moldNesting: '',
    moldCode: '20230123',
    moldType: '拉丝模',
    moldSpec: '出线模',
    moldArea: '',
    moldName: 'D3',
    moldMaterial: '',
    moldAperture: ''
  },
  {
    moldReturnedId: 1,
    nestingMoldId: 1,
    moldLedger: '',
    createTime: '',
    lsName: 'LS005',
    qbName: '',
    moldOutAperture: '0.001',
    moldOutCode: '20230312',
    moldOutArea: '一楼仓库',
    inBy: '',
    moldId: 1,
    moldLedgerList: '',
    lsId: 1,
    qbId: 1,
    moldNesting: '',
    moldCode: '20230123',
    moldType: '镀锡模',
    moldSpec: '进线模',
    moldArea: '',
    moldName: 'T3',
    moldMaterial: '',
    moldAperture: ''
  }
]);

const columns: Ref<DataTableColumns<awaitReturn.TableList>> = ref([
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
    title: '模具编码',
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
    title: '入库机台',
    key: 'moldType',
    align: 'center',
    width: 100,
    render: row => {
      return <n-space class="space">{row.lsName ? row.lsName : row.qbName}</n-space>;
    }
  },
  {
    title: '出口模编号',
    key: 'moldOutCode',
    align: 'center',
    width: 100
  },
  {
    title: '出口模孔径',
    key: 'moldOutAperture',
    align: 'center',
    width: 100
  },
  {
    title: '存放位置',
    key: 'moldOutArea',
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
              giveBack(row);
            }}
          >
            确认归还
          </n-button>
        </n-space>
      );
    }
  }
]);

const basicsMessageData = ref<basicsMessage.TableList[]>([]);

const basicsMessageColumns: Ref<DataTableColumns<basicsMessage.TableList>> = ref([
  {
    type: 'selection',
    disabled(row) {
      return row.nestingMoldId === null;
    }
  },
  {
    title: '模具类别',
    key: 'moldType',
    align: 'center',
    width: 100
  },
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
    title: '孔径',
    key: 'moldAperture',
    align: 'center',
    width: 100
  },
  {
    title: '数量',
    key: 'moldNum',
    align: 'center',
    width: 100
  },
  {
    title: '型号',
    key: 'moldSpec',
    align: 'center',
    width: 100
  }
]);

const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};

function cancel() {
  show.value = false;
}

function giveBack(row: awaitReturn.TableList) {
  show.value = true;
  checked.value = [];
  checkedRowKeys.value = [];
  basicsMessageData.value = [];
  addForm.value.moldReturnedId = row.moldReturnedId;
  if (row.qbId !== null) {
    addForm.value.qbId = row.qbId;
  } else {
    addForm.value.lsId = row.lsId;
  }
  if (row.nestingMoldId !== null) {
    addForm.value.nestingMoldId = row.nestingMoldId;
    confirmType.value = 1;
    addForm.value.moldLedger = {};
    dialogTitle.value = '套模信息';
    if (row.moldLedgerList !== null) {
      basicsMessageData.value = row.moldLedgerList.filter(item => item.status !== 'CHECKED_IN');
      return;
    }
    basicsMessageData.value = [];
  } else {
    dialogTitle.value = '台账信息';
    addForm.value.moldId = row.moldId;
    addForm.value.nestingMoldId = null;
    confirmType.value = 0;
    if (row.moldLedger !== null) {
      basicsMessageData.value.push(row.moldLedger);
      addForm.value.moldLedger = row.moldLedger;
    }
  }
}

function submit() {
  if (!addComplete.value) return;
  if (!isSubmitComplete.value) return;
  if (checked.value.length === 0 && confirmType.value === 1) {
    message.warning('请勾选列表');
    return;
  }
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      addComplete.value = false;
      if (addForm.value.nestingMoldId !== null) {
        addForm.value.moldLedgerList = basicsMessageData.value.filter(
          item => checked.value.findIndex(ele => ele === item.moldId) > -1
        );
      } else {
        addForm.value.moldLedgerList = null;
      }
      confirmAddMoldReturned(addForm.value)
        .then(res => {
          addComplete.value = true;
          if (res.code === 200) {
            message.success('归还成功');
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

function search() {
  init();
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    moldCode: '',
    moldType: ''
  };
  init();
}

function init() {
  startLoading();
  getMoldReturnedList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
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
:deep(.n-space) {
  justify-content: center !important;
}

:deep(.n-data-table .n-data-table-th .n-data-table-th__title-wrapper) {
  text-align: center;
}
</style>
