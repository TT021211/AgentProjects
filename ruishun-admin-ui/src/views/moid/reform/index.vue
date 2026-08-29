<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="改制时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item label="改制原因">
          <n-input v-model:value="searchForm.remark" type="text" placeholder="请输入改制原因" />
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
            改制申请
          </n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog :show="show" width="800px" :title="'新增改制申请'" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModel"
            style="width: 700px"
            label-placement="left"
            label-align="left"
            :label-width="100"
            :model="addForm"
            :rules="rules"
          >
            <n-grid x-gap="12" :cols="2">
              <n-form-item-grid-item label="改制时间" path="restructuringDate">
                <n-date-picker
                  v-model:formatted-value="addForm.restructuringDate"
                  type="datetime"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="改制原因" path="remark">
                <n-input
                  v-model:value="addForm.remark"
                  placeholder="请输入改制原因"
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
                  @update:value="handleUpdateValue"
                ></n-select>
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
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { Search } from '@vicons/ionicons5';
import { createRequiredFormRule } from '@/utils';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { getMoldRestructuringList, addMoldRestructuringList } from '~/src/service/api/moid/reform/index';
import { formatDate } from '~/src/utils/form/rule';
const searchForm = ref<reform.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  remark: '',
  startDate: null,
  endDate: null
});

const addForm = ref<reform.addForm>({
  moldId: null,
  nestingMoldId: null,
  restructuringDate: null,
  remark: '',
  moldType: undefined,
  moldLedgerList: []
});
const classifyName = ref<string>('');
const nestingName = ref<string>('');
const { loading, startLoading, endLoading } = useLoading();
const show = ref<boolean>(false);
const modelShow = ref<boolean>(false);
const ledgerShow = ref<boolean>(false);
const formModel = ref<FormInst | null>(null);
const classificationId = ref<number>(-1);
const message = useMessage();
const addComplete = ref<boolean>(true);
const range = ref<[number, number] | null>(null);
const formItemWidth = ref<string>('220px');
const moldType = ref<{ label: string; value: number }[]>([
  { label: '个', value: 0 },
  { label: '套', value: 1 }
]);
const nestingViewRef = ref();
const ledgerListRef = ref();
const rules = {
  restructuringDate: createRequiredFormRule('请选择改制时间'),
  remark: createRequiredFormRule('请选择改制原因'),
  moldType: createRequiredFormRule('请选择模具类别')
};

const data = ref<reform.TableList[]>([
  {
    moldRestructuringId: 1,
    moldId: 1,
    nestingMoldId: 1,
    restructuringDate: '2023-11-23',
    remark: '公差太大',
    moldLedger: {
      moldType: '拉丝模',
      moldCode: '20230122',
      moldSpec: '出线模',
      moldMaterial: '高金',
      engineeringTolerance: 0.05,
      moldArea: '一楼仓库'
    },
    beforeAperture: 0.278,
    nowAperture: 0.29
  },
  {
    moldRestructuringId: 1,
    moldId: 1,
    nestingMoldId: 1,
    restructuringDate: '2023-10-23',
    remark: '公差太大',
    moldLedger: {
      moldType: '镀锡模',
      moldCode: '20230122',
      moldSpec: '进线模',
      moldMaterial: '高金',
      engineeringTolerance: 0.09,
      moldArea: '一楼仓库'
    },
    beforeAperture: 0.258,
    nowAperture: 0.26
  },
  {
    moldRestructuringId: 1,
    moldId: 1,
    nestingMoldId: 1,
    restructuringDate: '2023-11-30',
    remark: '公差太大',
    moldLedger: {
      moldType: '拉丝模',
      moldCode: '20230122',
      moldSpec: '出线模',
      moldMaterial: '聚金',
      engineeringTolerance: 0.06,
      moldArea: '一楼仓库'
    },
    beforeAperture: 0.145,
    nowAperture: 0.15
  }
]);

