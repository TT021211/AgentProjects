<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline label-placement="top">
        <n-form-item label="仓库名称">
          <n-input v-model:value="searchForm.warehouseName" placeholder="请输入仓库名称" />
        </n-form-item>
        <n-form-item label="仓库编码">
          <n-input v-model:value="searchForm.warehouseCode" placeholder="请输入仓库编码" />
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
    <my-card title="仓库列表">
      <template #right>
        <div class="flex-center">
          <component
            :is="
              useAddBtn(() => {
                addWarehouse();
              })
            "
          ></component>
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :data="data"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
        :columns="columns"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="flag"
      :title="!formData.warehouseId ? '新增仓库' : '编辑仓库'"
      width="900px"
      @cancel="cancel"
      @submit="submit"
    >
      <template #content>
        <div>
          <n-form ref="formRef" :model="formData" :rules="rules" label-placement="left" label-width="100px">
            <n-grid :cols="2">
              <n-form-item-grid-item :span="1" label="仓库编码" path="warehouseCode">
                <n-input v-model:value="formData.warehouseCode" width="200px" placeholder="请输入仓库编码" />
                <n-switch v-model:value="value" @update:value="onChange">自动生成</n-switch>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="仓库名称" path="warehouseName">
                <n-input v-model:value="formData.warehouseName" placeholder="请输入仓库名称" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="负责人" path="charge">
                <n-input v-model:value="formData.charge" placeholder="请输入负责人" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="仓库类型" path="type">
                <n-select
                  v-model:value="formData.type"
                  filterable
                  placeholder="请选择仓库类型"
                  :options="typeOptions"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item v-show="formData.type === '1'" :span="1" label="关联工序" path="workstageId">
                <n-select
                  v-model:value="formData.workstageId"
                  filterable
                  placeholder="请选择关联工序"
                  :options="workstageOptions"
                  @update:value="changeWorkstage"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="是否启用：" path="ifEnable">
                <n-select
                  v-model:value="formData.enableFlag"
                  style="width: 200px"
                  :options="[
                    { label: '启用', value: '1' },
                    { label: '禁用', value: '0' }
                  ]"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="2" label="仓库备注" path="remark">
                <n-input
                  v-model:value="formData.remark"
                  :autosize="{
                    minRows: 2,
                    maxRows: 5
                  }"
                  type="textarea"
                  placeholder="请输入仓库描述"
                />
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted, getCurrentInstance } from 'vue';
import { useMessage } from 'naive-ui';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { dataTableConfig } from '@/config/dataTableConfig';
import { genCode } from '@/service/api/system/autoCodeDetail';
import { list, update, add, deleteInfo, getWorksstationIds } from '@/service/api/wms/warehouseSet';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
import { deepClone } from '~/src/utils';
import { getAllTechnologyList } from '~/src/service/api/technology/technologySetting';

const { proxy } = getCurrentInstance() as any;
const { wms_wmwarehouse_type } = proxy.useDict('wms_wmwarehouse_type');

const typeOptions = wms_wmwarehouse_type;

const message = useMessage();
const flag = ref<boolean>(false);
const { loading, startLoading, endLoading } = useLoading();
const data = ref<Tsetting.columns[]>([]);
const value = ref<boolean>(false);

const worksstationIds = ref<any[]>([]);
const machineOptions = ref<any[]>([]);
const machineOptionsOrigin = ref<any[]>([]);
const formData = ref<any>({
  workstageId: null,
  workstageName: '',
  warehouseName: '',
  warehouseCode: '',
  enableFlag: '1',
  charge: '',
  type: null,
  remark: ''
});
const formRef = ref<FormInst | null>(null);
const rules = {
  warehouseName: [{ required: true, message: '请输入仓库名称', trigger: 'blur' }],
  warehouseCode: [{ required: true, message: '请输入仓库编码', trigger: 'blur' }],
  type: [{ required: true, message: '请选择仓库类型', trigger: ['blur', 'change'] }],
  isEnable: [{ required: true, message: '请选择是否启用', trigger: 'blur' }]
};

