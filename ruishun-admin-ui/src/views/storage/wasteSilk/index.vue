<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="废丝包编号">
          <n-input v-model:value="searchForm.wasteSilkPackageCode" placeholder="请输入包编号"></n-input>
        </n-form-item>
        <n-form-item label="废丝包类型">
          <n-select
            v-model:value="searchForm.wasteSilkPackageType"
            :options="productList"
            class="w-160px"
            placeholder="请选择包类型"
            @update:value="
              (val, item: any) => {
                searchForm.wasteSilkPackageName = item.label;
              }
            "
          ></n-select>
        </n-form-item>
        <n-form-item label="废丝包状态">
          <n-select
            v-model:value="searchForm.status"
            :options="shipStatusOption"
            class="w-160px"
            placeholder="请选择包状态"
          ></n-select>
        </n-form-item>
        <n-form-item label="报表类型">
          <n-select
            v-model:value="time"
            clearable
            :options="[
              { label: '日报表', value: 0 },
              { label: '月报表', value: 1 },
              { label: '年报表', value: 2 }
            ]"
            class="w-160px"
            placeholder="请选择报表类型"
          ></n-select>
        </n-form-item>
        <n-form-item label="报表时间">
          <n-date-picker v-model:value="range" type="datetimerange" clearable />
        </n-form-item>
        <n-form-item>
          <n-button class="mr-5px" type="primary" @click="handleSearch()">
            <icon-tdesign-search class="mr-2px" />
            搜索
          </n-button>
          <n-button @click="handleReset()">
            <icon-ic-round-refresh class="mr-4px text-20px" />
            重置
          </n-button>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="废丝装包记录">
      <template #right>
        <div style="display: flex">
          <n-button type="info" size="small" style="margin-right: 10px" @click="lookReport">查看报表</n-button>
          <CxColumns v-model:columns="columns" size="small" />
        </div>
      </template>
      <n-data-table
        :data="data"
        :loading="loading"
        :columns="columns"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init()"></my-pagination>
    </my-card>
    <!-- 明细 -->
    <my-dialog
      v-model:show="torrDetail"
      sub-text="关闭"
      width="1300px"
      title="已装包废丝明细"
      :show-cancel="false"
      @cancel="closeDetail"
      @submit="closeDetail"
    >
      <template #content>
        <div class="test">
          <my-card title="废丝包详情记录">
            <template #right>
              <CxColumns v-model:columns="detailColumns" size="small" />
            </template>
            <n-data-table
              :loading="detailLoading"
              :columns="detailColumns"
              :data="detailData"
              :max-height="dataTableConfig.maxHeight"
              :scroll-x="dataTableConfig.scrollWidth(detailColumns)"
            ></n-data-table>
            <my-pagination v-model:search-form="searchFormDetail" @init="getDetailPackage()"></my-pagination>
          </my-card>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import { type DataTableColumns, NButton } from 'naive-ui';
import { dataTableConfig } from '@/config/dataTableConfig';
import { getWasteSilkPackageDetail, getWasteSilkPackage } from '@/service/api/storage/wasteSilk/index';
import { useLoading } from '~/src/hooks';
import { serviceEnv } from '~/.env-config';
import { formatDate } from '~/src/utils';

const { loading, startLoading, endLoading } = useLoading();

const shipStatusOption = ref([
  { label: '未装满', value: '1' },
  { label: '已装满', value: '2' },
  { label: '已出库', value: '3' }
]);
const productList: any = ref([
  { label: '裸铜包', value: '1' },
  { label: '镀锡包', value: '2' }
]);
const time = ref(0);
const range = ref<[number, number] | null>(null);
// 查看今日报表
function lookReport() {
  if (range.value !== null) {
    let formateValue = '';
    switch (time.value) {
      case 0:
        formateValue = 'yyyy-MM-dd';
        break;
      case 1:
        formateValue = 'yyyy-MM';
        break;
      case 2:
        formateValue = 'yyyy';
        break;
      default:
        formateValue = 'yyyy-MM-dd';
        break;
    }
    const todayTime = formatDate(new Date(range.value[0]), formateValue);
    const url = `${serviceEnv.prod.reportServerUrl}/jmreport/view/1084637167771058176?createTime=${todayTime}`;
    window.open(url, '_blank');
  } else {
    window.$message?.error('请选择时间范围！');
  }
}

const data = ref<DataType[]>([]);
// 装托明细
type searchTypeDateil = {
  wasteSilkPackageCode: string;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchFormDetail = ref<searchTypeDateil>({
  wasteSilkPackageCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});

const detailData = ref<DetailType[]>([]);
const detailColumns: Ref<DataTableColumns<DetailType>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchFormDetail.value.pageNum - 1) * searchFormDetail.value.pageSize + index + 1
  },
  {
    title: '废丝包编号',
    titleAlign: 'center',
    align: 'center',
    width: 150,
    key: 'wasteSilkPackageCode'
  },
  {
    title: '废丝包名',
    titleAlign: 'center',
    align: 'center',
    width: 200,
    key: 'wasteSilkPackageName'
  },
  {
    title: '废丝包类型',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'wasteSilkPackageType',
    render: row => {
      switch (row.wasteSilkPackageType) {
        case '1':
          return <n-tag type="warning">{'裸铜包'}</n-tag>;
        case '2':
          return <n-tag type="info">{'镀锡包'}</n-tag>;
        default:
          return <span>{''}</span>;
      }
    }
  },
  {
    title: '入库时间',
    titleAlign: 'center',
    align: 'center',
    width: 180,
    key: 'createTime'
  },
  {
    title: '负责人名称',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'userName'
  },
  {
    title: '负责人工号',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'jobNumber'
  },
  {
    title: '工序',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'mesProcess'
  },
  {
    title: '废丝重量',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'weight',
    render: row => {
      return <span>{`${row.weight} kg`}</span>;
    }
  }
]);
type DetailType = {
  wasteSilkPackageCode: string;
  wasteSilkPackageName: string;
  wasteSilkPackageType: string;
  userName: string;
  jobNumber: string;
  mesProcess: string;
  weight: string;
};

