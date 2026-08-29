package com.cuixing.wms.service;

import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.WmsWeighRecord;

import java.math.BigDecimal;
import java.util.List;


/**
 * (WmsBox)表服务接口
 *
 * @author makejava
 * @since 2024-10-21 11:44:28
 */
public interface WmsBoxService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsBox queryById(Long id);

    WmsBox queryByBoxCode(String boxCode);

    /**
     * 分页查询
     *
     * @param wmsBox 筛选条件
     * @return 查询结果
     */
    List<WmsBox> selectWmsBoxList(WmsBox wmsBox);

    /**
     * 从托中移除关联信息(取消关联盘、箱记录)
     */
    boolean disassociation(List<WmsBox> wmsBox);

    /**
     * 新增数据
     *
     * @param wmsBox 实例对象
     * @return 实例对象
     */
    WmsBox insert(WmsBox wmsBox);

    /**
     * 修改数据
     *
     * @param wmsBox 实例对象
     * @return 实例对象
     */
    WmsBox update(WmsBox wmsBox);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    List<WmsBox> selectInstallation(List<String> installationCodes);

    boolean deleteByBoxCode(String boxCode);

    void uploadSecondaryNode(WmsBox wmsBox);

    void updateWeight(Long id, Double countNetWeight, Double countGrossWeight);
}
