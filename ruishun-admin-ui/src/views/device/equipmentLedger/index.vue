<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="设备编号">
          <n-input v-model:value="searchForm.equipmentCode" type="text" placeholder="请输入设备编号" />
        </n-form-item>
        <n-form-item label="设备类别">
          <n-select
            v-model:value="searchForm.workbenchId"
            class="w-180px"
            :options="workbenchOptions"
            placeholder="请选择设备类型"
            @update:value="typeHandleChange"
          />
        </n-form-item>
        <n-form-item label="设备状态">
          <n-select
            v-model:value="searchForm.status"
            placeholder="请选择设备状态"
            class="w-180px"
            :options="deviceStatusList"
          ></n-select>
        </n-form-item>
        <n-form-item>
          <component
            :is="
              useSearchBtn(
                () => {
                  search();
                },
                () => {
                  reset();
                }
              )
            "
          />
        </n-form-item>
      </n-form>
    </my-card>
    <my-card :title="title">
      <template #right>
        <div class="flex">
          <component
            :is="
              useAddBtn(() => {
                showDialog = true;
                flag = true;
              })
            "
            style="margin-right: 15px"
          ></component>
          <n-button :loading="exportLoading" size="small" type="warning" class="mr-5px ml-5px" @click="exportList">
            <icon-uil:export class="mr-4px text-20px" />
            导出
          </n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>

    <my-dialog
      :title="!flag ? '编辑设备' : '新增设备'"
      width="600px"
      :show="showDialog"
      @cancel="handleCancel"
      @submit="handleSubmit"
    >
      <template #content>
        <div>
          <n-form
            ref="addFormRef"
            :rules="rules"
            style="width: 550px"
            :model="addForm"
            label-placement="left"
            label-width="auto"
          >
            <n-grid :cols="2" :x-gap="20">
              <n-form-item-grid-item label="设备编号" path="equipmentCode">
                <n-input v-model:value="addForm.equipmentCode" placeholder="请输入设备编号" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="设备名称" path="equipmentName">
                <n-input v-model:value="addForm.equipmentName" placeholder="请输入设备名称" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="设备类型" path="workbenchId">
                <n-select
                  v-model:value="addForm.workbenchId"
                  class="w-180px"
                  :options="workbenchOptions"
                  placeholder="请选择设备类型"
                  @update:value="typeHandleChange"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="规格型号" path="equipmentModel">
                <n-input v-model:value="addForm.equipmentModel" placeholder="请输入规格型号" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="所在位置" path="position">
                <n-input v-model:value="addForm.position" placeholder="请输入所在位置" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="提供线径" path="putWireDiameter">
                <n-input v-model:value="addForm.putWireDiameter" placeholder="请输入提供线径" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="完成线径" path="outWireDiameter">
                <n-input v-model:value="addForm.outWireDiameter" placeholder="请输入完成线径" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="供应商" path="supplier">
                <n-input v-model:value="addForm.supplier" placeholder="请输入设备供应商" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="购买日期" path="buyTime">
                <n-date-picker v-model:value="addForm.buyTime" type="date" placeholder="请选择交付日期" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="备注">
                <n-input v-model:value="addForm.remark" type="textarea" placeholder="请输入输入备注" />
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
    <my-dialog v-model:show="showDetail" title="配件列表" width="1000px" @cancel="offDialog" @submit="offDialog">
      <template #content>
        <n-data-table :loading="loading" :columns="columnsTable" :data="detailData"></n-data-table>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'EquipmentLedger'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { DocumentText } from '@vicons/ionicons5';
import { download } from '@/service/request/helpers';
import { getAttachmentListByEquipmentId } from '@/service/api/device/facilityPartsSet';
import { queryEquipmentTypeAll } from '@/service/api/device/facilityCategorySet';
import { useSearchBtn, useAddBtn, useEditBtn, useDelBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import {
  getEquipmentList,
  addEquipment,
  editEquipment,
  deleteEquipment
} from '~/src/service/api/device/equipmentLedger';
const { loading, startLoading, endLoading } = useLoading();

const message = useMessage();
const title = ref<string>('设备台账列表');
type detailDataType = {
  attachmentName: string;
  attachmentCode: string;
  equipmentId: string | null;
  equipmentCode: string;
  distributionPath: string;
  usedTime: string;
  suppier: string;
};
const detailData = ref<detailDataType[]>([]);
const columnsTable: DataTableColumns<detailDataType> = [
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => index + 1
  },
  {
    title: '配件名称',
    align: 'center',
    key: 'attachmentName'
  },
  {
    title: '配件编号',
    align: 'center',
    key: 'attachmentCode'
  },
  {
    title: '适配设备',
    align: 'center',
    key: 'equipmentCode'
  },
  {
    title: '配件分类路径',
    align: 'center',
    key: 'distributionPath'
  },
  {
    title: '使用寿命',
    align: 'center',
    key: 'usedTime'
  },
  {
    title: '供应商',
    align: 'center',
    key: 'suppier'
  }
];
const deviceStatusList = [
  { label: '正常', value: 0 },
  { label: '停机', value: 1 }
];
const showDetail = ref<boolean>(false);
const workbenchOptions = ref<any[]>([]);
const flag = ref<boolean>(true);
type addFormType = {
  id: string | null;
  equipmentCode: string;
  equipmentName: string;
  workbenchId: string | null;
  workbenchType: string;
  equipmentModel: string;
  position: string;
  supplier: string;
  status: string | null;
  putWireDiameter: string;
  outWireDiameter: string;
  buyTime: number | null;
  remark: string;
};
const addForm = ref<addFormType>({
  id: null,
  equipmentCode: '',
  equipmentName: '',
  workbenchId: null,
  workbenchType: '',
  equipmentModel: '',
  position: '',
  supplier: '',
  status: null,
  putWireDiameter: '',
  outWireDiameter: '',
  buyTime: null,
  remark: ''
});
const exportLoading = ref<boolean>(false);
const rules = {
  workbenchId: [
    {
      required: true,
      message: '请选择设备类型'
    }
  ],
  equipmentCode: [
    {
      required: true,
      message: '请输入设备编码'
    }
  ],
  equipmentName: [
    {
      required: true,
      message: '请输入设备名称'
    }
  ]
};
const showDialog = ref<boolean>(false);

