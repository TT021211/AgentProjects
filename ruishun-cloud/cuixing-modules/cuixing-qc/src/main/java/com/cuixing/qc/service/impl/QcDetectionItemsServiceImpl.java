package com.cuixing.qc.service.impl;

import com.cuixing.qc.entity.QcDetectionItems;
import com.cuixing.qc.mapper.QcDetectionItemsMapper;
import com.cuixing.qc.service.QcDetectionItemsService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质量管理-检测项设置(QcDetectionItems)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 16:56:21
 */
@Service("qcDetectionItemsService")
public class QcDetectionItemsServiceImpl implements QcDetectionItemsService {
    @Resource
    private QcDetectionItemsMapper qcDetectionItemsMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcDetectionItems queryById(Integer id) {
        return this.qcDetectionItemsMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param qcDetectionItems 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcDetectionItems> selectQcDetectionItemsList(QcDetectionItems qcDetectionItems) {
        return this.qcDetectionItemsMapper.selectQcDetectionItemsList(qcDetectionItems);
    }

    /**
     * 新增数据
     *
     * @param qcDetectionItems 实例对象
     * @return 实例对象
     */
    @Override
    public QcDetectionItems insert(QcDetectionItems qcDetectionItems) {
        this.qcDetectionItemsMapper.insert(qcDetectionItems);
        return qcDetectionItems;
    }

    /**
     * 修改数据
     *
     * @param qcDetectionItems 实例对象
     * @return 实例对象
     */
    @Override
    public QcDetectionItems update(QcDetectionItems qcDetectionItems) {
        this.qcDetectionItemsMapper.update(qcDetectionItems);
        return this.queryById(qcDetectionItems.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.qcDetectionItemsMapper.deleteById(id) > 0;
    }
}
