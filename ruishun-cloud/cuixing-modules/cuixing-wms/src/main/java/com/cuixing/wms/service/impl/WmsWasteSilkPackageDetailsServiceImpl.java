package com.cuixing.wms.service.impl;

import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.system.api.RemoteSystemService;
import com.cuixing.wms.entity.WmsWasteSilkPackage;
import com.cuixing.wms.entity.WmsWasteSilkPackageDetails;
import com.cuixing.wms.mapper.WmsWasteSilkPackageDetailsMapper;
import com.cuixing.wms.mapper.WmsWasteSilkPackageMapper;
import com.cuixing.wms.service.WmsWasteSilkPackageDetailsService;
import com.cuixing.wms.utils.NumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;

/**
 * 废丝包详情表(WmsWasteSilkPackageDetails)表服务实现类
 *
 * @author 阿江
 * @since 2025-04-18 14:20:55
 */
@Service("wmsWasteSilkPackageDetailsService")
public class WmsWasteSilkPackageDetailsServiceImpl implements WmsWasteSilkPackageDetailsService {
    @Resource
    private WmsWasteSilkPackageDetailsMapper wmsWasteSilkPackageDetailsMapper;
    @Autowired
    private WmsWasteSilkPackageMapper wmsWasteSilkPackageMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WmsWasteSilkPackageDetails queryById(Long id) {
        return this.wmsWasteSilkPackageDetailsMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param wmsWasteSilkPackageDetails 筛选条件
     * @return 查询结果
     */
    @Override
    public List<WmsWasteSilkPackageDetails> selectWmsWasteSilkPackageDetailsList(WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails) {
        return this.wmsWasteSilkPackageDetailsMapper.selectWmsWasteSilkPackageDetailsList(wmsWasteSilkPackageDetails);
    }

    /**
     * 新增数据
     *
     * @param wmsWasteSilkPackageDetails 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional
    public WmsWasteSilkPackageDetails insert(WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails) {
        wmsWasteSilkPackageDetails.setCreateTime(DateUtils.getNowDate());
        wmsWasteSilkPackageDetails.setCreateBy(SecurityUtils.getUsername());
        String packageCode = wmsWasteSilkPackageDetails.getWasteSilkPackageCode();
        if (packageCode != null && !packageCode.trim().isEmpty()) {
            // 判断包号是否存在
            WmsWasteSilkPackage wmsWasteSilkPackage = this.wmsWasteSilkPackageMapper.queryByCode(packageCode);

            if (wmsWasteSilkPackage != null) {
                if (!Objects.equals(wmsWasteSilkPackage.getWasteSilkPackageType(), wmsWasteSilkPackageDetails.getWasteSilkPackageType())){
                    throw new RuntimeException("包类型不匹配,请重新选对应类型包进行装包！");
                }
                // 更新包重量
                wmsWasteSilkPackage.setWeightCount(wmsWasteSilkPackage.getWeightCount().add(wmsWasteSilkPackageDetails.getWeight()));
                wmsWasteSilkPackage.setUpdateTime(DateUtils.getNowDate());
                wmsWasteSilkPackage.setUpdateBy(SecurityUtils.getUsername());
                this.wmsWasteSilkPackageMapper.update(wmsWasteSilkPackage);
            }
        }else{
            throw new RuntimeException("包号不能为空！");
        }
        this.wmsWasteSilkPackageDetailsMapper.insert(wmsWasteSilkPackageDetails);
        return wmsWasteSilkPackageDetails;
    }

    /**
     * 修改数据
     *
     * @param wmsWasteSilkPackageDetails 实例对象
     * @return 实例对象
     */
    @Override
    public WmsWasteSilkPackageDetails update(WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails) {
        wmsWasteSilkPackageDetails.setUpdateTime(DateUtils.getNowDate());
        wmsWasteSilkPackageDetails.setUpdateBy(SecurityUtils.getUsername());
        this.wmsWasteSilkPackageDetailsMapper.update(wmsWasteSilkPackageDetails);
        return this.queryById(wmsWasteSilkPackageDetails.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        WmsWasteSilkPackageDetails wmsWasteSilkPackageDetails = wmsWasteSilkPackageDetailsMapper.queryById(id);
        if (wmsWasteSilkPackageDetails.getWasteSilkPackageCode() != null) {
            //更新包重量
            WmsWasteSilkPackage wmsWasteSilkPackage = this.wmsWasteSilkPackageMapper.queryByCode(wmsWasteSilkPackageDetails.getWasteSilkPackageCode());
            if (wmsWasteSilkPackage != null) {
                wmsWasteSilkPackage.setWeightCount(wmsWasteSilkPackage.getWeightCount().subtract(wmsWasteSilkPackageDetails.getWeight()));
                this.wmsWasteSilkPackageMapper.update(wmsWasteSilkPackage);
            }

        }
        return this.wmsWasteSilkPackageDetailsMapper.deleteById(id) > 0;
    }
}
