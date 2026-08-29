<template>
  <div>
    <n-grid x-gap="12" :cols="12">
      <div class="card">
        <!-- <n-from></n-from> -->
      </div>
      <n-gi v-for="item in cardList" :key="item.id" :span="2">
        <div class="h-200px card">
          <div class="container-header">{{ item.title }}</div>
          <div class="container-content">
            <span class="num">{{ item.num }}</span>
            家
          </div>
        </div>
      </n-gi>

      <!-- <n-gi :span="3">
        <div style="background-color: #fff" class="h-200px"></div>
      </n-gi>
      <n-gi :span="3">
        <div style="background-color: #fff" class="h-200px"></div>
      </n-gi> -->
      <n-gi :span="6">
        <div ref="grade" class="h-340px mt-10px card"></div>
      </n-gi>
      <n-gi :span="3">
        <div ref="status" class="h-340px mt-10px card"></div>
      </n-gi>

      <n-gi :span="3">
        <div ref="industry" class="h-340px mt-10px card"></div>
      </n-gi>

      <n-gi :span="6" class="map-box mt-10px">
        <div ref="mapDom" class="map h-420px card"></div>
        <div class="address">客户地址分布</div>
      </n-gi>

      <n-gi :span="6" class="mt-10px">
        <div ref="followUpStatus" class="h-420px card"></div>
      </n-gi>
    </n-grid>
  </div>
</template>

<script setup lang="tsx">
import { ref, onMounted, nextTick } from 'vue';
import type { Ref } from 'vue';
import * as echarts from 'echarts';
// import axios from 'axios';
import { type ECOption, useEcharts } from '@/composables';
import { getClientAnalyze } from '@/service/api/client/analyse/index';
import { geoJson } from './options/address';

type card = {
  title: string;
  num: number;
  id: number;
};

const cardList = ref<Array<card>>([
  { title: '客户总数', num: 0, id: 1 },
  { title: '已成交客户数', num: 0, id: 2 },
  { title: '未成交客户数', num: 0, id: 3 },
  { title: '普通客户', num: 0, id: 4 },
  { title: '重要客户', num: 0, id: 5 },
  { title: '战略客户', num: 0, id: 6 }
]);

const statusData = ref<Array<{ name: string; value: number }>>([
  { name: '待跟进', value: 0 },
  { name: '潜在客户', value: 0 },
  { name: '有意向', value: 0 },
  { name: '高意向', value: 0 },
  { name: '未成交', value: 0 },
  { name: '已成交', value: 0 }
]);

const industryData = ref<Array<{ name: string; value: number }>>([
  { name: '微信沟通', value: 0 },
  { name: '电话沟通', value: 0 },
  { name: '线下拜访', value: 0 },
  { name: '其他', value: 0 }
]);

const gradeOptions = ref<ECOption>({
  title: {
    text: '客户渠道来源分析',
    left: 20
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      label: {
        backgroundColor: '#283b56'
      }
    }
  },
  legend: { bottom: 10, left: 'center', data: ['客户数量', '客户占比'] },
  toolbox: {
    show: false,
    feature: {
      dataView: { readOnly: false },
      restore: {},
      saveAsImage: {}
    }
  },
  xAxis: [
    {
      type: 'category',
      boundaryGap: true,
      data: []
    },
    {
      type: 'category',
      boundaryGap: true,
      data: []
    }
  ],
  yAxis: [
    {
      type: 'value',
      scale: true,
      name: '',
      min: 0,
      max: 100,
      boundaryGap: [0.2, 0.2],
      axisLabel: {
        show: true,
        formatter(value) {
          if (value === 0) {
            return `${value}%`;
          }
          return `${value}%`;
        }
      }
    },
    {
      type: 'value',
      scale: true,
      name: '',
      min: 0,
      boundaryGap: [0.2, 0.2]
    }
  ],
  series: [
    {
      name: '客户数量',
      type: 'bar',
      data: []
    },
    {
      name: '客户占比',
      type: 'line',
      data: [],
      tooltip: {
        valueFormatter(value) {
          return `${value}%`;
        }
      }
    }
  ]
}) as Ref<ECOption>;

