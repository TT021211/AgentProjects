package com.cuixing.wms.service;

import com.cuixing.wms.entity.WmsWasteSilkPackageDetails;
import java.util.List;


/**
 * 废丝包详情表(WmsWasteSilkPackageDetails)表服务接口
 *
 * @author 阿江
 * @since 2025-04-18 14:20:55
 */
public interface WmsWasteSilkPackageDetailsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsWasteSilkPackageDetails queryById(Long id);

    /**
     * 分页查询
     *
     * @param wmsWasteSilkPackageDetails 筛选条件
     * @return 查询结果
     */
    List<WmsWasteSilkPackageDetails> selectWmsWasteSilkPackageDetailsList(WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails);

    /**
     * 新增数据
     *
     * @param wmsWasteSilkPackageDetails 实例对象
     * @return 实例对象
     */
    WmsWasteSilkPackageDetails insert(WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails);

    /**
     * 修改数据
     *
     * @param wmsWasteSilkPackageDetails 实例对象
     * @return 实例对象
     */
    WmsWasteSilkPackageDetails update(WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
