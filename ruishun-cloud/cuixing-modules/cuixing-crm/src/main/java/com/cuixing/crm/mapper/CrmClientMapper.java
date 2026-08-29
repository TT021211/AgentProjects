package com.cuixing.crm.mapper;

import java.util.List;

import com.cuixing.crm.entity.CrmClient;
import com.cuixing.crm.entity.vo.CrmClientAnalyze;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

/**
 * 客户表;(crm_client)表数据库访问层
 * @author : cuixing-keyun-auto
 * @date : 2024-3-27
 */
@Mapper
public interface CrmClientMapper {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CrmClient queryById(BigInteger id);
    /**
     * 分页查询指定行数据
     *
     * @param crmClient 查询条件
     * @return 对象列表
     */
    List<CrmClient> selectCrmClientList(CrmClient crmClient);
    /**
     * 统计总行数
     *
     * @param crmClient 查询条件
     * @return 总行数
     */
    long count(CrmClient crmClient);
    /**
     * 新增数据
     *
     * @param crmClient 实例对象
     * @return 影响行数
     */
    int insert(CrmClient crmClient);
    /**
     * 批量新增数据
     *
     * @param entities List<CrmClient> 实例对象列表
     * @return 影响行数
     */
     int insertBatch(@Param("entities") List<CrmClient> entities);
    /**
     * 批量新增或按主键更新数据
     *
     * @param entities List<CrmClient> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<CrmClient> entities);
    /**
     * 更新数据
     *
     * @param crmClient 实例对象
     * @return 影响行数
     */
    int update(CrmClient crmClient);
    /**
     * 通过主键逻辑删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(BigInteger id);
    /**
     * 通过主键批量逻辑删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
    /**
     * 通过主键物理删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int removeById(BigInteger id);

    CrmClientAnalyze totalClientNum();

    @MapKey("leadSource")
    List<Map<String,Object>> sourceClientNum();

    @MapKey("status")
    List<Map<String,Object>> statusAnalyze();

    @MapKey("provinceName")
    List<Map<String,Object>> addressAnalyze();

    Set<BigInteger> selectIds();
}