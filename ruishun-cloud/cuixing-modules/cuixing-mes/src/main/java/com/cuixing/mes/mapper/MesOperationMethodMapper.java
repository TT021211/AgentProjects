package com.cuixing.mes.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.math.BigInteger;
import java.util.List;
import com.cuixing.mes.entity.MesOperationMethod;
import org.apache.ibatis.annotations.Param;

/**
 * 操作规范/作业指导书;(mes_operation_method)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2024-6-27
 */
@Mapper
public interface MesOperationMethodMapper{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MesOperationMethod queryById(BigInteger id);
    /** 
     * 分页查询指定行数据
     *
     * @param mesOperationMethod 查询条件
     * @return 对象列表
     */
    List<MesOperationMethod> selectMesOperationMethodList(MesOperationMethod mesOperationMethod);
    /** 
     * 新增数据
     *
     * @param mesOperationMethod 实例对象
     * @return 影响行数
     */
    int insert(MesOperationMethod mesOperationMethod);
    
    /** 
     * 更新数据
     *
     * @param mesOperationMethod 实例对象
     * @return 影响行数
     */
    int update(MesOperationMethod mesOperationMethod);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 影响行数
     */
    int deleteByIds(BigInteger[] ids);

     List<MesOperationMethod> getByEnanellingWorkbenchId(@Param("enanellingWorkbenchId") BigInteger enanellingWorkbenchId,@Param("operationType") String operationType);
 }