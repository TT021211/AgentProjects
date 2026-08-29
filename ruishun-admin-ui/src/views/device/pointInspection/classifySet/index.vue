<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="项目类型名称">
          <n-input v-model:value="searchForm.itemTypeName" placeholder="请输入项目类型名称"></n-input>
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
    <my-card title="项目类型列表">
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
      :title="flag ? '新增项目类型' : '编辑项目类型'"
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
            <n-form-item label="类型" :span="12" path="type">
              <n-radio-group v-model:value="addForm.type" name="left-size" style="margin-bottom: 12px">
                <n-radio-button value="0">点检</n-radio-button>
                <n-radio-button value="1">保养</n-radio-button>
              </n-radio-group>
            </n-form-item>
            <n-form-item label="项目名称" :span="12" path="itemTypeName">
              <n-input v-model:value="addForm.itemTypeName" placeholder="请输入项目类型名称"></n-input>
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
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { getItemTypeList, deleteItemType, editItemType, addItemType } from '@/service/api/device/classifySet';
import { queryEquipmentTypeAll } from '@/service/api/device/facilityCategorySet';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();
const router = useRouter();
type searchType = {
  itemTypeName: string;
  pageNum: number;
  pageSize: number;
  total: number;
  typeId: string | null;
};

const rules = {
  type: [
    {
      required: true,
      message: '请选择点检保养类型'
    }
  ],
  itemTypeName: [
    {
      required: true,
      message: '请输入项目类型名称'
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
  itemTypeName: '',
  pageNum: 1,
  pageSize: 10,
  typeId: null,
  total: 0
});
type addFormType = {
  itemTypeName: string;
  type: string | null;
  typeId: string | null;
  typeName: string | null;
};
const addForm = ref<addFormType>({
  itemTypeName: '',
  type: '0',
  typeId: null,
  typeName: null
});
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    itemTypeName: '',
    pageNum: 1,
    pageSize: 10,
    typeId: null,
    total: 0
  };
  init();
}
type DataType = {
  id: number;
  itemTypeName: string;
  typeName: string;
  ifEnable: string;
  totalItem: number;
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
    title: '项目类型',
    align: 'center',
    key: 'itemTypeName'
  },
  {
    title: '点检/保养',
    align: 'center',
    key: 'type',
    render: (row: any) => (
      <n-tag type={row.type === '0' ? 'success' : 'warning'}>{row.type === '0' ? '点检' : '保养'}</n-tag>
    )
  },
  {
    title: '设备类型',
    align: 'center',
    key: 'typeName'
  },
  {
    title: '操作',
    align: 'center',
    key: 'actions',
    render: row => {
      return [
        <n-badge color="rgba(82, 196, 26,0.9)" class="mr-18px mb-2" v-model:value={row.totalItem}>
          <n-button
            size="tiny"
            type="warning"
            onClick={() => {
              router.push({
                name: 'device_pointInspection_classifySetItem',
                query: { id: row.id, groupForm: JSON.stringify(row) }
              });
            }}
          >
            <icon-tdesign-edit class="mr-2" />
            分配项目
          </n-button>
        </n-badge>,
        useEditBtn(() => {
          handleEdit(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteItemType(row).then(res => {
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
    itemTypeName: '',
    type: '0',
    typeId: null,
    typeName: null
  };
};
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addItemType(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editItemType(addForm.value).then((res: any) => {
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
  getItemTypeList(searchForm.value).then(res => {
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
