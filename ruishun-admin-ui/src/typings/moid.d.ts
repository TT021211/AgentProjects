declare namespace mold {
  class public {
    moldCode: string;

    moldType: string;

    moldSpec: string;

    moldArea: string;

    moldName: string;

    moldMaterial: string;

    moldAperture: string;
  }
}

// 模具分类管理
declare namespace sortManagement {
  interface searchForm {
    classificationName: string;
    classificationType: string;
    classificationCode: string;
    pageNum: number;
    pageSize: number;
    total: number;
  }

  class public {
    classificationName: string;

    classificationType: string;

    classificationCode: string;

    enable: string;
  }

  interface addForm extends public {
    classificationId: number | null;
  }

  interface TableList extends public {
    classificationId: number;
  }
}

// 台账
declare namespace basicsMessage {
  interface searchForm {
    moldCode: string;
    moldMaterial: string;
    moldAperture?: string;
    moldSpec: string;
    moldArea: string;
    pageNum: number;
    pageSize: number;
    total: number;
  }

  class public {
    moldId: number | null;

    classificationId: number | null;

    moldCode: string | null;

    moldSpec: string | null;

    moldMaterial: string | null;

    moldAperture: number | null;

    moldNum: number | null;

    moldArea: string | null;

    moldSupplierId: number | null;

    engineeringTolerance: number | null;
  }

  interface addForm extends public {
    moldType: string | null;
  }

  interface TableList extends public {
    moldType: string;
    moldName: string;
    status: string;
    supplierName: string;
    inOutStatus: string;
    nestingMoldId: number;
    nowAperture: number;
    createBy: string;
    createTime: string;
    moldSupplierName: string;
    updateTolerance: number;
  }
}

// 供应商
declare namespace supplier {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    supplierName: string | null;
  }

  class public {
    remark: string;

    moldSupplierId: number;

    supplierName: string;
  }

  interface addForm extends public {}

  interface TableList extends public {}
}

// 拉丝套模
declare namespace drawing {
  interface searchForm {
    nestingName: string;
    nestingSpec: string;
    pageNum: number;
    pageSize: number;
    total: number;
  }

  class public {
    nestingName: string;

    nestingScope: string;

    nestingArea: string;

    nestingSpec: string;

    enable: string;

    nestingMoldId: number;
  }

  interface addForm extends public {
    moldLedgers: any[] | null;
    ledgerMsg: string;
  }

  interface TableList extends public {
    status: string;
    planId: number;
    moldLedgers: any[];
    planName: string;
  }

  interface viewData {
    moldId: number;
    nestingMoldId: number;
    moldName: string;
    classificationId: number;
    moldCode: string;
    moldSpec: string;
    moldMaterial: string;
    moldAperture: number;
    inOutStatus: string;
    nowAperture: number;
    updateTolerance: number;
  }
}

// 保养
declare namespace upkeepPlan {
  class searchForm {
    pageNum: number;

    pageSize: number;

    total: number;
  }
  // 记录
  interface recordTabelList {
    nestingName: string;
    nestingScope: string;
    nestingArea: string;
    maintainPlanName: string;
    maintainPlanType: string;
    createBy: string;
    createTime: string;
    maintainItems: any;
    clientName: string;
  }

  interface recodeSearchForm extends searchForm {
    nestingName: string;
    maintainPlanName: string;
    maintainPlanType: string;
    startDate: string | null;
    endDate: string | null;
  }

  // 计划
  class planPublic {
    maintainPlanCode: string | null;

    maintainPlanName: string | null;

    maintainPlanType: string | null;

    maintainPlanStarttime: string | null;

    maintainPlanEndtime: string | null;

    maintainPlanFrequency: string | null;

    moldNesting: any[];

    triggerName: string | null;

    jobName: string | null;
  }

  interface planTabelList extends planPublic {
    maintainPlanId: number;
    maintainPlanStatus: string;
  }

