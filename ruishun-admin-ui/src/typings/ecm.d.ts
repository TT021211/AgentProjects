// 能耗
declare namespace Energy {
  namespace Electrovalence {
    interface Columns {
      stepTariffId: number;
      startTime: number | undefined;
      endTime: number | undefined;
      name: string | undefined;
      electricityPrice: number | undefined;
      month: string;
    }
  }
  namespace energyDevice {
    interface Columns {
      electricDeviceName: string | undefined;
      createBy: string | undefined;
      createTime: string | undefined;
      electricDeviceCode: string | undefined;
      electricDeviceId: string | undefined;
      enableFlag: string | undefined;
      installationDate: string | undefined;
      isDeleted: string | undefined;
      location: string | undefined;
      manufacturer: string | undefined;
      model: string | undefined;
      remark: string | undefined;
      status: string | undefined;
      type: string | undefined;
      updateBy: string | undefined;
      updateTime: string | undefined;
      isMain: number | null;
      parentId: number | null;
    }
    interface AddForm {
      electricDeviceId?: string | undefined;
      electricDeviceName: string | undefined;
      electricDeviceCode: string | undefined;
      installationDate: number | undefined;
      status: string | undefined;
      location: string | undefined;
      manufacturer: string | undefined;
      remark: string | undefined;
      locationId: string | null;
      isMain: number;
      parentId: null | number;
    }
  }
  namespace machineEnergy {
    interface Columns {
      consumptionId: string | undefined;
      createBy: string | undefined;
      createTime: string | undefined;
      electricDeviceCode: string | undefined;
      electricDeviceId: string | undefined;
      enableFlag: string | undefined;
      isDeleted: string | undefined;
      params: string | undefined;
      recordingTime: string | undefined;
      remark: string | undefined;
      type: string | undefined;
      updateBy: string | undefined;
      updateTime: string | undefined;
      value: string | undefined;
    }
  }
}
