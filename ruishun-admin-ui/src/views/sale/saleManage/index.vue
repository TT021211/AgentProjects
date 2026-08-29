<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline label-placement="top" :model="searchForm">
        <n-form-item label="客户名称">
          <n-input v-model:value="searchForm.userName" placeholder="请输入客户名称"></n-input>
        </n-form-item>
        <n-form-item label="客户电话">
          <n-input v-model:value="searchForm.userPhone" placeholder="请输入客户电话"></n-input>
        </n-form-item>
        <n-form-item label="合同编号">
          <n-input v-model:value="searchForm.contractNumber" placeholder="请输入合同编号"></n-input>
        </n-form-item>
        <n-form-item label="产品名称">
          <n-input v-model:value="searchForm.productName" placeholder="请输入产品名称"></n-input>
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
    <my-card title="合同管理">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button type="primary" size="small" class="ml-5px" @click="openDialog">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增合同
          </n-button>
        </div>
      </template>
      <n-data-table :loading="loading" :data="data" :columns="columns"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="flag"
      width="1000px"
      :title="editFlag ? '编辑合同' : '新增合同'"
      @cancel="cancel"
      @submit="submit"
    >
      <template #content>
        <div style="width: 100%">
          <n-form ref="addFormRef" :model="addForm" :rules="rules" label-placement="left" label-width="110px">
            <h3 class="titleInfo">合同信息</h3>
            <n-grid :cols="3" :x-gap="10" :y-gap="10">
              <n-form-item-grid-item :span="1" label="合同编号" path="contractNumber">
                <n-input v-model:value="addForm.contractNumber" size="small" placeholder="请输入合同编号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="产品名称" path="productName">
                <n-input v-model:value="addForm.productName" size="small" placeholder="请输入产品名称"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="规格型号" path="specificationModel">
                <n-input v-model:value="addForm.specificationModel" size="small" placeholder="请输入规格型号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="重量" path="weight">
                <n-input-number v-model:value="addForm.weight" size="small" placeholder="请输入重量"></n-input-number>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="单位" path="unit">
                <n-input v-model:value="addForm.unit" size="small" placeholder="请输入单位"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="电铜价" path="copperPrice">
                <n-input-number
                  v-model:value="addForm.copperPrice"
                  size="small"
                  placeholder="请输入电铜价"
                ></n-input-number>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="加工费" path="processingFee">
                <n-input-number
                  v-model:value="addForm.processingFee"
                  size="small"
                  placeholder="请输入加工费"
                ></n-input-number>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="单价" path="price">
                <n-input-number v-model:value="addForm.price" size="small" placeholder="请输入单价"></n-input-number>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="总金额" path="totalPrice">
                <n-input-number
                  v-model:value="addForm.totalPrice"
                  size="small"
                  placeholder="请输入总金额"
                ></n-input-number>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="总金额(大写)" path="totalPriceUpper">
                <n-input
                  v-model:value="addForm.totalPriceUpper"
                  size="small"
                  placeholder="请输入总金额(大写)"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="签订日期" path="dateOfSigning">
                <n-date-picker
                  v-model:formatted-value="addForm.dateOfSigning"
                  placeholder="请选择签订日期"
                  value-format="yyyy-MM-dd"
                  class="w-160px"
                  type="date"
                  clearable
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="签订地点" path="signedAt">
                <n-input v-model:value="addForm.signedAt" size="small" placeholder="请输入签订地点"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="3" label="合同说明" path="contractNote">
                <n-input
                  v-model:value="addForm.contractNote"
                  type="textarea"
                  size="small"
                  placeholder="请输入合同说明"
                ></n-input>
              </n-form-item-grid-item>
            </n-grid>
            <h3 class="titleInfo">供货商信息</h3>
            <n-grid :cols="3" :x-gap="10" :y-gap="10">
              <n-form-item-grid-item :span="1" label="供货方" path="demandName">
                <n-input v-model:value="addForm.demandName" size="small" placeholder="请输入供货方"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="地址" path="demandAddress">
                <n-input v-model:value="addForm.demandAddress" size="small" placeholder="请输入地址"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="代表" path="demandLegal">
                <n-input v-model:value="addForm.demandLegal" size="small" placeholder="请输入代表"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="电话" path="demandPhone">
                <n-input v-model:value="addForm.demandPhone" size="small" placeholder="请输入电话"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="传真号" path="demandFax">
                <n-input v-model:value="addForm.demandFax" size="small" placeholder="请输入传真号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="开户银行" size="small" path="demandBank">
                <n-input v-model:value="addForm.demandBank" placeholder="请输入开户银行"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="账号" path="demandAccount">
                <n-input v-model:value="addForm.demandAccount" size="small" placeholder="请输入账号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="税号" path="demandTax">
                <n-input v-model:value="addForm.demandTax" size="small" placeholder="请输入税号"></n-input>
              </n-form-item-grid-item>
            </n-grid>
            <h3 class="titleInfo">购货方信息</h3>
            <n-grid :cols="3" :x-gap="10" :y-gap="10">
              <n-form-item-grid-item :span="1" label="购货方" path="userName">
                <n-input v-model:value="addForm.userName" size="small" placeholder="请输入购货方"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="地址" path="userAddress">
                <n-input v-model:value="addForm.userAddress" size="small" placeholder="请输入地址"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="代表" path="userLegal">
                <n-input v-model:value="addForm.userLegal" size="small" placeholder="请输入代表"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="电话" path="userPhone">
                <n-input v-model:value="addForm.userPhone" size="small" placeholder="请输入电话"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="传真号" path="userFax">
                <n-input v-model:value="addForm.userFax" size="small" placeholder="请输入传真号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="开户银行" path="userBank">
                <n-input v-model:value="addForm.userBank" size="small" placeholder="请输入开户银行"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="账号" path="userAccount">
                <n-input v-model:value="addForm.userAccount" size="small" placeholder="请输入账号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="税号" path="userTax">
                <n-input v-model:value="addForm.userTax" size="small" placeholder="请输入税号"></n-input>
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
    <my-dialog
      v-model:show="contractDetail"
      width="1100px"
      title="合同明细"
      @cancel="contractCancel"
      @submit="contractCancel"
    >
      <template #content>
        <div style="width: 100%; text-align: center">
          <h3 class="contractTitle">江西瑞顺超细铜线科技协同创新有限公司订购单</h3>
          <n-grid style="margin-top: -10px" :cols="3" :x-gap="10" :y-gap="10">
            <n-form-item-grid-item label-placement="left" :span="1" label="供货方:">
              <n-space>{{ contractData.demandName }}</n-space>
            </n-form-item-grid-item>
            <n-form-item-grid-item label-placement="left" :span="1" label="编号:">
              <n-space>{{ contractData.contractNumber }}</n-space>
            </n-form-item-grid-item>
            <n-form-item-grid-item label-placement="left" :span="1" label="购货方:">
              <n-space>{{ contractData.userName }}</n-space>
            </n-form-item-grid-item>
            <n-form-item-grid-item style="margin-top: -36px" label-placement="left" :span="1" label="签订时间:">
              <n-space>{{ contractData.dateOfSigning }}</n-space>
            </n-form-item-grid-item>
            <n-form-item-grid-item style="margin-top: -36px" label-placement="left" :span="1" label="签订地点:">
              <n-space>{{ contractData.signedAt }}</n-space>
            </n-form-item-grid-item>
          </n-grid>
          <n-data-table
            style="margin-top: -20px"
            :loading="loading"
            :data="contractList"
            :columns="contractColumns"
          ></n-data-table>
          <div style="font-weight: bold; font-size: 14px; text-align: left">
            金额(大写)：{{ contractData.totalPriceUpper }}
          </div>
          <div style="border: 1px solid #f3f3f3; padding: 12px; text-align: left; white-space: pre-wrap; margin: 5px 0">
            {{ contractData.contractNote }}
          </div>
          <div class="info">
            <div class="supplier">
              <h3 class="supplierTitle">供货方</h3>
              <p>供货商：{{ contractData.demandName }}</p>
              <p>地址：{{ contractData.demandAddress }}</p>
              <p>代表：{{ contractData.demandLegal }}</p>
              <p>电话：{{ contractData.demandPhone }}</p>
              <p>传真号：{{ contractData.demandFax }}</p>
              <p>开户银行：{{ contractData.demandBank }}</p>
              <p>账号：{{ contractData.demandAccount }}</p>
              <p>税号：{{ contractData.demandTax }}</p>
            </div>
            <div class="supplier">
              <h3 class="supplierTitle">购货方</h3>
              <p>购货商：{{ contractData.userName }}</p>
              <p>地址：{{ contractData.userAddress }}</p>
              <p>代表：{{ contractData.userLegal }}</p>
              <p>电话：{{ contractData.userPhone }}</p>
              <p>传真号：{{ contractData.userFax }}</p>
              <p>开户银行：{{ contractData.userBank }}</p>
              <p>账号：{{ contractData.userAccount }}</p>
              <p>税号：{{ contractData.userTax }}</p>
            </div>
          </div>
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
import { getUserList } from '@/service/api/sale/userManage';
import { useEditBtn, useDelBtn, useInfoBtn } from '@/hooks/common/useBtn';
import { useLoading, useBoolean } from '~/src/hooks';
import { createRequiredFormRule, formatDate } from '~/src/utils';
import {
  getSaleContractList,
  addSaleContractList,
  updateSaleContractList,
  delSaleContractList
} from '~/src/service/api/sale/contact';
const { loading, startLoading, endLoading } = useLoading();
const { bool: flag, setFalse: closeDialog, setTrue: openDialog } = useBoolean();
const message = useMessage();

