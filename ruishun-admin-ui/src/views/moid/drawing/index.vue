<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="套模编号">
          <n-input v-model:value="searchForm.nestingName" type="text" placeholder="请输入套模编号" />
        </n-form-item>
        <n-form-item label="当前在用规格">
          <n-input v-model:value="searchForm.nestingSpec" type="text" placeholder="请输入规格" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="拉丝套模列表">
      <template #right>
        <div>
          <component
            :is="
              useAddBtn(() => {
                addOrEdit(0);
              })
            "
          ></component>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog :show="show" width="900px" :title="title" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModel"
            width="700px"
            label-placement="left"
            label-align="left"
            :label-width="110"
            :model="addForm"
            :rules="rules"
          >
            <n-grid x-gap="12" :cols="2">
              <n-form-item-grid-item label="套模编号" path="nestingName">
                <n-input
                  v-model:value="addForm.nestingName"
                  :style="{ width: formItemWidth }"
                  placeholder="请输入套模编号"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="存放位置" path="nestingArea">
                <n-input
                  v-model:value="addForm.nestingArea"
                  :style="{ width: formItemWidth }"
                  placeholder="请输入存放位置"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="模具台账信息" path="ledgerMsg">
                <div style="position: relative; cursor: pointer" @click="ledgerShow = true">
                  <icon-mdi-search class="icon" />
                  <n-input
                    v-model:value="addForm.ledgerMsg"
                    type="text"
                    show-password-on="click"
                    :maxlength="8"
                    :disabled="true"
                    :style="{ width: formItemWidth }"
                  >
                    <n-icon :component="Search" />
                  </n-input>
                </div>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="是否启用">
                <n-switch v-model:value="addForm.enable" :checked-value="'Y'" :unchecked-value="'N'" />
              </n-form-item-grid-item>
            </n-grid>

            <div style="max-height: 400px; overflow: auto">
              <n-data-table
                v-model:checked-row-keys="checkedRowKeys"
                :columns="basicsMessageColumns"
                :data="basicsMessageData"
                :row-key="rowKey"
                @update:checked-row-keys="handleCheck"
              ></n-data-table>
            </div>
          </n-form>
        </div>
      </template>
    </my-dialog>

    <my-dialog :show="viewShow" width="800px" :title="'详情'" @cancel="viewShow = false" @submit="viewShow = false">
      <template #content>
        <div>
          <n-data-table :loading="loading" :columns="viewColumns" :data="viewData"></n-data-table>
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
import { useSearchBtn, useAddBtn } from '~/src/hooks/common/useBtn';
import { getMoidStatus } from '~/src/utils/common/moidFunc';
import {
  getMoldNestingList,
  addMoldNesting,
  editMoldNesting,
  deleteMoldNesting,
  getMoldNestingView,
  deleteByMoldLedger
} from '~/src/service/api/moid/drawing/index';
import basics from './basics/index.vue';
const searchForm = ref<drawing.searchForm>({
  nestingName: '',
  nestingSpec: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const addForm = ref<drawing.addForm>({
  nestingName: '',
  nestingScope: '',
  nestingArea: '',
  nestingSpec: '',
  enable: 'N',
  moldLedgers: [],
  nestingMoldId: -1,
  ledgerMsg: ''
});

const { loading, startLoading, endLoading } = useLoading();
const title = ref<string>('新增拉丝套模');
const show = ref<boolean>(false);
const viewShow = ref<boolean>(false);
const ledgerShow = ref<boolean>(false);

const type = ref<number>(0); // 0 : 添加， 1 : 编辑

const formModel = ref<FormInst | null>(null);

const message = useMessage();
const dialog = useDialog();
const addOrEditComplete = ref<boolean>(true);
const formItemWidth = ref<string>('240px');
const editBasicsMessageData = ref<basicsMessage.TableList[]>([]);
// const noMoreData = ref<boolean>(false);
const deleteByMoldLedgerComplete = ref<boolean>(true);
const checkedRowKeys = ref<Array<string | number>>([]);
const checked = ref<Array<string | number>>([]);
const editCheckedRowKeys = ref<Array<string | number>>([]);
const rules = {
  nestingName: createRequiredFormRule('请输入套模编号'),
  nestingArea: createRequiredFormRule('请输入存放位置'),
  ledgerMsg: createRequiredFormRule('请选择模具台账信息')
};

const rowKey = (row: any) => row.moldId;

const data = ref<drawing.TableList[]>([
  {
    nestingName: 'D3',
    nestingScope: '0.37-0.66',
    nestingArea: '一楼仓库',
    nestingSpec: '0.145',
    planName: '镀锡',
    enable: 'Y',
    status: '',
    planId: 1,
    moldLedgers: [],
    nestingMoldId: 1
  },
  {
    nestingName: 'L5',
    nestingScope: '0.2-0.5',
    nestingArea: '一楼仓库',
    nestingSpec: '0.25',
    planName: '拉丝',
    enable: 'Y',
    status: '',
    planId: 1,
    moldLedgers: [],
    nestingMoldId: 1
  },
  {
    nestingName: 'T5',
    nestingScope: '0.2-0.5',
    nestingArea: '一楼仓库',
    nestingSpec: '0.25',
    planName: '退火',
    enable: 'Y',
    status: '',
    planId: 1,
    moldLedgers: [],
    nestingMoldId: 1
  }
]);

const columns: Ref<DataTableColumns<drawing.TableList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '套模编号',
    key: 'nestingName',
    align: 'center',
    width: 100
  },
  {
    title: '套模范围',
    key: 'nestingScope',
    align: 'center',
    width: 100
  },
  {
    title: '存放位置',
    key: 'nestingArea',
    align: 'center',
    width: 100
  },
  {
    title: '当前在用规格',
    key: 'nestingSpec',
    align: 'center',
    width: 100
  },
  {
    title: '计划名称',
    key: 'planName',
    align: 'center',
    width: 100
  },
  {
    title: '是否启用',
    key: 'enableFlag',
    align: 'center',
    render: row => {
      return (
        <my-switch
          v-model:value={row.enable}
          checked={'Y'}
          unchecked={'N'}
          onChecked={val => {
            changeStatus(row, val);
          }}
          onUnchecked={val => {
            changeStatus(row, val);
          }}
        ></my-switch>
      );
    }
  },
  {
    title: '操作',
    key: 'actions',
    align: 'center',
    render: row => {
      return (
        <n-space justify={'center'}>
          <n-button
            attr-type="button"
            size={'small'}
            onClick={() => {
              addOrEdit(1, row);
            }}
          >
            <icon-mdi-edit />
            编辑
          </n-button>
          <n-button type="warning" size={'small'} onClick={() => handleDeleteItemType(row.nestingMoldId)}>
            <icon-mdi-delete />
            删除
          </n-button>
          <n-button
            type="info"
            size={'small'}
            onClick={() => {
              viewDetails(row.nestingMoldId, row);
            }}
          >
            查看详情
          </n-button>
        </n-space>
      );
    }
  }
]);

