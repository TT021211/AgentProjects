<template>
  <div>
    <my-card search title="搜索条件">
      <n-form inline>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="采购列表">
      <template #right>
        <div>
          <n-button type="primary" size="small" style="margin-right: 10px" @click="openAddOrEditForm(0)">
            <icon-ic-round-plus class="mr-4px text-20px" />
            新增
          </n-button>
        </div>
        <CxColumns v-model:columns="columns" size="small" />
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination></my-pagination>
    </my-card>
    <my-dialog
      :show="show"
      :title="!addProcureForm.id ? '新增采购单' : '编辑采购单'"
      width="1200px"
      @cancel="show = false"
      @submit="submitAddOrEditProcure"
    >
      <template #content>
        <div>
          <n-form ref="formRef" label-placement="left" label-width="100px" :model="addProcureForm" :rules="rules">
            <n-form-item label="采购单号:" path="purchaseCode" class="w-250px" style="float: left">
              <n-input
                v-model:value="addProcureForm.purchaseCode"
                placeholder="请输入采购单号"
                :disabled="flag"
                :style="{ width: formItemWidth }"
              />
            </n-form-item>
            <n-form-item label="采购厂家:" path="purchaseFactory" class="w-250px" style="float: left">
              <n-select
                v-model:value="addProcureForm.purchaseFactoryId"
                placeholder="请选择采购厂家"
                class="w-180px"
                :options="supplierList"
                :style="{ width: formItemWidth }"
                :disabled="flag"
                @update:value="handleUpdateValue"
              ></n-select>
            </n-form-item>
            <div>
              <n-button v-if="buttonShow === 1" type="primary" size="medium" style="margin-left: 20px" @click="sureAdd">
                确定
              </n-button>
            </div>
          </n-form>
          <my-card title="采购详情" :appear="false">
            <template #right>
              <div>
                <n-button
                  v-if="buttonShow === 0"
                  type="primary"
                  size="small"
                  style="margin-left: 10px"
                  @click="addBuyDetails"
                >
                  <icon-ic-round-plus class="mr-4px text-20px" />
                  新增
                </n-button>
              </div>
            </template>
            <n-data-table :loading="loading" :columns="detailColumns" :data="detailArrays"></n-data-table>
          </my-card>
        </div>
      </template>
    </my-dialog>

    <my-dialog
      v-model:show="addShow"
      title="新增采购详情"
      width="800px"
      @cancel="addShow = false"
      @submit="addOrderDetail"
    >
      <template #content>
        <div>
          <n-form
            ref="formModel"
            label-placement="left"
            label-width="100px"
            :model="addProcure"
            :rules="detailRules"
            style="width: 700px"
            label-align="left"
          >
            <n-grid x-gap="12" :cols="2">
              <n-form-item-grid-item label="模具编码" path="moldCode">
                <n-input
                  v-model:value="addProcure.moldCode"
                  placeholder="请输入模具编号"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="规格" path="purchaseSpec">
                <n-input
                  v-model:value="addProcure.purchaseSpec"
                  placeholder="请输入规格"
                  :style="{ width: formItemWidth }"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="公差" path="purchaseTolerance">
                <n-input-number
                  v-model:value="addProcure.purchaseTolerance"
                  placeholder="请输入公差"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="采购材料" path="purchaseMaterial">
                <n-input
                  v-model:value="addProcure.purchaseMaterial"
                  placeholder="请输入采购材料"
                  :style="{ width: formItemWidth }"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="采购类型" path="purchaseType">
                <n-input
                  v-model:value="addProcure.purchaseType"
                  placeholder="请输入采购类型"
                  :style="{ width: formItemWidth }"
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
import type { DataTableColumns, FormInst } from 'naive-ui';
import { useMessage } from 'naive-ui';
import { useLoading } from '@/hooks';
import { createRequiredFormRule } from '@/utils';
import { getPurchaseList, addPurchaseInsertBatch } from '@/service/api/moid/purchase/index';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { getmoldSupplierList } from '~/src/service/api/moid/supplier/index';

