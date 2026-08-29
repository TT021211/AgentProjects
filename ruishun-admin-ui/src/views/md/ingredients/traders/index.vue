<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="贸易商名称">
          <n-input v-model:value="searchForm.tradersName" placeholder="请输入贸易商名称"></n-input>
        </n-form-item>
        <n-form-item label="贸易商编码">
          <n-input v-model:value="searchForm.tradersCode" placeholder="请输入贸易商编码"></n-input>
        </n-form-item>
        <n-form-item label="联系人">
          <n-input v-model:value="searchForm.person" placeholder="请输入联系人"></n-input>
        </n-form-item>
        <n-form-item>
          <component
            :is="
              useSearchBtn(
                () => {
                  handleSearch();
                },
                () => {
                  handleReset();
                }
              )
            "
          ></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="贸易商列表">
      <template #right>
        <div>
          <component
            :is="
              useAddBtn(() => {
                dialogFlag = true;
                flag = true;
              })
            "
            style="margin-right: 15px"
          ></component>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      :title="flag ? '新增贸易商' : '编辑贸易商'"
      :show="dialogFlag"
      @cancel="handleCancel"
      @submit="handleSubmit"
    >
      <template #content>
        <div>
          <n-form
            ref="addFormRef"
            label-placement="left"
            label-width="100px"
            label-align="left"
            :model="addForm"
            :rules="rules"
          >
            <n-form-item label="贸易商名称" :span="15" path="tradersName">
              <n-input v-model:value="addForm.tradersName" placeholder="请输入贸易商名称"></n-input>
            </n-form-item>
            <n-form-item label="贸易商简称" :span="15" path="tradersNick">
              <n-input v-model:value="addForm.tradersNick" placeholder="请输入贸易商简称"></n-input>
            </n-form-item>
            <n-form-item label="贸易商编码" :span="15" path="tradersCode">
              <n-input v-model:value="addForm.tradersCode" placeholder="请输入贸易商编码"></n-input>
            </n-form-item>
            <n-form-item label="贸易商地址" :span="15" path="tradersAddress">
              <n-input v-model:value="addForm.tradersAddress" placeholder="请输入贸易商地址"></n-input>
            </n-form-item>
            <n-form-item label="联系人" :span="15" path="person">
              <n-input v-model:value="addForm.person" placeholder="请输入联系人" />
            </n-form-item>
            <n-form-item label="联系电话" :span="15" path="phone">
              <n-input v-model:value="addForm.phone" placeholder="请输入联系电话" />
            </n-form-item>
            <n-form-item label="备注" :span="15" path="remark">
              <n-input v-model:value="addForm.remark" type="textarea" placeholder="请输入备注" />
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
import { useMessage } from 'naive-ui';
import {
  getIngredientsTradersList,
  deleteIngredientsTraders,
  editIngredientsTraders,
  addIngredientsTraders
} from '@/service/api/md/ingredients/traders';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  tradersName: string;
  tradersCode: string;
  person: string;
  pageNum: number;
  pageSize: number;
  total: number;
};

const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  tradersName: '',
  tradersCode: '',
  person: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const rules = {
  tradersName: [
    {
      required: true,
      message: '请输入贸易商名称'
    }
  ],
  tradersCode: [
    {
      required: true,
      message: '请输入贸易商编码'
    }
  ]
};
type addFormType = {
  tradersName: string;
  tradersNick: string;
  tradersCode: string;
  tradersAddress: string;
  person: string;
  phone: string;
  remark: string;
};
const addForm = ref<addFormType>({
  tradersName: '',
  tradersNick: '',
  tradersCode: '',
  tradersAddress: '',
  person: '',
  phone: '',
  remark: ''
});
const addFormRef = ref<FormInst | null>(null);
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    tradersName: '',
    tradersCode: '',
    person: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  tradersName: string;
  tradersNick: string;
  tradersCode: string;
  tradersAddress: string;
  person: string;
  phone: string;
  remark: string;
};

const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '贸易商名称',
    align: 'center',
    width: 180,
    key: 'tradersName'
  },
  {
    title: '贸易商简称',
    width: 100,
    align: 'center',
    key: 'tradersNick'
  },
  {
    title: '贸易商编码',
    align: 'center',
    width: 120,
    key: 'tradersCode',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '贸易商地址',
    width: 220,
    align: 'center',
    key: 'tradersAddress',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '联系人',
    width: 120,
    align: 'center',
    key: 'person'
  },
  {
    title: '联系人电话',
    width: 140,
    align: 'center',
    key: 'phone'
  },
  {
    title: '备注',
    align: 'center',
    key: 'remark',
    width: 80,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '操作',
    align: 'center',
    key: 'actions',
    width: 200,
    render: row => {
      return [
        useEditBtn(() => {
          handleEdit(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteIngredientsTraders(row).then(res => {
            if (res.code === 200) {
              message.success('删除成功');
              init();
            }
          });
        }, 'tiny')
      ];
    }
  }
]);

const checked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};

// 弹窗
const dialogFlag = ref<boolean>(false);
const handleCancel = () => {
  dialogFlag.value = false;
  addForm.value = {
    tradersName: '',
    tradersNick: '',
    tradersCode: '',
    tradersAddress: '',
    person: '',
    phone: '',
    remark: ''
  };
};
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addIngredientsTraders(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editIngredientsTraders(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('修改成功');
            handleCancel();
            init();
          }
        });
      }
    }
  });
}
function handleEdit(row) {
  dialogFlag.value = true;
  flag.value = false;
  addForm.value = deepClone(row);
}

// 深度clone
function deepClone(obj: any) {
  return JSON.parse(JSON.stringify(obj));
}
function init() {
  startLoading();
  data.value = [];
  getIngredientsTradersList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  init();
});
</script>

<style scoped></style>
