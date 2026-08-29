<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="故障名称">
          <n-input v-model:value="searchForm.breakdownName" placeholder="请输入故障名称"></n-input>
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
    <my-card title="故障类型列表">
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
    <my-dialog
      :title="flag ? '新增故障类型' : '编辑故障类型'"
      :show="dialogFlag"
      @cancel="handleCancel"
      @submit="handleSubmit"
    >
      <template #content>
        <div>
          <n-form label-placement="left" label-width="70px" label-align="left" :model="addForm">
            <n-form-item label="故障名称" :span="12" path="breakdownName">
              <n-input v-model:value="addForm.breakdownName" placeholder="请输入故障名称"></n-input>
            </n-form-item>
            <n-form-item label="适配类型" :span="12" path="typeIdList">
              <n-select
                v-model:value="addForm.typeIdList"
                class="w-180px"
                multiple
                :options="typeOptions"
                placeholder="请选择设备类型"
                filterable
              />
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
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import {
  getBreakdownTypeList,
  deleteBreakdownType,
  editBreakdownType,
  addBreakdownType
} from '@/service/api/device/facilityFaultCategory';
import { queryEquipmentTypeAll } from '@/service/api/device/facilityCategorySet';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  breakdownName: string;
  pageNum: number;
  pageSize: number;
  total: number;
};

const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  breakdownName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
type addFormType = {
  breakdownName: string;
  typeId: string | null;
  typeName: string;
  typeIdList: number[] | null;
  remark: string;
};
const addForm = ref<addFormType>({
  breakdownName: '',
  typeId: null,
  typeName: '',
  typeIdList: null,
  remark: ''
});
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    breakdownName: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  breakdownName: string;
  typeName: string;
  remark: string;
};

const rowKey = (row: any) => row.id;
type typeOptionsType = {
  label: string;
  value: number;
};
const typeOptions = ref<typeOptionsType[]>([]);
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
    title: '故障名称',
    align: 'center',
    key: 'breakdownName'
  },
  {
    title: '适配设备类型',
    align: 'center',
    width: 200,
    ellipsis: {
      tooltip: true
    },
    key: 'typeName'
  },
  {
    title: '备注',
    align: 'center',
    width: 200,
    ellipsis: {
      tooltip: true
    },
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
          deleteBreakdownType(row).then(res => {
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
    breakdownName: '',
    typeId: null,
    typeName: '',
    typeIdList: null,
    remark: ''
  };
};
const handleSubmit = () => {
  if (addForm.value.typeIdList !== null) {
    addForm.value.typeName = '';
    addForm.value.typeId = '';
    addForm.value.typeIdList.forEach(item => {
      const index = typeOptions.value.findIndex(ele => ele.value === item);
      addForm.value.typeName += `${typeOptions.value[index].label},`;
      addForm.value.typeId += `${item},`;
    });
    addForm.value.typeName = addForm.value.typeName.slice(0, -1);
    addForm.value.typeId = addForm.value.typeId.slice(0, -1);
  }
  if (flag.value) {
    addBreakdownType(addForm.value).then((res: any) => {
      if (res.code === 200) {
        message.success('添加成功');
        handleCancel();
        init();
      }
    });
  } else {
    editBreakdownType(addForm.value).then((res: any) => {
      if (res.code === 200) {
        message.success('修改成功');
        handleCancel();
        init();
      }
    });
  }
};
function handleEdit(row) {
  dialogFlag.value = true;
  flag.value = false;
  addForm.value = deepClone(row);
  if (addForm.value.typeId !== null) {
    addForm.value.typeIdList = addForm.value.typeId.split(',').map(Number);
  }
}
// 深度clone
function deepClone(obj: any) {
  return JSON.parse(JSON.stringify(obj));
}
function init() {
  startLoading();
  data.value = [];
  getBreakdownTypeList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  queryEquipmentTypeAll(null).then(res => {
    if (res.code === 200) {
      typeOptions.value = [];
      res.data.forEach((item: any) => {
        typeOptions.value.push({
          label: item.typeName,
          value: item.id
        });
      });
    }
  });
  init();
});
</script>

<style scoped></style>
