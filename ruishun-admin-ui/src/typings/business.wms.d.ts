// 库存记录管理
declare namespace MaterialStockManagement {
  interface materialStock extends ApiMaterialStockManagement.materialStock {
    /** 序号 */
    index: number;
    /** 表格的key（id） */
    key: string;
  }
}
