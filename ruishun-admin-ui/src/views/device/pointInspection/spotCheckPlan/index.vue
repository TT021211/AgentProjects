<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="计划名称">
          <n-input v-model:value="searchForm.planName" placeholder="请输入配件编号"></n-input>
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
    <my-card title="点检计划列表">
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
    <my-dialog :title="flag ? '新增计划' : '编辑计划'" :show="dialogFlag" @cancel="handleCancel" @submit="handleSubmit">
      <template #content>
        <div>
          <n-form
            ref="addFormRef"
            label-placement="left"
            label-width="120px"
            label-align="left"
            :model="addForm"
            :rules="rules"
          >
            <n-form-item label="计划名称" :span="12" path="planName">
              <n-input v-model:value="addForm.planName" placeholder="请输入计划名称"></n-input>
            </n-form-item>
            <n-form-item label="点检项目类型" :span="12" path="itemTypeId">
              <n-select
                v-model:value="addForm.itemTypeId"
                class="w-180px"
                :options="typeOptions"
                placeholder="请选择点检项目类型"
                @update:value="typeHandleChange"
              />
            </n-form-item>
            <n-form-item label="开始时间" path="startTime">
              <n-date-picker v-model:value="addForm.startTime" type="date" placeholder="请选择开始日期" />
            </n-form-item>
            <n-form-item label="结束时间" :span="12" path="endTime">
              <n-date-picker v-model:value="addForm.endTime" type="date" placeholder="请选择结束日期" />
            </n-form-item>
            <n-form-item label="频率" :span="12" path="measureId">
              <n-select
                v-model:value="addForm.measureId"
                class="w-180px"
                :options="unitOptions"
                placeholder="请选择频率"
                @update:value="unitHandleChange"
              />
            </n-form-item>
            <n-form-item label="是否启用">
              <n-switch v-model:value="addForm.ifEnable" :checked-value="1" :unchecked-value="0" />
            </n-form-item>
            <n-form-item label="备注" :span="12" path="remark">
              <n-input v-model:value="addForm.remark" type="textarea" placeholder="请输入备注"></n-input>
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted, h } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage, NTag } from 'naive-ui';
import { getEqPlanList, deleteEqPlan, editEqPlan, addEqPlan } from '@/service/api/device/eqPlan';
import { queryItemTypeAll } from '@/service/api/device/classifySet';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  planType: string;
  planName: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
type searchItemType = {
  type: string;
};

const unitOptions = [
  {
    label: `天`,
    value: 0
  },
  {
    label: `周`,
    value: 1
  },
  {
    label: `月`,
    value: 2
  },
  {
    label: `季`,
    value: 3
  },
  {
    label: `年`,
    value: 4
  }
];

function changeType(str: number) {
  if (str === 0) {
    return 'success';
  } else if (str === 1) {
    return 'warning';
  } else if (str === 2) {
    return 'info';
  } else if (str === 3) {
    return 'success';
  } else if (str === 4) {
    return 'warning';
  }
  return 'error';
}
function getStatus(status: number) {
  if (status === 0) return '每天一次';
  if (status === 1) return '每周一次';
  if (status === 2) return '每月一次';
  if (status === 3) return '每季一次';
  if (status === 4) return '每年一次';
  return '';
}
const rules = {
  planName: [
    {
      required: true,
      message: '请输入点检名称'
    }
  ],
  itemTypeId: [
    {
      required: true,
      message: '请选择点检保养类型'
    }
  ],
  startTime: [
    {
      required: true,
      message: '请选择开始时间'
    }
  ],
  endTime: [
    {
      required: true,
      message: '请选择结束时间'
    }
  ],
  measureId: [
    {
      required: true,
      message: '请选择频率'
    }
  ]
};
const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchItemForm = ref<searchItemType>({
  type: '0'
});
const searchForm = ref<searchType>({
  planName: '',
  planType: '0',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
type addFormType = {
  planType: string;
  planName: string;
  itemTypeId: string | null;
  itemTypeName: string;
  startTime: number | null;
  endTime: number | null;
  measureId: string | null;
  measureName: string;
  ifEnable: string;
  remark: string;
};
const addForm = ref<addFormType>({
  planType: '0',
  planName: '',
  itemTypeId: null,
  itemTypeName: '',
  startTime: null,
  endTime: null,
  measureId: null,
  measureName: '',
  ifEnable: '',
  remark: ''
});
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    planType: '0',
    planName: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id: number;
  planCode: string;
  planName: string;
  planType: string;
  itemTypeId: string;
  itemTypeName: string;
  startTime: number;
  endTime: number;
  measureId: number;
  measureName: string;
  ifEnable: string;
  remark: string;
};
const typeOptions = ref<any[]>([]);
const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '计划编码',
    align: 'center',
    key: 'planCode'
  },
  {
    title: '计划名称',
    align: 'center',
    key: 'planName'
  },
  {
    title: '类型名称',
    align: 'center',
    key: 'itemTypeName'
  },
  {
    title: '开始时间',
    align: 'center',
    key: 'startTime'
  },
  {
    title: '结束时间',
    align: 'center',
    key: 'endTime'
  },
  {
    title: '频率',
    align: 'center',
    key: 'measureName',
    render: (row: DataType) => {
      return h(
        NTag,
        {
          type: changeType(row.measureId as number)
        },
        () => getStatus(row.measureId as number)
      );
    }
  },
  {
    title: '是否启用',
    key: 'ifEnable',
    align: 'center',
    render: row => {
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
    }
  },
  {
    title: '备注',
    align: 'center',
    key: 'remark'
  },
  {
    title: '操作',
    align: 'center',
    key: 'actions',
    render: row => {
      return [
        useEditBtn(() => {
          handleEdit(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteEqPlan(row).then(res => {
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
const addFormRef = ref<FormInst | null>(null);
const checked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};
function unitHandleChange(val, item) {
  addForm.value.measureId = val;
  addForm.value.measureName = item.label;
}

// 弹窗
const dialogFlag = ref<boolean>(false);
const handleCancel = () => {
  dialogFlag.value = false;
  addForm.value = {
    planType: '0',
    planName: '',
    itemTypeId: null,
    itemTypeName: '',
    startTime: null,
    endTime: null,
    measureId: null,
    measureName: '',
    ifEnable: '',
    remark: ''
  };
};
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addEqPlan(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editEqPlan(addForm.value).then((res: any) => {
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
  addForm.value = {
    ...row,
    startTime: new Date(row.startTime as unknown as string) as unknown as number,
    endTime: new Date(row.endTime as unknown as string) as unknown as number
  };
}
async function changeStatus(row: any, val: number) {
  row.ifEnable = val;
  await editEqPlan(row).then(res => {
    if (res.code === 200) {
      message.success(res.msg);
      handleCancel();
    }
  });
  init();
}
function typeHandleChange(val, item) {
  addForm.value.itemTypeId = val;
  addForm.value.itemTypeName = item.label;
}

function init() {
  startLoading();
  data.value = [];
  getEqPlanList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  queryItemTypeAll(searchItemForm.value).then(res => {
    if (res.code === 200) {
      typeOptions.value = [];
      res.data.forEach((item: any) => {
        typeOptions.value.push({
          label: item.itemTypeName,
          value: item.id
        });
      });
    }
  });
  init();
});
</script>

<style scoped></style>
