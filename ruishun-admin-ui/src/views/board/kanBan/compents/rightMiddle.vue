<template>
  <div id="container1" :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from 'echarts';
import { defineComponent } from '@vue/composition-api';
import resize from './dashboard/mixins/resize';
import {option} from './options/right_mid_options';
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
		deviceCount : {
			type: Number,
      default: 0
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
    // this.initChart()
		this.chartOptions = JSON.parse(JSON.stringify(option))
		this.chartOptions.title.text = `${this.deviceCount}`;
		this.initChart()
  },
	watch : {
		deviceCount (newValue) {
			this.chartOptions.title.text = `${newValue}`;
			this.initChart()
		}
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
					this.$emit('endLoading', 5)
				})
    }
  }
});
</script>
