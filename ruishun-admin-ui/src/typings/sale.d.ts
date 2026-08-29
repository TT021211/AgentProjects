declare namespace orderReview {
  interface columns {
    clientName: string;
    salesOrderCode: string;
    productName: string;
    productSize: string;
  }
}

declare namespace orderList {
  type searchForm = {
    salesOrderType: string | null;
    clientName: string;
    orderStatus: string | null;
    pageNum: number;
    pageSize: number;
    total: number;
  };
  interface columns {
    determineDeliveryTime: null | string;
    salesOrderType: string;
    clientName: string;
    salesOrderCode: string;
    clientContact: string;
    contactTel: string;
    clientEmail: string;
    orderStatus: string;
    sellerName: string;
    sellerTel: string;
  }
  interface orderStatus {
    type: string;
    title: string;
  }
}
declare namespace orderDetail {
  // 生产工单
  interface productOrder {
    productName: string;
    size: string;
    allowableError: string;
    hardness: string;
    productNum: number;
    productUnit: string;
  }
  // 锁价信息
  interface lockPriceInfo {
    productName: string;
    size: string;
    allowableError: string;
    hardness: string;
    productNum: number;
    lockedPriceNum: number;
    productWeight: string;
  }
  // 锁价记录
  interface lockPriceRecord {
    lockedCode: string | number;
    lockedTime: string;
    productName: string;
    size: string;
    lockedPriceWeight: string;
    unitPrice: string;
    totalPrice: number;
    createTime?: string;
    children?: lockPriceRecord[];
  }
  // 发货信息
  interface deliveryInfo {
    productName: string;
    productNum: number;
    shippedNum: number;
    residueShipNum: number;
  }

  // 发货通知
  interface deliveryNotice {
    deliveryCode: string;
    productName: string;
    productNum: number;
    deliverTime: string;
    logInfomation: string;
    status: string;
  }
  // 订单详情
  interface orderDetail {
    workmanship?: any;
    salesOrderDetailId?: string;
    productName: string;
    size: string;
    allowableError: string;
    hardness: string;
    productNum: string;
    productWeight: string;
    lockedPriceNum: number | string;
    lockedPriceWeight: string;
  }
}

declare namespace contractManage {
  interface searchForm {
    userName: string;
    userPhone: string;
    contractNumber: string;
    productName: string;
    pageNum: number;
    pageSize: number;
    total: number;
  }
  interface columns {
    id?: number;
    userName: string;
    userAddress: string;
    userLegal: string;
    userPhone: string;
    userFax: string;
    userBank: string;
    userAccount: string;
    userTax: string;
    demandName: string;
    demandAddress: string;
    demandLegal: string;
    demandPhone: string;
    demandFax: string;
    demandBank: string;
    demandAccount: string;
    demandTax: string;
    contractNumber: string;
    productName: string;
    specificationModel: string;
    unit: string;
    weight: number | null;
    copperPrice: number | null;
    processingFee: number | null;
    price: number | null;
    totalPrice: number | null;
    totalPriceUpper: string;
    dateOfSigning: string | null;
    signedAt: string;
    contractNote: string;
  }
  interface addForm {
    id?: number;
    userId: number;
    userName: string;
    userAddress: string;
    userLegal: string;
    userPhone: string;
    userFax: string;
    userBank: string;
    userAccount: string;
    userTax: string;
    demandName: string;
    demandAddress: string;
    demandLegal: string;
    demandPhone: string;
    demandFax: string;
    demandBank: string;
    demandAccount: string;
    demandTax: string;
    contractNumber: string;
    productName: string;
    specificationModel: string;
    unit: string;
    weight: number | null;
    copperPrice: number | null;
    processingFee: number | null;
    price: number | null;
    totalPrice: number | null;
    totalPriceUpper: string;
    dateOfSigning: string | null;
    signedAt: string;
    contractNote: string;
    isDeleted: number;
    version: number;
  }
}
declare namespace createContract {
  interface Options {
    label: string;
    value: number | string;
  }
  interface addForm {
    salesmanName: string;
    salesmanPhone: string;
    tempOrderId: null | number[];
    signLocation: string; // 签订地点
    clientName: string; // 客户名称
    salesContractCode: string; // 合同编码
    clientId: number | null; // 客户id
    clientCode: string; // 客户编码
    clientAddress: string; // 客户地址
    contact: string; // 联系人
    contactTel: string; // 联系人电话
    signingDate: null; // 签订日期
    deliveryDate: null; // 交货日期
  }
  interface salesOrderDetail {
    surplusWeight: number;
    productWeightTemp?: string;
    lockPriceOrderDetailId: number;
    productType: string; // 产品分类
    productName: string; // 产品名称
    size: string; // 产品规格
    productNum: string; // 产品数量
    productWeight: string; // 产品重量
    unitPriceIncludingTax: string; // 含税单价
    detailTotalAmount: string; // 明细总金额
    lockPriceOrderId: number; // 锁价单id
    remark: string; // 备注
  }
  interface lockPriceOrder {
    lockPriceOrderId: number; // 锁价单id
    salesOrderId: number; // 订单id
  }
  interface panels {
    crmLockPriceOrder: lockPriceOrder;
    lockPriceOrderDetailDTOList: salesOrderDetail[];
  }
}

