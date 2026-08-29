package com.cuixing.qc.service.impl;

import com.cuixing.common.core.constant.SecurityConstants;
import com.cuixing.common.core.domain.R;
import com.cuixing.qc.entity.QcClassification;
import com.cuixing.qc.entity.QcInspectionItems;
import com.cuixing.qc.mapper.QcClassificationMapper;
import com.cuixing.qc.mapper.QcInspectionItemsMapper;
import com.cuixing.qc.service.QcInspectionItemsService;
import com.cuixing.system.api.RemoteMdService;
import com.cuixing.system.api.domain.md.MdItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;

/**
 * 质量管理-质检检测项(QcInspectionItems)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 16:56:23
 */
@Service("qcInspectionItemsService")
public class QcInspectionItemsServiceImpl implements QcInspectionItemsService {
    @Resource
    private QcInspectionItemsMapper qcInspectionItemsMapper;
    @Resource
    private QcClassificationMapper qcClassificationMapper;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcInspectionItems queryById(Long id) {
        QcInspectionItems qcInspectionItems = this.qcInspectionItemsMapper.queryById(id);
        if (qcInspectionItems.getItemTypeId()!=null){
            QcClassification qcClassification = this.qcClassificationMapper.queryById(qcInspectionItems.getItemTypeId());
            if (qcClassification != null){
                qcInspectionItems.setQcClassification(qcClassification);
            }
        }
        return qcInspectionItems;
    }

    /**
     * 分页查询
     *
     * @param qcInspectionItems 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcInspectionItems> selectQcInspectionItemsList(QcInspectionItems qcInspectionItems) {
        List<QcInspectionItems> qcInspectionItem = this.qcInspectionItemsMapper.selectQcInspectionItemsList(qcInspectionItems);
        if (qcInspectionItem.size() > 0) {
            for (QcInspectionItems qcInspection : qcInspectionItem){
                if (qcInspection.getItemTypeId()!=null){
                    QcClassification qcClassification = this.qcClassificationMapper.queryById(qcInspection.getItemTypeId());
                    if (qcClassification != null){
                        qcInspection.setQcClassification(qcClassification);
                    }
                }
            }
        }
        return qcInspectionItem;
    }

    /**
     * 新增数据
     *
     * @param qcInspectionItems 实例对象
     * @return 实例对象
     */
    @Override
    public QcInspectionItems insert(QcInspectionItems qcInspectionItems) {
        this.qcInspectionItemsMapper.insert(qcInspectionItems);
        return qcInspectionItems;
    }

    /**
     * 修改数据
     *
     * @param qcInspectionItems 实例对象
     * @return 实例对象
     */
    @Override
    public QcInspectionItems update(QcInspectionItems qcInspectionItems) {
        this.qcInspectionItemsMapper.update(qcInspectionItems);
        return this.queryById(qcInspectionItems.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.qcInspectionItemsMapper.deleteById(id) > 0;
    }
}
