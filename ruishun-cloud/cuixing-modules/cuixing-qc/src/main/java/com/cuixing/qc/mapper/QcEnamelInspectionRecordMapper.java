package com.cuixing.qc.mapper;

import com.cuixing.qc.entity.QcEnamelInspectionRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 质量模块-漆包巡检记录(QcEnamelInspectionRecord)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-02 16:56:22
 */
public interface QcEnamelInspectionRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcEnamelInspectionRecord queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param qcEnamelInspectionRecord 查询条件
     * @return 对象列表
     */
    List<QcEnamelInspectionRecord> selectQcEnamelInspectionRecordList(QcEnamelInspectionRecord qcEnamelInspectionRecord);

    /**
     * 统计总行数
     *
     * @param qcEnamelInspectionRecord 查询条件
     * @return 总行数
     */
    long count(QcEnamelInspectionRecord qcEnamelInspectionRecord);

    /**
     * 新增数据
     *
     * @param qcEnamelInspectionRecord 实例对象
     * @return 影响行数
     */
    int insert(QcEnamelInspectionRecord qcEnamelInspectionRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcEnamelInspectionRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QcEnamelInspectionRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcEnamelInspectionRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QcEnamelInspectionRecord> entities);

    /**
     * 修改数据
     *
     * @param qcEnamelInspectionRecord 实例对象
     * @return 影响行数
     */
    int update(QcEnamelInspectionRecord qcEnamelInspectionRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

