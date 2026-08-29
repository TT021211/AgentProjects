<template>
  <div :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from 'echarts';
import { defineComponent } from '@vue/composition-api';
import resize from './dashboard/mixins/resize';
import option from './options/left_top_options';
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
		detectionDayNum : {
			type : Number,
			default : 0
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
  // async mounted() {
  //   // await this.getTypeData()

  // },
	watch : {
		detectionDayNum (newValue) {
			option.title.subtext =newValue
			this.initChart()
		}
	},
  beforeUnmount() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons');
      this.chart.setOption(option);
				this.$nextTick(() => {
					this.chart.resize()
					this.$emit('endLoading', 1)
				})
    }
  }
});
</script>
