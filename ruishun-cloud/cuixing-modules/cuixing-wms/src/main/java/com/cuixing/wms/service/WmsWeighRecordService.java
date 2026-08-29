package com.cuixing.wms.service;

import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.WmsWeighRecord;
import com.cuixing.wms.entity.vo.WmsCountDataVo;
import com.cuixing.wms.entity.vo.WmsWeighRecordVo;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 仓储管理---称重记录(WmsWeighRecord)表服务接口
 *
 * @author makejava
 * @since 2024-10-21 11:44:27
 */
public interface WmsWeighRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsWeighRecord queryById(Long id);

    /**
     * 查询当天三个最大值
     *
     * @return
     */
    WmsWeighRecord selectMaxValues();

    /**
     * 从托中移除关联信息(取消关联盘、箱记录)
     */
    boolean disassociationToRecord(List<WmsBox> wmsBox);

    /**
     * 分页查询
     *
     * @param wmsWeighRecord 筛选条件
     * @return 查询结果
     */
    List<WmsWeighRecord> selectWmsWeighRecordList(WmsWeighRecord wmsWeighRecord);

    /**
     * 新增数据
     *
     * @param wmsWeighRecord 实例对象
     * @return 实例对象
     */
    WmsWeighRecord insert(WmsWeighRecord wmsWeighRecord);

    /**
     * 修改数据
     *
     * @param wmsWeighRecord 实例对象
     * @return 实例对象
     */
    WmsWeighRecord update(WmsWeighRecord wmsWeighRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    List<WmsWeighRecord> queryYesterday();

    boolean deleteByBoxCode(String boxCode);

    WmsWeighRecord selectWmsWeighRecordToParams(WmsWeighRecord wmsWeighRecord);
    WmsWeighRecord selectWmsWeighRecordToParams2(WmsWeighRecord wmsWeighRecord);

    void updateNodeData(WmsWeighRecord wmWeigh);
    void uploadSecondaryNode(WmsWeighRecord wmWeigh);

    WmsCountDataVo getWmsCountData();

    Map<String, List<WmsWeighRecordVo>> getWmsCountDataForTime(List<String> wmsSpecs, Date date, String type);
}
