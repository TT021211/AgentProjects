<template>
  <div>
    <my-card title="搜索条件" search>
      <n-form inline>
        <n-form-item label="电表编码：">
          <n-input v-model:value="searchForm.electricDeviceCode" placeholder="请输入电表编码"></n-input>
        </n-form-item>
        <n-form-item>
          <component :is="useSearchBtn(search, reset)"></component>
        </n-form-item>
      </n-form>
    </my-card>
    <my-card title="机台能耗列表">
      <n-data-table
        :loading="loading"
        :data="data"
        :columns="columns"
        :max-height="dataTableConfig.maxHeight"
        :scroll-x="dataTableConfig.scrollWidth(columns)"
      ></n-data-table>
      <my-pagination v-model:search-form="searchForm" @init="init"></my-pagination>
    </my-card>
    <my-dialog
      v-model:show="showDialog"
      width="1000px"
      title="用电详情"
      :show-cancel="false"
      @cancel="showDialog = false"
      @submit="showDialog = false"
    >
      <template #content>
        <div class="w-full">
          <n-form inline label-placement="left">
            <n-form-item label="查询条件">
              <n-select
                :options="pickerOptions"
                placeholder="请选择"
                class="w-180px"
                @update:value="changeType"
              ></n-select>
            </n-form-item>
            <n-form-item label="日期">
              <n-date-picker :type="dateType" class="w-180px" @change="changeDate"></n-date-picker>
            </n-form-item>
          </n-form>
          <div ref="ElectricRef" class="w-1000px h-500px"></div>
        </div>
      </template>
    </my-dialog>
  </div>
</template>

<script setup lang="tsx">
import type { Ref } from 'vue';
import { ref } from 'vue';
import type { DataTableColumns } from 'naive-ui';
import dayjs from 'dayjs';
import { useEcharts } from '@/composables';
import type { ECOption } from '@/composables';
import { customBtn, useSearchBtn } from '@/hooks/common/useBtn';
import { getMachineEnergyList, getMachineEnergyDetail } from '@/service/api/ecm/machineEnergy';
import { dataTableConfig } from '@/config/dataTableConfig';
import { useLoading } from '~/src/hooks';
import 'echarts/lib/component/dataZoom';
const electId = ref<number | string>('');
const showDialog = ref(false);
const dateType = ref<any>('date');

const pickerOptions = [
  {
    label: '按天查询',
    value: 'date'
  },
  {
    label: '按月查询',
    value: 'month'
  },
  {
    label: '按年查询',
    value: 'year'
  }
];
function changeType(val: any) {
  // console.log(val);
  dateType.value = val;
}
function getType(str: string) {
  switch (str) {
    case 'date':
      return 'YYYY-MM-DD';
    case 'month':
      return 'YYYY-MM';
    case 'year':
      return 'YYYY';
    default:
      return 'YYYY-MM-DD';
  }
}
// 获取24小时的数组
const getHours = (): string[] => {
  const hours: string[] = [];
  for (let i = 0; i < 24; i += 1) {
    hours.push(`${i}时`);
  }
  return hours;
};
const getMonth = (): string[] => {
  const months: string[] = [];
  for (let i = 1; i <= 12; i += 1) {
    months.push(`${i}月`);
  }
  return months;
};
const getDay = (): string[] => {
  const days: string[] = [];
  for (let i = 1; i <= 31; i += 1) {
    days.push(`${i}日`);
  }
  return days;
};
function getYaxis(str: string) {
  switch (str) {
    case 'date':
      return getHours();
    case 'month':
      return getDay();
    case 'year':
      return getMonth();
    default:
      return getHours();
  }
}
const electricOption: Ref<ECOption> = ref({
  title: {
    text: `电表日能耗`,
    top: 'top',
    left: 'center'
  },
  toolbox: {
    show: true,
    feature: {
      saveAsImage: {}
      // magicType: { type: ['line', 'bar'] }
    }
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      label: {
        backgroundColor: '#6a7985'
      },
      formatter(params: any) {
        return `${params.value}wh`;
      }
    }
  },

  xAxis: {
    type: 'category',
    data: getYaxis(dateType.value)
  },
  yAxis: {
    type: 'value',
    axisLabel: {
      formatter: '{value} wh'
    }
  },
  dataZoom: [
    {},
    {
      type: 'inside'
    }
  ],
  series: [
    {
      data: [],
      type: 'bar'
    },
    {
      data: [],
      type: 'line',
      // areaStyle: {},
      smooth: true
    }
  ]
}) as Ref<ECOption>;
function changeDate(val: any) {
  const formatString = getType(dateType.value);
  getMachineEnergyDetail(electId.value as string, dayjs(new Date(val)).format(formatString), dateType.value).then(
    res => {
      showDialog.value = true;
      // electData.value = res.data;
      if (!electricOption.value) return;
      // eslint-disable-next-line no-plusplus
      if (!electricOption.value.title) return;
      if (!electricOption.value.xAxis) return;
      electricOption.value.xAxis = {
        type: 'category',
        data: getYaxis(dateType.value)
      };
      electricOption.value.title = {
        text: `电表消耗`,
        top: 'top',
        left: 'center',
        subtext: `已经使用${res.data[0]}(wh)`
      };
      electricOption.value.series = [
        {
          data: res.data.slice(1),
          type: 'bar'
        },
        {
          data: res.data.slice(1),
          type: 'line',
          // areaStyle: {},
          smooth: true
        }
      ];
    }
  );
}

