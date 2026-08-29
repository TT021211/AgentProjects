<template>
  <div class="h-full">
    <my-card title="搜索条件" search>
      <n-form ref="formRef" inline label-width="auto" :model="formValue" :rules="rules" size="medium">
        <n-form-item label="角色名称">
          <n-input v-model:value="formValue.roleName" placeholder="输入姓名" />
        </n-form-item>
        <n-form-item label="权限字符">
          <n-input v-model:value="formValue.roleKey" placeholder="输入字符权限" />
        </n-form-item>

        <n-form-item label="创建时间">
          <n-date-picker v-model:value="daterange" type="daterange" />
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
    <my-card title="角色列表">
      <template #right>
        <div class="flex center">
          <column-setting v-model:columns="columns" size="small" />
          <n-button
            v-hasPermi="['system:role:add']"
            class="ml-4px mr-5px"
            type="primary"
            size="small"
            @click="handleAddTable"
          >
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增
          </n-button>
          <n-button
            v-hasPermi="['system:role:remove']"
            class="mr-4px"
            type="error"
            size="small"
            :disabled="isBacthDelete"
            @click="showModal = true"
          >
            <icon-ic-round-delete class="mr-4px text-20px" />
            删除
          </n-button>
          <n-button
            v-hasPermi="['system:role:export']"
            class="mr-4px"
            type="success"
            size="small"
            @click="handleExport"
          >
            <icon-uil:export class="mr-4px text-20px" />
            导出Excel
          </n-button>
          <n-button class="mr-4px" size="small" type="primary" @click="getRoleData">
            <icon-mdi-refresh class="mr-4px text-16px" :class="{ 'animate-spin': loading }" />
            刷新表格
          </n-button>
        </div>
      </template>
      <div>
        <n-data-table
          :columns="columns"
          :data="roleList"
          :loading="loading"
          :row-key="rowKey"
          :scroll-x="1000"
          @update:checked-row-keys="handleCheck"
        />
        <n-pagination
          :page="page"
          :page-size="formValue.pageSize"
          :item-count="total"
          :page-sizes="[10, 20, 50, 100]"
          style="margin-top: 20px; float: right"
          show-size-picker
          @update:page="changePage"
          @update:page-size="changePageSize"
        >
          <template #prefix="{ itemCount }">共 {{ itemCount }} 条</template>
        </n-pagination>
      </div>
      <n-modal
        v-model:show="showModal"
        :mask-closable="false"
        preset="dialog"
        title="删除用户"
        content="你真的要删除这些用户吗？"
        positive-text="确认"
        negative-text="算了"
        @positive-click="onPositiveClick"
        @negative-click="onNegativeClick"
      />
      <table-action-modal
        v-model:visible="visible"
        :type="modalType"
        :edit-data="editData"
        :tree-data="menuOptions"
        :get-list="getRoleData"
        :checked-keys="checkedKeys"
      />
      <!-- 分配数据权限 -->
      <n-drawer v-model:show="active" :width="502">
        <n-drawer-content>
          <template #header>分配数据权限</template>
          <template #default>
            <n-form ref="formRef" :label-width="80" :model="formModel" :rules="rules">
              <n-grid :cols="24" :x-gap="18">
                <n-form-item-grid-item :span="12" label="角色名称">
                  <n-input v-model:value="formModel.roleName" :disabled="true" />
                </n-form-item-grid-item>
                <n-form-item-grid-item :span="12" label="权限字符">
                  <n-input v-model:value="formModel.roleKey" :disabled="true" />
                </n-form-item-grid-item>
                <n-form-item-grid-item :span="12" label="权限范围">
                  <n-select
                    v-model:value="formModel.dataScope"
                    :options="scopeOptions.options"
                    @update:value="changeScope"
                  />
                </n-form-item-grid-item>

                <n-form-item-grid-item v-if="formModel.dataScope == 2" :span="12" label="菜单权限">
                  <n-space>
                    <n-switch :rail-style="expand">
                      <template #checked>折叠</template>
                      <template #unchecked>展开</template>
                    </n-switch>
                    <n-switch v-model:value="selectedSwitchshow" @update:value="handleSwitchChange">
                      <template #checked>全选</template>
                      <template #unchecked>不选</template>
                    </n-switch>
                    <n-tree
                      block-line
                      :cascade="false"
                      checkable
                      cancelable
                      virtual-scroll
                      style="height: 200px; width: 200px"
                      key-field="id"
                      :data="deptOptions"
                      :default-expand-all="isExpand"
                      :checked-keys="defaultCheckedKeys"
                      @update:checked-keys="updateCheckedKeys"
                    />
                  </n-space>
                </n-form-item-grid-item>
              </n-grid>
            </n-form>
          </template>

          <template #footer>
            <n-button style="margin-right: 10px" @click="submitDataScope">提交</n-button>
            <n-button @click="active = false">取消</n-button>
          </template>
        </n-drawer-content>
      </n-drawer>
    </my-card>
  </div>
