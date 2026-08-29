<template>
	<div :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from 'echarts';
import { defineComponent } from '@vue/composition-api';
import resize from './dashboard/mixins/resize';
import {option,data} from './options/mid_top_right_options';
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
		productionData : {
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
		// await this.getTypeData()
		this.chartOptions = JSON.parse(JSON.stringify(option))
	},
	watch : {
		productionData : {
			handler (newValue) {
				let count = Number(newValue.completeWorkorder) + Number(newValue.productionWorkorder) + Number(newValue.schedulingWorkorder);
				data[0].value = count
				this.chartOptions.title[0].text = `{a|${count}}{b|}`;
				this.initChart()
		},
		deep : true
		}
	},
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
				this.$emit('endLoading', 9)
			})
		}
	}
});
</script>
