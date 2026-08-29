<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="组别名称">
          <n-input v-model:value="searchForm.groupName" placeholder="请输入组别名称"></n-input>
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
      :title="flag ? '新增项目组别' : '编辑项目组别'"
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
            <n-form-item label="组别名称" :span="12" path="groupName">
              <n-input v-model:value="addForm.groupName" placeholder="请输入组别名称"></n-input>
            </n-form-item>
            <n-form-item label="项目类型" :span="12" path="itemTypeId">
              <n-select
                v-model:value="addForm.itemTypeId"
                class="w-180px"
                :options="typeOptions"
                placeholder="请选择项目类型"
                @update:value="typeHandleChange"
              />
            </n-form-item>
            <n-form-item label="所属班组" :span="12" path="workgroupId">
              <n-select
                v-model:value="addForm.workgroupId"
                class="w-180px"
                :options="groupOptions"
                placeholder="请选择所属班组"
                @update:value="groupHandleChange"
              />
            </n-form-item>
            <n-form-item label="负责人" :span="12" path="headId">
              <n-select
                v-model:value="addForm.headId"
                class="w-180px"
                :options="headOptions"
                placeholder="请选择负责人"
                @update:value="headHandleChange"
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
import { getGroupList, deleteGroup, editGroup, addGroup } from '@/service/api/device/group';
import { queryItemTypeAll } from '@/service/api/device/classifySet';
import { getWorkgroupAll, getWorkgroupUserList } from '@/service/api/md/workgroup';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();
const router = useRouter();
type searchType = {
  groupName: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
type headSearchType = {
  workgroupId: string | null;
  pageNum: number;
  pageSize: number;
  total: number;
};
const rules = {
  groupName: [
    {
      required: true,
      message: '请输入组别名称'
    }
  ],
  itemTypeId: [
    {
      required: true,
      message: '请选择项目类型'
    }
  ],
  workgroupId: [
    {
      required: true,
      message: '请选择所属班组'
    }
  ],
  headId: [
    {
      required: true,
      message: '请选择负责人'
    }
  ]
};
const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  groupName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const headSearchForm = ref<headSearchType>({
  workgroupId: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
type addFormType = {
  groupName: string;
  workgroupId: string | null;
  workgroupName: string;
  itemTypeId: string | null;
  itemTypeName: string;
  headId: string | null;
  headName: string;
};
const addForm = ref<addFormType>({
  groupName: '',
  workgroupId: null,
  workgroupName: '',
  itemTypeId: null,
  itemTypeName: '',
  headId: null,
  headName: ''
});
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    groupName: '',
    pageNum: 1,
    pageSize: 100,
    total: 0
  };
  init();
}
type DataType = {
  id: number;
  groupName: string;
  itemTypeName: string;
  workgroupName: string;
  headName: string;
  totalEquipment: number;
};
const typeOptions = ref<any[]>([]);
const groupOptions = ref<any[]>([]);
const headOptions = ref<any[]>([]);
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
    title: '组别名称',
    align: 'center',
    key: 'groupName'
  },
  {
    title: '项目类型',
    align: 'center',
    key: 'itemTypeName'
  },
  {
    title: '所属班组',
    align: 'center',
    key: 'workgroupName'
  },
  {
    title: '负责人',
    align: 'center',
    key: 'headName'
  },
  {
    title: '操作',
    align: 'center',
    key: 'actions',
    render: row => {
      return [
        <n-badge color="rgba(82, 196, 26,0.9)" class="mr-18px mb-2" v-model:value={row.totalEquipment}>
          <n-button
            size="tiny"
            type="warning"
            onClick={() => {
              router.push({
                name: 'device_pointInspection_groupEquipment',
                query: { id: row.id, groupForm: JSON.stringify(row) }
              });
            }}
          >
            <icon-tdesign-edit class="mr-2" />
            分配设备
          </n-button>
        </n-badge>,
        useEditBtn(() => {
          handleEdit(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteGroup(row).then(res => {
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
  headOptions.value = [];
  addForm.value = {
    groupName: '',
    workgroupId: null,
    workgroupName: '',
    itemTypeId: null,
    itemTypeName: '',
    headId: null,
    headName: ''
  };
};
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addGroup(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editGroup(addForm.value).then((res: any) => {
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
  addForm.value.itemTypeId = val;
  addForm.value.itemTypeName = item.label;
}
function groupHandleChange(val, item) {
  addForm.value.workgroupId = val;
  addForm.value.workgroupName = item.label;
  headSearchForm.value.workgroupId = val;
  getWorkgroupUserList(headSearchForm.value).then(res => {
    if (res.code === 200) {
      headOptions.value = [];
      res.data.forEach((items: any) => {
        headOptions.value.push({
          label: items.memberName,
          value: items.memberId
        });
      });
    }
  });
}
function headHandleChange(val, item) {
  addForm.value.headId = val;
  addForm.value.headName = item.label;
}
// 深度clone
function deepClone(obj: any) {
  return JSON.parse(JSON.stringify(obj));
}
function init() {
  startLoading();
  data.value = [];
  getGroupList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  queryItemTypeAll(null).then(res => {
    if (res.code === 200) {
      typeOptions.value = [];
      res.data.forEach((item: any) => {
        typeOptions.value.push({
          label: item.itemTypeName,
          value: item.id
        });
      });
    }
  });
  getWorkgroupAll().then(res => {
    if (res.code === 200) {
      groupOptions.value = [];
      res.data.forEach((item: any) => {
        groupOptions.value.push({
          label: item.workgroupName,
          value: item.id
        });
      });
    }
  });
  headOptions.value = [];
  init();
});
</script>

<style scoped></style>