interface page {
  pageNum: number;
  pageSize: number;
  total: number;
}
declare namespace saleMoneyManage {
  interface searchForm extends page {
    clientName: string;
    saleContractCode: string;
    productSpec: string;
  }

  interface columns {
    id: number; // 主键
    clientName: string;
    saleContractCode: string;
    saleContractDate: string;
    productName: string;
    productSpec: string;
    saleContractWeight: string;
    copperPrice: string;
    processingFee: string;
    unitPrice: string;
    shipmentDate: string;
    deliveryWeight: string;
    totalAmount: string;
    loan: string;
  }

  interface addForm {
    id?: number;
    clientName: string;
    saleContractCode: string;
    saleContractDate: string;
    productName: string;
    productSpec: string;
    saleContractWeight: string;
    copperPrice: string;
    processingFee: string;
    unitPrice: string;
    shipmentDate: string;
    deliveryWeight: string;
    totalAmount: string;
    loan: string;
    isDeleted: number;
    version: number;
  }
}
// 合同管理
declare namespace saleManage {
  interface searchForm extends page {
    userName: string;
    userPhone: string;
    contractCode: string;
    productName: string;
  }
  interface columns {
    id: number; // 主键
    clientName: string;
    code: string;
    contact: string;
    phone: string;
    contractCode: string;
    signedDate: string;
    deliveryDate: string;
    contractTotal: string;
    status: string;
  }
}
// 用户管理
declare namespace UserManage {
  interface searchForm extends page {
    creditCode: string;
    clientName: string;
    salesmanName: string;
    attr1?: string | null;
    attr2?: string | null;
  }
  interface columns {
    id: number; // 主键
    clientName: string; // 客户名称
    clientNick: string; // 客户简称
    clientEn: string; // 客户英文编码
    creditCode: string; // 统一社会信用代码
    address: string; // 地址
    clientCode?: string; // 客户编码
    contact1?: string; // 联系人1
    contact1Tel: string; // 联系人1电话
    legalPersonName: string; // 法人姓名
    legalPersonPhone: string; // 法人电话
    salesmanName: string; // 业务员
    salesmanPhone: string; // 业务员电话
    leadSource: number;
    attr1: string;
    attr2: number;
    contact2: number;
    contact2Tel: string;
  }
  interface addForm {
    id?: number;
    paymentMethod: string;
    productsNum: string;
    products: string;
    pricingModel: string | null | number;
    leadSource: string | null | number;
    ifDistributor: string | null | number;
    businessScope: string;
    registeredCapital: string;
    foundingTime: string;
    contact1Email: string;
    contact1Fax: string;
    contact1Phone: string;
    contact1Tel: string;
    contact1Title: string;
    contact1: string;
    legalPersonIdcard: string;
    bankAccount: string; // 银行账号
    clientName: string; // 客户名称
    clientCode: string; // 客户编码
    clientNick?: string; // 客户简称
    clientEn?: string; // 客户英文编码
    creditCode: string; // 统一社会信用代码
    address: string; // 地址
    legalPersonName: string; // 法人姓名
    legalPersonPhone: string; // 法人电话
    salesmanName: string; // 业务员
    salesmanPhone: string; // 业务员电话
    attr1?: string | null;
    attr2?: string | null;
    contact2?: string | null;
    addressId?: string | null;
    contact2Tel?: string;
  }

