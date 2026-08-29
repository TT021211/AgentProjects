// 出入库标签

const boxButtons = [
  // {
  //   id: 1,
  //   tid: 'defaultModule.codeSn',
  //   text: '二维码条码',
  //   iconClass: 'iconfont sv-text',
  //   fieldName: 'codeSn'
  // },
  {
    id: 2,
    tid: 'defaultModule.trader',
    text: '贸易商名称',
    iconClass: 'iconfont sv-text',
    fieldName: 'trader'
  },
  {
    id: 3,
    tid: 'defaultModule.materialCode',
    text: '物料编码',
    iconClass: 'iconfont sv-text',
    fieldName: 'materialCode'
  },
  {
    id: 4,
    tid: 'defaultModule.batchNumber2',
    text: '批号',
    iconClass: 'iconfont sv-text',
    fieldName: 'batchNumber2'
  },
  {
    id: 5,
    tid: 'defaultModule.productName',
    text: '品名',
    iconClass: 'iconfont sv-text',
    fieldName: 'productName'
  },
  {
    id: 6,
    tid: 'defaultModule.wmsSpec',
    text: '规格',
    iconClass: 'iconfont sv-text',
    fieldName: 'wmsSpec'
  },
  {
    id: 7,
    tid: 'defaultModule.netWeight',
    text: '净重',
    iconClass: 'iconfont sv-text',
    fieldName: 'netWeight'
  },
  {
    id: 8,
    tid: 'defaultModule.grossWeight',
    text: '毛重',
    iconClass: 'iconfont sv-text',
    fieldName: 'grossWeight'
  },
  {
    id: 9,
    tid: 'defaultModule.axleLoad',
    text: '皮重',
    iconClass: 'iconfont sv-text',
    fieldName: 'axleLoad'
  },
  {
    id: 10,
    tid: 'defaultModule.createTime',
    text: '日期',
    iconClass: 'iconfont sv-text',
    fieldName: 'createTime'
  },
  {
    id: 11,
    tid: 'defaultModule.materialNumber',
    text: '材料厂家',
    iconClass: 'iconfont sv-text',
    fieldName: 'materialNumber'
  },
  {
    id: 12,
    tid: 'defaultModule.boxPerNumber',
    text: '每箱盘数',
    iconClass: 'iconfont sv-text',
    fieldName: 'boxPerNumber'
  },
  {
    id: 13,
    tid: 'defaultModule.boxSerialNumber',
    text: '箱号',
    iconClass: 'iconfont sv-text',
    fieldName: 'boxSerialNumber'
  },
  {
    id: 18,
    tid: 'defaultModule.installationNumber',
    text: '箱数',
    iconClass: 'iconfont sv-text',
    fieldName: 'installationNumber'
  },
  {
    id: 14,
    tid: 'defaultModule.wmsUnit',
    text: '单位',
    iconClass: 'iconfont sv-text',
    fieldName: 'wmsUnit'
  },
  {
    id: 15,
    tid: 'defaultModule.createBy',
    text: '发货人',
    iconClass: 'iconfont sv-text',
    fieldName: 'createBy'
  },
  {
    id: 16,
    tid: 'defaultModule.shipmentDate',
    text: '发货日期',
    iconClass: 'iconfont sv-text',
    fieldName: 'shipmentDate'
  },
  {
    id: 17,
    tid: 'defaultModule.snidCode',
    text: '产品链接',
    iconClass: 'iconfont sv-text',
    fieldName: 'snidCode'
  }
];
// 公共标签
const CommonButtons = [
  {
    id: 1,
    tid: 'commonModule.textElement',
    text: '文本',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 2,
    tid: 'commonModule.imageElement',
    text: '图片',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 3,
    tid: 'commonModule.htmlElement',
    text: 'HTML',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 4,
    tid: 'commonModule.tableElement',
    text: '表格',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 5,
    tid: 'commonModule.longTextElement',
    text: '长文本',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 6,
    tid: 'commonModule.qrcodeElement',
    text: '二维码',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 7,
    tid: 'commonModule.barcodeElement',
    text: '条形码',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 8,
    tid: 'commonModule.customElement',
    text: '自定义组件',
    iconClass: 'iconfont sv-text'
  }
];
// 辅助标签
const auxiliaryButtons = [
  {
    id: 1,
    tid: 'auxiliaryModule.verticalLineElement',
    text: '竖线',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 2,
    tid: 'auxiliaryModule.horizontalLineElement',
    text: '横线',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 3,
    tid: 'auxiliaryModule.rectangleElement',
    text: '矩形',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 4,
    tid: 'auxiliaryModule.ellipseElement',
    text: '椭圆',
    iconClass: 'iconfont sv-text'
  }
];
// 设备标签
const deviceButtons = [
  {
    id: 1,
    tid: 'deviceModule.machineryName',
    text: '设备名称',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 2,
    tid: 'deviceModule.machineryCode',
    text: '设备编号',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 3,
    tid: 'deviceModule.machineryTypeName',
    text: '设备类型',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 4,
    tid: 'deviceModule.machineryBrand',
    text: '品牌',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 5,
    tid: 'deviceModule.machinerySpec',
    text: '规格型号',
    iconClass: 'iconfont sv-text'
  }
];
// 包装箱标签
const packageButtons = [
  {
    id: 1,
    tid: 'packageModule.packageCode',
    text: '箱码',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 2,
    tid: 'packageModule.packageDate',
    text: '装箱日期',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 3,
    tid: 'packageModule.clientName',
    text: '客户名称',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 4,
    tid: 'packageModule.packageLength',
    text: '箱长度',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 5,
    tid: 'packageModule.packageWidth',
    text: '箱宽度',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 6,
    tid: 'packageModule.packageHeight',
    text: '箱高度',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 7,
    tid: 'packageModule.packageWidth',
    text: '箱宽度',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 8,
    tid: 'packageModule.netWeight',
    text: '净重',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 9,
    tid: 'packageModule.crossWeight',
    text: '毛重',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 10,
    tid: 'packageModule.itemNames',
    text: '产品名称',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 11,
    tid: 'packageModule.itemNamesCount',
    text: '产品名称+数量',
    iconClass: 'iconfont sv-text'
  }
];
// 物资标签
const stockButtons = [
  {
    id: 1,
    tid: 'stockModule.itemName',
    text: '物料名称',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 2,
    tid: 'stockModule.itemCode',
    text: '物料编码',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 3,
    tid: 'stockModule.specification',
    text: '规格型号',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 4,
    tid: 'stockModule.unitOfMeasure',
    text: '单位',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 5,
    tid: 'stockModule.batchCode',
    text: '入库批次号',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 6,
    tid: 'stockModule.vendorName',
    text: '供应商名称',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 7,
    tid: 'stockModule.recptDate',
    text: '入库日期',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 8,
    tid: 'stockModule.stockCode',
    text: '物资编码',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 9,
    tid: 'stockModule.warehouseCode',
    text: '仓库编码',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 10,
    tid: 'stockModule.warehouseName',
    text: '仓库名称',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 11,
    tid: 'stockModule.locationCode',
    text: '库区编码',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 12,
    tid: 'stockModule.locationName',
    text: '库区名称',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 13,
    tid: 'stockModule.areaCode',
    text: '库位编码',
    iconClass: 'iconfont sv-text'
  },
  {
    id: 14,
    tid: 'stockModule.areaName',
    text: '库位名称',
    iconClass: 'iconfont sv-text'
  }
];
// 导出数组
export { boxButtons, CommonButtons, auxiliaryButtons, deviceButtons, packageButtons, stockButtons };
