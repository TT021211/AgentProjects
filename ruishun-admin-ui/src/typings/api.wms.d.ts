/**
 * 库存记录管理
 */
declare namespace ApiMaterialStockManagement {
  interface materialStock {
    /** 事务ID */
    materialStockId: number;
    /** 物料类型ID */
    itemTypeId: number;
    /** 产品物料ID */
    itemId: number;
    /** 产品物料编码 */
    itemCode: string | null;
    /** 产品物料名称 */
    itemName: string | null;
    /** 规格型号 */
    specification: string;
    /** 单位 */
    unitOfMeasure: string;
    /** 入库批次号 */
    batchCode: string;
    /** 仓库ID */
    warehouseId: number;
    /** 仓库编码 */
    warehouseCode: string;
    /** 仓库名称 */
    warehouseName: string;
    /** 库区ID */
    locationId: number;
    /** 库区编码 */
    locationCode: string;
    /** 库区名称 */
    locationName: string;
    /** 库位ID */
    areaId: number;
    /** 库位编码 */
    areaCode: string;
    /** 库位名称 */
    areaName: string;
    /** 供应商ID */
    vendorId: number;
    /** 供应商编号 */
    vendorCode: string;
    /** 供应商名称 */
    vendorName: string;
    /** 供应商简称 */
    vendorNick: string;
    /** 在库数量 */
    quantityOnhand: number;
    /** 生产工单ID */
    workorderId: number;
    /** 生产工单编号 */
    workorderCode: string;
    /** 入库时间 */
    recptDate: string;
    /** 库存有效期 */
    expireDate: string;
    /** 预留字段1 */
    attr1: string;
    /** 预留字段2 */
    attr2: string;
    /** 预留字段3 */
    attr3: number;
    /** 预留字段4 */
    attr4: number;
    /** 创建者 */
    createBy: string;
    /** 创建时间 */
    createTime: string;
    /** 更新者 */
    updateBy: string;
    /** 更新时间 */
    updateTime: string;
    /** 数量 */
    transactionQuantity: number;
    afterQuantity: number;
    beforeQuantity: number;
    beforeWeight: number;
    afterWeight: number;
    waringValue: number;
    status?: string;
    artt9?: any;
    artt10?: any;
  }
}