const { domRef: ElectricRef } = useEcharts(electricOption);
// function renderLine(chartInstance: echarts.ECharts) {
//   chartInstance.setOption(electricOption.value);
// }
const { loading, startLoading, endLoading } = useLoading();
const searchForm = ref({
  electricDeviceCode: undefined,
  pageSize: 10,
  pageNum: 1,
  total: 0
});

const data = ref<Energy.machineEnergy.Columns[]>([]);
const columns: Ref<DataTableColumns<Energy.machineEnergy.Columns>> = ref([
  {
    title: '能耗数据ID',
    align: 'center',
    key: 'consumptionId'
  },
  {
    title: '电表编码',
    align: 'center',
    key: 'electricDeviceCode'
  },
  {
    title: '第三方记录时间',
    align: 'center',
    key: 'recordingTime'
  },
  {
    title: '数值（Kwh）',
    align: 'center',
    key: 'value',
    render: row => {
      return Number(row.value) / 1000;
    }
  },
  {
    title: '备注',
    align: 'center',
    key: 'remark'
  },
  {
    title: '操作',
    align: 'center',
    key: 'action',
    render: _row => {
      return customBtn({
        fun: () => {
          electId.value = _row.electricDeviceCode as string;
          getMachineEnergyDetail(_row.electricDeviceCode as string, dayjs(new Date()).format('YYYY-MM-DD')).then(
            res => {
              showDialog.value = true;
              // electData.value = res.data;
              if (!electricOption.value) return;
              // eslint-disable-next-line no-plusplus
              if (!electricOption.value.title) return;
              electricOption.value.title = {
                text: `每日电表消耗`,
                top: 'top',
                left: 'center',
                subtext: `今日使用${res.data.consumptionValues[0] / 1000}(Kwh)`
              };
              electricOption.value.series = [
                {
                  data: res.data.consumptionValues,
                  type: 'bar'
                },
                {
                  data: res.data.electricCharges,
                  type: 'line',
                  // areaStyle: {},
                  smooth: true
                }
              ];
              // ElectricRef.value = useEcharts(electricOption);
              showDialog.value = true;
            }
          );
        },
        icon: 'EyeOutline',
        text: '查看详情',
        size: 'small'
      });
    }
  }
]);
function search() {
  searchForm.value.pageNum = 1;
  init();
}
function reset() {
  searchForm.value = {
    electricDeviceCode: undefined,
    pageSize: 10,
    pageNum: 1,
    total: 0
  };
  init();
}
function init() {
  startLoading();
  getMachineEnergyList(searchForm.value).then(res => {
    endLoading();
    if (res.code !== 200) return;
    data.value = res.rows;
    searchForm.value.total = res.total;
  });
}

init();
</script>
<style scoped></style>
