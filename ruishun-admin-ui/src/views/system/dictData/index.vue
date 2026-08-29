<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="字典名称">
          <n-select
            v-model:value="searchForm.dictType"
            :options="typeOptions"
            class="w-180px"
            placeholder="请选择字典名称"
          ></n-select>
        </n-form-item>
        <n-form-item label="字典标签">
          <n-input v-model:value="searchForm.dictLabel" placeholder="请输入字典标签"></n-input>
        </n-form-item>
        <n-form-item label="状态">
          <n-select
            v-model:value="searchForm.status"
            class="w-180px"
            :options="[
              { value: '0', label: '启用' },
              { value: '1', label: '禁用' }
            ]"
            placeholder="请选择状态"
          ></n-select>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="字典数据">
      <template #right>
        <div>
          <component :is="useBackBtn()"></component>
          <component :is="useAddBtn(add)"></component>
          <NButton type="error" :disabled="!rowChecked.length" size="small" class="mr-5px" @click="delectAll">
            <NIcon size="15" :component="TrashOutline" class="mr-5px" />
            删除
          </NButton>
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
        </div>
      </template>
      <n-data-table
        :row-key="row => row.dictCode"
        :columns="columns"
        :data="data"
        :loading="loading"
        @update-checked-row-keys="changeSelect"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="addFlog"
      :title="editFlag ? '修改字典数据' : '新增字典数据'"
      @cancel="cancel"
      @submit.enter="submit"
      @keyup.enter="submit"
    >
      <template #content>
        <div>
          <n-form ref="addFormRef" :rules="rules" :model="addForm" label-placement="left" label-width="100px">
            <n-form-item label="字典类型">
              <n-input v-model:value="addForm.dictType" disabled placeholder="请输入字典类型"></n-input>
            </n-form-item>
            <!-- <n-form-item label="模板类型" path="remark">
              <n-select
                v-model:value="addForm.remark"
                :options="[
                  { value: '0', label: '轴标签' },
                  { value: '1', label: '箱标签' }
                ]"
                placeholder="请选择模板类型"
              ></n-select>
            </n-form-item> -->
            <n-form-item label="字典标签" path="dictLabel">
              <n-input v-model:value="addForm.dictLabel" placeholder="请输入字典标签"></n-input>
            </n-form-item>
            <n-form-item label="字典键值" path="dictValue">
              <n-input v-model:value="addForm.dictValue" placeholder="请输入字典键值"></n-input>
            </n-form-item>
            <n-form-item label="字典排序" path="dictSort">
              <n-input-number v-model:value="addForm.dictSort" placeholder="请输入字典排序"></n-input-number>
            </n-form-item>
            <n-form-item label="状态">
              <n-select
                v-model:value="addForm.status"
                :options="[
                  { value: '0', label: '启用' },
                  { value: '1', label: '禁用' }
                ]"
                placeholder="请选择状态"
              ></n-select>
            </n-form-item>
            <n-form-item label="回显样式">
              <n-select
                v-model:value="addForm.listClass"
                :options="[
                  { value: 'default', label: '默认' },
                  { value: 'info', label: '信息' },
                  { value: 'danger', label: '链接' },
                  { value: 'primary', label: '主要' },
                  { value: 'success', label: '成功' },
                  { value: 'warning', label: '警告' }
                ]"
                placeholder="请选择回显样式"
              ></n-select>
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
import { useRoute } from 'vue-router';
import type { DataTableColumns, DataTableRowKey, FormInst } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { TrashOutline } from '@vicons/ionicons5';
import {
  deleteDictData,
  addDictData,
  editDictData,
  getDictDataList,
  getDictTypeList
} from '@/service/api/system/dictData';
import useDictStore from '@/store/modules/dict';
import { useSearchBtn, useAddBtn, useDelBtn, useEditBtn, useBackBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import { createRequiredFormRule, deepClone } from '~/src/utils';

const addFormRef = ref<FormInst | null>(null);
const route = useRoute();
const addFlog = ref<boolean>(false);
const message = useMessage();
const editFlag = ref<boolean>(false);
const rowChecked = ref<DataTableRowKey[]>([]);

const dictStore = useDictStore();
function changeSelect(rowKeys: DataTableRowKey[]) {
  rowChecked.value = rowKeys;
}
function delectAll() {
  deleteDictData(rowChecked.value).then(res => {
    if (res.code === 200) {
      init();
      message.success('删除成功');
    }
  });
}
type addFormType = {
  cssClass: string;
  dictLabel: string;
  dictSort: number;
  dictType: string;
  dictValue: string;
  listClass: string | null;
  remark: string | null;
  status: string | null;
};
const addForm = ref<addFormType>({
  cssClass: '',
  dictLabel: '',
  dictSort: 0,
  dictType: '',
  dictValue: '',
  listClass: null,
  remark: null,
  status: null
});
const rules = {
  dictLabel: createRequiredFormRule('请输入字典标签'),
  dictValue: createRequiredFormRule('请输入字典键值'),
  dictSort: createRequiredFormRule('请输入字典排序'),
  remark: createRequiredFormRule('请选择模板类型')
};
const searchForm = ref<{
  status: string | null;
  remark: string | null;
  dictLabel: string;
  dictType: string;
  pageNum: number;
  pageSize: number;
  total: number;
}>({
  remark: null,
  status: null,
  dictLabel: '',
  dictType: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function add() {
  addFlog.value = true;
  editFlag.value = false;
  addForm.value.status = '0';
  addForm.value.listClass = 'default';
  const temp = searchForm.value.dictType ?? (route.query.id as string);
  addForm.value.dictType = temp;
}

const { loading, startLoading, endLoading } = useLoading();

type columnType = {
  createBy: string;
  createTime: string;
  cssClass: string;
  default: boolean;
  dictCode: number;
  dictLabel: string;
  dictSort: number;
  dictType: string;
  dictValue: string; // '0'
  isDefault: string;
  listClass: string;
  remark: string;
  status: string; // ''0
  updateBy: null;
  updateTime: null;
};
const data = ref<columnType[]>([]);
const columns: Ref<DataTableColumns<columnType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '字典编码',
    key: 'dictCode',
    width: 100
  },
  {
    title: '字典标签',
    key: 'dictLabel',
    width: 140
  },
  {
    title: '字典键值',
    key: 'dictValue',
    width: 140
  },
  {
    title: '字典排序',
    align: 'center',
    key: 'dictSort',
    width: 100
  },
  {
    title: '状态',
    key: 'status',
    width: 100,
    render: row => {
      return <n-tag type={row.status === '0' ? 'success' : 'error'}>{row.status === '0' ? '正常' : '停用'}</n-tag>;
    }
  },
  // {
  //   title: '模板类型',
  //   key: 'remark',
  //   width: 100,
  //   render(row) {
  //     return row.remark === '1' ? <span>{'箱模板'}</span> : <span>{'轴模板'}</span>;
  //   }
  // },
  {
    title: '创建时间',
    key: 'createTime',
    width: 250
  },
  {
    title: '操作',
    key: 'actions',
    width: 180,
    render: row => {
      return [
        useDelBtn(() => {
          deleteDictData(row.dictCode).then(res => {
            const dictType = row.dictType;
            if (res.code === 200) {
              dictStore.removeDict(dictType);
              init();
              message.success('删除成功');
            }
          });
        }),
        useEditBtn(() => {
          edit(row);
        })
      ];
    }
  }
]);
function cancel() {
  addFlog.value = false;
  const temp = {
    cssClass: '',
    dictLabel: '',
    dictSort: 0,
    dictValue: '',
    listClass: null,
    remark: null,
    status: null
  };
  addForm.value = { ...addForm.value, ...temp };
  init();
}
function edit(row: columnType) {
  addForm.value = deepClone(row);
  editFlag.value = true;
  addFlog.value = true;
}
function submit() {
  const dictType = addForm.value.dictType;
  addFormRef.value?.validate(errors => {
    if (!errors) {
      if (!editFlag.value) {
        addDictData(addForm.value).then(res => {
          if (res.code === 200) {
            dictStore.removeDict(dictType);
            message.success('添加成功');
            cancel();
          }
        });
      } else {
        editDictData(addForm.value).then(res => {
          if (res.code === 200) {
            dictStore.removeDict(dictType);
            message.success('修改成功');
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
    remark: null,
    status: null,
    dictLabel: '',
    dictType: route.query.id as string,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function init() {
  startLoading();

  getDictDataList(searchForm.value).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
  endLoading();
}
const typeOptions = ref<any[]>([]);
// const type = ref<string>('');
onMounted(() => {
  searchForm.value.dictType = route.query.id as string;
  // fetchDictData(type.value).then(res => {
  //   if (res.code === 200) {
  //     data.value = res.data;
  //     searchForm.value.total = res.total;
  //   }
  // });
  init();
  // 获取字典列表
  getDictTypeList().then(res => {
    res.data.forEach(item => {
      typeOptions.value.push({
        label: item.dictName,
        value: item.dictType
      });
    });
  });
});
</script>

<style scoped></style>
