declare namespace Procure {
  interface searchForm extends page {
    supplierName: string;
    materialsName: string;
    materialsCode: string;
  }
  namespace report {
    interface columns {
      id?: number;
      procureDate: string;
      supplierName: string;
      materialsName: string;
      materialsCode: string;
      unit: string;
      contractCount: string;
      price: string;
      arrivedTime: string;
      actualCount: string;
      totalPrice: string;
      invoice: string;
      paymentTime: string;
    }
  }

  namespace rawProcure {
    interface columns {
      planId?: string | null | number;
      planCode: string | null; // 计划编码
      planDateStart: number | null; // 计划开始时间
      planDateEnd: number | null; // 计划结束时间
      itemName: string | null; // 产品物料名称
      itemId: string | null;
      itemCode: string | null;
      itemType: string | null;
      quantity: string | null; // 计划数量
      purchasedQuantity: string | null; // 已采购数量
      unitOfMeasure: string | null; // 单位
      createBy: string | null; // 计划员
      planMonth: string | null; // 计划时间
      status: string | null; // 状态
    }
  }
  namespace rawProcureList {
    interface listType {
      createBy: string | null | number;
      createTime: string | null | number;
      deliveryDate: string | null | number;
      enableFlag: string | null | number;
      fromBillNo: string | null | number;
      itemCode: string | null | number;
      itemId: string | null | number;
      itemName: string | null | number;
      lineId: string | null | number;
      params: { [key: string]: any };
      price: string | null | number;
      purchaseId: string | null | number;
      quantity: string | null | number;
      realTotalPrice: string | null | number;
      realTotalWeight: string | null | number;
      receivedDate: string | null | number;
      remark: string | null | number;
      specification: string | null | number;
      totalPrice: string | null | number;
      totalWeight: string | null | number;
      type: string | null | number;
      unitOfMeasure: string | null | number;
      updateBy: string | null | number;
      updateTime: string | null | number;
      weight: string | null | number;
      weightUnit: string | null | number;
    }
    interface columns {
      attr1: string | null;
      children?: any[];
      beginTime: string | null | number;
      checkedBy: string | null | number;
      checkedByNick: string | null | number;
      checkedTime: string | null | number;
      contractTime: string | null | number;
      createBy: string | null | number;
      createByNick: string | null | number;
      createTime: string | null | number;
      deptId: string | null | number;
      deptName: string | null | number;
      enableFlag: string | null | number;
      endTime: string | null | number;
      filePath: string | null | number;
      flowAction: string | null | number;
      flowRecordJson: string | null | number;
      invoiceSta: string | null | number;
      lineList: listType[];
      paidMoney: string | null | number;
      params: { [key: string]: any };
      payableMoney: string | null | number;
      planCode: string | null | number;
      planId: string | null | number;
      purchaseCode: string | null | number;
      purchaseDate: string | null | number;
      purchaseId: string | null | number;
      quantity: string | null | number;
      realTotalPrice: string | null | number;
      realTotalWeight: string | null | number;
      remark: string | null | number;
      snidCode: string | null | number;
      status: string | null | number;
      totalPrice: string | null | number;
      totalWeight: string | null | number;
      type: string | null | number;
      updateBy: string | null | number;
      updateTime: string | null | number;
      vendorAddress: string | null | number;
      vendorCode: string | null | number;
      vendorContact1: string | null | number;
      vendorContact1Tel: string | null | number;
      vendorId: string | null | number;
      vendorName: string | null | number;
    }
  }
  namespace contractMange {
    interface searchContractForm {
      userName: string;
      userPhone: string;
      procureContractCode: string;
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
      demandName: string;
      demandAddress: string;
      demandLegal: string;
      demandPhone: string;
      demandFax: string;
      demandBank: string;
      demandAccount: string;
      procureContractCode: string;
      productName: string;
      productCode: string;
      procureNumber: string;
      prucureUnit: string;
      procurePrice: string;
      procureMoney: string;
      procureDate: string;
      remake: string;
      contractNote: string;
    }
    interface addForm {
      id?: number;
      userName: string;
      userAddress: string;
      userLegal: string;
      userPhone: string;
      userFax: string;
      userBank: string;
      userAccount: string;
      demandName: string;
      demandAddress: string;
      demandLegal: string;
      demandPhone: string;
      demandFax: string;
      demandBank: string;
      demandAccount: string;
      procureContractCode: string;
      productName: string;
      productCode: string;
      procureNumber: string;
      prucureUnit: string;
      procurePrice: string;
      procureMoney: string;
      procureDate: string | null;
      remake: string;
      contractNote: string;
      isDeleted: number;
      version: number;
    }
  }
}
