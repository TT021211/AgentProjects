<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline label-placement="top" :model="searchForm">
        <n-form-item label="客户名称" path="clientName">
          <n-input v-model:value="searchForm.userName" placeholder="请输入客户名称"></n-input>
        </n-form-item>
        <n-form-item label="产品名称" path="creditCode">
          <n-input v-model:value="searchForm.productName" placeholder="请输入产品名称"></n-input>
        </n-form-item>
        <n-form-item label="产品型号" path="creditCode">
          <n-input v-model:value="searchForm.productCode" placeholder="请输入产品型号"></n-input>
        </n-form-item>
        <n-form-item>
          <n-button type="primary" class="mr-10px" @click="handleSearch">
            <icon-ic-round-search class="mr-4px text-20px" />
            搜索
          </n-button>
          <n-button @click="handleReset">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="报表分析">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <!-- <n-button type="primary" size="small" class="ml-5px" @click="openDialog">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增报表
          </n-button> -->
        </div>
      </template>
      <n-data-table
        :max-height="400"
        :scroll-x="1800"
        :loading="loading"
        :data="data"
        :columns="columns"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="flag"
      width="1000px"
      :title="editFlag ? '编辑报表' : '新增报表'"
      @cancel="cancel"
      @submit="submit"
    >
      <template #content>
        <div style="width: 100%">
          <n-form ref="addFormRef" :model="addForm" :rules="rules" label-placement="left" label-width="130px">
            <n-grid :cols="3" :x-gap="10" :y-gap="10">
              <n-form-item-grid-item :span="1" label="销售日期" path="saleDate">
                <n-date-picker
                  v-model:formatted-value="addForm.saleDate"
                  placeholder="请选择销售日期"
                  value-format="yyyy-MM-dd"
                  class="w-160px"
                  type="date"
                  clearable
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="客户名称" path="userName">
                <n-input v-model:value="addForm.userName" placeholder="请输入供应商名称" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="产品名称" path="productName">
                <n-input v-model:value="addForm.productName" placeholder="请输入物资名称" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="产品型号">
                <n-input v-model:value="addForm.productCode" placeholder="请输入物资型号" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="单位" path="unit">
                <n-input v-model:value="addForm.unit" placeholder="请输入单位" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="合同数量" path="contractCount">
                <n-input v-model:value="addForm.contractCount" placeholder="请输入合同数量" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="单价" path="price">
                <n-input v-model:value="addForm.price" placeholder="请输入单价" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="到货时间" path="arrivedTime">
                <n-date-picker
                  v-model:formatted-value="addForm.arrivedTime"
                  placeholder="请选择到货日期"
                  value-format="yyyy-MM-dd"
                  class="w-160px"
                  type="date"
                  clearable
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="实收数量" path="actualCount">
                <n-input v-model:value="addForm.actualCount" placeholder="请输入实收数量" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="总价" path="totalPrice">
                <n-input v-model:value="addForm.totalPrice" placeholder="请输入总价" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="发票">
                <n-input v-model:value="addForm.invoice" placeholder="请输入发票" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="付款时间" path="paymentTime">
                <n-date-picker
                  v-model:formatted-value="addForm.paymentTime"
                  placeholder="请选择付款时间"
                  value-format="yyyy-MM-dd"
                  class="w-160px"
                  type="date"
                  clearable
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
import { ref, onMounted } from 'vue';
import { useMessage } from 'naive-ui';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useEditBtn, useDelBtn } from '@/hooks/common/useBtn';
import {
  getSaleReportList,
  addSaleReportList,
  updateSaleReportList,
  delSaleReportList
} from '~/src/service/api/sale/report';
import { useLoading, useBoolean } from '~/src/hooks';
import { createRequiredFormRule } from '~/src/utils';

