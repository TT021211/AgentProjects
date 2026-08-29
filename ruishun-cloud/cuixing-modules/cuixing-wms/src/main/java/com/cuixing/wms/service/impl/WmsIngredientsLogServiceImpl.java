package com.cuixing.wms.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.exception.md.ItemException;
import com.cuixing.common.core.exception.wms.RealtimeStockException;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.domain.md.MdItem;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import com.cuixing.system.api.domain.wms.WmsWarehouse;
import com.cuixing.wms.entity.WmsIngredientsLog;
import com.cuixing.wms.entity.vo.WmsIngredientsLogVo;
import com.cuixing.wms.mapper.WmsIngredientsLogMapper;
import com.cuixing.wms.mapper.WmsRealtimeStockMapper;
import com.cuixing.wms.mapper.WmsTransactionMapper;
import com.cuixing.wms.service.IWmsIngredientsLogService;
import com.cuixing.wms.service.IWmsWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 原料出入库日志;(wms_Ingredients_log)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-6-18
 */
@Service
public class WmsIngredientsLogServiceImpl implements IWmsIngredientsLogService {
    @Autowired
    private WmsIngredientsLogMapper wmsIngredientsLogMapper;
    @Autowired
    private WmsRealtimeStockMapper wmsRealtimeStockMapper;

    @Autowired
    private WmsRealtimeStockServiceImpl wmsRealtimeStockService;
    @Autowired
    private IWmsWarehouseService iWmsWarehouseService;

    @Autowired
    private WmsTransactionMapper wmsTransactionMapper;

    @Autowired
    private RemoteMdService remoteMdService;

