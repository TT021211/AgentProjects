<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="辅料类型">
          <n-select
            v-model:value="searchForm.orderType"
            class="w-180px"
            placeholder="请选择订单类型"
            :options="typeOption"
            clearable
          />
        </n-form-item>
        <n-form-item label="订单编号">
          <n-input v-model:value="searchForm.orderCode" placeholder="请输入辅料型号"></n-input>
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
    <my-card title="销售订单列表">
      <template #right>
        <div>
          <component
            :is="
              useAddBtn(() => {
                dialogFlag = true;
                flag = true;
              })
            "
            style="margin-right: 15px"
          ></component>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :row-key="rowKey"
        :columns="columns"
        :max-height="400"
        :scroll-x="1800"
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      :title="flag ? '新增销售订单' : '编辑销售订单'"
      :show="dialogFlag"
      width="750px"
      @cancel="handleCancel"
      @submit="handleSubmit"
    >
      <template #content>
        <div>
          <n-form
            ref="addFormRef"
            label-placement="left"
            label-width="100px"
            label-align="left"
            :model="addForm"
            :rules="rules"
          >
            <n-grid :cols="24" :x-gap="20">
              <n-form-item-grid-item label="订单类型" :span="11" path="orderType">
                <n-select
                  v-model:value="addForm.orderType"
                  placeholder="请选择订单类型"
                  :options="typeOption"
                  clearable
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item v-if="addForm.orderType === '0'" label="所属客户" :span="11" path="customerId">
                <n-select
                  v-model:value="addForm.customerId"
                  :options="customerOptions"
                  filterable
                  placeholder="请选择所属客户"
                  @update:value="customerHandleChange"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="关联产品" :span="11" path="productId">
                <n-select
                  v-model:value="addForm.productId"
                  :options="productOptions"
                  filterable
                  placeholder="请选择关联的产品"
                  @update:value="
                    (_value, option:any) => {
                      addForm.attr1 = option.batchNumber;
											addForm.artt4 = option.productName;
                    }
                  "
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="重量" :span="11" path="weight">
                <n-input v-model:value="addForm.weight" placeholder="请输入重量"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="单位" :span="11" path="measureId">
                <n-select
                  v-model:value="addForm.measureId"
                  filterable
                  :options="unitOptions"
                  placeholder="请选择单位"
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="11" label="交付日期" path="payTime">
                <n-date-picker v-model:value="addForm.payTime" type="date" placeholder="请选择交付日期" />
              </n-form-item-grid-item>
              <n-form-item-grid-item label="技术要求" :span="11" path="technicalRequirements">
                <n-input v-model:value="addForm.technicalRequirements" placeholder="请输入技术要求"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="标准:" :span="11" path="standardId">
                <n-select
                  v-model:value="addForm.standardId"
                  placeholder="请选择标准"
                  filterable
                  :options="standardList"
                  @update-value="(val, item: any) => {
										addForm.standardName = item.label;
									}"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="备注" :span="11" path="remark">
                <n-input v-model:value="addForm.remark" placeholder="请输入备注"></n-input>
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
    <my-dialog
      title="下发工单"
      :show="workorderFlag"
      height="200px"
      @cancel="workorderHandleCancel"
      @submit="workorderHandleSubmit"
    >
      <template #content>
        <div>
          <n-form
            ref="addWorkorderFormRef"
            label-placement="left"
            label-width="100px"
            label-align="left"
            :model="addWorkorderForm"
            :rules="workorderRules"
          >
            <n-form-item label="工艺路线" :span="12" path="routingId">
              <n-select
                v-model:value="addWorkorderForm.routingId"
                placeholder="请选择工艺路线"
                class="w-180px"
                :options="routingOption"
                clearable
              />
            </n-form-item>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>
<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, h, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage, NTag } from 'naive-ui';
import { ArrowRedo } from '@vicons/ionicons5';
import { getSaleOrderList, deleteSaleOrder, editSaleOrder, addSaleOrder } from '@/service/api/sale/order';
import { addProductWorkOrder } from '@/service/api/plan/productOrder/index';
import { getUserList } from '@/service/api/sale/userManage';
import { getAllUnit } from '@/service/api/md/unit/index';
import { getAllProductList } from '@/service/api/md/list/index';
import { getTechnologyListLine } from '@/service/api/technology/technologyLine';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
import { getDictDataList } from '~/src/service/api/system/dictData';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