const columns: Ref<DataTableColumns<Tsetting.columns>> = ref([
  {
    title: '仓库名称',
    align: 'center',
    key: 'warehouseName'
  },
  {
    title: '仓库编码',
    align: 'center',
    key: 'warehouseCode'
  },
  {
    title: '仓库类型',
    align: 'center',
    key: 'typeName',
    render: row => {
      return <dict-tag options={typeOptions.value} value={row.type}></dict-tag>;
    }
  },
  {
    title: '负责人',
    align: 'center',
    key: 'charge'
  },
  {
    title: '关联工序',
    align: 'center',
    key: 'workstageName'
  },
  {
    title: '是否启用',
    align: 'center',
    key: 'enableFlag',
    render: row => {
      return (
        <my-switch
          v-model:value={row.enableFlag}
          checked={'1'}
          unchecked={'0'}
          onChecked={val => {
            editStatus(row, val);
          }}
          onUnchecked={val => {
            editStatus(row, val);
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
    width: 200,
    render: row => {
      return [
        useEditBtn(() => {
          editTechnology(row);
        }),
        useDelBtn(() => {
          deleteTech(row);
        })
      ];
    }
  }
]);

const workstageOptions = ref<any[]>([]);

function changeWorkstage(val, item) {
  formData.value.workstageId = val;
  formData.value.workstageName = item.label;
}
function editTechnology(row: any) {
  machineOptions.value = [
    { label: row.workstationName, value: row.workstationId, workstationCode: row.workstationCode }
  ];
  const myoptions = machineOptionsOrigin.value.filter(item => !worksstationIds.value.includes(item.id));
  myoptions.forEach(item => {
    machineOptions.value.push({ label: item.workstationName, value: item.id, workstationCode: item.workstationCode });
  });
  flag.value = true;
  formData.value = deepClone(row);
}
function deleteTech(row: Tsetting.columns) {
  deleteInfo(row.warehouseId).then(res => {
    if (res.code === 200) {
      message.success('删除成功');
      init();
    } else {
      message.error(res.msg);
    }
  });
}
function editStatus(row: Tsetting.columns, val: number) {
  row.enableFlag = val;
  update(row).then(res => {
    if (res.code === 200) {
      message.success('修改成功');
    } else {
      message.error('修改失败');
    }
  });
}
const searchForm = ref<any>({
  warehouseName: '',
  warehouseCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

async function init() {
  // 初始化
  startLoading();
  await list(searchForm.value).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
  getWorksstationIds().then(res => {
    worksstationIds.value = res.data;
  });

  endLoading();
}
function addWarehouse() {
  // 过滤
  machineOptions.value = [];
  const myoptions = machineOptionsOrigin.value.filter(item => !worksstationIds.value.includes(item.id));
  myoptions.forEach(item => {
    machineOptions.value.push({ label: item.workstationName, value: item.id, workstationCode: item.workstationCode });
  });
  flag.value = true;
}
function cancel() {
  flag.value = false;
  formData.value = {
    workstageName: '',
    workstageCode: '',
    isEnable: 0,
    remark: ''
  };
}

function onChange() {
  if (value.value) {
    genCode('WAREHOUSE_CODE').then(response => {
      formData.value.warehouseCode = response.data;
    });
  } else {
    formData.value.warehouseCode = null;
  }
}
function handleSearch() {
  init();
}
function handleReset() {
  searchForm.value = {
    workstageName: '',
    workstageCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function submit() {
  formRef.value?.validate(error => {
    if (!error) {
      if (formData.value.warehouseId) {
        update(formData.value).then(res => {
          if (res.code === 200) {
            message.success('编辑成功');
            flag.value = false;
            init();
            cancel();
          } else {
            message.error('编辑失败');
          }
        });
      } else {
        add(formData.value).then(res => {
          if (res.code === 200) {
            message.success('新增成功');
            flag.value = false;
            init();
            cancel();
          } else {
            message.error('新增失败');
          }
        });
      }
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
  init();
});
</script>

<style lang="scss" scoped></style>
