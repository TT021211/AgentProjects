<template>
  <div class="h-full">
    <n-spin :show="show">
      <n-card :bordered="false" class="h-full rounded-8px shadow-sm" style="height: auto">
        <n-layout has-sider>
          <n-layout-sider
            bordered
            collapse-mode="width"
            :collapsed-width="50"
            :width="200"
            :collapsed="collapsed"
            show-trigger
            @collapse="collapsed = true"
            @expand="collapsed = false"
          >
            <n-menu
              :collapsed="collapsed"
              :accordion="true"
              :collapsed-icon-size="22"
              :collapsed-width="0"
              :options="menuOptions"
              key-field="id"
              label-field="label"
              children-field="children"
              @update:value="getMenu"
              @update:expanded-keys="handleUpdateExpandedKeys"
            />
          </n-layout-sider>
          <n-layout class="content_user">
            <n-layout-header>
              <my-card title="搜索条件" search>
                <n-form ref="formRef" inline label-width="auto" :model="formValue" :rules="rules" size="medium">
                  <n-form-item label="用户名">
                    <n-input v-model:value="formValue.userName" placeholder="输入姓名" />
                  </n-form-item>
                  <n-form-item label="电话号码">
                    <n-input v-model:value="formValue.phonenumber" placeholder="电话号码" />
                  </n-form-item>
                  <n-form-item label="创建时间">
                    <n-date-picker v-model:value="daterange" type="daterange" :format="format" />
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
              <my-card title="用户列表">
                <template #right>
                  <div class="flex-center">
                    <column-setting v-model:columns="columns" size="small" />
                    <n-button
                      v-hasPermi="['system:user:add']"
                      class="ml-5px mr-5px"
                      type="primary"
                      size="small"
                      @click="handleAddTable"
                    >
                      <icon-ic-round-plus class="mr-4px text-20px" />
                      新增
                    </n-button>
                    <n-button
                      v-hasPermi="['system:user:remove']"
                      class="mr-5px"
                      type="error"
                      :disabled="isBacthDelete"
                      size="small"
                      @click="showModal = true"
                    >
                      <icon-ic-round-delete class="mr-4px text-20px" />
                      删除
                    </n-button>
                    <!-- 批量删除模态框 -->
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
                    <!-- 修改密码 -->
                    <n-modal
                      v-model:show="showPasModal"
                      :mask-closable="false"
                      preset="dialog"
                      title="确认"
                      content="你确认"
                      positive-text="确认"
                      negative-text="算了"
                      @positive-click="onPositivePasClick"
                      @negative-click="onNegativePasClick"
                    >
                      <n-form
                        ref="pasFormRef"
                        inline
                        :rules="pasRules"
                        :show-require-mark="false"
                        :model="pasFormValue"
                      >
                        <n-form-item path="resetPas" label="密码">
                          <n-input
                            v-model:value="pasFormValue.resetPas"
                            type="password"
                            show-password-on="mousedown"
                            placeholder="密码"
                          />
                        </n-form-item>
                      </n-form>
                    </n-modal>

                    <n-button
                      v-hasPermi="['system:user:export']"
                      class="mr-5px"
                      type="success"
                      size="small"
                      @click="handleExport"
                    >
                      <icon-uil:export class="mr-4px text-20px" />
                      导出Excel
                    </n-button>
                    <n-button size="small" type="primary" @click="getTableData">
                      <icon-mdi-refresh class="mr-4px text-16px" :class="{ 'animate-spin': loading }" />
                      刷新表格
                    </n-button>
                  </div>
                </template>
                <div>
                  <n-data-table
                    :columns="columns"
                    :data="tableData"
                    :row-key="rowKey"
                    :loading="loading"
                    :scroll-x="1000"
                    @update:checked-row-keys="handleCheck"
                  />
                  <n-pagination
                    :page="page"
                    :page-size="formValue.pageSize"
                    :item-count="total"
                    style="margin-top: 20px; float: right"
                    :page-sizes="[10, 20, 50, 40]"
                    show-size-picker
                    @update:page="changePage"
                    @update:page-size="changePageSize"
                  >
                    <template #prefix="{ itemCount }">共 {{ itemCount }} 条</template>
                  </n-pagination>
                </div>
                <table-action-modal
                  v-model:visible="visible"
                  :type="modalType"
                  :edit-data="editData"
                  :tree-data="menuOptions"
                  :get-list="getTableData"
                />
              </my-card>
            </n-layout-header>
          </n-layout>
        </n-layout>
      </n-card>
      <template #icon>
        <n-icon>
          <Sync />
        </n-icon>
      </template>
    </n-spin>
  </div>
</template>

