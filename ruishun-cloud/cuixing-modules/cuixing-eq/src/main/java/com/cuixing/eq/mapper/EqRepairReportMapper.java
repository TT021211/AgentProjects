package com.cuixing.eq.mapper;

import com.cuixing.eq.entity.vo.EqKanban;
import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.eq.entity.EqRepairReport;
import org.apache.ibatis.annotations.Param;

/**
 * 设备报修单;(eq_repair_report)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-7-1
 */
@Mapper
public interface EqRepairReportMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqRepairReport queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param eqRepairReport 查询条件
     * @return 对象列表
     */
    List<EqRepairReport> selectEqRepairReportList(EqRepairReport eqRepairReport);
    /** 
     * 新增数据
     *
     * @param eqRepairReport 实例对象
     * @return 影响行数
     */
    int insert(EqRepairReport eqRepairReport);
    
    /** 
     * 更新数据
     *
     * @param eqRepairReport 实例对象
     * @return 影响行数
     */
    int update(EqRepairReport eqRepairReport);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

    int addKnowledge(BigInteger id);

    List<EqRepairReport> knowledgeList(EqRepairReport eqRepairReport);

    int deleteKnowledgeByIds(BigInteger[] ids);

    List<EqRepairReport> getByEquipmentId(@Param("equipmentId") BigInteger equipmentId);

    List<EqRepairReport> getStatusNotCompleteList();

    List<EqRepairReport> getListTowDay();

    EqKanban getRepairReportKanban();
}