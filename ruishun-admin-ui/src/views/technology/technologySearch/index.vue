<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="产品型号">
          <n-input v-model:value="searchForm.productModel" placeholder="请输入产品型号"></n-input>
        </n-form-item>
        <n-form-item label="产品规格">
          <n-input v-model:value="searchForm.productSpecification" placeholder="请输入产品规格"></n-input>
        </n-form-item>
        <n-form-item label="设备编号">
          <n-input v-model:value="searchForm.equipmentCode" placeholder="请输入设备编号"></n-input>
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
    <my-card title="工艺查询列表">
      <template #right>
        <CxColumns v-model:columns="columns" size="small" class="w-230px" />
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
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref, onMounted } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import { getMesProcessEnterList } from '@/service/api/technology/technologyEnter';
import { useSearchBtn } from '~/src/hooks/common/useBtn';
import { useLoading } from '~/src/hooks/index';
const { loading, startLoading, endLoading } = useLoading();

type searchType = {
  productModel: string;
  productSpecification: string;
  equipmentCode: string;
  pageNum: number;
  pageSize: number;
  total: number;
};

const data = ref<DataType[]>([]);
const searchForm = ref<searchType>({
  productModel: '',
  productSpecification: '',
  equipmentCode: '',
  pageNum: 1,
  pageSize: 10,
  total: 0
});
function handleSearch() {
  searchForm.value.pageNum = 1;
  init();
}
function handleReset() {
  searchForm.value = {
    productModel: '',
    productSpecification: '',
    equipmentCode: '',
    pageNum: 1,
    pageSize: 10,
    total: 0
  };
  init();
}
type DataType = {
  id?: number;
  productSpecification: string;
  productModel: string;
  productName: string;
  productCode: string;
  productEdition: string;
  equipmentCode: string;
  deliveryTime: string;
  softenTemperatureUp: string;
  softenTemperatureDown: string;
  ovenTemperatureEnter: string;
  ovenTemperatureMiddle: string;
  ovenTemperatureOut: string;
  catalyzeFirst: string;
  catalyzeSecond: string;
  catalyzeBefore: string;
  changeWindSpeed: string;
  circulatingFanSpeed: string;
  exhaustFanSpeed: string;
  wireSpeed: string;
  viscosityCup: string;
  putReelModel: string;
  createTime: number;
  createBy: string;
  examineBy: string;
  approveBy: string;
  executeTime: number;
  standardDiameter: string;
  wireFilmMax: string;
  wireThickMax: string;
  wireFilmMin: string;
  wireThickMin: string;
};

const rowKey = (row: any) => row.id;
const columns: Ref<DataTableColumns<DataType>> = ref([
  {
    type: 'selection'
  },
  {
    title: '序号',
    key: 'index',
    width: 100,
    align: 'center',
    render: (_row, index) => (searchForm.value.pageNum - 1) * searchForm.value.pageSize + index + 1
  },
  {
    title: '产品型号',
    align: 'center',
    width: 100,
    key: 'productModel'
  },
  {
    title: '产品规格',
    align: 'center',
    width: 100,
    key: 'productSpecification'
  },
  {
    title: '产品名称',
    align: 'center',
    width: 100,
    key: 'productName'
  },
  {
    title: '设备',
    align: 'center',
    width: 100,
    key: 'equipmentCode'
  },
  {
    title: '产品编号',
    align: 'center',
    width: 100,
    key: 'productCode'
  },
  {
    title: '审批状态',
    align: 'center',
    width: 100,
    key: 'approveStatus',
    render(row: any) {
      return (
        <n-tag type={row.approveStatus === '0' ? 'default' : 'success'}>
          {row.approveStatus === '0' ? '未批准' : '已批准'}
        </n-tag>
      );
    }
  },
  {
    title: '版次',
    align: 'center',
    width: 100,
    key: 'productEdition'
  },
  {
    title: '交期',
    align: 'center',
    width: 100,
    key: 'deliveryTime'
  },
  {
    title: '软化控温上层',
    align: 'center',
    width: 100,
    key: 'softenTemperatureUp'
  },
  {
    title: '软化控温下层',
    align: 'center',
    width: 100,
    key: 'softenTemperatureDown'
  },
  {
    title: '烘炉控温进口',
    align: 'center',
    width: 100,
    key: 'ovenTemperatureEnter'
  },
  {
    title: '烘炉控温炉中',
    align: 'center',
    width: 100,
    key: 'ovenTemperatureMiddle'
  },
  {
    title: '烘炉控温出口',
    align: 'center',
    width: 100,
    key: 'ovenTemperatureOut'
  },
  {
    title: '催化后第一次',
    align: 'center',
    width: 100,
    key: 'catalyzeFirst'
  },
  {
    title: '催化后第二次',
    align: 'center',
    width: 100,
    key: 'catalyzeSecond'
  },
  {
    title: '催化前控温',
    align: 'center',
    width: 100,
    key: 'catalyzeBefore'
  },
  {
    title: '交换热风速度',
    align: 'center',
    width: 100,
    key: 'changeWindSpeed'
  },
  {
    title: '循环风机速度',
    align: 'center',
    width: 100,
    key: 'circulatingFanSpeed'
  },
  {
    title: '排废风机速度',
    align: 'center',
    width: 100,
    key: 'exhaustFanSpeed'
  },
  {
    title: '线速',
    align: 'center',
    width: 100,
    key: 'wireSpeed'
  },
  {
    title: '粘粘度4号杯',
    align: 'center',
    width: 100,
    key: 'viscosityCup'
  },
  {
    title: '收线轴型号',
    align: 'center',
    width: 100,
    key: 'putReelModel'
  },
  {
    title: '创建时间',
    align: 'center',
    width: 100,
    key: 'createTime'
  },
  {
    title: '编制人',
    align: 'center',
    width: 100,
    key: 'createBy'
  },
  {
    title: '审核人',
    align: 'center',
    width: 100,
    key: 'examineBy'
  },
  {
    title: '批准人',
    align: 'center',
    width: 100,
    key: 'approveBy'
  },
  {
    title: '执行日期',
    align: 'center',
    width: 100,
    key: 'executeTime'
  },
  {
    title: '导体标称直径',
    align: 'center',
    width: 100,
    key: 'standardDiameter'
  }
  // {
  //   title: '漆包线最大外径薄漆膜',
  //   align: 'center',
  //   width: 100,
  //   key: 'wireFilmMax'
  // },
  // {
  //   title: '漆包线最大外径厚漆膜',
  //   align: 'center',
  //   width: 100,
  //   key: 'wireThickMax'
  // },
  // {
  //   title: '最小漆膜厚度薄漆膜',
  //   align: 'center',
  //   width: 100,
  //   key: 'wireFilmMin'
  // },
  // {
  //   title: '最小漆膜厚度厚漆膜',
  //   align: 'center',
  //   width: 100,
  //   key: 'wireThickMin'
  // }
]);

const checked = ref<(string | number)[]>([]);
const handleCheck = (rowKeys: (string | number)[]) => {
  checked.value = rowKeys;
};

function init() {
  startLoading();
  data.value = [];
  getMesProcessEnterList(searchForm.value).then(res => {
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
  endLoading();
}
onMounted(() => {
  init();
});
</script>

<style scoped></style>
