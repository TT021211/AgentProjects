<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="字典名称">
          <n-input v-model:value="searchForm.dictName" placeholder="请输入字典名称"></n-input>
        </n-form-item>
        <n-form-item label="字典类型">
          <n-input v-model:value="searchForm.dictType" placeholder="请输入字典名称"></n-input>
        </n-form-item>
        <n-form-item label="状态">
          <n-select
            v-model:value="searchForm.status"
            class="w-180px"
            :options="[
              { label: '启用', value: '0' },
              { label: '禁用', value: '1' }
            ]"
            placeholder="请输入字典名称"
          ></n-select>
        </n-form-item>
        <n-form-item label="创建时间">
          <n-date-picker v-model:formatted-value="searchForm.createTime" type="daterange" clearable></n-date-picker>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="字典列表">
      <template #right>
        <div>
          <NButton type="error" :disabled="!rowChecked.length" size="small" class="mr-5px" @click="delectAll">
            <NIcon size="15" :component="TrashOutline" class="mr-5px" />
            删除
          </NButton>
          <component
            :is="
              useAddBtn(() => {
                addFlag = true;
                editFlag = false;
              })
            "
          ></component>
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :columns="columns"
        :row-key="row => row.dictId"
        :data="data"
        @update:checked-row-keys="handleCheckedRowKeysChange"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="addFlag"
      :title="editFlag ? '编辑字典类型' : '新增字典类型'"
      @cancel="cancel"
      @submit="addDictType"
    >
      <template #content>
        <div>
          <n-form ref="formRef" :rules="rules" :model="form" label-placement="left" label-width="100">
            <n-form-item label="字典名称" path="dictName">
              <n-input v-model:value="form.dictName" placeholder="请输入字典名称"></n-input>
            </n-form-item>
            <n-form-item label="字典类型" path="dictType">
              <n-input v-model:value="form.dictType" placeholder="请输入字典类型"></n-input>
            </n-form-item>
            <n-form-item label="状态">
              <n-select
                v-model:value="form.status"
                :options="[
                  { label: '启用', value: '0' },
                  { label: '禁用', value: '1' }
                ]"
                placeholder="请选择状态"
              >
                >
              </n-select>
            </n-form-item>
            <n-form-item label="备注">
              <n-input v-model:value="form.remark" type="textarea" placeholder="请输入备注"></n-input>
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
import { useRouter } from 'vue-router';
import type { DataTableColumns, FormInst, DataTableRowKey } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { TrashOutline } from '@vicons/ionicons5';
import { getDictList, delDict, addDict, editDict } from '@/service/api/system/dict';
import useDictStore from '@/store/modules/dict';
import { useSearchBtn, useEditBtn, useAddBtn, useDelBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import { deepClone } from '~/src/utils';
const router = useRouter();
const message = useMessage();
const dictStore = useDictStore();
const editFlag = ref<boolean>(false);
const rowChecked = ref<DataTableRowKey[]>([]);
const addFlag = ref<boolean>(false);
const formRef = ref<null | FormInst>(null);
const { loading, startLoading, endLoading } = useLoading();
const searchForm = ref({
  dictName: '',
  dictType: '',
  status: null,
  createTime: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const form = ref<columnsType2>({
  dictName: '',
  dictType: '',
  status: null,
  remark: ''
});
const rules = {
  dictName: [{ required: true, message: '字典名称不能为空', trigger: 'blur' }],
  dictType: [{ required: true, message: '字典类型不能为空', trigger: 'blur' }]
};
type columnsType = {
  dictName: string;
  dictType: string;
  remark: string;
  status: string | null;
  createBy: string;
  createTime: string | null;
  dictId: number;
  updateBy: null | string;
  updateTime: null | string;
};
type columnsType2 = {
  dictName: string;
  dictType: string;
  remark: string;
  status: string | null;
  createBy?: string;
  createTime?: string | null;
  dictId?: number;
  updateBy?: null | string;
  updateTime?: null | string;
};
const data = ref<columnsType[]>([]);
const columns: Ref<DataTableColumns<columnsType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '字典编号',
    key: 'dictId',
    width: 100,
    sorter(row, row2) {
      return row.dictId - row2.dictId;
    }
  },
  {
    title: '字典名称',
    key: 'dictName'
  },
  {
    title: '字典类型',
    key: 'dictType',
    render: row => {
      return (
        <n-gradient-text
          style="cursor: pointer;"
          type="info"
          onClick={() => {
            goData(row.dictType);
          }}
        >
          {row.dictType}
        </n-gradient-text>
      );
    }
  },
  {
    title: '状态',
    key: 'status',
    render: row => {
      return (
        <my-switch
          v-model:value={row.status}
          checked={'0'}
          unchecked={'1'}
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
    title: '备注',
    key: 'remark'
  },
  {
    title: '创建时间',
    key: 'createTime'
  },
  {
    title: '操作',
    key: 'action',
    render: row => {
      return [
        useEditBtn(() => {
          editFlag.value = true;
          form.value = deepClone(row);
          addFlag.value = true;
        }),
        useDelBtn(() => {
          del(row.dictId);
        })
      ];
    }
  }
]);
type valType = string | number | boolean;
function changeStatus(row: any, val: valType) {
  const temp = row.dictId;
  row.dictId = val;
  editDict(row).then(res => {
    if (res.code === 200) {
      message.success('修改成功');
    } else {
      row.dictId = temp;
    }
  });
}
function goData(type: string) {
  router.push({ name: 'system_dictData', query: { id: type } });
}
function cancel() {
  addFlag.value = false;
  form.value = { dictName: '', dictType: '', status: null, remark: '' };
  init();
}
function addDictType() {
  const dictType = form.value.dictType;
  formRef.value?.validate(errors => {
    if (!errors) {
      if (!editFlag.value) {
        addDict(form.value).then(res => {
          if (res.code === 200) {
            message.success('新增成功');
            cancel();
          }
        });
      } else {
        editDict(form.value).then(res => {
          if (res.code === 200) {
            dictStore.removeDict(dictType);
            message.success('新增成功');
            cancel();
          }
        });
      }
    }
  });
}

function search() {
  searchForm.value.pageNum = 1;
  init();
}
function reset() {
  searchForm.value = {
    dictName: '',
    dictType: '',
    status: null,
    createTime: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function del(id: number | string) {
  delDict(id).then(res => {
    if (res.code === 200) {
      message.success('删除成功');
      init();
    }
  });
}
function handleCheckedRowKeysChange(rowKeys: DataTableRowKey[]) {
  rowChecked.value = rowKeys;
}
function delectAll() {
  delDict(rowChecked.value).then(res => {
    if (res.code === 200) {
      message.success('删除成功');
      init();
    }
  });
}
function init() {
  const tempsearch = {
    params: {
      beginTime: '',
      endTime: ''
    },
    dictName: searchForm.value.dictName,
    dictType: searchForm.value.dictType,
    status: searchForm.value.status,
    pageNum: searchForm.value.pageNum,
    pageSize: searchForm.value.pageSize,
    total: searchForm.value.total
  };
  if (searchForm.value.createTime) {
    tempsearch.params.beginTime = searchForm.value.createTime[0];
    tempsearch.params.endTime = searchForm.value.createTime[1];
  }
  startLoading();
  getDictList(tempsearch).then(res => {
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
