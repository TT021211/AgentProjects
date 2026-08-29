<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="产品型号">
          <n-input v-model:value="searchForm.productModel" placeholder="请输入产品型号"></n-input>
        </n-form-item>
        <n-form-item label="产品规格">
          <n-input v-model:value="searchForm.productSpecification" placeholder="请输入产品规格"></n-input>
        </n-form-item>
        <n-form-item label="设备编号">
          <n-input v-model:value="searchForm.equipmentCode" placeholder="请输入设备编号"></n-input>
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
    <my-card title="工艺录入列表">
      <template #right>
        <div>
          <n-button class="mr-20px ml-20px" type="info" size="small" @click="uploadShow = true">
            <ArrowUpCircleOutline class="mr-2px text-25px w-20px" />
            导入标准
          </n-button>
          <n-button :loading="exportLoading" size="small" type="warning" class="mr-50px" @click="exportList">
            <ArrowDownCircleOutline class="mr-2px text-25px w-20px" />
            导出标准
          </n-button>
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
      :show="uploadShow"
      :title="'导入内控标准'"
      width="400px"
      @cancel="uploadShow = false"
      @submit="submitUpload"
    >
      <template #content>
        <div>
          <n-form ref="formModel" style="width: 600px" label-placement="left" label-align="left" :label-width="130">
            <n-form-item class="mr-5px ml-190px">
              <n-upload
                :show-file-list="true"
                :style="{ width: formItemWidth }"
                :default-upload="true"
                :accept="'.xls,.xlsx'"
                :directory-dnd="true"
                :max="5"
                @change="changeUpload"
              >
                <n-button :style="{ width: formItemWidth }">选择文件</n-button>
                <n-upload-dragger>
                  <div style="margin-bottom: 1px">
                    <n-icon size="48" :depth="3">
                      <ArchiveOutline />
                    </n-icon>
                  </div>
                  <n-text style="font-size: 16px">点击或者拖动文件到该区域来上传</n-text>
                  <n-p depth="3" style="margin: 8px 0 0 0">提示：仅允许导入“xls”或“xlsx”格式文件！</n-p>
                </n-upload-dragger>
              </n-upload>
            </n-form-item>
            <n-button class="ml-260px mt-10px" type="primary" dashed @click="importTemplate()">下载模板</n-button>
          </n-form>
        </div>
      </template>
    </my-dialog>
    <my-dialog
      :title="flag ? '新增工艺' : '编辑工艺'"
      :show="dialogFlag"
      width="1200px"
      @cancel="handleCancel"
      @submit="handleSubmit"
    >
      <template #content>
        <div>
          <n-form
            ref="addFormRef"
            label-placement="left"
            label-width="80px"
            label-align="left"
            :model="addForm"
            :rules="rules"
          >
            <n-grid :cols="24" :x-gap="20">
              <n-form-item-grid-item label="产品规格" :span="7" path="productSpecification">
                <n-input v-model:value="addForm.productSpecification" placeholder="请输入产品规格"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="产品型号" :span="7" path="productModel">
                <n-input v-model:value="addForm.productModel" placeholder="请输入产品型号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="产品名称" :span="7" path="productName">
                <n-input v-model:value="addForm.productName" placeholder="请输入产品名称"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="产品编号" :span="7" path="productCode">
                <n-input v-model:value="addForm.productCode" placeholder="请输入产品编号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="版次" :span="7" path="productEdition">
                <n-input v-model:value="addForm.productEdition" placeholder="请输入版次" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="设备" :span="7" path="equipmentId">
                <n-select
                  v-model:value="addForm.equipmentId"
                  class="w-240px"
                  :options="equipmentOptions"
                  placeholder="请选择关联的设备"
                  @update:value="equipmentHandleChange"
                />
              </n-form-item-grid-item>
            </n-grid>
            <n-divider />
            <n-grid :cols="24" :x-gap="20">
              <n-form-item-grid-item :span="50">软化控温±30(℃)</n-form-item-grid-item>
              <n-form-item-grid-item label="上层" :span="7" path="softenTemperatureUp" label-width="60px">
                <n-input v-model:value="addForm.softenTemperatureUp" placeholder="请输入软化控温上层"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="下层" :span="7" path="softenTemperatureDown" label-width="60px">
                <n-input v-model:value="addForm.softenTemperatureDown" placeholder="请输入软化控温下层"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="50">烘炉控温±30(℃)</n-form-item-grid-item>
              <n-form-item-grid-item label="进口" :span="7" path="ovenTemperatureEnter" label-width="60px">
                <n-input v-model:value="addForm.ovenTemperatureEnter" placeholder="请输入烘炉控温进口"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="炉中" :span="7" path="ovenTemperatureMiddle" label-width="60px">
                <n-input v-model:value="addForm.ovenTemperatureMiddle" placeholder="请输入烘炉控温炉中"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="出口" :span="7" path="ovenTemperatureOut" label-width="60px">
                <n-input v-model:value="addForm.ovenTemperatureOut" placeholder="请输入烘炉控温出口" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="50">催化后±30(℃)</n-form-item-grid-item>
              <n-form-item-grid-item label="第一次" :span="7" path="catalyzeFirst" label-width="70px">
                <n-input v-model:value="addForm.catalyzeFirst" placeholder="请输入催化后第一次"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="第二次" :span="7" path="catalyzeSecond" label-width="70px">
                <n-input v-model:value="addForm.catalyzeSecond" placeholder="请输入催化后第儿次"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="50" />
              <n-form-item-grid-item label="催化前控温(℃)" :span="7" path="catalyzeBefore" label-width="120px">
                <n-input v-model:value="addForm.catalyzeBefore" placeholder="请输入催化前控温" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="交换热风速度(rpm)" :span="7" path="changeWindSpeed" label-width="150px">
                <n-input v-model:value="addForm.changeWindSpeed" placeholder="请输入交换热风速度"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="循环风机(rpm)" :span="7" path="circulatingFanSpeed" label-width="120px">
                <n-input v-model:value="addForm.circulatingFanSpeed" placeholder="请输入循环风机速度"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="排废风机(rpm)" :span="7" path="exhaustFanSpeed" label-width="120px">
                <n-input v-model:value="addForm.exhaustFanSpeed" placeholder="请输入排废风机速度" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="线速(m/min)" :span="7" path="wireSpeed" label-width="150px">
                <n-input v-model:value="addForm.wireSpeed" placeholder="请输入线速"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="漆粘度4号杯±5秒" :span="7" path="viscosityCup" label-width="135px">
                <n-input v-model:value="addForm.viscosityCup" placeholder="请输入"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="收线轴型号" :span="7" path="putReelModel" label-width="120px">
                <n-input v-model:value="addForm.putReelModel" placeholder="请输入收线轴型号"></n-input>
              </n-form-item-grid-item>
            </n-grid>
            <n-divider />
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
import { ArrowUpCircleOutline, ArrowDownCircleOutline, ArchiveOutline } from '@vicons/ionicons5';
import {
  getMesProcessEnterList,
  deleteMesProcessEnter,
  addMesProcessEnter,
  editMesProcessEnter,
  uploadProcessEnter
} from '@/service/api/technology/technologyEnter';
import { selectAllWorkbenchEnamellingList } from '@/service/api/md/workbench/enamelling';
import { download } from '@/service/request/helpers';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  productModel: string;
  productSpecification: string;
  equipmentCode: string;
  pageNum: number;
  pageSize: number;
  total: number;
};

