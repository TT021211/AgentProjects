<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <!-- <n-form-item label="日期">
          <n-date-picker v-model:formatted-value="forModel.inDate" type="datetime" :style="{ width: formItemWidth }" />
        </n-form-item> -->
        <n-form-item label="出入库类型">
          <n-select
            v-model:value="searchForm.status"
            :options="options"
            placeholder="请选择出入库类型"
            :style="{ width: formItemWidth }"
          ></n-select>
        </n-form-item>
        <n-form-item label="贸易商" path="ingredientsId">
          <n-select
            v-model:value="searchForm.ingredientsId"
            placeholder="请选择贸易商"
            :filterable="true"
            :options="merchantList"
            :style="{ width: formItemWidth }"
          ></n-select>
        </n-form-item>
        <n-form-item label="品名" path="productId">
          <n-select
            v-model:value="searchForm.productId"
            :style="{ width: formItemWidth }"
            placeholder="选择品名"
            :filterable="true"
            :options="productNameList"
          ></n-select>
        </n-form-item>
        <n-form-item label="规格" path="itemId">
          <n-select
            v-model:value="searchForm.itemId"
            placeholder="选择规格"
            :filterable="true"
            :options="specificationList"
            :style="{ width: formItemWidth }"
          ></n-select>
        </n-form-item>
        <n-form-item>
          <component
            :is="
              useSearchBtn(
                () => {
                  search();
                },
                () => {
                  reset();
                }
              )
            "
          />
        </n-form-item>
      </n-form>

      <n-form inline>
        <n-form-item label="批号">
          <n-input
            v-model:value="searchForm.lotNumber"
            placeholder="请输入批号"
            :style="{ width: formItemWidth }"
          ></n-input>
        </n-form-item>
        <n-form-item label="合计件数">
          <n-input-number
            v-model:value="totalNumber"
            :disabled="true"
            :style="{ width: formItemWidth }"
          ></n-input-number>
        </n-form-item>
        <n-form-item label="合计净重">
          <n-input-number v-model:value="netWeight" :disabled="true" :style="{ width: formItemWidth }"></n-input-number>
        </n-form-item>
        <n-form-item label="合计皮重">
          <n-input-number
            v-model:value="tareNumber"
            :disabled="true"
            :style="{ width: formItemWidth }"
          ></n-input-number>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card :title="title">
      <template #right>
        <div style="display: flex">
          <n-button type="info" size="small" style="margin-right: 10px" @click="lookReport">查看报表</n-button>
          <!-- <n-button type="error" size="small" style="margin-right: 10px" :disabled="checkedRowKeys.length === 0">
            批量错误处理
          </n-button> -->
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        v-model:checked-row-keys="checkedRowKeys"
        :loading="loading"
        :columns="columns"
        :data="data"
        :row-key="rowKey"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
        @update:checked-row-keys="handleCheck"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>

    <my-dialog v-model:show="editShow" title="编辑" width="800px" @cancel="editShow = false" @submit="submitEdit">
      <template #content>
        <div>
          <n-form
            ref="formModelRef"
            style="width: 700px"
            :model="editFormModel"
            label-placement="left"
            label-width="90px"
            :rules="rules"
          >
            <n-grid :cols="2" :x-gap="20">
              <n-form-item-grid-item label="贸易商" path="ingredientsId">
                <n-select
                  v-model:value="editFormModel.ingredientsId"
                  placeholder="请选择贸易商"
                  :filterable="true"
                  :options="merchantList"
                  :style="{ width: formItemWidth }"
                  @update-value="(val, item: any) => {
                      editFormModel.ingredientsName = item.label
                  }"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="品名" path="productId">
                <n-select
                  v-model:value="editFormModel.productId"
                  :style="{ width: formItemWidth }"
                  placeholder="选择品名"
                  :filterable="true"
                  :options="productNameList"
                  @update-value="(val, item: any) => {
                      editFormModel.productName = item.label
                  }"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="规格" path="itemId">
                <n-select
                  v-model:value="editFormModel.itemId"
                  placeholder="选择规格"
                  :filterable="true"
                  :options="specificationList"
                  :style="{ width: formItemWidth }"
                  @update-value="(val, item: any) => {
                      editFormModel.itemSpecification = item.label
                  }"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="材料厂家" path="manufacturerId">
                <n-select
                  v-model:value="editFormModel.manufacturerId"
                  placeholder="选择材料厂家"
                  :filterable="true"
                  :options="manufacturerList"
                  @update-value="(val, item: any) => {
                      editFormModel.manufacturerName = item.label
                  }"
                ></n-select>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="批号" path="lotNumber">
                <n-input
                  v-model:value="editFormModel.lotNumber"
                  placeholder="请输入批号"
                  :style="{ width: formItemWidth }"
                ></n-input>
              </n-form-item-grid-item>
              <n-form-item-grid-item label="料号" path="partNumber">
                <n-input
                  v-model:value="editFormModel.partNumber"
                  placeholder="请输入料号"
                  :style="{ width: formItemWidth }"
                ></n-input>
              </n-form-item-grid-item>
            </n-grid>
          </n-form>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
