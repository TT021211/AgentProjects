package com.cuixing.mes.service;

import java.math.BigInteger;
import com.cuixing.mes.entity.MesOperationMethod;
import java.util.List;

 /**
 * 操作规范/作业指导书;(mes_operation_method)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2024-6-27
 */
public interface IMesOperationMethodService{
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    MesOperationMethod queryById(BigInteger id);
    /** 
     * 分页查询
     *
     * @param mesOperationMethod 筛选条件
     * @return 查询结果
     */
    List<MesOperationMethod> selectMesOperationMethodList(MesOperationMethod mesOperationMethod);
    /** 
     * 新增数据
     *
     * @param mesOperationMethod 实例对象
     * @return 实例对象
     */
    int insert(MesOperationMethod mesOperationMethod);
    /** 
     * 更新数据
     *
     * @param mesOperationMethod 实例对象
     * @return 实例对象
     */
    int update(MesOperationMethod mesOperationMethod);
    /** 
     * 通过主键批量删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    int deleteByIds(BigInteger[] ids);

    List<MesOperationMethod> getByEnanellingWorkbenchId(BigInteger enanellingWorkbenchId,String operationType);
 }