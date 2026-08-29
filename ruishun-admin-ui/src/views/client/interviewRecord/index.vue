<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline label-placement="top" :model="searchForm">
        <n-form-item label="客户名称">
          <n-select
            v-model:value="searchForm.clientId"
            :options="clientList"
            placeholder="请选择客户"
            filterable
          ></n-select>
        </n-form-item>
        <n-form-item>
          <n-button type="primary" class="mr-10px" @click="handleSearch">
            <icon-ic-round-search class="mr-4px text-20px" />
            搜索
          </n-button>
          <n-button @click="handleReset">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="跟进记录列表">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns"></cx-columns>
          <n-button type="primary" size="small" class="ml-5px" @click="openAddRecord(false)">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增
          </n-button>
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :data="data"
        :columns="columns"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="recordShow"
      width="900px"
      :title="addOrEditRecord ? '编辑跟进记录' : '新增跟进记录'"
      @cancel="recordShow = false"
      @submit="submitRecord"
    >
      <template #content>
        <n-form
          ref="addRecordFormRef"
          :model="addRecordForm"
          :rules="recordRules"
          style="width: 900px"
          label-placement="left"
          label-width="130px"
        >
          <n-grid :cols="2">
            <n-form-item-grid-item label="客户名称" path="clientId" :span="1">
              <n-select
                v-model:value="addRecordForm.clientId"
                :options="clientList"
                placeholder="请选择客户"
                filterable
              ></n-select>
            </n-form-item-grid-item>
            <n-form-item-grid-item :span="1" label="跟进人" path="interviewName">
              <n-input v-model:value="addRecordForm.interviewName" placeholder="请输入跟进人"></n-input>
            </n-form-item-grid-item>
            <n-form-item-grid-item :span="1" label="跟进时间" path="interviewTime">
              <n-date-picker
                v-model:formatted-value="addRecordForm.interviewTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime"
                style="width: 100%"
                clearable
              />
            </n-form-item-grid-item>
            <n-form-item-grid-item label="跟进方式" path="interviewStatus" :span="1">
              <n-select
                v-model:value="addRecordForm.interviewStatus"
                :options="modeList"
                placeholder="请选择跟进方式"
                filterable
              ></n-select>
            </n-form-item-grid-item>
            <n-form-item-grid-item :span="1" label="跟进地址" path="attr2">
              <!-- <n-input v-model:value="addRecordForm.interviewAddress" placeholder="请输入跟进地址"></n-input>
            </n-form-item-grid-item> -->
              <div style="display: flex; flex-wrap: wrap; width: 100%">
                <n-cascader
                  v-model:value="addRecordForm.attr2"
                  style="width: 100%"
                  :expand-trigger="'click'"
                  :options="addressOptions"
                  :check-strategy="checkStrategyIsChild ? 'child' : 'all'"
                  :show-path="true"
                  remote
                  placeholder="请选择省市区"
                />
                <n-input
                  v-model:value="addRecordForm.attr1"
                  placeholder="请输入详细地址"
                  style="width: 100%"
                  class="mt-10px"
                  type="textarea"
                />
              </div>
            </n-form-item-grid-item>
            <n-form-item-grid-item :span="1" label="联系人">
              <n-input v-model:value="addRecordForm.connectionName" placeholder="请输入联系人"></n-input>
            </n-form-item-grid-item>
            <n-form-item-grid-item :span="1" label="跟进描述">
              <n-input v-model:value="addRecordForm.description" placeholder="请输入跟进描述"></n-input>
            </n-form-item-grid-item>
            <n-form-item-grid-item :span="1" label="备注">
              <n-input v-model:value="addRecordForm.remark" placeholder="请输入备注"></n-input>
            </n-form-item-grid-item>
          </n-grid>
        </n-form>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { useDialog } from 'naive-ui';
import {
  addMdClientInterview,
  getMdClientInterview,
  editMdClientInterview,
  deleteMdClientInterview,
  getAllUser
} from '@/service/api/client/interviewRecord';
import { dataTableConfig } from '@/config/dataTableConfig';
import { createRequiredFormRule } from '~/src/utils';
import { useResetSearch } from '~/src/utils/common/searchReset';
import { proCityList } from '../information/options/address';

const paymentMethodOptions = ref([
  { label: '款到发货', value: 1 },
  { label: '货到付款', value: 0 }
]);

const checkStrategyIsChild = ref(true);

const clientList = ref<SelectMixedOption[]>([]);

const addressOptions = ref<Array<any>>(JSON.parse(JSON.stringify(proCityList)));

const modeList = ref<SelectValueStringOption[]>([
  { label: '微信沟通', value: '0' },
  { label: '电话沟通', value: '1' },
  { label: '线下拜访', value: '2' },
  { label: '其他', value: '3' }
]);

function getPaymentMethod() {
  for (let i = 2; i <= 30; i += 1) {
    paymentMethodOptions.value.push({ label: `到货${i}天付款`, value: i });
  }
}

const addOrEditRecord = ref<boolean>(false);

const dialog = useDialog();

const recordShow = ref<boolean>(false);

const addRecordFormRef = ref<FormInst | null>(null);

const addRecordForm = ref<interviewRecord.addRecordForm>({
  id: null,
  clientId: null,
  clientName: '',
  interviewName: '',
  interviewTime: null,
  interviewAddress: '',
  connectionName: '',
  description: '',
  remark: '',
  interviewStatus: null,
  attr1: '',
  attr2: null
});

const resetAddRecordForm = JSON.parse(JSON.stringify(addRecordForm.value));

const { searchForm, reset } = useResetSearch({
  creditCode: '',
  clientId: null,
  salesmanName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0,
  attr1: null,
  attr2: null
});
const data = ref<interviewRecord.recordColumns[]>([]);

