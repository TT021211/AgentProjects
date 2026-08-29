<template>
  <div class="h-full">
    <my-card title="搜索条件" search>
      <n-form ref="formRef" inline label-width="auto" :model="formValue" size="medium">
        <n-form-item label="部门名称">
          <n-input v-model:value="formValue.deptName" placeholder="输入部门名称" />
        </n-form-item>
        <n-form-item label="状态">
          <n-select v-model:value="statusSelect.value" :options="statusSelect.options" style="width: 200px" />
        </n-form-item>
        <n-form-item>
          <n-button type="primary" class="mr-10px" @click="handleValidateClick">
            <icon-ic-round-search class="mr-4px text-20px" />
            搜索
          </n-button>
          <n-button @click="reset">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="部门列表">
      <template #right>
        <div class="flex-center">
          <n-switch v-model:value="expandedSwitchshow" class="mr-5px" @update:value="handleSwitchChange">
            <template #checked>展开</template>
            <template #unchecked>折叠</template>
          </n-switch>
          <n-button v-hasPermi="['system:dept:add']" size="small" class="mr-5px" type="primary" @click="handleAddTable">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增
          </n-button>
          <n-button class="mr-5px" type="error" size="small" :disabled="isBacthDelete" @click="showModal = true">
            <icon-ic-round-delete class="mr-4px text-20px" />
            删除
          </n-button>

          <n-button class="mr-5px" size="small" type="primary" @click="getDeptData">
            <icon-mdi-refresh class="mr-4px text-16px" :class="{ 'animate-spin': loading }" />
            刷新表格
          </n-button>
          <column-setting v-model:columns="columns" size="small" />
        </div>
      </template>
      <div>
        <n-data-table
          :columns="columns"
          :data="deptList"
          :row-key="rowKey"
          :expanded-row-keys="defExpand"
          :loading="loading"
          :on-update:expanded-row-keys="changeExpandedRow"
          @update:checked-row-keys="handleCheck"
        />
      </div>
      <table-action-modal
        v-model:visible="visible"
        :type="modalType"
        :tree-data="menuOptions"
        :edit-data="editData"
        :dept-id="addDeptId"
        :get-list="getDeptData"
      />
      <n-modal
        v-model:show="showModal"
        :mask-closable="false"
        preset="dialog"
        title="删除用户"
        content="你真的要删除这些部门吗？"
        positive-text="确认"
        negative-text="算了"
        @positive-click="onPositiveClick"
      />
    </my-card>
  </div>
</template>

<script lang="tsx" setup>
import { ref } from 'vue';
import type { Ref } from 'vue';
import { NTag, NButton, useMessage } from 'naive-ui';
import type { DataTableColumns, DataTableRowKey } from 'naive-ui';
// import { ReorderThreeOutline } from '@vicons/ionicons5';
import { deptStatusLabels } from '@/constants';
import { listDept, getDept, listDeptExcludeChild, delDept } from '@/service';
import { useLoading, useBoolean } from '@/hooks';
import { handleTree } from '@/utils/form';
import type { ModalType } from './components/table-action-modal.vue';
import TableActionModal from './components/table-action-modal.vue';

// 控制展开
// const collapseFlag = ref<boolean>(false);
const deptList = ref();
const rowKey = row => row.deptId;
const defExpand = ref<number[]>([]);
const allParentIds = ref<number[]>([]);
const { bool: visible, setTrue: openModal } = useBoolean();
const modalType = ref<ModalType>('add');
const expandedSwitchshow = ref(true);
const menuOptions = ref();
const message = useMessage();
const showModal = ref(false);
const editData = ref();
const delIds = ref();
const isBacthDelete = ref(true);
const addDeptId = ref();
const formValue = ref({
  deptName: undefined,
  status: undefined
});