const searchForm = ref<purchase.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const addProcureForm = ref<purchase.addForm>({
  id: null,
  purchaseCode: '',
  moldCode: '',
  purchaseSpec: '',
  purchaseTolerance: null,
  purchaseMaterial: '',
  purchaseType: '',
  status: '',
  purchaseFactory: '',
  purchaseFactoryId: null
});

const addProcure = ref<{
  moldCode: string;
  purchaseSpec: string;
  purchaseTolerance: number | null;
  purchaseMaterial: string;
  purchaseType: string;
}>({
  moldCode: '',
  purchaseSpec: '',
  purchaseTolerance: null,
  purchaseMaterial: '',
  purchaseType: ''
});

const { loading, startLoading, endLoading } = useLoading();
const addOrEditType = ref<number>(0);
const show = ref<boolean>(false);
const flag = ref<boolean>(false);
const addShow = ref<boolean>(false);
const formItemWidth = ref<string>('260px');
const buttonShow = ref<number>(1);
const formRef = ref<FormInst | null>(null);
const formModel = ref<FormInst | null>(null);
const supplierList = ref<{ label: string; value: number }[]>([]);
const message = useMessage();
const rules = {
  purchaseCode: createRequiredFormRule('请输入编号'),
  purchaseFactoryId: createRequiredFormRule('请选择采购厂家')
};

const detailRules = {
  moldCode: createRequiredFormRule('请输入编号'),
  purchaseSpec: createRequiredFormRule('请输入规格'),
  purchaseTolerance: createRequiredFormRule('请输入公差'),
  purchaseMaterial: createRequiredFormRule('请输入材质'),
  purchaseType: createRequiredFormRule('请输入采购类型')
};

const data = ref<purchase.TableList[]>([
  {
    id: 1,
    purchaseCode: '2023-11-22',
    moldCode: '20231122',
    purchaseSpec: '0.001',
    purchaseTolerance: 0.001,
    purchaseMaterial: '高金',
    purchaseType: '拉丝模具',
    status: '待采购',
    purchaseFactory: 'xxxx',
    inboundStatus: 'xxx',
    createTime: '2023-11-22',
    createBy: 'xxx'
  },
  {
    id: 1,
    purchaseCode: '2023-12-22',
    moldCode: '20231222',
    purchaseSpec: '0.001',
    purchaseTolerance: 0.015,
    purchaseMaterial: '聚金',
    purchaseType: '拉丝模具',
    status: '已采购',
    purchaseFactory: 'xxxx',
    inboundStatus: 'xxx',
    createTime: '2023-11-22',
    createBy: 'xxx'
  }
]);

const columns: Ref<DataTableColumns<purchase.TableList>> = ref([
  {
    title: '采购订单号',
    key: 'purchaseCode',
    align: 'center',
    width: 100
  },
  {
    title: '模具编码',
    key: 'moldCode',
    align: 'center',
    width: 100
  },
  {
    title: '采购规格',
    key: 'purchaseSpec',
    align: 'center',
    width: 100
  },
  {
    title: '采购公差',
    key: 'purchaseTolerance',
    align: 'center',
    width: 100
  },
  {
    title: '采购材料',
    key: 'purchaseMaterial',
    align: 'center',
    width: 100
  },
  {
    title: '采购类型',
    key: 'purchaseType',
    align: 'center',
    width: 100
  },
  {
    title: '状态',
    key: 'status',
    align: 'center',
    width: 100
  },
  {
    title: '采购厂家',
    key: 'purchaseFactory',
    align: 'center',
    width: 100
  },
  {
    title: '创建人',
    key: 'createBy',
    align: 'center',
    width: 100
  },
  {
    title: '创建时间',
    key: 'createTime',
    align: 'center',
    width: 100
  }
]);