<script lang="tsx" setup>
import type { Ref } from 'vue';
import { ref } from 'vue';
import { NIcon, NSwitch, NSpace, NPopconfirm, NButton, useMessage, useNotification, NPopselect } from 'naive-ui';
import type { DataTableColumns, DataTableRowKey, NotificationType, FormInst } from 'naive-ui';
import { Sync } from '@vicons/ionicons5';
import { routeName } from '@/router';
import { fetchUserList, changeUserStatus, deptTreeSelect, getUser, delUser, resetUserPwd } from '@/service';
// import { useAuthStore } from '@/store';
import { useRouterPush } from '@/composables';
import { useLoading, useBoolean } from '@/hooks';
import { transforUserData, formatDate, addDateRange, formRules } from '@/utils';
import { download } from '@/service/request/helpers';
import ColumnSetting from './components/column-setting.vue';
import TableActionModal from './components/table-action-modal.vue';
import type { ModalType } from './components/table-action-modal.vue';

// const collapseFlag = ref<boolean>(false);
const { loading, startLoading, endLoading } = useLoading(false);
const { bool: visible, setTrue: openModal } = useBoolean();
const checkedRowKeysRef = ref<DataTableRowKey[]>([]);
// const pagination = ref({ pageSize: 5 });
const rowKey = row => row.id;
const editData = ref(null);
const modalType = ref<ModalType>('add');
const message = useMessage();
const isBacthDelete = ref(true);
const format = 'yyyy-MM-dd';
const daterange = ref();
const collapsed = ref(true);
const menuOptions = ref();
const rowData = ref();
const show = ref(false);
const page = ref(1);
const total = ref();
const delIds = ref();
const rowPasId = ref();
const pasFormValue = ref({ resetPas: undefined });
const showModalRef = ref(false);
const showModal = showModalRef;
const showPasModalRef = ref(false);
const showPasModal = showPasModalRef;
const notification = useNotification();
// const auth = useAuthStore();
const { routerPush } = useRouterPush();
const pasFormRef = ref<FormInst | null>(null);

function setModalType(type: ModalType) {
  modalType.value = type;
}

