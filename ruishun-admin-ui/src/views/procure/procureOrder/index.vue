<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="物品名称">
          <n-input v-model:value="searchForm.productName" placeholder="请输入物品名称"></n-input>
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
    <my-card title="采购订单列表">
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
        @update-checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      :title="flag ? '新增采购订单' : '编辑采购订单'"
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
            label-width="120px"
            label-align="left"
            :model="addForm"
            :rules="rules"
          >
            <n-grid :cols="24" :x-gap="20">
              <n-form-item-grid-item label="物品名称" :span="10" path="productName">
                <n-input v-model:value="addForm.productName" placeholder="请输入物品名称"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="型号" :span="10">
                <n-input v-model:value="addForm.productSpec" placeholder="请输入型号"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="数量" :span="10" path="procureNumber">
                <n-input v-model:value="addForm.procureNumber" placeholder="请输入数量"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="单位" :span="10" path="measureName">
                <n-input v-model:value="addForm.measureName" placeholder="请输入单位" :min="0"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="计划到货时间" :span="10" path="arriveTime">
                <n-date-picker
                  v-model:formatted-value="addForm.arriveTime"
                  placeholder="请选择计划到货时间"
                  value-format="yyyy-MM-dd"
                  class="w-160px"
                  type="date"
                  clearable
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="10" label="申购日期" path="procureTime">
                <n-date-picker
                  v-model:formatted-value="addForm.procureTime"
                  placeholder="请选择申购日期"
                  value-format="yyyy-MM-dd"
                  class="w-160px"
                  type="date"
                  clearable
                />
              </n-form-item-grid-item>
              <n-form-item-grid-item :span="10" label="申购人" path="procurePerson">
                <n-input v-model:value="addForm.procurePerson" placeholder="请输入申购人"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="性能要求" :span="10">
                <n-input v-model:value="addForm.procureRequire" placeholder="请输入性能要求"></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="用途" :span="10">
                <n-input v-model:value="addForm.procurePurpose" placeholder="请输入用途"></n-input>
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>

    <my-dialog
      v-model:show="showAudit"
      width="500px"
      title="订单审核"
      :show-cancel="true"
      @cancel="closeAudit"
      @submit="confirmAudit"
    >
      <template #content>
        <div class="test">
          <n-form-item label-placement="left" label="流转动作">
            <n-radio-group v-model:value="auditStatus" name="radiogroup" @change="editOpinion">
              <n-space>
                <n-radio value="1">同意</n-radio>
                <n-radio value="2">不同意</n-radio>
              </n-space>
            </n-radio-group>
          </n-form-item>
          <n-form-item label-placement="left" label="流转意见">
            <n-input v-model:value="opinionAudit" type="textarea" placeholder="请输入流转意见"></n-input>
          </n-form-item>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { FormInst, DataTableColumns } from 'naive-ui';
