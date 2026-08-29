<template>
  <div class="h-full">
    <n-card :bordered="false" class="h-full rounded-8px shadow-sm">
      <n-layout class="content_user">
        <n-layout-header>
          <n-collapse>
            <template #arrow>
              <n-icon size="40">
                <ReorderThreeOutline />
              </n-icon>
            </template>

            <n-collapse-item title="展开" name="1">
              <div>
                <n-form ref="formRef" inline label-width="auto" :model="formValue" size="medium">
                  <n-form-item label="模具分类">
                    <n-select v-model:value="statusSelect.value" :options="statusSelect.options" style="width: 200px" />
                  </n-form-item>
                  <n-form-item label="状态">
                    <n-select v-model:value="statusSelect.value" :options="statusSelect.options" style="width: 200px" />
                  </n-form-item>
                  <n-form-item>
                    <n-button
                      attr-type="button"
                      style="margin-right: 10px"
                      round
                      strong
                      secondary
                      type="primary"
                      @click="handleValidateClick"
                    >
                      <icon-tdesign-file-search class="mr-8px text-15px" />
                      搜索
                    </n-button>
                    <n-button attr-type="button" round strong secondary @click="reset">
                      <icon-tdesign-arrow-left-right-3 class="mr-8px text-15px" />
                      重置
                    </n-button>
                  </n-form-item>
                </n-form>
              </div>
            </n-collapse-item>
          </n-collapse>
        </n-layout-header>
        <n-layout-content>
          <div class="overflow-hidden">
            <div class="flex-col h-full">
              <n-space class="pb-12px" justify="space-between">
                <n-space>
                  <n-button v-hasPermi="['system:dept:add']" type="primary" @click="handleAddTable">
                    <icon-ic-round-plus class="mr-4px text-20px" />
                    新增
                  </n-button>
                  <n-button type="error" ghost :disabled="isBacthDelete" @click="showModal = true">
                    <icon-ic-round-delete class="mr-4px text-20px" />
                    删除
                  </n-button>
                </n-space>
                <n-space align="center" :size="18">
                  <n-switch v-model:value="expandedSwitchshow" @update:value="handleSwitchChange">
                    <template #checked>展开</template>
                    <template #unchecked>折叠</template>
                  </n-switch>
                  <n-button size="small" type="primary" @click="getDeptData">
                    <icon-mdi-refresh class="mr-4px text-16px" :class="{ 'animate-spin': loading }" />
                    刷新表格
                  </n-button>
                  <column-setting v-model:columns="columns" size="small" />
                </n-space>
              </n-space>
            </div>
          </div>
          <!-- 表格数据 -->
          <div>
            <n-data-table
              :columns="columns"
              :data="deptList"
              :row-key="rowKey"
              :expanded-row-keys="defExpand"
              :loading="loading"
              :default-expand-all="true"
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
        </n-layout-content>
      </n-layout>
    </n-card>
  </div>
</template>

<script lang="tsx" setup>
import { ref } from 'vue';
import type { Ref } from 'vue';
import { NIcon, NSpace, NTag, NButton, useMessage } from 'naive-ui';
import type { DataTableColumns, DataTableRowKey } from 'naive-ui';
import { ReorderThreeOutline } from '@vicons/ionicons5';
import { deptStatusLabels } from '@/constants';
import { listDept, getDept, listDeptExcludeChild, delDept, getMouldClassify } from '@/service';
import { useLoading, useBoolean } from '@/hooks';
import { handleTree } from '@/utils/form';
import type { ModalType } from './components/table-action-modal.vue';
import TableActionModal from './components/table-action-modal.vue';

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
          <n-button
            size={'tiny'}
            color="#4cb8ff"
            onClick={() => handleEditTable(row.deptId)}
            v-hasPermi={['system:dept:edit']}
          >
            <icon-tdesign-edit class="mr-2px text-15px " />
            编辑
          </n-button>
          <n-button
            size={'tiny'}
            color="#73ff00"
            onClick={() => handleAddTable(row.deptId)}
            v-hasPermi={['system:dept:add']}
          >
            <icon-ic-round-plus class="mr-2px text-15px " />
            新增
          </n-button>
          <n-popconfirm onPositiveClick={() => handleDeleteTable(row.deptId)} v-hasPermi={['system:dept:remove']}>
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

// const mouldClassifySelect=ref({

// })

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
function getClassifySelect() {
  getMouldClassify().then(res => {
    // eslint-disable-next-line no-console
    console.log(res);
  });
}
function init() {
  getDeptData();
  getClassifySelect();
}
init();
</script>
