<template>
	  <div class="chart-container">
	    <dv-bar :config="barConfig"></dv-bar>
	  </div>
	</template>

<script>
import { ref } from 'vue';
import { dvBar } from '@jiaminghi/data-view';
// import {defineComponent} from "@vue/composition-api";
import { defineComponent } from 'vue';
export default defineComponent({
	  components: { dvBar },
  setup() {
	    const barConfig = ref({
		      data: [
		        { name: 'Category A', value: 120 },
		        { name: 'Category B', value: 80 },
		        // 更多数据...
		      ],
		      xField: 'name',
		      yField: 'value',
		      meta: {
		        name: { alias: '类别' },
		        value: { alias: '数值' },
		      },
	      appendPadding: 10,
		    });

	    return { barConfig };
	  },
});
</script>

<style scoped>
.chart-container {
	  width: 100%;
	  height: 400px;
	}
</style>
