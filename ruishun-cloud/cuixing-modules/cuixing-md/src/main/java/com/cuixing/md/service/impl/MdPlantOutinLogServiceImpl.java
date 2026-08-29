package com.cuixing.md.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.common.core.exception.md.ItemException;
import com.cuixing.common.core.exception.wms.RealtimeStockException;
import com.cuixing.common.core.web.domain.AjaxResult;
import com.cuixing.md.entity.MdPlantList;
import com.cuixing.md.mapper.MdPlantListMapper;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.system.api.RemoteWmsService;
import com.cuixing.system.api.domain.md.MdItem;
import com.cuixing.system.api.domain.wms.WmsRealtimeStock;
import com.cuixing.system.api.domain.wms.WmsWarehouse;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cuixing.md.entity.MdPlantOutinLog;
import com.cuixing.md.mapper.MdPlantOutinLogMapper;
import com.cuixing.md.service.IMdPlantOutinLogService;
import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
 /**
 * 油漆出入库记录;(md_plant_outin_log)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2024-5-31
 */
@Service
public class MdPlantOutinLogServiceImpl implements IMdPlantOutinLogService{
    @Autowired
    private MdPlantOutinLogMapper mdPlantOutinLogMapper;

    @Autowired
    private RemoteSystemService remoteSystemService;

   /* @Autowired
    private RemoteWmsService remoteWmsService;*/

    @Autowired
    private MdPlantListMapper mdPlantListMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public MdPlantOutinLog queryById(BigInteger id){
        return mdPlantOutinLogMapper.queryById(id);
    }


     @Override
     public MdPlantOutinLog selectByPlantCode(String plantCode) {
         return mdPlantOutinLogMapper.selectByPlantCode(plantCode);
    }

     /**
     * 分页查询
     *
     * @param mdPlantOutinLog 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MdPlantOutinLog> selectMdPlantOutinLogList(MdPlantOutinLog mdPlantOutinLog){
       return mdPlantOutinLogMapper.selectMdPlantOutinLogList(mdPlantOutinLog);
    }

    /**
     * 新增数据
     *
     * @param mdPlantOutinLogs 实例对象
     * @return 实例对象
     */
    @Override
    //@GlobalTransactional
    public List<MdPlantOutinLog> insert(List<MdPlantOutinLog> mdPlantOutinLogs){
        List<MdPlantOutinLog> result = new ArrayList<>();

        for (MdPlantOutinLog mdPlantOutinLog : mdPlantOutinLogs) {
            mdPlantOutinLog.setCreateTime(DateUtils.getNowDate());
            mdPlantOutinLog.setCreateBy(SecurityUtils.getUsername());
            //入库
            if (mdPlantOutinLog.getStatus().equals("0")) {
                //通过油漆id查询油漆
                MdPlantList mdPlantList = mdPlantListMapper.queryById(mdPlantOutinLog.getPlantId());
                if (mdPlantList == null) {
                    throw new ItemException("该油漆不存在！！！");
                }
                mdPlantOutinLog.setPlantName(mdPlantList.getPlantName());
                mdPlantOutinLog.setPlantType(mdPlantList.getPlantTypeName());
                mdPlantOutinLog.setPlantCode(mdPlantList.getPlantCode());

                String code = remoteSystemService.getAutoCode("PLANT_SPLE_CODE", SecurityConstants.INNER);
                mdPlantOutinLog.setIdentificationCode(code);
                WmsRealtimeStock wmsRealtimeStock = new WmsRealtimeStock();
                //获取物料信息
                wmsRealtimeStock.setItemId(mdPlantList.getId().longValue());
                wmsRealtimeStock.setItemCode(mdPlantList.getPlantCode());
                wmsRealtimeStock.setItemName(mdPlantList.getPlantName());
                wmsRealtimeStock.setItemTypeId(0L);
                wmsRealtimeStock.setItemIdentifier(code);
                wmsRealtimeStock.setSpecification(mdPlantList.getPlantTypeName());
                //批号
                wmsRealtimeStock.setBatchCode(mdPlantOutinLog.getCustomerBatch());
                //通过工序获取对应仓库信息
                wmsRealtimeStock.setUnitOfMeasure(mdPlantOutinLog.getMeasureName());
                //字典键值
               /* WmsWarehouse wmsWarehouse = remoteWmsService.getInfoByType("7", SecurityConstants.INNER).getData();
                if (null == wmsWarehouse) {
                    throw new RealtimeStockException("字典键值对应错误");
                }
                wmsRealtimeStock.setWarehouseId(wmsWarehouse.getWarehouseId());
                wmsRealtimeStock.setWarehouseCode(wmsWarehouse.getWarehouseCode());
                wmsRealtimeStock.setWarehouseName(wmsWarehouse.getWarehouseName());

                wmsRealtimeStock.setWeight(BigDecimal.valueOf(mdPlantOutinLog.getWeight()));
                wmsRealtimeStock.setTotalWeight(BigDecimal.valueOf(mdPlantOutinLog.getWeight()));
                wmsRealtimeStock.setWeightUnit(mdPlantOutinLog.getMeasureName());
                wmsRealtimeStock.setAttr4(0);
                wmsRealtimeStock.setAttr1("2");
                R<AjaxResult> ajaxResultR = remoteWmsService.addRealtimeStock(wmsRealtimeStock, SecurityConstants.INNER);
                if (ajaxResultR.getCode() != 200) {
                    throw new RealtimeStockException("库存增加失败！！！");
                }
                //出库
            } else if (mdPlantOutinLog.getStatus().equals("1")) {
                //通过物料唯一码查询物料数据
                WmsRealtimeStock inputStock = remoteWmsService.getInfoByItemIdentifier(mdPlantOutinLog.getIdentificationCode(), SecurityConstants.INNER).getData();
                //库存减少对应物料
                R<AjaxResult> ajaxResultR = remoteWmsService.subtractRealtimeStock(inputStock, SecurityConstants.INNER);
                if (ajaxResultR.getCode() != 200) {
                    throw new RealtimeStockException("库存删减失败！！！");
                }
            }*/
                mdPlantOutinLogMapper.insert(mdPlantOutinLog);

                MdPlantOutinLog plantOutinLog = mdPlantOutinLogMapper.queryById(mdPlantOutinLog.getId());
                result.add(plantOutinLog);
            }
        }
        return result;
    }

    /**
     * 更新数据
     *
     * @param mdPlantOutinLog 实例对象
     * @return 实例对象
     */
    @Override
    public int update(MdPlantOutinLog mdPlantOutinLog){
        mdPlantOutinLog.setUpdateTime(DateUtils.getNowDate());
		mdPlantOutinLog.setUpdateBy(SecurityUtils.getUsername());
        return mdPlantOutinLogMapper.update(mdPlantOutinLog);
    }
    /**
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public int deleteByIds(BigInteger[] ids){
        return mdPlantOutinLogMapper.deleteByIds(ids);
    }
}
