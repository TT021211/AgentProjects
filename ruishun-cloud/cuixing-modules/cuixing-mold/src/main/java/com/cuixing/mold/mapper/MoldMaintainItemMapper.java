package com.cuixing.mold.mapper;

import com.cuixing.mold.entity.MoldMaintainItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 模具保养表--保养设置表(MoldMaintainItem)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-25 16:52:04
 */
public interface MoldMaintainItemMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param maintainItemId 主键
     * @return 实例对象
     */
    MoldMaintainItem queryById(Long maintainItemId);

    /**
     * 查询指定行数据
     *
     * @param moldMaintainItem 查询条件
     * @return 对象列表
     */
    List<MoldMaintainItem> selectMoldMaintainItemList(MoldMaintainItem moldMaintainItem);

    /**
     * 统计总行数
     *
     * @param moldMaintainItem 查询条件
     * @return 总行数
     */
    long count(MoldMaintainItem moldMaintainItem);

    /**
     * 新增数据
     *
     * @param moldMaintainItem 实例对象
     * @return 影响行数
     */
    int insert(MoldMaintainItem moldMaintainItem);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldMaintainItem> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<MoldMaintainItem> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<MoldMaintainItem> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<MoldMaintainItem> entities);

    /**
     * 修改数据
     *
     * @param moldMaintainItem 实例对象
     * @return 影响行数
     */
    int update(MoldMaintainItem moldMaintainItem);

    /**
     * 通过主键删除数据
     *
     * @param maintainItemId 主键
     * @return 影响行数
     */
    int deleteById(Long maintainItemId);

    List<MoldMaintainItem> selectMoldMaintainItemList2(MoldMaintainItem moldMaintainItem);
}

