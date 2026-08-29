<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
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
        <n-form-item label="品名">
          <n-select
            v-model:value="searchForm.productName"
            :options="productList"
            class="w-180px"
            placeholder="请选择品名"
            @update:value="
              (val, item: any) => {
                searchForm.productName = item.label;
              }
            "
          ></n-select>
        </n-form-item>
        <n-form-item label="规格">
          <n-input
            v-model:value="searchForm.productSpec"
            placeholder="请输入规格"
            :style="{ width: formItemWidth }"
          ></n-input>
        </n-form-item>

        <n-form-item label="批号">
          <n-input
            v-model:value="searchForm.productBatchNumber"
            placeholder="请输入批号"
            :style="{ width: formItemWidth }"
          ></n-input>
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
        <n-form-item label="箱编号">
          <n-input v-model:value="searchForm.boxCode" placeholder="请输入箱编码"></n-input>
        </n-form-item>
        <n-form-item label="出入库时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item label="报表品名">
          <n-select
            v-model:value="productName"
            :options="productList"
            multiple
            clearable
            class="w-180px"
            placeholder="请选择报表品名"
            @update:value="
              (val, item: any) => {
								if(item.length > 0)
                productNameData = item.map(n=>n.label);
              }
            "
          ></n-select>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card :title="title">
      <template #right>
        <div style="display: flex">
          <n-button type="info" size="small" style="margin-right: 10px" @click="lookReport(1)">查看报表</n-button>
          <n-button type="info" size="small" style="margin-right: 10px" @click="lookReport(3)">查看库存</n-button>
          <n-button type="info" size="small" style="margin-right: 10px" @click="showSelYear = true">
            查看月度报表
          </n-button>
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
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>
    <my-dialog
      :show="showSelYear"
      width="450px"
      :title="'选择托'"
      @cancel="(showSelYear = false), (searchMonthForm.productName = null), (searchMonthForm.createTime = null)"
      @submit="lookMonthReport"
    >
      <template #content>
        <div>
          <n-form-item label-placement="left" label="品名">
            <n-select
              v-model:value="searchMonthForm.productName"
              :options="productList"
              class="w-220px"
              placeholder="请选择品名"
              @update:value="
              (val, item: any) => {
                searchMonthForm.productName = item.label;
              }
            "
            ></n-select>
          </n-form-item>
          <n-form-item label-placement="left" label="年份">
            <n-date-picker
              v-model:value="searchMonthForm.createTime"
              class="w-220px"
              value-format="yyyy"
              type="year"
              clearable
            />
          </n-form-item>
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
import type { DataTableColumns } from 'naive-ui';
import { dataTableConfig } from '@/config/dataTableConfig';
import { getMaterialList } from '@/service/api/md/itemlist/index';
import { getIngredientsTradersList } from '@/service/api/md/ingredients/traders';
import { getIngredientsManufacturerList } from '@/service/api/md/ingredients/manufacturer';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import { formatDate } from '~/src/utils/form/rule';
import { getFinishedList } from '~/src/service/api/storage/storageFinishedProduct';
import { getAllClassifyList } from '~/src/service/api/md/classify';
import { serviceEnv } from '~/.env-config';

const { loading, startLoading, endLoading } = useLoading();

const title = ref<string>('成品出入库');
const searchForm = ref<rawMaterial1.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  productSpec: '',
  status: null,
  productBatchNumber: '',
  productName: null,
  itemId: null,
  startTime: null,
  endTime: null,
  boxCode: '',
  'params[beginTime]': null,
  'params[endTime]': null
});
const options = [
  {
    label: `入库`,
    value: 'IN'
  },
  {
    label: `出库`,
    value: 'OUT'
  }
];
const productList: any = ref([]);
const formItemWidth = ref<string>('200px');
const checkedRowKeys = ref<Array<string | number>>([]);
const range = ref<[number, number] | null>(null);
// const forModel = ref<{ inDate: string | null }>({
//   inDate: null
// });
// const printRules = {
//   socketIp: createRequiredFormRule('请选择打印服务')
// };
const specificationList = ref<{ label: string; value: number }[]>([]);
const merchantList = ref<{ label: string; value: number }[]>([]);
const manufacturerList = ref<{ label: string; value: number }[]>([]);
// const showDialog = ref<boolean>(false);
const rowKey = (row: any) => row.id;

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
  // {
  //   title: '二维码条号',
  //   key: 'codeSn',
  //   width: 100
  // },
  // {
  //   title: '贸易商',
  //   width: 100,
  //   key: 'ingredientsName'
  // },
  {
    title: '箱编码',
    titleAlign: 'center',
    align: 'center',
    width: 150,
    key: 'boxCode'
  },
  {
    title: '品名',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'productName'
  },
  {
    title: '产品编码',
    titleAlign: 'center',
    align: 'center',
    width: 150,
    key: 'productCode'
  },
  {
    title: '规格',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'productSpec',
    ellipsis: {
      tooltip: true
    }
  },
  {
    title: '批号',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'productBatchNumber',
    ellipsis: {
      tooltip: true
    }
  },
  // {
  //   title: '料号',
  //   width: 160,
  //   key: 'partNumber'
  // },

  {
    title: '毛重',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'grossWeight',
    render(row) {
      return <span>{`${row.grossWeight} ${row.productUnit}`}</span>;
    }
  },

  {
    title: '净重',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'netWeight',
    render(row) {
      return <span>{`${row.netWeight} ${row.productUnit}`}</span>;
    }
  },
  // {
  //   title: '皮重',
  //   width: 100,
  //   key: 'tareWeight'
  // },
  {
    title: '单位',
    titleAlign: 'center',
    align: 'center',
    width: 60,
    key: 'productUnit'
  },
  // {
  //   title: '材料厂家',
  //   width: 120,
  //   key: 'manufacturerName'
  // },
  {
    title: '出入库状态',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'status',
    render: (row: any) => {
      let colorText = '';
      let showText = '';
      let showTag = true;
      switch (row.status) {
        case 'IN':
          colorText = 'success';
          showText = '入库';
          break;
        case 'OUT':
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
    title: '仓库',
    titleAlign: 'center',
    align: 'center',
    key: 'warehouseName',
    width: 80
  },
  {
    title: '出入库时间',
    titleAlign: 'center',
    align: 'center',
    key: 'createTime',
    width: 180
  },
  {
    title: '入库人',
    titleAlign: 'center',
    align: 'center',
    key: 'createBy',
    width: 80
  }
  // {
  //   title: '操作',
  //   width: 120,
  //   titleAlign: 'center',
  //   key: 'action',
  //   fixed: 'right',
  //   render: (row: any) => {
  //     return (
  //       <n-space justify={'center'}>
  //         {/* <n-button size="small" type="info" onClick={() => openSelectSocket(row)}>
  //           补打标签
  //         </n-button> */}
  //         <n-button
  //           size="small"
  //           type="error"
  //           onClick={() => {
  //             warning.warn(() => {
  //               delProductInout(row.boxCode).then(res => {
  //                 if (res.code === 200) {
  //                   message.success('删除成功');
  //                   init();
  //                 } else {
  //                   message.error('删除失败');
  //                 }
  //               });
  //             });
  //           }}
  //         >
  //           删除
  //         </n-button>
  //       </n-space>
  //     );
  //   }
  // }
]);

