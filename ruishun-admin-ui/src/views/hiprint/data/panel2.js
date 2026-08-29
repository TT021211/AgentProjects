export default {
  panels: [
    {
      index: 0,
      name: 1,
      height: 297,
      width: 210,
      paperHeader: 49.5,
      paperFooter: 780,
      printElements: [
        {
          options: {
            left: 175.5,
            top: 10.5,
            height: 27,
            width: 259,
            title: '合同报表',
            fontSize: 19,
            fontWeight: '600',
            textAlign: 'center',
            lineHeight: 26,
            coordinateSync: true,
            widthHeightSync: true,
            draggable: false,
            qrCodeLevel: 0
          },
          printElementType: {
            title: '自定义文本',
            type: 'text'
          }
        },
        {
          options: {
            left: 462,
            top: 51,
            height: 22.5,
            width: 123,
            title: '123456789',
            fontFamily: '微软雅黑',
            textAlign: 'center',
            textType: 'barcode',
            right: 584.25,
            bottom: 73.5,
            vCenter: 522.75,
            hCenter: 62.25,
            field: 'itemCode',
            testData: [],
            coordinateSync: false,
            widthHeightSync: false,
            hideTitle: true,
            qrCodeLevel: 0
          },
          printElementType: {
            title: '自定义文本',
            type: 'text'
          }
        },
        {
          options: {
            left: 60,
            top: 63,
            height: 16.5,
            width: 127.5,
            title: '品名：',
            fontFamily: 'Microsoft YaHei',
            lineHeight: 18,
            right: 146.25,
            bottom: 78.75,
            vCenter: 104.25,
            hCenter: 70.5,
            testData: [],
            coordinateSync: false,
            widthHeightSync: false,
            fontWeight: 'bold',
            letterSpacing: 0.75,
            qrCodeLevel: 0,
            field: 'itemName'
          },
          printElementType: {
            title: '自定义文本',
            type: 'text'
          }
        },
        {
          options: {
            left: 60,
            top: 90,
            height: 19,
            width: 213,
            title: '打印日期',
            fontFamily: '微软雅黑',
            lineHeight: 18,
            right: 270,
            bottom: 108.25,
            vCenter: 163.5,
            hCenter: 98.75,
            field: 'createTime',
            testData: [],
            coordinateSync: false,
            widthHeightSync: false,
            fontWeight: 'bold',
            qrCodeLevel: 0
          },
          printElementType: {
            title: '自定义文本',
            type: 'text'
          }
        },
        {
          options: {
            left: 430.5,
            top: 93,
            height: 13,
            width: 164,
            title: '规格型号',
            textAlign: 'center',
            fontFamily: '微软雅黑',
            right: 602,
            bottom: 106,
            vCenter: 520,
            hCenter: 99.5,
            field: 'specification',
            testData: [],
            coordinateSync: false,
            widthHeightSync: false,
            fontWeight: 'bold',
            qrCodeLevel: 0
          },
          printElementType: {
            title: '自定义文本',
            type: 'text'
          }
        },
        {
          options: {
            left: 31.5,
            top: 147,
            height: 151.5,
            width: 550,
            field: 'table',
            groupFieldsFormatter: 'function(type,options,data){ return ["name"] }',
            coordinateSync: false,
            widthHeightSync: false,
            columns: [
              [
                {
                  width: 70,
                  title: '行号',
                  field: 'id',
                  checked: true,
                  columnId: 'id',
                  fixed: false,
                  rowspan: 2,
                  colspan: 1,
                  tableColumnHeight: 30,
                  tableTextType: 'text',
                  tableBarcodeMode: 'CODE128A',
                  tableQRCodeLevel: 0
                },
                {
                  width: 100,
                  title: '人员信息',
                  checked: true,
                  fixed: false,
                  rowspan: 1,
                  colspan: 4,
                  tableColumnHeight: 30,
                  tableTextType: 'text',
                  tableBarcodeMode: 'CODE128A',
                  tableQRCodeLevel: 0
                }
              ],
              [
                {
                  width: 120,
                  title: '姓名',
                  field: 'name',
                  checked: true,
                  columnId: 'name',
                  fixed: false,
                  rowspan: 1,
                  colspan: 1,
                  align: 'left',
                  tableColumnHeight: '30',
                  tableTextType: 'text',
                  tableBarcodeMode: 'CODE128A',
                  tableQRCodeLevel: 0,
                  tableSummaryTitle: true,
                  tableSummary: 'sum',
                  tableSummaryAlign: 'left',
                  tableSummaryNumFormat: '0'
                },
                {
                  width: 120,
                  title: '性别',
                  field: 'gender',
                  checked: true,
                  columnId: 'gender',
                  fixed: false,
                  rowspan: 1,
                  colspan: 1,
                  tableColumnHeight: 30,
                  tableTextType: 'text',
                  tableBarcodeMode: 'CODE128A',
                  tableQRCodeLevel: 0
                },
                {
                  width: 120,
                  title: '销售数量',
                  field: 'count',
                  checked: true,
                  columnId: 'count',
                  fixed: false,
                  rowspan: 1,
                  colspan: 1,
                  tableColumnHeight: 30,
                  tableTextType: 'text',
                  tableBarcodeMode: 'CODE128A',
                  tableQRCodeLevel: 0
                },
                {
                  width: 120,
                  title: '销售金额',
                  field: 'amount',
                  checked: true,
                  columnId: 'amount',
                  fixed: false,
                  rowspan: 1,
                  colspan: 1,
                  tableColumnHeight: 30,
                  tableTextType: 'text',
                  tableBarcodeMode: 'CODE128A',
                  tableQRCodeLevel: 0
                }
              ]
            ]
          },
          printElementType: {
            title: '表格',
            type: 'table',
            editable: true,
            columnDisplayEditable: true,
            columnDisplayIndexEditable: true,
            columnTitleEditable: true,
            columnResizable: true,
            columnAlignEditable: true,
            isEnableEditField: true,
            isEnableContextMenu: true,
            isEnableInsertRow: true,
            isEnableDeleteRow: true,
            isEnableInsertColumn: true,
            isEnableDeleteColumn: true,
            isEnableMergeCell: true
          }
        },
        {
          options: {
            left: 12,
            top: 786,
            height: 49,
            width: 49
          },
          printElementType: {
            title: 'html',
            type: 'html'
          }
        }
      ],
      paperNumberLeft: 565.5,
      paperNumberTop: 819
    }
  ]
};
