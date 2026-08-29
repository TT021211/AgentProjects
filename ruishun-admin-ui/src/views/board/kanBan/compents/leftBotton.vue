<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from 'echarts';
// import { defineComponent } from '@vue/composition-api';
import { defineComponent } from 'vue';
import resize from './dashboard/mixins/resize';
export default defineComponent({
  mixins: [resize],
  props: {
    year: null,
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '480px'
    },
    height: {
      type: String,
      default: '400px'
    }
  },
  data() {
    return {
      chart: null,
      loading: true,
      dataForCount: [],
      dataForWeight: []
    };
  },
  async mounted() {
    // await this.getTypeData()
    // this.initChart()
  },
  beforeUnmount() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart(data1, data2) {
      this.chart = echarts.init(this.$el, 'macarons');
      this.chart.setOption({
        title: {
          text: ''
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 1],
          show: false,
          splitLine: {
            show: false
          },
          splitArea: { show: false }
        },
        yAxis: {
          type: 'category',
          data: data1.slice(0, 15)
        },
        series: [
          {
            name: '',
            type: 'bar',
            label: {
              show: true,
              position: 'right'
            },
            data: data2.slice(0, 15)
          }
        ]
      });
    }
  }
});
</script>