defineOptions({
  name: 'RawMaterial'
});
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns, FormInst } from 'naive-ui';
import { useMessage, useDialog } from 'naive-ui';
import { createRequiredFormRule } from '@/utils';
import { useSocketStore } from '@/store/modules/socket/index';
import { dataTableConfig } from '@/config/dataTableConfig';
import { getMaterialList } from '@/service/api/md/itemlist/index';
import { getIngredientsTradersList } from '@/service/api/md/ingredients/traders';
import { getIngredientsProductNameList } from '@/service/api/md/ingredients/productName';
import { getIngredientsManufacturerList } from '@/service/api/md/ingredients/manufacturer';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import { getIngredientsLog, deleteIngredientsLogMistake, editIngredientsLog } from '~/src/service/api/wms/rawMaterial';
import { formatDate } from '~/src/utils/form/rule';
import { serviceEnv } from '~/.env-config';
const { loading, startLoading, endLoading } = useLoading();
const title = ref<string>('原料出入库');
const socketStore = useSocketStore();
const searchForm = ref<rawMaterial1.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  // ingredientsName: '',
  status: null,
  lotNumber: '',
  ingredientsId: null,
  productId: null,
  itemId: null,
  'params[beginTime]': null,
  'params[endTime]': null
  // productName: ''
});
const options = [
  {
    label: `入库`,
    value: '0'
  },
  {
    label: `出库`,
    value: '1'
  }
];
const formModelRef = ref<FormInst | null>(null);
const message = useMessage();
const dialog = useDialog();
const formItemWidth = ref<string>('200px');
const totalNumber = ref<any>(0);
const netWeight = ref<any>(0);
const tareNumber = ref<any>(0);
const editShow = ref<boolean>(false);
const checkedRowKeys = ref<Array<string | number>>([]);
const range = ref<[number, number] | null>(null);
// const forModel = ref<{ inDate: string | null }>({
//   inDate: null
// });
const editFormModel = ref<any>({});
// const printRules = {
//   socketIp: createRequiredFormRule('请选择打印服务')
// };
const specificationList = ref<{ label: string; value: number }[]>([]);
const merchantList = ref<{ label: string; value: number }[]>([]);
const productNameList = ref<{ label: string; value: number }[]>([]);
const manufacturerList = ref<{ label: string; value: number }[]>([]);
// const showDialog = ref<boolean>(false);
const rowKey = (row: any) => row.id;

const rules = {
  ingredientsId: createRequiredFormRule('请选择贸易商'),
  productId: createRequiredFormRule('请选择品名'),
  itemId: createRequiredFormRule('请选择规格'),
  manufacturerId: createRequiredFormRule('请选择材料厂家'),
  lotNumber: createRequiredFormRule('请输入批号'),
  partNumber: createRequiredFormRule('请输入料号')
};

const data = ref<rawMaterial1.tabelList[]>([]);

