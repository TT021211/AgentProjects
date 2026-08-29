<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="设备编号">
          <n-input v-model:value="searchForm.equipmentCode" placeholder="请输入设备编号"></n-input>
        </n-form-item>
        <n-form-item label="生产规格">
          <n-input v-model:value="searchForm.params.specificationRange" placeholder="请输入规格"></n-input>
        </n-form-item>
        <n-form-item label="所属工序">
          <n-select
            v-model:value="searchForm.process"
            class="w-180px"
            :options="workstageOptions"
            placeholder="请选择工序名称"
            @update:value="
              (_value, option: any) => {
                searchForm.process = option.label;
              }
            "
          />
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
    <my-card title="拉丝机生产设置列表">
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
        :max-height="400"
        :scroll-x="1200"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog :title="flag ? '新增设置' : '编辑设置'" :show="dialogFlag" @cancel="handleCancel" @submit="handleSubmit">
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
            <n-form-item label="关联设备" :span="15" path="equipmentId">
              <n-select
                v-model:value="addForm.equipmentId"
                class="w-180px"
                :options="workbenchOptions"
                placeholder="请选择关联的设备"
                @update:value="workbenchHandleChange"
              />
            </n-form-item>
            <n-form-item label="机台型号" :span="15" path="modelId">
              <n-select
                v-model:value="addForm.modelId"
                class="w-180px"
                :options="modelOptions"
                placeholder="请选择关联的设备"
                @update:value="modelHandleChange"
              />
            </n-form-item>
            <n-form-item label="可生产下限" :span="15" path="productionLowerLimit">
              <n-input-number
                v-model:value="addForm.productionLowerLimit"
                placeholder="请输入可生产下限"
                step="0.01"
                min="0"
              />
            </n-form-item>
            <n-form-item label="可生产上限" :span="15" path="productionUpperLimit">
              <n-input-number
                v-model:value="addForm.productionUpperLimit"
                placeholder="请输入可生产上限"
                step="0.01"
                min="0"
              />
            </n-form-item>
            <n-form-item label="所属工序" :span="15" path="process">
              <n-select
                v-model:value="addForm.processId"
                class="w-180px"
                :options="workstageOptions"
                placeholder="请选择工序名称"
                @update:value="workstageHandleChange"
              />
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
  getWorkbenchWiredrawingList,
  deleteWorkbenchWiredrawing,
  editWorkbenchWiredrawing,
  addWorkbenchWiredrawing,
  updateIsEnable
} from '@/service/api/md/workbench/wiredrawing';
import { getWorkbenchTypeListAll } from '@/service/api/md/workbench/workbenchType';
import { getAllTechnologyList } from '@/service/api/technology/technologySetting/index';
import { getEquipmentAllNoRelevance, queryEquipmentById } from '@/service/api/device/equipmentLedger';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  equipmentCode: string;
  pageNum: number;
  pageSize: number;
  total: number;
  process: string | null;
  params: {
    specificationRange: string;
  };
};
const modelOptions = ref<any[]>([]);
const workbenchOptions = ref<any[]>([]);
const workstageOptions = ref<any[]>([]);
const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  equipmentCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0,
  process: null,
  params: {
    specificationRange: ''
  }
});
const rules = {
  equipmentId: [
    {
      required: true,
      message: '请选择关联设备'
    }
  ],
  modelId: [
    {
      required: true,
      message: '请选择机台型号'
    }
  ],
  productionLowerLimit: [
    {
      required: true,
      message: '请输入生产下限'
    }
  ],
  productionUpperLimit: [
    {
      required: true,
      message: '请输入生产上限'
    }
  ],
  process: [
    {
      required: true,
      message: '请输入所属工序'
    }
  ]
};
type addFormType = {
  equipmentId: string | null;
  modelId: string | null;
  modelName: string;
  equipmentCode: string;
  processId: string | null;
  process: string;
  productionLowerLimit: number | null;
  productionUpperLimit: number | null;
  remark: string;
};
const addForm = ref<addFormType>({
  equipmentId: null,
  modelId: null,
  modelName: '',
  equipmentCode: '',
  processId: null,
  process: '',
  productionLowerLimit: null,
  productionUpperLimit: null,
  remark: ''
});
const addFormRef = ref<FormInst | null>(null);
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function workstageHandleChange(val, item) {
  addForm.value.processId = val;
  addForm.value.process = item.label;
}
function modelHandleChange(val, item) {
  addForm.value.modelId = val;
  addForm.value.modelName = item.label;
}
function workbenchHandleChange(val, item) {
  addForm.value.equipmentId = val;
  addForm.value.equipmentCode = item.label;
}
function handleReset() {
  searchForm.value = {
    equipmentCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0,
    process: null,
    params: {
      specificationRange: ''
    }
  };
  init();
}
type DataType = {
  id?: number;
  workbenchType: string;
  modelName: string;
  equipmentCode: string;
  equipmentName: string;
  process: string;
  productionLowerLimit: number | null;
  productionUpperLimit: number | null;
  remark: string;
  isEnable: string;
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
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '机台类型',
    align: 'center',
    key: 'workbenchType'
  },
  {
    title: '机台型号',
    align: 'center',
    width: 120,
    key: 'modelName'
  },
  {
    title: '设备名称',
    align: 'center',
    width: 120,
    key: 'equipmentName'
  },
  {
    title: '设备编号',
    align: 'center',
    width: 120,
    key: 'equipmentCode'
  },
  {
    title: '是否启用',
    key: 'isEnable',
    align: 'center',
    render: row => {
      return (
        <my-switch
          v-model:value={row.isEnable}
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
    title: '生产规格下限',
    align: 'center',
    key: 'productionLowerLimit',
    width: 120
  },
  {
    title: '生产规格上限',
    align: 'center',
    key: 'productionUpperLimit',
    width: 120
  },
  {
    title: '所属工序',
    align: 'center',
    key: 'process'
  },
  {
    title: '备注',
    align: 'center',
    key: 'remark',
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
          deleteWorkbenchWiredrawing(row).then(res => {
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
    equipmentId: null,
    modelId: null,
    modelName: '',
    equipmentCode: '',
    processId: null,
    process: '',
    productionLowerLimit: null,
    productionUpperLimit: null,
    remark: ''
  };
};
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addWorkbenchWiredrawing(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
            getEquipmentAllNoRelevanceList();
          }
        });
      } else {
        editWorkbenchWiredrawing(addForm.value).then((res: any) => {
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
async function handleEdit(row) {
  dialogFlag.value = true;
  flag.value = false;
  await getEquipmentAllNoRelevance().then(res => {
    if (res.code === 200) {
      workbenchOptions.value = [];
      res.data.forEach((item: any) => {
        workbenchOptions.value.push({
          label: item.equipmentCode,
          value: item.id
        });
      });
    }
  });
  queryEquipmentById(row.equipmentId).then(res => {
    if (res.code === 200) {
      workbenchOptions.value.push({
        label: res.data.equipmentCode,
        value: res.data.id
      });
    }
  });
  addForm.value = deepClone(row);
}
async function changeStatus(row: any, val: number) {
  row.isEnable = val;
  await updateIsEnable(row).then(res => {
    if (res.code === 200) {
      message.success(res.msg);
      handleCancel();
    }
  });
  init();
}
// 深度clone
function deepClone(obj: any) {
  return JSON.parse(JSON.stringify(obj));
}
function init() {
  startLoading();
  data.value = [];
  getWorkbenchWiredrawingList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}

function getEquipmentAllNoRelevanceList() {
  getEquipmentAllNoRelevance().then(res => {
    if (res.code === 200) {
      workbenchOptions.value = [];
      res.data.forEach((item: any) => {
        workbenchOptions.value.push({
          label: item.equipmentCode,
          value: item.id
        });
      });
    }
  });
}

onMounted(() => {
  getAllTechnologyList().then(res => {
    if (res.code === 200) {
      workstageOptions.value = [];
      res.data.forEach((item: any) => {
        workstageOptions.value.push({
          label: item.workstageName,
          value: item.id
        });
      });
    }
  });
  getWorkbenchTypeListAll().then(res => {
    if (res.code === 200) {
      modelOptions.value = [];
      res.data.forEach((item: any) => {
        modelOptions.value.push({
          label: item.workbenchType,
          value: item.id
        });
      });
    }
  });
  getEquipmentAllNoRelevanceList();
  init();
});
</script>

<style scoped></style>
