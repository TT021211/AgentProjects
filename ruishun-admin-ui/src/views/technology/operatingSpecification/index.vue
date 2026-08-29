<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="操作规范名称">
          <n-input v-model:value="searchForm.operationName" type="text" placeholder="请输入操作规范名称" />
        </n-form-item>
        <n-form-item label="操作规范编码">
          <n-input v-model:value="searchForm.operationCode" type="text" placeholder="请输入操作规范编码" />
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
            新增
          </n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog :show="show" :title="title" width="900px" @cancel="cancel" @submit="submit">
      <template #content>
        <div>
          <n-form
            ref="formModel"
            style="width: 800px"
            label-placement="left"
            label-align="left"
            :label-width="130"
            :model="addForm"
            :rules="rules"
          >
            <n-grid :cols="2" :x-gap="20">
              <n-form-item-grid-item label="操作规范名称" path="operationName">
                <n-input
                  v-model:value="addForm.operationName"
                  placeholder="请输入操作规范名称"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="操作规范编码" path="operationCode">
                <n-input
                  v-model:value="addForm.operationCode"
                  placeholder="请输入操作规范编码"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="关联漆包机机型" path="enanellingWorkbenchId">
                <n-space vertical>
                  <n-select
                    v-model:value="addForm.enanellingWorkbenchId"
                    :options="enanellingWorkbenchList"
                    :style="{ width: formItemWidth }"
                    placeholder="请选择关联漆包机机型"
                    filterable
                    @update:value="handleUpdateValue"
                  ></n-select>
                </n-space>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="生效日期" path="ableTime">
                <n-date-picker
                  v-model:formatted-value="addForm.ableTime"
                  type="datetime"
                  clearable
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="上传文件">
                <n-upload
                  :show-file-list="true"
                  :style="{ width: formItemWidth }"
                  :default-file-list="fileList"
                  :headers="{ Authorization: 'Bearer ' + useAuthStore().token }"
                  :default-upload="true"
                  :max="1"
                  :action="baseurl + '/file/upload'"
                  :on-error="errorFile"
                  @finish="finish"
                  @remove="removeFile"
                >
                  <n-button :style="{ width: formItemWidth }">选择文件</n-button>
                </n-upload>
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>

    <div v-show="previewUrl">
      <iframe :src="previewUrl" width="100%" height="600px"></iframe>
    </div>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'SortManagement'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns, UploadFileInfo } from 'naive-ui';
import { useMessage, useDialog } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { getWorkbenchEnamellingList } from '@/service/api/md/workbench/enamelling';
import {
  getOperationMethodList,
  addOperationMethod,
  editOperationMethod,
  deleteOperationMethod
} from '@/service/api/technology/operationInstruction';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useAuthStore } from '~/src/store/modules/auth';
import { serviceEnv } from '~/.env-config';
import { Base64 } from '~/src/config/base64';
type SelectMixedOption = {
  label: string;
  value: number;
};

const searchForm = ref<operationInstruction.searchForm>({
  operationName: '',
  operationCode: '',
  enanellingWorkbenchId: null,
  pageNum: 1,
  pageSize: 10,
  total: 0,
  operationType: '1'
});

const addForm = ref<operationInstruction.addOrEditForm>({
  operationName: null,
  operationCode: null,
  enanellingWorkbenchId: null,
  enanellingWorkbenchName: null,
  ableTime: null,
  filePath: null,
  id: null,
  image: null,
  operationType: '0'
});

const { loading, startLoading, endLoading } = useLoading();
const baseurl = ref<string>(serviceEnv[import.meta.env.VITE_SERVICE_ENV as string].url);
const kkFileUrl = ref<string>(serviceEnv[import.meta.env.VITE_SERVICE_ENV as string].kkFileUrl);
const show = ref<boolean>(false);
const fileList = ref<UploadFileInfo[]>([]);
const type = ref<number>(0); // 0 : 添加， 1 : 编辑
const formItemWidth = ref<string>('240px');
const formModel = ref<FormInst | null>(null);
const title = ref<string>('');
const message = useMessage();
const dialog = useDialog();
const previewUrl = ref<string>('');
// const addOrEditComplete = ref<boolean>(true);
const enanellingWorkbenchList = ref<SelectMixedOption[]>([]);
const rules = {
  operationName: createRequiredFormRule('请输入操作规范名称'),
  operationCode: createRequiredFormRule('请输入操作规范编码'),
  enanellingWorkbenchId: createRequiredFormRule('请选择关联漆包机机型'),
  ableTime: createRequiredFormRule('请输入生效日期'),
  filePath: createRequiredFormRule('请选择上传文件')
};