const columns: Ref<DataTableColumns<rawMaterial1.tabelList>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '二维码条号',
    align: 'center',
    key: 'codeSn',
    width: 200
  },
  {
    title: '贸易商',
    align: 'center',
    width: 100,
    key: 'ingredientsName'
  },
  {
    title: '品名',
    align: 'center',
    width: 100,
    key: 'productName'
  },
  {
    title: '规格',
    align: 'center',
    width: 100,
    key: 'itemSpecification'
  },
  {
    title: '批号',
    align: 'center',
    width: 140,
    key: 'lotNumber'
  },
  {
    title: '料号',
    align: 'center',
    width: 140,
    key: 'partNumber'
  },

  {
    title: '毛重',
    align: 'center',
    width: 100,
    key: 'grossWeight'
  },

  {
    title: '净重',
    align: 'center',
    width: 100,
    key: 'netWeight'
  },

  {
    title: '皮重',
    align: 'center',
    width: 100,
    key: 'tareWeight'
  },
  {
    title: '单位',
    width: 100,
    align: 'center',
    key: 'measureName'
  },
  {
    title: '材料厂家',
    align: 'center',
    width: 120,
    key: 'manufacturerName'
  },
  {
    title: '状态',
    align: 'center',
    width: 100,
    key: 'status',
    render: (row: any) => {
      let colorText = '';
      let showText = '';
      let showTag = true;
      switch (row.status) {
        case '0':
          colorText = 'success';
          showText = '入库';
          break;
        case '1':
          colorText = 'error';
          showText = '出库';
          break;
        default:
          showTag = false;
          break;
      }
      return (
        <n-tag v-show={showTag} type={colorText}>
          {showText}
        </n-tag>
      );
    }
  },
  {
    title: '出入库时间',
    align: 'center',
    key: 'createTime',
    width: 180
  },
  {
    title: '操作',
    width: 220,
    titleAlign: 'center',
    align: 'center',
    key: 'action',
    fixed: 'right',
    render: (row: any) => {
      return (
        <n-space justify={'center'}>
          <n-button size="small" type="info" onClick={() => openSelectSocket(row)}>
            补打标签
          </n-button>
          <n-button size="small" type="warning" onClick={() => editRow(row)}>
            编辑
          </n-button>
          <n-button
            type="error"
            size="small"
            v-show={row.status === '0'}
            onClick={() => handleDeleteItemType(deleteIngredientsLogMistake, row.id)}
          >
            删除
          </n-button>
        </n-space>
      );
    }
  }
]);

function editRow(row: rawMaterial1.tabelList) {
  editShow.value = true;
  editFormModel.value = row;
}

function submitEdit() {
  if (!formModelRef.value) return;
  formModelRef.value.validate(errors => {
    if (!errors) {
      editIngredientsLog(editFormModel.value).then(res => {
        if (res.code === 200) {
          message.success('修改成功');
          editShow.value = false;
          init();
        }
      });
    }
  });
}

function lookReport() {
  let url = `${serviceEnv.prod.reportServerUrl}/jmreport/view/1019825464491180032`;
  let searchValue = '';
  let isAddQuestionMask = false;
  const filterData = ['pageNum', 'pageSize', 'total', 'params[beginTime]', 'params[endTime]'];
  for (const key in searchForm.value) {
    if (searchForm.value[key] && !filterData.includes(key)) {
      if (!isAddQuestionMask) {
        searchValue += `?${key}=${searchForm.value[key]}`;
        isAddQuestionMask = true;
      } else {
        searchValue += `&${key}=${searchForm.value[key]}`;
      }
    }
  }
  if (range.value !== null) {
    const beginTime = formatDate(new Date(range.value[0]), 'yyyy-MM-dd');
    const endTime = formatDate(new Date(range.value[1]), 'yyyy-MM-dd');
    if (isAddQuestionMask) {
      searchValue += `&beginTime=${beginTime}&endTime=${endTime}`;
    } else {
      searchValue = `?beginTime=${beginTime}&endTime=${endTime}`;
    }
  }
  //  if (forModel.value.inDate) {
  //    if (isAddQuestionMask) {
  //      searchValue += `&inDate=${forModel.value.inDate}`;
  //    } else {
  //      searchValue = `?inDate=${forModel.value.inDate}`;
  //    }
  //  }
  url += searchValue;
  window.open(url, '_blank');
}

