<template>
  <div class="h-full">
    <my-card title="搜索条件" search>
      <n-form ref="formRef" inline label-width="auto" :model="formValue" size="medium">
        <n-form-item label="岗位编码">
          <n-input v-model:value="formValue.postCode" placeholder="输入" />
        </n-form-item>
        <n-form-item label="岗位名称">
          <n-input v-model:value="formValue.postName" placeholder="输入部门名称" />
        </n-form-item>
        <n-form-item label="岗位状态">
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
    <my-card title="列表">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button
            v-hasPermi="['system:post:add']"
            size="small"
            class="mr-5px ml-5px"
            type="primary"
            @click="handleAddTable"
          >
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增
          </n-button>

          <n-button
            v-hasPermi="['system:post:remove']"
            type="error"
            size="small"
            class="mr-5px"
            :disabled="isBacthDelete"
            @click="showModal = true"
          >
            <icon-ic-round-delete class="mr-4px text-20px" />
            删除
          </n-button>
          <n-button
            v-hasPermi="['system:post:export']"
            size="small"
            class="mr-5px"
            type="primary"
            color="#2cc2d9"
            @click="handleExport"
          >
            <icon-tdesign-arrow-right-up-circle class="mr-4px text-20px" />
            导出
          </n-button>
          <n-button size="small" class="mr-5px" type="primary" @click="getPostData">
            <icon-mdi-refresh class="mr-4px text-16px" :class="{ 'animate-spin': loading }" />
            刷新表格
          </n-button>
        </div>
      </template>
      <div>
        <n-data-table
          :columns="columns"
          :data="postList"
          :row-key="rowKey"
          :loading="loading"
          :scroll-x="1000"
          @update:checked-row-keys="handleCheck"
        />
        <n-pagination
          :page="page"
          :page-size="formValue.pageSize"
          :item-count="total"
          :page-sizes="[10, 20, 50, 100]"
          show-size-picker
          style="margin-top: 20px; float: right"
          @update:page="changePage"
          @update-page-size="changePageSize"
        >
          <template #prefix="{ itemCount }">共 {{ itemCount }} 条</template>
        </n-pagination>
      </div>
      <table-action-modal
        v-model:visible="visible"
        :type="modalType"
        :tree-data="menuOptions"
        :edit-data="editData"
        :dept-id="addDeptId"
        :get-list="getPostData"
      />
      <n-modal
        v-model:show="showModal"
        :mask-closable="false"
        preset="dialog"
        title="删除岗位"
        content="你真的要删除这些岗位吗？"
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
// import { ReorderThreeOutline, Sync } from '@vicons/ionicons5';
import { deptStatusLabels } from '@/constants';
import { listPost, getPost, delPost } from '@/service';
import { useLoading, useBoolean } from '@/hooks';
import { download } from '@/service/request/helpers';
import type { ModalType } from './components/table-action-modal.vue';
import TableActionModal from './components/table-action-modal.vue';
// const collapseFlag = ref<boolean>(false);
const postList = ref();
const rowKey = row => row.postId;
const { bool: visible, setTrue: openModal } = useBoolean();
const modalType = ref<ModalType>('add');
const menuOptions = ref();
const message = useMessage();
const showModal = ref(false);
const editData = ref();
const delIds = ref();
const show = ref(false);
const total = ref();
const page = ref(1);
const isBacthDelete = ref(true);
const addDeptId = ref();
const formValue = ref({
  pageNum: 1,
  pageSize: 10,
  postName: undefined,
  postCode: undefined,
  status: undefined
});

const columns: Ref<DataTableColumns<PostManagement.PostType>> = ref([
  {
    type: 'selection',
    align: 'center'
  },
  {
    title: '岗位编号',
    key: 'postId',
    align: 'center'
  },
  {
    title: '岗位编码',
    key: 'postCode',
    align: 'center'
  },
  {
    title: '岗位名称',
    key: 'postName',
    align: 'center'
  },
  {
    title: '岗位排序',
    key: 'postSort',
    align: 'center'
  },
  {
    title: '状态',
    key: 'status',
    render: row => {
      if (row.status) {
        const tagTypes: Record<PostManagement.PostStatusKey, NaiveUI.ThemeColor> = {
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
            onClick={() => handleEditTable(row.postId)}
            v-hasPermi={['system:post:edit']}
          >
            <icon-tdesign-edit class="mr-2px text-15px " />
            编辑
          </n-button>

          <n-popconfirm onPositiveClick={() => handleDeleteTable(row.postId)} v-hasPermi={['system:post:edit']}>
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
]) as Ref<DataTableColumns<PostManagement.PostType>>;

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

const { loading, startLoading, endLoading } = useLoading(false);
// const { setTrue: openModal } = useBoolean();

// 搜索
function handleValidateClick() {
  getPostData();
}
function setModalType(type: ModalType) {
  modalType.value = type;
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
  getPostData();
}

// 新增
function handleAddTable(deptId) {
  setModalType('add');
  if (deptId !== undefined) {
    addDeptId.value = deptId;
  }

  openModal();
}
// 编辑
async function handleEditTable(postId: number) {
  getPost(postId).then(res => {
    setEditData(res.data);
    setModalType('edit');
    openModal();
  });
}
function setEditData(data) {
  editData.value = data;
}
// 获取用户信息
function getPostData() {
  startLoading();
  listPost({ params: formValue.value }).then(res => {
    postList.value = res.rows;
    total.value = res.total;
    if (postList.value) {
      setTimeout(() => {
        endLoading();
      }, 200);
    }
  });
}
// 删除部门
function handleDeleteTable(deptId: number) {
  delPost(deptId).then(() => {
    getPostData();
    message.success('删除成功');
  });
}

// 批量删除
function onPositiveClick() {
  delPost(delIds.value).then(() => {
    getPostData();
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
// 改变页签
function changePage(pageNum) {
  formValue.value.pageNum = pageNum;
  page.value = pageNum;
  getPostData();
}
// 改变页面数据条数
function changePageSize(pageSize) {
  formValue.value.pageNum = 1;
  page.value = 1;
  formValue.value.pageSize = pageSize;
  getPostData();
}
function handleExport() {
  show.value = true;
  download('system/post/export', formValue.value, `post_${new Date().getTime()}.xlsx`, undefined)
    .then(() => {
      show.value = false;
    })
    .catch(() => {
      show.value = false;
    });
}
function init() {
  getPostData();
}
init();
</script>
