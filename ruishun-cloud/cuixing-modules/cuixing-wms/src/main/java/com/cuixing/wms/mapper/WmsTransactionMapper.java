package com.cuixing.wms.mapper;


import com.cuixing.wms.entity.WmsTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
@Mapper
public interface WmsTransactionMapper {

    WmsTransaction selectWmsTransactionByTransactionId(Long var1);

    List<WmsTransaction> selectWmsTransactionList(WmsTransaction var1);
    List<WmsTransaction> selectWmsTransactionGroupList(WmsTransaction var1);

    int insertWmsTransaction(WmsTransaction var1);

    int updateWmsTransaction(WmsTransaction var1);

    int deleteWmsTransactionByTransactionId(Long var1);

    int deleteWmsTransactionByStockId(@Param("materialStockId") Long materialStockId);

    int deleteWmsTransactionByTransactionIds(Long[] var1);

    WmsTransaction selectByItemCode(@Param("itemCode") String itemCode,@Param("attr1") String attr1);

    WmsTransaction selectByItemCode2(@Param("itemCode") String itemCode,@Param("attr2") String attr2);

    List<WmsTransaction> selectWaringValueList(WmsTransaction wmTransaction);

    WmsTransaction selectByStockIdAndType(@Param("materialStockId") Long materialStockId);

    BigDecimal getCountWeight(@Param("status") String status);
}
