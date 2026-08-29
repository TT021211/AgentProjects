package com.cuixing.wms.service.impl;

import com.cuixing.wms.entity.WmsProductInout;
import com.cuixing.wms.mapper.WmsProductInoutMapper;
import com.cuixing.wms.service.WmsProductInoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;

/**
 * 成品出入库(WmsProductInout)表服务实现类
 *
 * @author makejava
 * @since 2024-10-25 09:39:26
 */
@Service("wmsProductInoutService")
public class WmsProductInoutServiceImpl implements WmsProductInoutService {
    @Autowired
    private WmsProductInoutMapper wmsProductInoutMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WmsProductInout queryById(Long id) {
        return this.wmsProductInoutMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param wmsProductInout 筛选条件
     * @return 查询结果
     */
    @Override
    public List<WmsProductInout> selectWmsProductInoutList(WmsProductInout wmsProductInout) {
        return this.wmsProductInoutMapper.selectWmsProductInoutList(wmsProductInout);
    }

    /**
     * 新增数据
     *
     * @param wmsProductInout 实例对象
     * @return 实例对象
     */
    @Override
    public WmsProductInout insert(WmsProductInout wmsProductInout) {
        this.wmsProductInoutMapper.insert(wmsProductInout);
        return wmsProductInout;
    }

    /**
     * 修改数据
     *
     * @param wmsProductInout 实例对象
     * @return 实例对象
     */
    @Override
    public WmsProductInout update(WmsProductInout wmsProductInout) {
        this.wmsProductInoutMapper.update(wmsProductInout);
        return this.queryById(wmsProductInout.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.wmsProductInoutMapper.deleteById(id) > 0;
    }

    @Override
    public List<WmsProductInout> selectCountWmsProductInoutList(WmsProductInout wmsProductInout) {
        return this.wmsProductInoutMapper.selectCountWmsProductInoutList(wmsProductInout);
    }

    @Override
    public boolean deleteByBoxCode(String boxCode) {
        return this.wmsProductInoutMapper.deleteByBoxCode(boxCode) > 0;
    }
}