const data = ref<operationInstruction.columns[]>([]);

const columns: Ref<DataTableColumns<operationInstruction.columns>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '操作规范名称',
    key: 'operationName',
    align: 'center',
    width: 100
  },
  {
    title: '操作规范编码',
    key: 'operationCode',
    align: 'center',
    width: 100
  },
  {
    title: '关联漆包机机型',
    key: 'enanellingWorkbenchName',
    align: 'center',
    width: 100
  },
  {
    title: '生效日期',
    key: 'ableTime',
    align: 'center',
    width: 100
  },
  {
    title: '上传文件',
    key: 'image',
    align: 'center',
    width: 100
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
              handleDeleteItemType(row.id);
            }}
          >
            <icon-mdi-delete />
            删除
          </n-button>

          <n-button
            type="info"
            size={'small'}
            onClick={() => {
              handlePreview(row);
            }}
          >
            <icon-mdi-search />
            预览
          </n-button>
        </n-space>
      );
    }
  }
]);

function search() {
  searchForm.value.pageNum = 1;
  init();
}
function reset() {
  searchForm.value = {
    operationName: '',
    operationCode: '',
    enanellingWorkbenchId: null,
    pageNum: 1,
    pageSize: 10,
    total: 0,
    operationType: '1'
  };
  init();
}

function addOrEdit(count: number, row?: operationInstruction.columns): void {
  type.value = count;
  show.value = true;
  if (count === 0) {
    fileList.value = [];
    title.value = '新增';
    for (const key in addForm.value) {
      if (addForm.value[key] !== null && key !== 'operationType') {
        addForm.value[key] = null;
      }
    }
  } else {
    title.value = '编辑';
    for (const key in addForm.value) {
      if (row) {
        addForm.value[key] = row[key];
      }
    }
    if (row) {
      fileList.value = [
        {
          id: row.id.toString(),
          name: row.image,
          status: 'finished'
        }
      ];
    }
  }
}

function handleUpdateValue(value: number) {
  const index = enanellingWorkbenchList.value.findIndex(item => item.value === value);
  if (index > -1) {
    addForm.value.enanellingWorkbenchName = enanellingWorkbenchList.value[index].label;
  }
}

function handlePreview(row: operationInstruction.columns) {
  const url = row.filePath; // 要预览文件的访问地址
  window.open(`${kkFileUrl.value}/onlinePreview?url=${encodeURIComponent(Base64.encode(url))}`);
}

function errorFile({ file, event }) {
  // eslint-disable-next-line no-console
  console.log(file, event);
  const response = JSON.parse(event.currentTarget.response);
  if (response.code !== 200) {
    message.error(response.msg);
  }
}

function finish({ file, event }) {
  // eslint-disable-next-line no-console
  console.log('file ==> ', file);
  // eslint-disable-next-line no-console
  console.log('event ==> ', event);
  const response = JSON.parse(event.currentTarget.response);
  if (response.code === 200) {
    addForm.value.filePath = response.data.url;
    addForm.value.image = response.data.name;
  } else {
    message.error(response.msg);
  }
}

function removeFile() {
  addForm.value.filePath = null;
  addForm.value.image = null;
}

function cancel(): void {
  show.value = false;
}

function submitRepetition(res: any, msg: string) {
  if (res.code === 200) {
    message.success(msg);
    show.value = false;
    init();
  }
}

function submit(): void {
  if (!addForm.value.filePath) {
    message.warning('请选择文件上传');
    return;
  }
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      addForm.value.operationType = '1';
      if (type.value === 0) {
        addOperationMethod(addForm.value).then(res => {
          submitRepetition(res, '添加成功');
        });
      } else {
        editOperationMethod(addForm.value).then(res => {
          submitRepetition(res, '编辑成功');
        });
      }
    }
  });
}

const sleep = id =>
  new Promise(resolve => {
    deleteOperationMethod(id).then(res => {
      resolve(res);
    });
  });

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

function getList() {
  getWorkbenchEnamellingList({ pageSize: 999, isEnable: 1 }).then(res => {
    res.rows.forEach((item: { equipmentCode: any; id: any }) => {
      enanellingWorkbenchList.value.push({
        label: item.equipmentCode,
        value: item.id
      });
    });
  });
}

function init() {
  startLoading();
  getOperationMethodList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
  getList();
});
</script>

<style scoped></style>
