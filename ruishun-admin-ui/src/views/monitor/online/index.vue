<template>
  <div class="h-full">
    <my-card title="搜索条件" search>
      <n-form ref="formRef" inline label-width="auto" :model="formValue" size="medium">
        <n-form-item label="登录地址">
          <n-input v-model:value="formValue.ipaddr" placeholder="请输入IP地址" />
        </n-form-item>
        <n-form-item label="用户名称">
          <n-input v-model:value="formValue.userName" placeholder="请输入登录名称" />
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
    <my-card title="在线用户列表">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button size="small" class="ml-5px" type="primary" @click="getLoginData">
            <icon-mdi-refresh class="mr-4px text-16px" :class="{ 'animate-spin': loading }" />
            刷新表格
          </n-button>
        </div>
      </template>
      <div>
        <n-data-table :columns="columns" :data="loginList" :loading="loading" :pagination="pagination"></n-data-table>
      </div>

      <n-modal
        v-model:show="showModal"
        :mask-closable="false"
        preset="dialog"
        title="用户退出"
        content="你真的要退出这个用户吗？"
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
import { NButton, useMessage } from 'naive-ui';
import type { DataTableColumns } from 'naive-ui';
import { onlineList, forceLogout } from '@/service';
import { useLoading } from '@/hooks';
import { formatDate } from '@/utils';

const loginList = ref([]);
const format = 'yyyy-MM-dd hh:mm:ss';
const message = useMessage();
const showModal = ref(false);
const pagination = ref({
  pageSize: 10,
  pageSizes: [10, 20, 50, 100],
  showSizePicker: true,
  onUpdatePageSize(pageSize) {
    pagination.value.pageSize = pageSize;
  },
  prefix: ({ itemCount }) => `共 ${itemCount} 条`,
  total: loginList.value.length,
  layout: 'total, prev, pager, next, jumper,prefix'
});
const delId = ref();

const formValue = ref({
  ipaddr: undefined,
  userName: undefined
});

const columns: Ref<DataTableColumns<LoginManagement.LoginType>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_, index) => {
      return `${index + 1}`;
    }
  },
  {
    title: '会话编号',
    key: 'tokenId',
    align: 'center'
  },
  {
    title: '登录名称',
    key: 'userName',
    align: 'center'
  },
  {
    title: '主机',
    key: 'ipaddr',
    align: 'center'
  },

  {
    title: '登录时间',
    key: 'loginTime',
    align: 'center',
    defaultSortOrder: 'ascend',
    sorter: 'default'
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    render: row => {
      return (
        <n-space justify={'center'}>
          {/* <n-popconfirm onPositiveClick={() => handleDeleteTable(row.tokenId)} v-hasPermi={['system:role:remove']}>
            {{
              default: () => '确认删除',
              trigger: () => ( */}
          <n-button
            size={'tiny'}
            color="#df0052"
            onClick={() => {
              showModal.value = true;
              delId.value = row.tokenId;
            }}
          >
            <icon-tdesign-logout class="mr-2px text-15px " />
            退出
          </n-button>
          {/* )
            }}
          </n-popconfirm> */}
        </n-space>
      );
    }
  }
]) as Ref<DataTableColumns<LoginManagement.LoginType>>;

const { loading, startLoading, endLoading } = useLoading(false);

// const { setTrue: openModal } = useBoolean();

// 搜索
function handleValidateClick() {
  getLoginData();
}

// 重置
function reset() {
  for (const key in formValue.value) {
    if (formValue.value[key] !== '') {
      formValue.value[key] = '';
    }
  }
  getLoginData();
}

// 获取用户信息
function getLoginData() {
  startLoading();
  onlineList({ params: formValue.value }).then(res => {
    res.rows.forEach(item => {
      item.loginTime = formatDate(new Date(item.loginTime), format);
    });
    loginList.value = res.rows;
    if (loginList.value) {
      setTimeout(() => {
        endLoading();
      }, 250);
    }
  });
}

// 确认删除
function onPositiveClick() {
  forceLogout(delId.value).then(() => {
    getLoginData();
    message.success('删除成功');
  });
}

function init() {
  getLoginData();
}
init();
</script>