const basicsMessageData = ref<basicsMessage.TableList[]>([]);

const basicsMessageColumns: Ref<DataTableColumns<basicsMessage.TableList>> = ref([
  {
    type: 'selection'
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
    title: '模具孔径',
    key: 'moldAperture',
    align: 'center',
    width: 100
  },
  {
    title: '模具数量',
    key: 'moldNum',
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
    title: '公差',
    key: 'engineeringTolerance',
    align: 'center',
    width: 100
  },
  {
    title: '状态',
    key: 'status',
    align: 'center',
    width: 100,
    render: row => {
      const obj = getMoidStatus(row.status);
      return <n-tag type={obj.statusType}>{obj.text}</n-tag>;
    }
  }
]);

const viewData = ref<drawing.viewData[]>([]);

const viewColumns: Ref<DataTableColumns<drawing.viewData>> = ref([
  {
    title: '序号',
    key: 'key',
    render: (_, index) => {
      return `${index + 1}`;
    }
  },
  {
    title: '套模编号',
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
    title: '模具孔径',
    key: 'moldAperture',
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
    title: '型号',
    key: 'moldSpec',
    align: 'center',
    width: 100
  },
  {
    title: '公差',
    key: 'engineeringTolerance',
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
          <n-popconfirm onPositiveClick={() => deleteMoldLedgerList(row.nestingMoldId, row.moldId)}>
            {{
              default: () => '确认删除',
              trigger: () => (
                <n-button type="warning" size={'small'}>
                  <icon-mdi-delete />
                  删除
                </n-button>
              )
            }}
          </n-popconfirm>
        </n-space>
      );
    }
  }
]);

const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
  if (checked.value.length === 0) {
    addForm.value.ledgerMsg = '';
  } else {
    addForm.value.ledgerMsg = '已选择模具台账信息';
  }
};

