package com.cuixing.qc.mapper;

import com.cuixing.qc.entity.QcEnamelProcessRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 质量管理-漆包过程检记录(QcEnamelProcessRecord)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-02 16:56:22
 */
public interface QcEnamelProcessRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QcEnamelProcessRecord queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param qcEnamelProcessRecord 查询条件
     * @return 对象列表
     */
    List<QcEnamelProcessRecord> selectQcEnamelProcessRecordList(QcEnamelProcessRecord qcEnamelProcessRecord);

    /**
     * 统计总行数
     *
     * @param qcEnamelProcessRecord 查询条件
     * @return 总行数
     */
    long count(QcEnamelProcessRecord qcEnamelProcessRecord);

    /**
     * 新增数据
     *
     * @param qcEnamelProcessRecord 实例对象
     * @return 影响行数
     */
    int insert(QcEnamelProcessRecord qcEnamelProcessRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcEnamelProcessRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QcEnamelProcessRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QcEnamelProcessRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QcEnamelProcessRecord> entities);

    /**
     * 修改数据
     *
     * @param qcEnamelProcessRecord 实例对象
     * @return 影响行数
     */
    int update(QcEnamelProcessRecord qcEnamelProcessRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

