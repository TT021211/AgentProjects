package com.cuixing.wms.service.impl;

import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.wms.entity.WmsWasteSilkPackage;
import com.cuixing.wms.mapper.WmsWasteSilkPackageMapper;
import com.cuixing.wms.service.WmsWasteSilkPackageService;
import com.cuixing.wms.utils.NumberGenerator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 废丝包主表(WmsWasteSilkPackage)表服务实现类
 *
 * @author 阿江
 * @since 2025-04-18 14:16:23
 */
@Service("wmsWasteSilkPackageService")
public class WmsWasteSilkPackageServiceImpl implements WmsWasteSilkPackageService {
    @Resource
    private WmsWasteSilkPackageMapper wmsWasteSilkPackageMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WmsWasteSilkPackage queryById(Long id) {
        return this.wmsWasteSilkPackageMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param wmsWasteSilkPackage 筛选条件
     * @return 查询结果
     */
    @Override
    public List<WmsWasteSilkPackage> selectWmsWasteSilkPackageList(WmsWasteSilkPackage wmsWasteSilkPackage) {
        return this.wmsWasteSilkPackageMapper.selectWmsWasteSilkPackageList(wmsWasteSilkPackage);
    }

    /**
     * 新增数据
     *
     * @param wmsWasteSilkPackage 实例对象
     * @return 实例对象
     */
    @Override
    public WmsWasteSilkPackage insert(WmsWasteSilkPackage wmsWasteSilkPackage) {
        wmsWasteSilkPackage.setCreateBy(SecurityUtils.getUsername());
        wmsWasteSilkPackage.setCreateTime(DateUtils.getNowDate());
        this.wmsWasteSilkPackageMapper.insert(wmsWasteSilkPackage);
        return wmsWasteSilkPackage;
    }

    /**
     * 修改数据
     *
     * @param wmsWasteSilkPackage 实例对象
     * @return 实例对象
     */
    @Override
    public WmsWasteSilkPackage update(WmsWasteSilkPackage wmsWasteSilkPackage) {
        wmsWasteSilkPackage.setUpdateBy(SecurityUtils.getUsername());
        wmsWasteSilkPackage.setUpdateTime(DateUtils.getNowDate());
        this.wmsWasteSilkPackageMapper.update(wmsWasteSilkPackage);
        return this.queryById(wmsWasteSilkPackage.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.wmsWasteSilkPackageMapper.deleteById(id) > 0;
    }

    @Override
    public WmsWasteSilkPackage queryByTimeAndStatus(String type) {
        WmsWasteSilkPackage wmsWasteSilkPackage = this.wmsWasteSilkPackageMapper.queryByTimeAndStatus(type);
        if (wmsWasteSilkPackage == null){
            wmsWasteSilkPackage = new WmsWasteSilkPackage();
            String bg = NumberGenerator.generateNumber("BG", new Date());
            wmsWasteSilkPackage.setWasteSilkPackageCode(bg);
            wmsWasteSilkPackage.setWasteSilkPackageName("废丝包"+"-"+bg);
            wmsWasteSilkPackage.setWasteSilkPackageType(type);
            wmsWasteSilkPackage.setWeightCount(BigDecimal.valueOf(0.00));
            wmsWasteSilkPackage.setIsDeleted(0);
            wmsWasteSilkPackage.setVersion(0);
            wmsWasteSilkPackage.setStatus("1");
            wmsWasteSilkPackage.setCreateBy(SecurityUtils.getUsername());
            wmsWasteSilkPackage.setCreateTime(new Date());
            this.wmsWasteSilkPackageMapper.insert(wmsWasteSilkPackage);
        }
        return this.wmsWasteSilkPackageMapper.queryById(wmsWasteSilkPackage.getId());
    }

    @Override
    public WmsWasteSilkPackage updateStatus(WmsWasteSilkPackage wmsWasteSilkPackage) {
        return this.wmsWasteSilkPackageMapper.updateStatus(wmsWasteSilkPackage);
    }
}
