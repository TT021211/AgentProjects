package com.cuixing.wms.service.impl;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.utils.StringUtils;
import com.cuixing.common.core.utils.bean.BeanUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import com.cuixing.wms.entity.WmsTransaction;
import com.cuixing.wms.mapper.WmsRealtimeStockMapper;
import com.cuixing.wms.mapper.WmsTransactionMapper;
import com.cuixing.wms.service.IWmsTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class WmsTransactionServiceImpl implements IWmsTransactionService {
    @Autowired
    private WmsTransactionMapper wmsTransactionMapper;
    @Autowired
    private WmsRealtimeStockMapper wmMaterialStockMapper;
    @Autowired
    private RemoteSystemService remoteSystemService;

    public WmsTransactionServiceImpl() {
    }

    @Override
    public synchronized WmsTransaction processTransaction(WmsTransaction wmsTransaction) {

        BigDecimal quantity = wmsTransaction.getTransactionQuantity().multiply(new BigDecimal(wmsTransaction.getTransactionFlag()));
        BigDecimal totalWeight = new BigDecimal(0);
        if (wmsTransaction.getTotalWeight() != null) {
            totalWeight = wmsTransaction.getTotalWeight().multiply(new BigDecimal(wmsTransaction.getTransactionFlag()));
        }

        wmsTransaction.setTransactionQuantity(quantity);
        wmsTransaction.setTotalWeight(totalWeight);
        wmsTransaction.setCreateTime(new Date());
        WmsTransaction wmsTransaction1 = wmsTransactionMapper.selectByItemCode(wmsTransaction.getItemCode(),wmsTransaction.getAttr1());
        if(wmsTransaction1 == null){
            wmsTransaction.setBeforeQuantity(BigDecimal.ZERO);
            wmsTransaction.setAfterQuantity(quantity);
            wmsTransaction.setBeforeWeight(BigDecimal.ZERO);
            wmsTransaction.setAfterWeight(totalWeight);
        }else{

            wmsTransaction.setBeforeQuantity(wmsTransaction1.getAfterQuantity());
            wmsTransaction.setAfterQuantity(wmsTransaction1.getAfterQuantity().add(quantity));
            wmsTransaction.setBeforeWeight(wmsTransaction1.getAfterWeight());
            wmsTransaction.setAfterWeight(wmsTransaction1.getAfterWeight().add(totalWeight));
            wmsTransaction.setWaringValue(wmsTransaction1.getWaringValue());
        }
        this.wmsTransactionMapper.insertWmsTransaction(wmsTransaction);
        return wmsTransaction;
    }

    // 类成员变量，用于缓存查询结果
    private WmsTransaction cachedWmsTransaction1 = null;
    @Override
    public synchronized WmsTransaction processTransaction2(WmsTransaction wmsTransaction, int start,String type,String taskType) {
        // 计算新的数量和重量
        BigDecimal quantity = wmsTransaction.getTransactionQuantity().multiply(new BigDecimal(wmsTransaction.getTransactionFlag()));
        BigDecimal totalWeight = new BigDecimal(0);
        if (wmsTransaction.getTotalWeight() != null) {
            totalWeight = wmsTransaction.getTotalWeight().multiply(new BigDecimal(wmsTransaction.getTransactionFlag()));
        }

        wmsTransaction.setTransactionQuantity(quantity);
        wmsTransaction.setTotalWeight(totalWeight);
        //第一次获取的最大库存信息
        if (type.equals("subtract")) {
            try {
                Thread.sleep(100);  // 延时 100 毫秒（可以根据需要调整）
                if (start==1){
                    cachedWmsTransaction1 = wmsTransactionMapper.selectByItemCode(wmsTransaction.getItemCode(), taskType);
                }
                // 减去库存 新增
                wmsTransaction.setBeforeQuantity(cachedWmsTransaction1.getAfterQuantity());
                wmsTransaction.setAfterQuantity(cachedWmsTransaction1.getAfterQuantity().add(quantity));
                wmsTransaction.setBeforeWeight(cachedWmsTransaction1.getAfterWeight());
                wmsTransaction.setAfterWeight(cachedWmsTransaction1.getAfterWeight().add(totalWeight));
                wmsTransaction.setCreateTime(DateUtils.getNowDate());
                // 更新第一次获取的最大库存
                cachedWmsTransaction1.setBeforeWeight(cachedWmsTransaction1.getAfterWeight());
                cachedWmsTransaction1.setAfterWeight(cachedWmsTransaction1.getAfterWeight().add(totalWeight));
                cachedWmsTransaction1.setBeforeQuantity(cachedWmsTransaction1.getAfterQuantity());
                cachedWmsTransaction1.setAfterQuantity(cachedWmsTransaction1.getAfterQuantity().add(quantity));
                this.wmsTransactionMapper.insertWmsTransaction(wmsTransaction);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
                throw new RuntimeException("插入数据时线程被中断", e);
            }
        }
        if (type.equals("add")){
            try {
                Thread.sleep(200);  // 延时 100 毫秒（可以根据需要调整）
                //新增转工艺库存
                WmsTransaction wmsTransaction2 = wmsTransactionMapper.selectByItemCode(wmsTransaction.getItemCode(), wmsTransaction.getAttr1());
                // 第一次新增库存
                if (wmsTransaction2 == null){
                    wmsTransaction.setBeforeQuantity(BigDecimal.ZERO);
                    wmsTransaction.setAfterQuantity(quantity);
                    wmsTransaction.setBeforeWeight(BigDecimal.ZERO);
                    wmsTransaction.setAfterWeight(totalWeight);
                    wmsTransaction.setCreateTime(DateUtils.getNowDate());           // 设置当前时间
                    this.wmsTransactionMapper.insertWmsTransaction(wmsTransaction);
                }else{
                    wmsTransaction.setBeforeQuantity(wmsTransaction2.getAfterQuantity());
                    wmsTransaction.setAfterQuantity(wmsTransaction2.getAfterQuantity().add(quantity));
                    wmsTransaction.setBeforeWeight(wmsTransaction2.getAfterWeight());
                    wmsTransaction.setAfterWeight(wmsTransaction2.getAfterWeight().add(totalWeight));
                    wmsTransaction.setCreateTime(DateUtils.getNowDate());
                    this.wmsTransactionMapper.insertWmsTransaction(wmsTransaction);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
                throw new RuntimeException("插入数据时线程被中断", e);
            }
        }
        return wmsTransaction;
    }

    private void validate(WmsTransaction transaction) {
        if (StringUtils.isNull(transaction.getTransactionType())) {
            throw new BussinessException("库存事务不能为空");
        } else if (StringUtils.isNull(transaction.getTransactionQuantity())) {
            throw new BussinessException("事务数量不能为空");
        } else if (StringUtils.isNull(transaction.getSourceDocCode())) {
            throw new BussinessException("来源单据号不能为空");
        } else if (StringUtils.isNull(transaction.getSourceDocLineId())) {
            throw new BussinessException("来源单据行ID不能为空");
        } else {
            if (StringUtils.isNull(transaction.getTransactionDate())) {
                transaction.setTransactionDate(new Date());
            }

            if (StringUtils.isNull(transaction.getRecptDate())) {
                transaction.setRecptDate(new Date());
            }

        }
    }


    public void initStock(WmsTransaction transaction, WmsRealtimeStock stock) {
        if (StringUtils.isNotNull(transaction.getMaterialStockId())) {
            WmsRealtimeStock st = this.wmMaterialStockMapper.selectWmMaterialStockByMaterialStockId(transaction.getMaterialStockId());
            BeanUtils.copyProperties(st, stock);
        } else {
//            MdItem item = this.mdItemMapper.selectMdItemById(transaction.getItemId());
//            stock.setItemTypeId(item.getItemTypeId());
            stock.setItemId(transaction.getItemId());
            stock.setItemCode(transaction.getItemCode());
            stock.setItemName(transaction.getItemName());
            stock.setSpecification(transaction.getSpecification());
            stock.setUnitOfMeasure(transaction.getUnitOfMeasure());
            stock.setBatchCode(transaction.getBatchCode());
            stock.setContainerCode(transaction.getContainerCode());
            stock.setWarehouseId(transaction.getWarehouseId());
            stock.setWarehouseCode(transaction.getWarehouseCode());
            stock.setWarehouseName(transaction.getWarehouseName());
            stock.setLocationId(transaction.getLocationId());
            stock.setLocationCode(transaction.getLocationCode());
            stock.setLocationName(transaction.getLocationName());
            if (StringUtils.isNotNull(transaction.getAreaId())) {
                stock.setAreaId(transaction.getAreaId());
                stock.setAreaCode(transaction.getAreaCode());
                stock.setAreaName(transaction.getAreaName());
            }

            if (StringUtils.isNotNull(transaction.getVendorId())) {
                stock.setVendorId(transaction.getVendorId());
            }

            stock.setVendorCode(transaction.getVendorCode());
            stock.setVendorName(transaction.getVendorName());
            stock.setVendorNick(transaction.getVendorNick());
            if (StringUtils.isNotNull(transaction.getRecptDate())) {
                stock.setRecptDate(new Date());
            } else {
                stock.setRecptDate(new Date());
            }

            if (StringUtils.isNotNull(transaction.getWorkorderId())) {
                stock.setWorkorderId(transaction.getWorkorderId());
                stock.setWorkorderCode(transaction.getWorkorderCode());
            }

            stock.setExpireDate(transaction.getExpireDate());
        }

    }

    @Override
    public WmsTransaction selectWmsTransactionByTransactionId(Long transactionId) {
        return this.wmsTransactionMapper.selectWmsTransactionByTransactionId(transactionId);
    }

    @Override
    public List<WmsTransaction> selectWmsTransactionList(WmsTransaction WmsTransaction) {
        return this.wmsTransactionMapper.selectWmsTransactionList(WmsTransaction);
    }

    @Override
    public List<WmsTransaction> selectWmsTransactionGroupList(WmsTransaction WmsTransaction) {
        return this.wmsTransactionMapper.selectWmsTransactionGroupList(WmsTransaction);
    }


    @Override
    public int insertWmsTransaction(WmsTransaction wmsTransaction) {
        // 设置创建时间和创建人
        wmsTransaction.setCreateTime(DateUtils.getNowDate());
        wmsTransaction.setCreateBy(SecurityUtils.getUsername());

        // 延时操作，保证每次插入都有时间差
        try {
            Thread.sleep(100);  // 延时 100 毫秒（可以根据需要调整）
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复中断状态
            throw new RuntimeException("插入数据时线程被中断", e);
        }

        // 执行插入操作
        return this.wmsTransactionMapper.insertWmsTransaction(wmsTransaction);
    }


    @Override
    public int updateWmsTransaction(WmsTransaction WmsTransaction) {
        WmsTransaction.setUpdateTime(DateUtils.getNowDate());
        WmsTransaction.setUpdateBy(SecurityUtils.getUsername());
        return this.wmsTransactionMapper.updateWmsTransaction(WmsTransaction);
    }

    @Override
    public int deleteWmsTransactionByTransactionIds(Long[] transactionIds) {
        return this.wmsTransactionMapper.deleteWmsTransactionByTransactionIds(transactionIds);
    }

    @Override
    public int deleteWmsTransactionByTransactionId(Long transactionId) {
        return this.wmsTransactionMapper.deleteWmsTransactionByTransactionId(transactionId);
    }

    @Override
    public List<WmsTransaction> selectWaringValueList(WmsTransaction wmTransaction) {
        return this.wmsTransactionMapper.selectWaringValueList(wmTransaction);
    }

    /**
     * 通过库存id、出入库类型查询数据
     */
    @Override
    public WmsTransaction selectByStockIdAndType(Long materialStockId){
        return this.wmsTransactionMapper.selectByStockIdAndType(materialStockId);
    }

}