</template>

<script lang="tsx" setup>
import type { Ref, CSSProperties } from 'vue';
import { ref } from 'vue';
import { NSwitch, NSpace, NButton, NPopconfirm, useMessage, NPopselect } from 'naive-ui';
import type { DataTableColumns, DataTableRowKey } from 'naive-ui';
// import { ReorderThreeOutline, Sync } from '@vicons/ionicons5';
import { routeName } from '@/router';
import {
  fetchRoleList,
  changeRoleStatus,
  delRole,
  treeselect as menuTreeselect,
  getRole,
  roleMenuTreeselect,
  deptRoleTreeSelect,
  dataScope
} from '@/service';
import { useRouterPush } from '@/composables';
import { useLoading, useBoolean } from '@/hooks';
import { formatDate, addDateRange } from '@/utils';
import { download } from '@/service/request/helpers';
import ColumnSetting from './components/column-setting.vue';
import type { ModalType } from './components/table-action-modal.vue';
import TableActionModal from './components/table-action-modal.vue';

// const collapseFlag = ref<boolean>(false);
const modalType = ref<ModalType>('add');
const checkedRowKeysRef = ref<DataTableRowKey[]>([]);
const daterange = ref();
const editData = ref(null);
const rowKey = row => row.roleId;
const format = 'yyyy-MM-dd';
const roleList = ref();
const page = ref(1);
const total = ref();
const show = ref(false);
const menuOptions = ref();
const deptOptions = ref();
const selectedSwitchshow = ref(false);
const checkedKeys = ref();
const isBacthDelete = ref(true);
const showModalRef = ref(false);
const showModal = showModalRef;
const delIds = ref();
const rowData = ref();
const { bool: visible, setTrue: openModal } = useBoolean();
const message = useMessage();
const active = ref(false);
const isExpand = ref(false);
const menuId: number[] = [];
const defaultCheckedKeys = ref();
const { routerPush } = useRouterPush();
const formValue = ref({
  pageNum: 1,
  pageSize: 10,
  roleName: undefined,
  roleKey: undefined,
  status: undefined
});
const statusSelect = ref({
  value: null,
  options: [
    {
      label: '正常',
      value: '0'
    },
    {
      label: '停用',
      value: '1'
    }
  ]
});
const scopeOptions = ref({
  value: null,
  options: [
    {
      value: '1',
      label: '全部数据权限'
    },
    {
      value: '2',
      label: '自定数据权限'
    },
    {
      value: '3',
      label: '本部门数据权限'
    },
    {
      value: '4',
      label: '本部门及以下数据权限'
    },
    {
      value: '5',
      label: '仅本人数据权限'
    }
  ]
});
type treeType = {
  id: number;
  label: string;
  children?: treeType[];
};
const rules = {
  user: {
    name: {
      required: true,
      message: '请输入姓名',
      trigger: 'blur'
    },
    age: {
      required: true,
      message: '请输入年龄',
      trigger: ['input', 'blur']
    }
  },
  phone: {
    required: true,
    message: '请输入电话号码',
    trigger: ['input']
  }
};
const moreOptions = [
  {
    label: '数据权限',
    value: 'dataAuth'
  },
  {
    label: '分配用户',
    value: 'dispathUser'
  }
];
const columns: Ref<DataTableColumns<RoleManagement.RoleType>> = ref([
  {
    type: 'selection',
    align: 'center'
  },
  {
    title: '角色编号',
    key: 'roleId',
    align: 'center'
  },
  {
    title: '角色名称',
    key: 'roleName',
    align: 'center'
  },

  {
    title: '权限字符',
    key: 'roleKey',
    align: 'center'
  },
  {
    title: '显示顺序',
    key: 'roleSort',
    align: 'center'
  },
  {
    title: '状态',
    key: 'status',
    align: 'center',
    render: row => {
      return (
        <NPopconfirm onPositiveClick={() => handlePositiveClick(row)} onNegativeClick={() => handleNegativeClick(row)}>
          {{
            default: () => `您确定要${row.status ? '启用' : '禁用'}${row.roleName}吗？`,
            trigger: () => <NSwitch v-model:value={row.status} />
          }}
        </NPopconfirm>
      );
    }
  },
  {
    title: '创建时间',
    key: 'createTime',
    align: 'center'
  },
  {
    title: '操作',
    key: 'actions',
    render: row => {
      return (
        <n-space justify={'center'}>
          <n-button
            size={'tiny'}
            color="#4cb8ff"
            onClick={() => handleEditTable(row.roleId)}
            v-hasPermi={['system:user:edit']}
          >
            <icon-tdesign-edit class="mr-2px text-15px " />
            编辑
          </n-button>
          <n-popconfirm onPositiveClick={() => handleDeleteTable(row.roleId)}>
            {{
              default: () => '确认删除',
              trigger: () => (
                <n-button size={'tiny'} color="#ed8182" v-hasPermi={['system:role:remove']}>
                  <icon-tdesign-delete-1 class="mr-2px text-15px " />
                  删除
                </n-button>
              )
            }}
          </n-popconfirm>
          <NPopselect trigger="click" options={moreOptions} onUpdateValue={value => roleMore(value, row)}>
            <NButton size={'tiny'} ghost type="primary" v-hasPermi={['system:role:edit']}>
              更多
              <icon-tdesign-chevron-right-double-s class="mr-2px text-15px " />
            </NButton>
          </NPopselect>
        </n-space>
      );
    },

    align: 'center'
  }
]) as Ref<DataTableColumns<RoleManagement.RoleType>>;

