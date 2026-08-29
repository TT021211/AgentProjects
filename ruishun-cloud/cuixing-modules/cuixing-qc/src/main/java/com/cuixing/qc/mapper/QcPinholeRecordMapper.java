package com.cuixing.qc.mapper;

import com.cuixing.qc.entity.QcPinholeRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 质量管理-针孔送检记录(QcPinholeRecord)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-02 16:56:25
 */
public interface QcPinholeRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcPinholeRecord queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param qcPinholeRecord 查询条件
     * @return 对象列表
     */
    List<QcPinholeRecord> selectQcPinholeRecordList(QcPinholeRecord qcPinholeRecord);

    /**
     * 统计总行数
     *
     * @param qcPinholeRecord 查询条件
     * @return 总行数
     */
    long count(QcPinholeRecord qcPinholeRecord);

    /**
     * 新增数据
     *
     * @param qcPinholeRecord 实例对象
     * @return 影响行数
     */
    int insert(QcPinholeRecord qcPinholeRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcPinholeRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QcPinholeRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcPinholeRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QcPinholeRecord> entities);

    /**
     * 修改数据
     *
     * @param qcPinholeRecord 实例对象
     * @return 影响行数
     */
    int update(QcPinholeRecord qcPinholeRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

