declare namespace Md {
  interface CopperBar {
    weightMeasureName: string;
    id: number;
    weightMeasureId: string;
    copperbarName: string;
    copperbarDiameter: string;
    unitWeight: string;
    createBy: string;
    createTime: string;
  }
  interface CopperBarSearch {
    copperbarName: string;
    copperbarDiameter: string;
    weightMeasureId: string | null;
    pageNum: number;
    pageSize: number;
    total: number;
  }
  interface CopperBarAdd {
    weightMeasureName: string;
    id?: number;
    copperbarDiameter: string;
    copperbarName: string;
    unitWeight: string;
    weightMeasureId: string | null;
  }
  interface WorkGroupMember {
    memberName: string;
    memberId: string;
    workgroupName: string;
    orkgroupCode: string;
    headName: string;
    ifEnable: number;
    createTime: string;
  }
  interface WorkMember {
    userId: number;
    userName: string;
    nickName: string;
    email: string;
    phonenumber: string;
  }
  interface WorkGroupSearch {
    workgroupId?: string;
    memberName: string;
    memberId: string;
    pageNum: number;
    pageSize: number;
    total: number;
  }
  interface workUser {
    phonenumber: string;
    userName: string | null;
    pageNum: number;
    pageSize: number;
    total: number | undefined;
  }
}
declare namespace List {
  type DataForm = {
    productId: number | string | null;
    id: number;
    processRouteId: string | null;
    productTypeId: string;
    productTypeName: string;
    productName: string;
    productCode: string;
    productSize: string;
    productSizeInternational: string;
    numMeasureId: string;
    weightMeasureId: string;
    specification: string;
    processRouteName: string;
    numMeasureName: string;
    weightMeasureName: string;
    createTime: string;
    remark: string;
    ifEnable: number;
    batchNumber: string | null;
    axialType: string;
    coefficient: string;
    materialCode: string;
  };
  type SearchForm = {
    productTypeId: string;
    productName: string;
    specification: string;
    batchNumber: string;
    pageNum: number;
    pageSize: number;
    total: number;
  };
  type AddForm = {
    processRouteName: string;
    id?: number;
    productTypeId: string;
    productSize: string | null;
    productSizeInternational: string;
    specification: string;
    processRouteId: string | null;
    productName: string;
    productTypeName?: string;
    weightMeasureId: string | null;
    weightMeasureName?: string;
    remark: string;
    ifEnable: number;
    batchNumber: string | null;
    axialType: string;
    coefficient: string;
    materialCode: string;
  };
}
declare namespace Type {
  interface searchForm {
    id: string | null;
    typeCode: string;
    ifEnable: number | null;
    pageNum: number;
    pageSize: number;
    total: number;
  }
  interface addForm {
    id?: number;
    typeName: string;
    typeCode: string;
    parentTypeId: number | null;
    ifEnable: number;
  }
  interface DataForm {
    createBy: string;
    createTime: string;
    updateBy: string;
    updateTime: string;
    remark: string;
    id: number;
    typeName: string;
    typeCode: string;
    parentTypeId: number;
    ancestors: string;
    ifEnable: number;
    version: number;
    isDeleted: number;
    attr1: string | null;
    attr2: string | null;
    attr3: string | null;
    children?: DataForm[];
  }
}
declare namespace Item {
  interface List {
    id: number;
    itemCode: string;
    itemName: string;
    itemTypeName: string;
    itemTypeCode?: string;
    itemTypeId: string | null | number;
    workstageId: string | null;
    workstageName: string;
    numMeasureName: string;
    minStock: string;
    maxStock: string;
    createTime: string;
    remark: string;
    ifEnable: number;
    attr1: string | null;
    attr2: string | null;
    attr3: string | null;
  }
  interface SearchForm {
    itemCode: string;
    numMeasureId?: string | null;
    itemName: string;
    numMeasureName?: string;
    itemTypeName?: string;
    itemTypeId?: string | number | null;
    pageNum: number;
    pageSize: number;
    total: number;
    weightMeasureId?: string | null;
  }
  interface AddForm {
    remark: string;
    id?: number;
    specification?: string;
    itemTypeId: string | number | null;
    itemTypeName?: string;
    workstageId: string | null;
    workstageName?: string;
    itemName: string;
    weightMeasureId?: string | null;
    weightMeasureName?: string;
    ifEnable: number;
  }
}

declare namespace PrintTemplate {
  interface Data {
    clientCode: null | string;
    clientId: null | string;
    clientName: null | string;
    code: null | string;
    content: null | string;
    createBy: null | string;
    createTime: null | string;
    defaultPrinterName: null | string;
    enableFlag: null | string;
    remark: null | string;
    status: null | string;
    templateCode: null | string;
    templateId: null | string;
    templateName: null | string;
    type: null | string;
    updateBy: null | string;
    updateTime: null | string;
    userId: null | string;
    userNickName: null | string;
  }
}
