<template>
  <n-modal v-model:show="showModal" preset="card" class="w-900px" title="存入用户" :bordered="false">
    <n-form ref="formRef" inline label-width="auto" :model="formValue" size="medium">
      <n-form-item label="用户名称">
        <n-input v-model:value="formValue.userName" placeholder="输入姓名" />
      </n-form-item>
      <n-form-item label="手机号码">
        <n-input v-model:value="formValue.phonenumber" placeholder="输入手机号码" />
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
    <n-modal
      v-model:show="showInsertModal"
      :mask-closable="false"
      preset="dialog"
      title="存入用户"
      content="你要将用户授权吗？"
      positive-text="确认"
      negative-text="算了"
      @positive-click="onPositiveClick"
    />

    <div>
      <n-data-table
        :columns="columns"
        :data="userList"
        :loading="loading"
        :row-key="rowKey"
        @update:checked-row-keys="handleCheck"
      />
      <n-pagination
        v-model:page="pagination.pageNum"
        v-model:page-size="pagination.pageSize"
        style="margin-top: 20px; float: right"
        :item-count="pagination.total"
        @update:page="addChangePage"
      ></n-pagination>
    </div>
    <template #footer>
      <n-space>
        <n-button @click="showInsert">存入</n-button>
        <n-button @click="closeModal">取消</n-button>
      </n-space>
    </template>
  </n-modal>
</template>
<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, computed } from 'vue';
import type { DataTableColumns, DataTableRowKey } from 'naive-ui';
import { NTag, useMessage } from 'naive-ui';
import { userAuthStatusLabels } from '@/constants';
import { unallocatedUserList, authUserSelectAll } from '@/service';
import { useLoading } from '@/hooks';
export interface Props {
  /** 弹窗可见性 */
  visible: boolean;
  roleId: string;
  getAuth;
}
interface Emits {
  (e: 'update:visible', visible: boolean): void;
}

const emit = defineEmits<Emits>();
const pagination = ref({ pageNum: 1, pageSize: 6, total: 0 });
const showModalRef = ref(false);
const message = useMessage();
const insertUserIds = ref();
const showInsertModal = showModalRef;
const props = withDefaults(defineProps<Props>(), {});
const userList = ref();
const checkedRowKeysRef = ref();
const { loading, startLoading, endLoading } = useLoading(false);
const rowKey = row => row.userId;
const showModal = computed({
  get() {
    getList();
    return props.visible;
  },
  set(visible) {
    emit('update:visible', visible);
  }
});
const formValue = ref({
  pageNum: 1,
  pageSize: 6,
  total: 0,
  userName: undefined,
  phonenumber: undefined,
  status: undefined,
  roleId: ''
});
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
  }
]) as Ref<DataTableColumns<UserAuthManagement.UserAuthType>>;
function handleValidateClick() {
  getList();
}
function getList() {
  formValue.value.roleId = props.roleId;
  startLoading();
  formValue.value.pageNum = pagination.value.pageNum;
  formValue.value.pageSize = pagination.value.pageSize;
  const userData = unallocatedUserList({ params: formValue.value });
  userData.then(res => {
    userList.value = res.rows;
    pagination.value.total = res.total;
    if (res.rows) {
      setTimeout(() => {
        endLoading();
      }, 1000);
    }
  });
}
function addChangePage(page: number) {
  pagination.value.pageNum = page;
  getList();
}
// 重置
function reset() {
  for (const key in formValue.value) {
    if (formValue.value[key] !== '') {
      formValue.value[key] = '';
    }
  }
  getList();
}

// 批量删除
function handleCheck(rowKeys: DataTableRowKey[]) {
  checkedRowKeysRef.value = rowKeys;
  insertUserIds.value = rowKeys;
}
function onPositiveClick() {
  const role_id = props.roleId;
  const userIds = insertUserIds.value;
  authUserSelectAll({ roleId: role_id, userIds }).then(res => {
    if (res.code === 200) {
      message.success('导入成功');
      getList();
      props.getAuth();
      insertUserIds.value = [];
      closeModal();
    } else {
      message.error('导入失败');
      insertUserIds.value = [];
    }
  });
}
function showInsert() {
  if (insertUserIds.value) {
    if (insertUserIds.value.length > 0) {
      showInsertModal.value = true;
    } else {
      message.warning('请选择需要授权的用户');
    }
  } else {
    message.warning('请选择需要授权的用户');
  }
}
function closeModal() {
  showModal.value = false;
}
</script>
