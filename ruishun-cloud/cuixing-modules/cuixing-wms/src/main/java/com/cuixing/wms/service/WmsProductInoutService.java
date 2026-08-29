package com.cuixing.wms.service;

import com.cuixing.wms.entity.WmsBox;
import com.cuixing.wms.entity.WmsProductInout;

import java.util.List;


/**
 * 成品出入库(WmsProductInout)表服务接口
 *
 * @author makejava
 * @since 2024-10-25 09:39:26
 */
public interface WmsProductInoutService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WmsProductInout queryById(Long id);


    /**
     * 分页查询
     *
     * @param wmsProductInout 筛选条件
     * @return 查询结果
     */
    List<WmsProductInout> selectWmsProductInoutList(WmsProductInout wmsProductInout);

    /**
     * 新增数据
     *
     * @param wmsProductInout 实例对象
     * @return 实例对象
     */
    WmsProductInout insert(WmsProductInout wmsProductInout);

    /**
     * 修改数据
     *
     * @param wmsProductInout 实例对象
     * @return 实例对象
     */
    WmsProductInout update(WmsProductInout wmsProductInout);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 统计
     * @param wmsProductInout
     * @return
     */
    List<WmsProductInout> selectCountWmsProductInoutList(WmsProductInout wmsProductInout);

    boolean deleteByBoxCode(String boxCode);
}