const columns: Ref<DataTableColumns<DeptManagement.DeptType>> = ref([
  {
    type: 'selection',
    align: 'center'
  },
  {
    title: '部门编号',
    key: 'deptId',
    align: 'center'
  },
  {
    title: '部门名称',
    key: 'deptName',
    align: 'center'
  },
  {
    title: '部门排序',
    key: 'orderNum',
    align: 'center'
  },
  {
    title: '状态',
    key: 'status',
    render: row => {
      if (row.status) {
        const tagTypes: Record<DeptManagement.DeptStatusKey, NaiveUI.ThemeColor> = {
          0: 'success',
          1: 'error'
        };

        return <NTag type={tagTypes[row.status]}>{deptStatusLabels[row.status]}</NTag>;
      }
      return <span></span>;
    },
    align: 'center'
  },
  {
    title: '创建时间',
    key: 'createTime',
    align: 'center'
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    render: row => {
      return (
        <n-space justify={'center'}>
          <div v-hasPermi={['system:dept:edit']}>
            <n-button size={'tiny'} color="#4cb8ff" onClick={() => handleEditTable(row.deptId)}>
              <icon-tdesign-edit class="mr-2px text-15px " />
              编辑
            </n-button>
          </div>
          <div v-hasPermi={['system:dept:add']}>
            <n-button size={'tiny'} type="primary" onClick={() => handleAddTable(row.deptId)}>
              <icon-ic-round-plus class="mr-2px text-15px " />
              新增
            </n-button>
          </div>
          <div v-hasPermi={['system:dept:remove']}>
            <n-popconfirm onPositiveClick={() => handleDeleteTable(row.deptId)}>
              {{
                default: () => '确认删除',
                trigger: () => (
                  <n-button size={'tiny'} color="#ed8182">
                    <icon-tdesign-delete-1 class="mr-2px text-15px " />
                    删除
                  </n-button>
                )
              }}
            </n-popconfirm>
          </div>
        </n-space>
      );
    }
  }
]) as Ref<DataTableColumns<DeptManagement.DeptType>>;

const statusSelect = ref({
  value: null,
  options: [
    {
      label: '正常',
      value: 'normal'
    },
    {
      label: '停用',
      value: 'error'
    }
  ]
});

const { loading, startLoading, endLoading } = useLoading(false);
// const { setTrue: openModal } = useBoolean();

// 搜索
function handleValidateClick() {
  getDeptData();
}

// 重置
function reset() {
  for (const key in formValue.value) {
    if (formValue.value[key] !== '') {
      formValue.value[key] = '';
    }
  }
  getDeptData();
}
// 折叠列表
function changeExpandedRow(rows: number[]) {
  if (!arraysEqual(allParentIds.value, rows)) {
    expandedSwitchshow.value = false;
  } else {
    expandedSwitchshow.value = true;
  }
  defExpand.value = rows;
}

function arraysEqual(arr1, arr2) {
  if (arr1.length !== arr2.length) {
    return false;
  }

  const set1 = new Set(arr1);
  const set2 = new Set(arr2);

  for (const item of set1) {
    if (!set2.has(item)) {
      return false;
    }
  }

  return true;
}
// 改变折叠框
function handleSwitchChange() {
  if (expandedSwitchshow.value) {
    // console.log('展开');
    defExpand.value = allParentIds.value;
    // console.log(allRowKeysArr);
  } else {
    // console.log('折叠');
    defExpand.value = [];
  }
}
function setModalType(type: ModalType) {
  modalType.value = type;
}

// 新增用户
function handleAddTable(deptId) {
  setModalType('add');
  if (deptId !== undefined) {
    addDeptId.value = deptId;
  }
  listDept({ params: '' }).then(response => {
    menuOptions.value = handleTree(response.data, 'deptId', 'parentId', 'children');
  });
  openModal();
}
// 编辑
async function handleEditTable(deptId: number) {
  await getDept(deptId).then(res => {
    setEditData(res.data);
  });
  listDeptExcludeChild(deptId).then(res => {
    menuOptions.value = handleTree(res.data, 'deptId', 'parentId', 'children');
  });
  setModalType('edit');
  openModal();
}
function setEditData(data) {
  editData.value = data;
}
// 获取用户信息
function getDeptData() {
  startLoading();
  listDept({ params: formValue.value }).then(res => {
    defExpand.value = [];
    const parentsIds = getAllParentIds(res.data);
    defExpand.value = parentsIds as number[];
    allParentIds.value = parentsIds as number[];
    deptList.value = handleTree(res.data, 'deptId', 'parentId', 'children');

    if (deptList.value) {
      setTimeout(() => {
        endLoading();
      }, 500);
    }
  });
}
// 删除部门
function handleDeleteTable(deptId: number) {
  delDept(deptId).then(() => {
    getDeptData();
    message.success('删除成功');
  });
}
function getAllParentIds(res) {
  const parentsIds = new Set();
  res.forEach(item => {
    parentsIds.add(item.parentId);
  });

  return [...parentsIds];
}
// 批量删除

function onPositiveClick() {
  delDept(delIds.value).then(() => {
    getDeptData();
    message.success('删除成功');
  });
}
function handleCheck(rowKeys: DataTableRowKey[]) {
  if (rowKeys.length > 0) {
    isBacthDelete.value = false;
    delIds.value = rowKeys;
  } else {
    isBacthDelete.value = true;
  }
}
function init() {
  getDeptData();
}
init();
</script>
