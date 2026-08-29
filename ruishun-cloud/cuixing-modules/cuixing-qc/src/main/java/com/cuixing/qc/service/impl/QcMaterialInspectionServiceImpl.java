package com.cuixing.qc.service.impl;

import com.cuixing.common.core.utils.DateUtils;
import com.cuixing.common.security.utils.SecurityUtils;
import com.cuixing.qc.entity.QcMaterialInspection;
import com.cuixing.qc.mapper.QcMaterialInspectionMapper;
import com.cuixing.qc.service.QcMaterialInspectionService;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.annotation.Resource;

/**
 * 原料检验表(QcMaterialInspection)表服务实现类
 *
 * @author 阿江
 * @since 2024-12-13 10:01:27
 */
@Service("qcMaterialInspectionService")
public class QcMaterialInspectionServiceImpl implements QcMaterialInspectionService {
    @Resource
    private QcMaterialInspectionMapper qcMaterialInspectionMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcMaterialInspection queryById(Long id) {
        return this.qcMaterialInspectionMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param qcMaterialInspection 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcMaterialInspection> selectQcMaterialInspectionList(QcMaterialInspection qcMaterialInspection) {
        return this.qcMaterialInspectionMapper.selectQcMaterialInspectionList(qcMaterialInspection);
    }

    /**
     * 新增数据
     *
     * @param qcMaterialInspection 实例对象
     * @return 实例对象
     */
    @Override
    public QcMaterialInspection insert(QcMaterialInspection qcMaterialInspection) {
        qcMaterialInspection.setCreateTime(DateUtils.getNowDate());
        qcMaterialInspection.setCreateBy(SecurityUtils.getUsername());
        this.qcMaterialInspectionMapper.insert(qcMaterialInspection);
        return qcMaterialInspection;
    }

    /**
     * 修改数据
     *
     * @param qcMaterialInspection 实例对象
     * @return 实例对象
     */
    @Override
    public QcMaterialInspection update(QcMaterialInspection qcMaterialInspection) {
        qcMaterialInspection.setUpdateTime(DateUtils.getNowDate());
        qcMaterialInspection.setUpdateBy(SecurityUtils.getUsername());
        this.qcMaterialInspectionMapper.update(qcMaterialInspection);
        return this.queryById(qcMaterialInspection.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.qcMaterialInspectionMapper.deleteById(id) > 0;
    }
}