  interface planAddForm extends planPublic {
    maintainPlanId: number | null;
    frequency: number | null;
    attr4: number | null;
    maintainPlanStatus: string | null;
  }

  interface planSearchFrom extends searchForm {
    maintainPlanCode: string;
    maintainPlanName: string;
    maintainPlanType: string;
  }

  // 设置
  class setPublic {
    maintainItemName: string;

    maintainItemContent: string;

    maintainItemStandard: string;

    maintainItemEnable: string;

    maintainItemRemark: string;
  }

  interface setTabelList extends setPublic {
    maintainItemId: number;
  }

  interface setAddForm extends setPublic {
    maintainItemId: number | null;
  }

  interface setSearchFrom extends searchForm {
    maintainItemName: string;
    maintainItemStandard?: string;
    maintainItemContent?: string;
  }

  // 带保养列表
  interface listSearchForm extends searchForm {
    nestingName: string;
    maintainPlanName: string;
    maintainPlanType: string;
    startDate: string | null;
    endDate: string | null;
  }

  interface listTabelList extends searchForm {
    moldNesting: any;
    moldMaintainPlan: any;
    id: number;
    nestingId: number;
    planId: number;
    status: string;
    createTime: string;
    planName: string;
    nestingName: string;
    nestingScope: string;
    nestingArea: string;
    planType: string;
  }
}
// 出库
declare namespace outWarehouse {
  interface searchForm {
    moldCode: string;
    moldType: string;
    recipient: string;
    moldOutboundTime: string | null;
    pageNum: number;
    pageSize: number;
    total: number;
    startDate: string | null;
    endDate: string | null;
  }

  interface TableList extends mold.public {
    moldOutboundId: number;
    nestingId: number;
    moldId: number;
    qbId: number;
    recipient: string;
    moldOutboundTime: string;
    moldLedger: any;
    lsName: string;
    inOutStatus: string;
  }

  interface addForm {
    machineType: number | undefined;
    moldType: number | undefined;
    nestingId: number | null;
    lsId: number | null;
    recipient: '';
    moldId: number | null;
    qbId: number | null;
    machineId: number | null;
    classificationId: number | null;
    moldLedgerList: any;
    moldOutboundTime: string | null;
    nestingMoldId: number | null;
    moldLedger: any;
    moldNesting: any;
  }
}
// 入库
declare namespace joinWarehouse {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    recipient: string;
    moldCode: string;
    moldType: string;
    startDate: string | null;
    endDate: string | null;
  }

  interface TableList extends mold.public {
    moldOutboundId: number;
    nestingId: number;
    moldId: number;
    qbId: number;
    recipient: string;
    moldStorageTime: string;
    moldLedger: any;
    lsName: string;
    qbName: string;
    inOutStatus: string;
  }

  interface addForm {
    machineType: number | null;
    moldType: number | null;
    nestingId: number | null;
    lsId: number | null;
    recipient: '';
    moldId: number | null;
    qbId: number | null;
    machineId: number | null;
    classificationId: number | null;
    moldLedgerList: any;
    moldStorageTime: string | null;
    nestingMoldId: number | null;
  }
}

// 生命周期报表
declare namespace lifeCycleReport {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    moldCode: string;
    moldType: string;
    startDate: string | null;
    endDate: string | null;
  }

  interface TableList extends mold.public {
    id: number;
    nestingId: number;
    moldId: number;
    status: string;
    moldLedger: any;
    createTime: string;
  }
}

// 待归还模具
declare namespace awaitReturn {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    moldCode: string;
    moldType: string;
  }
  interface addForm {
    moldId: number | null;
    qbId: number | null;
    lsId: number | null;
    remark: string;
    inBy: string;
    nestingMoldId: number | null;
    moldLedgerList: any;
    moldLedger: any;
    moldReturnedId: number | null;
  }
  interface TableList extends mold.public {
    moldReturnedId: number;
    nestingMoldId: number;
    moldLedger: any;
    createTime: string;
    lsName: string;
    qbName: string;
    moldOutAperture: string;
    moldOutCode: string;
    moldOutArea: string;
    inBy: string;
    moldId: number | null;
    moldLedgerList: any;
    lsId: number | null;
    qbId: number | null;
    moldNesting: any;
  }
}

