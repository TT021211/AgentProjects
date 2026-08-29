<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="设备编码">
          <n-input v-model:value="searchForm.equipmentCode" placeholder="请输入设备编码"></n-input>
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
    <my-card :title="MyData.GetData().groupName">
      <template #right>
        <div>
          <n-button size="small" type="success" class="mr-15px" @click="goBack">
            <icon-tdesign-rollback class="mr-4px text-16px" />
            返回
          </n-button>
          <component
            :is="
              useAddBtn(
                () => {
                  dialogFlag = true;
                  flag = true;
                  addInit();
                },
                'small',
                '分配设备'
              )
            "
            style="margin-right: 15px"
          ></component>
          <n-button :disabled="!checked.length" type="error" size="small" class="mr-5px" @click="deleteList">
            <icon-tdesign-delete class="mr-3px text-15px" />
            批量删除
          </n-button>
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
    <my-dialog title="设备列表" :show="dialogFlag" width="1200px" @submit="handleSubmit" @cancel="handleCancel">
      <template #content>
        <div style="width: 100%; display: flex; flex-direction: column">
          <n-form :model="addSearchForm" inline label-placement="top">
            <n-form-item label="设备编码">
              <n-input v-model:value="addSearchForm.equipmentCode" placeholder="请输入设备编码"></n-input>
            </n-form-item>
            <n-form-item label="设备类型">
              <n-input v-model:value="addSearchForm.workbenchType" placeholder="请输入设备类型"></n-input>
            </n-form-item>
            <n-form-item>
              <component
                :is="
                  useSearchBtn(
                    () => {
                      addHandleSearch();
                    },
                    () => {
                      addHandleReset();
                    }
                  )
                "
              ></component>
            </n-form-item>
          </n-form>

          <n-data-table
            :loading="loading"
            :columns="itemColumns"
            :row-key="addRowKey"
            :data="itemList"
            @update-checked-row-keys="addHandleCheck"
          ></n-data-table>
          <my-pagination v-model:search-form="addSearchForm" @init="addInit"></my-pagination>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import type { DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import {
  getItemGroupDetailList,
  deleteItemGroupDetail,
  addItemGroupDetailList
} from '@/service/api/device/groupEquipment';
import { getEquipmentList } from '@/service/api/device/equipmentLedger';
import { queryEquipmentTypeAll } from '@/service/api/device/facilityCategorySet';
import { useAddBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading, useWarning } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  groupId: string | null;
  equipmentCode: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
type addSearchFormType = {
  equipmentCode: string;
  workbenchType: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
const route = useRoute();
const router = useRouter();
const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const itemList = ref<itemDataType[]>([]);
const dialog = useWarning();
const searchForm = ref<searchType>({
  groupId: route.query.id as string,
  equipmentCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const addSearchForm = ref<addSearchFormType>({
  equipmentCode: '',
  workbenchType: '',
  pageNum: 1,
  pageSize: 5,
  total: 0
});
type addFormType = {
  itemName: string;
  typeId: string | null;
  typeName: string | null;
  itemContent: string;
  ifEnable: number;
  distributionPath: string;
  completionStandard: string;
  remark: string;
};
const addForm = ref<addFormType>({
  itemName: '',
  typeId: null,
  typeName: null,
  itemContent: '',
  ifEnable: 0,
  distributionPath: '',
  completionStandard: '',
  remark: ''
});
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function addHandleSearch() {
  addSearchForm.value.pageNum = 1;
  addInit();
}
function handleReset() {
  searchForm.value = {
    groupId: route.query.id as string,
    equipmentCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function addHandleReset() {
  addSearchForm.value = {
    equipmentCode: '',
    workbenchType: '',
    pageNum: 1,
    pageSize: 5,
    total: 0
  };
  addInit();
}
function goBack() {
  router.go(-1);
}
function getData() {
  const preData = JSON.parse(route.query.groupForm as string);
  return {
    AddVersion: () => {
      preData.version += 1;
    },
    GetData: () => {
      return preData;
    },
    SetTotalMembers: value => {
      preData.totalItem = value;
    }
  };
}
const MyData = getData();
type DataType = {
  id: number;
  itemName: string;
  typeName: string;
  itemContent: string;
  distributionPath: string;
  completionStandard: string;
  ifEnable: string;
  remark: string;
};
type itemDataType = {
  equipmentName: string;
  equipmentCode: string;
  workbenchType: string;
  equipmentModel: string;
  position: string;
  remark: string;
};
const typeOptions = ref<any[]>([]);
const rowKey = (row: any) => row.id;
const addRowKey = (row: any) => row.id;
const itemColumns: Ref<DataTableColumns<itemDataType>> = ref([
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
    title: '设备编码',
    align: 'center',
    key: 'equipmentCode'
  },
  {
    title: '设备名称',
    align: 'center',
    key: 'equipmentName'
  },
  {
    title: '设备类型',
    align: 'center',
    key: 'workbenchType'
  },
  {
    title: '规格型号',
    align: 'center',
    key: 'equipmentModel'
  },
  {
    title: '位置',
    align: 'center',
    key: 'position'
  },
  {
    title: '备注',
    align: 'center',
    key: 'remark'
  }
]);
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
    title: '设备编码',
    align: 'center',
    key: 'equipmentCode'
  },
  {
    title: '设备名称',
    align: 'center',
    key: 'equipmentName'
  },
  {
    title: '设备类型',
    align: 'center',
    key: 'typeName'
  },
  {
    title: '规格型号',
    align: 'center',
    key: 'equipmentModel'
  },
  {
    title: '位置',
    align: 'center',
    key: 'position'
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
        useDelBtn(() => {
          deleteItemGroupDetail(row.id).then(res => {
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
const addChecked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};
const addHandleCheck = (rowKeys: (string | number)[]) => {
  addChecked.value = rowKeys;
};
// 弹窗
const dialogFlag = ref<boolean>(false);
const handleCancel = () => {
  dialogFlag.value = false;
  addForm.value = {
    itemName: '',
    typeId: null,
    typeName: null,
    itemContent: '',
    ifEnable: 0,
    distributionPath: '',
    completionStandard: '',
    remark: ''
  };
};
async function handleSubmit() {
  MyData.SetTotalMembers(searchForm.value.total);
  let ids = '/';
  addChecked.value.forEach(item => {
    ids += `${item},`;
  });
  ids = ids.substring(0, ids.length - 1);
  addItemGroupDetailList(ids, MyData.GetData()).then((res: any) => {
    if (res.code === 200) {
      message.success('添加成功');
      handleCancel();
      init();
    } else {
      message.error('添加失败');
    }
  });
}
function deleteList() {
  const deleteData: any[] = [];
  checked.value.forEach(item => {
    deleteData.push(item);
  });
  dialog.warn(() => {
    deleteItemGroupDetail(deleteData).then(res => {
      if (res.code === 200) {
        message.success('删除成功');
        init();
      }
    });
  });
}
function init() {
  startLoading();
  data.value = [];
  getItemGroupDetailList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
function addInit() {
  startLoading();
  itemList.value = [];
  getEquipmentList(addSearchForm.value).then(res => {
    if (res.code === 200) {
      itemList.value = res.rows;
      addSearchForm.value.total = res.total;
    }
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
