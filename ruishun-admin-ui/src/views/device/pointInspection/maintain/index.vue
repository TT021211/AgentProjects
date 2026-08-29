<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="配件编号">
          <n-input v-model:value="searchForm.itemName" placeholder="请输入配件编号"></n-input>
        </n-form-item>
        <n-form-item label="设备类型">
          <n-select
            v-model:value="searchForm.typeId"
            class="w-180px"
            :options="typeOptions"
            placeholder="请选择设备类型"
            @update:value="typeHandleChange"
          />
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
    <my-card title="维护项列表">
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
      :title="flag ? '新增维护项' : '编辑维护项'"
      :show="dialogFlag"
      @cancel="handleCancel"
      @submit="handleSubmit"
    >
      <template #content>
        <div>
          <n-form
            ref="addFormRef"
            label-placement="left"
            label-width="80px"
            label-align="left"
            :model="addForm"
            :rules="rules"
          >
            <n-form-item label="项目名称" :span="12" path="itemName">
              <n-input v-model:value="addForm.itemName" placeholder="请输入项目名称"></n-input>
            </n-form-item>
            <n-form-item label="设备类型" :span="12" path="typeId">
              <n-select
                v-model:value="addForm.typeId"
                class="w-180px"
                :options="typeOptions"
                placeholder="请选择设备类型"
                @update:value="typeHandleChange"
              />
            </n-form-item>
            <n-form-item label="项目内容" :span="12" path="itemContent">
              <n-input v-model:value="addForm.itemContent" placeholder="请输入项目内容"></n-input>
            </n-form-item>
            <n-form-item label="完成标准" :span="12" path="completionStandard">
              <n-input v-model:value="addForm.completionStandard" placeholder="请输入完成标准"></n-input>
            </n-form-item>
            <n-form-item label="是否启用">
              <n-switch v-model:value="addForm.ifEnable" :checked-value="1" :unchecked-value="0" />
            </n-form-item>
            <n-form-item label="备注" :span="12" path="remark">
              <n-input v-model:value="addForm.remark" type="textarea" placeholder="请输入备注"></n-input>
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
import { getItemList, deleteItem, editItem, addItem } from '@/service/api/device/maintain';
import { queryEquipmentTypeAll } from '@/service/api/device/facilityCategorySet';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  itemName: string;
  pageNum: number;
  pageSize: number;
  total: number;
  typeId: string | null;
};

const rules = {
  itemName: [
    {
      required: true,
      message: '请输入项目名称'
    }
  ],
  typeId: [
    {
      required: true,
      message: '请选择项目类型'
    }
  ]
};
const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  itemName: '',
  pageNum: 1,
  pageSize: 10,
  typeId: null,
  total: 0
});
type addFormType = {
  itemName: string;
  typeId: string | null;
  typeName: string | null;
  itemContent: string;
  ifEnable: number;
  distributionPath: string;
  completionStandard: string;
  remark: string;
};
const addForm = ref<addFormType>({
  itemName: '',
  typeId: null,
  typeName: null,
  itemContent: '',
  ifEnable: 0,
  distributionPath: '',
  completionStandard: '',
  remark: ''
});
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    itemName: '',
    pageNum: 1,
    pageSize: 10,
    typeId: null,
    total: 0
  };
  init();
}
type DataType = {
  id: number;
  itemName: string;
  typeName: string;
  itemContent: string;
  distributionPath: string;
  completionStandard: string;
  ifEnable: string;
  remark: string;
};
const typeOptions = ref<any[]>([]);
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
    title: '项目名称',
    align: 'center',
    key: 'itemName'
  },
  {
    title: '设备类型',
    align: 'center',
    key: 'typeName'
  },
  {
    title: '项目内容',
    align: 'center',
    key: 'itemContent'
  },
  {
    title: '完成标准',
    align: 'center',
    key: 'completionStandard'
  },
  {
    title: '是否启用',
    key: 'ifEnable',
    align: 'center',
    render: row => {
      return (
        <my-switch
          v-model:value={row.ifEnable}
          checked={1}
          unchecked={0}
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
    title: '备注',
    align: 'center',
    key: 'remark'
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
          deleteItem(row).then(res => {
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
const addFormRef = ref<FormInst | null>(null);
const checked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};

// 弹窗
const dialogFlag = ref<boolean>(false);
const handleCancel = () => {
  dialogFlag.value = false;
  addForm.value = {
    itemName: '',
    typeId: null,
    typeName: null,
    itemContent: '',
    ifEnable: 0,
    distributionPath: '',
    completionStandard: '',
    remark: ''
  };
};
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addItem(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editItem(addForm.value).then((res: any) => {
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
async function changeStatus(row: any, val: number) {
  row.ifEnable = val;
  await editItem(row).then(res => {
    if (res.code === 200) {
      message.success(res.msg);
      handleCancel();
    }
  });
  init();
}
function typeHandleChange(val, item) {
  addForm.value.typeId = val;
  addForm.value.typeName = item.label;
}
// 深度clone
function deepClone(obj: any) {
  return JSON.parse(JSON.stringify(obj));
}
function init() {
  startLoading();
  data.value = [];
  getItemList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  queryEquipmentTypeAll(null).then(res => {
    if (res.code === 200) {
      typeOptions.value = [];
      res.data.forEach((item: any) => {
        typeOptions.value.push({
          label: item.typeName,
          value: item.id
        });
      });
    }
  });
  init();
});
</script>

<style scoped></style>