// 查看明细
const detailLoading = ref<boolean>(false);
const torrDetail = ref<boolean>(false);
function closeDetail() {
  detailLoading.value = true;
  torrDetail.value = false;
  searchFormDetail.value = {
    wasteSilkPackageCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
}
function handleEditTable(row) {
  torrDetail.value = true;
  detailData.value = [];
  searchFormDetail.value.wasteSilkPackageCode = row.wasteSilkPackageCode;
  getDetailPackage();
}
function getDetailPackage() {
  getWasteSilkPackageDetail(searchFormDetail.value).then(res => {
    detailData.value = res.rows;
    searchFormDetail.value.total = res.total;
    detailLoading.value = false;
  });
}
// 装托记录
type searchType = {
  wasteSilkPackageCode: string;
  wasteSilkPackageName: string;
  wasteSilkPackageType: string | null;
  status: string | null;
  pageNum: number;
  pageSize: number;
  total: number;
};
const searchForm = ref<searchType>({
  wasteSilkPackageCode: '',
  wasteSilkPackageName: '',
  wasteSilkPackageType: null,
  status: null,
  pageNum: 1,
  pageSize: 10,
  total: 0
});

function handleSearch() {
  init();
}
function handleReset() {
  searchForm.value = {
    wasteSilkPackageCode: '',
    wasteSilkPackageName: '',
    wasteSilkPackageType: null,
    status: null,
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id: string;
  wasteSilkPackageCode: string;
  wasteSilkPackageName: string;
  wasteSilkPackageType: string;
  status: string;
  jobNumber: string;
  mesProcess: string;
  weightCount: string;
};
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    title: '序号',
    key: 'index',
    align: 'center',
    width: 70,
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '废丝包编号',
    titleAlign: 'center',
    align: 'center',
    width: 150,
    key: 'wasteSilkPackageCode'
  },
  {
    title: '废丝包名',
    titleAlign: 'center',
    align: 'center',
    width: 200,
    key: 'wasteSilkPackageName'
  },
  {
    title: '废丝包类型',
    titleAlign: 'center',
    align: 'center',
    width: 120,
    key: 'wasteSilkPackageType',
    render: row => {
      switch (row.wasteSilkPackageType) {
        case '1':
          return <n-tag type="warning">{'裸铜包'}</n-tag>;
        case '2':
          return <n-tag type="info">{'镀锡包'}</n-tag>;
        default:
          return <span>{''}</span>;
      }
    }
  },
  {
    title: '装包总重量',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'weightCount',
    render: row => {
      return <span>{`${row.weightCount} kg`}</span>;
    }
  },
  {
    title: '出货状态',
    titleAlign: 'center',
    align: 'center',
    width: 80,
    key: 'shipStatus',
    render: row => {
      switch (row.status) {
        case '1':
          return <n-tag type="error">{'未装满'}</n-tag>;
        case '2':
          return <n-tag type="success">{'已装满'}</n-tag>;
        case '3':
          return <n-tag type="success">{'已出库'}</n-tag>;
        default:
          return <span>{''}</span>;
      }
    }
  },
  {
    title: '操作',
    titleAlign: 'center',
    align: 'center',
    width: 100,
    key: 'cz',
    render: row => {
      return (
        <div>
          <n-button onClick={() => handleEditTable(row)} size="tiny" type="info" quaternary class="mr-5px">
            <n-icon size="20">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                <g fill="none">
                  <path
                    d="M3.26 11.602C3.942 8.327 6.793 6 10 6c3.206 0 6.057 2.327 6.74 5.602a.5.5 0 0 0 .98-.204C16.943 7.673 13.693 5 10 5c-3.693 0-6.943 2.673-7.72 6.398a.5.5 0 0 0 .98.204zM10 8a3.5 3.5 0 1 0 0 7a3.5 3.5 0 0 0 0-7zm-2.5 3.5a2.5 2.5 0 1 1 5 0a2.5 2.5 0 0 1-5 0z"
                    fill="currentColor"
                  ></path>
                </g>
              </svg>
            </n-icon>
            明细
          </n-button>
        </div>
      );
    }
  }
]);
function init() {
  startLoading();
  data.value = [];
  getWasteSilkPackage(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  init();
});
</script>
<style scope>
.test {
  width: 1200px;
  display: flex;
  flex-direction: column;
}
.n-icon {
  margin-right: 5px;
}
.n-button .n-button__content {
  font-size: 14px;
}
.n-data-table .n-data-table-th.n-data-table-th--fixed-right,
.n-data-table .n-data-table-td.n-data-table-td--fixed-right {
  box-shadow: -5px 2px 10px 0px rgb(198, 196, 196);
}
</style>
