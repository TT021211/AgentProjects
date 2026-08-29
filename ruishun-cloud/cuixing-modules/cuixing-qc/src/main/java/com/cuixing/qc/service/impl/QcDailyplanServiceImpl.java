package com.cuixing.qc.service.impl;

import com.cuixing.qc.entity.QcDailyplan;
import com.cuixing.qc.mapper.QcDailyplanMapper;
import com.cuixing.qc.service.QcDailyplanService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * 日常送检计划(QcDailyplan)表服务实现类
 *
 * @author makejava
 * @since 2024-07-02 16:56:19
 */
@Service("qcDailyplanService")
public class QcDailyplanServiceImpl implements QcDailyplanService {
    @Resource
    private QcDailyplanMapper qcDailyplanMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param dailyplanId 主键
     * @return 实例对象
     */
    @Override
    public QcDailyplan queryById(Long dailyplanId) {
        return this.qcDailyplanMapper.queryById(dailyplanId);
    }

    /**
     * 分页查询
     *
     * @param qcDailyplan 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QcDailyplan> selectQcDailyplanList(QcDailyplan qcDailyplan) {
        return this.qcDailyplanMapper.selectQcDailyplanList(qcDailyplan);
    }

    /**
     * 新增数据
     *
     * @param qcDailyplan 实例对象
     * @return 实例对象
     */
    @Override
    public QcDailyplan insert(QcDailyplan qcDailyplan) {
        this.qcDailyplanMapper.insert(qcDailyplan);
        return qcDailyplan;
    }

    /**
     * 修改数据
     *
     * @param qcDailyplan 实例对象
     * @return 实例对象
     */
    @Override
    public QcDailyplan update(QcDailyplan qcDailyplan) {
        this.qcDailyplanMapper.update(qcDailyplan);
        return this.queryById(qcDailyplan.getDailyplanId());
    }

    /**
     * 通过主键删除数据
     *
     * @param dailyplanId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long dailyplanId) {
        return this.qcDailyplanMapper.deleteById(dailyplanId) > 0;
    }
}