const contractDetail = ref<boolean>(false);
const contractData = ref<any>({});
function contractCancel() {
  contractDetail.value = false;
}
const contractList = ref<contractManage.columns[]>([]);
const contractColumns: Ref<DataTableColumns<contractManage.columns>> = ref([
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
    key: 'specificationModel',
    width: 120
  },
  {
    title: '重量',
    titleAlign: 'center',
    align: 'center',
    key: 'weight',
    width: 120
  },
  {
    title: '单位',
    key: 'unit',
    titleAlign: 'center',
    align: 'center',
    width: 80
  },
  {
    title: '电铜价',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'copperPrice'
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
    key: 'price',
    width: 80
  },
  {
    title: '总金额',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'totalPrice'
  }
]);

const clientOptions: any = ref([]);
const paymentMethodOptions = ref([
  { label: '款到发货', value: 1 },
  { label: '货到付款', value: 0 }
]);
function getPaymentMethod() {
  for (let i = 2; i <= 30; i += 1) {
    paymentMethodOptions.value.push({ label: `到货${i}天付款`, value: i });
  }
}
const addFormRef = ref<FormInst | null>(null);
const editFlag = ref<boolean>(false);
const addForm = ref<contractManage.addForm>({
  userName: '',
  userAddress: '',
  userLegal: '',
  userPhone: '',
  userFax: '',
  userBank: '',
  userAccount: '',
  userTax: '',
  demandName: '',
  demandAddress: '',
  demandLegal: '',
  demandPhone: '',
  demandFax: '',
  demandBank: '',
  demandAccount: '',
  demandTax: '',
  contractNumber: '',
  productName: '',
  specificationModel: '',
  unit: '',
  weight: null,
  copperPrice: null,
  processingFee: null,
  price: null,
  totalPrice: null,
  totalPriceUpper: '',
  dateOfSigning: null,
  signedAt: '',
  contractNote: '',
  userId: 2,
  isDeleted: 0,
  version: 0
});

