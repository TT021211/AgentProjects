<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="项目名称">
          <n-input v-model:value="searchForm.maintainItemName" type="text" placeholder="请输入项目名称" />
        </n-form-item>
        <n-form-item label="项目内容">
          <n-input v-model:value="searchForm.maintainItemContent" type="text" placeholder="请输入项目内容" />
        </n-form-item>
        <n-form-item label="完成标准">
          <n-input v-model:value="searchForm.maintainItemStandard" type="text" placeholder="请输入完成标准" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="">
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

    <my-dialog :show="show" width="800px" :title="title" @cancel="cancel" @submit="submit">
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
            <n-form-item label="项目名称" path="maintainItemName">
              <n-input v-model:value="addForm.maintainItemName" placeholder="请输入项目名称" />
            </n-form-item>
            <n-form-item label="项目内容" path="maintainItemContent">
              <n-input v-model:value="addForm.maintainItemContent" placeholder="请输入项目内容" />
            </n-form-item>
            <n-form-item label="完成标准" path="maintainItemStandard">
              <n-input v-model:value="addForm.maintainItemStandard" placeholder="请输入完成标准"></n-input>
            </n-form-item>
            <n-form-item label="是否启用">
              <n-switch v-model:value="addForm.maintainItemEnable" :checked-value="'Y'" :unchecked-value="'N'" />
            </n-form-item>
            <n-form-item label="备注" path="maintainItemRemark">
              <n-input v-model:value="addForm.maintainItemRemark" placeholder="请输入备注"></n-input>
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'UpkeepPlanSet'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { useLoading } from '~/src/hooks';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import {
  getMoldMaintainItem,
  addMoldMaintainItem,
  editMoldMaintainItem,
  deleteMoldMaintainItem
} from '~/src/service/api/moid/upkeepPlan/index';
const searchForm = ref<upkeepPlan.setSearchFrom>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  maintainItemName: '',
  maintainItemStandard: '',
  maintainItemContent: ''
});

const addForm = ref<upkeepPlan.setAddForm>({
  maintainItemName: '',
  maintainItemContent: '',
  maintainItemStandard: '',
  maintainItemEnable: '',
  maintainItemRemark: '',
  maintainItemId: null
});

const { loading, startLoading, endLoading } = useLoading();

const show = ref<boolean>(false);

const type = ref<number>(0); // 0 : 添加， 1 : 编辑

const formModel = ref<FormInst | null>(null);

const message = useMessage();

const addOrEditComplete = ref<boolean>(true);
const title = ref<string>('');
const rules = {
  maintainItemName: createRequiredFormRule('请输入项目名称'),
  maintainItemContent: createRequiredFormRule('请输入项目内容'),
  maintainItemStandard: createRequiredFormRule('完成标准')
};

const data = ref<upkeepPlan.setTabelList[]>([
  {
    maintainItemId: 1,
    maintainItemName: '润滑',
    maintainItemContent: 'xxxxx',
    maintainItemStandard: 'xxx',
    maintainItemEnable: 'Y',
    maintainItemRemark: ''
  },
  {
    maintainItemId: 1,
    maintainItemName: '线路整齐',
    maintainItemContent: 'xxxxx',
    maintainItemStandard: 'xxx',
    maintainItemEnable: 'Y',
    maintainItemRemark: ''
  },
  {
    maintainItemId: 1,
    maintainItemName: '安全防护',
    maintainItemContent: 'xxxxx',
    maintainItemStandard: 'xxx',
    maintainItemEnable: 'Y',
    maintainItemRemark: ''
  },
  {
    maintainItemId: 1,
    maintainItemName: '排废风机',
    maintainItemContent: 'xxxxx',
    maintainItemStandard: 'xxx',
    maintainItemEnable: 'Y',
    maintainItemRemark: ''
  },
  {
    maintainItemId: 1,
    maintainItemName: '排线',
    maintainItemContent: 'xxxxx',
    maintainItemStandard: 'xxx',
    maintainItemEnable: 'Y',
    maintainItemRemark: ''
  }
]);

const columns: Ref<DataTableColumns<upkeepPlan.setTabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '项目名称',
    key: 'maintainItemName',
    align: 'center',
    width: 100
  },
  {
    title: '项目内容',
    key: 'maintainItemContent',
    align: 'center',
    width: 100
  },
  {
    title: '完成标准',
    key: 'maintainItemStandard',
    align: 'center',
    width: 100
  },
  {
    title: '是否启用',
    key: 'maintainItemEnable',
    align: 'center',
    render: row => {
      return (
        <my-switch
          v-model:value={row.maintainItemEnable}
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
    title: '备注',
    key: 'maintainItemRemark',
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
          <n-popconfirm onPositiveClick={() => handleDeleteItemType(row.maintainItemId)}>
            {{
              default: () => '确认删除',
              trigger: () => (
                <n-button type="warning" size={'small'}>
                  <icon-mdi-delete />
                  删除
                </n-button>
              )
            }}
          </n-popconfirm>
        </n-space>
      );
    }
  }
]);

function search() {
  init();
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    maintainItemName: '',
    maintainItemStandard: '',
    maintainItemContent: ''
  };
  init();
}

function addOrEdit(count: number, row?: upkeepPlan.setTabelList): void {
  type.value = count;
  show.value = true;
  if (count === 0) {
    title.value = '新增';
    for (const key in addForm.value) {
      if (key !== 'maintainItemId') {
        addForm.value[key] = '';
      }
    }
    addForm.value.maintainItemEnable = 'N';
    addForm.value.maintainItemId = null;
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
        addMoldMaintainItem(addForm.value)
          .then(res => {
            submitSame('添加成功', res.code);
          })
          .catch(() => {
            addOrEditComplete.value = true;
          });
      } else {
        editMoldMaintainItem(addForm.value)
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

function changeStatus(row: upkeepPlan.setTabelList, val: string): void {
  row.maintainItemEnable = val;
  const msg = row.maintainItemEnable === 'Y' ? '启用成功' : '已关闭启用';
  editMoldMaintainItem(row).then(res => {
    if (res.code === 200) {
      message.success(msg);
    }
  });
}

function handleDeleteItemType(id: number): void {
  if (id !== null) {
    deleteMoldMaintainItem(id).then(res => {
      submitSame('删除成功', res.code);
    });
  }
}

function init() {
  startLoading();
  getMoldMaintainItem(searchForm.value).then(res => {
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
