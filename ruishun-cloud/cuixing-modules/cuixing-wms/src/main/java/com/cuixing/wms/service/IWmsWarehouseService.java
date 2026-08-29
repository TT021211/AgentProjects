package com.cuixing.wms.service;


import com.cuixing.system.api.domain.wms.WmsWarehouse;

import java.math.BigInteger;
import java.util.List;

public interface IWmsWarehouseService {
    WmsWarehouse selectWmWarehouseByWarehouseId(Long var1);

    WmsWarehouse selectWmWarehouseByWarehouseCode(String var1);

    List<WmsWarehouse> selectWmWarehouseList(WmsWarehouse var1);

    List<WmsWarehouse> getTreeList();

    String checkWarehouseCodeUnique(WmsWarehouse var1);

    String checkWarehouseNameUnique(WmsWarehouse var1);

    int insertWmWarehouse(WmsWarehouse var1);

    int updateWmWarehouse(WmsWarehouse var1);

    int deleteWmWarehouseByWarehouseIds(Long[] var1);

    int deleteWmWarehouseByWarehouseId(Long var1);

    WmsWarehouse initVirtualWarehouse();

    WmsWarehouse getinfoByWorkstageId(BigInteger workstageId);

    WmsWarehouse selectWmWarehouseBytype(String type);
}