const { loading, startLoading, endLoading } = useLoading(false);
const formModel = ref({
  roleName: undefined,
  roleKey: undefined,
  dataScope: undefined,
  roleId: undefined
});

// const { setTrue: openModal } = useBoolean();
function setModalType(type: ModalType) {
  modalType.value = type;
}
// 批量删除
function handleCheck(rowKeys: DataTableRowKey[]) {
  checkedRowKeysRef.value = rowKeys;
  if (rowKeys.length > 0) {
    isBacthDelete.value = false;
    delIds.value = rowKeys;
  } else {
    isBacthDelete.value = true;
  }
}
// function createDefaultFormModel() {
//   return ;
// }
function findId(obj: treeType) {
  if (obj) {
    menuId.push(obj.id);
    if (obj.children) {
      obj.children.forEach(item => findId(item));
    }
  }
}
// 修改权限范围
function changeScope(value) {
  // console.log(typeof value);
  formModel.value.dataScope = value;
}
// 展开
const expand = ({ checked }: { checked: boolean }) => {
  const style: CSSProperties = {};
  if (checked) {
    style.background = '#d03050';
    isExpand.value = checked;
  } else {
    style.background = '#2080f0';
    isExpand.value = checked;
  }
  return style;
};
function handleSwitchChange(value) {
  if (value) {
    deptOptions.value.forEach(item => findId(item));
    defaultCheckedKeys.value = menuId;
  } else {
    defaultCheckedKeys.value = [];
  }
}

/** 根据角色ID查询部门树结构 */
function getDeptTree(roleId) {
  return deptRoleTreeSelect(roleId).then(response => {
    deptOptions.value = response.depts;
    deptOptions.value.forEach(item => findId(item));
    // defaultCheckedKeys.value = judgeIsDeptParents(response.checkedKeys, menuId);
    return response;
  });
}

// function judgeIsDeptParents(ids,allDeptIds) {
// ids.filter(checkedId=>{
// 	deptOptions.value.map(item=>{
// 		if()
// 	})
// })
// }
function updateCheckedKeys(keys: Array<string | number>) {
  defaultCheckedKeys.value = keys;
}
// 搜索
function handleValidateClick() {
  getRoleData();
}
// 更多
async function roleMore(value, row) {
  switch (value) {
    case 'dataAuth':
      await activate(row.roleId);
      active.value = true;
      break;
    case 'dispathUser':
      routerPush({ name: routeName('system_dispacthUser'), query: row, hash: '#DEMO_HASH' });
      break;
    default:
      break;
  }
}

