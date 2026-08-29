package com.cuixing.qc.mapper;

import com.cuixing.qc.entity.QcWireDrawingRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 质量管理-拉丝过程检记录(QcWireDrawingRecord)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-02 16:56:26
 */
public interface QcWireDrawingRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcWireDrawingRecord queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param qcWireDrawingRecord 查询条件
     * @return 对象列表
     */
    List<QcWireDrawingRecord> selectQcWireDrawingRecordList(QcWireDrawingRecord qcWireDrawingRecord);

    /**
     * 统计总行数
     *
     * @param qcWireDrawingRecord 查询条件
     * @return 总行数
     */
    long count(QcWireDrawingRecord qcWireDrawingRecord);

    /**
     * 新增数据
     *
     * @param qcWireDrawingRecord 实例对象
     * @return 影响行数
     */
    int insert(QcWireDrawingRecord qcWireDrawingRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcWireDrawingRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QcWireDrawingRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcWireDrawingRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QcWireDrawingRecord> entities);

    /**
     * 修改数据
     *
     * @param qcWireDrawingRecord 实例对象
     * @return 影响行数
     */
    int update(QcWireDrawingRecord qcWireDrawingRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

