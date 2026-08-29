<template>
  <n-grid :x-gap="16" :y-gap="16" :item-responsive="true">
    <n-grid-item span="0:24 640:24 1024:6">
      <n-card :bordered="false" class="rounded-8px shadow-sm">
        <div class="w-full h-360px py-12px">
          <h3 class="text-16px font-bold">销售统计</h3>
          <p class="text-#aaa">Sales Statistics</p>
          <h3 class="pt-32px text-24px font-bold">
            <count-to prefix="$" :start-value="0" :end-value="7754" />
          </h3>
          <p class="text-#aaa">今日销售额</p>
          <h3 class="pt-32px text-24px font-bold">
            <count-to :start-value="0" :end-value="1234" />
          </h3>
          <p class="text-#aaa">今日销售重量</p>
          <!-- <n-button class="mt-24px whitespace-pre-wrap" type="primary">Last Month Summary</n-button> -->
        </div>
      </n-card>
    </n-grid-item>
    <n-grid-item span="0:24 640:24 1024:10">
      <n-card :bordered="false" class="rounded-8px shadow-sm">
        <div ref="lineRef" class="w-full h-360px"></div>
      </n-card>
    </n-grid-item>
    <n-grid-item span="0:24 640:24 1024:8">
      <n-card :bordered="false" class="rounded-8px shadow-sm">
        <div ref="pieRef" class="w-full h-360px"></div>
      </n-card>
    </n-grid-item>
  </n-grid>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import type { Ref } from 'vue';
import { type ECOption, useEcharts } from '@/composables';

defineOptions({ name: 'DashboardAnalysisTopCard' });

const myData = ref<any[]>(['fda']);
onMounted(() => {
  // eslint-disable-next-line no-plusplus
  for (let i = 0; i < 1000; i++) {
    myData.value.push(i);
  }
  // setInterval(() => {
  //   myData.value.push('321');
  // }, 1000);
});
const lineOptions = ref<ECOption>({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      label: {
        backgroundColor: '#6a7985'
      }
    },
    formatter: '{b}<br/>{a}: {c}<br/>{a1}: {c1}'
  },
  legend: {
    data: ['出库量', '入库量']
  },

  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: [
    {
      type: 'category',
      boundaryGap: false,
      data: myData.value,
      splitLine: {
        interval: 50000 // 分隔线间隔
      }
    }
  ],
  yAxis: [
    {
      type: 'value'
    }
  ],
  series: [
    {
      color: '#8e9dff',
      name: '出库量',
      type: 'line',
      smooth: true,
      stack: 'Total',
      areaStyle: {
        color: {
          type: 'linear',
          x: 0,
          y: 0,
          x2: 0,
          y2: 1,
          colorStops: [
            {
              offset: 0.25,
              color: '#8e9dff'
            },
            {
              offset: 1,
              color: '#fff'
            }
          ]
        }
      },
      emphasis: {
        focus: 'series'
      },
      data: myData.value
    },
    {
      color: '#26deca',
      name: '入库量',
      type: 'line',
      smooth: true,
      stack: 'Total',
      areaStyle: {
        color: {
          type: 'linear',
          x: 0,
          y: 0,
          x2: 0,
          y2: 1,
          colorStops: [
            {
              offset: 0.25,
              color: '#26deca'
            },
            {
              offset: 1,
              color: '#fff'
            }
          ]
        }
      },
      emphasis: {
        focus: 'series'
      },
      data: [2208, 2016, 2916, 4512, 8281, 2008, 1963, 2367, 2956, 678]
    }
  ]
}) as Ref<ECOption>;
const { domRef: lineRef } = useEcharts(lineOptions);

const pieOptions = ref<ECOption>({
  tooltip: {
    trigger: 'item'
  },
  legend: {
    bottom: '1%',
    left: 'center',
    itemStyle: {
      borderWidth: 0
    }
  },
  series: [
    {
      startAngle: 180,
      color: [
        {
          type: 'linear',
          x: 0,
          y: 0,
          x2: 0,
          y2: 1,
          colorStops: [
            {
              offset: 0,
              color: 'red' // 0% 处的颜色
            },
            {
              offset: 1,
              color: 'blue' // 100% 处的颜色
            }
          ],
          global: false // 缺省为 false
        },
        {
          type: 'linear',
          x: 0,
          y: 0,
          x2: 0,
          y2: 1,
          colorStops: [
            {
              offset: 0,
              color: 'skyblue' // 0% 处的颜色
            },
            {
              offset: 1,
              color: 'blue' // 100% 处的颜色
            }
          ],
          global: false // 缺省为 false
        }
      ],
      name: '库存数量',
      type: 'pie',
      radius: ['45%', '75%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 1
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: '12'
        }
      },
      labelLine: {
        show: false
      },
      data: [
        { value: 20, name: 'QZ' },
        { value: 10, name: 'QA' },
        { value: 30, name: 'QZY' },
        { value: 40, name: 'OY' }
      ]
    }
  ]
}) as Ref<ECOption>;
// let angle = 180;
const { domRef: pieRef } = useEcharts(pieOptions);
// onMounted(() => {
//   setInterval(() => {
//     angle += 5;
//     if (pieOptions.value && pieOptions.value.series && pieOptions.value.series[0]) {
//       pieOptions.value.series[0].startAngle = angle;
//       // pieRef.value?.setOption({series:[startAngle: angle]});
//     }
//   }, 1000);
// });
</script>

<style scoped></style>
