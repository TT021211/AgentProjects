<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline label-placement="top" :model="searchForm">
        <n-form-item label="客户名称">
          <n-input v-model:value="searchForm.clientName" placeholder="请输入客户名称"></n-input>
        </n-form-item>
        <n-form-item label="合同编码">
          <n-input v-model:value="searchForm.saleContractCode" placeholder="请输入合同编码"></n-input>
        </n-form-item>
        <n-form-item label="规格">
          <n-input v-model:value="searchForm.productSpec" placeholder="请输入规格"></n-input>
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
    <my-card title="价格管理">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button type="primary" size="small" class="ml-5px" @click="openDialog">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增价格
          </n-button>
        </div>
      </template>
      <n-data-table :loading="loading" :data="data" :columns="columns"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="flag"
      width="1000px"
      :title="editFlag ? '编辑价格' : '新增价格'"
      @cancel="cancel"
      @submit="submit"
    >
      <template #content>
        <div style="width: 100%">
          <n-form ref="addFormRef" :model="addForm" :rules="rules" label-placement="left" label-width="130px">
            <n-grid :cols="3" :x-gap="10" :y-gap="10">
              <n-form-item-grid-item :span="1" label="客户名称" path="clientName">
                <n-input v-model:value="addForm.clientName" placeholder="请输入客户名称" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="合同编号" path="saleContractCode">
                <n-input v-model:value="addForm.saleContractCode" placeholder="请输入合同编号" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="合同日期" path="saleContractDate">
                <n-input v-model:value="addForm.saleContractDate" placeholder="请输入合同日期" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="产品名称" path="productName">
                <n-input v-model:value="addForm.productName" placeholder="请输入产品名称" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="规格" path="productSpec">
                <n-input v-model:value="addForm.productSpec" placeholder="请输入规格"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="合同重量" path="saleContractWeight">
                <n-input v-model:value="addForm.saleContractWeight" placeholder="请输入合同重量" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="电铜价" path="copperPrice">
                <n-input v-model:value="addForm.copperPrice" placeholder="请输入电铜价" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="加工费" path="processingFee">
                <n-input v-model:value="addForm.processingFee" placeholder="请输入加工费" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="单价" path="unitPrice">
                <n-input v-model:value="addForm.unitPrice" placeholder="请输入单价"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="发货日期" path="shipmentDate">
                <n-input v-model:value="addForm.shipmentDate" placeholder="请输入发货日期"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="送货重量" path="deliveryWeight">
                <n-input v-model:value="addForm.deliveryWeight" placeholder="请输入送货重量"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="总金额" path="totalAmount">
                <n-input v-model:value="addForm.totalAmount" placeholder="请输入总金额"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="贷款" path="loan">
                <n-input v-model:value="addForm.loan" placeholder="请输入贷款"></n-input>
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
import type { DataTableColumns, FormInst } from 'naive-ui';
import { useEditBtn, useDelBtn } from '@/hooks/common/useBtn';
import {
  getSalePriceList,
  addSalePriceList,
  updateSalePriceList,
  delSalePriceList
} from '~/src/service/api/sale/money';
import { useLoading, useBoolean } from '~/src/hooks';
import { createRequiredFormRule } from '~/src/utils';

const { loading, startLoading, endLoading } = useLoading();
const paymentMethodOptions = ref([
  { label: '款到发货', value: 1 },
  { label: '货到付款', value: 0 }
]);
function getPaymentMethod() {
  for (let i = 2; i <= 30; i += 1) {
    paymentMethodOptions.value.push({ label: `到货${i}天付款`, value: i });
  }
}
const message = useMessage();
const addFormRef = ref<FormInst | null>(null);
// const dialog = useWarning();
const { bool: flag, setFalse: closeDialog, setTrue: openDialog } = useBoolean();
const editFlag = ref<boolean>(false);
const addForm = ref<saleMoneyManage.addForm>({
  clientName: '',
  saleContractCode: '',
  saleContractDate: '',
  productName: '',
  productSpec: '',
  saleContractWeight: '',
  copperPrice: '',
  processingFee: '',
  unitPrice: '',
  shipmentDate: '',
  deliveryWeight: '',
  totalAmount: '',
  loan: '',
  isDeleted: 0,
  version: 0
});

