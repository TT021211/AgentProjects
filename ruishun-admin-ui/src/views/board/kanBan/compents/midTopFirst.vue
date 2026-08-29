<template>
	<div :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from 'echarts';
// import { defineComponent } from '@vue/composition-api';
import resize from './dashboard/mixins/resize';
import {option} from './options/mid_top_left_first_options';
import { defineComponent } from 'vue';
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
		}
	},
	data() {
		return {
			// myChart: null,
			loading: true,
			dataForCount: [],
			dataForWeight: []
		};
	},
	async mounted() {
		// await this.getTypeData()
		this.initChart()
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
			myChart.setOption(option);
			this.$nextTick(() => {
				myChart.resize()
				this.$emit('endLoading', 8)
			})
		}
	}
});
</script>
