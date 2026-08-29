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
        <n-form-item label="跟进状态">
          <n-select
            v-model:value="searchForm.attr1"
            placeholder="请选择跟进状态"
            class="w-180px"
            :options="followUpStatusList"
            filterable
          ></n-select>
        </n-form-item>
        <n-form-item label="客户类型">
          <n-select
            v-model:value="searchForm.attr2"
            placeholder="请选择客户类型"
            class="w-180px"
            :options="clientTypeList"
            filterable
          ></n-select>
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
          <cx-columns v-model:columns="columns"></cx-columns>
          <!-- <n-button type="warning" size="small" class="ml-5px" @click="synchronization">
            <icon-ic-round-plus class="mr-4px text-20px" />
            同步erp客户
          </n-button> -->
          <n-button type="primary" size="small" class="ml-5px" @click="openDialog">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增客户
          </n-button>
        </div>
      </template>
      <n-data-table
        :loading="loading"
        :data="data"
        :columns="columns"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
        :row-props="rowProps"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>

    <my-dialog
      v-model:show="flag"
      width="1200px"
      :title="editFlag ? '编辑客户' : '新增客户'"
      @cancel="cancel"
      @submit="submit"
    >
      <template #content>
        <div style="width: 100%">
          <n-form ref="addFormRef" :model="addForm" :rules="rules" label-placement="left" label-width="130px">
            <n-grid :cols="3" :x-gap="10" :y-gap="10">
              <n-form-item-grid-item :span="1" label="客户名称" path="clientName">
                <n-input v-model:value="addForm.clientName" placeholder="请输入客户名称" style="width: 100%" />
              </n-form-item-grid-item>
              <!-- <n-form-item-grid-item :span="1" label="开户银行" path="bankAccount">
                <n-input v-model:value="addForm.bankAccount" placeholder="请输入开户银行" />
              </n-form-item-grid-item> -->
              <n-form-item-grid-item :span="1" label="客户简称" path="clientNick">
                <n-input v-model:value="addForm.clientNick" placeholder="请输入客户简称" style="width: 100%" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="客户编码" path="clientCode">
                <n-input v-model:value="addForm.clientCode" placeholder="请输入客户编码" style="width: 100%" />
              </n-form-item-grid-item>
              <!-- <n-form-item-grid-item :span="1" label="统一信用社代码" path="creditCode">
                <n-input v-model:value="addForm.creditCode" placeholder="请输入统一信用社代码"></n-input>
              </n-form-item-grid-item> -->
              <n-form-item-grid-item :span="1" label="地址" path="addressId">
                <!-- <n-input v-model:value="addForm.address" placeholder="请输入地址" /> -->
                <div style="display: flex; flex-wrap: wrap; width: 100%">
                  <n-cascader
                    v-model:value="addForm.addressId"
                    style="width: 100%"
                    :expand-trigger="'click'"
                    :options="addressOptions"
                    :check-strategy="checkStrategyIsChild ? 'child' : 'all'"
                    :show-path="showPath"
                    remote
                    placeholder="请选择省市区"
                  />
                  <n-input
                    v-model:value="addForm.contact2Tel"
                    placeholder="请输入详细地址"
                    style="width: 100%"
                    class="mt-10px"
                    type="textarea"
                  />
                </div>
              </n-form-item-grid-item>

              <!-- <n-form-item-grid-item :span="1" label="法人姓名">
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
              </n-form-item-grid-item> -->
              <!-- <n-form-item-grid-item :span="1" label="公司成立时间">
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
              </n-form-item-grid-item> -->
              <n-form-item-grid-item :span="1" label="客户来源">
                <n-select
                  v-model:value="addForm.leadSource"
                  :options="[
                    { label: '电话来访', value: 1 },
                    { label: '客户介绍', value: 2 },
                    { label: '上门拜访', value: 3 },
                    { label: '销售自拓', value: 4 },
                    { label: '其他', value: 5 }
                  ]"
                  style="width: 100%"
                  placeholder="请选择"
                ></n-select>
              </n-form-item-grid-item>

              <n-form-item-grid-item :span="1" label="客户级别">
                <n-select
                  v-model:value="addForm.contact2"
                  :options="clientGradeList"
                  placeholder="请选择"
                  style="width: 100%"
                ></n-select>
              </n-form-item-grid-item>
              <!-- <n-form-item-grid-item :span="1" label="价格模式">
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
              </n-form-item-grid-item> -->

              <n-form-item-grid-item v-show="addForm.attr2 === 1" :span="1" label="业务员">
                <n-input v-model:value="addForm.salesmanName" placeholder="请输入业务员" />
              </n-form-item-grid-item>
              <n-form-item-grid-item v-show="addForm.attr2 === 1" :span="1" label="业务员电话" path="salesmanPhone">
                <n-input v-model:value="addForm.salesmanPhone" placeholder="请输入业务员电话" />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="跟进状态">
                <n-select
                  v-model:value="addForm.attr1"
                  placeholder="请选择跟进状态"
                  style="width: 100%"
                  :options="followUpStatusList"
                  filterable
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="客户类型">
                <n-select v-model:value="addForm.attr2" :options="clientTypeList" placeholder="请选择"></n-select>
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>

    <archives v-show="archivesShow" :from-data="archivesForm" @close-archives="closeArchives" @save="save" />
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { useMessage } from 'naive-ui';
import type { DataTableColumns, FormInst } from 'naive-ui';
import {
  getUserList,
  addUser,
  deleteUser,
  editUserFach
  // getSyncClientList
  // getProvinces
} from '@/service/api/sale/userManage';
import { useEditBtn, useDelBtn } from '@/hooks/common/useBtn';
import { dataTableConfig } from '@/config/dataTableConfig';
import { useLoading, useBoolean } from '~/src/hooks';
import { createRequiredFormRule } from '~/src/utils';
import { useResetSearch } from '~/src/utils/common/searchReset';
import { proCityList } from './options/address';
import archives from './archives/index.vue';