type searchType = {
  orderType: string | null;
  orderCode: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
const typeOption = [
  {
    label: `客户订单`,
    value: '0'
  },
  {
    label: `备库订单`,
    value: '1'
  }
];
const routingOption = ref<any[]>([]);
const flag = ref<boolean>(true);
const standardList = ref<any>([]);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  orderType: null,
  orderCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const rules = {
  orderType: [
    {
      required: true,
      message: '请输入订单类型'
    }
  ],
  model: [
    {
      required: true,
      message: '请输入型号'
    }
  ],
  productId: [
    {
      required: true,
      message: '请选择关联的产品'
    }
  ],
  weight: [
    {
      required: true,
      message: '请输入重量'
    }
  ],
  measureId: [
    {
      required: true,
      message: '请选择单位'
    }
  ],
  specification: [
    {
      required: true,
      message: '请输入订单规格'
    }
  ],
  payTime: [
    {
      required: true,
      message: '请选择交付日期'
    }
  ],
  technicalRequirements: [
    {
      required: true,
      message: '请输入技术要求'
    }
  ],
  standardId: [
    {
      required: true,
      message: '请选择标准'
    }
  ],
  remark: [
    {
      required: true,
      message: '请输入备注'
    }
  ]
};
const workorderRules = {
  routingId: [
    {
      required: true,
      message: '请选择工艺路线'
    }
  ]
};
type addFormType = {
  orderType: string | null;
  customerName: string;
  productId: string | null;
  specification: string;
  model: string;
  weight: string;
  measureId: string | null;
  payTime: number | null;
  customerId: string | null;
  technicalRequirements: string;
  standardId: string | null;
  standardName: string;
  remark: string;
  attr1: string;
  artt4: string;
};
type addWorkorderFormType = {
  orderId: string | null;
  orderType: string | null;
  customerName: string;
  orderCode: string;
  productId: string | null;
  productSpecification: string;
  productModel: string;
  weight: string;
  measureId: string | null;
  payTime: number | null;
  routingId: string | null;
  attr1: '';
};
const addForm = ref<addFormType>({
  orderType: null,
  customerName: '',
  productId: null,
  specification: '',
  model: '',
  weight: '',
  measureId: null,
  payTime: null,
  customerId: null,
  technicalRequirements: '',
  standardId: null,
  standardName: '',
  remark: '',
  attr1: '',
  artt4: ''
});
const addWorkorderForm = ref<addWorkorderFormType>({
  orderId: null,
  orderType: null,
  customerName: '',
  orderCode: '',
  productId: null,
  productSpecification: '',
  productModel: '',
  weight: '',
  measureId: null,
  payTime: null,
  routingId: null,
  attr1: ''
});
const addFormRef = ref<FormInst | null>(null);
const addWorkorderFormRef = ref<FormInst | null>(null);
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    orderType: null,
    orderCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  orderType: string | null;
  customerName: string;
  orderCode: string;
  specification: string;
  model: string;
  weight: string;
  measureId: string | null;
  measureName: string;
  payTime: number;
  status: string;
};
function changeType(str: any) {
  if (str === '0') {
    return 'default';
  } else if (str === '1') {
    return 'warning';
  } else if (str === '2' || str === '3') {
    return 'info';
  } else if (str === '4') {
    return 'success';
  } else if (str === '5') {
    return 'error';
  }
  return 'error';
}
function getStatus(status: string) {
  if (status === '0') return '未下发';
  if (status === '1') return '已下发';
  if (status === '2') return '已排产';
  if (status === '3') return '生产中';
  if (status === '4') return '已完成';
  if (status === '5') return '已作废';
  return '';
}
const unitOptions = ref<{ value: string; label: string }[]>([]);
const productOptions = ref<{ value: string; label: string; batchNumber: string }[]>([]);
const customerOptions = ref<{ value: string; label: string }[]>([]);
const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    width: 60,
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '订单类型',
    width: 80,
    key: 'orderType',
    render: (row: any) => (
      <n-tag type={row.orderType === '0' ? 'success' : 'warning'}>
        {row.orderType === '0' ? '客户订单' : '备库订单'}
      </n-tag>
    )
  },
  {
    title: '客户名称',
    width: 70,
    align: 'center',
    key: 'customerName'
  },
  {
    title: '订单编号',
    align: 'center',
    width: 120,
    key: 'orderCode',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '产品名称',
    width: 80,
    align: 'center',
    key: 'artt4',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '产品规格',
    width: 70,
    align: 'center',
    key: 'specification',
    ellipsis: {
      tooltip: true
    }
  },
  // {
  //   title: '产品型号',
  //   width: 120,
  //   align: 'center',
  //   key: 'model'
  // },
  {
    title: '重量',
    width: 60,
    align: 'center',
    key: 'weight'
  },
  {
    title: '备注',
    width: 60,
    align: 'center',
    key: 'remark'
  },
  {
    title: '要求',
    width: 120,
    align: 'center',
    key: 'technicalRequirements',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '单位',
    align: 'center',
    width: 40,
    key: 'measureName'
  },
  {
    title: '交付时间',
    align: 'center',
    width: 80,
    key: 'payTime'
  },
  {
    title: '状态',
    width: 80,
    align: 'center',
    key: 'status',
    render: (row: DataType) => {
      return h(
        NTag,
        {
          type: changeType(row.status)
        },
        () => getStatus(row.status as string)
      );
    }
  },
  {
    title: '操作',
    align: 'center',
    fixed: 'right',
    key: 'actions',
    width: 180,
    render: row => {
      return [
        <n-button color="#8a2be2" size="tiny" class="mr" v-show={row.status === '0'} onClick={() => handleInfo(row)}>
          <ArrowRedo class="mr-1px text-15px w-13px" />
          下发工单
        </n-button>,
        useEditBtn(() => {
          handleEdit(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteSaleOrder(row).then(res => {
            if (res.code === 200) {
              message.success('删除成功');
              init();
            }
          });
        }, 'tiny')
      ];
    }
  }
]);

