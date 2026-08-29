<template>
  <div id="container1" :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from 'echarts';
import { defineComponent } from '@vue/composition-api';
import resize from './dashboard/mixins/resize';
import {option} from './options/left_mid_options';
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
      default: '400px'
    },
    height: {
      type: String,
      default: '200px'
    },
		productionData : {
			type : Object,
			default () {
				return {}
			}
		}
  },
  data() {
    return {
      loading: true,
      dataForCount: [],
      dataForWeight: [],
			chartOptions : {}
    };
  },
  async mounted() {
    // await this.getTypeData()
		this.chartOptions =JSON.parse(JSON.stringify(option))
    this.initChart()
  },
	watch : {
		productionData : {
			handler (newValue) {
				let series1 = [];
			let series2 = [];
			let xAxisData = []
				const mesProductWorkorderWeek = newValue.mesProductWorkorderWeek || [];
				mesProductWorkorderWeek.forEach(item => {
					xAxisData.push(item.date);
					series1.push(item.schedulingNum);
					series2.push(item.completedNum);
				});
				this.chartOptions.xAxis[0].data = xAxisData;
				this.chartOptions.series[0].data = series1;
				this.chartOptions.series[1].data = series2;
			 this.initChart();
		},
		deep : true
		}
	},
  // beforeUnmount() {
  //   if (!this.myChart) {
  //     return;
  //   }
  //   this.myChart.dispose();
  //   this.myChart = null;
  // },
  methods: {
    initChart() {
      let myChart = echarts.init(this.$el, 'macarons');
      myChart.setOption(this.chartOptions);
				this.$nextTick(() => {
					myChart.resize()
					this.$emit('endLoading', 2)
				})
    }
  }
});
</script>
