package com.cuixing.wms.service;

import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.WmsInstallation;

import java.util.List;


/**
 * (WmsInstallation)表服务接口
 *
 * @author makejava
 * @since 2024-10-21 11:44:29
 */
public interface WmsInstallationService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsInstallation queryById(Long id);

    /**
     * 分页查询
     *
     * @param wmsInstallation 筛选条件
     * @return 查询结果
     */
    List<WmsInstallation> selectWmsInstallationList(WmsInstallation wmsInstallation);

    /**
     * 新增数据
     *
     * @param wmsInstallation 实例对象
     * @return 实例对象
     */
    WmsInstallation insert(WmsInstallation wmsInstallation);

    /**
     * 修改数据
     *
     * @param wmsInstallation 实例对象
     * @return 实例对象
     */
    WmsInstallation update(WmsInstallation wmsInstallation);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 通过托编码查询
     * @param installationCode
     * @return
     */
    WmsInstallation queryByCode(String installationCode);

    /**
     * 删除托当中的箱
     */
    boolean removeForBoxAndWeighRecord(List<WmsBox> boxes);

    void uploadSecondaryNode(WmsInstallation wmsInstallation);
}
