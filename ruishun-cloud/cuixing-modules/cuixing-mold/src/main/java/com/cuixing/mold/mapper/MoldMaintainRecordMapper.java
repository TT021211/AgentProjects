package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldMaintainRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模具保养表--保养记录表(MoldMaintainRecord)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-25 16:52:05
 */
public interface MoldMaintainRecordMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param maintainRecordId 主键
     * @return 实例对象
     */
    MoldMaintainRecord queryById(Long maintainRecordId);

    /**
     * 查询指定行数据
     *
     * @param moldMaintainRecord 查询条件
     * @return 对象列表
     */
    List<MoldMaintainRecord> selectMoldMaintainRecordList(MoldMaintainRecord moldMaintainRecord);

    /**
     * 统计总行数
     *
     * @param moldMaintainRecord 查询条件
     * @return 总行数
     */
    long count(MoldMaintainRecord moldMaintainRecord);

    /**
     * 新增数据
     *
     * @param moldMaintainRecord 实例对象
     * @return 影响行数
     */
    int insert(MoldMaintainRecord moldMaintainRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldMaintainRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldMaintainRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldMaintainRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldMaintainRecord> entities);

    /**
     * 修改数据
     *
     * @param moldMaintainRecord 实例对象
     * @return 影响行数
     */
    int update(MoldMaintainRecord moldMaintainRecord);

    /**
     * 通过主键删除数据
     *
     * @param maintainRecordId 主键
     * @return 影响行数
     */
    int deleteById(Long maintainRecordId);

}

