package com.cuixing.qc.service.impl;

import com.cuixing.common.core.exception.mes.wm.BussinessException;
import com.cuixing.qc.entity.QcClassification;
import com.cuixing.qc.entity.QcDefect;
import com.cuixing.qc.entity.QcDefectClassification;
import com.cuixing.qc.mapper.QcClassificationMapper;
import com.cuixing.qc.mapper.QcDefectClassificationMapper;
import com.cuixing.qc.mapper.QcDefectMapper;
import com.cuixing.qc.service.QcDefectService;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.annotation.Resource;

/**
 * 质检管理-缺陷项设置(QcDefect)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 17:22:41
 */
@Service("qcDefectService")
public class QcDefectServiceImpl implements QcDefectService {
    @Resource
    private QcDefectMapper qcDefectMapper;
    @Resource
    private QcDefectClassificationMapper qcDefectClassificationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param defectId 主键
     * @return 实例对象
     */
    @Override
    public QcDefect queryById(Long defectId) {
        QcDefect qcDefect = this.qcDefectMapper.queryById(defectId);
        if (qcDefect != null){
            if (qcDefect.getDefectClassificationId() != null){
                QcDefectClassification qcDefectClassification = this.qcDefectClassificationMapper.queryById(Integer.valueOf(qcDefect.getDefectClassificationId()));
                if(qcDefectClassification != null){
                    qcDefect.setQcDefectClassification(qcDefectClassification);
                }
            }else{
                throw new BussinessException("缺陷分类id不能为空");
            }
        }
        return qcDefect;
    }

    /**
     * 分页查询
     *
     * @param qcDefect 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcDefect> selectQcDefectList(QcDefect qcDefect) {
        List<QcDefect> qcDefects = this.qcDefectMapper.selectQcDefectList(qcDefect);
        if (qcDefects.size() > 0) {
            for (QcDefect newqcDefect : qcDefects){
                if (newqcDefect.getDefectClassificationId() != null){
                    QcDefectClassification qcDefectClassification = this.qcDefectClassificationMapper.queryById(Integer.valueOf(newqcDefect.getDefectClassificationId()));
                    if(qcDefectClassification != null){
                        newqcDefect.setQcDefectClassification(qcDefectClassification);
                    }
                }else{
                    throw new BussinessException("缺陷分类id不能为空");
                }
            }
        }
        return qcDefects;
    }

    /**
     * 新增数据
     *
     * @param qcDefect 实例对象
     * @return 实例对象
     */
    @Override
    public QcDefect insert(QcDefect qcDefect) {
        this.qcDefectMapper.insert(qcDefect);
        return qcDefect;
    }

    /**
     * 修改数据
     *
     * @param qcDefect 实例对象
     * @return 实例对象
     */
    @Override
    public QcDefect update(QcDefect qcDefect) {
        this.qcDefectMapper.update(qcDefect);
        return this.queryById(qcDefect.getDefectId());
    }

    /**
     * 通过主键删除数据
     *
     * @param defectId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long defectId) {
        return this.qcDefectMapper.deleteById(defectId) > 0;
    }
}