const paymentMethodOptions = ref([
  { label: '款到发货', value: 1 },
  { label: '货到付款', value: 0 }
]);

const followUpStatusList = ref([
  { label: '待跟进', value: '待跟进' },
  { label: '潜在客户', value: '潜在客户' },
  { label: '有意向', value: '有意向' },
  { label: '高意向', value: '高意向' },
  { label: '未成交', value: '未成交' },
  { label: '已成交', value: '已成交' }
]);

const clientGradeList = ref<Array<{ label: string; value: string }>>([
  { label: '普通客户', value: '1' },
  { label: '重要客户', value: '2' },
  { label: '战略客户', value: '3' }
]);

const clientTypeList = ref([
  { label: '海量客户', value: 0 },
  { label: '个人客户', value: 1 }
]);

const checkStrategyIsChild = ref(true);

const showPath = ref(true);

const addressOptions = ref<Array<any>>(JSON.parse(JSON.stringify(proCityList)));

const rowClickTimer = ref<any>(null);

const archivesForm = ref({
  bankAccount: '',
  creditCode: '',
  foundingTime: null,
  registeredCapital: '',
  businessScope: '',
  ifDistributor: null,
  pricingModel: null,
  products: '',
  productsNum: ''
});
const archivesShow = ref<boolean>(false);
const message = useMessage();

const addFormRef = ref<FormInst | null>(null);

// const dialog = useWarning();
const { bool: flag, setFalse: closeDialog, setTrue: openDialog } = useBoolean();
const { loading, startLoading, endLoading } = useLoading();
const editFlag = ref<boolean>(false);
const addForm = ref<information.addForm>({
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
  salesmanPhone: '',
  attr1: null,
  attr2: null,
  contact2: null,
  addressId: null,
  contact2Tel: ''
});

const rules = {
  clientCode: createRequiredFormRule('客户编码'),
  clientName: createRequiredFormRule('客户名称'),
  addressId: createRequiredFormRule('请选择地址'),
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
      pattern: /^((1[0-9]))\d{9}$/,
      message: '手机号格式错误',
      trigger: 'change'
    }
  ]
};
const { searchForm, reset } = useResetSearch({
  creditCode: '',
  clientName: '',
  salesmanName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0,
  attr1: null,
  attr2: null
});
const data = ref<information.columns[]>([]);

const columns: Ref<DataTableColumns<information.columns>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1,
    width: 120
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
    width: 200,
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '客户编码',
    align: 'center',
    key: 'clientCode',
    width: 120
  },
  {
    title: '客户来源',
    align: 'center',
    key: 'leadSource',
    width: 120,
    render: row => {
      return getLeadSource(row.leadSource);
    }
  },
  {
    title: '客户级别',
    align: 'center',
    key: 'contact2',
    width: 120,
    render: row => {
      return getGrade(row.contact2);
    }
  },

  // {
  //   title: '统一社会信用代码',
  //   align: 'center',
  //   key: 'creditCode',
  //   width: 200,
  //   ellipsis: {
  //     tooltip: true
  //   }
  // },
  {
    title: '地址',
    align: 'center',
    key: 'address',
    width: 200,
    ellipsis: {
      tooltip: true
    },
    render: row => {
      return (row.address || '') + (row.contact2Tel || '');
    }
  },
  // {
  //   title: '法人姓名',
  //   key: 'legalPersonName',
  //   align: 'center',
  //   width: 200,
  //   ellipsis: {
  //     tooltip: true
  //   }
  // },
  // {
  //   title: '法人电话',
  //   align: 'center',
  //   key: 'legalPersonPhone',
  //   width: 150
  // },
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
    title: '客户类型',
    align: 'center',
    key: '',
    width: 100,
    fixed: 'right',
    render: row => getClientTypeText(row.attr2).text
  },
  {
    title: '跟进状态',
    align: 'center',
    width: 100,
    key: 'attr1',
    fixed: 'right',
    render: row => (
      <n-tag type={attr1Style(row.attr1).type} v-show={row.attr1}>
        {row.attr1}
      </n-tag>
    )
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    width: 180,
    fixed: 'right',
    render: row => {
      return (
        <div class="flex">
          {/* <n-button
            type="info"
            size="small"
            class="mr-10px"
            onClick={() => {
              openRecord(row);
            }}
          >
            走访记录
          </n-button> */}
          {useEditBtn(() => {
            editUser(row);
          }, 'small')}

          {useDelBtn(() => {
            deleteById(row);
          }, 'small')}
        </div>
      );
    }
  }
]);

