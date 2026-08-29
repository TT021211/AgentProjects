<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="油漆名称">
          <n-input v-model:value="searchForm.plantName" placeholder="请输入油漆名称"></n-input>
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
    <my-card title="油漆清单">
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
    <my-dialog :title="flag ? '新增油漆' : '编辑油漆'" :show="dialogFlag" @cancel="handleCancel" @submit="handleSubmit">
      <template #content>
        <div>
          <n-form label-placement="left" label-width="70px" label-align="left" :model="addForm">
            <n-form-item label="油漆名称" :span="12" path="plantName">
              <n-input v-model:value="addForm.plantName" placeholder="请输入油漆名称"></n-input>
            </n-form-item>
            <n-form-item label="油漆型号" :span="11" path="plantTypeId">
              <n-select
                v-model:value="addForm.plantTypeId"
                :options="typeOptions"
                filterable
                placeholder="请选择油漆型号"
                @update:value="plantTypeHandleChange"
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
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { getPlantList, deletePlantList, editPlantList, addPlantList } from '@/service/api/md/plant/plantList';
import { getPlantTypeAll } from '@/service/api/md/plant/plantType';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  plantName: string;
  pageNum: number;
  pageSize: number;
  total: number;
};

const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  plantName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
type addFormType = {
  plantName: string;
  plantCode: string;
  plantTypeName: string;
  plantTypeId: string | null;
};
const addForm = ref<addFormType>({
  plantName: '',
  plantCode: '',
  plantTypeName: '',
  plantTypeId: null
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
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  plantName: string;
};

const rowKey = (row: any) => row.id;
const typeOptions = ref<{ value: string; label: string }[]>([]);
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
    title: '油漆编码',
    align: 'center',
    key: 'plantCode'
  },
  {
    title: '油漆名称',
    align: 'center',
    key: 'plantName'
  },
  {
    title: '油漆类型',
    align: 'center',
    key: 'plantTypeName'
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
          deletePlantList(row).then(res => {
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
    plantName: '',
    plantCode: '',
    plantTypeName: '',
    plantTypeId: null
  };
};
const handleSubmit = () => {
  if (flag.value) {
    addPlantList(addForm.value).then((res: any) => {
      if (res.code === 200) {
        message.success('添加成功');
        handleCancel();
        init();
      }
    });
  } else {
    editPlantList(addForm.value).then((res: any) => {
      if (res.code === 200) {
        message.success('修改成功');
        handleCancel();
        init();
      }
    });
  }
};
function plantTypeHandleChange(val, item) {
  addForm.value.plantTypeId = val;
  addForm.value.plantTypeName = item.label;
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
  getPlantList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  init();
  getPlantTypeAll(null).then(res => {
    if (res.code === 200) {
      typeOptions.value = [];
      res.data.forEach((item: any) => {
        typeOptions.value.push({
          label: item.type,
          value: item.id
        });
      });
    }
  });
});
</script>

<style scoped></style>