const columns: Ref<DataTableColumns<interviewRecord.recordColumns>> = ref([
  {
    title: '客户名称',
    key: 'clientName',
    width: 200,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '跟进人',
    key: 'interviewName',
    width: 100,
    align: 'center'
  },
  {
    title: '跟进时间',
    key: 'interviewTime',
    width: 180,
    align: 'center'
  },
  {
    title: '跟进方式',
    key: 'interviewStatus',
    width: 120,
    align: 'center',
    render: row => {
      const sectionType = getInterviewStatusText(row.interviewStatus);
      const obj = {
        color: sectionType.color,
        textColor: sectionType.textColor,
        borderColor: sectionType.color
      };
      return <n-tag color={obj}>{sectionType.text}</n-tag>;
    }
  },
  {
    title: '跟进地址',
    key: 'interviewAddress',
    width: 180,
    align: 'center',
    ellipsis: {
      tooltip: true
    },
    render: row => row.interviewAddress + row.attr1
  },
  {
    title: '联系人',
    key: 'connectionName',
    width: 100,
    align: 'center'
  },
  {
    title: '跟进描述',
    key: 'description',
    width: 120,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '备注',
    key: 'remark',
    width: 120,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    width: 100,
    render: row => {
      return (
        <div class="flex">
          <n-button
            type="warning"
            size="small"
            class="mr-10px"
            onClick={() => {
              openAddRecord(true, row);
            }}
          >
            编辑
          </n-button>
          <n-button
            type="error"
            size="small"
            class="mr-10px"
            onClick={() => {
              deleteRecord(row.id);
            }}
          >
            删除
          </n-button>
        </div>
      );
    }
  }
]);

const recordRules = {
  interviewName: createRequiredFormRule('请填写跟进人'),
  interviewTime: createRequiredFormRule('请选择跟进时间'),
  attr2: createRequiredFormRule('请填写跟进地址'),
  clientId: createRequiredFormRule('请填客户名称')
};
const recordSearchForm = ref<interviewRecord.recordSearchForm>({
  clientId: null,
  clientName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const loading = ref<boolean>(false);

function getInterviewStatusText(interviewStatus: string) {
  switch (interviewStatus) {
    case '0':
      return { text: '微信沟通', color: '#1084FF', textColor: '#fff' };
    case '1':
      return { text: '电话沟通', color: '#00D6A7', textColor: '#fff' };
    case '2':
      return { text: '线下拜访', color: '#FF7300', textColor: '#fff' };
    case '3':
      return { text: '其他方式', color: '#FFA700', textColor: '#fff' };
    default:
      return { text: '其他方式', color: '#FFA700', textColor: '#fff' };
  }
}

function openAddRecord(bool: boolean, row?: interviewRecord.recordColumns) {
  recordShow.value = true;
  addOrEditRecord.value = bool;
  addRecordForm.value = JSON.parse(JSON.stringify(resetAddRecordForm));
  if (!addOrEditRecord.value) {
    addRecordForm.value.clientId = recordSearchForm.value.clientId;
    addRecordForm.value.clientName = recordSearchForm.value.clientName;
  } else {
    if (!row) return;
    for (const key in addRecordForm.value) {
      if (row[key] || row[key] === 0 || row[key] === '0') {
        addRecordForm.value[key] = row[key];
      }
    }
    addRecordForm.value.attr2 = addRecordForm.value.attr2?.toString() || null;
  }
}

function getAddressName(code: string) {
  const provinceCode = `${code.slice(0, 2)}0000`;
  const cityCode = `${code.slice(0, 4)}00`;
  const provinceItem = proCityList.find(item => item.value === provinceCode);
  if (!provinceItem) return '';
  if (!provinceItem.children) return provinceItem.label;
  const cityItem = provinceItem.children.find(item => item.value === cityCode);
  if (!cityItem) return provinceItem.label;
  const districtItem = cityItem.children.find(item => item.value === code);
  return provinceItem.label + cityItem.label + (districtItem?.label || '');
}

function submitRecord() {
  addRecordFormRef.value?.validate(errors => {
    if (errors) return;
    if (addRecordForm.value.attr2 !== null) {
      addRecordForm.value.interviewAddress = getAddressName(addRecordForm.value.attr2);
    }
    if (!addOrEditRecord.value) {
      addMdClientInterview(addRecordForm.value).then(res => {
        if (res.code === 200) {
          window.$message?.success('添加成功');
          recordShow.value = false;
          init();
        }
      });
    } else {
      editMdClientInterview(addRecordForm.value).then(res => {
        if (res.code === 200) {
          window.$message?.success('编辑成功');
          recordShow.value = false;
          init();
        }
      });
    }
  });
}

function deleteRecord(deleteId: number) {
  const d = dialog.warning({
    title: '删除',
    content: '确认要删除吗?',
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        deleteMdClientInterview(deleteId).then((res: any) => {
          if (res.code === 200) {
            window.$message?.success('删除成功');
            init();
          }
          resolve(true);
        });
      });
    }
  });
}

function handleSearch() {
  init();
}
function handleReset() {
  reset();
  init();
}

function init() {
  loading.value = true;
  getMdClientInterview(recordSearchForm.value).then(res => {
    loading.value = false;
    if (res.code === 200) {
      data.value = res.rows;
      recordSearchForm.value.total = res.total;
    } else {
      data.value = [];
    }
  });
}

function getClientList() {
  getAllUser().then(res => {
    const resultData = res.data || [];
    resultData.forEach(item => {
      clientList.value.push({
        label: item.clientName,
        value: item.id,
        ...item
      });
    });
  });
}

onMounted(() => {
  init();
  getPaymentMethod();
  getClientList();
});
</script>

<style scoped lang="scss"></style>