// function synchronization() {
//   const d = dialog.warning({
//     title: '提示',
//     content: '确认要同步数据吗吗?',
//     positiveText: '确认',
//     negativeText: '取消',
//     onPositiveClick: () => {
//       d.loading = true;
//       return new Promise(resolve => {
//         getSyncClientList().then(res => {
//           if (res.code === 200) {
//             init();
//             window.$message?.success('同步成功');
//           }
//           resolve(true);
//         });
//       });
//     }
//   });
// }

function getLeadSource(leadSource: number) {
  switch (leadSource) {
    case 1:
      return '电话来访';
    case 2:
      return '客户介绍';
    case 3:
      return '上门拜访';
    case 4:
      return '销售自拓';
    case 5:
      return '其他';
    default:
      return '';
  }
}

function getGrade(grade: number) {
  switch (grade) {
    case 1:
      return '普通客户';
    case 2:
      return '重要客户';
    case 3:
      return '战略客户';
    default:
      return '';
  }
}

function rowProps(row: UserManage.columns) {
  return {
    style: 'cursor: pointer;',
    onClick: () => {
      if (rowClickTimer.value) {
        clearTimeout(rowClickTimer.value);
        rowClickTimer.value = null;
        archivesShow.value = true;
        addForm.value = JSON.parse(JSON.stringify(row));
        for (const key in archivesForm.value) {
          if (Object.hasOwn(archivesForm.value, key)) {
            archivesForm.value[key] = row[key];
          }
        }
        return;
      }
      rowClickTimer.value = setTimeout(() => {
        rowClickTimer.value = null;
      }, 300);
    }
  };
}

function closeArchives() {
  archivesShow.value = false;
}

function getPaymentMethod() {
  for (let i = 2; i <= 30; i += 1) {
    paymentMethodOptions.value.push({ label: `到货${i}天付款`, value: i });
  }
}

function getClientTypeText(num: number) {
  switch (num) {
    case 0:
      return {
        text: '海量客户'
      };
    case 1:
      return {
        text: '个人客户'
      };
    default:
      return { text: '' };
  }
}

function attr1Style(str: string) {
  switch (str) {
    case '待跟进':
      return {
        type: 'warning'
      };
    case '潜在客户':
      return {
        type: 'info'
      };
    case '有意向':
      return {
        type: 'info'
      };
    case '高意向':
      return {
        type: 'success'
      };
    case '未成交':
      return {
        type: 'error'
      };
    case '已成交':
      return {
        type: 'success'
      };
    default:
      return {
        type: ''
      };
  }
}

function save(e) {
  for (const key in e.value) {
    if (Object.hasOwn(e.value, key)) {
      addForm.value[key] = e.value[key];
    }
  }
  editUserFach(addForm.value).then(res => {
    if (res.code === 200) {
      message.success(e.tip);
      init();
    }
  });
}

function handleSearch() {
  init();
}
function handleReset() {
  reset();
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
    salesmanPhone: '',
    attr1: null,
    attr2: null,
    contact2: null,
    addressId: null,
    contact2Tel: ''
  };
  closeDialog();
}
function editUser(row) {
  row.addressId = row.addressId?.toString();
  addForm.value = { ...row };
  editFlag.value = true;
  openDialog();
}
function deleteById(row: information.columns) {
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
      if (addForm.value.addressId !== null) {
        addForm.value.address = getAddressName(addForm.value.addressId);
      }
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

function getAddressName(code: string) {
  const provinceCode = `${code.slice(0, 2)}0000`;
  const cityCode = `${code.slice(0, 4)}00`;
  const provinceItem = proCityList.find(item => item.value === provinceCode);
  if (!provinceItem) return '';
  if (!provinceItem.children) return provinceItem.label;
  const cityItem = provinceItem.children.find(item => item.value === cityCode);
  if (!cityItem) return provinceItem.label;
  const districtItem = cityItem.children.find(item => item.value === code);
  return provinceItem.label + cityItem.label + (districtItem?.label || '');
}

function init() {
  startLoading();
  getUserList(searchForm.value).then(res => {
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

<style scoped lang="scss"></style>
