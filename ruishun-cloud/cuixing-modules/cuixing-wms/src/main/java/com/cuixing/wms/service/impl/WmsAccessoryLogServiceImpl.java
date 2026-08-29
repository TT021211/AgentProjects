package com.cuixing.wms.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.constant.WmsConstants;
import com.cuixing.common.core.exception.md.ItemException;
import com.cuixing.common.core.exception.wms.RealtimeStockException;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.domain.md.MdAccessory;
import com.cuixing.system.api.domain.wms.WmsWarehouse;
import com.cuixing.wms.entity.WmsTransaction;
import com.cuixing.wms.mapper.WmsTransactionMapper;
import com.cuixing.wms.service.IWmsTransactionService;
import com.cuixing.wms.service.IWmsWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.wms.entity.WmsAccessoryLog;
import com.cuixing.wms.mapper.WmsAccessoryLogMapper;
import com.cuixing.wms.service.IWmsAccessoryLogService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
 /**
 * 辅料出入库记录;(wms_accessory_log)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-28
 */
@Service
public class WmsAccessoryLogServiceImpl implements IWmsAccessoryLogService{
    @Autowired
    private WmsAccessoryLogMapper wmsAccessoryLogMapper;

    @Autowired
    private RemoteMdService remoteMdService;

    @Autowired
    private WmsTransactionMapper wmsTransactionMapper;

    @Autowired
    private IWmsWarehouseService iWmsWarehouseService;


    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WmsAccessoryLog queryById(BigInteger id){
        return wmsAccessoryLogMapper.queryById(id);
    }
    
    /** 
     * 分页查询
     *
     * @param wmsAccessoryLog 筛选条件
     * @return 查询结果
     */
    @Override
    public List<WmsAccessoryLog> selectWmsAccessoryLogList(WmsAccessoryLog wmsAccessoryLog){
       return wmsAccessoryLogMapper.selectWmsAccessoryLogList(wmsAccessoryLog);
    }
    
    /** 
     * 新增数据
     *
     * @param wmsAccessoryLog 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public int insert(WmsAccessoryLog wmsAccessoryLog){
    	wmsAccessoryLog.setCreateTime(DateUtils.getNowDate());
		wmsAccessoryLog.setCreateBy(SecurityUtils.getUsername());

        //添加辅料库存数据
        WmsTransaction transaction = new WmsTransaction();
        //获取辅料信息
        MdAccessory mdAccessory = remoteMdService.getAccessory(wmsAccessoryLog.getAccessoryId(), SecurityConstants.FROM_SOURCE).getData();
        if (null == mdAccessory){
            throw new ItemException("辅料获取失败");
        }
        transaction.setItemId(mdAccessory.getId().longValue());
        transaction.setItemCode(mdAccessory.getAttr1());
        transaction.setItemName(mdAccessory.getItemTypeName());
        transaction.setSpecification(mdAccessory.getModel());
        transaction.setUnitOfMeasure(mdAccessory.getMeasureName());
        //字典键值
        WmsWarehouse wmsWarehouse = iWmsWarehouseService.selectWmWarehouseBytype("3");
        if(null == wmsWarehouse){
            throw new RealtimeStockException("字典键值对应错误");
        }
        transaction.setWarehouseId(wmsWarehouse.getWarehouseId());
        transaction.setWarehouseCode(wmsWarehouse.getWarehouseCode());
        transaction.setWarehouseName(wmsWarehouse.getWarehouseName());

        transaction.setAfterQuantity(new BigDecimal(wmsAccessoryLog.getNumber()));
        transaction.setCreateTime(DateUtils.getNowDate());
        transaction.setCreateBy(SecurityUtils.getUsername());
        transaction.setTransactionQuantity(new BigDecimal(1));

        if(wmsAccessoryLog.getStatus().equals("0")){
            transaction.setTransactionFlag(WmsConstants.STORAGE_CHECK_FLAG_IN);
            transaction.setTransactionType(WmsConstants.ITEM_RECPT_TYPE);
        }else{
            transaction.setTransactionFlag(WmsConstants.STORAGE_CHECK_FLAG_OUT);
            transaction.setTransactionType(WmsConstants.ITEM_ISSUE_OUT_TYPE);
        }
        transaction.setTransactionDate(new Date());
        transaction.setAttr1("2");
        BigDecimal quantity = transaction.getAfterQuantity().multiply(new BigDecimal(transaction.getTransactionFlag()));
        //库存是否有这条数据
        WmsTransaction wmsTransaction1 = wmsTransactionMapper.selectByItemCode(transaction.getItemCode(), transaction.getAttr1());
        if(wmsTransaction1 == null){
            transaction.setBeforeQuantity(BigDecimal.ZERO);
            transaction.setAfterQuantity(quantity);
        }else{
            transaction.setBeforeQuantity(wmsTransaction1.getAfterQuantity());
            transaction.setAfterQuantity(wmsTransaction1.getAfterQuantity().add(quantity));
            if (wmsTransaction1.getWaringValue() != null){
                transaction.setWaringValue(wmsTransaction1.getWaringValue());
            }
        }
        if (transaction.getAfterQuantity().doubleValue() < 0){
            throw new RealtimeStockException("操作失败，库存不足");
        }
        wmsTransactionMapper.insertWmsTransaction(transaction);

        return wmsAccessoryLogMapper.insert(wmsAccessoryLog);
    }

    
    /** 
     * 更新数据
     *
     * @param wmsAccessoryLog 实例对象
     * @return 实例对象
     */
    @Override
    public int update(WmsAccessoryLog wmsAccessoryLog){
        wmsAccessoryLog.setUpdateTime(DateUtils.getNowDate());
		wmsAccessoryLog.setUpdateBy(SecurityUtils.getUsername());
        return wmsAccessoryLogMapper.update(wmsAccessoryLog);
    }
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return wmsAccessoryLogMapper.deleteByIds(ids);
    }
}