    @Autowired
    private RemoteSystemService remoteSystemService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WmsIngredientsLog queryById(BigInteger id){
        return wmsIngredientsLogMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param wmsIngredientsLog 筛选条件
     * @return 查询结果
     */
    @Override
    public WmsIngredientsLogVo selectWmsIngredientsLogListSum(WmsIngredientsLog wmsIngredientsLog){
        List<WmsIngredientsLog> wmsIngredientsLogs = wmsIngredientsLogMapper.selectWmsIngredientsLogList(wmsIngredientsLog);
        int count = wmsIngredientsLogs.size();
        WmsIngredientsLogVo wmsIngredientsLogVo = new WmsIngredientsLogVo();
        wmsIngredientsLogVo.setCount(count);
        Double totalTareWeight = 0.0;
        Double totalNetWeight = 0.0;
        for (WmsIngredientsLog ingredientsLog : wmsIngredientsLogs) {
            if (ingredientsLog.getStatus().equals("0")){
                Double tareWeight = ingredientsLog.getTareWeight();
                if (null == tareWeight){
                    tareWeight = 0.0;
                }
                totalTareWeight += tareWeight;
                totalNetWeight += ingredientsLog.getNetWeight();
            }else{
                totalTareWeight -= ingredientsLog.getTareWeight();
                totalNetWeight -= ingredientsLog.getNetWeight();
            }
        }
        wmsIngredientsLogVo.setCount(count);
        wmsIngredientsLogVo.setTotalNetWeight(totalNetWeight);
        wmsIngredientsLogVo.setTotalTareWeight(totalTareWeight);
        wmsIngredientsLogVo.setWmsIngredientsLogList(wmsIngredientsLogs);
        return wmsIngredientsLogVo;
    }

     @Override
     public List<WmsIngredientsLog> selectWmsIngredientsLogList(WmsIngredientsLog wmsIngredientsLog) {
         return wmsIngredientsLogMapper.selectWmsIngredientsLogList(wmsIngredientsLog);
     }

     /**
     * 新增数据
     *
     * @param wmsIngredientsLog 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public WmsIngredientsLog insert(WmsIngredientsLog wmsIngredientsLog){
    	wmsIngredientsLog.setCreateTime(DateUtils.getNowDate());
		wmsIngredientsLog.setCreateBy(SecurityUtils.getUsername());

        String code = remoteSystemService.getAutoCode("ITEM_SOLE_CODE", "1");
        if (null == code){
            throw new ItemException("物料唯一码获取失败！");
        }
        wmsIngredientsLog.setCodeSn(code);
        //添加原料库存数据(相当于原料在制品)
        WmsRealtimeStock wmsRealtimeStock = new WmsRealtimeStock();
        //获取物料信息
        MdItem mdItem = remoteMdService.getItem(wmsIngredientsLog.getItemId(), SecurityConstants.FROM_SOURCE).getData();
        if (null == mdItem){
            throw new ItemException("物料获取失败");
        }
        wmsRealtimeStock.setItemId(mdItem.getId().longValue());
        wmsRealtimeStock.setItemCode(mdItem.getItemCode());
        wmsRealtimeStock.setItemName(mdItem.getItemName());
        wmsRealtimeStock.setItemTypeId(0L);
        wmsRealtimeStock.setItemIdentifier(code);
        wmsRealtimeStock.setSpecification(mdItem.getSpecification());
        wmsRealtimeStock.setItemTypeName(mdItem.getWeightMeasureName());
        wmsRealtimeStock.setAttr4(0);
        //批号
        wmsRealtimeStock.setBatchCode(wmsIngredientsLog.getLotNumber());
        //料号
        wmsRealtimeStock.setPartNumber(wmsIngredientsLog.getPartNumber());
        //通过工序获取对应仓库信息
        wmsRealtimeStock.setUnitOfMeasure(wmsIngredientsLog.getMeasureName());
        if (wmsIngredientsLog.getAttr2()==1){
            wmsRealtimeStock.setAttr1("4");
        }else{
            //库存类型
            wmsRealtimeStock.setAttr1("0");
        }
        //字典键值
        WmsWarehouse wmsWarehouse = iWmsWarehouseService.selectWmWarehouseBytype("6");
        if(null == wmsWarehouse){
            throw new RealtimeStockException("字典键值对应错误");
        }
        wmsRealtimeStock.setWarehouseId(wmsWarehouse.getWarehouseId());
        wmsRealtimeStock.setWarehouseCode(wmsWarehouse.getWarehouseCode());
        wmsRealtimeStock.setWarehouseName(wmsWarehouse.getWarehouseName());
        wmsRealtimeStock.setWeight(BigDecimal.valueOf(wmsIngredientsLog.getNetWeight()));
        wmsRealtimeStock.setTotalWeight(BigDecimal.valueOf(wmsIngredientsLog.getGrossWeight()));
        wmsRealtimeStock.setWeightUnit(wmsIngredientsLog.getMeasureName());
        wmsRealtimeStock.setCreateTime(DateUtils.getNowDate());
        wmsRealtimeStock.setCreateBy(SecurityUtils.getUsername());
        wmsRealtimeStockMapper.insertWmsRealtimeStock(wmsRealtimeStock);

        wmsRealtimeStockService.processTransaction(wmsRealtimeStock, "add");
        int res = wmsIngredientsLogMapper.insert(wmsIngredientsLog);
        if (res != 1){
            throw new RealtimeStockException("新增原料失败");
        }
        return wmsIngredientsLogMapper.queryById(wmsIngredientsLog.getId());
    }

    /**
     * 更新数据
     *
     * @param wmsIngredientsLog 实例对象
     * @return 实例对象
     */
    @Override
    public int update(WmsIngredientsLog wmsIngredientsLog){
        //通过二维码条码查询对应库存
        WmsRealtimeStock wmsRealtimeStock = wmsRealtimeStockMapper.selectByItemIdentifier(wmsIngredientsLog.getCodeSn());
        if (null == wmsRealtimeStock){
            throw new RealtimeStockException("该物料已使用，不能进行修改!!!");
        }

        wmsIngredientsLog.setUpdateTime(DateUtils.getNowDate());
		wmsIngredientsLog.setUpdateBy(SecurityUtils.getUsername());

        return wmsIngredientsLogMapper.update(wmsIngredientsLog);
    }

     @Override
     public WmsIngredientsLog queryByCodeSn(String codeSn) {
         return wmsIngredientsLogMapper.queryByCodeSn(codeSn);
     }

     /**
      * 通过主键批量删除数据处理入库错误
      *
      * @param ids 主键
      * @return 是否成功
      */
     @Override
     @Transactional
     public int deleteByIds(BigInteger[] ids){
         int result =0;
         for (int i = 0; i < ids.length; i++) {
             WmsIngredientsLog wmsIngredientsLog = wmsIngredientsLogMapper.queryById(ids[i]);
             //通过二维码条码查询对应库存
             WmsRealtimeStock wmsRealtimeStock = wmsRealtimeStockMapper.selectByItemIdentifier(wmsIngredientsLog.getCodeSn());
             if (null == wmsRealtimeStock){
                 throw new RealtimeStockException("该物料已使用，不能进行错误处理!!!");
             }
             //执行库存的出库操作
             int i1 = wmsTransactionMapper.deleteWmsTransactionByStockId(wmsRealtimeStock.getMaterialStockId());
             if (i1 == 0){
                 throw new RealtimeStockException("删除库存失败!!!");
             }
             int i2 = wmsRealtimeStockMapper.deleteWmsRealtimeStockByMaterialStockId(wmsRealtimeStock.getMaterialStockId());
             if (i2 == 0){
                 throw new RealtimeStockException("删除库存失败!!!");
             }
         }
         return wmsIngredientsLogMapper.deleteByIds(ids);
     }
     /**
      * 添加出库记录方式处理入库错误
      * @param ids
      * @return
      */
     @Override
     @Transactional
     public int mistakeByIds(BigInteger[] ids) {
         int result =0;
         for (int i = 0; i < ids.length; i++) {
             WmsIngredientsLog wmsIngredientsLog = wmsIngredientsLogMapper.queryById(ids[i]);
             //通过二维码条码查询对应库存
             WmsRealtimeStock wmsRealtimeStock = wmsRealtimeStockMapper.selectByItemIdentifier(wmsIngredientsLog.getCodeSn());
             if (null == wmsRealtimeStock){
                 throw new RealtimeStockException("该物料不存在!!!");
             }
             if (wmsRealtimeStock.getScanCount() > 1){
                 throw new RealtimeStockException("该物料已经过扫码，不能进行删除!!!");
             }
             //执行库存的出库操作
             result += wmsRealtimeStockService.mistakeWmsRealtimeStock(wmsRealtimeStock);
         }
         return wmsIngredientsLogMapper.deleteByIds(ids);
     }

     @Override
     public List<WmsIngredientsLog> selectMaxBatch(String manufacturerName) {
         List<WmsIngredientsLog> wmsIngredientsLogs = this.wmsIngredientsLogMapper.selectMaxBatch(manufacturerName);
         List<WmsIngredientsLog> resultLogs = new ArrayList<>();

         if (!wmsIngredientsLogs.isEmpty()) {
             // 获取列表中批号最大的一条记录
             WmsIngredientsLog maxBatchLog = wmsIngredientsLogs.stream()
                     .max(Comparator.comparingInt(log -> Integer.parseInt(extractBatchNumber(log.getLotNumber())))) // 按批号数字部分比较
                     .orElse(null); // 如果没有记录，返回 null

             resultLogs.add(maxBatchLog); // 将最大批号的记录添加到结果列表中
         }

         return resultLogs;
     }

    // 提取批号中的数字部分
    public static String extractBatchNumber(String batchNo) {
        // 正则表达式匹配批号结尾的数字
        Pattern pattern = Pattern.compile("(\\d+)$");
        Matcher matcher = pattern.matcher(batchNo);

        if (matcher.find()) {
            return matcher.group(1); // 返回匹配的数字部分
        }
        return ""; // 如果没有匹配到，返回空字符串
    }

 }
