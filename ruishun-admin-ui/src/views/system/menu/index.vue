<template>
  <div class="h-full">
    <my-card title="搜索条件" search>
      <n-form ref="formRef" inline label-width="auto" :model="formValue" size="medium">
        <n-form-item label="菜单名称">
          <n-input v-model:value="formValue.menuName" placeholder="输入菜单名称" />
        </n-form-item>
        <n-form-item label="状态">
          <n-select v-model:value="formValue.status" :options="statusSelect.options" style="width: 200px" />
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
    <my-card title="菜单列表">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button
            v-hasPermi="['system:menu:add']"
            type="primary"
            size="small"
            class="mr-5px ml-5px"
            @click="handleAddTable"
          >
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增
          </n-button>

          <n-button type="error" size="small" :disabled="isBacthDelete" class="mr-5px" @click="showModal = true">
            <icon-ic-round-delete class="mr-4px text-20px" />
            删除
          </n-button>
          <n-button size="small" class="mr-5px" type="primary" @click="getMenuData">
            <icon-mdi-refresh class="mr-4px text-16px" :class="{ 'animate-spin': loading }" />
            刷新表格
          </n-button>
        </div>
      </template>
      <n-data-table
        :columns="columns"
        :data="menuList"
        :row-key="rowKey"
        :expanded-row-keys="defExpand"
        :loading="loading"
        :default-expand-all="true"
        :scroll-x="1000"
        :on-update:expanded-row-keys="changeExpandedRow"
        @update:checked-row-keys="handleCheck"
      />

      <table-action-modal
        v-model:visible="visible"
        :type="modalType"
        :tree-data="menuOptions"
        :edit-data="editData"
        :menu-id="addMenuId"
        :get-list="getMenuData"
      />
      <n-modal
        v-model:show="showModal"
        :mask-closable="false"
        preset="dialog"
        title="删除菜单"
        content="你真的要删除这些菜单吗？"
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
import { deptStatusLabels } from '@/constants';
import { listMenu, getMenu, delMenu } from '@/service';
import { useLoading, useBoolean } from '@/hooks';
import { handleTree } from '@/utils/form';
import type { ModalType } from './components/table-action-modal.vue';
import TableActionModal from './components/table-action-modal.vue';

const menuList = ref();
const rowKey = row => row.menuId;
const defExpand = ref<number[]>([]);
const allParentIds = ref<number[]>([]);
const { bool: visible, setTrue: openModal } = useBoolean();
const modalType = ref<ModalType>('add');
const expandedSwitchshow = ref(false);
const menuOptions = ref();
const message = useMessage();
const showModal = ref(false);
const editData = ref();
const delIds = ref();
const isBacthDelete = ref(true);
const addMenuId = ref();
const formValue = ref({
  menuName: undefined,
  status: undefined
});
function getImg(name) {
  return new URL(name, import.meta.url).href;
}
const columns: Ref<DataTableColumns<MenuManagement.MenuType>> = ref([
  {
    type: 'selection',
    align: 'center'
  },
  {
    title: '菜单编号',
    key: 'menuId',
    align: 'center'
  },
  {
    title: '菜单名称',
    key: 'menuName',
    align: 'center'
  },
  {
    title: '菜单图标',
    key: 'icon',
    render: row => {
      if (row.menuType === 'C' || row.menuType === 'M') {
        return <img src={getImg(`../${row.icon}`)} class="text-25px center" style="margin:auto" />;
      }
      return '';
    },
    align: 'center'
  },
  {
    title: '显示顺序',
    key: 'orderNum',
    align: 'center'
  },
  {
    title: '权限标识',
    key: 'perms',
    align: 'center'
  },
  {
    title: '菜单路径',
    key: 'component',
    align: 'center'
  },

  {
    title: '状态',
    key: 'status',
    render: row => {
      // const isBorder = ref(true);
      if (row.status) {
        const tagTypes: Record<MenuManagement.MenuStatusKey, NaiveUI.ThemeColor> = {
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
            onClick={() => handleEditTable(row.menuId)}
            v-hasPermi={['system:menu:edit']}
          >
            <icon-tdesign-edit class="mr-2px text-15px " />
            编辑
          </n-button>
          <n-button
            size={'tiny'}
            type="primary"
            onClick={() => handleAddTable(row.menuId)}
            v-hasPermi={['system:menu:add']}
          >
            <icon-ic-round-plus class="mr-2px text-15px " />
            新增
          </n-button>
          <n-popconfirm onPositiveClick={() => handleDeleteTable(row.menuId)}>
            {{
              default: () => '确认删除',
              trigger: () => (
                <n-button size={'tiny'} color="#ed8182" v-hasPermi={['system:menu:remove']}>
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
]) as Ref<DataTableColumns<MenuManagement.MenuType>>;

const statusSelect = ref({
  value: null,
  options: [
    {
      label: '正常',
      value: '0'
    },
    {
      label: '禁用',
      value: '1'
    }
  ]
});

const { loading, startLoading, endLoading } = useLoading(false);
// const { setTrue: openModal } = useBoolean();

// 搜索
function handleValidateClick() {
  getMenuData();
}

// 重置
function reset() {
  for (const key in formValue.value) {
    if (formValue.value[key] !== '') {
      formValue.value[key] = '';
    }
  }
  getMenuData();
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
// function handleSwitchChange() {
//   if (expandedSwitchshow.value) {
//     defExpand.value = allParentIds.value;
//   } else {
//     defExpand.value = [];
//   }
// }
function setModalType(type: ModalType) {
  modalType.value = type;
}

// 新增用户
function handleAddTable(menuId) {
  if (menuId !== undefined) {
    addMenuId.value = menuId;
  }
  setModalType('add');
  getTreeList();

  openModal();
}
async function getTreeList() {
  await listMenu({ params: '' }).then(res => {
    menuOptions.value = [];
    const menu = { menuId: 0, menuName: '主目录', children: [] };
    menu.children = handleTree(res.data, 'menuId', 'parentId', 'children');
    menuOptions.value.push(menu);
  });
}
// 编辑
async function handleEditTable(menuId: number) {
  await getMenu(menuId).then(res => {
    setEditData(res.data);
    getTreeList();
    setModalType('edit');
    openModal();
  });
}
function setEditData(data) {
  editData.value = data;
}
// 获取用户信息
function getMenuData() {
  startLoading();
  listMenu({ params: formValue.value }).then(res => {
    defExpand.value = [];
    const parentsIds = getAllParentIds(res.data);

    // defExpand.value = parentsIds as number[];
    allParentIds.value = parentsIds as number[];
    menuList.value = handleTree(res.data, 'menuId', 'parentId', 'children');
    if (menuList.value) {
      setTimeout(() => {
        endLoading();
      }, 200);
    }
  });
}
// 删除部门
function handleDeleteTable(menuId: number) {
  delMenu(menuId).then(() => {
    getMenuData();
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
  delMenu(delIds.value).then(() => {
    getMenuData();
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
  getMenuData();
}
init();
</script>
