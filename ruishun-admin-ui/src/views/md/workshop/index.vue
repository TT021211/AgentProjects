<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline :model="searchForm">
        <n-form-item label="车间名称">
          <n-input v-model:value="searchForm.workshopName" placeholder="请输入车间名称" />
        </n-form-item>
        <n-form-item label="负责人">
          <n-input v-model:value="searchForm.head" placeholder="请输入负责人" />
        </n-form-item>
        <n-form-item label="是否启用">
          <n-select
            v-model:value="searchForm.ifEnable"
            style="width: 180px"
            :options="[
              { label: '启用', value: 1 },
              { label: '禁用', value: 0 }
            ]"
          />
        </n-form-item>
        <n-form-item>
          <n-button type="primary" class="mr-10px" @click="handleSearch">
            <icon-ic-round-search class="mr-4px text-20px" />
            搜索
          </n-button>
          <n-button @click="handleReset">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="车间列表">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="medium"></cx-columns>
          <n-button type="primary" class="ml-5px" @click="openDialog">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增车间
          </n-button>
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <!-- <n-pagination
        v-model:page="searchForm.pageNum"
        v-model:page-size="searchForm.pageSize"
        style="margin-top: 20px; float: right"
        :item-count="searchForm.total"
        @update:page="changePage"
      ></n-pagination> -->
      <my-pagination v-model:search-form="searchForm" @init="getList"></my-pagination>
    </my-card>
    <my-dialog :title="addForm.id ? '编辑车间' : '新增车间'" :show="showDialog" @cancel="cancel" @submit="addWorkshop">
      <template #content>
        <div>
          <n-form ref="formRef" label-placement="left" label-width="100px" :model="addForm" :rules="rules">
            <n-form-item label="车间名称：" path="workshopName">
              <n-input v-model:value="addForm.workshopName" placeholder="请输入车间名称" />
            </n-form-item>
            <n-form-item label="车间编码：" path="workshopCode">
              <n-input v-model:value="addForm.workshopCode" placeholder="请输入车间编码" />
            </n-form-item>
            <n-form-item label="车间面积：" path="area">
              <n-input v-model:value="addForm.area" placeholder="请输入所属区域" />
            </n-form-item>
            <n-form-item label="负责人：" path="head">
              <n-input v-model:value="addForm.head" placeholder="请输入负责人" />
            </n-form-item>

            <n-form-item label="是否启用：" path="ifEnable">
              <n-switch v-model:value="addForm.ifEnable" :checked-value="1" :unchecked-value="0"></n-switch>
              <!-- <n-select
                v-model:value="addForm.ifEnable"
                style="width: 180px"
                :options="[
                  { label: '启用', value: 1 },
                  { label: '禁用', value: 0 }
                ]"
              /> -->
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { onMounted, ref } from 'vue';
import { useMessage, useDialog } from 'naive-ui';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { getWorkshopList, deleteWorkshop, editWorkshop, addWorkshop as add } from '@/service/api/md/workshop';
import { useLoading, useBoolean } from '~/src/hooks';
import { createRequiredFormRule, deepClone } from '~/src/utils';

const formRef = ref<FormInst | null>(null);
const dialog = useDialog();
const message = useMessage();
type addType = {
  id?: number;
  workshopName: string;
  workshopCode: string;
  area: string;
  head: string;
  ifEnable: number | undefined | string | boolean;
};
const rules = {
  workshopName: createRequiredFormRule('请输入车间名称'),
  workshopCode: createRequiredFormRule('请输入车间编号'),
  area: createRequiredFormRule('请输入车间面积'),
  head: createRequiredFormRule('请输入车间负责人')
};
const addForm = ref<addType>({
  workshopName: '',
  workshopCode: '',
  area: '',
  head: '',
  ifEnable: undefined
});
const { bool: showDialog, setTrue: openDialog, setFalse: closeDialog } = useBoolean();
type searchType = {
  workshopName: string;
  head: string;
  ifEnable: number | null;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchForm = ref<searchType>({
  workshopName: '',
  head: '',
  ifEnable: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function handleSearch() {
  searchForm.value.pageNum = 1;
  getList();
}
function handleReset() {
  searchForm.value = {
    workshopName: '',
    head: '',
    ifEnable: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  getList();
}
// function changePage(page: number) {
//   searchForm.value.pageNum = page;
//   getList();
// }
type columnsType = {
  workshopName: string;
  workshopCode: string;
  area: string;
  head: string;
  ifEnable: number;
  createTime: string;
};
const { loading, startLoading, endLoading } = useLoading();
const data = ref<columnsType[]>([]);
function getList() {
  startLoading();
  getWorkshopList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
function cancel() {
  addForm.value = {
    workshopName: '',
    workshopCode: '',
    area: '',
    head: '',
    ifEnable: undefined
  };
  closeDialog();
}
function edit(row: any) {
  addForm.value = deepClone(row);
  openDialog();
}
function changeStatus(row: any, val: number) {
  row.ifEnable = val;
  editWorkshop(row).then(res => {
    if (res.code === 200) {
      message.success('修改成功');
      getList();
    }
  });
}
function addWorkshop() {
  if (!formRef.value) return;
  formRef.value.validate(error => {
    if (!error) {
      if (addForm.value.id) {
        editWorkshop(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('编辑成功');
            getList();
          }
        });
      } else {
        add(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('新增成功');
            getList();
          }
        });
      }

      cancel();
    }
  });
}
const columns: Ref<DataTableColumns<columnsType>> = ref([
  {
    title: '车间名称',
    align: 'center',
    key: 'workshopName'
  },
  {
    title: '车间编码',
    align: 'center',
    key: 'workshopCode'
  },
  {
    title: '面积',
    align: 'center',
    key: 'area'
  },
  {
    title: '负责人',
    align: 'center',
    key: 'head'
  },
  {
    title: '是否启用',
    align: 'center',
    key: 'ifEnable',
    render(row) {
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
    align: 'center',
    key: 'action',
    width: 180,
    render(row: any) {
      return (
        <div>
          <n-button
            type="primary"
            size="small"
            class="mr-2"
            onClick={() => {
              edit(row);
            }}
          >
            <icon-tdesign-edit class="mr-2" />
            编辑
          </n-button>
          <n-button
            type="error"
            size="small"
            onClick={() => {
              dialog.warning({
                title: '警告',
                content: '此操作将删除该车间以及绑定的信息，确定删除吗？',
                style: { borderRadius: '10px' },
                maskClosable: false,
                closable: false,
                positiveText: '确定',
                negativeText: '取消',
                onPositiveClick: () => {
                  deleteWorkshop(row.id).then(res => {
                    if (res.code === 200) {
                      getList();
                      message.success('删除成功');
                    }
                  });
                },
                onNegativeClick: () => {
                  message.info('取消删除');
                }
              });

              console.log(row);
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
onMounted(() => {
  getList();
});
</script>

<style lang="scss" scoped></style>
