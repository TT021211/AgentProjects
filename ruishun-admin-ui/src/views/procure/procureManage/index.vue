<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline label-placement="top" :model="searchForm">
        <n-form-item label="供应商">
          <n-input v-model:value="searchForm.userName" placeholder="请输入供应商"></n-input>
        </n-form-item>
        <n-form-item label="电话">
          <n-input v-model:value="searchForm.userPhone" placeholder="请输入电话"></n-input>
        </n-form-item>
        <n-form-item label="合同编号">
          <n-input v-model:value="searchForm.procureContractCode" placeholder="请输入合同编号"></n-input>
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
              <n-form-item-grid-item :span="1" label="合同编号" path="procureContractCode">
                <n-input
                  v-model:value="addForm.procureContractCode"
                  size="small"
                  placeholder="请输入合同编号"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="产品名称" path="productName">
                <n-input v-model:value="addForm.productName" size="small" placeholder="请输入产品名称"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="规格型号" path="productCode">
                <n-input v-model:value="addForm.productCode" size="small" placeholder="请输入规格型号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="数量" path="procureNumber">
                <n-input v-model:value="addForm.procureNumber" size="small" placeholder="请输入数量"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="单位" path="prucureUnit">
                <n-input v-model:value="addForm.prucureUnit" size="small" placeholder="请输入单位"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="单价" path="procurePrice">
                <n-input v-model:value="addForm.procurePrice" size="small" placeholder="请输入单价"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="金额" path="procureMoney">
                <n-input v-model:value="addForm.procureMoney" size="small" placeholder="请输入金额"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="需求日期" path="procureDate">
                <n-date-picker
                  v-model:formatted-value="addForm.procureDate"
                  placeholder="请选择需求日期"
                  value-format="yyyy-MM-dd"
                  class="w-160px"
                  type="date"
                  clearable
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="备注" path="remake">
                <n-input v-model:value="addForm.remake" size="small" placeholder="请输入备注"></n-input>
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
            <h3 class="titleInfo">供应商信息</h3>
            <n-grid :cols="3" :x-gap="10" :y-gap="10">
              <n-form-item-grid-item :span="1" label="供应商" path="userName">
                <n-input v-model:value="addForm.userName" size="small" placeholder="请输入供应商"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="供应商地址" path="userAddress">
                <n-input v-model:value="addForm.userAddress" size="small" placeholder="请输入供应商地址"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="供应商法人" path="userLegal">
                <n-input v-model:value="addForm.userLegal" size="small" placeholder="请输入供应商法人"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="供应商电话" path="userPhone">
                <n-input v-model:value="addForm.userPhone" size="small" placeholder="请输入供应商电话"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="供应商传真号" path="userFax">
                <n-input v-model:value="addForm.userFax" size="small" placeholder="请输入供应商传真号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="开户银行" size="small" path="userBank">
                <n-input v-model:value="addForm.userBank" placeholder="请输入开户银行"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="供应商账号" path="userAccount">
                <n-input v-model:value="addForm.userAccount" size="small" placeholder="请输入供应商账号"></n-input>
              </n-form-item-grid-item>
            </n-grid>
            <h3 class="titleInfo">需方信息</h3>
            <n-grid :cols="3" :x-gap="10" :y-gap="10">
              <n-form-item-grid-item :span="1" label="需方" path="demandName">
                <n-input v-model:value="addForm.demandName" size="small" placeholder="请输入需方"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="需方地址" path="demandAddress">
                <n-input v-model:value="addForm.demandAddress" size="small" placeholder="请输入需方地址"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="需方法人" path="demandLegal">
                <n-input v-model:value="addForm.demandLegal" size="small" placeholder="请输入需方法人"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="需方电话" path="demandPhone">
                <n-input v-model:value="addForm.demandPhone" size="small" placeholder="请输入需方电话"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="需方传真号" path="demandFax">
                <n-input v-model:value="addForm.demandFax" size="small" placeholder="请输入需方传真号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="开户银行" path="demandBank">
                <n-input v-model:value="addForm.demandBank" size="small" placeholder="请输入开户银行"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="1" label="需方账号" path="demandAccount">
                <n-input v-model:value="addForm.demandAccount" size="small" placeholder="请输入需方账号"></n-input>
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
            <n-form-item-grid-item label-placement="left" :span="1" label="供应商:">
              <n-space>{{ contractData.userName }}</n-space>
            </n-form-item-grid-item>
            <n-form-item-grid-item label-placement="left" :span="1" label="编号:">
              <n-space>{{ contractData.procureContractCode }}</n-space>
            </n-form-item-grid-item>
            <n-form-item-grid-item label-placement="left" :span="1" label="需方:">
              <n-space>{{ contractData.demandName }}</n-space>
            </n-form-item-grid-item>
          </n-grid>
          <n-data-table
            style="margin-top: -20px"
            :loading="loading"
            :data="contractList"
            :columns="contractColumns"
          ></n-data-table>
          <div style="border: 1px solid #f3f3f3; padding: 12px; text-align: left; white-space: pre-wrap; margin: 5px 0">
            {{ contractData.contractNote }}
          </div>
          <div class="info">
            <div class="user">
              <h3 class="userTitle">供应商</h3>
              <p>单位名称：{{ contractData.userName }}</p>
              <p>单位地址：{{ contractData.userAddress }}</p>
              <p>法定代表人：{{ contractData.userLegal }}</p>
              <p>电话：{{ contractData.userPhone }}</p>
              <p>传真号：{{ contractData.userFax }}</p>
              <p>开户银行：{{ contractData.userBank }}</p>
              <p>账号：{{ contractData.userAccount }}</p>
            </div>
            <div class="user">
              <h3 class="userTitle">需方</h3>
              <p>单位名称：{{ contractData.demandName }}</p>
              <p>单位地址：{{ contractData.demandAddress }}</p>
              <p>法定代表人：{{ contractData.demandLegal }}</p>
              <p>电话：{{ contractData.demandPhone }}</p>
              <p>传真号：{{ contractData.demandFax }}</p>
              <p>开户银行：{{ contractData.demandBank }}</p>
              <p>账号：{{ contractData.demandAccount }}</p>
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
import { formatDate, createRequiredFormRule } from '@/utils';
import { getUserList } from '@/service/api/sale/userManage';
import { useEditBtn, useDelBtn, useInfoBtn } from '@/hooks/common/useBtn';
import { useLoading, useBoolean } from '~/src/hooks';
import {
  getProcureContractList,
  addProcureContractList,
  updateProcureContractList,
  delProcureContractList
} from '~/src/service/api/procure/contact';
const { loading, startLoading, endLoading } = useLoading();
const { bool: flag, setFalse: closeDialog, setTrue: openDialog } = useBoolean();
const message = useMessage();

