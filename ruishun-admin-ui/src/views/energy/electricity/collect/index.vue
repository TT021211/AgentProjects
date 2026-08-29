<template>
  <div style="background-color: #000d3d">
    <div ref="lineRef" class="w-full h-560px"></div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import type { Ref } from 'vue';
import { graphic } from 'echarts';
import { type ECOption, useEcharts } from '@/composables';
// type EChartsOption = echarts.EChartsOption;

const lineOptions = ref<any>({
  tooltip: {
    backgroundColor: 'rgba(0, 13, 26, 0.80)',
    borderColor: 'rgba(143, 225, 252, 0)',
    padding: 8,
    textStyle: {
      color: '#fff'
    },
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    },
    formatter(params) {
      let str = '';
      for (let i = 0; i < params.length; i++) {
        if (params[i].seriesName !== '') {
          str += `${params[i].seriesName}: ${params[i].value}`;
        }
      }
      return str;
    }
  },
  xAxis: {
    type: 'category',
    data: ['大拉', '中拉', '小拉', '微拉', '漆包', '连拉连包'],
    axisLabel: {
      show: true,
      textStyle: {
        fontSize: 15 // 字体大小
      }
    }
  },
  legend: {
    data: ['日', '月', '季', '年'],
    icon: 'rect',
    itemWidth: 14,
    itemHeight: 14,
    right: 25,
    top: 10,
    textStyle: {
      fontSize: 14,
      color: '#FFFFFF'
    }
  },
  yAxis: [
    {
      type: 'value',
      name: '',
      max: 100,
      minInterval: 20,
      axisLabel: {
        textStyle: {
          color: '#F5F5F5', // 更改坐标轴文字颜色
          fontSize: 12 // 更改坐标轴文字大小
        },
        formatter: '{value}'
      },
      splitLine: {
        // 网格
        show: true,
        lineStyle: {
          color: '#dadde4',
          type: 'dashed'
        }
      }
    }
  ],
  series: [
    {
      name: '日',
      type: 'bar',
      itemStyle: {
        borderWidth: 1,
        opacity: 1, // 这个是 透明度
        // normal: {
        // 	color: 'rgba(54,164,255,0.35)',
        // 	borderColor: '#1578D8',
        // 	borderWidth: 1
        // },
        color: new graphic.LinearGradient(
          0,
          1,
          0,
          0,
          [
            {
              offset: 0,
              color: 'rgba(0,168,251, 0)' // 0% 处的颜色
            },
            {
              offset: 1,
              color: 'rgba(9,61,107, 1)' // 100% 处的颜色
            }
          ],
          false
        )
      },
      // 实现数字展示在柱状图
      label: {
        show: false,
        position: 'top',
        fontSize: 12,
        color: '#F5F5F5',
        offset: [0, -10],
        formatter: '{c}' // 添加单位
      },
      data: [48, 67, 81, 97, 77, 66, 82, 72]
    },
    {
      name: '月',
      type: 'bar',
      itemStyle: {
        borderWidth: 1,
        // lenged文本
        opacity: 1, // 这个是 透明度
        // normal: {
        // 	color: 'rgba(220,185,25, 1)',
        // 	borderColor: '#837734',
        // 	borderWidth: 1
        // },
        color: new graphic.LinearGradient(
          0,
          1,
          0,
          0,
          [
            {
              offset: 0,
              color: 'rgba(220,185,25, 1)' // 100% 处的颜色
            },
            {
              offset: 1,

              color: 'rgba(39,76,85, 1)' // 0% 处的颜色
            }
          ],
          false
        )
      },
      label: {
        show: false,
        position: 'top',
        fontSize: 12,
        color: '#F5F5F5',
        offset: [0, -10],
        formatter: '{c}' // 添加单位
      },
      data: [63, 38, 60, 48, 85, 95, 37, 25]
    },
    {
      name: '季',
      type: 'bar',
      itemStyle: {
        // lenged文本
        opacity: 1, // 这个是 透明度
        // normal: {
        // 	color: 'rgba(220,185,25, 1)',
        // 	borderColor: '#837734',
        // 	borderWidth: 1
        // },
        color: new graphic.LinearGradient(
          0,
          1,
          0,
          0,
          [
            {
              offset: 0,
              color: 'rgba(18,212,227,0.9)' //
            },
            {
              offset: 1,

              color: 'rgba(10,59,100, 0.3)' //
            }
          ],
          false
        )
      },
      // 实现数字展示在柱状图
      label: {
        show: false,
        position: 'top',
        fontSize: 12,
        color: '#F5F5F5',
        offset: [0, -10],
        formatter: '{c}' // 添加单位
      },
      data: [68, 17, 82, 77, 37, 66, 92, 72]
    },
    {
      name: '年',
      type: 'bar',
      itemStyle: {
        // lenged文本
        opacity: 1, // 这个是 透明度
        // normal: {
        // 	color: 'rgba(220,185,25, 1)',
        // 	borderColor: '#837734',
        // 	borderWidth: 1
        // },
        color: new graphic.LinearGradient(
          0,
          1,
          0,
          0,
          [
            {
              offset: 0,
              color: 'rgba(18,212,227,0.9)' //
            },
            {
              offset: 1,

              color: 'rgba(10,59,100, 0.3)' //
            }
          ],
          false
        )
      },
      // 实现数字展示在柱状图
      label: {
        show: false,
        position: 'top',
        fontSize: 12,
        color: '#F5F5F5',
        offset: [0, -10],
        formatter: '{c}' // 添加单位
      },
      data: [68, 17, 82, 77, 37, 66, 92, 72]
    }
  ]
}) as Ref<ECOption>;

const { domRef: lineRef } = useEcharts(lineOptions);

onMounted(() => {
  console.log(11112);
});
</script>

<style lang="scss" scoped></style>