// 编辑
async function handleEditTable(id: number) {
  await getRoleMenuTreeselect(id).then(res => {
    checkedKeys.value = res.checkedKeys;
  });
  await getRole(id).then(response => {
    rowData.value = response.data;

    if (rowData.value) {
      setEditData(rowData.value);
    }
  });
  setModalType('edit');
  openModal();
}
function setEditData(data) {
  editData.value = data;
}
// 删除
async function handleDeleteTable(id: number) {
  await delRole(id).then(res => {
    if (res.code === 200) {
      window.$message?.info(`成功删除了id为${id}的用户`);
      getRoleData();
    }
  });
}
// function setModalType(type: ModalType) {
//   modalType.value = type;
// }

// 新增用户
function handleAddTable() {
  getMenuTreeselect();
  openModal();
  setModalType('add');
}
/** 提交按钮（数据权限） */
function submitDataScope() {
  if (formModel.value.roleId !== undefined) {
    Reflect.set(formModel.value, 'deptIds', defaultCheckedKeys.value);
    dataScope(formModel.value).then(() => {
      message.success('修改成功');
      getRoleData();
      active.value = false;
    });
  }
}

// 批量删除
async function onPositiveClick() {
  await delRole(delIds.value).then(res => {
    if (res.code === 200) {
      getRoleData();
      message.success('删除成功');
      showModalRef.value = false;
    }
  });
}
function onNegativeClick() {
  showModalRef.value = false;
}
// 查询才菜单树形结构
async function getMenuTreeselect() {
  await menuTreeselect().then(response => {
    menuOptions.value = response.data;
  });
}
function getRoleMenuTreeselect(roleId) {
  return roleMenuTreeselect(roleId).then(response => {
    menuOptions.value = response.menus;
    return response;
  });
}
// 用户状态
// 状态确认框
function handlePositiveClick(row) {
  changeRoleStatus(row.roleId, row.status ? '0' : '1').then(res => {
    if (res.code === 200) {
      message.info('操作成功');
    } else {
      row.status = !row.status;
      message.error(res.msg);
    }
  });
}
function handleNegativeClick(row) {
  row.status = !row.status;
}
// 重置
function reset() {
  daterange.value = null;
  formValue.value.pageNum = 1;
  page.value = 1;
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const { pageNum, pageSize, ...rest } = formValue.value;
  for (const key in rest) {
    if (formValue.value[key] !== '') {
      formValue.value[key] = '';
    }
  }
  getRoleData();
}
// 数据权限
async function activate(id: number) {
  await getDeptTree(id).then(res => {
    defaultCheckedKeys.value = res.checkedKeys;
  });
  await getRole(id).then(response => {
    formModel.value = response.data;
  });
}
// function judgeIsDeptParents(ids, allDepts) {
//   ids.filter(item => {});
// }
// 获取用户信息
async function getRoleData() {
  startLoading();
  let dateArr;
  if (daterange.value) {
    dateArr = daterange.value.map(item => {
      return formatDate(new Date(item), format);
    });
  }
  const roleData = await fetchRoleList({ params: addDateRange(formValue.value, dateArr, undefined) });
  roleList.value = roleData.rows;
  total.value = roleData.total;
  // eslint-disable-next-line array-callback-return
  roleData.rows.forEach(item => {
    if (item.status === '0') {
      item.status = true;
    } else {
      item.status = false;
    }
  });

  if (roleData) {
    setTimeout(() => {
      endLoading();
    }, 200);
  }
}
// 改变页签
function changePage(pageNum) {
  formValue.value.pageNum = pageNum;
  page.value = pageNum;
  getRoleData();
}
function changePageSize(pageSize) {
  formValue.value.pageSize = pageSize;
  getRoleData();
}
// 导出为excel
function handleExport() {
  show.value = true;
  download('system/role/export', formValue.value, `role_${new Date().getTime()}.xlsx`, undefined)
    .then(() => {
      show.value = false;
    })
    .catch(() => {
      show.value = false;
    });
}
function init() {
  getRoleData();
}
init();
</script>