// 换规单
declare namespace gaugeChangeForm {
  interface searchForm {
    model: string;
    spec: string;
    moldBeforSpec: string;
    moldAfterSpec: string;
    pageNum: number;
    pageSize: number;
    total: number;
  }
  interface addForm {
    model: string;
    spec: string;
    quantity: number | null;
    lsDeviceId: number | null;
    qbDeviceId: number | null;
    startTime: string | null;
    endTime: string | null;
    remark: string;
    orderDate: string | null;
    moldBeforeId: number | null;
    moldId: number | null;
    moldBeforSpec: string | null;
    moldAfterSpec: string | null;
  }

  interface TableList {
    moldGaugeId: number;
    clientId: number;
    lsdeviceid: number;
    model: string;
    spec: string;
    quantity: number;
    lsName: string;
    qbName: string;
    startDate: string;
    endDate: string;
    moldBeforSpec: number;
    moldAfterSpec: number;
    remark: string;
    orderDate: string;
  }
}

// 报废
declare namespace scrap {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    remark: string;
    startDate: string | null;
    endDate: string | null;
  }
  interface addForm {
    moldId: number | null;
    nestingMoldId: number | null;
    scrappingDate: string | null;
    remark: string;
    moldType: number | undefined;
    moldLedgerList: any[];
    status: string;
  }

  interface TableList {
    moldScrappingId: number;
    moldLedger: any;
    moldId: number;
    nestingMoldId: number;
    scrappingDate: string;
    remark: string;
    status: string;
    moldName: string;
    moldType: string;
    moldCode: string;
    moldSpec: string;
    moldMaterial: string;
    area: string;
    nestingName: string;
  }
}

// 改制
declare namespace reform {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    remark: string;
    startDate: string | null;
    endDate: string | null;
  }
  interface addForm {
    moldId: number | null;
    nestingMoldId: number | null;
    restructuringDate: string | null;
    remark: string;
    moldType: number | undefined;
    moldLedgerList: any[];
  }

  interface TableList {
    moldRestructuringId: number;
    moldId: number;
    nestingMoldId: number;
    restructuringDate: string;
    remark: string;
    moldLedger: any;
    beforeAperture: number;
    nowAperture: number;
  }
}

// 维修保养
declare namespace maintenance {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    moldCode: string;
    moldType: string;
    startDate: string | null;
    endDate: string | null;
  }
  interface addForm {
    moldLedgerList: any[];
    status: string | null;
    ledgerMsg: string;
  }

  interface TableList extends mold.public {
    id: number;
    moldId: number;
    status: string;
    createTime: string;
    moldLedger: any;
    moldLedgerList: any;
  }
}

// 模具采购
declare namespace purchase {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
  }
  interface addForm {
    id: number | null;
    purchaseCode: string;
    moldCode: string;
    purchaseSpec: string;
    purchaseTolerance: number | null;
    purchaseMaterial: string;
    purchaseType: string;
    status: string;
    purchaseFactory: string;
    purchaseFactoryId: number | null;
  }

  interface TableList {
    id: number;
    purchaseCode: string;
    moldCode: string;
    purchaseSpec: string;
    purchaseTolerance: number;
    purchaseMaterial: string;
    purchaseType: string;
    status: string;
    purchaseFactory: string;
    inboundStatus: string;
    createTime: string;
    createBy: string;
  }

  interface detailArrays {
    purchaseCode: string;
    moldCode: string;
    purchaseSpec: string;
    purchaseTolerance: number | null;
    purchaseMaterial: string;
    purchaseType: string;
    purchaseFactory: string;
    status: string;
  }
}
