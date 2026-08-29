<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="类型名称">
          <n-input v-model:value="searchForm.typeName" placeholder="请输入类型名称"></n-input>
        </n-form-item>
        <n-form-item label="类型编码">
          <n-input v-model:value="searchForm.typeCode" placeholder="请输入类型编码"></n-input>
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
    <my-card title="设备类型列表">
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
    <my-dialog :title="flag ? '新增类型' : '编辑类型'" :show="dialogFlag" @cancel="handleCancel" @submit="handleSubmit">
      <template #content>
        <div>
          <n-form
            ref="addFormRef"
            label-placement="left"
            label-width="90px"
            label-align="left"
            :model="addForm"
            :rules="rules"
          >
            <n-form-item label="类型名称" :span="15" path="typeName">
              <n-input v-model:value="addForm.typeName" placeholder="请输入类型名称"></n-input>
            </n-form-item>
            <n-form-item label="类型编码" :span="15" path="typeCode">
              <n-input v-model:value="addForm.typeCode" placeholder="请输入类型编码"></n-input>
            </n-form-item>
            <n-form-item label="备注" path="remark">
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
  getEquipmentTypeList,
  deleteEquipmentType,
  editEquipmentType,
  addEquipmentType
} from '@/service/api/device/facilityCategorySet';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  typeName: string;
  typeCode: string;
  pageNum: number;
  pageSize: number;
  total: number;
};

const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  typeName: '',
  typeCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const rules = {
  typeName: [
    {
      required: true,
      message: '请输入类型名称'
    }
  ],
  typeCode: [
    {
      required: true,
      message: '请输入类型编码'
    }
  ]
};
type addFormType = {
  typeName: string;
  typeCode: string;
  remark: string;
};
const addForm = ref<addFormType>({
  typeName: '',
  typeCode: '',
  remark: ''
});
const addFormRef = ref<FormInst | null>(null);
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    typeName: '',
    typeCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  workbenchType: string;
  workbenchCode: string;
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
    title: '类型名称',
    align: 'center',
    key: 'typeName'
  },
  {
    title: '类型编码',
    align: 'center',
    key: 'typeCode'
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
    render: row => {
      return [
        useEditBtn(() => {
          handleEdit(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteEquipmentType(row).then(res => {
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
    typeName: '',
    typeCode: '',
    remark: ''
  };
};
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addEquipmentType(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editEquipmentType(addForm.value).then((res: any) => {
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
  getEquipmentTypeList(searchForm.value).then(res => {
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
