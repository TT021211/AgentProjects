<template>
  <div>
    <a-button type="primary" @click="show">查看模板json</a-button>
    <a-modal v-model:open="visible" :mask-closable="false" @cancel="hideModal">
      <a-spin :spinning="spinning" style="min-height: 100px">
        <a-textarea v-model:value="jsonOut" style="width: 40vw; height: 50vh" />
      </a-spin>
      <template #title>
        <a-space>
          <div style="margin-right: 20px">JSON</div>
          <a-switch
            v-model:checked="tidMode"
            checked-children="tid模式"
            un-checked-children="默认"
            @change="onModeChange"
          />
          <a-switch
            v-model:checked="beautify"
            checked-children="美化"
            un-checked-children="压缩"
            @change="onModeChange"
          />
        </a-space>
      </template>
      <template #footer>
        <a-button key="close" type="info" @click="hideModal">关闭</a-button>
      </template>
    </a-modal>
  </div>
</template>

<script>
// import { defineComponent } from '@vue/composition-api';
import { defineComponent } from 'vue';
export default defineComponent({
  name: 'JSONView',
  props: {
    template: {
      type: Object
    }
  },
  data() {
    return {
      visible: false,
      spinning: true,
      jsonOut: '',
      tidMode: false,
      beautify: false
    };
  },
  computed: {},
  watch: {},
  created() {
    console.log('created');
  },
  mounted() {
    console.log('mounted');
  },
  methods: {
    hideModal() {
      this.visible = false;
    },
    show() {
      this.visible = true;
      this.spinning = true;
      setTimeout(() => {
        const json = this.tidMode ? this.template.getJsonTid() : this.template.getJson();
        const beautify = this.beautify ? 2 : 0;
        this.jsonOut = JSON.stringify(json, null, beautify);
        this.spinning = false;
      }, 500);
    },
    onModeChange() {
      this.show();
    }
  }
});
</script>
<style lang="less" scoped>
/deep/ .ant-modal-body {
  padding: 0px;
}

/deep/ .ant-modal-content {
  margin-bottom: 24px;
}
</style>
