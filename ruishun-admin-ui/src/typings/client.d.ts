type SelectMixedOption = {
  label: string;
  value: number;
};

type SelectValueStringOption = {
  label: string;
  value: string;
};

declare class search {
  pageNum: number;

  pageSize: number;

  total: number;
}

declare namespace information {
  interface addForm {
    id?: number;
    contact2: number | null;
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
    attr1: string | null;
    attr2: number | null;
    addressId: string | null;
    contact2Tel: string;
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
    contact1Tel?: string; // 联系人1电话
    legalPersonName: string; // 法人姓名
    legalPersonPhone: string; // 法人电话
    salesmanName: string; // 业务员
    salesmanPhone: string; // 业务员电话
    attr1: string;
    attr2: number;
    leadSource: number;
    contact2: number;
    contact2Tel: string;
  }
}

declare namespace contactPerson {
  interface addOrEditForm {
    id: number | null;
    clientId: number | null;
    clientName: string;
    clientCode: string;
    personName: string;
    personPhone: string;
    section: string | null;
    job: string;
    wxNumber: string;
    mailbox: string;
    detail: string;
    isDecision: string;
    dutyBy: string;
  }

  interface TableList {
    id: number;
    clientName: string;
    clientCode: string;
    personName: string;
    personPhone: string;
    section: string;
    job: string;
    wxNumber: string;
    mailbox: string;
    detail: string;
    isDecision: string;
    dutyBy: string;
  }
}
declare namespace interviewRecord {
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

  interface recordSearchForm extends search {
    clientId: number | null;
    clientName: string;
  }
}
