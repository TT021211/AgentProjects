package com.cuixing.mes.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.mes.entity.MesCarryLog;

 /**
 * 搬运日志;(mes_carry_log)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-10-10
 */
@Mapper
public interface MesCarryLogMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MesCarryLog queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mesCarryLog 查询条件
     * @return 对象列表
     */
    List<MesCarryLog> selectMesCarryLogList(MesCarryLog mesCarryLog);
    /** 
     * 新增数据
     *
     * @param mesCarryLog 实例对象
     * @return 影响行数
     */
    int insert(MesCarryLog mesCarryLog);
    
    /** 
     * 更新数据
     *
     * @param mesCarryLog 实例对象
     * @return 影响行数
     */
    int update(MesCarryLog mesCarryLog);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);
}