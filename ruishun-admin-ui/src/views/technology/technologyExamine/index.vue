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
    <my-card title="工艺审核列表">
      <template #right>
        <CxColumns v-model:columns="columns" size="small" />
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
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { getMesProcessEnterList } from '@/service/api/technology/technologyEnter';
import { useSearchBtn, useEditBtn } from '~/src/hooks/common/useBtn';
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

const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  productModel: '',
  productSpecification: '',
  equipmentCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
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
        useEditBtn(
          () => {
            handleExamine(row);
          },
          'tiny',
          '审核'
        )
      ];
    }
  }
]);

const checked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};

function handleExamine(row: DataType) {
  // eslint-disable-next-line no-console
  console.log(row);
  message.success('审核成功');
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
  init();
});
</script>

<style scoped></style>
