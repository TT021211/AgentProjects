package com.cuixing.wms.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.constant.WmsConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.wms.RealtimeStockException;
import com.cuixing.common.core.text.Convert;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.core.utils.StringUtils;
import com.cuixing.common.core.utils.bean.BeanUtils;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.system.api.RemoteMesService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.mes.MesOuputLog;
import com.cuixing.system.api.domain.wms.WmsWarehouse;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import com.cuixing.wms.entity.WmsIngredientsLog;
import com.cuixing.wms.entity.WmsTransaction;
import com.cuixing.wms.mapper.WmsIngredientsLogMapper;
import com.cuixing.wms.mapper.WmsWarehouseMapper;
import com.cuixing.wms.mapper.WmsRealtimeStockMapper;
import com.cuixing.wms.service.IWmsRealtimeStockService;
import com.cuixing.wms.service.IWmsTransactionService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存Service业务层处理
 *
 * @author wupujian
 * @date 2024-05-29
 */
@Service
public class WmsRealtimeStockServiceImpl implements IWmsRealtimeStockService
{
    @Autowired
    private WmsRealtimeStockMapper wmsRealtimeStockMapper;


    @Autowired
    private WmsWarehouseMapper wmsWarehouseMapper;

    @Autowired
    private RemoteSystemService remoteSystemService;

    @Autowired
    private IWmsTransactionService wmsTransactionService;

    @Autowired
    private WmsIngredientsLogMapper wmsIngredientsLogMapper;

    @Autowired
    private RemoteMesService remoteMesService;

    /**
     * 查询库存
     *
     * @param materialStockId 库存主键
     * @return 库存
     */
    @Override
    public WmsRealtimeStock selectWmsRealtimeStockByMaterialStockId(Long materialStockId)
    {
        return wmsRealtimeStockMapper.selectWmsRealtimeStockByMaterialStockId(materialStockId);
    }

    /**
     * 查询库存列表
     *
     * @param wmsRealtimeStock 库存
     * @return 库存
     */
    @Override
    public List<WmsRealtimeStock> selectWmsRealtimeStockList(WmsRealtimeStock wmsRealtimeStock)
    {
        return wmsRealtimeStockMapper.selectWmsRealtimeStockList(wmsRealtimeStock);
    }

