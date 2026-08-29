<template>
	<div id="container1" :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from 'echarts';
import { defineComponent } from '@vue/composition-api';
import resize from './dashboard/mixins/resize';
import {option} from './options/right_bot_options';
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
		energyData : {
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
		this.chartOptions = JSON.parse(JSON.stringify(option))
		this.initChart()
	},
	watch : {
		energyData : {
			handler (newValue) {
				this.chartOptions.xAxis.data = newValue.xAxisData;
				this.chartOptions.series[0].data = newValue.LSSeriesData
				this.chartOptions.series[1].data = newValue.GYSeriesData
			 	this.initChart();
		},
		deep : true
		},
	},
	beforeUnmount() {
		// if (!this.myChart) {
		//   return;
		// }
		// this.myChart.dispose();
		// this.myChart = null;
	},
	methods: {
		initChart() {
			let myChart = echarts.init(this.$el, 'macarons');
			myChart.setOption(this.chartOptions);
			this.$nextTick(() => {
				myChart.resize()
				this.$emit('endLoading', 6)
			})
		}
	}
});
</script>
