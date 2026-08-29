<template>
  <a-modal v-model:open="visible" :mask-closable="false" :width="width + 'mm'" @cancel="hideModal">
    <a-spin :spinning="spinning" style="min-height: 100px">
      <div id="preview_content_design"></div>
    </a-spin>
    <template #title>
      <a-space>
        <div style="margin-right: 20px">打印预览</div>
        <a-button :loading="waitShowPrinter" type="primary" icon="printer" @click.stop="print">打印</a-button>
        <a-button type="primary" icon="printer" @click.stop="toPdf">pdf</a-button>
      </a-space>
    </template>
    <template #footer>
      <a-button key="close" type="info" @click="hideModal">关闭</a-button>
    </template>
  </a-modal>
</template>

<script>
// import { defineComponent } from '@vue/composition-api';
import { defineComponent } from 'vue';
export default defineComponent({
  name: 'PrintPreview',
  props: {},
  data() {
    return {
      visible: false,
      spinning: true,
      waitShowPrinter: false,
      // 纸张宽 mm
      width: 0,
      // 模板
      hiprintTemplate: {},
      // 数据
      printData: {}
    };
  },
  computed: {},
  watch: {},
  created() {
    console.log('created');
  },
  mounted() {
    console.log('created');
  },
  methods: {
    hideModal() {
      this.visible = false;
    },
    show(hiprintTemplate, printData, width = '210') {
      console.log('hiprintTemplate ==> ', hiprintTemplate);

      this.visible = true;
      this.spinning = true;
      this.width = width;
      this.hiprintTemplate = hiprintTemplate;
      this.printData = printData;
      setTimeout(() => {
        // eslint-disable-next-line no-undef
        $('#preview_content_design').html(hiprintTemplate.getHtml(printData));
        this.spinning = false;
      }, 500);
    },
    print() {
      this.waitShowPrinter = true;
      console.log('打印预览');
      this.hiprintTemplate.print(
        this.printData,
        {},
        {
          callback: () => {
            console.log('callback');
            this.waitShowPrinter = false;
          }
        }
      );
    },
    toPdf() {
      this.hiprintTemplate.toPdf({}, '打印预览');
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
