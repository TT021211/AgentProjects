// 仓储管理

// 原料出入库
declare namespace rawMaterial {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    ingredientsName: string;
    lotNumber: string;
  }

  interface tabelList {
    codeSn: string;
    ingredientsName: string;
    productName: string;
    itemSpecification: string;
    lotNumber: string | number | null;
    partNumber: string | number | null;
    grossWeight: number | null;
    netWeight: number | null;
    tareWeight: number | null;
    measureName: string;
    manufacturerName: string;
    status: string | null;
    createTime: string;
  }
}

// 原料出入库
declare namespace rawMaterial1 {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    // ingredientsName: string;
    status: string | null;
    lotNumber?: string;
    productSpec?: string;
    productBatchNumber?: string;
    ingredientsId?: number | null;
    productId?: number | null;
    productName?: string | null;
    itemId?: number | null;
    startTime?: string | null;
    endTime?: string | null;
    boxCode?: string;
    'params[beginTime]': string | null;
    'params[endTime]': string | null;
    // productName: string;
  }

  interface tabelList {
    codeSn: string;
    ingredientsName: string;
    productName: string;
    itemSpecification: string;
    lotNumber: string | number | null;
    partNumber: string | number | null;
    grossWeight: number | null;
    netWeight: number | null;
    tareWeight: number | null;
    productUnit: string;
    measureName: string;
    manufacturerName: string;
    status: string | null;
    createTime: string;
    id: number;
  }
}

// 辅料出入库
declare namespace ingredients {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    status: string | null;
    accessoryName: string;
  }

  interface tabelList {
    accessoryName: string;
    model: string;
    weight: number | null;
    weightMeasureName: string;
    manufacturerName: string;
    status: string | null;
    createTime: string;
  }
}
// 半成品出入库
declare namespace semiProduct {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    wmsStatus: number | null;
    batchCode: string;
    ingredientsName: string;
    itemSpecification: string;
    weightTime: string | null;
    manufacturerName: string;
  }

  interface tabelList {
    weightTime: string;
    outputSn: string;
    ingredientsName: string;
    itemSpecification: string;
    batchCode: string;
    partNumber: string;
    outputGrossWeight: number;
    outputWeight: number;
    outputTareWeight: number;
    measureName: string;
    manufacturerName: string;
    wmsStatus: number;
    createTime: string;
  }
}
