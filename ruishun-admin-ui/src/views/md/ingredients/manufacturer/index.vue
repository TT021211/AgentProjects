<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="贸易商名称">
          <n-input v-model:value="searchForm.manufacturerName" placeholder="请输入贸易商名称"></n-input>
        </n-form-item>
        <n-form-item label="贸易商编码">
          <n-input v-model:value="searchForm.manufacturerCode" placeholder="请输入贸易商编码"></n-input>
        </n-form-item>
        <n-form-item label="联系人">
          <n-input v-model:value="searchForm.person" placeholder="请输入联系人"></n-input>
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
    <my-card title="贸易商列表">
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
      :title="flag ? '新增贸易商' : '编辑贸易商'"
      :show="dialogFlag"
      @cancel="handleCancel"
      @submit="handleSubmit"
    >
      <template #content>
        <div>
          <n-form
            ref="addFormRef"
            label-placement="left"
            label-width="100px"
            label-align="left"
            :model="addForm"
            :rules="rules"
          >
            <n-form-item label="厂家名称" :span="15" path="manufacturerName">
              <n-input v-model:value="addForm.manufacturerName" placeholder="请输入厂家名称"></n-input>
            </n-form-item>
            <n-form-item label="厂家简称" :span="15" path="manufacturerNick">
              <n-input v-model:value="addForm.manufacturerNick" placeholder="请输入厂家简称"></n-input>
            </n-form-item>
            <n-form-item label="厂家编码" :span="15" path="manufacturerCode">
              <n-input v-model:value="addForm.manufacturerCode" placeholder="请输入厂家编码"></n-input>
            </n-form-item>
            <n-form-item label="厂家地址" :span="15" path="manufacturerAddress">
              <n-input v-model:value="addForm.manufacturerAddress" placeholder="请输入厂家地址"></n-input>
            </n-form-item>
            <n-form-item label="联系人" :span="15" path="person">
              <n-input v-model:value="addForm.person" placeholder="请输入联系人" />
            </n-form-item>
            <n-form-item label="联系电话" :span="15" path="phone">
              <n-input v-model:value="addForm.phone" placeholder="请输入联系电话" />
            </n-form-item>
            <n-form-item label="备注" :span="15" path="remark">
              <n-input v-model:value="addForm.remark" type="textarea" placeholder="请输入备注" />
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
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import {
  getIngredientsManufacturerList,
  deleteIngredientsManufacturer,
  editIngredientsManufacturer,
  addIngredientsManufacturer
} from '@/service/api/md/ingredients/manufacturer';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  manufacturerName: string;
  manufacturerCode: string;
  person: string;
  pageNum: number;
  pageSize: number;
  total: number;
};

const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  manufacturerName: '',
  manufacturerCode: '',
  person: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const rules = {
  manufacturerName: [
    {
      required: true,
      message: '请输入贸易商名称'
    }
  ],
  manufacturerCode: [
    {
      required: true,
      message: '请输入贸易商编码'
    }
  ]
};
type addFormType = {
  manufacturerName: string;
  manufacturerNick: string;
  manufacturerCode: string;
  manufacturerAddress: string;
  person: string;
  phone: string;
  remark: string;
};
const addForm = ref<addFormType>({
  manufacturerName: '',
  manufacturerNick: '',
  manufacturerCode: '',
  manufacturerAddress: '',
  person: '',
  phone: '',
  remark: ''
});
const addFormRef = ref<FormInst | null>(null);
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    manufacturerName: '',
    manufacturerCode: '',
    person: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  manufacturerName: string;
  manufacturerNick: string;
  manufacturerCode: string;
  manufacturerAddress: string;
  person: string;
  phone: string;
  remark: string;
};

const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '厂家名称',
    align: 'center',
    width: 180,
    key: 'manufacturerName'
  },
  {
    title: '厂家简称',
    align: 'center',
    key: 'manufacturerNick'
  },
  {
    title: '厂家编码',
    align: 'center',
    key: 'manufacturerCode'
  },
  {
    title: '厂家地址',
    align: 'center',
    key: 'manufacturerAddress'
  },
  {
    title: '联系人',
    align: 'center',
    key: 'person'
  },
  {
    title: '联系人电话',
    align: 'center',
    key: 'phone'
  },
  {
    title: '备注',
    align: 'center',
    key: 'remark',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '操作',
    align: 'center',
    key: 'actions',
    width: 200,
    render: row => {
      return [
        useEditBtn(() => {
          handleEdit(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteIngredientsManufacturer(row).then(res => {
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
    manufacturerName: '',
    manufacturerNick: '',
    manufacturerCode: '',
    manufacturerAddress: '',
    person: '',
    phone: '',
    remark: ''
  };
};
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addIngredientsManufacturer(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editIngredientsManufacturer(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('修改成功');
            handleCancel();
            init();
          }
        });
      }
    }
  });
}
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
  data.value = [];
  getIngredientsManufacturerList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  init();
});
</script>

<style scoped></style>