const formValue = ref({
  pageNum: 1,
  pageSize: 10,
  userName: undefined,
  status: undefined,
  phonenumber: undefined,
  deptId: undefined
});
const statusSelect = ref({
  value: [0],
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

const moreOptions = [
  {
    label: '重置密码',
    value: 'changePassword'
  },
  {
    label: '分配角色',
    value: 'dispathRole'
  }
];
const pasRules = {
  resetPas: formRules.password
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

// const active = ref(true)
const columns: Ref<DataTableColumns<UserManagement.User>> = ref([
  {
    type: 'selection',
    align: 'center'
  },
  {
    title: '用户编号',
    key: 'id',
    align: 'center'
  },
  {
    title: '用户名称',
    key: 'name',
    align: 'center'
  },
  {
    title: '用户昵称',
    key: 'nickName',
    align: 'center'
  },
  {
    title: '部门',
    key: 'department',
    align: 'center'
  },
  {
    title: '手机号码',
    key: 'phone',
    align: 'center'
  },
  {
    title: '工号',
    key: 'jobNumber',
    align: 'center'
  },
  {
    title: '状态',
    key: 'status',
    align: 'center',
    render: row => {
      // const active=ref(true)
      return (
        <NPopconfirm onPositiveClick={() => handlePositiveClick(row)} onNegativeClick={() => handleNegativeClick(row)}>
          {{
            default: () => `您确定要${row.status ? '启用' : '禁用'}${row.nickName}吗？`,
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
        <NSpace justify={'center'}>
          <NButton
            size={'tiny'}
            type="primary"
            onClick={() => handleEditTable(row.id)}
            v-hasPermi={['system:user:edit']}
          >
            <icon-tdesign-edit class="mr-2px text-15px " />
            编辑
          </NButton>
          <NPopconfirm onPositiveClick={() => handleDeleteTable(row.id)}>
            {{
              default: () => '确认删除',
              trigger: () => (
                <NButton size={'tiny'} color="#ed8182" v-hasPermi={['system:user:remove']}>
                  <icon-tdesign-delete-1 class="mr-2px text-15px " />
                  删除
                </NButton>
              )
            }}
          </NPopconfirm>
          <NPopselect trigger="click" options={moreOptions} onUpdateValue={value => test(value, row)}>
            <NButton size={'tiny'} ghost type="primary" v-hasPermi={['system:user:resetPwd', 'system:user:edit']}>
              更多
              <icon-tdesign-chevron-right-double-s class="mr-2px text-15px " />
            </NButton>
          </NPopselect>
        </NSpace>
      );
    },
    align: 'center'
  }
]) as Ref<DataTableColumns<UserManagement.User>>;

const tableData = ref<UserManagement.User[]>([]);
const allData = ref<Array<any>>([]);

function handleUpdateExpandedKeys(value) {
  // eslint-disable-next-line no-console
  console.log(value);
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
// 批量删除
function onNegativeClick() {
  showModalRef.value = false;
}

async function onPositiveClick() {
  await delUser(delIds.value);
  getTableData();
  message.success('删除成功');
  showModalRef.value = false;
}
// 修改密码

async function onPositivePasClick() {
  await pasFormRef.value?.validate(errors => {
    if (!errors) {
      resetUserPwd(rowPasId.value, pasFormValue.value.resetPas)
        .then(res => {
          if (res.code === 200) {
            message.success('重置密码成功');
            pasFormValue.value.resetPas = undefined;
          }
          // auth.resetAuthStore();
        })
        .catch(() => {
          message.error('重置密码失败');
        });
    } else {
      message.error('Invalid');
    }
  });
  // rowPasId
}

function onNegativePasClick() {
  pasFormValue.value.resetPas = undefined;
  showPasModalRef.value = false;
}
// 搜索
async function handleValidateClick() {
  getTableData();
}
// 用户状态
// 状态确认框
function handlePositiveClick(row) {
  changeUserStatus(row.id, row.status ? '0' : '1');
  notify('success', row);
}
function handleNegativeClick(row) {
  row.status = !row.status;
}
// 重置
function reset() {
  formValue.value.pageNum = 1;
  page.value = 1;
  daterange.value = null;
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const { pageNum, pageSize, ...rest } = formValue.value;
  for (const key in rest) {
    if (formValue.value[key] !== '') {
      formValue.value[key] = '';
    }
  }

  getTableData();
}

// function setModalType(type: ModalType) {
//   modalType.value = type;
// }

// 新增用户
function handleAddTable() {
  openModal();
  setModalType('add');
}
// 获取用户信息
async function getTableData() {
  startLoading();
  let dateArr;
  if (daterange.value) {
    dateArr = daterange.value.map(item => {
      return formatDate(new Date(item), format);
    });
  }
  const data = await fetchUserList({ params: addDateRange(formValue.value, dateArr, undefined) });

  allData.value = data.rows;
  total.value = data.total;
  const userListData = transforUserData(data.rows);
  if (userListData) {
    setTimeout(() => {
      setTableData(userListData);
      endLoading();
    }, 200);
  }
}
// 查询菜单信息
function getMenu(value) {
  formValue.value.deptId = value;
  getTableData();
}
function setTableData(data) {
  tableData.value = data;
}

// 编辑
function handleEditTable(rowId) {
  // const findItem = allData.value.find(item => item.userId === rowId);

  getUser(rowId).then(response => {
    rowData.value = {
      ...response.data,
      deviceId: response.data.deviceId ? response.data.deviceId.split(',') : []
    };
    Reflect.set(rowData.value, 'postIds', response.postIds);
    Reflect.set(rowData.value, 'roleIds', response.roleIds);
    if (rowData.value) {
      setEditData(rowData.value);
    }
    setModalType('edit');
    openModal();
  });
}
function setEditData(data) {
  editData.value = data;
}
// 删除
async function handleDeleteTable(rowId) {
  await delUser(rowId);
  window.$message?.info(`成功删除了id为${rowId}的用户`);
  getTableData();
}

function getDeptTree() {
  deptTreeSelect().then(response => {
    menuOptions.value = response.data;
  });
}
// 导出为excel
function handleExport() {
  show.value = true;

  download('system/user/export', formValue.value, `user_${new Date().getTime()}.xlsx`, undefined)
    .then(() => {
      show.value = false;
    })
    .catch(() => {
      show.value = false;
    });
}
// 通知
function notify(type: NotificationType, row) {
  notification[type]({
    content: '提示',
    meta: row.status ? `${row.name}启动成功` : `${row.name}已被禁用`,
    duration: 1500,
    keepAliveOnHover: true
  });
}
// 改变页签
function changePage(pageNum) {
  formValue.value.pageNum = pageNum;
  page.value = pageNum;
  getTableData();
}
function changePageSize(pageSize) {
  formValue.value.pageSize = pageSize;
  getTableData();
}
function test(value, row) {
  switch (value) {
    case 'changePassword':
      showPasModal.value = true;
      rowPasId.value = row.id;
      break;
    case 'dispathRole':
      routerPush({ name: routeName('system_dispacthRole'), query: row, hash: '#DEMO_HASH' });
      break;
    default:
      break;
  }
}

// console.log(value, row);

function init() {
  getTableData();
  getDeptTree();
}
init();
</script>
<style lang="scss" scoped>
.content_user {
  margin-left: 20px;
}
.n-form {
  display: flex;
  flex-wrap: wrap;
}
:deep(.n-form-item .n-form-item-feedback-wrapper) {
  min-height: 0px !important;
  margin-bottom: 0px;
  background-color: antiquewhite;
}
</style>