const showSelYear = ref(false);
type searchMonthType = {
  productName: string | null;
  createTime: any;
};
const searchMonthForm = ref<searchMonthType>({
  productName: null,
  createTime: null
});
// 查看月报表
function lookMonthReport() {
  let url = `${serviceEnv.prod.reportServerUrl}/jmreport/view/1016559313296359424`;
  searchMonthForm.value.createTime = new Date(searchMonthForm.value.createTime).getFullYear();
  let searchValue = '';
  let isAddQuestionMask = false;
  for (const key in searchMonthForm.value) {
    if (!isAddQuestionMask) {
      searchValue += `?${key}=${searchMonthForm.value[key]}`;
      isAddQuestionMask = true;
    } else {
      searchValue += `&${key}=${searchMonthForm.value[key]}`;
    }
  }
  url += searchValue;
  showSelYear.value = false;
  window.open(url, '_blank');
}
const productName: any = ref(null);
const productNameData: any = ref([]);
// 查看库存和成品出入库
function lookReport(type) {
  let url = `${serviceEnv.prod.reportServerUrl}/jmreport/view/`;
  if (type === 1) {
    url += '1090429047112859648';
  } else if (type === 3) {
    url += '1087111151280324608';
  }
  let searchValue = '';
  let isAddQuestionMask = false;
  const filterData = ['pageNum', 'pageSize', 'total', 'params[beginTime]', 'params[endTime]'];
  for (const key in searchForm.value) {
    if (
      searchForm.value[key] &&
      !filterData.includes(key) &&
      key !== 'boxCode' &&
      key !== 'productBatchNumber' &&
      key !== 'productName'
    ) {
      if (!isAddQuestionMask) {
        searchValue += `?${key}=${searchForm.value[key]}`;
        isAddQuestionMask = true;
      } else {
        searchValue += `&${key}=${searchForm.value[key]}`;
      }
    }
  }

  if (productNameData.value.length > 0) {
    if (typeof productNameData.value !== 'string') {
      productNameData.value = productNameData.value.join("','");
      productNameData.value = `'${productNameData.value}'`;
    }
    if (isAddQuestionMask) {
      searchValue += `&productName=${productNameData.value}`;
    } else {
      searchValue += `?productName=${productNameData.value}`;
      isAddQuestionMask = true;
    }
  }
  if (range.value !== null) {
    const beginTime = formatDate(new Date(range.value[0]), 'yyyy-MM-dd');
    const endTime = formatDate(new Date(range.value[1]), 'yyyy-MM-dd');
    if (isAddQuestionMask) {
      searchValue += `&beginTime=${beginTime}&endTime=${endTime}`;
    } else {
      searchValue = `?beginTime=${beginTime}&endTime=${endTime}`;
      isAddQuestionMask = true;
    }
  }

  url += searchValue;
  window.open(url, '_blank');
}

function search() {
  if (range.value) {
    searchForm.value.startTime = formatDate(new Date(range.value[0]), 'yyyy-MM-dd hh-mm-ss');
    searchForm.value.endTime = formatDate(new Date(range.value[1]), 'yyyy-MM-dd hh-mm-ss');
  } else {
    searchForm.value.startTime = null;
    searchForm.value.endTime = null;
  }
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
    productBatchNumber: '',
    productName: null,
    productSpec: '',
    startTime: null,
    endTime: null,
    boxCode: '',
    'params[beginTime]': null,
    'params[endTime]': null
  };
  range.value = null;
  search();
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

  getAllClassifyList({ pageNum: 1 }).then(res => {
    res.data.forEach(item => {
      if (item.parentTypeId !== 0) {
        productList.value.push({
          id: item.id,
          value: item.id,
          key: item.id,
          label: item.typeName
        });
      }
    });
  });
}

function init() {
  startLoading();
  data.value = [];
  getFinishedList(searchForm.value).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
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
