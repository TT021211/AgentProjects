package com.cuixing.qc.service;

import com.cuixing.qc.entity.QcInspectionSummart;
import com.cuixing.qc.entity.vo.QcCountData;

import java.math.BigInteger;
import java.util.List;


/**
 * 检验汇总表(QcInspectionSummart)表服务接口
 *
 * @author 阿江
 * @since 2024-12-13 10:01:27
 */
public interface QcInspectionSummartService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcInspectionSummart queryById(BigInteger id);

    /**
     * 分页查询
     *
     * @param qcInspectionSummart 筛选条件
     * @return 查询结果
     */
    List<QcInspectionSummart> selectQcInspectionSummartList(QcInspectionSummart qcInspectionSummart);

    /**
     * 新增数据
     *
     * @param qcInspectionSummart 实例对象
     * @return 实例对象
     */
    QcInspectionSummart insert(QcInspectionSummart qcInspectionSummart);

    /**
     * 修改数据
     *
     * @param qcInspectionSummart 实例对象
     * @return 实例对象
     */
    QcInspectionSummart update(QcInspectionSummart qcInspectionSummart);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(BigInteger id);

    List<QcCountData> getQcCountData();

}
