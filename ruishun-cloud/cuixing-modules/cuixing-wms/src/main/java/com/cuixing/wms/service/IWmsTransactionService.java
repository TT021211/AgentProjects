package com.cuixing.wms.service;


import com.cuixing.wms.entity.WmsTransaction;

import java.math.BigInteger;
import java.util.List;

public interface IWmsTransactionService {

    WmsTransaction processTransaction(WmsTransaction var1);
    WmsTransaction processTransaction2(WmsTransaction var1,int start,String type,String taskType);

    WmsTransaction selectWmsTransactionByTransactionId(Long var1);

    List<WmsTransaction> selectWmsTransactionGroupList(WmsTransaction var1);
    List<WmsTransaction> selectWmsTransactionList(WmsTransaction var1);

    int insertWmsTransaction(WmsTransaction var1);

    int updateWmsTransaction(WmsTransaction var1);

    int deleteWmsTransactionByTransactionIds(Long[] var1);

    int deleteWmsTransactionByTransactionId(Long var1);

    List<WmsTransaction> selectWaringValueList(WmsTransaction wmTransaction);

    WmsTransaction selectByStockIdAndType(Long materialStockId);
}
