<template>
  <div>
    <my-card title="搜索">
      <n-form inline>
        <n-form-item label="客户名称">
          <n-select
            v-model:value="searchForm.clientId"
            :options="clientList"
            placeholder="请选择客户"
            filterable
          ></n-select>
        </n-form-item>
        <n-form-item label="联系人名称">
          <n-input v-model:value="searchForm.personName" placeholder="请输入联系人名称" />
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

    <my-card title="联系人列表">
      <template #right>
        <div class="flex-center">
          <n-button type="info" size="small" class="mr-10px" @click="addOrEdit(0)">
            <icon-mdi-add />
            新增
          </n-button>
          <CxColumns v-model:columns="columns" />
        </div>
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :columns="columns"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
      ></n-data-table>
    </my-card>

    <my-dialog
      v-model:show="show"
      :title="addOrEditForm.clientId ? '编辑联系人' : '新增联系人'"
      width="1100px"
      @cancel="show = false"
      @submit="submit"
    >
      <template #content>
        <div>
          <n-form
            ref="formModel"
            style="width: 1000px"
            :rules="rules"
            :model="addOrEditForm"
            label-placement="left"
            label-width="120px"
          >
            <n-grid :cols="3" :x-gap="20">
              <n-form-item-grid-item label="客户名称" path="clientId" :span="1">
                <n-space>
                  <n-select
                    v-model:value="addOrEditForm.clientId"
                    :options="clientList"
                    :style="{ width: formItemWidth }"
                    placeholder="请选择客户"
                    filterable
                    @update:value="handleUpdateValue"
                    @scroll="handleScroll"
                  ></n-select>
                </n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="客户编码">
                <n-input
                  v-model:value="addOrEditForm.clientCode"
                  placeholder="请选择客户"
                  :style="{ width: formItemWidth }"
                  :disabled="true"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="联系人名称" path="personName">
                <n-input
                  v-model:value="addOrEditForm.personName"
                  placeholder="请输入联系人名称"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="联系人电话" path="personPhone">
                <n-input
                  v-model:value="addOrEditForm.personPhone"
                  placeholder="请输入联系人电话"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="部门">
                <n-select
                  v-model:value="addOrEditForm.section"
                  :options="sectionList"
                  :style="{ width: formItemWidth }"
                  placeholder="请选择部门"
                  filterable
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="职务">
                <n-input v-model:value="addOrEditForm.job" placeholder="请输入职务" :style="{ width: formItemWidth }" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="微信号">
                <n-input
                  v-model:value="addOrEditForm.wxNumber"
                  placeholder="请输入微信号"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="邮箱">
                <n-input
                  v-model:value="addOrEditForm.mailbox"
                  placeholder="请输入邮箱"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="联系人详情">
                <n-input
                  v-model:value="addOrEditForm.detail"
                  placeholder="请输入联系人详情"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="决策人">
                <n-input
                  v-model:value="addOrEditForm.isDecision"
                  placeholder="请输入决策人"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="负责人">
                <n-input
                  v-model:value="addOrEditForm.dutyBy"
                  placeholder="请输入决策人"
                  :style="{ width: formItemWidth }"
                />
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
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useDialog } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { dataTableConfig } from '@/config/dataTableConfig';
import { getAllUser } from '@/service/api/sale/userManage';
import {
  getCrmContactPerson,
  addCrmContactPerson,
  editCrmContactPerson,
  deleteCrmContactPerson
} from '@/service/api/client/contactPerson/index';
import { useResetSearch } from '~/src/utils/common/searchReset';
import { useLoading } from '~/src/hooks/index';

const { searchForm, reset } = useResetSearch({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  clientId: null,
  personName: ''
});

const addOrEditForm = ref<contactPerson.addOrEditForm>({
  id: null,
  clientId: null,
  clientName: '',
  clientCode: '',
  personName: '',
  personPhone: '',
  section: null,
  job: '',
  wxNumber: '',
  mailbox: '',
  detail: '',
  isDecision: '',
  dutyBy: ''
});

const rules = {
  clientId: createRequiredFormRule('请选择客户'),
  personName: createRequiredFormRule('请输入联系人名称'),
  personPhone: createRequiredFormRule('请输入联系人电话')
};

const sectionList = ref<SelectValueStringOption[]>([
  { label: '市场部', value: '0' },
  { label: '采购部', value: '1' },
  { label: '生产部', value: '2' },
  { label: '人事部', value: '3' },
  { label: '销售部', value: '4' },
  { label: '行政部', value: '5' },
  { label: '其他', value: '6' }
]);

const clientList = ref<SelectMixedOption[]>([]);

const formModel = ref<FormInst | null>(null);

const dialog = useDialog();

const addOrEditType = ref<number>(0); // 0 : 添加， 1 : 编辑

