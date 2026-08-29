<template>
  <div class="h-full">
    <my-card title="搜索条件" search>
      <div>
        <n-form ref="formRef" inline label-width="auto" :model="formValue" :rules="rules" size="medium">
          <n-form-item label="用户名称">
            <n-input v-model:value="formValue.userName" placeholder="输入姓名" />
          </n-form-item>
          <n-form-item label="手机号码">
            <n-input v-model:value="formValue.phonenumber" placeholder="输入手机号码" />
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
      </div>
    </my-card>
    <my-card title="用户列表">
      <template #right>
        <div class="flex-center">
          <n-button v-hasPermi="['system:role:add']" class="mr-5px" type="success" size="small" @click="handleAddTable">
            <icon-ic-round-plus class="mr-4px text-20px" />
            存入用户
          </n-button>
          <n-button
            v-hasPermi="['system:role:remove']"
            class="mr-5px"
            type="error"
            size="small"
            :disabled="isBacthDelete"
            @click="showModal = true"
          >
            <icon-tdesign-close-octagon class="mr-2px text-15px" />
            取消授权
          </n-button>
          <n-button class="mr-5px" size="small" type="primary" @click="getuserAuthData">
            <icon-mdi-refresh class="mr-4px text-16px" :class="{ 'animate-spin': loading }" />
            刷新表格
          </n-button>
          <n-button size="small" color="#8c70f0" @click="goBack">
            <icon-tdesign-rollback class="mr-4px text-16px" />
            返回
          </n-button>
          <column-setting v-model:columns="columns" />
        </div>
      </template>
      <div>
        <n-data-table
          :columns="columns"
          :data="userList"
          :loading="loading"
          :row-key="rowKey"
          @update:checked-row-keys="handleCheck"
        />
        <n-pagination
          :page="page"
          :page-size="formValue.pageSize"
          :item-count="total"
          style="margin-top: 20px; float: right"
          @update:page="changePage"
        />
      </div>
      <n-modal
        v-model:show="showModal"
        :mask-closable="false"
        preset="dialog"
        title="取消授权"
        content="你确定要解除这些用户的权限吗？"
        positive-text="确认"
        negative-text="算了"
        @positive-click="onPositiveClick"
        @negative-click="onNegativeClick"
      />
      <table-action-modal v-model:visible="visible" :role-id="roleId" :get-auth="getuserAuthData" />
    </my-card>
  </div>
</template>

<script lang="tsx" setup>
import type { Ref } from 'vue';
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { NButton, useMessage, NTag } from 'naive-ui';
import type { DataTableColumns, DataTableRowKey } from 'naive-ui';
// import { ReorderThreeOutline } from '@vicons/ionicons5';
import { userAuthStatusLabels } from '@/constants';
import { routeName } from '@/router';
import { allocatedUserList, authUserCancelAll, authUserCancel } from '@/service';
import { useRouterPush } from '@/composables';
import { useLoading, useBoolean } from '@/hooks';
import { localStg } from '~/src/utils';
import TableActionModal from './components/table-action-modal.vue';

// const pagination = { pageSize: 6 };
const checkedRowKeysRef = ref<DataTableRowKey[]>([]);
const { routerPush } = useRouterPush();
const rowKey = row => row.userId;

const userList = ref();
const { bool: visible, setTrue: openModal } = useBoolean();
const isBacthDelete = ref(true);
const showModalRef = ref(false);
const showModal = showModalRef;
const userAuthIds = ref();
const route = useRoute();
const roleId = ref();
const total = ref();
const page = ref(1);
const message = useMessage();
const { loading, startLoading, endLoading } = useLoading(false);
const formValue = ref({
  pageNum: 1,
  pageSize: 6,
  userName: undefined,
  phonenumber: undefined,
  status: undefined,
  roleId: ''
});

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

