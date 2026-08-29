<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form :model="searchForm" inline>
        <n-form-item label="启用状态">
          <n-select
            v-model:value="searchForm.ifEnable"
            placeholder="请选择"
            :options="options"
            style="width: 180px"
          ></n-select>
        </n-form-item>
        <n-form-item label="单位名称">
          <n-input v-model:value="searchForm.measureName"></n-input>
        </n-form-item>
        <n-form-item>
          <n-button type="primary" class="mr-10px" @click="handleSearch">
            <icon-ic-round-search class="mr-4px text-20px" />
            搜索
          </n-button>
          <n-button @click="handleResert">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="单位列表">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button type="primary" class="ml-5px" size="small" @click="openDialog">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增单位
          </n-button>
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <!-- <n-pagination
        :page="searchForm.pageNum"
        :item-count="searchForm.pageTotal"
        style="margin-top: 20px; float: right"
        :page-size="searchForm.pageSize"
        @update:page="changePage"
      ></n-pagination> -->
      <my-pagination v-model:search-form="searchForm" @init="getList"></my-pagination>
    </my-card>
    <my-dialog :show="show" :title="addForm.id ? '编辑单位' : '新增单位'" @cancel="closeDialog" @submit="submitEvent">
      <template #content>
        <div>
          <n-form
            ref="formRef"
            :model="addForm"
            label-width="120px"
            :rules="rules"
            label-placement="left"
            label-align="left"
          >
            <n-form-item label="单位名称：" path="measureName">
              <n-input v-model:value="addForm.measureName"></n-input>
            </n-form-item>
            <n-form-item label="单位编码：" path="measureCode">
              <n-input v-model:value="addForm.measureCode"></n-input>
            </n-form-item>
            <n-form-item label="是否启用：">
              <n-switch v-model:value="addForm.ifEnable" :checked-value="1" :unchecked-value="0"></n-switch>
            </n-form-item>
            <n-form-item label="是否主单位：" path="ifPrimary">
              <n-radio-group v-model:value="addForm.ifPrimary" @change="change">
                <n-radio :key="1" :value="1">是</n-radio>
                <n-radio :key="0" :value="0">否</n-radio>
              </n-radio-group>
            </n-form-item>
            <n-form-item v-if="!flag" label="主单位：" path="primaryName">
              <n-select v-model:value="addForm.primaryName" :options="primaryOptions"></n-select>
            </n-form-item>
            <n-form-item v-if="!flag" label="换算率：">
              <n-input v-model:value="addForm.changeRate"></n-input>
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
import type { DataTableColumns, FormInst } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { useLoading, useWarning } from '@/hooks';
import { getUnitList, deleteUnit, addUnit, getAllUnit, editUnit } from '@/service/api/md/unit/index';
import { createRequiredFormRule } from '~/src/utils';

const flag = ref<boolean>(false);

const dialog = useWarning();
const options = [
  {
    label: '启用',
    value: '1'
  },
  {
    label: '禁用',
    value: '0'
  }
];
const primaryOptions = ref<{ value: string; label: string }[]>([]);
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();
type dataType = {
  measureCode: string;
  measureName: string;
  ifPrimary: string;
  changeRate: string;
  ifEnable: string;
  createTime: string;
};
type searchType = {
  ifEnable: string | null;
  measureName: string;
  pageNum: number;
  pageSize: number;
  total?: number;
};
const searchForm = ref<searchType>({
  ifEnable: null,
  measureName: '',
  pageNum: 1,
  pageSize: 10
});
function handleResert() {
  searchForm.value = {
    ifEnable: null,
    measureName: '',
    pageNum: 1,
    pageSize: 10
  };
  getList();
}
function handleSearch() {
  searchForm.value.pageNum = 1;
  getList();
}
// function changePage(page: number) {
//   searchForm.value.pageNum = page;
//   getList();
// }

type addType = {
  id?: string;
  measureCode: string;
  measureName: string;
  ifPrimary: number;
  changeRate: string;
  primaryName: string;
  ifEnable: number | string | boolean | undefined;
};

