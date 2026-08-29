package com.cuixing.wms.service;

import com.cuixing.wms.entity.WmsWasteSilkPackage;
import java.util.List;


/**
 * 废丝包主表(WmsWasteSilkPackage)表服务接口
 *
 * @author 阿江
 * @since 2025-04-18 14:16:23
 */
public interface WmsWasteSilkPackageService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsWasteSilkPackage queryById(Long id);

    /**
     * 分页查询
     *
     * @param wmsWasteSilkPackage 筛选条件
     * @return 查询结果
     */
    List<WmsWasteSilkPackage> selectWmsWasteSilkPackageList(WmsWasteSilkPackage wmsWasteSilkPackage);

    /**
     * 新增数据
     *
     * @param wmsWasteSilkPackage 实例对象
     * @return 实例对象
     */
    WmsWasteSilkPackage insert(WmsWasteSilkPackage wmsWasteSilkPackage);

    /**
     * 修改数据
     *
     * @param wmsWasteSilkPackage 实例对象
     * @return 实例对象
     */
    WmsWasteSilkPackage update(WmsWasteSilkPackage wmsWasteSilkPackage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    WmsWasteSilkPackage queryByTimeAndStatus(String type);

    WmsWasteSilkPackage updateStatus(WmsWasteSilkPackage wmsWasteSilkPackage);
}
