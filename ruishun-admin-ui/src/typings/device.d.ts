// 设备管理

// 设备台账
declare namespace equipmentLedger {
  interface searchForm {
    pageNum: number;
    pageSize: number;
    total: number;
    equipmentCode: string;
    workbenchId: string | null;
    status: string | null;
  }

  interface tabelList {
    equipmentId: string | null;
    equipmentCode: string;
    equipmentName: string;
    workbenchType: string;
    equipmentModel: string;
    status: string;
    putWireDiameter: string;
    outWireDiameter: string;
    buyTime: string | null;
    remark: string;
  }
}