const sleep = (func: any, params: any) =>
  new Promise(resolve => {
    func(params).then(res => {
      resolve(res);
    });
  });

const handleCheck = (e, val) => {
  // eslint-disable-next-line no-console
  console.log('e ==> ', e);
  // eslint-disable-next-line no-console
  console.log('val ==> ', val);
  // checkedRowKeys.value = [];
  // val.forEach(item => {
  //   if (item.status) {
  //     if (item.status === '0') {
  //     checkedRowKeys.value.push(item.id)
  //   }
  //   }

  // });
};

function handleDeleteItemType(func: any, params: any): void {
  const d = dialog.warning({
    title: '提示',
    content: '确认要进行删除吗?',
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      d.loading = true;
      return new Promise((resolve, reject) => {
        sleep(func, params)
          .then((res: any) => {
            resolve(res);
            if (res.code === 200) {
              message.success('删除成功');
              init();
            }
          })
          .catch(err => {
            reject(err);
          });
      });
    }
  });
}

function search() {
  if (range.value !== null) {
    searchForm.value['params[beginTime]'] = formatDate(new Date(range.value[0]), 'yyyy-MM-dd');
    searchForm.value['params[endTime]'] = formatDate(new Date(range.value[1]), 'yyyy-MM-dd');
  } else {
    searchForm.value['params[beginTime]'] = null;
    searchForm.value['params[endTime]'] = null;
  }
  totalNumber.value = 0;
  netWeight.value = 0;
  tareNumber.value = 0;
  // searchForm.value.pageNum = 1;
  init();
}

function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    status: null,
    total: 0,
    // ingredientsName: '',
    lotNumber: '',
    ingredientsId: null,
    productId: null,
    itemId: null,
    'params[beginTime]': null,
    'params[endTime]': null
  };
  range.value = null;
  search();
}

function openSelectSocket(row: rawMaterial1.tabelList) {
  dialog.warning({
    title: `补打标签`,
    content: `确认要补打标签吗?`,
    positiveText: '确认',
    negativeText: '取消',
    onPositiveClick: () => {
      socketStore.setMsg(
        JSON.stringify(
          JSON.stringify({
            templateType: 'middleDrawRaw',
            printValue: [row]
          })
        )
      );
      message.success('已发送打印命令');
    }
  });
}

function getList() {
  getMaterialList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        if (item.workstageName === ' ') {
          specificationList.value.push({ label: item.specification, value: item.id });
        }
      });
    }
  });

  getIngredientsTradersList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        merchantList.value.push({ label: item.tradersName, value: item.id });
      });
    }
  });

  getIngredientsManufacturerList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        manufacturerList.value.push({ label: item.manufacturerName, value: item.id, ...item });
      });
    }
  });

  getIngredientsProductNameList({ pageSize: 999 }).then(res => {
    if (res.code === 200) {
      res.rows.forEach(item => {
        productNameList.value.push({ label: item.productName, value: item.id });
      });
    }
  });
}

function init() {
  startLoading();
  getIngredientsLog({ ...searchForm.value }).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
      totalNumber.value = Number(res.count);
      netWeight.value = Number(res.totalNetWeight.toFixed(3));
      tareNumber.value = Number(res.totalTareWeight.toFixed(3));
    }
    endLoading();
  });
}
onMounted(() => {
  init();
  getList();
});
</script>

<style scoped>
.active {
  background-color: lightgreen !important;
  color: #fff !important;
}

:deep(.n-input__input-el) {
  color: black !important;
}
:deep(.n-data-table .n-data-table-th.n-data-table-th--fixed-right),
:deep(.n-data-table .n-data-table-td.n-data-table-td--fixed-right) {
  box-shadow: -5px 2px 10px 0px rgb(198, 196, 196);
}
</style>
