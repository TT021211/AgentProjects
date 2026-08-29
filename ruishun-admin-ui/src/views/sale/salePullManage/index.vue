<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="类型">
          <n-select
            v-model:value="searchForm.type"
            placeholder="请选择类型"
            class="w-180px"
            :options="typeList"
          ></n-select>
        </n-form-item>
        <n-form-item label="销售订单号">
          <n-input v-model:value="searchForm.purchasOrderCode" placeholder="请输入销售订单号" />
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
    <my-card title="退货记录">
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
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
import { getSaleReturnList } from '~/src/service/api/sale/pullManage';
const { loading, startLoading, endLoading } = useLoading();
// 搜索条件
type searchType = {
  type: string | null;
  purchasOrderCode: string;
  returnSpecification: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
// 类型集合
type typeListContent = {
  label: string;
  value: string;
};
// 表格字段
type DataType = {
  type: string;
  purchasOrderCode: string;
  returnNumber: number;
  returnWeight: number;
  returnSnCode: string;
  returnName: string;
  returnModel: string;
  returnSpecification: string;
  returnSuppier: string;
  createTime: string;
  createBy: string;
  quantityUnit: string;
};

// 类型分类
const typeList = ref<Array<typeListContent>>([
  { label: '原料', value: '0' },
  { label: '辅料', value: '1' }
]);
// 表格数据
const data = ref<DataType[]>([]);
// 搜索条件
const searchForm = ref<searchType>({
  type: null,
  purchasOrderCode: '',
  returnSpecification: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
// 搜索方法
function handleSearch() {
  init();
}
// 重置
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
// 表格设置
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
    key: 'type',
    width: 100,
    render: row => typeList.value.find(item => item.value === row.type)?.label
  },
  {
    title: '销售编号',
    align: 'center',
    key: 'saleCode',
    width: 140
  },
  {
    title: '退货重量(kg)',
    align: 'center',
    key: 'returnWeight',
    width: 120
  },
  {
    title: '退货数量',
    align: 'center',
    key: 'returnNumber',
    width: 120
  },
  {
    title: '数量单位',
    align: 'center',
    key: 'measureName',
    width: 80
  },
  {
    title: '退货二维码',
    align: 'center',
    key: 'returnQrcode',
    width: 180
  },
  {
    title: '退货名称',
    align: 'center',
    key: 'returnName',
    width: 120
  },
  {
    title: '退货型号',
    align: 'center',
    key: 'returnModel',
    width: 120
  },
  {
    title: '退货规格',
    align: 'center',
    key: 'returnSpec',
    width: 80
  },
  {
    title: '退货供应商',
    align: 'center',
    key: 'returnClient',
    width: 100
  },
  {
    title: '退货时间',
    align: 'center',
    key: 'returnTime',
    width: 120
  },
  {
    title: '退货人',
    align: 'center',
    key: 'createBy',
    width: 100
  }
]);
// 获取表格数据
function init() {
  startLoading();
  getSaleReturnList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    } else {
      data.value = [];
    }
  });
}
// 挂载后函数
onMounted(() => {
  init();
});
</script>

<style scoped></style>
