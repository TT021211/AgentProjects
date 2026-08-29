<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="设备编号">
          <n-input v-model:value="searchForm.equipmentCode" placeholder="请输入设备编号"></n-input>
        </n-form-item>
        <!-- <n-form-item label="称重机模板">
          <n-input v-model:value="searchForm.machineType" placeholder="请输入称重机模板"></n-input>
        </n-form-item> -->
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
    <my-card title="称重机生产设置列表">
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
        :max-height="400"
        :scroll-x="1800"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
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
            <n-form-item :span="1" path="printTemplateId" label="称重机打印模板">
              <n-select
                v-model:value="addForm.printTemplateId"
                class="w-180px"
                :options="machineTypeList"
                placeholder="称重机打印模板"
                @update:value="(val,item : any) => {
                  addForm.printTemplateName = item.label
                }"
              />
            </n-form-item>
            <n-form-item :span="1" path="machineType" label="数据解析值">
              <n-select
                v-model:value="addForm.machineType"
                class="w-180px"
                :options="dataAnalysisList"
                placeholder="请选择数据解析值"
              />
            </n-form-item>
            <n-form-item :span="1" path="machineBrand" label="称重机品牌">
              <n-input v-model:value="addForm.machineBrand" placeholder="请输入称重机品牌"></n-input>
            </n-form-item>
            <n-form-item label="备注" :span="15" path="attr1">
              <n-input v-model:value="addForm.attr1" type="textarea" placeholder="请输入备注" />
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
  getWeighMachineList,
  deleteWeighMachine,
  editWeighMachine,
  addWeighMachine,
  updateIsEnable
} from '@/service/api/md/workbench/weighMachine';
import { getWorkbenchTypeListAll } from '@/service/api/md/workbench/workbenchType';
import { getEquipmentAllNoRelevance, queryEquipmentById } from '@/service/api/device/equipmentLedger';
import { getPrintTemplateList } from '@/service/api/md/printTemplate';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  equipmentCode: string;
  machineType: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
const machineTypeList = ref<{ label: string; value: number }[]>([]);
const dataAnalysisList = ref<{ label: string; value: string }[]>([
  { label: '托利多', value: 'A' },
  { label: '三鑫', value: 'B' }
]);
const modelOptions = ref<any[]>([]);
const workbenchOptions = ref<any[]>([]);
const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  equipmentCode: '',
  machineType: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const rules = {
  equipmentId: [
    {
      required: true,
      message: '请选择关联设备'
    }
  ],
  printTemplateId: [
    {
      required: true,
      message: '请选择称重机打印模板'
    }
  ],
  modelId: [
    {
      required: true,
      message: '请选择机台型号'
    }
  ],
  machineTemplate: [
    {
      required: true,
      message: '请选择数据解析值'
    }
  ]
};
type addFormType = {
  equipmentId: string | null;
  modelId: string | null;
  modelName: string;
  equipmentCode: string;
  remark: string;
  machineBrand: string;
  machineType: string;
  machineTemplate: string;
  attr1: string;
  printTemplateId: number | null;
  printTemplateName: string;
};
const addForm = ref<addFormType>({
  equipmentId: null,
  modelId: null,
  modelName: '',
  equipmentCode: '',
  remark: '',
  machineBrand: '',
  machineType: '',
  machineTemplate: '',
  attr1: '',
  printTemplateId: null,
  printTemplateName: ''
});
const addFormRef = ref<FormInst | null>(null);
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
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
    machineType: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  workbenchType: string;
  modelName: string;
  equipmentCode: string;
  equipmentName: string;
  remark: string;
  ifEnable: string;
};

const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    width: 70,
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '机台类型',
    align: 'center',
    width: 100,
    key: 'workbenchType'
  },
  {
    title: '机台型号',
    align: 'center',
    width: 180,
    key: 'modelName'
  },
  {
    title: '设备名称',
    align: 'center',
    width: 160,
    key: 'machineName'
  },
  {
    title: '设备编号',
    align: 'center',
    width: 140,
    key: 'equipmentCode'
  },
  {
    title: '打印模板值',
    align: 'center',
    width: 160,
    key: 'printTemplateName'
  },

  {
    title: '是否启用',
    key: 'ifEnable',
    width: 100,
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
    title: '称重机品牌',
    align: 'center',
    width: 180,
    key: 'machineBrand',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '数据解析值',
    align: 'center',
    width: 120,
    key: 'machineType'
  },
  {
    title: '备注',
    align: 'center',
    key: 'attr1',
    width: 150,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '操作',
    align: 'center',
    key: 'actions',
    fixed: 'right',
    width: 160,
    render: row => {
      return [
        useEditBtn(() => {
          handleEdit(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteWeighMachine(row).then(res => {
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
    remark: '',
    machineBrand: '',
    machineType: '',
    machineTemplate: '',
    attr1: '',
    printTemplateId: null,
    printTemplateName: ''
  };
};
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addWeighMachine(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editWeighMachine(addForm.value).then((res: any) => {
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
  row.ifEnable = val;
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

function getList() {
  getPrintTemplateList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        machineTypeList.value.push({
          label: item.templateName,
          value: item.templateId
        });
      });
    }
  });
}

function init() {
  startLoading();
  data.value = [];
  getWeighMachineList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
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
  init();
  getList();
});
</script>

<style scoped></style>
