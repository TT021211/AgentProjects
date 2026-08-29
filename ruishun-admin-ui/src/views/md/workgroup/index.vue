<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline :model="searchForm">
        <n-form-item label="所属工序">
          <n-select
            v-model:value="searchForm.workstationId"
            placeholder="请输入所属工序"
            style="width: 180px"
            :options="workstageOptions"
          />
        </n-form-item>
        <n-form-item label="班组名称">
          <n-input v-model:value="searchForm.workgroupName" placeholder="请输入班组名称"></n-input>
        </n-form-item>
        <n-form-item label="负责人">
          <n-input v-model:value="searchForm.headName" placeholder="请输入负责人"></n-input>
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
    <my-card title="班组列表">
      <template #right>
        <cx-columns v-model:columns="columns" size="medium"></cx-columns>
        <n-button class="ml-5px" type="primary" @click="openDialog">
          <icon-ic-round-plus class="mr-4px text-20px" />
          新增班组
        </n-button>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <!-- <n-pagination
        v-model:page="searchForm.pageNum"
        v-model:page-size="searchForm.pageSize"
        style="margin-top: 20px; float: right"
        :item-count="searchForm.total"
        @update:page="changePage"
      ></n-pagination> -->
      <my-pagination v-model:searchForm="searchForm" @init="handleSearch"></my-pagination>
    </my-card>
    <my-dialog :title="addForm.id ? '修改班组' : '新增班组'" :show="showDialog" @cancel="cancel" @submit="addWorkshop">
      <template #content>
        <div>
          <n-form ref="formRef" label-placement="left" label-width="100px" :model="addForm" :rules="rules">
            <n-form-item label="班组名称：" path="workshopName">
              <n-input v-model:value="addForm.workgroupName" placeholder="请输入班组名称" />
            </n-form-item>

            <n-form-item label="班组编码：" path="workgroupCode">
              <n-input v-model:value="addForm.workgroupCode" placeholder="请输入班组名称" />
            </n-form-item>
            <n-form-item label="所属车间：" path="workshopId">
              <n-select
                v-model:value="addForm.workshopId"
                filterable
                :options="workshopOptions"
                placeholder="请输入所属车间"
                @update-value="handelSelectWorkshop"
              />
            </n-form-item>
            <n-form-item label="所属工序：" path="workstageName">
              <n-select
                v-model:value="addForm.workstageId"
                filterable
                placeholder="请输入所属工序"
                :options="workstageOptions"
                @update-value="handelSelectWorkstage"
              />
            </n-form-item>
            <n-form-item label="班组长：" path="headName">
              <n-input v-model:value="addForm.headName" placeholder="请输入班组长" />
            </n-form-item>
            <n-form-item label="是否启用：" path="ifEnable">
              <n-select
                v-model:value="addForm.ifEnable"
                :options="[
                  { label: '启用', value: 1 },
                  { label: '禁用', value: 0 }
                ]"
              />
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
import { useRouter } from 'vue-router';
import { useMessage, useDialog } from 'naive-ui';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { getWorkgroupList, editWorkgroup, addWorkgroup, deleteWorkgroup } from '@/service/api/md/workgroup';
import { getAllWorkshopList } from '@/service/api/md/workshop';
import { getAllTechnologyList } from '@/service/api/technology/technologySetting';
import { createRequiredFormRule, deepClone } from '~/src/utils';
import { useLoading, useBoolean } from '~/src/hooks';
const workshopOptions = ref<any[]>([]);
const workstageOptions = ref<any[]>([]);
const router = useRouter();
// const group = ref<any[]>([]);
// const { bool: show, setTrue: open, setFalse: close } = useBoolean();
const dialog = useDialog();
const message = useMessage();
type addType = {
  id?: number | '';
  workstageId: number | null;
  workshopId: number | string | null;
  workgroupName: string;
  workshopName: string;
  workgroupCode: string;
  workstageName: string;
  headName: string;
  ifEnable: number | null;
};
const addForm = ref<addType>({
  id: '',
  workstageId: null,
  workshopId: null,
  workgroupCode: '',
  workgroupName: '',
  workshopName: '',
  workstageName: '',
  headName: '',
  ifEnable: null
});
const rules = {
  workgroupName: createRequiredFormRule('请输入班组名称'),

  workshopId: createRequiredFormRule('请选择所属车间'),
  workstageName: createRequiredFormRule('请输入所属工序'),
  headName: createRequiredFormRule('请输入班组长'),
  ifEnable: createRequiredFormRule('请选择是否启用')
};
const { bool: showDialog, setTrue: openDialog, setFalse: closeDialog } = useBoolean();
type searchType = {
  workstationId: number | null;
  headName: string;
  workgroupName: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchForm = ref<searchType>({
  workstationId: null,
  workgroupName: '',
  headName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function handleSearch() {
  console.log(searchForm.value);
  // searchForm.value.pageNum = 1;
  getList();
}
function handleResert() {
  searchForm.value = {
    workstationId: null,
    workgroupName: '',
    headName: '',
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
  workgroupName: string;
  workgroupCode: string;
  workshopName: string;
  workstageName: string;
  headName: string;
  ifEnable: number;
  createTime: string;
};
const { loading, startLoading, endLoading } = useLoading();
const data = ref<columnsType[]>([]);
function getList() {
  startLoading();
  getWorkgroupList(searchForm.value).then(res => {
    console.log(res);
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
function cancel() {
  addForm.value = {
    id: '',
    workstageId: null,
    workshopId: null,
    workgroupCode: '',
    workgroupName: '',
    workshopName: '',
    workstageName: '',
    headName: '',
    ifEnable: null
  };
  closeDialog();
}
function edit(row: any) {
  addForm.value = deepClone(row);
  openDialog();
}
const formRef = ref<FormInst | null>(null);
function addWorkshop() {
  formRef.value?.validate(errors => {
    if (!errors) {
      if (addForm.value.id) {
        editWorkgroup(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('编辑成功');
            getList();
          }
        });
      } else {
        addWorkgroup(addForm.value).then(res => {
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
type valType = string | number | boolean;
function changeStatus(row: any, val: valType) {
  row.ifEnable = val;
  editWorkgroup(row).then(res => {
    if (res.code === 200) {
      message.success('修改成功');
    }
  });
}
const columns: Ref<DataTableColumns<columnsType>> = ref([
  {
    title: '班组名称',
    align: 'center',
    key: 'workgroupName'
  },
  {
    title: '班组编码',
    align: 'center',
    key: 'workgroupCode',
    width: 100
  },
  {
    title: '所属车间',
    align: 'center',
    key: 'workshopName'
  },
  {
    title: '所属工序',
    align: 'center',
    key: 'workstageName'
  },
  {
    title: '班组长',
    align: 'center',
    key: 'headName',
    ellipsis: { tooltip: true }
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
        />
      );
      // return <n-tag type={row.ifEnable === 1 ? 'success' : 'error'}>{row.ifEnable === 1 ? '启用' : '禁用'}</n-tag>;
    }
  },
  {
    title: '创建时间',
    align: 'center',
    key: 'createTime',
    ellipsis: { tooltip: true }
  },
  {
    title: '成员',
    align: 'center',
    width: 130,
    key: 'man',
    render(row: any) {
      return (
        <div>
          <n-badge color="rgba(82, 196, 26,0.9)" v-model:value={row.totalMembers}>
            <n-button
              size="small"
              type="warning"
              onClick={() => {
                router.push({
                  name: 'md_workgrouporganization',
                  query: { id: row.id, groupForm: JSON.stringify(row) }
                });
              }}
            >
              <icon-tdesign-edit class="mr-2" />
              编辑成员
            </n-button>
          </n-badge>
        </div>
      );
    }
  },
  {
    title: '操作',
    align: 'center',
    key: 'action',
    width: 200,
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
                content: '此操作将删除该机组以及绑定的信息，确定删除吗？',
                style: { borderRadius: '10px' },
                maskClosable: false,
                closable: false,
                positiveText: '确定',
                negativeText: '取消',
                onPositiveClick: () => {
                  deleteWorkgroup(row.id).then(res => {
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
function handelSelectWorkshop(
  val,
  args: {
    workshopName: string;
  }
) {
  console.log(val, args);
  addForm.value.workshopName = args.workshopName;
}
function handelSelectWorkstage(
  val,
  args: {
    workstageName: string;
  }
) {
  console.log(val, args);
  addForm.value.workstageName = args.workstageName;
}
onMounted(() => {
  getList();
  getAllWorkshopList().then(res => {
    if (res.code === 200) {
      res.data.forEach((item: any) => {
        workshopOptions.value.push({
          label: item.workshopName,
          value: item.id,
          ...item
        });
      });
    }
  });
  getAllTechnologyList().then(res => {
    console.log(res);
    res.data.forEach(item => {
      workstageOptions.value.push({
        label: item.workstageName,
        value: item.id,
        ...item
      });
    });
  });
});
</script>

<style lang="scss" scoped>
:deep(.n-divider) {
  margin-top: 0px;
  margin-bottom: 0px;
}
</style>