const show = ref<boolean>(false);

const formItemWidth = ref<string>('200px');

const { loading, startLoading, endLoading } = useLoading();

const data = ref<contactPerson.TableList[]>([]);

const columns: Ref<DataTableColumns<contactPerson.TableList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => index + 1,
    width: 100
  },
  {
    title: '客户名称',
    key: 'clientName',
    align: 'center',
    width: 180,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '客户编码',
    key: 'clientCode',
    align: 'center',
    width: 180,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '联系人名称',
    key: 'personName',
    align: 'center',
    width: 120
  },
  {
    title: '联系人电话',
    key: 'personPhone',
    align: 'center',
    width: 120
  },
  {
    title: '部门',
    key: 'section',
    align: 'center',
    width: 120,
    render: row => {
      const sectionType = sectionText(row.section);
      const obj = {
        color: sectionType.color,
        textColor: sectionType.textColor,
        borderColor: sectionType.color
      };
      return <n-tag color={obj}>{sectionType.label}</n-tag>;
    }
  },
  {
    title: '职务',
    key: 'job',
    align: 'center',
    width: 120
  },
  {
    title: '微信号',
    key: 'wxNumber',
    align: 'center',
    width: 120
  },
  {
    title: '邮箱',
    key: 'mailbox',
    align: 'center',
    width: 160
  },
  {
    title: '联系人详情',
    key: 'detail',
    align: 'center',
    width: 160,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '决策人',
    key: 'isDecision',
    align: 'center',
    width: 160
  },
  {
    title: '负责人',
    key: 'dutyBy',
    align: 'center',
    width: 120
  },
  {
    title: '操作',
    key: 'actions',
    width: 180,
    align: 'center',
    fixed: 'right',
    render: row => {
      return (
        <n-space justify={'center'}>
          <n-button
            attr-type="button"
            size={'small'}
            type="warning"
            onClick={() => {
              addOrEdit(1, row);
            }}
          >
            <icon-mdi-edit />
            编辑
          </n-button>

          <n-button
            type="error"
            size={'small'}
            onClick={() => {
              handleDeleteItemType(row.id);
            }}
          >
            <icon-mdi-delete />
            删除
          </n-button>
        </n-space>
      );
    }
  }
]);

function handleUpdateValue(val, option) {
  console.log('val ==>', val);
  addOrEditForm.value.clientName = option.clientName;
  addOrEditForm.value.clientCode = option.clientCode;
}

function addOrEdit(num: number, row?: contactPerson.TableList) {
  addOrEditType.value = num;
  show.value = true;
  addOrEditForm.value = {
    id: null,
    clientId: null,
    clientName: '',
    clientCode: '',
    personName: '',
    personPhone: '',
    section: null,
    job: '',
    wxNumber: '',
    mailbox: '',
    detail: '',
    isDecision: '',
    dutyBy: ''
  };
  if (num === 1) {
    if (!row) return;
    for (const key in addOrEditForm.value) {
      if (row[key] || row[key] === '0') {
        addOrEditForm.value[key] = row[key];
      }
    }
  }
}

function handleDeleteItemType(id: number) {
  const d = dialog.warning({
    title: '删除',
    content: '确认要删除吗?',
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        deleteCrmContactPerson(id).then(res => {
          if (res.code === 200) {
            init();
            window.$message?.success('删除成功');
          }
          resolve(true);
        });
      });
    }
  });
}

function submitSame(func: any, msg: string): void {
  func(addOrEditForm.value).then(res => {
    if (res.code === 200) {
      show.value = false;
      window.$message?.success(msg);
      init();
    }
  });
}

function submit(): void {
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      if (addOrEditType.value === 0) {
        submitSame(addCrmContactPerson, '添加成功');
      } else {
        submitSame(editCrmContactPerson, '编辑成功');
      }
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
  startLoading();
  getCrmContactPerson(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}

function handleScroll() {
  console.log('handleScroll');
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

function sectionText(section: string) {
  switch (section) {
    case '0':
      return { label: '市场部', color: '#FF3838', textColor: '#fff' };
    case '1':
      return { label: '采购部', color: '#FFA400', textColor: '#fff' };
    case '2':
      return { label: '生产部', color: '#1FD95C', textColor: '#fff' };
    case '3':
      return { label: '人事部', color: '#9FCF00', textColor: '#fff' };
    case '4':
      return { label: '销售部', color: '#00C7FC', textColor: '#fff' };
    case '5':
      return { label: '行政部', color: '#4E61FF', textColor: '#fff' };
    case '6':
      return { label: '其他', color: '#FAFAFD', textColor: '#000' };
    default:
      return { label: '其他', color: '#FAFAFD', textColor: '#000' };
  }
}

onMounted(() => {
  init();
  getClientList();
});
</script>

<style lang="scss" scoped></style>
