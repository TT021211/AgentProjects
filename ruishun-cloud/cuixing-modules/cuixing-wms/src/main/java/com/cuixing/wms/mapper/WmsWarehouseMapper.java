package com.cuixing.wms.mapper;



import com.cuixing.system.api.domain.wms.WmsWarehouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface WmsWarehouseMapper {
    WmsWarehouse selectWmWarehouseByWarehouseId(Long var1);

    WmsWarehouse selectWmWarehouseByWarehouseCode(String var1);

    List<WmsWarehouse> selectWmWarehouseList(WmsWarehouse var1);

    List<WmsWarehouse> getTreeList();

    WmsWarehouse checkWarehouseCodeUnique(WmsWarehouse var1);

    WmsWarehouse checkWarehouseNameUnique(WmsWarehouse var1);

    int insertWmWarehouse(WmsWarehouse var1);

    int updateWmWarehouse(WmsWarehouse var1);

    int deleteWmWarehouseByWarehouseId(Long var1);

    int deleteWmWarehouseByWarehouseIds(Long[] var1);

    WmsWarehouse getinfoByWorkstageId(@Param("workstageId") BigInteger workstageId);

    WmsWarehouse getinfoBytype(String type);
}
