<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline label-placement="top" :model="searchForm">
        <n-form-item label="货源名称" path="clientName">
          <n-input v-model:value="searchForm.sourceName" placeholder="请输入货源名称"></n-input>
        </n-form-item>
        <n-form-item label="统一社会代码" path="creditCode">
          <n-input v-model:value="searchForm.sourceUniqueCode" placeholder="请输入统一社会代码"></n-input>
        </n-form-item>
        <n-form-item label="业务员" path="salesmanName">
          <n-input v-model:value="searchForm.salesman" placeholder="请输入业务员"></n-input>
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
    <my-card title="货源列表">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button type="primary" size="small" class="ml-5px" @click="openDialog">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增货源
          </n-button>
        </div>
      </template>
      <n-data-table :loading="loading" :data="data" :columns="columns"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="flag"
      width="450px"
      :title="editFlag ? '编辑货源' : '新增货源'"
      @cancel="cancel"
      @submit="submit"
    >
      <template #content>
        <div style="width: 100%">
          <n-form ref="addFormRef" :model="addForm" :rules="rules" label-placement="left" label-width="130px">
            <n-grid :cols="3" :x-gap="10" :y-gap="10">
              <n-form-item-grid-item :span="3" label="货源名称:" path="sourceName">
                <n-input v-model:value="addForm.sourceName" size="small" placeholder="请输入货源名称" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="3" label="货源简称:" path="abbreviation">
                <n-input v-model:value="addForm.abbreviation" size="small" placeholder="请输入货源简称" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="3" label="货源编码:" path="sourceCode">
                <n-input v-model:value="addForm.sourceCode" size="small" placeholder="请输入货源编码" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="3" label="业务员:" path="salesman">
                <n-input v-model:value="addForm.salesman" size="small" placeholder="请输入业务员" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="3" label="业务员电话:" path="phoneNumber">
                <n-input v-model:value="addForm.phoneNumber" size="small" placeholder="请输入业务员电话" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="3" label="统一社会信用代码:" path="sourceUniqueCode">
                <n-input v-model:value="addForm.sourceUniqueCode" size="small" placeholder="请输入统一社会信用代码" />
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
import { useMessage } from 'naive-ui';
import type { DataTableColumns, FormInst } from 'naive-ui';
import {
  getProcureSourceList,
  deleteSourceOrder,
  addSourceOrder,
  editSourceOrder
} from '@/service/api/procure/sourceManage';
import { useEditBtn, useDelBtn } from '@/hooks/common/useBtn';
import { useLoading, useBoolean } from '~/src/hooks';
import { createRequiredFormRule } from '~/src/utils';

const message = useMessage();
const { loading, startLoading, endLoading } = useLoading();
const addFormRef = ref<FormInst | null>(null);
// const dialog = useWarning();
const { bool: flag, setFalse: closeDialog, setTrue: openDialog } = useBoolean();
const editFlag = ref<boolean>(false);
const addForm = ref<SourceManage.addForm>({
  sourceName: '',
  abbreviation: '',
  sourceCode: '',
  sourceUniqueCode: '',
  salesman: '',
  phoneNumber: '',
  isDeleted: 0,
  version: 0
});

const rules = {
  sourceName: createRequiredFormRule('请输入货源名称'),
  abbreviation: createRequiredFormRule('请输入货源简称'),
  sourceCode: createRequiredFormRule('请输入货源编码'),
  salesman: createRequiredFormRule('请输入业务员'),
  phoneNumber: createRequiredFormRule('请输入业务员电话')
};
const searchForm = ref<SourceManage.searchForm>({
  sourceName: '',
  sourceUniqueCode: '',
  salesman: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const data = ref<SourceManage.columns[]>([]);
const columns: Ref<DataTableColumns<SourceManage.columns>> = ref([
  {
    title: '序号',
    key: 'index',
    titleAlign: 'center',
    align: 'center',
    render(_row, index) {
      return index + 1;
    },
    width: 60
  },
  {
    title: '货源名称',
    key: 'sourceName',
    width: 200,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '货源简称',
    align: 'center',
    key: 'abbreviation',
    width: 80
  },
  {
    title: '货源编码',
    align: 'center',
    key: 'sourceCode',
    width: 80
  },
  {
    title: '统一社会信用代码',
    align: 'center',
    key: 'sourceUniqueCode',
    width: 200,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '业务员',
    align: 'center',
    width: 120,
    key: 'salesman'
  },
  {
    title: '业务员电话',
    align: 'center',
    width: 150,
    key: 'phoneNumber'
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    width: 160,
    fixed: 'right',
    render: row => {
      return (
        <div>
          {useEditBtn(() => {
            editUser(row);
          }, 'tiny')}

          {useDelBtn(() => {
            deleteById(row);
          }, 'tiny')}
        </div>
      );
    }
  }
]);
function handleSearch() {
  init();
}
function handleReset() {
  searchForm.value = {
    sourceName: '',
    sourceUniqueCode: '',
    salesman: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function cancel() {
  addForm.value = {
    sourceName: '',
    abbreviation: '',
    sourceCode: '',
    sourceUniqueCode: '',
    salesman: '',
    phoneNumber: '',
    isDeleted: 0,
    version: 0
  };
  closeDialog();
}
function editUser(row) {
  addForm.value = { ...row };
  editFlag.value = true;
  openDialog();
}
function deleteById(row: SourceManage.columns) {
  deleteSourceOrder(row).then(res => {
    if (res.code === 200) {
      init();
    }
  });
}
function submit() {
  addFormRef.value?.validate(errors => {
    if (!errors) {
      if (addForm.value.id) {
        editSourceOrder(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('修改成功！');
            cancel();
            init();
          }
        });
      } else {
        addSourceOrder(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('新增成功！');
            cancel();
            init();
          }
        });
      }
    }
  });
}
function init() {
  startLoading();
  getProcureSourceList(searchForm.value).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
      endLoading();
    }
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped lang="scss"></style>