const columns: Ref<DataTableColumns<reform.TableList>> = ref([
  {
    title: '模具类别',
    key: 'moldType',
    align: 'center',
    width: 100,
    render: row => {
      return <n-space class="space">{row.moldLedger?.moldType}</n-space>;
    }
  },
  {
    title: '模具编号',
    key: 'moldCode',
    align: 'center',
    width: 100,
    render: row => {
      return <n-space class="space">{row.moldLedger?.moldCode}</n-space>;
    }
  },
  {
    title: '型号',
    key: 'moldSpec',
    align: 'center',
    width: 100,
    render: row => {
      return <n-space class="space">{row.moldLedger?.moldSpec}</n-space>;
    }
  },
  {
    title: '材质',
    key: 'moldMaterial',
    align: 'center',
    width: 100,
    render: row => {
      return <n-space class="space">{row.moldLedger?.moldMaterial}</n-space>;
    }
  },
  {
    title: '改之前孔径',
    key: 'beforeAperture',
    align: 'center',
    width: 100
  },
  {
    title: '改之后孔径',
    key: 'nowAperture',
    align: 'center',
    width: 100
  },
  {
    title: '公差',
    key: 'engineeringTolerance',
    align: 'center',
    width: 100,
    render: row => {
      return <n-space class="space">{row.moldLedger?.engineeringTolerance}</n-space>;
    }
  },
  {
    title: '存放位置',
    key: 'moldArea',
    align: 'center',
    width: 100,
    render: row => {
      return <n-space class="space">{row.moldLedger?.moldArea}</n-space>;
    }
  },
  {
    title: '改制时间',
    key: 'restructuringDate',
    align: 'center',
    width: 100
  },
  {
    title: '改制原因',
    key: 'remark',
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
    remark: '',
    startDate: null,
    endDate: null
  };
  init();
}

function handleUpdateValue(value) {
  if (value === 0) {
    ledgerListRef.value.changeColumns();
    ledgerListRef.value.changeColumnsTolerance();
  } else {
    nestingViewRef.value.changeColumns();
    nestingViewRef.value.changeColumnsTolerance();
  }
}

function addData() {
  show.value = true;
  addForm.value = {
    moldId: null,
    nestingMoldId: null,
    restructuringDate: null,
    remark: '',
    moldType: undefined,
    moldLedgerList: []
  };
  classifyName.value = '';
  nestingName.value = '';
}
function cancel(): void {
  show.value = false;
}

function submit(): void {
  if (!addComplete.value) return;
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      addForm.value.moldLedgerList = [];
      if (addForm.value.moldType === 0) {
        if (classificationId.value === -1) {
          message.warning('请选择模具分类');
          return;
        }
        if (ledgerListRef.value.checkedRowKeys.length === 0) {
          message.warning('请选择台账列表');
          return;
        }
        addForm.value.nestingMoldId = null;
        const list = ledgerListRef.value.getData();
        const index = list.findIndex(item => !item.nowAperture || item.nowAperture < 0 || item.nowAperture === 0);
        if (index > -1) {
          message.warning('改制后孔径不能小于或等于0');
          return;
        }
        const i = list.findIndex(item => !item.updateTolerance);
        if (i > -1) {
          message.warning('请填写公差');
          return;
        }
        list.forEach(item => {
          item.moldAperture = item.nowAperture;
          item.engineeringTolerance = item.updateTolerance;
          addForm.value.moldLedgerList.push({
            moldId: item.moldId,
            moldAperture: item.moldAperture,
            engineeringTolerance: item.engineeringTolerance
          });
        });
      } else {
        if (addForm.value.nestingMoldId === null) {
          message.warning('请选择套模名称');
          return;
        }
        if (nestingViewRef.value.checkedRowKeys.length === 0) {
          message.warning('请选择套模列表');
          return;
        }
        const list = nestingViewRef.value.getData();
        const index = list.findIndex(item => !item.nowAperture || item.nowAperture < 0 || item.nowAperture === 0);
        if (index > -1) {
          message.warning('改制后孔径不能小于或等于0');
          return;
        }
        const i = list.findIndex(item => !item.updateTolerance);
        if (i > -1) {
          message.warning('请填写公差');
          return;
        }
        list.forEach(item => {
          item.moldAperture = item.nowAperture;
          item.engineeringTolerance = item.updateTolerance;
          addForm.value.moldLedgerList.push({
            moldId: item.moldId,
            moldAperture: item.moldAperture,
            nestingMoldId: addForm.value.nestingMoldId,
            engineeringTolerance: item.engineeringTolerance
          });
        });
      }
      addComplete.value = false;
      addMoldRestructuringList(addForm.value)
        .then(res => {
          addComplete.value = true;
          if (res.code === 200) {
            message.success('改制成功');
            init();
            show.value = false;
          }
        })
        .catch(() => {
          addComplete.value = true;
        });
    }
  });
}

function confirm(e) {
  addForm.value.nestingMoldId = e.nestingMoldId;
  modelShow.value = false;
  nestingName.value = e.nestingName;
}

function ledgerSubmit(e) {
  ledgerShow.value = false;
  classifyName.value = `${e.classificationName}+${e.classificationType}+${e.classificationCode}`;
  classificationId.value = e.classificationId;
}

function init() {
  startLoading();
  getMoldRestructuringList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      res.rows.forEach(item => {
        if (item.restructuringDate) {
          item.restructuringDate = formatDate(new Date(item.restructuringDate), 'yyyy-MM-dd hh:mm:ss');
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
  top: 50%;
  transform: translateY(-50%);
  border-left: 1px solid #ddd;
  padding-left: 10px;
  cursor: pointer;
}

:deep(.n-input__input-el) {
  color: #000 !important;
}
</style>
