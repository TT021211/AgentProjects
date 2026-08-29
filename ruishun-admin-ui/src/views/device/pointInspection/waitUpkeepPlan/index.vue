<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="设备编码">
          <n-input v-model:value="searchForm.equipmentCode" placeholder="请输入设备编码"></n-input>
        </n-form-item>
        <n-form-item label="负责人">
          <n-input v-model:value="searchForm.headName" placeholder="请输入负责人名称"></n-input>
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
    <my-card title="待保养列表">
      <template #right>
        <div>
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
    <my-dialog title="设备保养" :show="dialogFlag" height="200px" @cancel="handleCancel" @submit="handleSubmit">
      <template #content>
        <div>
          <n-form label-placement="left" label-width="100px" label-align="left" :model="addForm">
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
import { ConstructOutline } from '@vicons/ionicons5';
import { getEqListList, deleteEqList } from '@/service/api/device/waitPlan';
import { addEqListLog } from '@/service/api/device/eqRecord';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  planType: string;
  equipmentCode: string;
  headName: string;
  pageNum: number;
  pageSize: number;
  total: number;
};

const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  planType: '1',
  equipmentCode: '',
  headName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
type addFormType = {
  equipmentId: string | null;
  equipmentCode: string;
  equipmentName: string;
  equipmentModel: string;
  planId: string | null;
  planCode: string;
  planType: string;
  planName: string;
  itemTypeId: string | null;
  itemTypeName: string;
  workgroupId: string | null;
  workgroupName: string;
  headId: string | null;
  headName: string;
  remark: string;
};
const addForm = ref<addFormType>({
  equipmentId: null,
  equipmentCode: '',
  equipmentName: '',
  equipmentModel: '',
  planId: null,
  planCode: '',
  planType: '1',
  planName: '',
  itemTypeId: null,
  itemTypeName: '',
  workgroupId: null,
  workgroupName: '',
  headId: null,
  headName: '',
  remark: ''
});
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    planType: '1',
    equipmentCode: '',
    headName: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id: number;
  equipmentId: string | null;
  equipmentCode: string;
  equipmentName: string;
  equipmentModel: string;
  planId: string | null;
  planCode: string;
  planType: string;
  planName: string;
  itemTypeId: string | null;
  itemTypeName: string;
  workgroupId: string | null;
  workgroupName: string;
  headId: string | null;
  headName: string;
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
    key: 'equipmentModel'
  },
  {
    title: '计划名称',
    align: 'center',
    key: 'planName'
  },
  {
    title: '计划编码',
    align: 'center',
    key: 'planCode'
  },
  {
    title: '保养类型',
    align: 'center',
    key: 'itemTypeName'
  },
  {
    title: '保养时间',
    align: 'center',
    key: 'createTime'
  },
  {
    title: '保养组别',
    align: 'center',
    key: 'workgroupName'
  },
  {
    title: '负责人',
    align: 'center',
    key: 'headName'
  },
  {
    title: '操作',
    align: 'center',
    key: 'actions',
    render: row => {
      return [
        <n-button color="#8a2be2" size="tiny" class="mr" onClick={() => handleInfo(row)}>
          <ConstructOutline class="mr-1px text-15px w-13px" />
          保养
        </n-button>
      ];
    }
  }
]);
// 弹窗
const dialogFlag = ref<boolean>(false);
const checked = ref<(string | number)[]>([]);
const deleteRowId = ref<string | number>('');
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};
async function handleSubmit() {
  await addEqListLog(addForm.value).then((res: any) => {
    if (res.code === 200) {
      deleteEqList(deleteRowId.value).then((delRes: any) => {
        if (delRes.code === 200) {
          message.success('点检成功');
          init();
        }
      });
      dialogFlag.value = false;
    }
  });
}
const handleCancel = () => {
  dialogFlag.value = false;
  addForm.value = {
    equipmentId: null,
    equipmentCode: '',
    equipmentName: '',
    equipmentModel: '',
    planId: null,
    planCode: '',
    planType: '1',
    planName: '',
    itemTypeId: null,
    itemTypeName: '',
    workgroupId: null,
    workgroupName: '',
    headId: null,
    headName: '',
    remark: ''
  };
};
function handleInfo(row) {
  console.log(row);
  addForm.value = {
    equipmentId: row.equipmentId,
    equipmentCode: row.equipmentCode,
    equipmentName: row.equipmentName,
    equipmentModel: row.equipmentModel,
    planId: row.planId,
    planCode: row.planCode,
    planType: row.planType,
    planName: row.planName,
    itemTypeId: row.itemTypeId,
    itemTypeName: row.itemTypeName,
    workgroupId: row.workgroupId,
    workgroupName: row.workgroupName,
    headId: row.headId,
    headName: row.headName,
    remark: ''
  };
  deleteRowId.value = row.id;
  dialogFlag.value = true;
}
function init() {
  startLoading();
  data.value = [];
  getEqListList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  init();
});
</script>

<style scoped></style>