const message = useMessage();
const { loading, startLoading, endLoading } = useLoading();
const addFormRef = ref<FormInst | null>(null);
// const dialog = useWarning();
const { bool: flag, setFalse: closeDialog, setTrue: openDialog } = useBoolean();
const editFlag = ref<boolean>(false);
const addForm = ref<reportManage.addForm>({
  saleDate: null,
  userName: '',
  productName: '',
  productCode: '',
  unit: '',
  contractCount: '',
  price: '',
  arrivedTime: null,
  actualCount: '',
  totalPrice: '',
  invoice: '',
  paymentTime: null
});

const rules = {
  saleDate: createRequiredFormRule('请输入销售日期'),
  userName: createRequiredFormRule('请输入客户名称'),
  productName: createRequiredFormRule('请输入客户名称'),
  unit: createRequiredFormRule('请输入单位'),
  contractCount: createRequiredFormRule('请输入合同数量'),
  price: createRequiredFormRule('请输入单价'),
  arrivedTime: createRequiredFormRule('请输入到货时间'),
  actualCount: createRequiredFormRule('请输入实收数量'),
  totalPrice: createRequiredFormRule('请输入总价'),
  paymentTime: createRequiredFormRule('请输入付款时间')
};
const searchForm = ref<reportManage.searchForm>({
  userName: '',
  productName: '',
  productCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const data = ref<reportManage.columns[]>([]);
const columns: Ref<DataTableColumns<reportManage.columns>> = ref([
  {
    title: '序号',
    key: 'index',
    titleAlign: 'center',
    align: 'center',
    render(_row, index) {
      return index + 1;
    },
    width: 60
  },
  {
    title: '销售日期',
    key: 'saleOrderDate',
    width: 200,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '客户名称',
    align: 'center',
    key: 'clientName',
    width: 120
  },
  {
    title: '产品名称',
    align: 'center',
    key: 'productName',
    width: 80
  },
  {
    title: '产品型号',
    align: 'center',
    key: 'productModel',
    width: 200,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '单位',
    align: 'center',
    width: 120,
    key: 'unit'
  },
  {
    title: '合同数量',
    align: 'center',
    width: 150,
    key: 'saleContractNumber'
  },
  {
    title: '单价',
    align: 'center',
    width: 150,
    key: 'price'
  },
  {
    title: '发货时间',
    align: 'center',
    width: 150,
    key: 'deliveryTime'
  },
  {
    title: '实收数量',
    align: 'center',
    width: 150,
    key: 'actualCount'
  },
  {
    title: '总价',
    align: 'center',
    width: 150,
    key: 'totalPrice'
  },
  {
    title: '发票',
    align: 'center',
    width: 150,
    key: 'invoice'
  },
  {
    title: '付款时间',
    align: 'center',
    width: 150,
    key: 'paymentTime'
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    width: 160,
    fixed: 'right',
    render: row => {
      return (
        <div>
          {useEditBtn(() => {
            editUser(row);
          }, 'tiny')}

          {useDelBtn(() => {
            deleteById(row);
          }, 'tiny')}
        </div>
      );
    }
  }
]);
function handleSearch() {
  init();
}
function handleReset() {
  searchForm.value = {
    userName: '',
    productName: '',
    productCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function cancel() {
  addForm.value = {
    saleDate: null,
    userName: '',
    productName: '',
    productCode: '',
    unit: '',
    contractCount: '',
    price: '',
    arrivedTime: null,
    actualCount: '',
    totalPrice: '',
    invoice: '',
    paymentTime: null
  };
  closeDialog();
}
function editUser(row) {
  addForm.value = { ...row };
  editFlag.value = true;
  openDialog();
}
function deleteById(row: any) {
  delSaleReportList(row.id).then(res => {
    if (res.code === 200) {
      init();
    }
  });
}
function submit() {
  addFormRef.value?.validate(errors => {
    if (!errors) {
      if (addForm.value.id) {
        updateSaleReportList(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('修改成功！');
            cancel();
            init();
          }
        });
      } else {
        addSaleReportList(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('新增成功！');
            cancel();
            init();
          }
        });
      }
    }
  });
}
function init() {
  startLoading();
  getSaleReportList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
});
</script>

<style scoped lang="scss"></style>
