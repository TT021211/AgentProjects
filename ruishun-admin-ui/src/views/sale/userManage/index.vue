<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline label-placement="top" :model="searchForm">
        <n-form-item label="客户名称" path="clientName">
          <n-input v-model:value="searchForm.clientName" placeholder="请输入客户名称"></n-input>
        </n-form-item>
        <n-form-item label="统一社会代码" path="creditCode">
          <n-input v-model:value="searchForm.creditCode" placeholder="请输入统一社会代码"></n-input>
        </n-form-item>
        <n-form-item label="业务员" path="salesmanName">
          <n-input v-model:value="searchForm.salesmanName" placeholder="请输入业务员"></n-input>
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
    <my-card title="用户列表">
      <template #right>
        <div class="flex-center">
          <cx-columns v-model:columns="columns" size="small"></cx-columns>
          <n-button type="primary" size="small" class="ml-5px" @click="openDialog">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增客户
          </n-button>
        </div>
      </template>
      <n-data-table :loading="loading" :data="data" :columns="columns"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="flag"
      width="1000px"
      :title="editFlag ? '编辑客户' : '新增客户'"
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
              <n-form-item-grid-item :span="1" label="开户银行" path="bankAccount">
                <n-input v-model:value="addForm.bankAccount" placeholder="请输入开户银行" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="客户简称" path="clientNick">
                <n-input v-model:value="addForm.clientNick" placeholder="请输入客户简称" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="客户编码" path="clientCode">
                <n-input v-model:value="addForm.clientCode" placeholder="请输入客户编码" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="统一信用社代码" path="creditCode">
                <n-input v-model:value="addForm.creditCode" placeholder="请输入统一信用社代码"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="地址" path="address">
                <n-input v-model:value="addForm.address" placeholder="请输入地址" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="法人姓名" path="legalPersonName">
                <n-input v-model:value="addForm.legalPersonName" placeholder="请输入法人姓名" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="法人电话" path="legalPersonPhone">
                <n-input v-model:value="addForm.legalPersonPhone" placeholder="请输入法人电话" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="法人身份证">
                <n-input v-model:value="addForm.legalPersonIdcard" placeholder="请输入法人身份证"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="联系人">
                <n-input v-model:value="addForm.contact1" placeholder="请输入联系人"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="联系人职务">
                <n-input v-model:value="addForm.contact1Title" placeholder="请输入联系人职务"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="联系人电话">
                <n-input v-model:value="addForm.contact1Tel" placeholder="请输入联系人电话"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="联系人手机">
                <n-input v-model:value="addForm.contact1Phone" placeholder="请输入联系人手机"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="联系人传真">
                <n-input v-model:value="addForm.contact1Fax" placeholder="请输入联系人传真"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="联系人邮箱">
                <n-input v-model:value="addForm.contact1Email" placeholder="请输入联系人邮箱"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="公司成立时间">
                <n-input v-model:value="addForm.foundingTime" placeholder="请输入公司成立时间"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="注册资金">
                <n-input v-model:value="addForm.registeredCapital" placeholder="请输入注册资金"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="经营范围">
                <n-input v-model:value="addForm.businessScope" placeholder="请输入经营范围"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="终端/经销商">
                <n-select
                  v-model:value="addForm.ifDistributor"
                  :options="[
                    { label: '终端', value: 0 },
                    { label: '经销商', value: 1 }
                  ]"
                  placeholder="请选择"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="建立模式">
                <n-select
                  v-model:value="addForm.leadSource"
                  :options="[
                    { label: '电话来访', value: 1 },
                    { label: '客户介绍', value: 2 },
                    { label: '上门拜访', value: 3 }
                  ]"
                  placeholder="请选择"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="价格模式">
                <n-select
                  v-model:value="addForm.pricingModel"
                  :options="[
                    { label: '报价', value: '报价' },
                    { label: '固定', value: '固定' },
                    { label: '其他', value: '其他' }
                  ]"
                  placeholder="请选择"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="需求产品">
                <n-input v-model:value="addForm.products" placeholder="请输入需求产品"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="月需求量">
                <n-input v-model:value="addForm.productsNum" placeholder="请输入月需求量"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="付款方式">
                <n-select
                  v-model:value="addForm.paymentMethod"
                  :options="paymentMethodOptions"
                  placeholder="请输入付款方式"
                ></n-select>
              </n-form-item-grid-item>

              <n-form-item-grid-item :span="1" label="业务员" path="salesmanName">
                <n-input v-model:value="addForm.salesmanName" placeholder="请输入业务员" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="业务员电话" path="salesmanPhone">
                <n-input v-model:value="addForm.salesmanPhone" placeholder="请输入业务员电话" />
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
import { getUserList, addUser, deleteUser, editUserFach } from '@/service/api/sale/userManage';
import { useEditBtn, useDelBtn } from '@/hooks/common/useBtn';
import { useLoading, useBoolean } from '~/src/hooks';
import { createRequiredFormRule } from '~/src/utils';

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
const { loading, startLoading, endLoading } = useLoading();
const editFlag = ref<boolean>(false);
const addForm = ref<UserManage.addForm>({
  paymentMethod: '',
  productsNum: '',
  products: '',
  pricingModel: null,
  leadSource: null,
  ifDistributor: null,
  businessScope: '',
  registeredCapital: '',
  foundingTime: '',
  contact1Email: '',
  contact1Fax: '',
  contact1Phone: '',
  contact1Tel: '',
  contact1Title: '',
  contact1: '',
  legalPersonIdcard: '',
  bankAccount: '', // 银行账号
  clientName: '',
  clientCode: '',
  creditCode: '',
  clientNick: '',
  address: '',
  legalPersonName: '',
  legalPersonPhone: '',
  salesmanName: '',
  salesmanPhone: ''
});

