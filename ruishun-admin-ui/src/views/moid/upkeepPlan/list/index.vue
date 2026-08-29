<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="套模编号">
          <n-input v-model:value="searchForm.nestingName" type="text" placeholder="请输入套模编号" />
        </n-form-item>
        <n-form-item label="计划名称">
          <n-input v-model:value="searchForm.maintainPlanName" type="text" placeholder="请输入计划名称" />
        </n-form-item>
        <n-form-item label="保养类型">
          <n-input v-model:value="searchForm.maintainPlanType" type="text" placeholder="请输入保养类型" />
        </n-form-item>
        <n-form-item label="保养时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="待保养列表">
      <template #right>
        <div>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog :show="show" width="700px" :title="'保养'" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModelRef"
            style="width: 600px"
            label-placement="left"
            :label-width="100"
            :model="addForm"
            :rules="rules"
          >
            <n-grid :x-gap="20" :cols="2">
              <n-form-item-grid-item label="套模编号: ">
                <n-space>{{ formModel.nestingName }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="套模范围: ">
                <n-space>{{ formModel.nestingScope }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="存放位置: ">
                <n-space>{{ formModel.nestingArea }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="计划名称: ">
                <n-space>{{ formModel.maintainPlanName }}</n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="模具供应商" path="clientId">
                <n-select
                  v-model:value="addForm.clientId"
                  placeholder="请选择模具供应商"
                  class="w-180px"
                  :options="supplierList"
                  @update:value="handleUpdateValue"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="保养类型: ">
                <!-- <n-space>{{ formModel.maintainPlanType }}</n-space> -->
                <div style="position: relative; cursor: pointer" @click="typeShow = true">
                  <icon-mdi-search class="icon" />
                  <n-input
                    v-model:value="formModel.typeMsg"
                    type="text"
                    show-password-on="click"
                    :maxlength="8"
                    :disabled="true"
                  >
                    <n-icon :component="Search" />
                  </n-input>
                </div>
              </n-form-item-grid-item>
            </n-grid>

            <div style="max-height: 350px; overflow: auto">
              <n-data-table :loading="loading" :columns="setColumns" :data="setData"></n-data-table>
            </div>
          </n-form>
        </div>
      </template>
    </my-dialog>

    <n-modal v-model:show="typeShow">
      <n-card style="width: 1100px">
        <maintainList
          @submit="maintainListSubmit"
          @cancel="
            () => {
              typeShow = false;
            }
          "
        />
      </n-card>
    </n-modal>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'UpkeepPlanList'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { Search } from '@vicons/ionicons5';
import { createRequiredFormRule } from '@/utils';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { getMoldMaintainList, addMoldMaintain } from '~/src/service/api/moid/upkeepPlan/index';
import { getmoldSupplierList } from '~/src/service/api/moid/supplier/index';
import { formatDate } from '~/src/utils/form/rule';
import maintainList from './maintainList/index.vue';
const searchForm = ref<upkeepPlan.listSearchForm>({
  nestingName: '',
  maintainPlanName: '',
  maintainPlanType: '',
  startDate: null,
  endDate: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const addForm = ref<{
  nestingId: number;
  planId: number;
  ItemsId: string;
  id: number;
  status: string;
  clientId: number | null;
  clientName: string;
}>({
  nestingId: -1,
  planId: -1,
  ItemsId: '',
  id: -1,
  status: '',
  clientId: null,
  clientName: ''
});

const formModel = ref<{
  nestingName: string | null;
  nestingScope: string | null;
  nestingArea: string | null;
  maintainPlanName: string | null;
  maintainPlanType: string | null;
  typeMsg: string | null;
}>({
  nestingName: '',
  nestingScope: '',
  nestingArea: '',
  maintainPlanName: '',
  maintainPlanType: '',
  typeMsg: ''
});

const { loading, startLoading, endLoading } = useLoading();

const message = useMessage();
const formModelRef = ref<FormInst | null>(null);
const show = ref<boolean>(false);
const typeShow = ref<boolean>(false);
const range = ref<[number, number] | null>(null);
const data = ref<upkeepPlan.listTabelList[]>([
  {
    moldNesting: '',
    moldMaintainPlan: '',
    id: 1,
    nestingId: 1,
    planId: 1,
    status: '',
    createTime: '2024-1-1',
    planName: '拉丝保养计划',
    nestingName: 'LS005',
    nestingScope: '5#拉丝机',
    nestingArea: '一楼仓库',
    planType: '拉丝每月保养',
    pageNum: 1,
    pageSize: 1,
    total: 1
  },
  {
    moldNesting: '',
    moldMaintainPlan: '',
    id: 1,
    nestingId: 1,
    planId: 1,
    status: '',
    createTime: '2024-1-1',
    planName: '镀锡保养计划',
    nestingName: 'DX005',
    nestingScope: '5#镀锡机',
    nestingArea: '三楼仓库',
    planType: '镀锡每月保养',
    pageNum: 1,
    pageSize: 1,
    total: 1
  },
  {
    moldNesting: '',
    moldMaintainPlan: '',
    id: 1,
    nestingId: 1,
    planId: 1,
    status: '',
    createTime: '2024-1-1',
    planName: '退火镀锡保养计划',
    nestingName: 'THDX005',
    nestingScope: '5#退火镀锡机',
    nestingArea: '三楼仓库',
    planType: '退火镀锡每月保养',
    pageNum: 1,
    pageSize: 1,
    total: 1
  }
]);
const supplierList = ref<{ label: string; value: number }[]>([]);
const addComplete = ref<boolean>(true);

const rules = {
  clientId: createRequiredFormRule('请选择模具供应商')
};

const columns: Ref<DataTableColumns<upkeepPlan.listTabelList>> = ref([
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
    title: '计划名称',
    key: 'planName',
    align: 'center',
    width: 100
  },
  {
    title: '保养类型',
    key: 'planType',
    align: 'center',
    width: 100
  },
  {
    title: '保养日期',
    key: 'createTime',
    align: 'center',
    width: 100
  },

  // {
  //   title: '保养状态',
  //   key: 'status',
  //   align: 'center',
  //   width: 100,
  //   render: row => {
  //     const obj = getMoidStatus(row.status);
  //     return <n-tag type={obj.statusType}>{obj.text}</n-tag>;
  //   }
  // },
  {
    title: '操作',
    key: 'action',
    fixed: 'right',
    width: 180,
    render: row => {
      return (
        <n-space>
          <n-button
            type="info"
            onClick={() => {
              upkeep(row);
            }}
          >
            保养
          </n-button>
        </n-space>
      );
    }
  }
]);

const setData = ref<upkeepPlan.setTabelList[]>([]);
const setColumns: Ref<DataTableColumns<upkeepPlan.setTabelList>> = ref([
  {
    title: '项目名称',
    key: 'maintainItemName',
    align: 'center',
    width: 100
  },
  {
    title: '项目内容',
    key: 'maintainItemContent',
    align: 'center',
    width: 100
  },
  {
    title: '完成标准',
    key: 'maintainItemStandard',
    align: 'center',
    width: 100
  },
  {
    title: '备注',
    key: 'maintainItemRemark',
    align: 'center',
    width: 100
  }
]);

const checked = ref<(string | number)[]>([]);

function upkeep(row: upkeepPlan.listTabelList) {
  show.value = true;
  formModel.value.nestingName = row.moldNesting?.nestingName;
  formModel.value.nestingScope = row.moldNesting?.nestingScope;
  formModel.value.nestingArea = row.moldNesting?.nestingArea;
  formModel.value.maintainPlanName = row.moldMaintainPlan?.maintainPlanName;
  formModel.value.maintainPlanType = row.moldMaintainPlan?.maintainPlanType;
  addForm.value.id = row.id;
  addForm.value.clientId = null;
  formModel.value.typeMsg = '';
  checked.value = [];
  setData.value = [];
}

function handleUpdateValue(val) {
  const index = supplierList.value.findIndex(item => item.value === val);
  if (index > -1) {
    addForm.value.clientName = supplierList.value[index].label;
  }
}

function maintainListSubmit(e): void {
  checked.value = [];
  e.forEach(item => {
    checked.value.push(item.maintainItemId);
  });
  setData.value = e;
  formModel.value.typeMsg = '已选择保养项';
  typeShow.value = false;
}

function cancel(): void {
  show.value = false;
}

function submit(): void {
  if (!formModelRef.value) return;
  formModelRef.value.validate(errors => {
    if (!errors) {
      const obj = data.value.find(item => item.id === addForm.value.id);
      if (obj) {
        addForm.value.planId = obj.planId;
        addForm.value.nestingId = obj.nestingId;
        addForm.value.status = obj.status;
      }
      let str = '';
      checked.value.forEach(item => {
        str += `${item},`;
      });
      addForm.value.ItemsId = str.slice(0, -1);
      addComplete.value = false;
      addMoldMaintain(addForm.value).then(res => {
        addComplete.value = true;
        if (res.code === 200) {
          message.success('保养成功');
          init();
          show.value = false;
        }
      });
    }
  });
}

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
    nestingName: '',
    maintainPlanName: '',
    maintainPlanType: '',
    startDate: null,
    endDate: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function getList() {
  getmoldSupplierList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        supplierList.value.push({
          label: item.supplierName,
          value: item.moldSupplierId
        });
      });
    }
  });
}

function init() {
  startLoading();
  getMoldMaintainList(searchForm.value).then(res => {
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