const detailArrays = ref<purchase.detailArrays[]>([]);

const detailColumns: Ref<DataTableColumns<purchase.detailArrays>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70
    // render: (row, index) => {
    //   // log
    //   return index;
    // }
  },
  {
    title: '采购订单号',
    key: 'purchaseCode',
    align: 'center',
    width: 120
  },
  {
    title: '模具编码',
    key: 'moldCode',
    align: 'center',
    width: 120
  },
  {
    title: '采购规格',
    key: 'purchaseSpec',
    align: 'center',
    width: 120
  },
  {
    title: '采购公差',
    key: 'purchaseTolerance',
    align: 'center',
    width: 120
  },
  {
    title: '采购材料',
    key: 'purchaseMaterial',
    align: 'center',
    width: 120
  },
  {
    title: '采购类型',
    key: 'purchaseType',
    align: 'center',
    width: 120
  },
  {
    title: '采购厂家',
    key: 'purchaseFactory',
    align: 'center',
    width: 120
  },
  {
    title: '操作',
    key: 'action',
    align: 'center',
    width: 120
    // render: () => {}
  }
]);

function openAddOrEditForm(typeNumber: number) {
  addOrEditType.value = typeNumber;
  show.value = true;
  flag.value = false;
  buttonShow.value = 1;
  if (addOrEditType.value === 0) {
    detailArrays.value = [];
    addProcureForm.value = {
      id: null,
      purchaseCode: '',
      moldCode: '',
      purchaseSpec: '',
      purchaseTolerance: null,
      purchaseMaterial: '',
      purchaseType: '',
      status: '',
      purchaseFactory: '',
      purchaseFactoryId: null
    };
  } else {
    console.log('编辑');
  }
}

function addBuyDetails() {
  addProcure.value = {
    moldCode: '',
    purchaseSpec: '',
    purchaseTolerance: null,
    purchaseMaterial: '',
    purchaseType: ''
  };
  addShow.value = true;
}

function addOrderDetail() {
  if (!formModel.value) return;
  formModel.value.validate(errors => {
    if (!errors) {
      flag.value = true;
      buttonShow.value = 0;
      detailArrays.value.push({
        purchaseCode: addProcureForm.value.purchaseCode,
        purchaseFactory: addProcureForm.value.purchaseFactory,
        moldCode: addProcure.value.moldCode,
        purchaseSpec: addProcure.value.purchaseSpec,
        purchaseTolerance: addProcure.value.purchaseTolerance,
        purchaseMaterial: addProcure.value.purchaseMaterial,
        purchaseType: addProcure.value.purchaseType,
        status: '0'
      });
      addShow.value = false;
    }
  });
}

function submitAddOrEditProcure() {
  if (addOrEditType.value === 0) {
    if (detailArrays.value.length === 0) {
      message.warning('请添加采购信息');
      return;
    }
    addPurchaseInsertBatch(detailArrays.value).then(res => {
      if (res.code === 200) {
        message.success('添加成功');
        show.value = false;
        init();
      }
    });
  } else {
    console.log('编辑');
  }
}

function sureAdd() {
  if (!formRef.value) return;
  formRef.value.validate(errors => {
    if (!errors) {
      flag.value = true;
      buttonShow.value = 0;
    }
  });
}

function handleUpdateValue(value: string, option: { label: string; value: number }) {
  console.log('value', value);
  addProcureForm.value.purchaseFactory = option.label;
}

function search() {
  init();
}
function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}

function getList() {
  getmoldSupplierList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        supplierList.value.push({
          label: item.supplierName,
          value: item.moldSupplierId
        });
      });
    }
  });
}

function init() {
  startLoading();
  getPurchaseList(searchForm.value).then(res => {
    endLoading();
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
    }
  });
}

onMounted(() => {
  init();
  getList();
});
</script>

<style lang="scss" scoped></style>