const formItemWidth = ref<string>('220px');
const uploadShow = ref<boolean>(false);
const exportLoading = ref<boolean>(false);
const uploadFormData = ref<any | null>(null);

const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  productModel: '',
  productSpecification: '',
  equipmentCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const rules = {
  productSpecification: [
    {
      required: true,
      message: '请输入产品规格'
    }
  ],
  productModel: [
    {
      required: true,
      message: '请输入产品型号'
    }
  ],
  productName: [
    {
      required: true,
      message: '请输入产品名称'
    }
  ],
  productCode: [
    {
      required: true,
      message: '请输入产品编号'
    }
  ],
  productEdition: [
    {
      required: true,
      message: '请输入版次'
    }
  ],
  equipmentCode: [
    {
      required: true,
      message: '请输入设备'
    }
  ],
  softenTemperatureUp: [
    {
      required: true,
      message: '请输入软化控温上层'
    }
  ],
  softenTemperatureDown: [
    {
      required: true,
      message: '请输入软化控温下层'
    }
  ],
  ovenTemperatureEnter: [
    {
      required: true,
      message: '请输入烘炉控温进口'
    }
  ],
  ovenTemperatureMiddle: [
    {
      required: true,
      message: '请输入烘炉控温炉中'
    }
  ],
  ovenTemperatureOut: [
    {
      required: true,
      message: '请输入烘炉控温出口'
    }
  ],
  catalyzeFirst: [
    {
      required: true,
      message: '请输入催化后第一次'
    }
  ],
  catalyzeSecond: [
    {
      required: true,
      message: '请输入催化后第二次'
    }
  ],
  catalyzeBefore: [
    {
      required: true,
      message: '请输入催化前控温'
    }
  ],
  changeWindSpeed: [
    {
      required: true,
      message: '请输入交换热风速度'
    }
  ],
  circulatingFanSpeed: [
    {
      required: true,
      message: '请输入循环风机速度'
    }
  ],
  exhaustFanSpeed: [
    {
      required: true,
      message: '请输入排废风机速度'
    }
  ],
  wireSpeed: [
    {
      required: true,
      message: '请输入线速'
    }
  ],
  viscosityCup: [
    {
      required: true,
      message: '请输入粘粘度4号杯'
    }
  ],
  putReelModel: [
    {
      required: true,
      message: '请输入收线轴型号'
    }
  ]
};
type addFormType = {
  productSpecification: string;
  productModel: string;
  productName: string;
  productCode: string;
  productEdition: string;
  equipmentId: string | null;
  equipmentCode: string;
  softenTemperatureUp: string;
  softenTemperatureDown: string;
  ovenTemperatureEnter: string;
  ovenTemperatureMiddle: string;
  ovenTemperatureOut: string;
  catalyzeFirst: string;
  catalyzeSecond: string;
  catalyzeBefore: string;
  changeWindSpeed: string;
  circulatingFanSpeed: string;
  exhaustFanSpeed: string;
  wireSpeed: string;
  viscosityCup: string;
  putReelModel: string;
};
const addForm = ref<addFormType>({
  productSpecification: '',
  productModel: '',
  productName: '',
  productCode: '',
  productEdition: '',
  equipmentId: null,
  equipmentCode: '',
  softenTemperatureUp: '',
  softenTemperatureDown: '',
  ovenTemperatureEnter: '',
  ovenTemperatureMiddle: '',
  ovenTemperatureOut: '',
  catalyzeFirst: '',
  catalyzeSecond: '',
  catalyzeBefore: '',
  changeWindSpeed: '',
  circulatingFanSpeed: '',
  exhaustFanSpeed: '',
  wireSpeed: '',
  viscosityCup: '',
  putReelModel: ''
});
const addFormRef = ref<FormInst | null>(null);
const equipmentOptions = ref<any[]>([]);
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    productModel: '',
    productSpecification: '',
    equipmentCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  productSpecification: string;
  productModel: string;
  productName: string;
  productCode: string;
  productEdition: string;
  equipmentCode: string;
};