    /**
     * 新增库存
     *
     * @param wmsRealtimeStock 库存
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertWmsRealtimeStock(WmsRealtimeStock wmsRealtimeStock, String type)
    {
        if(wmsRealtimeStock.getWeight() == null){
            wmsRealtimeStock.setWeight(new BigDecimal(0));
        }
        if(wmsRealtimeStock.getQuantityOnhand() == null){
            wmsRealtimeStock.setQuantityOnhand(new BigDecimal(0));
        }
        int result = 0;
        if(StringUtils.equals(type, "add")){
            wmsRealtimeStock.setRecptDate(DateUtils.getNowDate());
            wmsRealtimeStock.setCreateTime(DateUtils.getNowDate());
            wmsRealtimeStock.setCreateBy(SecurityUtils.getUsername());
            result = wmsRealtimeStockMapper.insertWmsRealtimeStock(wmsRealtimeStock);
            processTransaction(wmsRealtimeStock, type);
        }else if(StringUtils.equals(type, "subtract")){
                    if (wmsRealtimeStock.getScanCount() > 0){
                        WmsRealtimeStock updateWmsRealtimeStock = wmsRealtimeStockMapper.selectWmsRealtimeStockByMaterialStockId(wmsRealtimeStock.getMaterialStockId());
                        updateWmsRealtimeStock.setScanCount(updateWmsRealtimeStock.getScanCount() + 1);
                        return wmsRealtimeStockMapper.updateWmsRealtimeStock(updateWmsRealtimeStock);
                    }
                    if (wmsRealtimeStock.getWarehouseId()!=52L){
                        //如果删减库存为原料，则添加一条原料出库记录
                        WmsWarehouse wmsWarehouse = wmsWarehouseMapper.selectWmWarehouseByWarehouseId(wmsRealtimeStock.getWarehouseId());
                        //如果键值为6为空，则是原料仓
                        if (wmsWarehouse.getType().equals("6")) {
                            WmsIngredientsLog wmsIngredientsLog = wmsIngredientsLogMapper.queryByCodeSn(wmsRealtimeStock.getItemIdentifier());
                            if(null == wmsIngredientsLog){
                                throw new RealtimeStockException("无该物料的入库记录");
                            }
                            wmsIngredientsLog.setStatus("1");
                            wmsIngredientsLog.setCreateTime(DateUtils.getNowDate());
                            wmsIngredientsLog.setCreateBy(SecurityUtils.getUsername());
                            wmsIngredientsLog.setJobNumber("");
                            wmsIngredientsLog.setId(null);
                            wmsIngredientsLogMapper.insert(wmsIngredientsLog);
                        }
                    }
                    processTransaction(wmsRealtimeStock, type);
                    //库存流水
                    WmsRealtimeStock updateWmsRealtimeStock = wmsRealtimeStockMapper.selectWmsRealtimeStockByMaterialStockId(wmsRealtimeStock.getMaterialStockId());
                    updateWmsRealtimeStock.setScanCount(updateWmsRealtimeStock.getScanCount() + 1);
                    return wmsRealtimeStockMapper.updateWmsRealtimeStock(updateWmsRealtimeStock);
        }
        return  result;
    }

    //原料库错误处理
    @Transactional(rollbackFor = Exception.class)
    public int mistakeWmsRealtimeStock(WmsRealtimeStock wmsRealtimeStock){
        processTransaction(wmsRealtimeStock, "subtract");
        return wmsRealtimeStockMapper.deleteWmsRealtimeStockByMaterialStockId(wmsRealtimeStock.getMaterialStockId());
    }


    public void processTransaction(WmsRealtimeStock wmsRealtimeStock, String type){

        // 新增库存流水
        WmsTransaction transaction = new WmsTransaction();
        BeanUtils.copyBeanProp(transaction, wmsRealtimeStock);
        transaction.setTransactionQuantity(new BigDecimal(1));
        transaction.setMaterialStockId(wmsRealtimeStock.getMaterialStockId());
        if(type.equals("add")){
            transaction.setTransactionFlag(WmsConstants.STORAGE_CHECK_FLAG_IN);
            transaction.setTransactionType(WmsConstants.ITEM_RECPT_TYPE);
            if (wmsRealtimeStock.getAttr4().equals(1)){
                transaction.setAttr1("1");
            }
        }else{
            transaction.setTransactionFlag(WmsConstants.STORAGE_CHECK_FLAG_OUT);
            transaction.setTransactionType(WmsConstants.ITEM_ISSUE_OUT_TYPE);
            if (wmsRealtimeStock.getAttr4().equals(1)){
                transaction.setAttr1("1");
            }
        }
        transaction.setTransactionDate(new Date());
        this.wmsTransactionService.processTransaction(transaction);
    }
    public void processTransaction2(WmsRealtimeStock wmsRealtimeStock, String type,String type2,int start,String taskType){

        if (null == wmsRealtimeStock.getTotalWeight()){
            return;
        }
        // 新增库存流水
        WmsTransaction transaction = new WmsTransaction();
        BeanUtils.copyBeanProp(transaction, wmsRealtimeStock);
        transaction.setTransactionQuantity(new BigDecimal(1));
        transaction.setMaterialStockId(wmsRealtimeStock.getMaterialStockId());

        if(type.equals("add")){
            transaction.setTransactionFlag(WmsConstants.STORAGE_CHECK_FLAG_IN);
            transaction.setTransactionType(WmsConstants.ITEM_RECPT_TYPE);
            addArtt1(type2, transaction,wmsRealtimeStock);
        }else{
            transaction.setTransactionFlag(WmsConstants.STORAGE_CHECK_FLAG_OUT);
            transaction.setTransactionType(WmsConstants.ITEM_ISSUE_OUT_TYPE);
            addArtt1(type2, transaction,wmsRealtimeStock);
        }
        transaction.setTransactionDate(new Date());
        this.wmsTransactionService.processTransaction2(transaction,start,type,taskType);
    }
    private void addArtt1(String type, WmsTransaction transaction,WmsRealtimeStock wmsRealtimeStock){
        //退火
        if (Objects.equals(type, "1")){
            //添加退火在制品
            if (wmsRealtimeStock.getAttr4().equals(1)){
                transaction.setAttr1("4");
            }
        }
        //镀锡
        if (Objects.equals(type, "2")){
            //添加镀锡在制品
            if (wmsRealtimeStock.getAttr4().equals(1)){
                transaction.setAttr1("5");
            }
        }
        //并丝
        if (Objects.equals(type, "3")){
            //添加并丝在制品
            if (wmsRealtimeStock.getAttr4().equals(1)){
                transaction.setAttr1("6");
            }
        }
        //绞线
        if (Objects.equals(type, "4")){
            //添加绞线在制品
            if (wmsRealtimeStock.getAttr4().equals(1)){
                transaction.setAttr1("7");
            }
        }
    }
    /**
     * 修改库存
     *
     * @param wmsRealtimeStock 库存
     * @return 结果
     */
    @Override
    public int updateWmsRealtimeStock(WmsRealtimeStock wmsRealtimeStock)
    {
        // 查询库存现有量 扣减库存
        WmsRealtimeStock wmsRealtimeStock1 = wmsRealtimeStockMapper.selectWmsRealtimeStockByItemIdentifier(wmsRealtimeStock.getItemIdentifier(), wmsRealtimeStock.getWarehouseId());
        if(wmsRealtimeStock1 != null){
            wmsRealtimeStock.setQuantityOnhand(wmsRealtimeStock1.getQuantityOnhand().subtract(wmsRealtimeStock.getQuantityOnhand()));
            if(wmsRealtimeStock1.getWeight() != null && wmsRealtimeStock.getWeight() != null){
                wmsRealtimeStock.setWeight(wmsRealtimeStock1.getWeight().subtract(wmsRealtimeStock.getWeight()));
            }
            if(wmsRealtimeStock.getQuantityOnhand().compareTo(new BigDecimal(0)) < 0 || wmsRealtimeStock.getWeight().compareTo(new BigDecimal(0)) < 0){
                return WmsConstants.OUT_OF_STOCK;
            }
        }else {
            return WmsConstants.NOT_EXIST;
        }
        wmsRealtimeStock.setUpdateTime(DateUtils.getNowDate());
        wmsRealtimeStock.setMaterialStockId(wmsRealtimeStock1.getMaterialStockId());
        wmsRealtimeStock.setVersion(wmsRealtimeStock1.getVersion());
        return wmsRealtimeStockMapper.updateWmsRealtimeStock(wmsRealtimeStock);
    }

