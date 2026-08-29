<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <!-- <n-form-item label="称重时间">
          <n-date-picker v-model:formatted-value="searchForm.weightTime" :style="{ width: formItemWidth }" />
        </n-form-item> -->
        <n-form-item label="时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item label="出入库状态">
          <n-select
            v-model:value="searchForm.wmsStatus"
            :options="options"
            placeholder="请选择出入库状态"
            :style="{ width: formItemWidth }"
          ></n-select>
        </n-form-item>
        <n-form-item label="批号">
          <n-input
            v-model:value="searchForm.batchCode"
            placeholder="请输入批号"
            :style="{ width: formItemWidth }"
          ></n-input>
        </n-form-item>
        <n-form-item label="贸易商" path="ingredientsId">
          <n-select
            v-model:value="ingredientsId"
            placeholder="请选择贸易商"
            :filterable="true"
            :options="merchantList"
            :style="{ width: formItemWidth }"
            @update-value="(val,item : any) => {
              searchForm.ingredientsName = item.label;
            }"
          ></n-select>
        </n-form-item>
        <n-form-item label="材料厂家" path="manufacturerId">
          <n-select
            v-model:value="manufacturerId"
            placeholder="请选择材料厂家"
            :filterable="true"
            :options="manufacturerList"
            :style="{ width: formItemWidth }"
            @update-value="(val,item : any) => {
              searchForm.manufacturerName = item.label;
            }"
          ></n-select>
        </n-form-item>
        <n-form-item label="规格" path="itemSpecification">
          <n-input v-model:value="searchForm.itemSpecification" placeholder="请输入规格"></n-input>
        </n-form-item>
        <n-form-item label="合计件数">
          <n-input v-model:value="totalNumber" :disabled="true" :style="{ width: formItemWidth }"></n-input>
        </n-form-item>
        <n-form-item label="合计净重">
          <n-input v-model:value="netWeight" :disabled="true" :style="{ width: formItemWidth }"></n-input>
        </n-form-item>
        <n-form-item label="合计皮重">
          <n-input v-model:value="tareNumber" :disabled="true" :style="{ width: formItemWidth }"></n-input>
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
    </my-card>
    <my-card :title="title">
      <template #right>
        <div style="display: flex">
          <n-button type="info" size="small" style="margin-right: 10px" @click="lookReport">查看报表</n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table :loading="loading" :columns="columns" :data="data"></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { getIngredientsTradersList } from '@/service/api/md/ingredients/traders';
import { getIngredientsManufacturerList } from '@/service/api/md/ingredients/manufacturer';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks';
import { getMesOutputLogWmsList } from '~/src/service/api/wms/semiProduct';
import { formatDate } from '~/src/utils/form/rule';
const { loading, startLoading, endLoading } = useLoading();

const title = ref<string>('半成品出入库');

const searchForm = ref<semiProduct.searchForm>({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  wmsStatus: null,
  batchCode: '',
  ingredientsName: '',
  itemSpecification: '',
  weightTime: null,
  manufacturerName: ''
});
const options = ref<{ label: string; value: number }[]>([
  {
    label: `入库`,
    value: 0
  },
  {
    label: `出库`,
    value: 1
  }
]);
const ingredientsId = ref<number | null>(null);
const manufacturerId = ref<number | null>(null);
const formItemWidth = ref<string>('200px');
const totalNumber = ref<any>(0);
const netWeight = ref<any>(0);
const tareNumber = ref<any>(0);
const range = ref<[number, number] | null>(null);
const merchantList = ref<{ label: string; value: number }[]>([]);
const manufacturerList = ref<{ label: string; value: number }[]>([]);
const data = ref<semiProduct.tabelList[]>([]);

const columns: Ref<DataTableColumns<semiProduct.tabelList>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '二维码条号',
    key: 'outputSn',
    width: 180
  },
  {
    title: '贸易商',
    width: 80,
    key: 'ingredientsName'
  },
  {
    title: '规格',
    width: 100,
    key: 'itemSpecification'
  },
  {
    title: '批号',
    width: 120,
    key: 'batchCode'
  },
  {
    title: '料号',
    width: 160,
    key: 'partNumber'
  },

  {
    title: '毛重',
    width: 100,
    key: 'outputGrossWeight'
  },

  {
    title: '净重',
    width: 100,
    key: 'outputWeight'
  },

  {
    title: '皮重',
    width: 100,
    key: 'outputTareWeight'
  },
  {
    title: '单位',
    width: 100,
    key: 'measureName'
  },
  {
    title: '材料厂家',
    width: 180,
    key: 'manufacturerName'
  },
  {
    title: '状态',
    width: 100,
    key: 'wmsStatus',
    render: (row: any) => {
      let colorText = '';
      let showText = '';
      let showTag = true;
      switch (row.wmsStatus) {
        case 0:
          colorText = 'success';
          showText = '入库';
          break;
        case 1:
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
    key: 'createTime',
    width: 180
  }
]);

function lookReport() {
  let url = 'http://huaerda-jimu.24yt.com/jmreport/view/998755386337329152';
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

  url += searchValue;
  window.open(url, '_blank');
}

function search() {
  if (range.value !== null) {
    searchForm.value['params[beginTime]'] = formatDate(new Date(range.value[0]), 'yyyy-MM-dd');
    searchForm.value['params[endTime]'] = formatDate(new Date(range.value[1]), 'yyyy-MM-dd');
  } else {
    searchForm.value['params[beginTime]'] = null;
    searchForm.value['params[endTime]'] = null;
  }
  searchForm.value.pageNum = 1;
  netWeight.value = 0;
  tareNumber.value = 0;
  totalNumber.value = 0;
  init();
}

function reset() {
  searchForm.value = {
    pageNum: 1,
    pageSize: 10,
    total: 0,
    wmsStatus: null,
    batchCode: '',
    ingredientsName: '',
    itemSpecification: '',
    weightTime: null,
    manufacturerName: ''
  };
  ingredientsId.value = null;
  manufacturerId.value = null;
  range.value = null;
  search();
}

function getList() {
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
}

function init() {
  startLoading();
  const searchFormClone = JSON.parse(JSON.stringify(searchForm.value));
  if (searchForm.value.wmsStatus === 0) {
    delete searchFormClone.wmsStatus;
  }
  getMesOutputLogWmsList(searchFormClone).then(res => {
    if (res.code === 200) {
      data.value = res.rows;
      searchForm.value.total = res.total;
      totalNumber.value = res.count;
      netWeight.value = res.totalNetWeight;
      tareNumber.value = res.totalTareWeight;
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

:deep(.n-form.n-form--inline) {
  flex-wrap: wrap !important;
}
</style>