const contractDetail = ref<boolean>(false);
const contractData = ref<any>({});
function contractCancel() {
  contractDetail.value = false;
}
const contractList = ref<Procure.contractMange.columns[]>([]);
const contractColumns: Ref<DataTableColumns<Procure.contractMange.columns>> = ref([
  {
    title: '产品名称',
    titleAlign: 'center',
    align: 'center',
    key: 'productName',
    width: 80
  },
  {
    title: '规格型号',
    titleAlign: 'center',
    align: 'center',
    key: 'productCode',
    width: 140
  },
  {
    title: '数量',
    titleAlign: 'center',
    align: 'center',
    key: 'procureNumber',
    width: 150
  },
  {
    title: '单位',
    key: 'prucureUnit',
    titleAlign: 'center',
    align: 'center',
    width: 120
  },
  {
    title: '单价',
    titleAlign: 'center',
    align: 'center',
    key: 'procurePrice',
    width: 120
  },
  {
    title: '金额',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'procureMoney'
  },
  {
    title: '需求日期',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'procureDate'
  },
  {
    title: '备注',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'remake'
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
const addForm = ref<Procure.contractMange.addForm>({
  userName: '',
  userAddress: '',
  userLegal: '',
  userPhone: '',
  userFax: '',
  userBank: '',
  userAccount: '',
  demandName: '',
  demandAddress: '',
  demandLegal: '',
  demandPhone: '',
  demandFax: '',
  demandBank: '',
  demandAccount: '',
  procureContractCode: '',
  productName: '',
  productCode: '',
  procureNumber: '',
  prucureUnit: '',
  procurePrice: '',
  procureMoney: '',
  procureDate: null,
  remake: '',
  contractNote: '',
  isDeleted: 0,
  version: 0
});

const rules = {
  userName: createRequiredFormRule('请输入供应商'),
  userAddress: createRequiredFormRule('请输入供应商地址'),
  // userLegal: createRequiredFormRule('请输入供应商法人'),
  // userPhone: [
  //   {
  //     required: true,
  //     message: '请输入供应商电话',
  //     trigger: 'change'
  //   },
  //   {
  //     pattern: /^1[3456789]\d{9}$/,
  //     message: '手机号格式错误',
  //     trigger: 'change'
  //   }
  // ],
  // userFax: createRequiredFormRule('请输入供应商传真号'),
  // userBank: createRequiredFormRule('请输入开户银行'),
  // userAccount: createRequiredFormRule('请输入供应商账号'),
  demandName: createRequiredFormRule('请输入需方'),
  demandAddress: createRequiredFormRule('请输入需方地址'),
  // demandLegal: createRequiredFormRule('请输入需方法人'),
  // demandPhone: [
  //   {
  //     required: true,
  //     message: '请输入需方电话',
  //     trigger: 'change'
  //   },
  //   {
  //     pattern: /^1[3456789]\d{9}$/,
  //     message: '手机号格式错误',
  //     trigger: 'change'
  //   }
  // ],
  // demandFax: createRequiredFormRule('请输入需方传真号'),
  // demandBank: createRequiredFormRule('请输入开户银行'),
  // demandAccount: createRequiredFormRule('请输入需方账号'),
  procureContractCode: createRequiredFormRule('请输入合同编号'),
  productName: createRequiredFormRule('请输入产品名称'),
  productCode: createRequiredFormRule('请输入规格型号'),
  procureNumber: createRequiredFormRule('请输入数量'),
  prucureUnit: createRequiredFormRule('请输入单位'),
  procurePrice: createRequiredFormRule('请输入单价'),
  procureMoney: createRequiredFormRule('请输入金额'),
  procureDate: createRequiredFormRule('请输入需求日期'),
  // remake: createRequiredFormRule('请输入备注'),
  contractNote: createRequiredFormRule('请输入合同说明')
};
const searchForm = ref<Procure.contractMange.searchContractForm>({
  userName: '',
  userPhone: '',
  procureContractCode: '',
  productName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const data = ref<Procure.contractMange.columns[]>([]);
const columns: Ref<DataTableColumns<Procure.contractMange.columns>> = ref([
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
    title: '供应商',
    key: 'userName',
    width: 140,
    titleAlign: 'center',
    align: 'center',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '供应商电话',
    key: 'userPhone',
    width: 120,
    titleAlign: 'center',
    align: 'center'
  },
  {
    title: '合同编号',
    titleAlign: 'center',
    align: 'center',
    key: 'procureContractCode',
    width: 140
  },
  {
    title: '产品名称',
    titleAlign: 'center',
    align: 'center',
    key: 'productName',
    width: 80
  },
  {
    title: '规格型号',
    titleAlign: 'center',
    align: 'center',
    key: 'productCode',
    width: 140
  },
  {
    title: '数量',
    titleAlign: 'center',
    align: 'center',
    key: 'procureNumber',
    width: 80
  },
  {
    title: '单位',
    key: 'prucureUnit',
    titleAlign: 'center',
    align: 'center',
    width: 80
  },
  {
    title: '单价',
    titleAlign: 'center',
    align: 'center',
    key: 'procurePrice',
    width: 100
  },
  {
    title: '金额',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'procureMoney'
  },
  {
    title: '需求日期',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'procureDate'
  },
  {
    title: '备注',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'remake'
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
    procureContractCode: '',
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
    demandName: '',
    demandAddress: '',
    demandLegal: '',
    demandPhone: '',
    demandFax: '',
    demandBank: '',
    demandAccount: '',
    procureContractCode: '',
    productName: '',
    productCode: '',
    procureNumber: '',
    prucureUnit: '',
    procurePrice: '',
    procureMoney: '',
    procureDate: null,
    remake: '',
    contractNote: '',
    isDeleted: 0,
    version: 0
  };
  closeDialog();
}
function editUser(row) {
  addForm.value = row;
  editFlag.value = true;
  addForm.value.procureDate = formatDate(new Date(row.procureDate), 'yyyy-MM-dd');
  openDialog();
}
function deleteById(row: Procure.contractMange.columns) {
  delProcureContractList(row).then(res => {
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
        updateProcureContractList(addForm.value).then(res => {
          if (res.code === 200) {
            message.success('修改成功！');
            cancel();
            init();
          }
        });
      } else {
        addProcureContractList(addForm.value).then(res => {
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
  getProcureContractList(searchForm.value).then(res => {
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
.userTitle {
  font-size: 18px;
  font-weight: bold;
  text-align: center;
}
.user {
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