    /**
     * 批量删除库存
     *
     * @param materialStockIds 需要删除的库存主键
     * @return 结果
     */
    @Override
    public int deleteWmsRealtimeStockByMaterialStockIds(String materialStockIds)
    {
        return wmsRealtimeStockMapper.deleteWmsRealtimeStockByMaterialStockIds(Convert.toStrArray(materialStockIds));
    }

    /**
     * 删除库存信息
     *
     * @param materialStockId 库存主键
     * @return 结果
     */
    @Override
    public int deleteWmsRealtimeStockByMaterialStockId(Long materialStockId)
    {
        return wmsRealtimeStockMapper.deleteWmsRealtimeStockByMaterialStockId(materialStockId);
    }

    //通过物料唯一码查询物料信息
    @Override
    public WmsRealtimeStock getInfoByItemIdentifier(String itemIdentifier) {
        return wmsRealtimeStockMapper.selectByItemIdentifier(itemIdentifier);
    }

    @Override
    @Transactional
    public WmsRealtimeStock updateDetectionStatus(String itemIdentifier) {
        WmsRealtimeStock infoByItemIdentifier = getInfoByItemIdentifier(itemIdentifier);
        if (infoByItemIdentifier == null){
            throw new RealtimeStockException("该物料识别码不存在");
        }
        //删减拉丝在制品
        processTransaction(infoByItemIdentifier, "subtract");
        //添加漆包在制品
        infoByItemIdentifier.setAttr4(1);
        processTransaction(infoByItemIdentifier, "add");
        wmsRealtimeStockMapper.updateWmsRealtimeStock(infoByItemIdentifier);
        return infoByItemIdentifier;
    }

