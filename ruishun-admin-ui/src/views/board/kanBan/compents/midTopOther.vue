<template>
	<div :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from 'echarts';
import { defineComponent } from '@vue/composition-api';
import resize from './dashboard/mixins/resize';
import {option} from './options/mid_top_left_other_options';
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
			default: '230px'
		},
		chartData : {
			type : Object,
			default () {
				return {}
			}
  }
	},
	data() {
		return {
			// myChart: null,
			loading: true,
			dataForCount: [],
			dataForWeight: [],
			chartOptions : {}
		};
	},
	async mounted() {
		this.chartOptions = JSON.parse(JSON.stringify(option));
		this.chartOptions.title[0].text = this.chartData.week;
		this.chartOptions.title[1].text = this.chartData.carryNum;
		this.initChart()
	},
	// watch : {
	// 	chartData : {
	// 		handler (newValue) {
	// 		console.log('newValue ==>',newValue);

	// 		//  this.initChart();
	// 	},
	// 	deep : true
	// 	}
	// },
	beforeUnmount() {
		// if (!this.myChart) {
		// 	return;
		// }
		// this.myChart.dispose();
		// this.myChart = null;
	},
	methods: {
		initChart() {
			let myChart = echarts.init(this.$el);
			myChart.setOption(this.chartOptions);
			this.$nextTick(() => {
				myChart.resize()
				this.$emit('endLoading', 8)
			})
		}
	}
});
</script>
