<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="辅料供应商">
          <n-input v-model:value="searchForm.supplierName" placeholder="请输入辅料供应商"></n-input>
        </n-form-item>
        <n-form-item>
          <component
            :is="
              useSearchBtn(
                () => {
                  handleSearch();
                },
                () => {
                  handleReset();
                }
              )
            "
          ></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="辅料供应商">
      <template #right>
        <div>
          <component
            :is="
              useAddBtn(() => {
                dialogFlag = true;
                flag = true;
              })
            "
            style="margin-right: 15px"
          ></component>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      :title="flag ? '新增辅料供应商' : '编辑辅料供应商'"
      :show="dialogFlag"
      @cancel="handleCancel"
      @submit="handleSubmit"
    >
      <template #content>
        <div>
          <n-form
            ref="addFormRef"
            label-placement="left"
            label-width="120px"
            label-align="left"
            :model="addForm"
            :rules="rules"
          >
            <n-form-item label="辅料供应商" :span="12" path="supplierName">
              <n-input v-model:value="addForm.supplierName" placeholder="请输入辅料供应商"></n-input>
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import {
  getaccessorySupplierList,
  deleteaccessorySupplier,
  addaccessorySupplier,
  editaccessorySupplier
} from '@/service/api/md/accessorySupplier/index';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  supplierName: string;
  pageNum: number;
  pageSize: number;
  total: number;
};

const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  supplierName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
type addFormType = {
  supplierName: string;
};
const addForm = ref<addFormType>({
  supplierName: ''
});
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    supplierName: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  supplierName: string;
};

const addFormRef = ref<FormInst | null>(null);

const rules = {
  supplierName: createRequiredFormRule('请输入供应商')
};

const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '辅料供应商',
    align: 'center',
    key: 'supplierName'
  },
  {
    title: '操作',
    align: 'center',
    key: 'actions',
    render: row => {
      return [
        useEditBtn(() => {
          handleEdit(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteaccessorySupplier(row).then(res => {
            if (res.code === 200) {
              message.success('删除成功');
              init();
            }
          });
        }, 'tiny')
      ];
    }
  }
]);

const checked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};

// 弹窗
const dialogFlag = ref<boolean>(false);
const handleCancel = () => {
  dialogFlag.value = false;
  addForm.value = {
    supplierName: ''
  };
};
const handleSubmit = () => {
  if (!addFormRef.value) return;
  addFormRef.value.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addaccessorySupplier(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editaccessorySupplier(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('修改成功');
            handleCancel();
            init();
          }
        });
      }
    }
  });
};
function handleEdit(row) {
  dialogFlag.value = true;
  flag.value = false;
  addForm.value = deepClone(row);
}

// 深度clone
function deepClone(obj: any) {
  return JSON.parse(JSON.stringify(obj));
}
function init() {
  startLoading();
  getaccessorySupplierList(searchForm.value).then(res => {
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

<style scoped></style>
