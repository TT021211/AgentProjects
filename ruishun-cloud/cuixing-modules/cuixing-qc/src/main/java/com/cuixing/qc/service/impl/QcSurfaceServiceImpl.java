package com.cuixing.qc.service.impl;

import com.cuixing.qc.entity.QcSurface;
import com.cuixing.qc.mapper.QcSurfaceMapper;
import com.cuixing.qc.service.QcSurfaceService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * 质检管理-表面检缺陷项设置(QcSurface)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 16:56:25
 */
@Service("qcSurfaceService")
public class QcSurfaceServiceImpl implements QcSurfaceService {
    @Resource
    private QcSurfaceMapper qcSurfaceMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QcSurface queryById(Integer id) {
        return this.qcSurfaceMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param qcSurface 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcSurface> selectQcSurfaceList(QcSurface qcSurface) {
        return this.qcSurfaceMapper.selectQcSurfaceList(qcSurface);
    }

    /**
     * 新增数据
     *
     * @param qcSurface 实例对象
     * @return 实例对象
     */
    @Override
    public QcSurface insert(QcSurface qcSurface) {
        this.qcSurfaceMapper.insert(qcSurface);
        return qcSurface;
    }

    /**
     * 修改数据
     *
     * @param qcSurface 实例对象
     * @return 实例对象
     */
    @Override
    public QcSurface update(QcSurface qcSurface) {
        this.qcSurfaceMapper.update(qcSurface);
        return this.queryById(qcSurface.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.qcSurfaceMapper.deleteById(id) > 0;
    }
}
