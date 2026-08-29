<template>
  <div>
    <div class="controls">
      <label for="granularity">时间区间:</label>
      <select id="granularity" v-model="granularity">
        <option value="1">天</option>
      </select>
    </div>
    <div ref="chartRef" :style="{ height: height, width: width }"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import { defineComponent } from '@vue/composition-api';
import resize from './dashboard/mixins/resize';
export default defineComponent({
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '150vh'
    },
    height: {
      type: String,
      default: '400px'
    }
  },
  data() {
    return {
      granularity: '1',
      myChart: null,
      realData: {
        daily: [],
        monthly: [],
        yearly: []
      },
      animationInterval: null,
      timeAxes: {
        daily: [],
        monthly: [],
        yearly: []
      }
    };
  },
  watch: {
    realData: {
      handler() {
        this.initChart();
      },
      deep: true
    },
    granularity() {
      this.getData();
    }
  },
  mounted() {
    this.getData();
  },
  beforeUnmount() {
    if (this.animationInterval) {
      clearInterval(this.animationInterval);
    }
    if (this.myChart) {
      this.myChart.dispose();
      this.myChart = null;
    }
  },
  methods: {
    getRandom(min, max) {
      return Math.floor(Math.random() * (max - min + 1) + min);
    },
    getData() {
      this.timeAxes.daily = [];
      this.realData.daily = [];
      // 获取当前时间
      const now = new Date();
      const hours = 24; // 显示过去 24 小时

      // 生成过去 24 小时的时间轴
      for (let i = hours - 1; i >= 0; i--) {
        const time = new Date(now.getTime() - i * 60 * 60 * 1000); // 每小时递减
        const hourStr = time.toLocaleTimeString('zh-CN', {
          hour12: false,
          hour: '2-digit',
          minute: '2-digit'
        }); // 格式如 "14:00"
        this.timeAxes.daily.push(hourStr);

        // 为每个产品型号生成数据
        ['LY05', 'SC05'].forEach((model, index) => {
          this.realData.daily.push({
            productModel: model,
            weight: this.getRandom(15, 20),
            date: hourStr,
            percentage: '96.18%',
            qualityStatus: '合格',
            id: `${model}-${hourStr}-${index}`
          });
        });
      }
      this.initChart();
    },
    initChart() {
      if (!this.$refs.chartRef) return;

      // 初始化 ECharts
      // eslint-disable-next-line import/namespace
      this.myChart = echarts.init(this.$refs.chartRef);
      const rawData = this.realData.daily || [];
      // eslint-disable-next-line no-constant-binary-expression
      const times = [...new Set(this.timeAxes.daily)] || [];
      const models = [...new Set(rawData.map(item => item.productModel))];
      // 转换为 ECharts 格式
      const series = models.map((model, index) => ({
        name: model,
        type: 'line',
        data: times.map(time => {
          const record = rawData.find(item => item.productModel === model && item.date === time);
          return record ? record.weight : null;
        }),
        itemStyle: {
          color: ['#5470c6', '#91cc75', '#fac858', '#ee6666'][index % 4]
        }
      }));
      // console.log(series);
      // ECharts 配置
      const option = {
        tooltip: {
          trigger: 'item',
          confine: true,
          formatter: params => {
            const record = rawData.find(r => r.productModel === params.seriesName && r.date === params.name);
            if (!record) return '无数据';
            const statusColor = params.value < 16 || params.value > 19 ? 'red' : 'green';
            return `
						批次: ${params.seriesName}<br/>
						合格率: ${record.percentage}<br/>
						重量: ${params.value} kg<br/>
						质量状态: <span style="color:${statusColor};">${params.value < 16 || params.value > 19 ? '不合格' : '合格'}</span> `;
          }
        },
        title: {
          // eslint-disable-next-line no-nested-ternary
          text: `质检合格率 (${this.granularity === '1' ? '天' : this.granularity === '2' ? '月' : '年'})`,
          left: 'center',
          textStyle: { fontSize: 16 }
        },
        xAxis: {
          type: 'category',
          data: times,
          axisLabel: {
            interval: 0,
            rotate: this.granularity === 'hourly' ? 45 : 0,
            fontSize: 10
          }
        },
        yAxis: {
          min: 10,
          max: 24,
          type: 'value',
          name: '重量(kg)',
          nameTextStyle: { fontSize: 12 }
        },
        series,
        animationDuration: 10000,
        animationDurationUpdate: 200,
        animationEasing: 'linear',
        dataZoom: [
          {
            type: 'slider',
            xAxisIndex: 0,
            filterMode: 'none'
          }
        ],
        grid: {
          right: 100,
          bottom: 80,
          left: 50
        },
        graphic: {
          elements: models.map((model, index) => ({
            type: 'text',
            right: 20,
            top: 30 + index * 20,
            style: {
              text: model,
              font: '12px Arial',
              fill: ['#5470c6', '#91cc75', '#fac858', '#ee6666'][index % 4]
            },
            z: 100
          }))
        }
      };

      this.myChart.setOption(option, true);
      this.myChart.on('click', params => {
        if (params.value < 16 || params.value > 19) {
          const { seriesName, name } = params;
          const record = rawData.find(r => r.productModel === seriesName && r.date === name);
          this.realData.daily = this.realData.daily.filter(f => record.id !== f.id);
          record.weight = this.getRandom(15, 20);
          this.realData.daily.push({ ...record });
          this.initChart();
          // delRecordingById(record.id);
          // this.getData();
        }
      });
      // 清除旧动画
      if (this.animationInterval) {
        clearInterval(this.animationInterval);
      }

      // 动画循环
      let currentTimeIndex = 0;
      const animate = () => {
        if (currentTimeIndex >= times.length) currentTimeIndex = 0;
        const currentTime = times[currentTimeIndex];
        const currentData = rawData.filter(item => item.date === currentTime);
        currentData.sort((a, b) => b.weight - a.weight);

        this.myChart.setOption({
          graphic: currentData.map((item, index) => ({
            type: 'text',
            right: 20,
            top: 30 + index * 20,
            style: {
              text: `${item.productModel}: ${item.weight}kg`,
              font: '12px Arial',
              fill: ['#5470c6', '#91cc75', '#fac858', '#ee6666'][index % 4]
            },
            z: 100
          }))
        });

        currentTimeIndex++;
      };

      animate();
      this.animationInterval = setInterval(animate, 1000);

      // 触发 resize
      this.$nextTick(() => {
        this.myChart.resize();
      });
    }
  }
});
</script>

<style scoped>
.controls {
  margin-bottom: 10px;
}
select {
  padding: 5px;
  font-size: 14px;
}
</style>
