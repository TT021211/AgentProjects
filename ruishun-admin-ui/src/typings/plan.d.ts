declare namespace planWorkOrder {
  interface searchForm {
    status: number | undefined;
    pageNum: number;
    pageSize: number;
    total: number;
    orderType: number | null;
    model: string;
    workorderCode: string;
    productSpecification: string;
  }

  interface FormModel {
    orderCode: string | number | null; // 订单编号
    productModel: string | null; // 产品型号
    productSpecification: string | number | null; // 产品规格
    payTime: string | null; // 交付时间
    orderType: string | number | null; // 订单类型
    customerName: string | null; // 客户
    workorderCode: string | null; // 生产工单
    workorderId: number | null;
    models: string | null; // 产品型号
    specifications: any; // 产品规格
    measureId: number | null;
    measureName: string | null;
    onlyReadWeight: string | number | null; // 只读重量
    residueWeight: string | number | null; // 剩余重量
    productIds: string | null;
  }

  interface planWorkOrderList {
    id: number;
    orderId: number | null;
    orderType: string | number | null; // 订单类型
    customerName: string | null; // 客户
    orderCode: string | null; // 订单编号
    workorderCode: string | null; // 生产工单
    productModel: string | null; // 产品型号
    routingId: number | null; // 关联工艺路线id
    weight: number | null; // 重量
    payTime: string | null; // 交付时间
    status: string | null; // 状态
    productSpecification: any; // 规格
    wiredrawingSchedulingWeight: number | null; // 拉丝排产重量
    enamellingSchedulingWeight: number | null; // 漆包排产重量
    productId: string;
    productSize?: string;
    specification: string;
    equipmentCode: string;
    axleNumber: string;
    wireDisc: string;
    plantName: string;
    insertWorkorder: string;
    insertModel: string;
    insertStatus: string;
    insertId: number;
    createTime: string;
    routingName: string;
    attr1: string;
  }

  interface drawBenchData {
    wireDiscId: number | null;
    equipmentCode: string | null;
    equipmentId: null | string;
    startTime: string | null;
    endTime: string | null;
    axleNumber: string | null;
    discNumber: number | null;
    remark: string | null;
    equipmentIdList: number[] | null;
    weight: string | number | null;
    enamellingEquipmentId: number | null;
    enamellingEquipmentCode: string | null;
    artt5: string | null;
    artt6: string | null;
    status: string | null;
    wireDisc: string | null; // 线盘
  }

  interface enamelCoverData {
    equipmentCode: string | null;
    equipmentId: string | null;
    equipmentIdList: number[] | null;
    axleNumber: string | null; // 位置
    wireDisc: string | null; // 线盘
    plantName: number | null | string; // 油漆
    startTime: string | null; // 开始时间
    endTime: string | null;
    discNumber: number | null; // 盘数
    remark: string | null;
    weight: number | null;
    standardValue: number | null;
    standardName: string | null;
    plantNumber: number | null;
    wireDiscNumber: number | null;
    wireDiscId: number | null;
    plantNameList: any;
    artt5: string | null;
    status: string | null;
    productId: string | null;
    model: string;
    specification: string;
    artt6: '';
  }

  type SelectMixedOption = {
    label: string;
    value: number;
    deviceId?: object | null;
  };
}

declare namespace drawingWorkOrder {
  interface searchForm {
    status: number | undefined;
    pageNum: number;
    pageSize: number;
    total: number;
    equipmentId: number | null;
    orderType: number | null;
    model: string;
    wiredrawingCode: string;
    specification: string;
    enamellingEquipmentCode: string;
    equipmentCode: string;
  }

  interface tabelList {
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
    wiredrawingCode: string;
    enamellingEquipmentCode: string;
    degreeOfCompletion: string;
    wightCompletion: string;
    createTime: string;
    equipmentId: number;
  }
}

declare namespace paintJobOrder {
  interface searchForm {
    status: number | undefined;
    pageNum: number;
    pageSize: number;
    total: number;
    equipmentId: number | null;
    orderType: number | null;
    model: string;
    workorderCode: string;
    specification: string;
    equipmentCode: string;
  }

  interface tabelList {
    id: number;
    orderType: string | number | null; // 订单类型
    customerName: string; // 客户
    orderCode: string | null; // 订单编号
    workorderCode: string | null; // 生产工单号
    equipmentCode: string | null; // QB机台
    axleNumber: string | null; // 位置
    wireDisc: string | null; // 线盘
    plantName: number | null; // 油漆
    model: string | null; // 产品型号
    specification: string | null; // 产品规格
    weight: string | number | null; // 重量
    discNumber: number | null; // 盘数
    startTime: string | null; // 开始时间
    endTime: string | null; // 结束时间
    remark: string | null; // 特殊要求
    status: string | null; // 状态
    enamellingCode: string;
    attr1: string;
    equipmentId: number;
    degreeOfCompletion: string;
    wightCompletion: string;
    createTime: string;
    artt5: string;
  }
}

declare namespace enamelEoverPlanAdmin {
  interface searchForm {
    status: number | undefined;
    pageNum: number;
    pageSize: number;
    total: number;
    orderType: number | null;
    model: string;
    workorderCode: string;
    equipmentId: number | null;
    specification: string;
  }
}
