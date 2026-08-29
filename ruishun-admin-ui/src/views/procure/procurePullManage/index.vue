<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="类型：">
          <n-select
            v-model:value="searchForm.type"
            placeholder="请选择类型"
            class="w-180px"
            :options="typeList"
          ></n-select>
        </n-form-item>
        <n-form-item label="采购订单号">
          <n-input v-model:value="searchForm.purchasOrderCode" placeholder="请输入采购订单号" />
        </n-form-item>
        <n-form-item label="供应商">
          <n-input v-model:value="searchForm.returnSpecification" placeholder="请输入供应商" />
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
    <my-card title="采购记录">
      <template #right>
        <div class="flex-center">
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :data="data" :loading="loading" :columns="columns"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
// import { getMdReturnGoods } from '@/service/api/procure/procurePushManage/index';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
import { getProcureReceivingList } from '~/src/service/api/procure/pullManage';
const { loading, startLoading, endLoading } = useLoading();

type searchType = {
  type: string | null;
  purchasOrderCode: string;
  returnSpecification: string;
  pageNum: number;
  pageSize: number;
  total: number;
};

type typeListContent = {
  label: string;
  value: string;
};

const typeList = ref<Array<typeListContent>>([
  { label: '原料', value: '0' },
  { label: '辅料', value: '1' }
]);

const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  type: null,
  purchasOrderCode: '',
  returnSpecification: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function handleSearch() {
  init();
}

function handleReset() {
  searchForm.value = {
    type: null,
    purchasOrderCode: '',
    returnSpecification: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  type: string;
  receivingType: string;
  procureCode: string;
  procureWeight: string;
  procureNumber: string;
  attr1: string;
  procureQrcode: string;
  procureName: string;
  procureModel: string;
  procureSpec: string;
  procureClient: string;
  procureDate: string;
  createBy: string;
};

const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '类型',
    align: 'center',
    key: 'receivingType',
    width: 100,
    render: row => typeList.value.find(item => item.value === row.receivingType)?.label
  },
  {
    title: '采购合同编号',
    align: 'center',
    key: 'procureCode',
    width: 140
  },
  {
    title: '采购重量(kg)',
    align: 'center',
    key: 'procureWeight',
    width: 120
  },
  {
    title: '采购数量',
    align: 'center',
    key: 'procureNumber',
    width: 120
  },
  {
    title: '数量单位',
    align: 'center',
    key: 'attr1',
    width: 120
  },
  {
    title: '采购二维码',
    align: 'center',
    key: 'procureQrcode',
    width: 180
  },
  {
    title: '采购名称',
    align: 'center',
    key: 'procureName',
    width: 140
  },
  {
    title: '采购型号',
    align: 'center',
    key: 'procureModel',
    width: 140
  },
  {
    title: '采购规格',
    align: 'center',
    key: 'procureSpec',
    width: 100
  },
  {
    title: '采购供应商',
    align: 'center',
    key: 'procureClient',
    width: 100
  },
  {
    title: '采购时间',
    align: 'center',
    key: 'procureDate',
    width: 180
  },
  {
    title: '采购人',
    align: 'center',
    key: 'createBy',
    width: 100
  }
]);

function init() {
  startLoading();
  getProcureReceivingList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    } else {
      data.value = [];
    }
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped></style>