  interface recordColumns {
    id: number;
    clientId: number | null;
    clientName: string;
    interviewName: string;
    interviewTime: Date | null;
    interviewAddress: string;
    connectionName: string;
    description: string;
    remark: string;
    interviewStatus: string;
    attr1: string;
    attr2: string;
  }

  interface addRecordForm {
    id: number | null;
    clientId: number | null;
    clientName: string;
    interviewName: string;
    interviewTime: string | null;
    interviewAddress: string;
    connectionName: string;
    description: string;
    remark: string;
    interviewStatus: number | null;
    attr1: string | null;
    attr2: string | null;
  }

  interface recordSearchForm extends page {
    clientId: number | null;
    clientName: string;
  }
}

declare namespace ContractDetail {
  interface clientInfo {
    salesContractCode: string; // 合同编码
    clientName: string; // 客户名称
    clientCode: string; // 客户编码
    signingDate: string; // 签订日期
    deliveryDate: string; // 交货日期
    taxInclusiveTotalAmount: number | string; // 总金额
    sellerName: string; // 业务员
  }
  interface productSearchFrom extends page {
    salesContractId: number; // 合同id
  }

  interface productList {
    taxInclusiveUnitPrice: string; // 含税单价
    salesContractDetailId?: number; // 主键id
    productName: string; // 产品名称
    productType: string; // 产品规格
    allowableError: string; // 公差要求
    hardness: string; // 硬度要求
    conductivity: string; // 导电率
    copperContent: string; // 含铜量
    packageRequirements: string; // 包装要求
    productNum: string; // 产品数量
    productWeight: string; // 重量要求

    taxInclusiveTotalAmount: string; // 含税总金额
    status: string; // 状态
  }
  interface crmSalesOrder {
    id: number;
    clientName: string;
    salesOrderCode: string;
    cancelName: string;
    contactTel: string;
    clientEmail: string;
    orderStatus: string;
  }
  interface crmSalesOrderDetailList {
    productName: string;
    size: string;
    allowableError: string;
    hardness: string;
    productNum: string | number;
    productWeight: string;
  }
  interface panels {
    crmSalesOrder: crmSalesOrder;
    crmSalesOrderDetailList: crmSalesOrderDetailList[];
  }
}

declare namespace SendGoods {
  interface sendGoodsColumns {
    sendGoodsCode: string;
    product: string;
    sendNum: number;
    trueNum: number;
    sendTime: string;
    logisticsMessage: string;
    status: string;
  }
}
// 报表管理
declare namespace reportManage {
  interface searchForm extends page {
    userName: string;
    productName: string;
    productCode: string;
  }
  interface columns {
    id?: number;
    saleDate: string | null;
    userName: string;
    productName: string;
    materialsCode: string;
    unit: string;
    contractCount: string;
    price: string;
    arrivedTime: string | null;
    actualCount: string;
    totalPrice: string;
    invoice: string;
    paymentTime: string | null;
  }
  interface addForm {
    id?: number;
    saleDate: string | null;
    userName: string;
    productName: string;
    productCode: string;
    unit: string;
    contractCount: string;
    price: string;
    arrivedTime: string | null;
    actualCount: string;
    totalPrice: string;
    invoice: string;
    paymentTime: string | null;
  }
}

declare namespace SourceManage {
  interface searchForm extends page {
    sourceName: string;
    sourceUniqueCode: string;
    salesman: string;
  }

  interface columns {
    id: number; // 主键
    sourceName: string; // 客户名称
    abbreviation: string; // 客户简称
    sourceCode: string; // 客户英文编码
    sourceUniqueCode: string; // 统一社会信用代码
    salesman: string; // 业务员
    phoneNumber: string; // 业务员电话
  }
  interface addForm {
    id?: number;
    sourceName: string; // 客户名称
    abbreviation: string; // 客户简称
    sourceCode: string; // 客户英文编码
    sourceUniqueCode: string; // 统一社会信用代码
    salesman: string; // 业务员
    phoneNumber: string; // 业务员电话
    isDeleted: number;
    version: number;
  }
}
