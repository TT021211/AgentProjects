package com.cuixing.eq.service;

import java.math.BigInteger;
import com.cuixing.eq.entity.EqRepairReport;
import java.util.List;

 /**
 * 设备报修单;(eq_repair_report)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-7-1
 */
public interface IEqRepairReportService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    EqRepairReport queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param eqRepairReport 筛选条件
     * @return 查询结果
     */
    List<EqRepairReport> selectEqRepairReportList(EqRepairReport eqRepairReport);
    /** 
     * 新增数据
     *
     * @param eqRepairReport 实例对象
     * @return 实例对象
     */
    int insert(EqRepairReport eqRepairReport);
    /** 
     * 更新数据
     *
     * @param eqRepairReport 实例对象
     * @return 实例对象
     */
    int update(EqRepairReport eqRepairReport);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

     /**
      * * 写入设备知识库
      * @param id
      * @return
      */
    int addKnowledge(BigInteger id);

     List<EqRepairReport> knowledgeList(EqRepairReport eqRepairReport);

     int deleteKnowledgeByIds(BigInteger[] ids);

     List<EqRepairReport> getByWorkbenchEnamellingId(BigInteger workbenchEnamellingId);
 }