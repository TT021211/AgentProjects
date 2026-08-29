<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="模具分类">
          <n-input v-model:value="searchForm.classificationName" type="text" placeholder="请输入模具分类" />
        </n-form-item>
        <n-form-item label="型号">
          <n-input v-model:value="searchForm.classificationType" type="text" placeholder="请输入型号" />
        </n-form-item>
        <n-form-item label="分类编码">
          <n-input v-model:value="searchForm.classificationCode" type="text" placeholder="请输入分类编码" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="模具分类管理列表">
      <template #right>
        <div>
          <n-button size="small" type="info" style="margin-right: 10px" @click="addOrEdit(0)">
            <icon-mdi-add />
            新增模具分类
          </n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog :show="show" :title="title" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModel"
            class="w-260px"
            label-placement="left"
            label-align="left"
            :label-width="80"
            :model="addForm"
            :rules="rules"
          >
            <n-form-item label="模具分类" path="classificationName">
              <n-input v-model:value="addForm.classificationName" placeholder="请输入模具分类" />
            </n-form-item>
            <n-form-item label="型号" path="classificationType">
              <n-input v-model:value="addForm.classificationType" placeholder="请输入型号" />
            </n-form-item>
            <n-form-item label="分类编码" path="classificationCode">
              <n-input v-model:value="addForm.classificationCode" placeholder="请输入编码"></n-input>
            </n-form-item>
            <n-form-item label="是否启用">
              <n-switch v-model:value="addForm.enable" :checked-value="'Y'" :unchecked-value="'N'" />
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'SortManagement'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage, useDialog } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import {
  getMoidList,
  addMoidClassify,
  editMoidClassify,
  deleteMoidClassify
} from '~/src/service/api/moid/sortManagement/index';
const searchForm = ref<sortManagement.searchForm>({
  classificationName: '',
  classificationType: '',
  classificationCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const addForm = ref<sortManagement.addForm>({
  classificationId: null,
  classificationName: '',
  classificationType: '',
  classificationCode: '',
  enable: ''
});

const { loading, startLoading, endLoading } = useLoading();

const show = ref<boolean>(false);

const type = ref<number>(0); // 0 : 添加， 1 : 编辑

const formModel = ref<FormInst | null>(null);
const title = ref<string>('');
const message = useMessage();
const dialog = useDialog();
const addOrEditComplete = ref<boolean>(true);

const rules = {
  classificationName: createRequiredFormRule('请输入模具分类'),
  classificationType: createRequiredFormRule('请输入型号'),
  classificationCode: createRequiredFormRule('请输入分类编码'),
  enable: createRequiredFormRule('请选择是否启用')
};

const data = ref<sortManagement.TableList[]>([
  {
    classificationId: 1,
    classificationName: '拉丝模',
    classificationType: '出线模',
    classificationCode: 'LSM',
    enable: 'Y'
  },
  {
    classificationId: 2,
    classificationName: '冷接模',
    classificationType: 'DM-2',
    classificationCode: 'LSM',
    enable: 'Y'
  },
  {
    classificationId: 2,
    classificationName: '镀锡模',
    classificationType: '立式',
    classificationCode: 'LSM',
    enable: 'Y'
  }
]);

const columns: Ref<DataTableColumns<sortManagement.TableList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '模具分类',
    key: 'classificationName',
    align: 'center',
    width: 100
  },
  {
    title: '型号',
    key: 'classificationType',
    align: 'center',
    width: 100
  },
  {
    title: '分类编码',
    key: 'classificationCode',
    align: 'center',
    width: 100
  },
  {
    title: '是否启用',
    key: 'enableFlag',
    align: 'center',
    render: row => {
      return (
        <my-switch
          v-model:value={row.enable}
          checked={'Y'}
          unchecked={'N'}
          onChecked={val => {
            changeStatus(row, val);
          }}
          onUnchecked={val => {
            changeStatus(row, val);
          }}
        ></my-switch>
      );
    }
  },
  {
    title: '操作',
    key: 'actions',
    align: 'center',
    render: row => {
      return (
        <n-space justify={'center'}>
          <n-button
            attr-type="button"
            size={'small'}
            onClick={() => {
              addOrEdit(1, row);
            }}
          >
            <icon-mdi-edit />
            编辑
          </n-button>

          <n-button
            type="warning"
            size={'small'}
            onClick={() => {
              handleDeleteItemType(row.classificationId);
            }}
          >
            <icon-mdi-delete />
            删除
          </n-button>
        </n-space>
      );
    }
  }
]);

const sleep = id =>
  new Promise(resolve => {
    deleteMoidClassify(id).then(res => {
      resolve(res);
    });
  });

function search() {
  init();
}
function reset() {
  searchForm.value = {
    classificationName: '',
    classificationType: '',
    classificationCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function addOrEdit(count: number, row?: sortManagement.TableList): void {
  type.value = count;
  show.value = true;
  if (count === 0) {
    title.value = '新增';
    for (const key in addForm.value) {
      if (key !== 'classificationId' && key !== 'enable') {
        addForm.value[key] = '';
      }
    }
    addForm.value.classificationId = null;
    addForm.value.enable = 'N';
  } else {
    title.value = '编辑';
    for (const key in addForm.value) {
      if (row) {
        addForm.value[key] = row[key];
      }
    }
  }
}

function cancel(): void {
  show.value = false;
}

function submitSame(msg: string, code: number): void {
  show.value = false;
  addOrEditComplete.value = true;
  if (code === 200) {
    message.success(msg);
    init();
  }
}

function submit(): void {
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      if (!addOrEditComplete.value) return;
      addOrEditComplete.value = false;
      if (type.value === 0) {
        addMoidClassify(addForm.value)
          .then(res => {
            submitSame('添加成功', res.code);
          })
          .catch(() => {
            addOrEditComplete.value = true;
          });
      } else {
        editMoidClassify(addForm.value)
          .then(res => {
            submitSame('编辑成功', res.code);
          })
          .catch(() => {
            addOrEditComplete.value = true;
          });
      }
    }
  });
}

function handleDeleteItemType(id: number | null): void {
  const d = dialog.warning({
    title: '删除',
    content: '确认要删除吗?',
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise(resolve => {
        sleep(id).then((res: any) => {
          if (res.code === 200) {
            message.success('删除成功');
            init();
            resolve(res);
          }
        });
      });
    }
  });
}

function changeStatus(row: sortManagement.TableList, val: string): void {
  row.enable = val;
  const msg = row.enable === 'Y' ? '启用成功' : '已关闭启用';
  editMoidClassify(row).then(res => {
    if (res.code === 200) {
      message.success(msg);
    }
  });
}

function init() {
  startLoading();
  getMoidList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped></style>
