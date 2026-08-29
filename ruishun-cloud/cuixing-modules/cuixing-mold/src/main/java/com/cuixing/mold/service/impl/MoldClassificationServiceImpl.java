package com.cuixing.mold.service.impl;

import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.mold.entity.MoldClassification;
import com.cuixing.mold.mapper.MoldClassificationMapper;
import com.cuixing.mold.service.MoldClassificationService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

/**
 * 模具分类表(MoldClassification)表服务实现类
 *
 * @author makejava
 * @since 2024-06-26 11:04:32
 */
@Service("moldClassificationService")
public class MoldClassificationServiceImpl implements MoldClassificationService {
    @Resource
    private MoldClassificationMapper moldClassificationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param classificationId 主键
     * @return 实例对象
     */
    @Override
    public MoldClassification queryById(Long classificationId) {
        return this.moldClassificationMapper.queryById(classificationId);
    }

    @Override
    public MoldClassification queryclassificationType(String classificationType,String classificationName) {
        return this.moldClassificationMapper.queryclassificationType(classificationType,classificationName);
    }

    /**
     * 分页查询
     *
     * @param moldClassification 筛选条件
     * @return 查询结果
     */
    @Override
    public List<MoldClassification> selectMoldClassificationList(MoldClassification moldClassification) {
        return this.moldClassificationMapper.selectMoldClassificationList(moldClassification);
    }

    /**
     * 新增数据
     *
     * @param moldClassification 实例对象
     * @return 实例对象
     */
    @Override
    public MoldClassification insert(MoldClassification moldClassification) {
        this.moldClassificationMapper.insert(moldClassification);
        return moldClassification;
    }

    /**
     * 修改数据
     *
     * @param moldClassification 实例对象
     * @return 实例对象
     */
    @Override
    public MoldClassification update(MoldClassification moldClassification) {
        this.moldClassificationMapper.update(moldClassification);
        return this.queryById(moldClassification.getClassificationId());
    }

    /**
     * 通过主键删除数据
     *
     * @param classificationId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long classificationId) {
        return this.moldClassificationMapper.deleteById(classificationId) > 0;
    }

    @Override
    public List<MoldClassification> selectMoldClassificationList2(MoldClassification moldClassification) {
        return this.moldClassificationMapper.selectMoldClassificationList2(moldClassification);
    }
}
