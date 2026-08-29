import { hiprint } from 'vue-plugin-hiprint';

export default function init(printElementData) {
  const data = [];
  if (Array.isArray(printElementData)) {
    printElementData.forEach(item => {
      data.push({
        tid: `${item.tid}`, // 唯一 key
        type: 'text', // 元素类型
        title: `${item.text}`, // 这里和 options 中的 title 一样。"拖拽时就显示" 优先级高于 options 的, "无法彻底删除"
        options: {
          title: `${item.text}`, // 标题, "拖进设计器才会显示"
          field: `${item.fieldName}`, // 字段名称
          testData: `\${${item.fieldName}}` // 测试数据
        }
      });
    });
  }
  function providerShould(context) {
    // 移除 旧的 defaultModule "元素"类型, 以避免重复
    context.removePrintElementTypes('defaultModule');
    // 添加 defaultModule "元素"类型
    context.addPrintElementTypes('defaultModule', [new hiprint.PrintElementTypeGroup('业务组件', data)]);

    // 移除 旧的 defaultModule "元素"类型, 以避免重复
    context.removePrintElementTypes('commonModule');
    // 添加 defaultModule "元素"类型
    context.addPrintElementTypes('commonModule', [
      // PrintElementTypeGroup 分组
      new hiprint.PrintElementTypeGroup('公共组件', [
        {
          tid: 'commonModule.textElement',
          type: 'text',
          title: '文本',
          options: {
            title: '文本'
          }
        },
        {
          tid: 'commonModule.imageElement',
          type: 'image',
          title: '图片',
          options: {
            title: '图片',
            field: 'imageUrl',
            testData: 'src/assets/images/profile.jpg'
          }
        },
        {
          tid: 'commonModule.htmlElement',
          type: 'html',
          title: 'HTML',
          options: {
            title: 'HTML',
            field: 'htmlContent',
            testData: '<div>默认HTML</div>'
          }
        },
        {
          tid: 'commonModule.tableElement',
          type: 'table',
          title: '表格',
          options: {
            title: '表格',
            field: 'tableData'
          }
        },
        {
          tid: 'commonModule.longTextElement',
          type: 'longText',
          title: '长文本',
          options: {
            title: '长文本',
            field: 'longTextContent',
            testData: '示例长文本'
          }
        },
        {
          tid: 'commonModule.qrcodeElement',
          type: 'qrcode',
          title: '二维码',
          options: {
            title: '二维码',
            field: 'qrcode'
          }
        },
        {
          tid: 'commonModule.barcodeElement',
          type: 'barcode',
          title: '条形码',
          options: {
            title: '条形码',
            field: 'barcode'
          }
        },
        {
          tid: 'commonModule.customElement',
          type: 'text',
          title: '自定义组件',
          options: {
            title: '自定义组件',
            field: 'customContent',
            testData: '自定义数据'
          }
        }
      ])
    ]);
    // 移除 旧的 defaultModule "元素"类型, 以避免重复
    context.removePrintElementTypes('auxiliaryModule');
    // 添加 defaultModule "元素"类型
    context.addPrintElementTypes('auxiliaryModule', [
      // PrintElementTypeGroup 分组
      new hiprint.PrintElementTypeGroup('辅助', [
        {
          tid: 'auxiliaryModule.verticalLineElement',
          type: 'vline',
          title: '竖线',
          options: {
            // 竖线配置项
            height: 100, // 默认高度
            color: '#000000' // 默认颜色
            // 其他需要的配置
          }
        },
        {
          tid: 'auxiliaryModule.horizontalLineElement',
          type: 'hline',
          title: '横线',
          options: {
            // 横线配置项
            width: 100, // 默认宽度
            color: '#000000' // 默认颜色
            // 其他需要的配置
          }
        },
        {
          tid: 'auxiliaryModule.rectangleElement',
          type: 'rect',
          title: '矩形',
          options: {
            // 矩形配置项
            width: 100, // 默认宽度
            height: 50, // 默认高度
            borderColor: '#000000', // 边框颜色
            borderWidth: 1 // 边框宽度
            // 其他需要的配置
          }
        },
        {
          tid: 'auxiliaryModule.ellipseElement',
          type: 'oval',
          title: '椭圆',
          options: {
            // 椭圆配置项
            rx: 50, // 椭圆的x方向半径
            ry: 25, // 椭圆的y方向半径
            borderColor: '#000000', // 边框颜色
            borderWidth: 1 // 边框大小
            // 其他需要的配置
          }
        }
      ])
    ]);
  }

  const providerList = providerShould;
  return {
    addElementTypes: providerList
  };
}
