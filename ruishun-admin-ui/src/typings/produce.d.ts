declare namespace workOrderArrange {
  interface searchForm {
    workOrderType: number;
    status: number | undefined;
    pageNum: number;
    pageSize: number;
    total: number;
    wiredrawingCode: string;
    specification: string;
    equipmentCode: string;
    annealingCode: string;
    tinningCode: string;
    doublewireCode: string;
    strandedwireCode: string;
  }

  class TabelList {
    id: number;

    orderType: string | number | null; // 订单类型

    customerName: string; // 客户

    orderCode: string | null; // 订单编号

    workorderCode: string | null; // 生产工单号

    equipmentCode: string | null; // LS机台

    model: string | null; // 产品型号

    specification: string | null; // 产品规格

    weight: string | number | null; // 重量

    discNumber: number | null; // 盘数

    startTime: string | null; // 开始时间

    endTime: string | null; // 结束时间

    remark: string | null; // 特殊要求

    status: string | null; // 状态

    taskSn: string; // 工序工单

    createTime: string;
  }
  interface lsTabelList extends TabelList {
    wiredrawingCode: string;
  }

  interface thTabelList extends TabelList {
    axleNumber: string | null; // 位置
    wireDisc: string | null; // 线盘
    annealingCode: string;
  }
  interface dxTabelList extends TabelList {
    axleNumber: string | null; // 位置
    wireDisc: string | null; // 线盘
    tinningCode: string;
  }
  interface bsTabelList extends TabelList {
    axleNumber: string | null; // 位置
    wireDisc: string | null; // 线盘
    doublewireCode: string;
  }
  interface jxTabelList extends TabelList {
    axleNumber: string | null; // 位置
    wireDisc: string | null; // 线盘
    strandedwireCode: string;
  }
}

declare namespace feedRecords {
  interface searchForm {
    workstationCode: string; // 机台名称
    taskType: number | null; // 工序
    taskSn: string; // 工序工单
    orderSn: string; // 销售订单
    pageNum: number;
    pageSize: number;
    total: number;
    productSpecification: string;
    itemSpecification: string;
    partNumber: string;
    batchCode: string;
  }

  interface TabelList {
    id: number | null;
    taskType: string | number | null;
    workstationName: string;
    orderType: string | number | null; // 订单类型
    orderSn: string;
    clientName: string;
    workorderSn: string;
    taskSn: string;
    productModel: string;
    productSpecification: string;
    inputWeight: number | null;
    measureName: string;
    createTime: string;
    createBy: string;
    partNumber: string;
    batchCode: string;
    itemSpecification: string;
    axleNumber: string;
    alexPositionNumber: string;
  }
}

declare namespace workRecords {
  interface searchForm {
    taskSn: string; // 生产工单号
    workstationId: number | null; // 机台
    taskType: number | null; // 工单类型
    orderSn: string; // 订单编号
    pageNum: number;
    pageSize: number;
    total: number;
    batchCode: string;
    ingredientsName: string;
    manufacturerName: string;
    itemSpecification: string;
  }

  interface TabelList {
    id: number;
    taskType: string | number | null;
    workstationName: string;
    orderType: string | number | null; // 订单类型
    orderSn: string;
    clientName: string;
    workorderSn: string;
    outputSn: string;
    productModel: string;
    productSpecification: string;
    inputWeight: number;
    measureName: string;
    createTime: string;
    createBy: string;
    lotNumber: string;
    partNumber: string;
    batchCode: string;
    itemSpecification: string;
    jobNumber: string;
    alexPositionNumber: string | number;
    alexLot: string;
  }
}

declare namespace carryRecords {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    specification: string;
    equipmentCode: string;
    jobNumber: string;
    enamellingEquipmentCode: string;
    itemIdentifier: string;
  }

  interface TabelList {
    specification: string;
    equipmentCode: string;
    enamellingEquipmentCode: string;
    itemIdentifier: string;
    jobNumber: string;
    createTime: string;
  }
}