const rules = {
  clientCode: createRequiredFormRule('客户编码'),
  clientName: createRequiredFormRule('客户名称'),
  creditCode: [
    {
      required: false,
      message: '请输入统一社会信用代码',
      trigger: 'blur'
    },
    {
      pattern: /^[0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}$/,
      message: '统一社会信用代码格式不正确',
      trigger: 'blur'
    }
  ],
  legalPersonName: createRequiredFormRule('请输入法人姓名'),
  legalPersonPhone: [
    {
      required: false,
      message: '请输入法人电话',
      trigger: 'blur'
    },
    {
      pattern: / ^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\d{8}$/,
      message: '手机号格式错误',
      trigger: 'change'
    }
  ],
  salesmanName: createRequiredFormRule('请输入业务员'),
  salesmanPhone: [
    {
      required: false,
      message: '请输入业务员电话',
      trigger: 'blur'
    },
    {
      pattern: / ^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\d{8}$/,
      message: '手机号格式错误',
      trigger: 'change'
    }
  ]
};
const searchForm = ref<UserManage.searchForm>({
  creditCode: '',
  clientName: '',
  salesmanName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const data = ref<UserManage.columns[]>([]);
const columns: Ref<DataTableColumns<UserManage.columns>> = ref([
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
    width: 200,
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '客户简称',
    align: 'center',
    key: 'clientNick',
    width: 80
  },
  {
    title: '客户编码',
    align: 'center',
    key: 'clientCode',
    width: 80
  },
  {
    title: '统一社会信用代码',
    align: 'center',
    key: 'creditCode',
    width: 200,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '地址',
    align: 'center',
    key: 'address',
    width: 200,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '法人姓名',
    key: 'legalPersonName',
    align: 'center',
    width: 100
  },
  {
    title: '法人电话',
    align: 'center',
    key: 'legalPersonPhone',
    width: 150
  },
  {
    title: '业务员',
    align: 'center',
    width: 120,
    key: 'salesmanName'
  },
  {
    title: '业务员电话',
    align: 'center',
    width: 150,
    key: 'salesmanPhone'
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
    creditCode: '',
    clientName: '',
    salesmanName: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
function cancel() {
  addForm.value = {
    paymentMethod: '',
    productsNum: '',
    products: '',
    pricingModel: null,
    leadSource: null,
    ifDistributor: null,
    businessScope: '',
    registeredCapital: '',
    foundingTime: '',
    contact1Email: '',
    contact1Fax: '',
    contact1Phone: '',
    contact1Tel: '',
    contact1Title: '',
    contact1: '',
    legalPersonIdcard: '',
    bankAccount: '', // 银行账号
    clientName: '',
    clientCode: '',
    creditCode: '',
    clientNick: '',
    address: '',
    legalPersonName: '',
    legalPersonPhone: '',
    salesmanName: '',
    salesmanPhone: ''
  };
  closeDialog();
}
function editUser(row) {
  addForm.value = { ...row };
  editFlag.value = true;
  openDialog();
}
function deleteById(row: UserManage.columns) {
  // dialog.warn(() => {
  //   console.log(row);

  // });
  deleteUser(row.id).then(res => {
    if (res.code === 200) {
      init();
    }
  });
}
function submit() {
  addFormRef.value?.validate(errors => {
    if (!errors) {
      if (addForm.value.id) {
        editUserFach(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('修改成功！');
            cancel();
            init();
          }
        });
      } else {
        addUser(addForm.value).then(res => {
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
  getUserList(searchForm.value).then(res => {
    // eslint-disable-next-line no-console
    console.log(res);
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
      endLoading();
    }
  });
}
onMounted(() => {
  init();
  getPaymentMethod();
});
</script>

<style scoped lang="scss"></style>