const checked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};
// 弹窗
const workorderFlag = ref<boolean>(false);
const dialogFlag = ref<boolean>(false);
const handleCancel = () => {
  dialogFlag.value = false;
  addForm.value = {
    orderType: null,
    customerName: '',
    productId: null,
    specification: '',
    model: '',
    weight: '',
    measureId: null,
    payTime: null,
    customerId: null,
    technicalRequirements: '',
    standardId: null,
    standardName: '',
    remark: '',
    attr1: '',
    artt4: ''
  };
};
function customerHandleChange(val, item) {
  addForm.value.customerId = val;
  addForm.value.customerName = item.label;
}
const handleProductCancel = () => {
  workorderFlag.value = false;
  addWorkorderForm.value = {
    orderId: null,
    orderType: null,
    customerName: '',
    orderCode: '',
    productId: null,
    productSpecification: '',
    productModel: '',
    weight: '',
    measureId: null,
    payTime: null,
    routingId: null,
    attr1: ''
  };
};
const workorderHandleCancel = () => {
  workorderFlag.value = false;
  addWorkorderForm.value.orderId = null;
};
function handleInfo(row) {
  addWorkorderForm.value = {
    orderId: row.id,
    orderType: row.orderType,
    customerName: row.customerName,
    orderCode: row.orderCode,
    productId: row.productId,
    productSpecification: row.specification,
    productModel: row.model,
    weight: row.weight,
    measureId: row.measureId,
    payTime: row.payTime,
    routingId: row.routingId,
    attr1: row.attr1
  };
  workorderFlag.value = true;
}
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addSaleOrder(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editSaleOrder(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('修改成功');
            handleCancel();
            init();
          }
        });
      }
    }
  });
}
async function workorderHandleSubmit() {
  await addWorkorderFormRef.value?.validate(errors => {
    if (!errors) {
      addProductWorkOrder([addWorkorderForm.value]).then((res: any) => {
        if (res.code === 200) {
          message.success('下发成功');
          handleProductCancel();
          init();
        }
      });
    }
  });
}
function handleEdit(row) {
  dialogFlag.value = true;
  flag.value = false;
  addForm.value = {
    ...row,
    payTime: new Date(row.payTime as unknown as string) as unknown as null
  };
}

function init() {
  startLoading();
  data.value = [];
  getSaleOrderList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
function getList() {
  getDictDataList({ pageSize: 999, dictType: 'technology_client_norm' }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        standardList.value.push({
          label: item.dictLabel,
          value: item.dictValue
        });
      });
    }
  });
  getTechnologyListLine().then(res => {
    if (res.code === 200) {
      routingOption.value = [];
      res.data.forEach((item: any) => {
        routingOption.value.push({
          label: item.routingName,
          value: item.id
        });
      });
    }
  });
  getAllUnit().then(res => {
    if (res.code === 200) {
      unitOptions.value = [];
      res.data.forEach((item: any) => {
        unitOptions.value.push({
          label: item.measureName,
          value: item.id
        });
      });
    }
  });
  getAllProductList().then(res => {
    if (res.code === 200) {
      productOptions.value = [];
      res.data.forEach((item: any) => {
        productOptions.value.push({
          label: `${item.productName} (${item.batchNumber})`,
          batchNumber: item.batchNumber,
          ...item,
          value: item.id
        });
      });
    }
  });
  getUserList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      customerOptions.value = [];
      res.rows.forEach((item: any) => {
        customerOptions.value.push({
          label: item.clientNick,
          value: item.id
        });
      });
    }
  });
}
onMounted(() => {
  init();
  getList();
});
</script>

<style scoped></style>
