<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="报废时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item label="报废原因">
          <n-input v-model:value="searchForm.remark" type="text" placeholder="请输入报废原因" />
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
            报废申请
          </n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog :show="show" width="800px" :title="'新增报废'" @cancel="cancel" @submit="submit">
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
              <n-form-item-grid-item label="报废时间" path="scrappingDate">
                <n-date-picker
                  v-model:formatted-value="addForm.scrappingDate"
                  type="datetime"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="报废原因" path="remark">
                <n-input
                  v-model:value="addForm.remark"
                  placeholder="请输入报废原因"
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
        <LedgerData
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
import { getMoldScrappingList, addMoldScrappingList } from '~/src/service/api/moid/scrap/index';
import { formatDate } from '~/src/utils/form/rule';
const searchForm = ref<scrap.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  remark: '',
  startDate: null,
  endDate: null
});

const addForm = ref<scrap.addForm>({
  moldId: null,
  nestingMoldId: null,
  scrappingDate: null,
  remark: '',
  moldType: undefined,
  moldLedgerList: [],
  status: 'SCRAPPED'
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
  scrappingDate: createRequiredFormRule('请选择报废时间'),
  remark: createRequiredFormRule('请选择报废原因'),
  moldType: createRequiredFormRule('请选择模具类别')
};

const data = ref<scrap.TableList[]>([
  {
    moldScrappingId: 1,
    moldLedger: '',
    moldId: 1,
    nestingMoldId: 1,
    scrappingDate: '2023-11-08',
    remark: '公差太大',
    status: '',
    moldName: '',
    moldType: '冷接模',
    moldCode: '20230311',
    moldSpec: '出线模',
    moldMaterial: '高金',
    area: '一楼仓库',
    nestingName: 'D3'
  },
  {
    moldScrappingId: 1,
    moldLedger: '',
    moldId: 1,
    nestingMoldId: 1,
    scrappingDate: '2023-11-08',
    remark: '公差太大',
    status: '',
    moldName: '',
    moldType: '拉丝模',
    moldCode: '20230311',
    moldSpec: '出线模',
    moldMaterial: '高金',
    area: '一楼仓库',
    nestingName: 'L3'
  },
  {
    moldScrappingId: 1,
    moldLedger: '',
    moldId: 1,
    nestingMoldId: 1,
    scrappingDate: '2023-11-08',
    remark: '公差太大',
    status: '',
    moldName: '',
    moldType: '镀锡模',
    moldCode: '20230311',
    moldSpec: '进线模',
    moldMaterial: '聚金',
    area: '一楼仓库',
    nestingName: 'T3'
  }
]);

const columns: Ref<DataTableColumns<scrap.TableList>> = ref([
  {
    title: '套模名称',
    key: 'nestingName',
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
    key: 'area',
    align: 'center',
    width: 100
  },
  {
    title: '报废时间',
    key: 'scrappingDate',
    align: 'center',
    width: 100
  },
  {
    title: '报废原因',
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

function addData() {
  show.value = true;
  addForm.value = {
    moldId: null,
    nestingMoldId: null,
    scrappingDate: null,
    remark: '',
    moldType: undefined,
    moldLedgerList: [],
    status: 'SCRAPPED'
  };
  classificationId.value = -1;
  classifyName.value = '';
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
        ledgerListRef.value.checkedRowKeys.forEach(item => {
          addForm.value.moldLedgerList.push({
            moldId: item
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
        nestingViewRef.value.checkedRowKeys.forEach(item => {
          addForm.value.moldLedgerList.push({
            moldId: item,
            nestingMoldId: addForm.value.nestingMoldId
          });
        });
      }
      addComplete.value = false;
      addMoldScrappingList(addForm.value)
        .then(res => {
          addComplete.value = true;
          if (res.code === 200) {
            message.success('报废成功');
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
  getMoldScrappingList(searchForm.value).then(res => {
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