const columns: Ref<DataTableColumns<UserAuthManagement.UserAuthType>> = ref([
  {
    type: 'selection',
    align: 'center'
  },
  {
    title: '用户编号',
    key: 'userId',
    align: 'center'
  },
  {
    title: '用户名称',
    key: 'userName',
    align: 'center'
  },

  {
    title: '用户昵称',
    key: 'nickName',
    align: 'center'
  },
  {
    title: '邮箱',
    key: 'email',
    align: 'center'
  },

  {
    title: '手机号码',
    key: 'phonenumber',
    align: 'center'
  },
  {
    title: '状态',
    key: 'status',
    align: 'center',
    render: row => {
      if (row.status) {
        const tagTypes: Record<UserAuthManagement.UserAuthStatusKey, NaiveUI.ThemeColor> = {
          0: 'success',
          1: 'error'
        };

        return <NTag type={tagTypes[row.status]}>{userAuthStatusLabels[row.status]}</NTag>;
      }

      return <span></span>;
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
          <n-popconfirm onPositiveClick={() => handleDeleteTable(row)}>
            {{
              default: () => '确认取消吗？',
              trigger: () => (
                <n-button size={'small'} type="error" v-hasPermi={['system:role:remove']}>
                  <icon-tdesign-close-octagon class="mr-2px text-15px " />
                  取消授权
                </n-button>
              )
            }}
          </n-popconfirm>
        </n-space>
      );
    },

    align: 'center'
  }
]) as Ref<DataTableColumns<UserAuthManagement.UserAuthType>>;

// let formModel = reactive({
//   roleName: undefined,
//   roleKey: undefined,s
//   dataScope: undefined,
//   roleId: undefined
// });

// 存入用户
function handleAddTable() {
  roleId.value = localStg.get('roleId');
  openModal();
}
// 回退
function goBack() {
  routerPush({ name: routeName('system_role') });
  localStg.remove('roleId');
}
// 批量取消授权
function handleCheck(rowKeys: DataTableRowKey[]) {
  checkedRowKeysRef.value = rowKeys;
  if (rowKeys.length > 0) {
    isBacthDelete.value = false;
    userAuthIds.value = rowKeys;
  } else {
    isBacthDelete.value = true;
  }
}

// 搜索
function handleValidateClick() {
  getuserAuthData();
}

// 删除
async function handleDeleteTable(row) {
  await authUserCancel({ userId: row.userId, roleId: localStg.get('roleId') }).then(() => {
    window.$message?.success(`成功取消了${row.userName}的权限`);
    getuserAuthData();
  });
}

// 批量删除
async function onPositiveClick() {
  await authUserCancelAll({ roleId: localStg.get('roleId'), userIds: userAuthIds.value });
  getuserAuthData();
  message.success('取消授权成功');
  showModalRef.value = false;
}
function onNegativeClick() {
  showModalRef.value = false;
}

// 重置
function reset() {
  formValue.value.pageNum = 1;
  page.value = 1;
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const { pageNum, pageSize, ...rest } = formValue.value;
  for (const key in rest) {
    if (formValue.value[key] !== '') {
      formValue.value[key] = '';
    }
  }
  getuserAuthData();
}

// 获取用户信息
async function getuserAuthData() {
  const allRoleId = localStg.get('roleId');
  formValue.value.roleId = allRoleId as string;
  startLoading();
  const userData = await allocatedUserList({ params: formValue.value });
  total.value = userData.total;
  userList.value = userData.rows;
  // eslint-disable-next-line array-callback-return
  if (userData) {
    setTimeout(() => {
      endLoading();
    }, 200);
  }
  // localStg.remove('roleId');
}
function changePage(pageNum) {
  formValue.value.pageNum = pageNum;
  page.value = pageNum;
  getuserAuthData();
}
function init() {
  formValue.value.roleId = route.query.roleId as string;
  localStg.set('roleId', route.query.roleId as string);
  getuserAuthData();
}
init();
</script>