const searchForm = ref<equipmentLedger.searchForm>({
  pageNum: 1,
  pageSize: 10,
  equipmentCode: '',
  workbenchId: null,
  status: null,
  total: 0
});
function exportList() {
  exportLoading.value = true;
  download('eq/eqEquipment/export', searchForm.value, `设备台账.xlsx`, undefined)
    .then(() => {
      exportLoading.value = false;
    })
    .catch(() => {
      exportLoading.value = false;
    });
}
const data = ref<equipmentLedger.tabelList[]>([]);

const columns: Ref<DataTableColumns<equipmentLedger.tabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '设备编号',
    align: 'center',
    key: 'equipmentCode',
    width: 100
  },
  {
    title: '设备名称',
    align: 'center',
    width: 100,
    key: 'equipmentName'
  },
  {
    title: '设备类别',
    width: 100,
    align: 'center',
    key: 'workbenchType'
  },
  {
    title: '规格型号',
    align: 'center',
    width: 100,
    key: 'equipmentModel'
  },
  {
    title: '状态',
    align: 'center',
    width: 100,
    key: 'status',
    render: (row: any) => (
      <n-tag type={row.status === '0' ? 'success' : 'error'}>{row.status === '0' ? '正常' : '停机'}</n-tag>
    )
  },
  {
    title: '提供线径',
    align: 'center',
    width: 100,
    key: 'putWireDiameter'
  },

  {
    title: '完成线径',
    align: 'center',
    width: 100,
    key: 'outWireDiameter'
  },

  {
    title: '购买日期',
    align: 'center',
    width: 100,
    key: 'buyTime'
  },

  {
    title: '主要配件',
    align: 'center',
    width: 100,
    key: 'attachment',
    render(row) {
      return (
        <n-button size="tiny" style="opacity:0.7" color="#52C41A" class="mr-3px" onClick={() => getAttachmentList(row)}>
          <DocumentText class="mr-5px text-15px w-13px " />
          查看详情
        </n-button>
      );
    }
  },

  {
    title: '备注',
    align: 'center',
    width: 100,
    key: 'remark'
  },
  {
    title: '操作',
    align: 'center',
    width: 200,
    key: 'action',
    render: row => {
      return [
        useEditBtn(() => {
          handleEdit(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteEquipment(row).then(res => {
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

function search() {
  searchForm.value.pageNum = 1;
  init();
}

function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    equipmentCode: '',
    workbenchId: null,
    status: null
  };
  init();
}

function typeHandleChange(val, item) {
  addForm.value.workbenchId = val;
  addForm.value.workbenchType = item.label;
}
const addFormRef = ref<FormInst | null>(null);
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addEquipment(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editEquipment(addForm.value).then((res: any) => {
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
  showDialog.value = true;
  flag.value = false;
  addForm.value = {
    ...row,
    buyTime: new Date(row.buyTime as unknown as string) as unknown as null
  };
}

function offDialog() {
  showDetail.value = false;
}
function handleCancel() {
  showDialog.value = false;
  addForm.value = {
    id: null,
    equipmentCode: '',
    equipmentName: '',
    workbenchId: null,
    workbenchType: '',
    equipmentModel: '',
    position: '',
    supplier: '',
    status: null,
    putWireDiameter: '',
    outWireDiameter: '',
    buyTime: null,
    remark: ''
  };
}
function getAttachmentList(row) {
  showDetail.value = true;
  getAttachmentListByEquipmentId(row).then(res => {
    detailData.value = res.data;
  });
}
async function init() {
  startLoading();
  getEquipmentList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  queryEquipmentTypeAll(null).then(res => {
    if (res.code === 200) {
      workbenchOptions.value = [];
      res.data.forEach((item: any) => {
        workbenchOptions.value.push({
          label: item.typeName,
          value: item.id
        });
      });
    }
  });
  init();
});
</script>

<style scoped>
.active {
  background-color: lightgreen !important;
  color: #fff !important;
}

.flex {
  display: flex;
  align-items: center;
}
</style>
