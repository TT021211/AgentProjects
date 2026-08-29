<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="缺陷项编码">
          <n-input v-model:value="searchForm.defectClassificationCode" type="text" placeholder="请输入缺陷项编码" />
        </n-form-item>
        <n-form-item label="缺陷项名称">
          <n-input v-model:value="searchForm.defectClassificationName" type="text" placeholder="请输入缺陷项名称" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="缺陷项分类管理列表">
      <template #right>
        <div>
          <n-button size="small" type="info" style="margin-right: 10px" @click="addOrEdit(0)">
            <icon-mdi-add />
            新增缺陷项分类
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
            class="w-360px"
            label-placement="left"
            label-align="left"
            :label-width="120"
            :model="addForm"
            :rules="rules"
          >
            <n-form-item label="缺陷项编码" path="defectClassificationCode">
              <n-input v-model:value="addForm.defectClassificationCode" type="text" placeholder="请输入缺陷项编码" />
            </n-form-item>
            <n-form-item label="缺陷项名称" path="defectClassificationName">
              <n-input v-model:value="addForm.defectClassificationName" type="text" placeholder="请输入缺陷项名称" />
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
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage, useDialog } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import {
  getQcDefectClassification,
  addQcDefectClassification,
  editQcDefectClassification,
  deleteQcDefectClassification
} from '~/src/service/api/quality/flawClassify/index';
const searchForm = ref<flawClassify.searchForm>({
  defectClassificationCode: '',
  defectClassificationName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const addForm = ref<flawClassify.addOrEditData>({
  id: null,
  defectClassificationCode: '',
  defectClassificationName: '',
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
  defectClassificationCode: createRequiredFormRule('请输入缺陷项编码'),
  defectClassificationName: createRequiredFormRule('请输入缺陷项名称')
};

const data = ref<flawClassify.TableList[]>([]);

const columns: Ref<DataTableColumns<flawClassify.TableList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '缺陷项编码',
    key: 'defectClassificationCode',
    align: 'center',
    width: 100
  },
  {
    title: '缺陷项名称',
    key: 'defectClassificationName',
    align: 'center',
    width: 100
  },
  {
    title: '是否启用',
    key: 'enableFlag',
    align: 'center',
    width: 100,
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
    width: 100,
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
              handleDeleteItemType(row.id);
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
    deleteQcDefectClassification(id).then(res => {
      resolve(res);
    });
  });

function search() {
  init();
}
function reset() {
  searchForm.value = {
    defectClassificationCode: '',
    defectClassificationName: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function addOrEdit(count: number, row?: flawClassify.TableList): void {
  type.value = count;
  show.value = true;
  if (count === 0) {
    title.value = '新增缺陷项分类';
    addForm.value = {
      id: null,
      defectClassificationCode: '',
      defectClassificationName: '',
      enable: ''
    };
  } else {
    title.value = '编辑缺陷项分类';
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

function submitSame(func: any, msg: string): void {
  func(addForm.value)
    .then(res => {
      addOrEditComplete.value = true;
      if (res.code === 200) {
        show.value = false;
        message.success(msg);
        init();
      }
    })
    .catch(() => {
      addOrEditComplete.value = true;
    });
}

function submit(): void {
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      if (!addOrEditComplete.value) {
        message.warning('点击速度过快，请稍等');
        return;
      }
      addOrEditComplete.value = false;
      if (type.value === 0) {
        submitSame(addQcDefectClassification, '添加成功');
      } else {
        submitSame(editQcDefectClassification, '编辑成功');
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

function changeStatus(row: flawClassify.TableList, val: string): void {
  row.enable = val;
  const msg = row.enable === 'Y' ? '启用成功' : '已关闭启用';
  editQcDefectClassification(row).then(res => {
    if (res.code === 200) {
      message.success(msg);
    }
  });
}

function init() {
  startLoading();
  getQcDefectClassification(searchForm.value).then(res => {
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