const { domRef: grade } = useEcharts(gradeOptions);

const statusOptions = ref<ECOption>({
  title: {
    text: '客户状态分析',
    // subtext: 'Fake Data',
    left: 20
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  legend: {
    bottom: 10,
    left: 'center',
    data: ['待跟进', '潜在客户', '有意向', '高意向', '未成交', '已成交']
  },
  series: [
    {
      type: 'pie',
      radius: '50%',
      center: ['50%', '50%'],
      selectedMode: 'single',
      data: [
        { name: '待跟进', value: 0 },
        { name: '潜在客户', value: 0 },
        { name: '有意向', value: 0 },
        { name: '高意向', value: 0 },
        { name: '未成交', value: 0 },
        { name: '已成交', value: 0 }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}) as Ref<ECOption>;

const { domRef: status } = useEcharts(statusOptions);

const industryOptions = ref<ECOption>({
  title: {
    text: '客户跟进方式',
    left: 20
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    bottom: 10,
    left: 'center',
    data: ['微信沟通', '电话沟通', '线下拜访', '其他']
  },
  series: [
    {
      name: '数量',
      type: 'pie',
      radius: ['30%', '60%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 20,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: industryData.value
    }
  ]
}) as Ref<ECOption>;

const { domRef: industry } = useEcharts(industryOptions);

const mapOptions = ref({
  title: {
    text: '客户地址分布',
    left: '50%'
  },
  tooltip: {
    trigger: 'item',
    formatter(params) {
      return `${params.data.name} : ${params.data.value || 0}`;
      // return
    } // 鼠标悬停显示省份名称和数据
  },
  series: [
    {
      type: 'map',
      map: 'china', // 使用注册的中国地图
      // center: ['30%', '50%'],
      label: {
        show: false,
        color: '#000' // 显示省份名称
      },
      itemStyle: {
        areaColor: '#ddd',
        borderColor: '#fff',
        borderWidth: 1
      },
      emphasis: {
        itemStyle: {
          areaColor: '#FF7F50' // 鼠标悬停时高亮
        }
      },
      data: [
        // 其他省份的数据...
      ]
    }
  ],
  visualMap: {
    type: 'piecewise',
    pieces: [
      // 可以定义多段颜色映射，根据value的不同显示不同的颜色
      { gt: -1, lte: 0, color: '#cccccc' },
      { gt: 0, lte: 100, color: '#10B0FF' }, // value大于0且小于等于100的颜色
      { gt: 100, lte: 200, color: '#88F3AE' }, // value大于100且小于等于200的颜色
      { gt: 200, lte: 400, color: '#FF9261' },
      { gt: 400, lte: 1000, color: '#e4393c' }
    ]
  }
});

const followUpStatusOptions = ref<ECOption>({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      label: {
        backgroundColor: '#283b56'
      }
    }
  },
  title: {
    text: '跟进客户数',
    left: 20
  },
  xAxis: [
    {
      type: 'category',
      data: []
    }
  ],
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [],
      type: 'line',
      smooth: true,
      symbol: 'circle', // 设置为圆形
      symbolSize: 8, // 设置圆形的大小
      color: '#10B0FF', // 颜色
      itemStyle: {
        color: '#10B0FF', // 设置圆形的填充颜色
        borderColor: '#10B0FF' // 设置圆形的边框颜色
      }
    }
  ]
}) as Ref<ECOption>;

const { domRef: followUpStatus } = useEcharts(followUpStatusOptions);

const mapDom = ref<HTMLElement | null>(null);

const eChart = ref<any>(echarts);

function init() {
  return new Promise(resolve => {
    getClientAnalyze().then(res => {
      resolve(res);
      if (res.code === 200) {
        cardList.value[0].num = res.data.totalClient || 0;
        cardList.value[1].num = res.data.completeClient || 0;
        cardList.value[2].num = res.data.potentialClient || 0;
        cardList.value[3].num = res.data.normalClient || 0;
        cardList.value[4].num = res.data.importanceClient || 0;
        cardList.value[5].num = res.data.strategyClient || 0;

        const sourceAnalyze: Array<number> = [];
        const sourceAnalyze2: Array<string> = [];
        const sourceXAxisAnalyze: Array<string> = [];
        res.data.sourceAnalyze.forEach(item => {
          sourceAnalyze.push(item.totalClient);
          sourceXAxisAnalyze.push(item.leadSource);
          sourceAnalyze2.push(((item.totalClient / res.data.totalClient) * 100).toFixed(2));
        });
        if (gradeOptions.value.series) {
          gradeOptions.value.series[0].data = sourceAnalyze;
          gradeOptions.value.series[1].data = sourceAnalyze2;
        }
        if (gradeOptions.value.xAxis) {
          gradeOptions.value.xAxis[0].data = sourceXAxisAnalyze;
        }
        const industrySeriesData: any = [];
        res.data.typeAnalyze.forEach(item => {
          industrySeriesData.push({ name: item.attr1, value: item.totalClient });
        });
        if (industryOptions.value.series) {
          industryOptions.value.series[0].data = industrySeriesData;
        }
        const mapSeriesData: any = [];
        res.data.addressAnalyze.forEach(item => {
          mapSeriesData.push({ name: item.provinceName, value: item.customerCount });
        });
        mapOptions.value.series[0].data = mapSeriesData;

        res.data.statusAnalyze.forEach(item => {
          if (item.status) {
            const index = statusData.value.findIndex(ele => ele.name === item.status);
            if (index > -1) {
              statusData.value[index].value = item.totalClient;
            }
          }
        });
        if (statusOptions.value.series) {
          statusOptions.value.series[0].data = statusData.value;
        }

        res.data.typeAnalyze.forEach(item => {
          if (item.status) {
            const index = industryData.value.findIndex(ele => ele.name === item.type);
            if (index > -1) {
              industryData.value[index].value = item.totalClient;
            }
          }
        });
        if (industryOptions.value.series) {
          industryOptions.value.series[0].data = industryData.value;
        }

        const followUpXAxisData: any = [];
        const followUpSeriesData: any = [];
        res.data.interviewClientMonth.forEach(item => {
          followUpXAxisData.push(item.month);
          followUpSeriesData.push(item.count);
        });
        if (followUpStatusOptions.value.series) {
          followUpStatusOptions.value.series[0].data = followUpSeriesData;
        }
        if (followUpStatusOptions.value.xAxis) {
          followUpStatusOptions.value.xAxis[0].data = followUpXAxisData;
        }
      }
    });
  });
}

onMounted(async () => {
  await init();
  try {
    // 注册中国地图
    eChart.value.registerMap('china', geoJson);

    nextTick(() => {
      const chart = eChart.value.init(mapDom.value);

      chart.setOption(mapOptions.value);
    });
  } catch (error) {
    console.log('error ==?', error);
  }
});
</script>

<style lang="scss" scoped>
.card {
  padding-top: 15px;
  background-color: #fff;
  text-align: center;
  box-sizing: border-box;
  position: relative;
  cursor: pointer;
  &:hover {
    box-shadow: 0 0 3px 3px rgba($color: #ddd, $alpha: 0.3);
  }
  .container-content {
    left: 10px;
    overflow: hidden;
    position: absolute;
    right: 10px;
    bottom: 50px;
    .num {
      font-size: 30px;
    }
  }
}

.map-box {
  overflow-y: auto;
  overflow-x: hidden;
  position: relative;
}

.address {
  position: sticky;
  left: 20px;
  top: 20px;
  z-index: 999;
  font-size: 18px;
  font-weight: 600;
}

.map {
  position: absolute;
  left: -50%;
  top: -10%;
  z-index: 99;
  width: 200%;
  height: 180%;
}
</style>
