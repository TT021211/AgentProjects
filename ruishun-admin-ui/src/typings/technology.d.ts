interface page {
  pageNum: number;
  pageSize: number;
  total: number;
}

declare namespace Tsetting {
  interface searchForm extends page {
    workstageName: string;
    workstageCode: string;
  }
  interface formData {
    id?: number;
    workstageName: string;
    workstageCode: string;
    isEnable: number;
    remark: string;
  }
  interface columns {
    // 工序id
    id: number;
    // 工序名称
    workstageName: string;

    enableFlag: number | string;
    type: number | string;

    warehouseId: number;
    // 父工序
    // parentWorkstageName: string;
    // 是否启用
    isEnable: number;
    // 工序编码
    workstageCode: string;
    // 父工序编码
    // parentWorkstageCode: string;
    // 备注
    remark: string;
  }
}
interface option {
  id: number;
  label: string;
}
declare namespace Tline {
  interface searchForm extends page {
    routingName: string;
    routingCode: string;
  }

  interface columns {
    // 主键
    id: number;
    // 工艺路线名称
    routingName: string;
    // 工艺路线编码
    routingCode: string;
    // 工序路线
    routingWorkstages: option[];
    // 备注
    remark: string;
  }
  interface formData {
    id?: number;
    // 工艺路线名称
    routingName: string;
    // 工艺路线编码
    routingCode: string;
    // 工序路线
    routingWorkstages?: option[];
    // 备注
    remark: string;
  }
  interface addProcessType {
    routingId: number;
    workstageId: number | null;
    orderNum: number;
  }
  interface processDataType {
    createBy: string;
    createTime: string;
    id: number;
    isDeleted: number;
    orderMark: string;
    orderNum: number;
    remark: undefined | string;
    routingCode: string;
    routingId: number;
    routingName: string;
    updateBy: null | string;
    updateTime: null | string;
    version: number;
    workstageCode: string;
    workstageId: number;
    workstageName: string;
  }
}

declare namespace ProductLine {
  interface searchForm extends page {
    productId: number;
  }
  interface columns {
    id: number;
    routingId: number;
    routingName: string;
    isDefault: number;
  }
  interface link {
    productId: number;
    routingId: number;
    isDefault?: number;
  }
}
declare namespace LineProduct {
  interface searchForm extends page {
    routingId: number;
  }
  interface columns {
    id: number;
    productCode: string;
    productName: string;
    remark: string;
  }
  interface psearchForm extends page {
    productCode: string;
    productName: string;
  }
}

declare namespace operationInstruction {
  interface searchForm extends page {
    operationName: string;
    operationCode: string;
    enanellingWorkbenchId: number | null;
    operationType: string;
  }
  interface columns {
    id: number;
    operationName: string;
    operationCode: string;
    enanellingWorkbenchId: number;
    enanellingWorkbenchName: string;
    ableTime: string;
    filePath: string;
    isDeleted: string;
    image: string;
  }

  interface addOrEditForm {
    operationName: string | null;
    operationCode: string | null;
    enanellingWorkbenchId: number | null;
    enanellingWorkbenchName: string | null;
    ableTime: string | null;
    filePath: string | null;
    id: number | null;
    image: string | null;
    operationType: string;
  }
}