import { useMessage } from 'naive-ui';
import {
  getProcureOrderList,
  deleteProcureOrder,
  editProcureOrder,
  addProcureOrder
} from '@/service/api/procure/order';
import { queryIngredientsProductName } from '@/service/api/md/ingredients/productName';
import { queryIngredientsManufacturer } from '@/service/api/md/ingredients/manufacturer';
import { useAddBtn, useEditBtn, useDelBtn, useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
import { createRequiredFormRule, formatDate } from '~/src/utils';
const { loading, startLoading, endLoading } = useLoading();
const message = useMessage();

// 审核
const showAudit = ref<boolean>(false);
const auditStatus = ref('1');
const opinionAudit = ref('同意');
const auditForm = ref({});
function closeAudit() {
  showAudit.value = false;
  auditStatus.value = '1';
  opinionAudit.value = '同意';
  auditForm.value = {};
  init();
}
function editOpinion() {
  if (auditStatus.value === '1') {
    opinionAudit.value = '同意';
  } else {
    opinionAudit.value = '不同意';
  }
}
// 确认审核
function confirmAudit() {
  editProcureOrder({ ...auditForm.value, attr1: opinionAudit.value, attr2: auditStatus.value }).then(res => {
    if (res.code === 200) {
      window.$message?.success('审核成功');
      closeAudit();
    }
  });
}

type searchType = {
  productName: string;
  pageNum: number;
  pageSize: number;
  total: number;
};

type optionType = {
  label: string;
  value: number;
};
const nameOptions = ref<optionType[]>([]);
const manufacturerOptions = ref<optionType[]>([]);

const flag = ref<boolean>(true);
const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  productName: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
const rules = {
  productName: createRequiredFormRule('请输入物品名称'),
  procureNumber: createRequiredFormRule('请输入数量'),
  measureName: createRequiredFormRule('请输入单位'),
  arriveTime: createRequiredFormRule('请选择计划到货时间'),
  procureTime: createRequiredFormRule('请选择申购日期'),
  procurePerson: createRequiredFormRule('请输入申购人')
};
type addFormType = {
  id?: string;
  productName: string;
  productSpec: string;
  procureNumber: string;
  measureName: string;
  procureRequire: string;
  procurePurpose: string;
  arriveTime: string | null;
  procureTime: string | null;
  procurePerson: string;
  isDeleted: number;
  version: number;
};
const addForm = ref<addFormType>({
  productName: '',
  productSpec: '',
  procureNumber: '',
  measureName: '',
  procureRequire: '',
  procurePurpose: '',
  arriveTime: null,
  procureTime: null,
  procurePerson: '',
  isDeleted: 0,
  version: 0
});
const addFormRef = ref<FormInst | null>(null);
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    productName: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  productName: string;
  productSpec: string;
  procureNumber: string;
  measureName: string;
  procureRequire: string;
  procurePurpose: string;
  arriveTime: string;
  procureTime: string;
  procurePerson: string;
  attr1: string;
  attr2: string;
};
// const measureNameOptions = ref<{ value: string; label: string }[]>([]);
const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  // {
  //   type: 'selection'
  // },
  {
    title: '序号',
    key: 'index',
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '物品名称',
    align: 'center',
    key: 'productName'
  },
  {
    title: '型号',
    align: 'center',
    key: 'productSpec'
  },
  {
    title: '数量',
    align: 'center',
    key: 'procureNumber'
  },
  {
    title: '单位',
    align: 'center',
    key: 'measureName'
  },
  {
    title: '性能要求',
    align: 'center',
    key: 'procureRequire'
  },
  {
    title: '用途',
    align: 'center',
    key: 'procurePurpose'
  },
  {
    title: '申购日期',
    align: 'center',
    key: 'procureTime'
  },
  {
    title: '计划到货时间',
    align: 'center',
    key: 'arriveTime'
  },
  {
    title: '申购人',
    align: 'center',
    key: 'procurePerson'
  },
  {
    title: '审核状态',
    align: 'center',
    key: 'attr1'
  },
  {
    title: '操作',
    align: 'center',
    key: 'actions',
    width: 240,
    render: row => {
      return [
        <n-button
          v-show={!row.attr2}
          size="tiny"
          type="warning"
          class="mr-5px"
          onClick={() => {
            auditForm.value = row;
            showAudit.value = true;
          }}
        >
          <icon-tdesign-edit class="mr-1px text-15px " />
          审核
        </n-button>,
        useEditBtn(() => {
          handleEdit(row);
        }, 'tiny'),
        useDelBtn(() => {
          deleteProcureOrder(row).then(res => {
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
const dialogFlag = ref<boolean>(false);
const handleCancel = () => {
  dialogFlag.value = false;
  addForm.value = {
    productName: '',
    productSpec: '',
    procureNumber: '',
    measureName: '',
    procureRequire: '',
    procurePurpose: '',
    arriveTime: null,
    procureTime: null,
    procurePerson: '',
    isDeleted: 0,
    version: 0
  };
};
async function handleSubmit() {
  await addFormRef.value?.validate(errors => {
    if (!errors) {
      if (flag.value) {
        addProcureOrder(addForm.value).then((res: any) => {
          if (res.code === 200) {
            message.success('添加成功');
            handleCancel();
            init();
          }
        });
      } else {
        editProcureOrder(addForm.value).then((res: any) => {
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
function handleEdit(row) {
  dialogFlag.value = true;
  flag.value = false;
  addForm.value = {
    ...row,
    procureNumber: row.procureNumber.toString()
  };
  addForm.value.arriveTime = formatDate(new Date(row.arriveTime), 'yyyy-MM-dd');
  addForm.value.procureTime = formatDate(new Date(row.procureTime), 'yyyy-MM-dd');
}

function init() {
  startLoading();
  data.value = [];
  getProcureOrderList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  init();
  queryIngredientsProductName().then(res => {
    res.data.forEach((item: any) => {
      nameOptions.value.push({
        label: item.productName,
        value: item.id
      });
    });
  });
  queryIngredientsManufacturer().then(res => {
    res.data.forEach((item: any) => {
      manufacturerOptions.value.push({
        label: item.manufacturerName,
        value: item.id
      });
    });
  });
});
</script>

<style scoped></style>
