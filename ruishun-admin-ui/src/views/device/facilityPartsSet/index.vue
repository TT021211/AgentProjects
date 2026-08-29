<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="配件编号">
          <n-input v-model:value="searchForm.attachmentCode" placeholder="请输入配件编号"></n-input>
        </n-form-item>
        <n-form-item label="配件编号">
          <n-input v-model:value="searchForm.equipmentCode" placeholder="请输入所属设备"></n-input>
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
    <my-card title="配件列表">
      <template #right>
        <div>
          <component
            :is="
              useAddBtn(() => {
                changeDevice = false;
                dialogFlag = true;
                flag = true;
              })
            "
          ></component>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
        :scroll-x="2000"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      :title="changeDevice ? '更换配件' : flag ? '新增配件' : '编辑配件'"
      :show="dialogFlag"
      width="700px"
      @submit="handleSubmit"
      @cancel="handleCancel"
    >
      <template #content>
        <div>
          <n-form
            ref="formRef"
            label-placement="left"
            :rules="rules"
            label-width="100px"
            label-align="left"
            :model="addForm"
          >
            <n-grid :cols="24" :x-gap="20">
              <n-form-item-grid-item label="配件名称" :span="12" path="attachmentName">
                <n-input v-model:value="addForm.attachmentName" placeholder="请输入配件名称"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="配件编码" :span="12" path="attachmentCode">
                <n-input v-model:value="addForm.attachmentCode" placeholder="请输入配件编码"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="配件分配路径" :span="12" path="distributionPath">
                <n-input v-model:value="addForm.distributionPath" placeholder="请输入配件分配路径"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="供应商" :span="12" path="supplier">
                <n-input v-model:value="addForm.supplier" placeholder="请输入供应商"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="配件规格" :span="12" path="specification">
                <n-input v-model:value="addForm.specification" placeholder="请输入配件规格"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="配件型号" :span="12" path="model">
                <n-input v-model:value="addForm.model" placeholder="请输入配件型号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="配件尺寸" :span="12" path="dimension">
                <n-input v-model:value="addForm.dimension" placeholder="请输入配件尺寸"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="功率" :span="12" path="power">
                <n-input v-model:value="addForm.power" placeholder="请输入功率"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="所属设备" :span="12" path="equipmentId">
                <n-select
                  v-model:value="addForm.equipmentId"
                  class="w-full"
                  :options="equipmentOptions"
                  placeholder="请选择关联设备"
                  @update:value="equipmentHandleChange"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="最大使用时间" :span="12" path="maxServiceTime">
                <n-input v-model:value="addForm.maxServiceTime" placeholder="请输入最大使用时间">
                  <template #suffix>
                    <div>天</div>
                  </template>
                </n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="最大生产重量" :span="12" path="maxServiceWeight">
                <n-input v-model:value="addForm.maxServiceWeight" placeholder="请输入最大生产重量"></n-input>
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
    <!-- <my-dialog title="更换设备" v-model:show="changeDialog">
			<template #content>
				<div>
					<n-form v-model="changeForm" label-placement="left" >
						<n-form-item label="更换实际按"></n-form-item>
					</n-form>
				</div>
			</template>
		</my-dialog> -->
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { useMessage } from 'naive-ui';
import dayjs from 'dayjs';
import {
  getAttachmentList,
  // deleteAttachment,
  editAttachment,
  addAttachment,
  updateAttachment
} from '@/service/api/device/facilityPartsSet';
import { getEquipmentAll } from '@/service/api/device/equipmentLedger';
import { useAddBtn, useEditBtn, useBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const changeDevice = ref<boolean>(false);
const message = useMessage();
// const changeDialog=ref<boolean>(false)
const rules = {
  attachmentName: [
    {
      required: true,
      message: '请输入配件名称',
      trigger: 'blur'
    }
  ],
  attachmentCode: [
    {
      required: true,
      message: '请输入配件编码',
      trigger: 'blur'
    }
  ],
  // equipmentId: [
  //   {
  //     required: true,
  //     message: '请选择关联设备',
  //     trigger: 'blur'
  //   }
  // ],
  distributionPath: [
    {
      required: true,
      message: '请输入配件分配路径',
      trigger: 'blur'
    }
  ],
  supplier: [
    {
      required: true,
      message: '请输入供应商',
      trigger: 'blur'
    }
  ]
};
const formRef = ref<FormInst | null>(null);
type searchType = {
  plantName: string;
  pageNum: number;
  pageSize: number;
  total: number;
  attachmentCode: string;
  equipmentCode: string;
};

const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  plantName: '',
  pageNum: 1,
  pageSize: 10,
  attachmentCode: '',
  equipmentCode: '',
  total: 0
});
type addFormType = {
  id?: string;
  attachmentName: string;
  attachmentCode: string;
  equipmentId: string | null;
  equipmentCode: string;
  distributionPath: string;
  usedTime: string;
  supplier: string;
  maxServiceTime: string | null;
  maxServiceWeight: string | null;
  specification: string;
  model: string;
  dimension: string;
  power: string;
};
const addForm = ref<addFormType>({
  attachmentName: '',
  attachmentCode: '',
  equipmentId: null,
  equipmentCode: '',
  distributionPath: '',
  usedTime: '',
  supplier: '',
  maxServiceTime: null,
  maxServiceWeight: null,
  model: '',
  dimension: '',
  power: '',
  specification: ''
});
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    plantName: '',
    pageNum: 1,
    pageSize: 10,
    attachmentCode: '',
    equipmentCode: '',
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  attachmentName: string;
  attachmentCode: string;
  equipmentCode: string;
  distributionPath: string;
  usedTime: string;
  supplier: string;
  replaceTime: number | string;
  status: string;
  maxServiceTime: string | number;
};
const equipmentOptions = ref<any[]>([]);
const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1,
    width: 100
  },
  {
    title: '配件名称',
    align: 'center',
    key: 'attachmentName',
    width: 100
  },
  {
    title: '配件编号',
    align: 'center',
    key: 'attachmentCode',
    width: 100
  },
  {
    title: '配件规格',
    align: 'center',
    key: 'specification',
    width: 100
  },
  {
    title: '配件型号',
    align: 'center',
    key: 'model',
    width: 100
  },
  {
    title: '配件尺寸',
    align: 'center',
    key: 'dimension',
    width: 100
  },
  {
    title: '配件功率',
    align: 'center',
    key: 'power',
    width: 100
  },
  {
    title: '已使用时间',
    key: 'usedTime',
    render: row => {
      return <div>{`${row.usedTime ? row.usedTime : '0'}天`}</div>;
    },
    width: 100
  },
  {
    title: '适配设备',
    align: 'center',
    key: 'equipmentCode',
    width: 100
  },
  {
    title: '配件状态',
    key: 'status',
    render: row => {
      return <n-tag type={row.status !== '预警' ? 'success' : 'error'}>{row.status}</n-tag>;
    },
    width: 100
  },
  {
    title: '已生产重量',
    key: 'producedWeight',
    width: 100
  },
  {
    title: '更换时间',
    key: 'replaceTime',
    render: row => {
      return row.replaceTime ? dayjs(row.replaceTime).format('YYYY-MM-DD hh:mm:ss') : '';
    },
    width: 100,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '配件分类路径',
    align: 'center',
    key: 'distributionPath',
    width: 100
  },
  {
    title: '最大生产重量',
    align: 'center',
    key: 'maxServiceWeight',
    width: 100
  },
  {
    title: '使用寿命',
    align: 'center',
    key: 'usedTime',
    width: 100
  },
  {
    title: '供应商',
    align: 'center',
    key: 'supplier',
    width: 100
  },
  {
    title: '最大使用时间',
    key: 'maxServiceTime',
    render: row => {
      return <div>{`${row.maxServiceTime}天`}</div>;
    },
    width: 100
  },
  {
    title: '操作',
    align: 'center',
    key: 'actions',
    width: 180,
    render: row => {
      return [
        useEditBtn(() => {
          changeDevice.value = false;
          handleEdit(row);
        }, 'tiny'),
        // useDelBtn(() => {
        //   deleteAttachment(row).then(res => {
        //     if (res.code === 200) {
        //       message.success('删除成功');
        //       init();
        //     }
        //   });
        // }, 'tiny')
        useBtn(
          () => {
            console.log(row);
            changeDevice.value = true;
            handleEdit(row);
            // editAttachment({ ...row, status: '已更换', replaceTime: new Date().getTime() }).then(res => {
            //   if (res.code === 200) {
            //     message.success('更换成功');
            //     init();
            //   }
            // });
          },
          'det',
          'tiny',
          '更换设备'
        )
      ];
    },
    fixed: 'right'
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
    attachmentName: '',
    attachmentCode: '',
    equipmentId: null,
    equipmentCode: '',
    distributionPath: '',
    usedTime: '',
    supplier: '',
    maxServiceTime: null,
    maxServiceWeight: null,
    model: '',
    dimension: '',
    power: '',
    specification: ''
  };
};
const handleSubmit = () => {
  formRef.value?.validate(err => {
    if (!err) {
      if (changeDevice.value) {
        const {
          attachmentName,
          attachmentCode,
          equipmentId,
          equipmentCode,
          distributionPath,
          usedTime,
          supplier,
          maxServiceTime,
          maxServiceWeight
        } = addForm.value;
        updateAttachment(
          {
            attachmentName,
            attachmentCode,
            equipmentId,
            equipmentCode,
            distributionPath,
            usedTime,
            supplier,
            maxServiceTime,
            maxServiceWeight
          },
          addForm.value.id as string
        ).then((res: any) => {
          if (res.code === 200) {
            message.success('更换成功');
            handleCancel();

            init();
          }
        });
      } else if (flag.value) {
        addAttachment(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editAttachment(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('修改成功');
            handleCancel();
            init();
          }
        });
      }
    }
  });
};
function handleEdit(row) {
  dialogFlag.value = true;
  flag.value = false;
  addForm.value = deepClone(row);
}
function equipmentHandleChange(val, item) {
  addForm.value.equipmentId = val;
  addForm.value.equipmentCode = item.label;
}
// 深度clone
function deepClone(obj: any) {
  return JSON.parse(JSON.stringify(obj));
}
function init() {
  startLoading();
  data.value = [];
  getAttachmentList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  getEquipmentAll().then(res => {
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
