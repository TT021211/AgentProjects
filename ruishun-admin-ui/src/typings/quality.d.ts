declare namespace qualityType {
  interface defectType {
    defectDescription: string;
    defectClassify: string;
    defectClass: string;
    status: string;
  }
}

declare namespace public {
  class search {
    pageNum: number;

    pageSize: number;

    total: number;
  }
}

// 日常送检计划
declare namespace dailyInspectionPlan {
  interface searchForm extends public.search {
    dailyplanCode: string;
    dailyplanName: string;
  }

  interface TableList {
    dailyplanId: number;
    dailyplanDate: string;
    dailyplanDevice: string;
    dailyplanAxle: string;
    dailyplanBeforeSpec: number;
    dailyplanAfterSpec: number;
    dailyplanRemark: string;
    dailyplanSubmitter: string;
    dailyplanCode: string;
    dailyplanName: string;
  }

  interface addOrEditData {
    dailyplanId: number | null;
    dailyplanDate: string | null;
    dailyplanDevice: string;
    dailyplanAxle: string;
    dailyplanBeforeSpec: number | null;
    dailyplanAfterSpec: number | null;
    dailyplanRemark: string;
    dailyplanSubmitter: string;
    dailyplanCode: string;
    dailyplanName: string;
  }
}

// 质检分类
declare namespace qualityInspectionClassify {
  interface searchForm extends public.search {
    classificationName: string;
  }

  interface TableList {
    id: number;
    classificationName: string;
    enable: string;
  }

  interface addOrEditData {
    id: number | null;
    classificationName: string;
    enable: string;
  }
}

// 缺陷项分类
declare namespace flawClassify {
  class publicFlied {
    defectClassificationCode: string;

    defectClassificationName: string;
  }
  interface searchForm extends public.search, flawClassify.publicFlied {}

  interface TableList extends flawClassify.publicFlied {
    id: number;
    enable: string;
  }

  interface addOrEditData extends flawClassify.publicFlied {
    id: number | null;
    enable: string;
  }
}

// 检测项设置
declare namespace testItemSet {
  class interPublic {
    detectionCode: string;

    detectionName: string;

    isRequired: string;

    enable: string;

    attr1: string;
  }
  interface searchForm extends public.search {
    detectionCode: string;
    detectionName: string;
  }

  interface TableList extends testItemSet.interPublic {
    id: number;
  }

  interface addOrEditData extends testItemSet.interPublic {
    id: number | null;
  }
}

// 缺陷项设置
declare namespace qcDefect {
  class interPublic {
    defectCode: string;

    defectName: string;

    defectLevel: string;

    remark: string;

    enable: string;
  }
  interface searchForm extends public.search {
    defectCode: string;
    defectName: string;
  }

  interface TableList extends qcDefect.interPublic {
    defectId: number;
    defectClassificationId: number;
    qcDefectClassification: flawClassify.TableList;
  }

  interface addOrEditData extends qcDefect.interPublic {
    defectId: number | null;
    defectClassificationId: number | null;
  }
}

// 质检检测项
declare namespace qualityInspectionItem {
  class interPublic {
    detectionCode: string;

    detectionName: string;

    isRequired: string;

    enable: string;

    attr1: string;
  }
  interface searchForm extends public.search {
    detectionCode: string;
    detectionName: string;
  }

  interface TableList extends qualityInspectionItem.interPublic {
    id: number;
    itemTypeId: number;
    qcClassification: qualityInspectionClassify.TableList;
  }

  interface addOrEditData extends qualityInspectionItem.interPublic {
    id: number | null;
    itemTypeId: number | null;
  }
}

// 质检检测项
declare namespace surfaceFlawSet {
  class interPublic {
    surfaceCode: string;

    surfaceName: string;

    isRequired: string;

    enable: string;
  }
  interface searchForm extends public.search {
    surfaceCode: string;
    surfaceName: string;
  }

  interface TableList extends surfaceFlawSet.interPublic {
    id: number;
    defectClassificationId: number;
    qcDefectClassification: flawClassify.TableList;
  }

  interface addOrEditData extends surfaceFlawSet.interPublic {
    id: number | null;
    defectClassificationId: number | null;
  }
}

// 质检检测项
declare namespace oilPaintRecord {
  interface searchForm extends public.search {
    paintSpec: string;
    purchaseOrder: string;
    detectionResult: string;
  }

  interface TableList {
    id: number;
    inspectionId: number;
    paintSpec: string;
    purchaseOrder: string;
    supplier: string;
    batchNumber: string;
    weight: number;
    measure: string;
    qualityInspector: string;
    detectionResult: string;
  }
}

// 线盘检记录
declare namespace trayRecord {
  interface searchForm extends public.search {
    productName: string;
  }

  interface TableList {
    id: number;
    inspectionId: number;
    paintSpec: string;
    purchaseOrder: string;
    supplier: string;
    batchNumber: string;
    number: number;
    qualityInspector: string;
    detectionResult: string;
    defectId: number | null;
    qcInspectionItems: qualityInspectionItem.TableList;
    qcDefectList: qcDefect.TableList[];
    status: string;
  }

  interface addOrEditData {
    inspectionId: number | null;
    paintSpec: string;
    purchaseOrder: string;
    supplier: string;
    batchNumber: string;
    number: number | null;
    qualityInspector: string;
    detectionResult: string | null;
    defectId: string;
  }
}

// 拉丝过程检记录
declare namespace drawingProcessCheckRecord {
  interface searchForm extends public.search {
    generateWorkOrderNumber: string;
    orderNumber: string;
    taskType?: string;
    inspectionType?: string | null;
    status?: string | null;
    deviceName?: string | null;
    machineTable?: string | null;
    qualifiedOrNot?: string | null;
  }

  interface TableList {
    id: number;
    qualifiedOrNot: string; // 是否合格
    taskType: string; // 工单类型
    deviceName: string; // 机台
    orderType: string;
    orderNumber: string;
    clientName: string;
    status: string;
    generateWorkOrderNumber: string;
    productSpecifications: string | number;
    batchNumber: string;
    weight: number;
    measure: string;
    qualityInspector: string;
    attr1: string;
    createTime: string;
    defectClassificationId: string;
    qcDefectList: any;
  }
}

// 漆包过程检记录
declare namespace enamellingProcessCheckRecord {
  interface searchForm extends public.search {
    generateWorkOrderNumber: string;
    orderNumber: string;
    qualifiedOrNot: string | null;
    deviceName: string | null;
  }

  interface TableList {
    id: number;
    qualifiedOrNot: string; // 是否合格
    workOrdeType: string; // 工单类型
    deviceName: string; // 机台
    orderType: string;
    axisNumber: string;
    orderNumber: string;
    clientName: string;
    generateWorkOrderNumber: string;
    productModel: string;
    productSpecifications: string | number;
    batchNumber: string;
    partNumber: string;
    weight: number;
    measure: string;
    qualityInspector: string;
    attr1: string;
    createTime: string;
    qcDefectList: any;
  }
}