const rules = {
  clientName: createRequiredFormRule('请输入客户名称'),
  saleContractCode: createRequiredFormRule('请输入合同编号'),
  saleContractDate: createRequiredFormRule('请输入合同日期'),
  productName: createRequiredFormRule('请输入产品名称'),
  productSpec: createRequiredFormRule('请输入规格'),
  saleContractWeight: createRequiredFormRule('请输入合同重量'),
  copperPrice: createRequiredFormRule('请输入电铜价'),
  processingFee: createRequiredFormRule('请输入加工费'),
  unitPrice: createRequiredFormRule('请输入单价'),
  shipmentDate: createRequiredFormRule('请输入发货日期'),
  deliveryWeight: createRequiredFormRule('请输入送货重量'),
  totalAmount: createRequiredFormRule('请输入总金额'),
  loan: createRequiredFormRule('请输入贷款')
};
const searchForm = ref<saleMoneyManage.searchForm>({
  clientName: '',
  saleContractCode: '',
  productSpec: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const data = ref<saleMoneyManage.columns[]>([]);
const columns: Ref<DataTableColumns<saleMoneyManage.columns>> = ref([
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
    title: '客户名称',
    key: 'clientName',
    width: 120,
    titleAlign: 'center',
    align: 'center'
  },
  {
    title: '合同编号',
    titleAlign: 'center',
    align: 'center',
    key: 'saleContractCode',
    width: 180
  },
  {
    title: '合同日期',
    titleAlign: 'center',
    align: 'center',
    key: 'saleContractDate',
    width: 120
  },
  {
    title: '产品名称',
    titleAlign: 'center',
    align: 'center',
    key: 'productName',
    width: 120
  },
  {
    title: '规格',
    titleAlign: 'center',
    align: 'center',
    key: 'productSpec',
    width: 80
  },
  {
    title: '合同重量(KG)',
    key: 'saleContractWeight',
    titleAlign: 'center',
    align: 'center',
    width: 120
  },
  {
    title: '电铜价',
    titleAlign: 'center',
    align: 'center',
    key: 'copperPrice',
    width: 120
  },
  {
    title: '加工费',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'processingFee'
  },
  {
    title: '单价',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'unitPrice'
  },
  {
    title: '发货日期',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'shipmentDate'
  },
  {
    title: '送货重量',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'deliveryWeight'
  },
  {
    title: '总金额',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'totalAmount'
  },
  {
    title: '贷款',
    titleAlign: 'center',
    align: 'center',
    width: 160,
    key: 'loan'
  },
  {
    title: '操作',
    key: 'action',
    titleAlign: 'center',
    align: 'center',
    width: 180,
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
    clientName: '',
    saleContractCode: '',
    productSpec: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function cancel() {
  addForm.value = {
    clientName: '',
    saleContractCode: '',
    saleContractDate: '',
    productName: '',
    productSpec: '',
    saleContractWeight: '',
    copperPrice: '',
    processingFee: '',
    unitPrice: '',
    shipmentDate: '',
    deliveryWeight: '',
    totalAmount: '',
    loan: '',
    isDeleted: 0,
    version: 0
  };
  closeDialog();
}
function editUser(row) {
  addForm.value = { ...row };
  editFlag.value = true;
  openDialog();
}
function deleteById(row: saleMoneyManage.columns) {
  delSalePriceList(row).then(res => {
    if (res.code === 200) {
      init();
    }
  });
}
function submit() {
  addFormRef.value?.validate(errors => {
    if (!errors) {
      if (addForm.value.id) {
        updateSalePriceList(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('修改成功！');
            cancel();
            init();
          }
        });
      } else {
        addSalePriceList(addForm.value).then(res => {
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
  getSalePriceList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}
onMounted(() => {
  init();
  getPaymentMethod();
});
</script>

<style scoped lang="scss">
.n-data-table .n-data-table-th.n-data-table-th--fixed-right,
.n-data-table .n-data-table-td.n-data-table-td--fixed-right {
  box-shadow: -5px 2px 10px 0px rgb(198, 196, 196);
}
</style>

function startLoading() { throw new Error('Function not implemented.'); } function startLoading() { throw new
Error('Function not implemented.'); }