    @Override
    public boolean updateDetectionStatus2(List<String> itemIdentifiers, String type,String taskType) {
        List<WmsRealtimeStock> infoByItemIdentifier = getInfoByItemIdentifier2(itemIdentifiers);
        if (infoByItemIdentifier.isEmpty()) {
            throw new RealtimeStockException("该物料识别码不存在");
        }
        int start=0;
        for (WmsRealtimeStock wmsRealtimeStock : infoByItemIdentifier) {
            start=start+1;
            // 延时操作，保证每次插入都有时间差
            try {
                // 删减拉丝在制品
                processTransaction2(wmsRealtimeStock, "subtract", type,start,taskType);
                Thread.sleep(1000);  // 延时 100 毫秒（可以根据需要调整）
                // 添加漆包在制品
                wmsRealtimeStock.setAttr4(1);
                processTransaction2(wmsRealtimeStock, "add", type,start,taskType);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 恢复中断状态
                throw new RuntimeException("插入数据时线程被中断", e);
            }
            // 更新数据库
            wmsRealtimeStockMapper.updateWmsRealtimeStock(wmsRealtimeStock);
        }
        return true;
    }

    private List<WmsRealtimeStock> getInfoByItemIdentifier2(List<String> itemIdentifiers) {
        return wmsRealtimeStockMapper.getInfoByItemIdentifier2(itemIdentifiers);
    }

    @Override
    //@GlobalTransactional
//    @Transactional
    public MesOuputLog WriteBackWight(WmsRealtimeStock wmsRealtimeStock) {
        //修改报工记录库存
        MesOuputLog mesOuputLog = remoteMesService.queryByOutputSn(wmsRealtimeStock.getItemIdentifier(), SecurityConstants.INNER).getData();

        if (mesOuputLog == null){
            throw new RealtimeStockException("报工记录不存在");
        }

        if (mesOuputLog.getOutputWeight().compareTo(BigDecimal.ZERO) != 0){
            WmsRealtimeStock oldWmsRealtimeStock = wmsRealtimeStockMapper.selectWmsRealtimeStockByMaterialStockId(wmsRealtimeStock.getMaterialStockId());
            processTransaction(oldWmsRealtimeStock, "subtract");
        }
        processTransaction(wmsRealtimeStock, "add");
        mesOuputLog.setWeightTime(DateUtils.getNowDate());
        mesOuputLog.setOutputWeight(wmsRealtimeStock.getWeight());
        mesOuputLog.setOutputGrossWeight(wmsRealtimeStock.getTotalWeight());
        mesOuputLog.setOutputTareWeight(wmsRealtimeStock.getTareWeight());
        mesOuputLog.setIngredientsName(wmsRealtimeStock.getIngredientsName());
        mesOuputLog.setManufacturerName(wmsRealtimeStock.getManufacturerName());
        R<AjaxResult> ajaxResultR = remoteMesService.updateMesOutputLog(mesOuputLog, SecurityConstants.INNER);
        if (ajaxResultR.getCode() != 200){
            throw new RuntimeException("mes服务调用失败{"+ajaxResultR.getMsg()+"}");
        }
        wmsRealtimeStock.setUpdateBy(SecurityUtils.getUsername());
        wmsRealtimeStock.setUpdateTime(new Date());
        int result = wmsRealtimeStockMapper.updateWmsRealtimeStock(wmsRealtimeStock);
        if (result > 0){
            //返回报工记录
            return mesOuputLog;
        }else{
            throw new RealtimeStockException("回写重量失败");
        }
    }
}

