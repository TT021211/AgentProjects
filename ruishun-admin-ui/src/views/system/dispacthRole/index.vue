<template>
  <div class="h-full">
    <my-card title="用户信息">
      <template #right>
        <div>
          <n-button type="success" size="small" @click="goBack">
            <icon-tdesign-rollback class="mr-4px text-16px" />
            返回
          </n-button>
        </div>
      </template>
      <n-form ref="formRef" inline label-width="auto" :model="formValue" size="medium">
        <n-form-item label="用户昵称">
          <n-input v-model:value="formValue.nickName" :disabled="true" />
        </n-form-item>
        <n-form-item label="当前账号">
          <n-input v-model:value="formValue.userName" :disabled="true" />
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="分配角色">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button
            color="#44db01"
            class="mr-5px ml-5px"
            size="small"
            :disabled="isBacthDispacth"
            @click="showModal = true"
          >
            <icon-tdesign-cloud-upload class="mr-4px text-20px" />
            分配
          </n-button>
          <n-button size="small" @click="getRoleAuthData">
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
          :checked-row-keys="hasRoles"
          @update:checked-row-keys="handleCheck"
        />
        <n-pagination
          :page="page"
          :page-size="formValue.pageSize"
          :item-count="total"
          style="margin-top: 20px; float: right"
          :page-sizes="[10, 20, 50, 100]"
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
        title="取消分配"
        content="你确定要分配到这些的角色吗？"
        positive-text="确认"
        negative-text="算了"
        @positive-click="onPositiveClick"
      />
    </my-card>
  </div>
</template>

<script lang="tsx" setup>
import type { Ref } from 'vue';
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { useMessage, NTag } from 'naive-ui';
import type { DataTableColumns, DataTableRowKey } from 'naive-ui';
import { roleAuthStatusLabels } from '@/constants';
import { routeName } from '@/router';
import { getAuthRole, updateAuthRole } from '@/service';
import { useRouterPush } from '@/composables';
import { useLoading } from '@/hooks';

const route = useRoute();
const hasRoles = ref();
const userId = ref();
const total = ref();
const page = ref(1);
const roleList = ref();
const rowKey = row => row.roleId;
const showModal = ref(false);
const dispacthRoleIds = ref();
const isBacthDispacth = ref(true);
const { routerPush } = useRouterPush();
const message = useMessage();
const { loading, startLoading, endLoading } = useLoading(false);
// const checkedRowKeysRef = ref<DataTableRowKey[]>([]);
const formValue = ref({
  pageNum: 1,
  pageSize: 6,
  nickName: '',
  userName: ''
});
const columns: Ref<DataTableColumns<RoleAuthManagement.RoleAuthType>> = ref([
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
      if (row.status) {
        const tagTypes: Record<RoleAuthManagement.RoleAuthStatusKey, NaiveUI.ThemeColor> = {
          0: 'success',
          1: 'error'
        };

        return <NTag type={tagTypes[row.status]}>{roleAuthStatusLabels[row.status]}</NTag>;
      }

      return <span></span>;
    }
  },
  {
    title: '创建时间',
    key: 'createTime',
    align: 'center'
  }
]) as Ref<DataTableColumns<RoleAuthManagement.RoleAuthType>>;
function getRoleAuthData() {
  startLoading();
  getAuthRole(userId.value).then(res => {
    const roleIds = res.user.roles.map(item => {
      return item.roleId;
    });
    hasRoles.value = roleIds;
    roleList.value = res.roles;
    formValue.value = res.user;
    total.value = res.total || 6;
    if (roleList.value) {
      setTimeout(() => {
        endLoading();
      }, 200);
    }
  });
}
// 批量分配角色
function handleCheck(rowKeys: DataTableRowKey[]) {
  hasRoles.value = rowKeys;

  if (rowKeys.length > 0) {
    isBacthDispacth.value = false;
    dispacthRoleIds.value = rowKeys;
  } else {
    isBacthDispacth.value = true;
  }
}

// 回退
function goBack() {
  routerPush({ name: routeName('system_user') });
}
// 批量分配角色
function onPositiveClick() {
  updateAuthRole({ userId: userId.value, roleIds: dispacthRoleIds.value }).then(res => {
    if (res.code === 200) {
      message.success('分配成功');
      getRoleAuthData();
    } else {
      message.error('分配失败');
    }
  });
}
function changePage(pageNum) {
  formValue.value.pageNum = pageNum;
  page.value = pageNum;
  getRoleAuthData();
}
function changePageSize(size) {
  formValue.value.pageSize = size;
  getRoleAuthData();
}
function init() {
  userId.value = route.query.id;
  getRoleAuthData();
}
init();
</script>

<style scoped></style>
