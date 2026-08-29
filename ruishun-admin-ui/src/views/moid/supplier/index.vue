<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="供应商名称">
          <n-input v-model:value="searchForm.supplierName" type="text" placeholder="请输入供应商名称" />
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="供应商列表">
      <template #right>
        <div>
          <component
            :is="
              useAddBtn(() => {
                addOrEdit(0);
              })
            "
          ></component>
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
            class="w-300px"
            label-placement="left"
            label-align="left"
            :label-width="100"
            :model="addForm"
            :rules="rules"
          >
            <n-form-item label="供应商名称" path="supplierName">
              <n-input v-model:value="addForm.supplierName" placeholder="请输入供应商名称" />
            </n-form-item>
            <n-form-item label="备注" path="remark">
              <n-input v-model:value="addForm.remark" placeholder="请输入备注" />
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
import { useMessage } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { useLoading } from '~/src/hooks';
import { useSearchBtn, useAddBtn } from '~/src/hooks/common/useBtn';
import {
  getmoldSupplierList,
  addMoldSupplier,
  editMoldSupplier,
  deleteMoldSupplier
} from '~/src/service/api/moid/supplier/index';

const searchForm = ref<supplier.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  supplierName: ''
});

const addForm = ref<supplier.addForm>({
  supplierName: '',
  remark: '',
  moldSupplierId: -1
});

const { loading, startLoading, endLoading } = useLoading();

const show = ref<boolean>(false);

const type = ref<number>(0); // 0 : 添加， 1 : 编辑

const formModel = ref<FormInst | null>(null);

const message = useMessage();
const title = ref<string>('');
const addOrEditComplete = ref<boolean>(true);

const rules = {
  supplierName: createRequiredFormRule('请输入供应商名称')
};

const data = ref<supplier.TableList[]>([
  {
    remark: '',
    moldSupplierId: 1,
    supplierName: '****'
  },
  {
    remark: '',
    moldSupplierId: 2,
    supplierName: '****'
  },
  {
    remark: '',
    moldSupplierId: 3,
    supplierName: '****'
  }
]);

const columns: Ref<DataTableColumns<supplier.TableList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '供应商名称',
    key: 'supplierName',
    align: 'center',
    width: 100
  },
  {
    title: '备注',
    key: 'remark',
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
          <n-popconfirm onPositiveClick={() => handleDeleteItemType(row.moldSupplierId)}>
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
    supplierName: ''
  };
  init();
}

function addOrEdit(count: number, row?: supplier.TableList): void {
  type.value = count;
  show.value = true;
  if (count === 0) {
    title.value = '新增供应商';
    addForm.value = {
      supplierName: '',
      remark: '',
      moldSupplierId: -1
    };
  } else {
    title.value = '编辑供应商';
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
        addMoldSupplier(addForm.value)
          .then(res => {
            if (res.code === 200) {
              submitSame('添加成功', res.code);
            }
          })
          .catch(() => {
            addOrEditComplete.value = true;
          });
      } else {
        editMoldSupplier(addForm.value)
          .then(res => {
            if (res.code === 200) {
              submitSame('编辑成功', res.code);
            }
          })
          .catch(() => {
            addOrEditComplete.value = true;
          });
      }
    }
  });
}

function handleDeleteItemType(id: number | null): void {
  if (id !== null) {
    deleteMoldSupplier(id).then(res => {
      if (res.code === 200) {
        submitSame('删除成功', res.code);
      }
    });
  }
}

function init() {
  startLoading();
  getmoldSupplierList(searchForm.value).then(res => {
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