const rules = {
  measureCode: createRequiredFormRule('请输入单位编码'),
  measureName: createRequiredFormRule('请输入单位名称'),
  ifPrimary: createRequiredFormRule('请选择是否主单位'),
  // changeRate: createRequiredFormRule('请输入换算率'),
  primaryName: createRequiredFormRule('请选择主单位')
};
const addForm = ref<addType>({
  measureCode: '',
  measureName: '',
  ifPrimary: 0,
  changeRate: '',
  primaryName: '',
  ifEnable: undefined
});
function change() {
  // const num = parseInt(addForm.value.ifPrimary, 10);
  if (addForm.value.ifPrimary === 1) {
    flag.value = true;
  } else {
    flag.value = false;
  }
}
const show = ref<boolean>(false);
function openDialog(row: any) {
  getAllUnit().then(res => {
    primaryOptions.value = [];
    if (res.code === 200) {
      res.data.forEach((item: any) => {
        primaryOptions.value.push({
          label: item.measureName,
          value: item.id
        });
      });
    }
  });
  if (row) {
    addForm.value = row;
    if (row.ifPrimary === 1) {
      flag.value = true;
    } else {
      flag.value = false;
    }
  }
  show.value = true;
}
function closeDialog() {
  addForm.value = {
    measureCode: '',
    measureName: '',
    ifPrimary: 0,
    changeRate: '',
    primaryName: '',
    ifEnable: undefined
  };
  show.value = false;
}
const formRef = ref<FormInst | null>(null);
function submitEvent() {
  formRef.value?.validate(errors => {
    if (!errors) {
      if (addForm.value.id) {
        editUnit(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('编辑成功');
            getList();
          }
        });
      } else {
        addUnit(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('新增成功');
            getList();
          }
        });
      }
      closeDialog();
    }
  });
}
function changeStatus(row: any, val: number) {
  row.ifEnable = val;
  editUnit(row).then(res => {
    if (res.code === 200) {
      message.success('编辑成功');
      getList();
    }
  });
}
const columns: Ref<DataTableColumns<dataType>> = ref([
  {
    title: '单位编码',
    align: 'center',
    key: 'measureCode'
  },
  {
    title: '单位名称',
    align: 'center',
    key: 'measureName'
  },
  {
    title: '是否主单位',
    align: 'center',
    key: 'ifPrimary',
    render(row: any) {
      return <n-tag type={row.ifPrimary === 1 ? 'success' : 'error'}>{row.ifPrimary === 1 ? '是' : '否'}</n-tag>;
    }
  },
  {
    title: '换算率',
    align: 'center',
    key: 'changeRate'
  },
  {
    title: '是否启用',
    align: 'center',
    key: 'ifEnable',
    render(row: any) {
      return (
        <my-switch
          v-model:value={row.ifEnable}
          checked={1}
          unchecked={0}
          onChecked={val => {
            changeStatus(row, val);
          }}
          onUnchecked={val => {
            changeStatus(row, val);
          }}
        ></my-switch>
      );
      // return <n-tag type={row.ifEnable === 1 ? 'success' : 'error'}>{row.ifEnable === 1 ? '启用' : '禁用'}</n-tag>;
    }
  },
  {
    title: '创建时间',
    align: 'center',
    key: 'createTime'
  },
  {
    title: '操作',
    key: 'actions',
    align: 'center',
    width: 200,
    render(row: any) {
      return (
        <div class="flex-center">
          <n-button
            type="primary"
            size="small"
            class="mr-2"
            onClick={() => {
              openDialog(row);
            }}
          >
            <icon-tdesign-edit class="mr-2" />
            编辑
          </n-button>
          <n-button
            type="error"
            size="small"
            onClick={() => {
              dialog.warn(() => {
                deleteUnit(row.id).then(res => {
                  if (res.code === 200) {
                    message.success('删除成功');
                    getList();
                  } else {
                    message.error('删除失败');
                  }
                });
              });
            }}
          >
            <icon-tdesign-delete class="mr-2" />
            删除
          </n-button>
        </div>
      );
    }
  }
]);
const data = ref<dataType[]>([]);
function getList() {
  startLoading();
  getUnitList(searchForm.value).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
    endLoading();
  });
}
onMounted(() => {
  getList();
});
</script>

<style lang="scss" scoped></style>