const rules = {
  userName: createRequiredFormRule('请输入购货方'),
  userAddress: createRequiredFormRule('请输入购货方地址'),
  demandName: createRequiredFormRule('请输入供货商'),
  demandAddress: createRequiredFormRule('请输入供货商地址'),
  contractNumber: createRequiredFormRule('请输入合同编号'),
  productName: createRequiredFormRule('请输入产品名称'),
  specificationModel: createRequiredFormRule('请输入规格型号'),
  weight: createRequiredFormRule('请输入重量'),
  unit: createRequiredFormRule('请输入单位'),
  copperPrice: createRequiredFormRule('请输入电铜价'),
  processingFee: createRequiredFormRule('请输入加工费'),
  price: createRequiredFormRule('请输入单价'),
  totalPrice: createRequiredFormRule('请输入总金额'),
  totalPriceUpper: createRequiredFormRule('请输入总金额(大写)'),
  dateOfSigning: createRequiredFormRule('请选择签订日期'),
  signedAt: createRequiredFormRule('请输入签订地点'),
  contractNote: createRequiredFormRule('请输入合同说明')
};
const searchForm = ref<contractManage.searchForm>({
  userName: '',
  userPhone: '',
  contractNumber: '',
  productName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const data = ref<contractManage.columns[]>([]);
const columns: Ref<DataTableColumns<contractManage.columns>> = ref([
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
    key: 'userName',
    width: 140,
    titleAlign: 'center',
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '客户电话',
    key: 'userPhone',
    width: 120,
    titleAlign: 'center',
    align: 'center'
  },
  {
    title: '合同编号',
    titleAlign: 'center',
    align: 'center',
    key: 'contractNumber',
    width: 140
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
    key: 'specificationModel',
    width: 140
  },
  {
    title: '单位',
    key: 'unit',
    titleAlign: 'center',
    align: 'center',
    width: 120
  },
  {
    title: '重量',
    titleAlign: 'center',
    align: 'center',
    key: 'weight',
    width: 150
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
    key: 'processingFee',
    width: 120
  },
  {
    title: '单价',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'price'
  },
  {
    title: '总金额',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'totalPrice'
  },
  {
    title: '签订时间',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'dateOfSigning'
  },
  {
    title: '签订地点',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'signedAt'
  },
  {
    title: '操作',
    key: 'action',
    titleAlign: 'center',
    align: 'center',
    width: 240,
    fixed: 'right',
    render: row => {
      return (
        <div>
          {useInfoBtn(() => {
            lookDetail(row);
          }, 'tiny')}
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
function lookDetail(row) {
  // eslint-disable-next-line no-console
  console.log('明细信息', row);
  contractData.value = row;
  contractList.value = [];
  contractList.value.push(row);
  contractDetail.value = true;
}
function handleSearch() {
  init();
}
function handleReset() {
  searchForm.value = {
    userName: '',
    userPhone: '',
    contractNumber: '',
    productName: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function cancel() {
  addForm.value = {
    userName: '',
    userAddress: '',
    userLegal: '',
    userPhone: '',
    userFax: '',
    userBank: '',
    userAccount: '',
    userTax: '',
    demandName: '',
    demandAddress: '',
    demandLegal: '',
    demandPhone: '',
    demandFax: '',
    demandBank: '',
    demandAccount: '',
    demandTax: '',
    contractNumber: '',
    productName: '',
    specificationModel: '',
    unit: '',
    weight: null,
    copperPrice: null,
    processingFee: null,
    price: null,
    totalPrice: null,
    totalPriceUpper: '',
    dateOfSigning: null,
    signedAt: '',
    contractNote: '',
    userId: 2,
    isDeleted: 0,
    version: 0
  };
  closeDialog();
}
function editUser(row) {
  addForm.value = { ...row };
  addForm.value.dateOfSigning = formatDate(new Date(row.dateOfSigning), 'yyyy-MM-dd');
  editFlag.value = true;
  openDialog();
}
function deleteById(row: contractManage.columns) {
  delSaleContractList(row).then(res => {
    if (res.code === 200) {
      message.success('删除成功');
      init();
    } else {
      message.error('删除失败');
    }
  });
}
function submit() {
  addFormRef.value?.validate(errors => {
    if (!errors) {
      if (addForm.value.id) {
        updateSaleContractList(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('修改成功！');
            cancel();
            init();
          }
        });
      } else {
        addSaleContractList(addForm.value).then(res => {
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
  getSaleContractList(searchForm.value).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
      endLoading();
    }
  });
}
function getList() {
  getUserList(null).then(res => {
    if (res.code === 200) {
      clientOptions.value = [];
      res.rows.forEach((item: any) => {
        clientOptions.value.push({
          label: item.clientNick,
          value: item.clientNick,
          id: item.id,
          code: item.clientCode
        });
      });
    }
  });
}
onMounted(() => {
  init();
  getList();
  getPaymentMethod();
});
</script>

<style scoped lang="scss">
.supplierTitle {
  font-size: 18px;
  font-weight: bold;
  text-align: center;
}
.supplier {
  flex: 1;
  text-align: left;
  border: 1px solid #f3f3f3;
  padding: 10px;
  margin-right: 10px;
}
.info {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-around;
  margin-top: -5px;
}
.titleInfo {
  font-size: 18px;
  font-weight: bold;
  text-align: center;
  margin: -10px 0 10px 0;
}
.titleInfos {
  font-size: 14px;
  font-weight: bold;
  text-align: center;
  margin: -10px 0 10px 0;
}
.contractTitle {
  font-size: 25px;
  font-weight: bold;
  text-align: center;
  margin: -10px 0 10px 0;
}
.n-data-table .n-data-table-th.n-data-table-th--fixed-right,
.n-data-table .n-data-table-td.n-data-table-td--fixed-right {
  box-shadow: -5px 2px 10px 0px rgb(198, 196, 196);
}
</style>