function search() {
  init();
}
function reset() {
  searchForm.value = {
    nestingName: '',
    nestingSpec: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function basicsSubmit(e) {
  basicsMessageData.value = e.itemList;
  ledgerShow.value = false;
  addForm.value.ledgerMsg = '已选择模具台账信息';
  checkedRowKeys.value = e.checkedRowKeys;
  if (type.value === 1) {
    basicsMessageData.value = [...editBasicsMessageData.value, ...basicsMessageData.value];
    checkedRowKeys.value = [...editCheckedRowKeys.value, ...checkedRowKeys.value];
  }
  checked.value = checkedRowKeys.value;
}

function addOrEdit(count: number, row?: drawing.TableList): void {
  type.value = count;
  show.value = true;
  basicsMessageData.value = [];
  if (count === 0) {
    addForm.value = {
      nestingName: '',
      nestingScope: '',
      nestingArea: '',
      nestingSpec: '',
      enable: 'N',
      moldLedgers: [],
      nestingMoldId: -1,
      ledgerMsg: ''
    };
    title.value = '新增拉丝套模';
  } else {
    title.value = '编辑拉丝套模';
    for (const key in addForm.value) {
      if (row) {
        addForm.value[key] = row[key];
      }
    }
    if (row !== undefined) {
      editGetLedgerList(row);
    }
  }
}

async function editGetLedgerList(row: drawing.TableList) {
  startLoading();
  const res = await getMoldNestingView({ nestingMoldId: row.nestingMoldId });
  endLoading();
  if (res.code === 200 && res.rows.length > 0) {
    basicsMessageData.value = res.rows;
    editBasicsMessageData.value = res.rows;
    res.rows.forEach(item => {
      checkedRowKeys.value.push(item.moldId);
      checked.value.push(item.moldId);
      editCheckedRowKeys.value.push(item.moldId);
    });
    if (checkedRowKeys.value.length > 0) {
      addForm.value.ledgerMsg = '已选择模具台账信息';
    }
  }
}

function deleteMoldLedgerList(nestingMoldId: number, moidId: number) {
  if (!deleteByMoldLedgerComplete.value) return;
  const element = data.value.find(item => item.nestingMoldId === nestingMoldId);
  if (element && element.moldLedgers) {
    element.moldLedgers = element.moldLedgers?.filter(item => item.moldId === moidId);
    deleteByMoldLedgerComplete.value = false;
    deleteByMoldLedger({ moldLedgers: element.moldLedgers }).then(res => {
      deleteByMoldLedgerComplete.value = true;
      if (res.code === 200) {
        message.success('删除成功');
        viewDetails(element.nestingMoldId, element);
        init();
      }
    });
  }
}

function viewDetails(id: number, row: drawing.TableList) {
  viewShow.value = true;
  viewData.value = [];
  startLoading();
  getMoldNestingView({ nestingMoldId: id }).then(res => {
    if (res.code === 200) {
      viewData.value = res.rows;
      row.moldLedgers = res.rows;
    }
    endLoading();
  });
}

function cancel(): void {
  show.value = false;
}

function submitSame(msg: string, code: number): void {
  show.value = false;
  addOrEditComplete.value = true;
  if (code === 200) {
    message.success(msg);
    init();
  }
}

function submit(): void {
  if (checked.value.length === 0) {
    message.warning('请勾选模具基础信息台账');
    return;
  }
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      if (!addOrEditComplete.value) return;
      addOrEditComplete.value = false;
      addForm.value.moldLedgers = [];
      basicsMessageData.value.forEach(item => {
        const index = checked.value.findIndex(val => val === item.moldId);
        if (index > -1 && addForm.value.moldLedgers !== null) {
          addForm.value.moldLedgers.push(item);
        }
      });
      if (type.value === 0) {
        addMoldNesting(addForm.value)
          .then(res => {
            submitSame('添加成功', res.code);
          })
          .catch(() => {
            addOrEditComplete.value = true;
          });
      } else {
        editMoldNesting(addForm.value)
          .then(res => {
            submitSame('编辑成功', res.code);
          })
          .catch(() => {
            addOrEditComplete.value = true;
          });
      }
    }
  });
}

const sleep = id =>
  new Promise(resolve => {
    deleteMoldNesting(id).then(res => {
      resolve(res);
    });
  });

function handleDeleteItemType(id: number): void {
  const d = dialog.warning({
    title: '删除',
    content: '确认要删除吗?',
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        sleep(id).then((res: any) => {
          if (res.code === 200) {
            message.success('删除成功');
            init();
            resolve(res);
          }
        });
      });
    }
  });
}

async function changeStatus(row: drawing.TableList, val: string): Promise<void> {
  row.enable = val;
  const msg = row.enable === 'Y' ? '启用成功' : '已关闭启用';
  const res = await getMoldNestingView({ nestingMoldId: row.nestingMoldId });
  if (res.code === 200 && row.moldLedgers === null && res.rows) {
    row.moldLedgers = res.rows;
  }
  const result = await editMoldNesting(row);
  if (result.code === 200) {
    message.success(msg);
  }
}

function init() {
  startLoading();
  getMoldNestingList(searchForm.value).then(res => {
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