function exportList() {
  exportLoading.value = true;
  download('mes/mesProcessEnter/export', null, `工艺数据.xlsx`, undefined)
    .then(() => {
      exportLoading.value = false;
    })
    .catch(() => {
      exportLoading.value = false;
    });
}
function importTemplate() {
  exportLoading.value = true;
  download('mes/mesProcessEnter/importTemplate', null, `工艺模板.xlsx`, undefined)
    .then(() => {
      exportLoading.value = false;
    })
    .catch(() => {
      exportLoading.value = false;
    });
}
function submitUpload() {
  if (uploadFormData.value === null) {
    message.warning('请选择文件进行上传');
    return;
  }
  uploadProcessEnter(uploadFormData.value, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(res => {
    if (res.code === 200) {
      message.success('上传成功');
      uploadShow.value = false;
      init();
    }
  });
}
function changeUpload(files) {
  if (files.fileList.length === 0) {
    uploadFormData.value = null;
    return;
  }
  uploadFormData.value = files.file;
}

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
    title: '产品型号',
    align: 'center',
    key: 'productModel'
  },
  {
    title: '产品规格',
    align: 'center',
    key: 'productSpecification'
  },
  {
    title: '产品名称',
    align: 'center',
    key: 'productName'
  },
  {
    title: '产品编号',
    align: 'center',
    key: 'productCode'
  },
  {
    title: '版次',
    align: 'center',
    key: 'productEdition'
  },
  {
    title: '设备',
    align: 'center',
    key: 'equipmentCode'
  },
  {
    title: '审批状态',
    align: 'center',
    key: 'approveStatus',
    render(row: any) {
      return (
        <n-tag type={row.approveStatus === '0' ? 'default' : 'success'}>
          {row.approveStatus === '0' ? '未批准' : '已批准'}
        </n-tag>
      );
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
          deleteMesProcessEnter(row).then(res => {
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

function equipmentHandleChange(val, item) {
  addForm.value.equipmentId = val;
  addForm.value.equipmentCode = item.label;
}

// 弹窗
const dialogFlag = ref<boolean>(false);
const handleCancel = () => {
  dialogFlag.value = false;
  addForm.value = {
    productSpecification: '',
    productModel: '',
    productName: '',
    productCode: '',
    productEdition: '',
    equipmentId: null,
    equipmentCode: '',
    softenTemperatureUp: '',
    softenTemperatureDown: '',
    ovenTemperatureEnter: '',
    ovenTemperatureMiddle: '',
    ovenTemperatureOut: '',
    catalyzeFirst: '',
    catalyzeSecond: '',
    catalyzeBefore: '',
    changeWindSpeed: '',
    circulatingFanSpeed: '',
    exhaustFanSpeed: '',
    wireSpeed: '',
    viscosityCup: '',
    putReelModel: ''
  };
};
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addMesProcessEnter(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editMesProcessEnter(addForm.value).then((res: any) => {
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
  getMesProcessEnterList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  selectAllWorkbenchEnamellingList(null).then(res => {
    if (res.code === 200) {
      equipmentOptions.value = [];
      res.data.forEach((item: any) => {
        equipmentOptions.value.push({
          label: item.equipmentCode,
          value: item.id
        });
      });
    }
  });
  init();
});
</script>

<style scoped></style>
